package com.soulkey.android.weather.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.soulkey.android.weather.data.DataProvider;
import com.soulkey.android.weather.manager.LocationManager;
import com.soulkey.android.weather.model.HistoryModel;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.view.HomeMvpView;
import com.soulkey.android.weather.util.BaseSubscriber;
import com.soulkey.weather.model.Weather;

import javax.inject.Inject;

import timber.log.Timber;

public class HomePresenter extends Presenter<HomeMvpView> {

    public static final int FETCH_WEATHER_REQUEST_CODE = nextId();
    public static final int ADD_HISTORY_REQUST_CODE = nextId();
    public static final int HISTORY_REQUEST_CODE = nextId();

    @Inject DataProvider mDataProvider;
    @Inject LocationManager mLocationManager;

    public HomePresenter(@NonNull HomeMvpView view, AppServicesComponent component) {
        super(view, component);
        fetchWeather();
    }

    @Override
    protected void inject(@NonNull AppServicesComponent component) {
        component.inject(this);
    }

    public void fetchWeather() {
        fetchWeather(mDataProvider.getLastCity());
    }

    public void fetchWeather(@NonNull Place place) {
        getView().showLoading();
        fetchWeather(place.getAddress().toString());
    }

    public void fetchWeather(@NonNull final String cityName) {
        mDataProvider.setLastCity(cityName);
        bind(mDataProvider.fetchWeather(cityName), new BaseSubscriber<Weather>(getView(), FETCH_WEATHER_REQUEST_CODE) {
            @Override
            public void onNext(Weather weather) {
                completeFetchWeather(weather);
            }
        });
    }

    public void fetchWeather(double latitude, double longitude) {
        bind(mDataProvider.fetchWeather(latitude, longitude), new BaseSubscriber<Weather>(getView(), FETCH_WEATHER_REQUEST_CODE) {
            @Override
            public void onNext(Weather weather) {
                mDataProvider.setLastCity(weather.getCityName());
                completeFetchWeather(weather);
            }
        });
    }

    public void onGpsClicked(@Nullable Location location) {
        getView().showLoading();
        if (location != null) {
            fetchWeather(location.getLatitude(), location.getLongitude());
        } else {
            if (!mLocationManager.isGpsAvailable()) {
                requestGpsPermission();
            } else {
                getView().showFailureMessage();
            }
        }
    }

    public void requestGpsPermission() {
        mLocationManager.requestLocationSetting(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult((Activity) getContext(), LocationManager.REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            getView().showFailureMessage();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        break;
                    default:
                        getView().showFailureMessage();
                        break;
                }
            }
        });
    }

    private void completeFetchWeather(Weather weather) {
        getView().showWeatherInfo(weather);
        addHistory(weather);
    }

    private void addHistory(Weather weather) {
        bind(mDataProvider.addHistory(weather), new BaseSubscriber<HistoryModel>(getView(), ADD_HISTORY_REQUST_CODE) {
            @Override
            public void onNext(HistoryModel historyModel) {
                getView().showHistoryAdded();
            }
        });
    }
}
