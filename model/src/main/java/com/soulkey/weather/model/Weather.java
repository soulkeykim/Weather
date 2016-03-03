package com.soulkey.weather.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Weather {
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");

    private int mId;
    private String mCityName;
    private String mCountry;
    private String mMain;
    private String mDescription;
    private String mIcon;
    private double mTemp;
    private int mHumidity;
    private long mSunRise;
    private long mSunSet;

    private Weather(Builder builder) {
        mId = builder.mId;
        mCityName = builder.mCityName;
        mCountry = builder.mCountry;
        mMain = builder.mMain;
        mDescription = builder.mDescription;
        mIcon = builder.mIcon;
        mTemp = builder.mTemp;
        mHumidity = builder.mHumidity;
        mSunRise = builder.mSunRise;
        mSunSet = builder.mSunSet;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private static Builder newBuilder(Weather copy) {
        final Builder builder = new Builder();
        builder.mId = copy.mId;
        builder.mCityName = copy.mCityName;
        builder.mCountry = copy.mCountry;
        builder.mMain = copy.mMain;
        builder.mDescription = copy.mDescription;
        builder.mIcon = copy.mIcon;
        builder.mTemp = copy.mTemp;
        builder.mHumidity = copy.mHumidity;
        builder.mSunRise = copy.mSunRise;
        builder.mSunSet = copy.mSunSet;
        return builder;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getMain() {
        return mMain;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getTemp() {
        return String.format("%.2f", mTemp);
    }

    public String getHumidity() {
        return String.format("%d%%", mHumidity);
    }

    public String getSunRise() {
        return TIME_FORMAT.format(new Date(TimeUnit.SECONDS.toMillis(mSunRise)));
    }

    public String getSunSet() {
        return TIME_FORMAT.format(new Date(TimeUnit.SECONDS.toMillis(mSunSet)));
    }

    public static final class Builder {
        private int mId;
        private String mCityName;
        private String mCountry;
        private String mMain;
        private String mDescription;
        private String mIcon;
        private double mTemp;
        private int mHumidity;
        private long mSunRise;
        private long mSunSet;

        private Builder() {
        }

        public Builder id(int val) {
            mId = val;
            return this;
        }

        public Builder cityName(String val) {
            mCityName = val;
            return this;
        }

        public Builder country(String val) {
            mCountry = val;
            return this;
        }

        public Builder main(String val) {
            mMain = val;
            return this;
        }

        public Builder description(String val) {
            mDescription = val;
            return this;
        }

        public Builder icon(String val) {
            mIcon = val;
            return this;
        }

        public Builder temp(double val) {
            mTemp = val;
            return this;
        }

        public Builder humidity(int val) {
            mHumidity = val;
            return this;
        }

        public Builder sunrise(long val) {
            mSunRise = val;
            return this;
        }

        public Builder sunset(long val) {
            mSunSet = val;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }

}
