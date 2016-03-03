package com.soulkey.android.weather.util;

import com.soulkey.android.weather.BuildConfig;
import com.soulkey.android.weather.WTTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WTTestRunner.class)
public class WeatherUtilsTest {

    @Test
    public void testConstructor() {
        // For code coverage..
        new WeatherUtils();
    }

    @Test
    public void testGetUnitSymbol() {
        assertThat(WeatherUtils.getUnitSymbol()).isEqualTo(BuildConfig.TEMP_UNIT.equals(WeatherUtils.CELSIUS) ? WeatherUtils.CELSIUS_SYMBOL : WeatherUtils.FAHRENHEIT_SYMBOL);
    }
}
