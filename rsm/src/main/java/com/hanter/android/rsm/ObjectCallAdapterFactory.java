package com.hanter.android.rsm;

import android.support.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import rx.Observable;

public class ObjectCallAdapterFactory extends CallAdapter.Factory {

    public static ObjectCallAdapterFactory create() {
        return new ObjectCallAdapterFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Rsm rsm) {
        Class<?> rawType = getRawType(returnType);
        if (rawType == Observable.class) {
            return null;
        } else {
            return new ObjectCallAdapter<>(returnType);
        }
    }

}
