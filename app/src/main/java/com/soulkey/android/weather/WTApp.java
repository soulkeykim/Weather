package com.soulkey.android.weather;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.module.DaggerAppServicesComponent;
import com.soulkey.android.weather.module.WTModule;
import com.soulkey.android.weather.util.CrashlyticsLogger;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 *
 */
public class WTApp extends Application {

    private AppServicesComponent mAppServicesComponent;

    public static WTApp from(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        return (WTApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            enableDebugTools();
        }

        enableAppOnlyFunctionality();

        mAppServicesComponent = DaggerAppServicesComponent.builder()
                .wTModule(getModule())
                .build();
    }

    WTModule getModule() {
        return new WTModule(this);
    }

    public AppServicesComponent getAppServicesComponent() {
        return mAppServicesComponent;
    }

    void enableDebugTools() {
        Timber.plant(new Timber.DebugTree());
        StrictMode.enableDefaults();
    }

    void enableAppOnlyFunctionality() {
        Fabric.with(this, new Crashlytics(), new Answers());
        Timber.plant(new CrashlyticsLogger());
    }


}
