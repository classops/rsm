package com.hanter.android.rsm.adapter;

import android.support.annotation.Nullable;

import com.hanter.android.rsm.CallAdapter;
import com.hanter.android.rsm.Rsm;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;

public class RxJava2CallAdapterFactory extends CallAdapter.Factory {

    public static RxJava2CallAdapterFactory create() {
        return new RxJava2CallAdapterFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Rsm rsm) {
        Class<?> rawType = CallAdapter.Factory.getRawType(returnType);
        if (rawType != Observable.class) {
            return null;
        }
        Type responseType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        return new Rxjava2CallAdapter(responseType, null);
    }



}
