package com.soulkey.android.weather.data;

import android.support.annotation.NonNull;

import com.soulkey.android.weather.BuildConfig;
import com.soulkey.android.weather.model.HistoryModel;
import com.soulkey.weather.model.Weather;
import com.soulkey.weather.network.OpenWeatherMap.OpenWeatherMapProvider;
import com.soulkey.weather.network.OpenWeatherMap.model.WeatherModel;

import rx.Observable;
import rx.functions.Func1;

public class DataProvider {
    private final OpenWeatherMapProvider mOpenWeatherMapProvider;
    private final RealmDataProvider mRealmDataProvider;
    private final AppSettings mAppSettings;

    public DataProvider(OpenWeatherMapProvider openWeatherMapProvider, RealmDataProvider realmDataProvider, AppSettings appSettings) {
        mOpenWeatherMapProvider = openWeatherMapProvider;
        mRealmDataProvider = realmDataProvider;
        mAppSettings = appSettings;
    }

    public Observable<Weather> fetchWeather(@NonNull String cityName) {
        return mOpenWeatherMapProvider.fetchWeather(cityName)
                .flatMap(new Func1<WeatherModel, Observable<Weather>>() {
                    @Override
                    public Observable<Weather> call(WeatherModel weatherModel) {
                        return Observable.just(buildWeather(weatherModel));
                    }
                });
    }


    public Observable<Weather> fetchWeather(double latitude, double longitude) {
        return mOpenWeatherMapProvider.fetchWeather(latitude,longitude)
                .flatMap(new Func1<WeatherModel, Observable<Weather>>() {
                    @Override
                    public Observable<Weather> call(WeatherModel weatherModel) {
                        return Observable.just(buildWeather(weatherModel));
                    }
                });

    }

    private Weather buildWeather(WeatherModel weatherModel) {
        return Weather.newBuilder()
                .id(weatherModel.getId())
                .cityName(weatherModel.getCityName())
                .country(weatherModel.getCountry())
                .main(weatherModel.getMain())
                .description(weatherModel.getDescription())
                .icon(weatherModel.getIcon())
                .temp(weatherModel.getTemp())
                .humidity(weatherModel.getHumidity())
                .sunrise(weatherModel.getSunRise())
                .sunset(weatherModel.getSunSet())
                .build();
    }

    public Observable<HistoryModel> addHistory(Weather weather) {
        return mRealmDataProvider.addHistory(weather);
    }

    public Observable<HistoryModel> deleteHistory(String cityName) {
        return mRealmDataProvider.deleteHistory(cityName);
    }

    public String getLastCity() {
        return mAppSettings.lastCity().get(BuildConfig.DEFAULT_CITY);
    }

    public void setLastCity(@NonNull String city) {
        mAppSettings.lastCity().put(city);
    }

}
