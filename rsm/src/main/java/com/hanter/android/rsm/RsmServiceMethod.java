package com.hanter.android.rsm;

import android.support.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class RsmServiceMethod<S, D> {

    private final Rsm rsm;
    private final Method method;
    private final Annotation[] methodAnnotations;
    private final Annotation[][] parameterAnnotationsArray;
    private final Type[] parameterTypes;

    private Type responseType;
    CallAdapter<S, D> callAdapter;
    private RsmConverter<String, D> converter;

    public RsmServiceMethod(Rsm rsm, Method method) {
        this.rsm = rsm;
        this.method = method;
        this.methodAnnotations = method.getAnnotations();
        this.parameterTypes = method.getGenericParameterTypes();
        this.parameterAnnotationsArray = method.getParameterAnnotations();

        this.callAdapter = createCallAdapter();
        this.responseType = this.callAdapter.responseType();
        this.converter = createResponseConverter();
    }

    private RsmConverter<String, D> createResponseConverter() {
        Annotation[] annotations = method.getAnnotations();
        try {
            return rsm.responseBodyConverter(responseType, annotations);
        } catch (RuntimeException e) { // Wide exception range because factories are user code.
            throw methodError(e, "Unable to create converter for %s", responseType);
        }
    }

    private CallAdapter<S, D> createCallAdapter() {
        Type returnType = method.getGenericReturnType();
        if (Utils.hasUnresolvableType(returnType)) {
            throw methodError(
                    "Method return type must not include a type variable or wildcard: %s", returnType);
        }
        if (returnType == void.class) {
            throw methodError("Service methods cannot return void.");
        }
        Annotation[] annotations = method.getAnnotations();
        try {
            //noinspection unchecked
            return (CallAdapter<S, D>) rsm.callAdapter(returnType, annotations);
        } catch (RuntimeException e) { // Wide exception range because factories are user code.
            throw methodError(e, "Unable to create call adapter for %s", returnType);
        }
    }

    String toAsset(@Nullable Object... args) throws IOException {
        AssetName annotation = this.method.getAnnotation(AssetName.class);
        return AssetUtils.getFromAssets(this.rsm.getAssetManager(), annotation.value());
    }

    /** Builds a method return value from an HTTP response body. */
    D toResponse(String body) throws IOException {
        return this.converter.convert(body);
    }

    private RuntimeException methodError(String message, Object... args) {
        return methodError(null, message, args);
    }

    private RuntimeException methodError(Throwable cause, String message, Object... args) {
        message = String.format(message, args);
        return new IllegalArgumentException(message
                + "\n    for method "
                + method.getDeclaringClass().getSimpleName()
                + "."
                + method.getName(), cause);
    }

    private RuntimeException parameterError(
            Throwable cause, int p, String message, Object... args) {
        return methodError(cause, message + " (parameter #" + (p + 1) + ")", args);
    }

    private RuntimeException parameterError(int p, String message, Object... args) {
        return methodError(message + " (parameter #" + (p + 1) + ")", args);
    }

}
