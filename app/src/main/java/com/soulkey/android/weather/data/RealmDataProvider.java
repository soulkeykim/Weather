package com.soulkey.android.weather.data;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.soulkey.android.weather.model.HistoryModel;
import com.soulkey.android.weather.util.RealmObservable;
import com.soulkey.weather.model.Weather;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;

public class RealmDataProvider {
    private Context mContext;

    public RealmDataProvider(Context context) {
        mContext = context;
    }

    public Observable<HistoryModel> addHistory(@NonNull final Weather weather) {
        return RealmObservable.object(mContext, new Func1<Realm, HistoryModel>() {
            @Override
            public HistoryModel call(Realm realm) {
                HistoryModel historyModel = new HistoryModel();
                historyModel.setName(weather.getCityName());
                historyModel.setLastDate(System.currentTimeMillis());
                return realm.copyToRealmOrUpdate(historyModel);
            }
        });
    }

    public Observable<HistoryModel> deleteHistory(final String cityName) {
        return RealmObservable.object(mContext, new Func1<Realm, HistoryModel>() {
            @Override
            public HistoryModel call(Realm realm) {
                HistoryModel historyModel = realm.where(HistoryModel.class).equalTo("name", cityName).findFirst();
                if (historyModel != null) {
                    realm.beginTransaction();
                    historyModel.removeFromRealm();
                    realm.commitTransaction();
                }

                return historyModel;
            }
        });
    }
}
