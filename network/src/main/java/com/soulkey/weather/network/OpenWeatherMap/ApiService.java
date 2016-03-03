package com.soulkey.weather.network.OpenWeatherMap;

import com.soulkey.weather.network.OpenWeatherMap.model.WeatherModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    String API_VERSION = "2.5/";
    String PREFIX_API = "data/" + API_VERSION;

    @GET(PREFIX_API + "weather")
    Observable<WeatherModel> fetchWeather(@Query("q") String cityName);

    @GET(PREFIX_API + "weather")
    Observable<WeatherModel> fetchWeather(@Query("lat") double latitude, @Query("lon") double longitude);
}
