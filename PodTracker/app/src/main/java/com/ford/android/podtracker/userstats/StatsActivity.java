package com.ford.android.podtracker.userstats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.User;


public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        User user = (User) getIntent().getSerializableExtra("user");

        initFragment(StatsFragment.newInstance(user));
    }

    private void initFragment(Fragment statsFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_placeholder_stats, statsFragment);
        ft.commit();
    }
}
