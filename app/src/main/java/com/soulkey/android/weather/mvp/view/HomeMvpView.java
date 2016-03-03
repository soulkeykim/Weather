package com.soulkey.android.weather.mvp.view;

import com.soulkey.weather.model.Weather;

/**
 *
 */
public interface HomeMvpView extends MvpView {

    void showWeatherInfo(Weather weather);

    void showLoading();

    void hideLoading();

    void showFailureMessage();

    void showHistoryAdded();
}
