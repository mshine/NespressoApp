package com.ford.android.podtracker;

import java.util.Date;

/**
 * Created by matthewshine on 06/09/2016.
 */
public class UserPods {

    private int id;
    private int userId;
    private Date transactionDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
