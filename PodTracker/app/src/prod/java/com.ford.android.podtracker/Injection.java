package com.ford.android.podtracker.adduser;

import com.ford.android.podtracker.data.UsersServiceApi;
import com.ford.android.podtracker.data.UsersServiceApiImpl;

/**
 * Created by mshine7 on 22/09/2016.
 */
public class Injection {
    public static UsersServiceApi provideUsersRepository() {
        return new UsersServiceApiImpl();
    }

//    public static UsersRepository provideUsersRepository() {
//        return UserRepositories.getInMemoryRepoInstance(new UsersServiceApiImpl());
//    }
    //MOCK

}
