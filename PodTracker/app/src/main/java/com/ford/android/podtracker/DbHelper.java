package com.ford.android.podtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ford.android.podtracker.Model.PodTransaction;
import com.ford.android.podtracker.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mshine7 on 02/09/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    // Database Info
    private static final String DATABASE_NAME = "podtracker";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_USER_PODS = "user_pods";
    private static final String TABLE_POD_TYPES = "pod_types";

    //users Table Columns
    private static final String u_ID = "_id";
    private static final String u_NAME = "name";
    private static final String u_POD_COUNT = "pod_count";

    //user_pods Table Columns
    private static final String up_ID = "_id";
    private static final String up_USER_ID = "user_id";
    private static final String up_TRANSACTION_DATE = "transaction_date";

    //pod_types Table Columns
    private static final String p_ID = "_id";
    private static final String p_NAME = "pod_name";
    private static final String p_PRICE = "price";

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
    DbHelper(Context context) {
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

//        String CREATE_POD_TYPES_TABLE = "CREATE TABLE " + TABLE_POD_TYPES +
//                "(" +
//                p_ID + " INTEGER PRIMARY KEY ," +
//                p_NAME + " TEXT," +
//                p_PRICE + " DECIMAL" +
//                ")";
//        db.execSQL(CREATE_POD_TYPES_TABLE);
//
//        insertPodTypes();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PODS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POD_TYPES);

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

//    public void insertPodTypes() {
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        db.beginTransaction();
//
//        try {
//            ContentValues values = new ContentValues();
//            values.put(p_NAME, "Kazaar");
//            values.put(p_PRICE, 0.40);
//            db.insertOrThrow(TABLE_USERS, null, values);
//
//            values.put(p_NAME, "Caramelito");
//            values.put(p_PRICE, 0.50);
//            db.insertOrThrow(TABLE_USERS, null, values);
//
//            db.setTransactionSuccessful();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Log.d(TAG, "Error while trying to add pod type to database");
//        } finally {
//            db.endTransaction();
//        }
//    }

    public void insertUserPods(PodTransaction podTransaction) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(up_USER_ID, podTransaction.getUserId());
            values.put(up_TRANSACTION_DATE, podTransaction.getTransactionDate().toString());

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

    public List<PodTransaction> getUsersPodsList(int userId) {

        List<PodTransaction> userPods = new ArrayList<>();

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_USER_PODS + " WHERE " + up_USER_ID + " = " + userId;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    PodTransaction user = new PodTransaction();
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

    public void updatePodCount(int id, int podCount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(u_POD_COUNT, podCount);
        db.update(TABLE_USERS, cv, u_ID + "=" + id, null);
    }

    public void deleteUserRow(int id) {
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

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
