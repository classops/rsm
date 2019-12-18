package com.hanter.android.rsm.adapter;

import com.hanter.android.rsm.RsmCall;

import java.io.IOException;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class CallObservableOnSubscribe<T> implements ObservableOnSubscribe<T> {

    private final RsmCall<T> call;

    public CallObservableOnSubscribe(RsmCall<T> call) {
        this.call = call;
    }

    @Override
    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
        try {
            T response = call.execute();
            emitter.onNext(response);
            emitter.onComplete();
        } catch (IOException e) {
            emitter.onError(e);
        }
    }
}
