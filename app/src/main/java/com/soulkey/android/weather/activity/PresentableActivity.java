package com.soulkey.android.weather.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.presenter.Presenter;

/**
 * Base class for all activities which have a corresponding {@link Presenter} object
 *
 * @param <T> The type of mPresenter the activity uses
 */
abstract class PresentableActivity<T extends Presenter> extends BaseActivity {

    T mPresenter;

    /**
     * Return a mPresenter to use for this activity. This will only be called once per activity,
     * so there is no need to cache the results
     *
     * @param component Used for injecting dependencies
     * @return A {@link Presenter} instance to use with this activity
     */
    @NonNull
    protected abstract T createPresenter(AppServicesComponent component);

    /**
     * @return The mPresenter instance originally returned from {@link #createPresenter(AppServicesComponent)}
     */
    @NonNull
    protected T getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter(mApp.getAppServicesComponent());
        if (mPresenter == null) {
            throw new IllegalStateException("mPresenter == null");
        }

        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
        super.onDestroy();
    }
}