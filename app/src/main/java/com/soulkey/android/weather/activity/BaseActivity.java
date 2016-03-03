package com.soulkey.android.weather.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.soulkey.android.weather.WTApp;
import com.soulkey.android.weather.manager.DialogManager;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxActivity {

    @Inject protected DialogManager mDialogManager;

    protected WTApp mApp;

    /**
     * @return the layout resource to use for this activity,
     * or a value <= 0 if no layout should be used
     */
    @LayoutRes protected abstract int getLayoutResource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (WTApp) getApplicationContext();
        mApp.getAppServicesComponent().inject(this);

        final int layoutResId = getLayoutResource();
        if (layoutResId > 0) {
            setContentView(layoutResId);
        }
        ButterKnife.bind(this);
    }
}
