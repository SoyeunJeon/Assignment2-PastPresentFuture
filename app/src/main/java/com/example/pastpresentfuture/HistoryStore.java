package com.example.pastpresentfuture;

/**
 * Created by s3622567 on 5/12/2017.
 */

// The structure of data to be stored in database

public class HistoryStore {

    String address;
    String title;
    double latitude;
    double longitude;

    public HistoryStore() { }

    public HistoryStore(String address, String title, double latitude, double longitude) {
        this.address = address;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
