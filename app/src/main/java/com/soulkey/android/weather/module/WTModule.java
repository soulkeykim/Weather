package com.soulkey.android.weather.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lacronicus.easydatastorelib.DatastoreBuilder;
import com.soulkey.android.weather.BuildConfig;
import com.soulkey.android.weather.WTApp;
import com.soulkey.android.weather.data.AppSettings;
import com.soulkey.android.weather.data.DataProvider;
import com.soulkey.android.weather.data.RealmDataProvider;
import com.soulkey.android.weather.manager.DialogManager;
import com.soulkey.android.weather.manager.LocationManager;
import com.soulkey.weather.network.OpenWeatherMap.OpenWeatherMapProvider;
import com.soulkey.weather.network.OpenWeatherMap.OpenWeatherMapProviderImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module to provide dependency injection
 */
@SuppressWarnings("unused")
@Module
public class WTModule {
    private WTApp application;

    public WTModule(WTApp app) {
        application = app;
    }

    @Provides
    @Singleton
    WTApp providesApp() {
        return application;
    }

    @Provides
    Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    AppSettings providesAppSettings(SharedPreferences prefs) {
        return new DatastoreBuilder(prefs).create(AppSettings.class);
    }

    @Provides
    @Singleton
    OpenWeatherMapProvider providesOpenWeatherMapProvider() {
        return new OpenWeatherMapProviderImpl(BuildConfig.API_SERVER, BuildConfig.API_KEY, BuildConfig.TEMP_UNIT, BuildConfig.DEBUG);
    }

    @Provides
    @Singleton
    RealmDataProvider providesRealmDataProvider(Context context) {
        return new RealmDataProvider(context);
    }

    @Provides
    @Singleton
    DataProvider providesDataProvider(OpenWeatherMapProvider openWeatherMapProvider, RealmDataProvider realmDataProvider, AppSettings appSettings) {
        return new DataProvider(openWeatherMapProvider, realmDataProvider, appSettings);
    }

    @Provides
    @Singleton
    DialogManager providesDialogManager() {
        return new DialogManager();
    }

    @Singleton
    @Provides
    LocationManager providesLocationManager(Context context) {
        return new LocationManager(context);
    }
}
