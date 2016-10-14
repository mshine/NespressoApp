package com.ford.android.podtracker.userprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.User;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        User user = (User) getIntent().getSerializableExtra("user");

        initDetailFragment(UserDetailFragment.newInstance(user));
        initStatsFragment(UserStatsFragment.newInstance(user));

//        UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(user);
//        UserStatsFragment userStatsFragment = UserStatsFragment.newInstance(user);

    }

    private void initDetailFragment(Fragment userDetailFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_detail, userDetailFragment);
        ft.commit();
    }

    private void initStatsFragment(Fragment userStatsFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_stats, userStatsFragment);
        ft.commit();
    }
}
