package com.ford.android.podtracker.userprofile;

import com.ford.android.podtracker.data.PodTransaction;

import java.util.List;

/**
 * Created by MSHINE7 on 29/09/2016.
 */

public interface UserProfileContract {

    interface View {
        void showStats(List<PodTransaction> podTransactions);

        void showDetails(int userId);
    }

    interface UserActionsListener {
        void loadStats(int userId);

        void loadDetails(int userId);
    }
}
