package com.ford.android.podtracker;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPodActivity extends AppCompatActivity {

    @BindView(R.id.btn_add_pod)
    ImageButton btnAddPod;
    @BindView(R.id.tv_add_pod_count)
    TextView tvPodCount;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pod);

        ButterKnife.bind(this);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        final User user = (User) getIntent().getSerializableExtra("user");

        int podCount[] = {dbHelper.getUser(user.getId()).getPodCount()};
        tvPodCount.setText(String.valueOf(podCount));

        btnAddPod.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(AddPodActivity.this);
            alert.setTitle("Add Pod Confirmation");
            alert.setMessage("Are you sure you want to increment the pod count for " + user.getName() + "?");
            alert.setPositiveButton("Yes", (dialog, which) -> {
                podCount[0]++;
                tvPodCount.setText(String.valueOf(podCount[0]));

                dbHelper.updatePodCount(user.getId(), podCount[0]);

                PodTransaction podTransaction = new PodTransaction();

                podTransaction.setUserId(user.getId());
                podTransaction.setTransactionDate(Calendar.getInstance().getTime());

                dbHelper.insertUserPods(podTransaction);
                dialog.dismiss();
            });
            alert.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            alert.show();
        });

    }
}