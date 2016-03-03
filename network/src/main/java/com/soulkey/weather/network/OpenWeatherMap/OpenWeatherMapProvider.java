package com.soulkey.weather.network.OpenWeatherMap;

import com.soulkey.weather.network.OpenWeatherMap.model.WeatherModel;

import rx.Observable;

public interface OpenWeatherMapProvider {
    Observable<WeatherModel> fetchWeather(String cityName);

    Observable<WeatherModel> fetchWeather(double latitude, double longitude);
}
