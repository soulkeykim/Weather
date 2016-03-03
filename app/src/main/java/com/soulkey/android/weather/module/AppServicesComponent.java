package com.soulkey.android.weather.module;

import com.soulkey.android.weather.activity.BaseActivity;
import com.soulkey.android.weather.mvp.presenter.HistoryPresenter;
import com.soulkey.android.weather.mvp.presenter.HomePresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component to provide dependency injection
 */
@Singleton
@Component(modules = WTModule.class)
public interface AppServicesComponent {
	void inject(BaseActivity activity);

	void inject(HomePresenter presenter);
	void inject(HistoryPresenter presenter);
}
