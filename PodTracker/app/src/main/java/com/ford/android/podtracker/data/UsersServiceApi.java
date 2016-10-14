package com.ford.android.podtracker.data;

import java.util.List;

/**
 * Created by mshine7 on 22/09/2016.
 */
public interface UsersServiceApi {

    List<User> getAllUsers();

    List<PodTransaction> getUserStats(int userId);

    User getUser(int userId);

    PodType getPodType(String podName);

    void saveUser(User user);

    void incrementPodCount(int userId, int podCount);

    void incrementTotalOwed(int userId, double totalOwed);

    void insertPods(PodTransaction podTransaction);

    void deleteUser(User user);
}


