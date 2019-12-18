package com.hanter.android.rsm.adapter;

import android.support.annotation.Nullable;

import com.hanter.android.rsm.CallAdapter;
import com.hanter.android.rsm.RsmCall;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class Rxjava2CallAdapter<S> implements CallAdapter<S, Object> {

    private final Type responseType;
    @Nullable
    private final Scheduler scheduler;

    public Rxjava2CallAdapter(Type responseType) {
        this(responseType, null);
    }

    public Rxjava2CallAdapter(Type responseType, @Nullable Scheduler scheduler) {
        this.responseType = responseType;
        this.scheduler = scheduler;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapter(RsmCall<S> call) {
        return Observable.create(new CallObservableOnSubscribe<>(call));
    }
}
