package com.hanter.android.rsm;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名：Rsm <br/>
 * 描述：Rsm(Response Simulator) 从Assets资源中资源中模拟HTTP响应数据
 * ServiceMethod类，提供 callAdapter 和 converter 进行数据转换
 * Adapter方法，逐步匹配适应的 AdapterFactory
 * 创建时间：2018/10/30 22:24
 *
 * @author hanter
 * @version 1.0
 */
public class Rsm {

    private static Rsm sRsm;

    private final AssetManager assetManager;
    private final Gson gson = new Gson();

    private final List<RsmConverter.Factory> converterFactories;
    private final List<CallAdapter.Factory> adapterFactories;

    public static synchronized void init(@NonNull Context context) {
        if (sRsm == null) {
            sRsm = new Rsm(context.getApplicationContext());
        }
    }

    public static synchronized Rsm get() {
        return sRsm;
    }

    public Rsm(Context context) {
        this(context.getAssets());
    }

    public Rsm(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.converterFactories = new ArrayList<>();
        this.adapterFactories = new ArrayList<>();
    }

    public <T> T create(final Class<T> clazz) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        } else {
                            Type returnType = method.getGenericReturnType();
                            Annotation[] annotations = method.getAnnotations();

//                            AssetName aa = method.getAnnotation(AssetName.class);
//                            if (aa != null) {
//                                String response = AssetUtils.getFromAssets(assetManager, aa.value());
//                                RsmConverter<String, ?> converter = responseConverter(returnType, annotations);
//                                return converter.convert(response);
//                            } else {
//                                return null;
//                            }

                            RsmServiceMethod<Object, Object> serviceMethod = new RsmServiceMethod<>(Rsm.this, method);
                            AssetCall<Object> call = new AssetCall<>(serviceMethod, args);
                            return serviceMethod.callAdapter.adapter(call);
                        }
                    }
                });
    }

    public RsmConverter<String, ?> responseConverter(Type type, Annotation[] annotations) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseConverter<>(gson, adapter);
    }

    public boolean addCallAdapterFactory(CallAdapter.Factory factory) {
        return this.adapterFactories.add(factory);
    }

    public boolean addConverterFactory(RsmConverter.Factory factory) {
        return this.converterFactories.add(factory);
    }

    public CallAdapter<?, ?> callAdapter(Type returnType, Annotation[] annotations) {
        return nextCallAdapter(null, returnType, annotations);
    }

    private CallAdapter<?,?> nextCallAdapter(CallAdapter.Factory skipPast, Type returnType, Annotation[] annotations) {
        int start = adapterFactories.indexOf(skipPast) + 1;
        for (int i = start, count = adapterFactories.size(); i < count; i++) {
            CallAdapter<?, ?> adapter = adapterFactories.get(i).get(returnType, annotations, this);
            if (adapter != null) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("no call adapter");
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public <D> RsmConverter<String, D> responseBodyConverter(Type responseType, Annotation[] annotations) {
        return nextResponseBodyConverter(null, responseType, annotations);
    }

    public <T> RsmConverter<String, T> nextResponseBodyConverter(
            @Nullable RsmConverter.Factory skipPast, Type type, Annotation[] annotations) {

        int start = converterFactories.indexOf(skipPast) + 1;
        for (int i = start, count = converterFactories.size(); i < count; i++) {
            RsmConverter<String, ?> converter =
                    converterFactories.get(i).responseBodyConverter(type, annotations, this);
            if (converter != null) {
                //noinspection unchecked
                return (RsmConverter<String, T>) converter;
            }
        }
        throw new IllegalArgumentException("no converter");
    }
}
