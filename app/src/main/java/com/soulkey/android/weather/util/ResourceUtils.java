package com.soulkey.android.weather.util;

import android.content.Context;

public class ResourceUtils {
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/ic_" + imageName, null, context.getPackageName());
    }
}
