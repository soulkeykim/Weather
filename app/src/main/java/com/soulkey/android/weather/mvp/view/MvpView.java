package com.soulkey.android.weather.mvp.view;

import android.content.Context;

/**
 *
 */
public interface MvpView {

    enum ErrorType {
        GENERAL,
        NETWORK,
        NO_NETWORK
    }

    Context getContext();

    void showError(ErrorType errorType, int requestCode);
}
