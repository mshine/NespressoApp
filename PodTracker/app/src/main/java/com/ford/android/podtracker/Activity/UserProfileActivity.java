package com.ford.android.podtracker.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ford.android.podtracker.Fragment.UserDetailFragment;
import com.ford.android.podtracker.Fragment.UserStatsFragment;
import com.ford.android.podtracker.Model.User;
import com.ford.android.podtracker.R;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        User user = (User) getIntent().getSerializableExtra("user");
        UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(user);
        UserStatsFragment userStatsFragment = UserStatsFragment.newInstance(user);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_detail, userDetailFragment);
        ft.add(R.id.fragment_stats, userStatsFragment);
        ft.commit();

    }
}
