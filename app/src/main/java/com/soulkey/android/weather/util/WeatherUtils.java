package com.soulkey.android.weather.util;

import android.support.annotation.VisibleForTesting;

import com.soulkey.android.weather.BuildConfig;

public class WeatherUtils {
    @VisibleForTesting static final String CELSIUS = "metric";
    @VisibleForTesting static final String CELSIUS_SYMBOL = "ºC";
    @VisibleForTesting static final String FAHRENHEIT = "imperial";
    @VisibleForTesting static final String FAHRENHEIT_SYMBOL = "ºF";

    public static String getUnitSymbol() {
        return BuildConfig.TEMP_UNIT.equals(CELSIUS) ? CELSIUS_SYMBOL : FAHRENHEIT_SYMBOL;
    }
}
