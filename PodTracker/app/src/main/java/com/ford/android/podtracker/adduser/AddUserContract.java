package com.ford.android.podtracker.adduser;

/**
 * Created by mshine7 on 22/09/2016.
 */
public interface AddUserContract {

    interface View {
        void showUsersList(String name);
    }

    interface UserActionsListener {
        void saveUser(String name);
    }

}
