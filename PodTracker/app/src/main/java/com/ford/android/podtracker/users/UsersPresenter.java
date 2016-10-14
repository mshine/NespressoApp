package com.ford.android.podtracker.users;

import android.support.annotation.NonNull;

import com.ford.android.podtracker.data.User;
import com.ford.android.podtracker.data.UsersServiceApi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mshine7 on 22/09/2016.
 */
public class UsersPresenter implements UsersContract.UserActionsListener {

    private final UsersServiceApi mUsersServiceApi;
    private final UsersContract.View mUsersView;

    public UsersPresenter(@NonNull UsersServiceApi usersRepository, @NonNull UsersContract.View usersView) {
        mUsersServiceApi = checkNotNull(usersRepository, "usersRepository cannot be null");
        mUsersView = checkNotNull(usersView, "usersView cannot be null!");
    }

    @Override
    public void loadUsers(boolean forceUpdate) {
        mUsersView.showUsers(mUsersServiceApi.getAllUsers());
    }

    @Override
    public void addNewUser() {
        mUsersView.showAddUser();
    }

    @Override
    public void openUserProfile(@NonNull User requestedUser) {
        checkNotNull(requestedUser, "requestedUser cannot be null!");
        mUsersView.showUserProfile(requestedUser);
    }

    @Override
    public void openUserStats(User user) {
        mUsersView.showStats(user);
    }

    @Override
    public void openAddPod(User user) {
        mUsersView.showAddPods(user);
    }

    @Override
    public String deleteUser(User user) {
        mUsersServiceApi.deleteUser(user);
        mUsersView.showUsers(mUsersServiceApi.getAllUsers());
        return user.getName();
    }
}
