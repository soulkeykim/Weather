package com.soulkey.android.weather.util;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

public class CrashlyticsLogger extends Timber.Tree {

    public CrashlyticsLogger() {
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (t != null) {
            Crashlytics.logException(t);
        } else {
            Crashlytics.log(message);
        }
    }
}