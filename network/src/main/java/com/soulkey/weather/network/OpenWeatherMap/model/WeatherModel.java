package com.soulkey.weather.network.OpenWeatherMap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {

    @SerializedName("id") private int mId;
    @SerializedName("name") private String mCityName;
    @SerializedName("sys") private Sys mSys;
    @SerializedName("main") private Main mMain;
    @SerializedName("weather") private List<Weather> mWeathers;

    public int getId() {
        return mId;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getMain() {
        return mWeathers != null && mWeathers.size() > 0 ? mWeathers.get(0).mMain : "";
    }

    public String getDescription() {
        return mWeathers != null && mWeathers.size() > 0 ? mWeathers.get(0).mDescription : "";
    }

    public String getIcon() {
        return mWeathers != null && mWeathers.size() > 0 ? mWeathers.get(0).mIcon : "";
    }

    public double getTemp() {
        return mMain.mTemp;
    }

    public int getHumidity() {
        return mMain.mHumidity;
    }

    public String getCountry() {
        return mSys != null ? mSys.mCountry : "";
    }

    public long getSunRise() {
        return mSys.mSunRise;
    }

    public long getSunSet() {
        return mSys.mSunSet;
    }

    protected class Weather {
        @SerializedName("main") private String mMain;
        @SerializedName("description") private String mDescription;
        @SerializedName("icon") private String mIcon;
    }

    protected class Main {
        @SerializedName("temp") private double mTemp;
        @SerializedName("humidity") private int mHumidity;
    }

    protected class Sys {
        @SerializedName("country") private String mCountry;
        @SerializedName("sunrise") private long mSunRise;
        @SerializedName("sunset") private long mSunSet;
    }
}
