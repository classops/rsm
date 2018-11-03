package com.hanter.android.rsm;

import java.io.IOException;
import java.lang.reflect.Type;

public class ObjectCallAdapter<S> implements CallAdapter<S,Object> {

    private final Type responseType;

    public ObjectCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Object adapter(RsmCall<S> call) {
        try {
            return call.execute();
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    public Type responseType() {
        return responseType;
    }


}
