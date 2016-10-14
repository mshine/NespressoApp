package com.ford.android.podtracker.addpod;

import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.PodType;
import com.ford.android.podtracker.data.User;

/**
 * Created by MSHINE7 on 26/09/2016.
 */

public interface AddPodContract {
    
    interface View {
    }
    
    interface UserActionsListener {
        
        void addPod(int userId, int podCount);

        void addToTotal(int userId, double amount);

        void insertUserPods(PodTransaction podTransaction);

        PodType getPodType(String podName);

        User loadUser(int userId);
    }
}
