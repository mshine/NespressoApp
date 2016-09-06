package com.ford.android.podtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
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


    //users Table Columns
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String POD_COUNT = "pod_count";

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
                _ID + " INTEGER PRIMARY KEY ," +
                NAME + " TEXT," +
                POD_COUNT + " INTEGER" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

            onCreate(db);
        }
    }

    public void insertUser(UserData userData) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(NAME, userData.name);
            values.put(POD_COUNT, userData.podCount);

            db.insertOrThrow(TABLE_USERS, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add user to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<UserData> getUsersList() {

        List<UserData> users = new ArrayList<>();

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    UserData userData = new UserData();
                    userData.id = cursor.getInt(cursor.getColumnIndex(_ID));
                    userData.name = cursor.getString(cursor.getColumnIndex(NAME));
                    userData.podCount = cursor.getInt(cursor.getColumnIndex(POD_COUNT));

                    users.add(userData);

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

    public UserData getUser(int id) {

        UserData user = new UserData();
        String USER_SELECT_QUERY = "SELECT * FROM " + TABLE_USERS + " WHERE " + _ID + " = " + id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);

        if (cursor != null) {
            cursor.moveToFirst();

            user.id = cursor.getInt(cursor.getColumnIndex(_ID));
            user.name = cursor.getString(cursor.getColumnIndex(NAME));
            user.podCount = cursor.getInt(cursor.getColumnIndex(POD_COUNT));
            cursor.close();
        } else {
            Log.d(TAG, "Error while trying to get a user from database");
        }
        return user;
    }

    void updatePodCount(int id, int podCount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(POD_COUNT, podCount);
        db.update(TABLE_USERS, cv, _ID + "=" + id, null);
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
