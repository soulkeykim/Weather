package com.soulkey.android.weather.mvp.presenter;

import android.content.Context;

import com.soulkey.android.weather.mvp.view.MvpView;

import org.robolectric.RuntimeEnvironment;

import java.util.concurrent.CountDownLatch;

public abstract class MockMvpView implements MvpView {

    protected CountDownLatch mCountDownLatch = new CountDownLatch(1);

    @Override
    public Context getContext() {
        return RuntimeEnvironment.application;
    }

    @Override
    public final void showError(ErrorType errorType, int requestCode) {
        showErrorImpl(errorType, requestCode);
        mCountDownLatch.countDown();
    }

    protected void showErrorImpl(ErrorType errorType, int requestCode) {
    }
}
