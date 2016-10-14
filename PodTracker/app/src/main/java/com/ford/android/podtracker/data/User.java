package com.ford.android.podtracker.data;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by mshine7 on 02/09/2016.
 */
public class User implements Serializable {
    private int id;
    private String name;
    private int podCount;
    private double totalOwed;

    public User() {

    }

    public User(String name) {
        this.name = name;
        this.podCount = 0;
        this.totalOwed = 0.00;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPodCount() {
        return podCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPodCount(int podCount) {
        this.podCount = podCount;
    }

    public boolean isEmpty() {
        return (name == null || "".equals(name));
    }

    @SuppressLint("NewApi")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name);
    }

    @SuppressLint("NewApi")
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public double getTotalOwed() {
        return totalOwed;
    }

    public void setTotalOwed(double totalOwed) {
        this.totalOwed = totalOwed;
    }
}
