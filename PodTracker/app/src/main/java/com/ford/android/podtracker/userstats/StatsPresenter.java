package com.ford.android.podtracker.userstats;

import android.support.annotation.NonNull;

import com.ford.android.podtracker.data.UsersServiceApi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mshine7 on 23/09/2016.
 */
public class StatsPresenter implements StatsContract.UserActionsListener {

    @NonNull
    private final UsersServiceApi mUsersServiceApi;
    @NonNull
    private final StatsContract.View mStatsView;

    public StatsPresenter(@NonNull UsersServiceApi usersServiceApi, @NonNull StatsContract.View statsView) {
        mUsersServiceApi = checkNotNull(usersServiceApi);
        mStatsView = checkNotNull(statsView);
    }

    @Override
    public void loadStats(int userId) {
        mStatsView.showStats(mUsersServiceApi.getUserStats(userId));
    }
}
