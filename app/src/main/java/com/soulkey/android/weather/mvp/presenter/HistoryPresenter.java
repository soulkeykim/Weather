package com.soulkey.android.weather.mvp.presenter;

import android.support.annotation.NonNull;

import com.soulkey.android.weather.data.DataProvider;
import com.soulkey.android.weather.model.HistoryModel;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.view.HistoryMvpView;
import com.soulkey.android.weather.util.BaseSubscriber;

import javax.inject.Inject;

import io.realm.Realm;

public class HistoryPresenter extends Presenter<HistoryMvpView> {

    public static final int HISTORY_SELECTED_REQUEST_CODE = nextId();
    public static final int DELETE_HISTORY_REQUEST_CODE = nextId();

    @Inject DataProvider mDataProvider;

    public HistoryPresenter(@NonNull HistoryMvpView view, AppServicesComponent component) {
        super(view, component);
    }

    @Override
    protected void inject(@NonNull AppServicesComponent component) {
        component.inject(this);
    }

    public void onDeleteAllClicked() {
        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        realm.where(HistoryModel.class).findAll().clear();
        realm.commitTransaction();
    }

    public void selectHistory(@NonNull String cityName) {
        mDataProvider.setLastCity(cityName);
        getView().showHomeScreen();
    }

    public void deleteHistory(@NonNull String cityName) {
        bind(mDataProvider.deleteHistory(cityName), new BaseSubscriber<HistoryModel>(getView(), DELETE_HISTORY_REQUEST_CODE){
            @Override
            public void onNext(HistoryModel historyModel) {
                getView().showHistoryDeleted();
            }
        });
    }
}
