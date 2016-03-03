package com.soulkey.weather.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

abstract public class NetworkDataProvider {

    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(10);

    protected OkHttpClient createDefaultHttpClient(Interceptor interceptor, boolean isDebug) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        if (isDebug) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.interceptors().add(httpLoggingInterceptor);
        }

        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }
}
