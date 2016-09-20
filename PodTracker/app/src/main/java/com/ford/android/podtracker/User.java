package com.ford.android.podtracker;

import java.io.Serializable;

/**
 * Created by mshine7 on 02/09/2016.
 */
public class User implements Serializable {
    private int id;
    private String name;
    private int podCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPodCount() {
        return podCount;
    }

    public void setPodCount(int podCount) {
        this.podCount = podCount;
    }

    public void incrementPodCount() {
    }

    public void decrementPodCount() {
    }
}
