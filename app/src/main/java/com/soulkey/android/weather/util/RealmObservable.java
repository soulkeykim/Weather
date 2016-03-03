package com.soulkey.android.weather.util;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.functions.Func1;
import rx.Observable;

public final class RealmObservable {
    private RealmObservable() {
    }

    public static <T extends RealmObject> Observable<T> object(Context context, final Func1<Realm, T> function) {
        return Observable.create(new OnSubscribeRealm<T>(context) {
            @Override
            public T get(Realm realm) {
                return function.call(realm);
            }
        });
    }
}
