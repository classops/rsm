package com.hanter.android.rsm.adapter;

import com.hanter.android.rsm.RsmCall;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

public class CallOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private final RsmCall<T> call;

    public CallOnSubscribe(RsmCall<T> call) {
        this.call = call;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        try {
            T response = call.execute();
            subscriber.onNext(response);
            subscriber.onCompleted();
        } catch (IOException e) {
            subscriber.onError(e);
        }
    }

}
