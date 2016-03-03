package com.soulkey.android.weather;

import android.app.Activity;

public class WTLifecycleCallbacks extends SimpleAppLifecycleCallbacks {

    int visibleActivities;

    @Override
    public void onActivityResumed(Activity activity) {
        visibleActivities++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        visibleActivities = Math.max(0, visibleActivities - 1);
    }

    public boolean isAppInForeground() {
        return visibleActivities > 0;
    }
}
