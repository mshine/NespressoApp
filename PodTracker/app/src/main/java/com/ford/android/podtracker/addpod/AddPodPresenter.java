package com.ford.android.podtracker.addpod;

import android.support.annotation.NonNull;

import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.PodType;
import com.ford.android.podtracker.data.User;
import com.ford.android.podtracker.data.UsersServiceApi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by MSHINE7 on 26/09/2016.
 */

public class AddPodPresenter implements AddPodContract.UserActionsListener {

    @NonNull
    private final UsersServiceApi mUsersServiceApi;
    @NonNull
    private final AddPodContract.View mAddPodView;

    public AddPodPresenter(@NonNull UsersServiceApi usersServiceApi, @NonNull AddPodContract.View addPodView) {
        mUsersServiceApi = checkNotNull(usersServiceApi);
        mAddPodView = checkNotNull(addPodView);
    }

    @Override
    public void addPod(int userId, int podCount) {
        mUsersServiceApi.incrementPodCount(userId, podCount);
    }



    @Override
    public void addToTotal(int userId, double amount) {
        mUsersServiceApi.incrementTotalOwed(userId, amount);
    }

    @Override
    public void insertUserPods(PodTransaction podTransaction) {
        mUsersServiceApi.insertPods(podTransaction);
    }

    @Override
    public PodType getPodType(String podName) {
        return mUsersServiceApi.getPodType(podName);
    }

    @Override
    public User loadUser(int userId) {
        return mUsersServiceApi.getUser(userId);
    }
}
