package com.ford.android.podtracker.userstats;

import com.ford.android.podtracker.data.PodTransaction;

import java.util.List;

/**
 * Created by mshine7 on 23/09/2016.
 */
public interface StatsContract {

    interface View {
        void showStats(List<PodTransaction> podTransactions);
    }

    interface UserActionsListener {
        void loadStats(int userId);
    }

}
