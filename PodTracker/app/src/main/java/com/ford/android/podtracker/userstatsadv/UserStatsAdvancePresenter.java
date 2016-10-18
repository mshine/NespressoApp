package com.ford.android.podtracker.userstatsadv;

import android.support.annotation.NonNull;

import com.ford.android.podtracker.data.UsersServiceApi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by MSHINE7 on 29/09/2016.
 */

public class UserStatsAdvancePresenter implements UserStatsAdvanceContract.UserActionsListener {

    @NonNull
    private final UsersServiceApi mUsersServiceApi;

    @NonNull
    private final UserStatsAdvanceContract.View mUserProfileView;

    public UserStatsAdvancePresenter(@NonNull UsersServiceApi usersServiceApi, @NonNull UserStatsAdvanceContract.View userProfileView) {
        mUsersServiceApi = checkNotNull(usersServiceApi);
        mUserProfileView = checkNotNull(userProfileView);
    }

    @Override
    public void loadStats(int userId) {
        mUserProfileView.showStats(mUsersServiceApi.getUserStats(userId));
    }

}
