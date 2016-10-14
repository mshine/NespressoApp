package com.ford.android.podtracker.data;

import com.ford.android.podtracker.DbHelper;
import com.ford.android.podtracker.MyContext;

import java.util.List;

/**
 * Created by mshine7 on 22/09/2016.
 */
public class UsersServiceApiImpl implements UsersServiceApi {

    private final DbHelper dbHelper = DbHelper.getInstance(MyContext.getAppContext());

    @Override
    public List<User> getAllUsers() {
        return dbHelper.getUsersList();
    }

    @Override
    public List<PodTransaction> getUserStats(int userId) {
        return dbHelper.getUsersPodsList(userId);
    }

    @Override
    public User getUser(int userId) {
        return dbHelper.getUser(userId);
    }

    @Override
    public PodType getPodType(String podName) {
        return dbHelper.getPodType(podName);
    }

    @Override
    public void saveUser(User user) {
        dbHelper.insertUser(user);
    }

    @Override
    public void incrementPodCount(int userId, int podCount) {
        dbHelper.updatePodCount(userId, podCount);
    }

    @Override
    public void incrementTotalOwed(int userId, double totalOwed) {
        dbHelper.updateTotalOwed(userId, totalOwed);
    }

    @Override
    public void insertPods(PodTransaction podTransaction) {
        dbHelper.insertUserPods(podTransaction);
    }

    @Override
    public void deleteUser(User user) {
        dbHelper.deleteUserRow(user.getId());
    }
}
