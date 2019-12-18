package com.hanter.android.rsm.adapter;

import android.support.annotation.Nullable;

import com.hanter.android.rsm.CallAdapter;
import com.hanter.android.rsm.Rsm;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Observable;

public class RxJavaCallAdapterFactory extends CallAdapter.Factory {

    public static RxJavaCallAdapterFactory create() {
        return new RxJavaCallAdapterFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Rsm rsm) {
        Class<?> rawType = CallAdapter.Factory.getRawType(returnType);
        if (rawType != Observable.class) {
            return null;
        }
        Type responseType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        return new RxjavaCallAdapter(responseType, null);
    }



}
