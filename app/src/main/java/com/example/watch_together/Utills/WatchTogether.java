package com.example.watch_together.Utills;

import android.app.Application;

public class WatchTogether extends Application {
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
