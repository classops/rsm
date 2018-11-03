package com.hanter.android.rsm;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

/**
 * 类名：GsonResponseConverter <br/>
 * 描述：响应类型转换器
 * 创建时间：2018/10/30 23:01
 *
 * @author hanter
 * @version 1.0
 */
public class GsonResponseConverter<T> implements RsmConverter<String, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public GsonResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(String value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(new StringReader(value));
        return adapter.read(jsonReader);
    }

}
