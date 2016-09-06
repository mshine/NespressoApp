package com.ford.android.podtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mshine7 on 02/09/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    // Database Info
    private static final String DATABASE_NAME = "PodTracker";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_USER_PODS = "user_pods";

    //users Table Columns
    private static final String u_ID = "_id";
    private static final String u_NAME = "name";
    private static final String u_POD_COUNT = "pod_count";

    //user_pods Table Columns
    private static final String up_ID = "_id";
    private static final String up_USER_ID = "user_id";
    private static final String up_TRANSACTION_DATE = "transaction_date";

    private static DbHelper mDbHelper;

    public static synchronized DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new DbHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }


    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                u_ID + " INTEGER PRIMARY KEY ," +
                u_NAME + " TEXT," +
                u_POD_COUNT + " INTEGER" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_USER_PODS_TABLE = "CREATE TABLE " + TABLE_USER_PODS +
                "(" +
                up_ID + " INTEGER PRIMARY KEY ," +
                up_USER_ID + " INTEGER," +
                up_TRANSACTION_DATE + " DATETIME" +
                ")";
        db.execSQL(CREATE_USER_PODS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PODS);

            onCreate(db);
        }
    }

    public void insertUser(User user) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(u_NAME, user.getName());
            values.put(u_POD_COUNT, user.getPodCount());

            db.insertOrThrow(TABLE_USERS, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add user to database");
        } finally {
            db.endTransaction();
        }
    }

    public void insertUserPods(UserPods userPods) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(up_USER_ID, userPods.getUserId());
            values.put(up_TRANSACTION_DATE, userPods.getTransactionDate().toString());

            db.insertOrThrow(TABLE_USER_PODS, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add user pods to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<User> getUsersList() {

        List<User> users = new ArrayList<>();

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex(u_ID)));
                    user.setName(cursor.getString(cursor.getColumnIndex(u_NAME)));
                    user.setPodCount(cursor.getInt(cursor.getColumnIndex(u_POD_COUNT)));

                    users.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return users;
    }

    public List<UserPods> getUsersPodsList(int userId) {

        List<UserPods> userPods = new ArrayList<>();

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_USER_PODS + " WHERE " + up_USER_ID + " = " + userId;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    UserPods user = new UserPods();
                    user.setId(cursor.getInt(cursor.getColumnIndex(up_ID)));
                    user.setUserId(cursor.getInt(cursor.getColumnIndex(up_USER_ID)));
                    user.setTransactionDate(new Date(cursor.getString(cursor.getColumnIndex(up_TRANSACTION_DATE))));

                    userPods.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get userPods from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return userPods;
    }

    public User getUser(int id) {

        User user = new User();
        String USER_SELECT_QUERY = "SELECT * FROM " + TABLE_USERS + " WHERE " + u_ID + " = " + id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);

        if (cursor != null) {
            cursor.moveToFirst();

            user.setId(cursor.getInt(cursor.getColumnIndex(u_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(u_NAME)));
            user.setPodCount(cursor.getInt(cursor.getColumnIndex(u_POD_COUNT)));
            cursor.close();
        } else {
            Log.d(TAG, "Error while trying to get a user from database");
        }
        return user;
    }

    void updatePodCount(int id, int podCount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(u_POD_COUNT, podCount);
        db.update(TABLE_USERS, cv, u_ID + "=" + id, null);
    }

    void deleteUserRow(int id) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("delete from " + TABLE_USERS + " where _id ='" + id + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete user");
        } finally {
            db.endTransaction();
        }
    }
}
