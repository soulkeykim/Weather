package com.soulkey.android.weather.util;

import android.view.View;

/**
 * Common utilities and helper methods to perform on {@link View} objects
 */
public class ViewUtils {

    ViewUtils() {
        // No instances
    }

    /**
     * Set the visibility of all the given views to {@link View#GONE}
     *
     * @param views The views to hide
     */
    public static void hide(View... views) {
        setVisibility(View.GONE, views);
    }

    /**
     * Set the visibility of all the given views to {@link View#INVISIBLE}
     *
     * @param views The views to
     */
    public static void hideInvisible(View... views) {
        setVisibility(View.INVISIBLE, views);
    }

    /**
     * Set the visibility of all the given views to {@link View#VISIBLE}
     *
     * @param views The views to show
     */
    public static void show(View... views) {
        setVisibility(View.VISIBLE, views);
    }

    /**
     * Set the visibility of all given views
     *
     * @param visibility The visibility of the view. Eg. {@link View#VISIBLE}
     * @param views      The views to set the visibility of
     */
    private static void setVisibility(int visibility, View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(visibility);
                }
            }
        }
    }
}