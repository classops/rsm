package com.hanter.android.rsm;

import android.support.annotation.Nullable;
import java.io.IOException;

public class AssetCall<T> implements RsmCall<T> {

    private final RsmServiceMethod<?, T> serviceMethod;
    private final @Nullable
    Object[] args;

    AssetCall(RsmServiceMethod<?, T> serviceMethod, @Nullable Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    @Override
    public T execute() throws IOException {
        String json = serviceMethod.toAsset(this.args);
        return serviceMethod.toResponse(json);
    }

}
