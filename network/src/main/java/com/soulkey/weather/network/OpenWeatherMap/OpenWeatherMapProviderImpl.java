package com.soulkey.weather.network.OpenWeatherMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soulkey.weather.network.NetworkDataProvider;
import com.soulkey.weather.network.OpenWeatherMap.model.WeatherModel;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class OpenWeatherMapProviderImpl extends NetworkDataProvider implements OpenWeatherMapProvider {

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ").create();
    private static final String API_KEY = "APPID";
    private static final String TEMPERATURE_UNIT = "units";
    private final ApiService mApiService;

    public OpenWeatherMapProviderImpl(String endpoint, String apikey, String tempUnit, boolean isDebug) {
        mApiService = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(createDefaultHttpClient(getInterceptor(apikey, tempUnit), isDebug))
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Override
    public Observable<WeatherModel> fetchWeather(String cityName) {
        return mApiService.fetchWeather(cityName);
    }

    @Override
    public Observable<WeatherModel> fetchWeather(double latitude, double longitude) {
        return mApiService.fetchWeather(latitude, longitude);
    }

    private Interceptor getInterceptor(final String apiKey, final String tempUnit) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder()
                        .addQueryParameter(API_KEY, apiKey)
                        .addQueryParameter(TEMPERATURE_UNIT, tempUnit)
                        .build();
                request = request.newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);
            }
        };
    }
}
