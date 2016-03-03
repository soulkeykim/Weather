package com.soulkey.android.weather.data;

import com.lacronicus.easydatastorelib.Preference;
import com.lacronicus.easydatastorelib.StringEntry;

public interface AppSettings {

    @Preference("last_city") StringEntry lastCity();
}