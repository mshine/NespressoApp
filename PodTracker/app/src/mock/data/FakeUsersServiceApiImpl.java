package data;

import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.PodType;
import com.ford.android.podtracker.data.User;
import com.ford.android.podtracker.data.UsersServiceApi;

import java.util.List;

/**
 * Created by MSHINE7 on 29/09/2016.
 */

public class FakeUsersServiceApiImpl implements UsersServiceApi {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<PodTransaction> getUserStats(int userId) {
        return null;
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public PodType getPodType(String podName) {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void incrementPodCount(int userId, int podCount) {

    }

    @Override
    public void incrementTotalOwed(int userId, double totalOwed) {

    }

    @Override
    public void insertPods(PodTransaction podTransaction) {

    }

    @Override
    public void deleteUser(User user) {

    }
}
