package com.ford.android.podtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPodActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @BindView(R.id.btnAddPod)
    ImageButton btnAddPod;
    @BindView(R.id.tvAddPodCount)
    TextView tvPodCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pod);

        ButterKnife.bind(this);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        final User user = (User) getIntent().getSerializableExtra("user");

        final int[] podCount = {dbHelper.getUser(user.getId()).getPodCount()};
        tvPodCount.setText(String.valueOf(podCount[0]));

        btnAddPod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                podCount[0]++;
                tvPodCount.setText(String.valueOf(podCount[0]));

                dbHelper.updatePodCount(user.getId(), podCount[0]);
            }
        });

    }
}