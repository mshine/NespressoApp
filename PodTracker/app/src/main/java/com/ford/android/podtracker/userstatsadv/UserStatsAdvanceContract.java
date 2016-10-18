package com.ford.android.podtracker.userstatsadv;

import com.ford.android.podtracker.data.PodTransaction;

import java.util.List;

/**
 * Created by MSHINE7 on 29/09/2016.
 */

public interface UserStatsAdvanceContract {

    interface View {
        void showStats(List<PodTransaction> podTransactions);
    }

    interface UserActionsListener {
        void loadStats(int userId);

    }
}
