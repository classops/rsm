package com.hanter.android.rsm;

import android.support.annotation.Nullable;
import java.lang.reflect.Type;
import rx.Observable;
import rx.Scheduler;

public class RxjavaCallAdapter<S> implements CallAdapter<S, Object> {

    private final Type responseType;
    @Nullable
    private final Scheduler scheduler;

    public RxjavaCallAdapter(Type responseType) {
        this(responseType, null);
    }

    public RxjavaCallAdapter(Type responseType, @Nullable Scheduler scheduler) {
        this.responseType = responseType;
        this.scheduler = scheduler;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapter(RsmCall<S> call) {
        return Observable.create(new CallOnSubscribe<>(call));
    }
}
