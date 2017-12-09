package com.example.pastpresentfuture;

/**
 * Created by s3622567 on 1/12/2017.
 */

// The structure of data to be stored in database

public class EventStore {
    String title;
    String date;
    String memo;
    String location;

    public EventStore() {}

    public EventStore(String title, String date, String memo, String location) {
        this.title = title;
        this.date = date;
        this.memo = memo;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
