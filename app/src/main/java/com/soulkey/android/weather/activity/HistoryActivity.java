package com.soulkey.android.weather.activity;

import android.content.Context;
import android.os.Bundle;

import com.soulkey.android.weather.R;
import com.soulkey.android.weather.adapter.HistoryRecyclerViewAdapter;
import com.soulkey.android.weather.model.HistoryModel;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.presenter.HistoryPresenter;
import com.soulkey.android.weather.mvp.view.HistoryMvpView;

import butterknife.Bind;
import butterknife.OnClick;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import timber.log.Timber;

public class HistoryActivity extends PresentableActivity<HistoryPresenter> implements HistoryMvpView, HistoryListener {

    @Bind(R.id.history_rrv) RealmRecyclerView mHistoryRrv;

    private Realm mRealm;
    private HistoryRecyclerViewAdapter mHistoryReclerViewAdapter;

    @Override
    protected HistoryPresenter createPresenter(AppServicesComponent component) {
        return new HistoryPresenter(this, component);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.act_history;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRealmRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
        mRealm = null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showHomeScreen() {
        setResult(HistoryPresenter.HISTORY_SELECTED_REQUEST_CODE);
        finish();
    }

    @Override
    public void showHistoryDeleted() {
        Timber.d("showHistoryDeleted");
    }

    @Override
    public void showError(ErrorType errorType, int requestCode) {
        mDialogManager.runDefaultErrorHandling(getContext(), errorType, null);
    }

    @OnClick(R.id.delete_all_btn)
    public void onDeleteAllClicked() {
        getPresenter().onDeleteAllClicked();
    }

    private void setRealmRecyclerView() {
        mRealm = Realm.getInstance(this);
        RealmResults<HistoryModel> historyModels = mRealm.where(HistoryModel.class).findAllSorted("lastDate", Sort.DESCENDING);
        mHistoryReclerViewAdapter = new HistoryRecyclerViewAdapter(getBaseContext(), historyModels, true, true, "name", this);
        mHistoryRrv.setAdapter(mHistoryReclerViewAdapter);
    }

    @Override
    public void selectHistory(String cityName) {
        getPresenter().selectHistory(cityName);
    }

    @Override
    public void deleteHistory(String cityName) {
        getPresenter().deleteHistory(cityName);
    }
}
