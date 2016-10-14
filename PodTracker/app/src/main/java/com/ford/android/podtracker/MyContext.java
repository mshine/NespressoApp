package com.ford.android.podtracker;

import android.app.Application;
import android.content.Context;

/**
 * Created by mshine7 on 23/09/2016.
 */
public class MyContext extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyContext.context;
    }
}