package com.ford.android.podtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPodActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @BindView(R.id.btn_add_pod)
    ImageButton btnAddPod;
    @BindView(R.id.tv_add_pod_count)
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

                UserPods up = new UserPods();

                up.setUserId(user.getId());
                up.setTransactionDate(new Date());

                dbHelper.insertUserPods(up);

            }
        });

    }
}