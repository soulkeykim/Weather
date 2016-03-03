package com.soulkey.android.weather.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HistoryModel extends RealmObject {
    @PrimaryKey
    private String name;
    private String country;
    private double lon;
    private double lat;
    private long lastDate;

    public HistoryModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public long getLastDate() {
        return lastDate;
    }

    public void setLastDate(long lastDate) {
        this.lastDate = lastDate;
    }
}
