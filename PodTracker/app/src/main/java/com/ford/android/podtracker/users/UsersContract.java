package com.ford.android.podtracker.users;

import android.support.annotation.NonNull;

import com.ford.android.podtracker.data.User;

import java.util.List;

/**
 * Created by mshine7 on 22/09/2016.
 */
public interface UsersContract {

    interface View {

        void showUsers(List<User> users);

        void showAddUser();

        void showUserProfile(User user);

        void showStats(User user);

        void showAddPods(User user);

        void showHour(User user);

    }

    interface UserActionsListener {

        void loadUsers(boolean forceUpdate);

        void addNewUser();

        void openUserProfile(@NonNull User requestedUser);

        void openUserStats(User user);

        void openAddPod(User user);

        String deleteUser(User user);

        void openHour(User selectedUser);
    }
}
