package com.ford.android.podtracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ford.android.podtracker.DbHelper;
import com.ford.android.podtracker.Model.PodTransaction;
import com.ford.android.podtracker.R;
import com.ford.android.podtracker.Model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;
import static java.util.Calendar.getInstance;

public class AddPodActivity extends AppCompatActivity {

    @BindView(R.id.btn_add_pod)
    ImageButton btnAddPod;
    @BindView(R.id.tv_add_pod_count)
    TextView tvPodCount;
    private DbHelper dbHelper;
    User user;
    boolean podIncremented;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pod);

        ButterKnife.bind(this);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        user = (User) getIntent().getSerializableExtra("user");

        int podCount[] = {dbHelper.getUser(user.getId()).getPodCount()};
        tvPodCount.setText(valueOf(podCount[0]));

        btnAddPod.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(AddPodActivity.this);
            alert.setTitle("Add Pod Confirmation");
            alert.setMessage("Are you sure you want to increment the pod count for " + user.getName() + "?");
            alert.setPositiveButton("Yes", (dialog, which) -> {
                podIncremented = true;
                podCount[0]++;
                tvPodCount.setText(valueOf(podCount[0]));

                dbHelper.updatePodCount(user.getId(), podCount[0]);

                PodTransaction podTransaction = new PodTransaction();

                podTransaction.setUserId(user.getId());
                podTransaction.setTransactionDate(getInstance().getTime());

                dbHelper.insertUserPods(podTransaction);
                dialog.dismiss();
            });
            alert.setNegativeButton("No", (dialog, which) -> {
                podIncremented = false;
                dialog.dismiss();
            });
            alert.show();
        });

    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        if (podIncremented) {
            intent.putExtra("user", user);
            intent.putExtra("podCount", user.getPodCount() + 1);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        super.finish();
    }
}