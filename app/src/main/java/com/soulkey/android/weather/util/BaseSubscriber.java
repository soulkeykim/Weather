package com.soulkey.android.weather.util;

import com.soulkey.android.weather.mvp.view.MvpView;

import java.io.IOException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import timber.log.Timber;


import static com.soulkey.android.weather.mvp.view.MvpView.ErrorType.GENERAL;
import static com.soulkey.android.weather.mvp.view.MvpView.ErrorType.NETWORK;
import static com.soulkey.android.weather.mvp.view.MvpView.ErrorType.NO_NETWORK;

public class BaseSubscriber<T> extends Subscriber<T> {

    protected MvpView mMvpView;
    protected int mRequestCode;

    public BaseSubscriber(MvpView mvpView, int requestCode) {
        mMvpView = mvpView;
        mRequestCode = requestCode;
    }

    @Override
    public void onCompleted() {
        // No-op. For subclasses to override
    }

    @Override
    public final void onError(Throwable e) {
        Timber.e(e.getMessage(), e);
        final MvpView.ErrorType type;
        if (e instanceof SocketTimeoutException) {
            type = NETWORK;
        } else if (e instanceof IOException) {
            type = NO_NETWORK;
        } else {
            type = GENERAL;
        }
        onError(e, type);
    }

    @Override
    public void onNext(T t) {
        // No-op. For subclasses to override
    }

    public void onError(Throwable e, MvpView.ErrorType type) {
        // Default implementation, non final for subclasses to override
        mMvpView.showError(type, mRequestCode);
    }

}