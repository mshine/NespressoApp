package com.ford.android.podtracker.data;

import com.annimon.stream.Stream;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by matthewshine on 06/09/2016.
 */
public class PodTransaction implements Serializable {

    private int id;
    private int userId;
    private int userPodTypeId;
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

    public static int getNoOfPodsForLastMonth(Stream<Date> transactionDates) {
        Stream dateStream = transactionDates.filter(t -> t.getMonth() == Calendar.getInstance().get(Calendar.MONTH) - 1);
        return (int) dateStream.count();
    }

    public static int getNoOfPodsForCurrentMonth(Stream<Date> transactionDates) {
        //noinspection WrongConstant - I DONT KNOW WHAT'S WRONG WITH YOU!!
        Stream dateStream = transactionDates.filter(t -> t.getMonth() == Calendar.getInstance().get(Calendar.MONTH));
        return (int) dateStream.count();
    }

    public int getUserPodTypeId() {
        return userPodTypeId;
    }

    public void setUserPodTypeId(int userPodTypeId) {
        this.userPodTypeId = userPodTypeId;
    }
}