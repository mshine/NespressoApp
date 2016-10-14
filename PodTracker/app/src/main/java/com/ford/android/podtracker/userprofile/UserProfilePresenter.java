package com.ford.android.podtracker.userprofile;

import android.support.annotation.NonNull;

import com.ford.android.podtracker.data.UsersServiceApi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by MSHINE7 on 29/09/2016.
 */

public class UserProfilePresenter implements UserProfileContract.UserActionsListener {

    @NonNull
    private final UsersServiceApi mUsersServiceApi;

    @NonNull
    private final UserProfileContract.View mUserProfileView;

    public UserProfilePresenter(@NonNull UsersServiceApi usersServiceApi, @NonNull UserProfileContract.View userProfileView) {
        mUsersServiceApi = checkNotNull(usersServiceApi);
        mUserProfileView = checkNotNull(userProfileView);
    }

    @Override
    public void loadStats(int userId) {
        mUserProfileView.showStats(mUsersServiceApi.getUserStats(userId));
    }

    @Override
    public void loadDetails(int userId) {
        mUserProfileView.showDetails(userId);
    }
}
