package com.ford.android.podtracker.adduser;

import android.support.annotation.NonNull;

import com.ford.android.podtracker.data.User;
import com.ford.android.podtracker.data.UsersServiceApi;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by mshine7 on 22/09/2016.
 */
public class AddUserPresenter implements AddUserContract.UserActionsListener {

    @NonNull
    private final UsersServiceApi mUsersServiceApi;
    @NonNull
    private final AddUserContract.View mAddUserView;

    public AddUserPresenter(@NonNull UsersServiceApi usersServiceApi, @NonNull AddUserContract.View addUserView) {
        mUsersServiceApi = checkNotNull(usersServiceApi);
        mAddUserView = checkNotNull(addUserView);
    }

    @Override
    public void saveUser(String name) {
        User newUser = new User(name);
        if (newUser.isEmpty()) {
            //TODO showemptyusererror? - test
        } else {
            mUsersServiceApi.saveUser(newUser);
            mAddUserView.showUsersList(name);
        }
    }
}
