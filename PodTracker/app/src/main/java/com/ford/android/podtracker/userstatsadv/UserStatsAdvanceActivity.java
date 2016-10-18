package com.ford.android.podtracker.userstatsadv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.User;

public class UserStatsAdvanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats_advance);

        User user = (User) getIntent().getSerializableExtra("user");

        initFragment(UserStatsAdvanceFragment.newInstance(user));

//        UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(user);
//        UserStatsAdvanceFragment userStatsFragment = UserStatsAdvanceFragment.newInstance(user);

    }

    private void initFragment(Fragment userStatsAdvanceFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_stats_advance, userStatsAdvanceFragment);
        ft.commit();
    }
}
