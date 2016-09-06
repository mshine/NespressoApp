package com.ford.android.podtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddPodActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private TextView tvPodCount;
    private ImageButton btnAddPod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pod);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        tvPodCount = (TextView) findViewById(R.id.tvAddPodCount);
        final UserData user = (UserData) getIntent().getSerializableExtra("user");
        final UserData user2 = dbHelper.getUser(user.id);
        tvPodCount.setText(String.valueOf(user2.podCount));

        btnAddPod = (ImageButton) findViewById(R.id.btnAddPod);
        btnAddPod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user2.podCount++;
                tvPodCount.setText(String.valueOf(user2.podCount));

                dbHelper.updatePodCount(user2.id, user2.podCount);
    }
});

    }
}