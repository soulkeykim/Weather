package com.soulkey.android.weather.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.soulkey.android.weather.activity.BaseActivity;
import com.soulkey.android.weather.fragment.BaseFragment;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.view.MvpView;
import com.soulkey.android.weather.util.RxUtils;

import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Base class for all presenters (In the Model-View-Presenter architecture) within the application
 */
public abstract class Presenter<V extends MvpView> {

    private static final AtomicInteger NEXT_ID = new AtomicInteger();
    private final V mView;

    public Presenter(@NonNull V view, AppServicesComponent component) {
        if (view == null) {
            throw new IllegalArgumentException("view != null");
        }  else if (component == null) {
            throw new IllegalArgumentException("component != null");
        }

        mView = view;
        inject(component);
    }

    protected abstract void inject(@NonNull AppServicesComponent component);

    public static int nextId() {
        return NEXT_ID.incrementAndGet();
    }

    protected V getView() {
        return mView;
    }

    protected Context getContext() {
        return mView.getContext();
    }

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    public void onDestroy() {

    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    protected <R> Subscription bind(Observable<R> observable, Observer<? super R> observer) {
        final Observable<R> boundObservable;
        if (mView instanceof BaseFragment) {
            boundObservable = RxUtils.bindFragment((BaseFragment) mView, observable);
        } else if (getContext() instanceof BaseActivity) {
            boundObservable = RxUtils.bindActivity((BaseActivity) getContext(), observable);
        } else {
            boundObservable = observable;
        }

        return boundObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
