package com.hanter.android.rsm;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final class AssetUtils {

    public static String getFromInputStream(InputStream is) throws IOException {
        InputStreamReader inputReader = new InputStreamReader(is);
        BufferedReader bufReader = new BufferedReader(inputReader);
        StringBuilder sb = new StringBuilder();
        int length;
        char[] buffer = new char[2048];
        while ((length = bufReader.read(buffer)) != -1) {
            sb.append(buffer, 0, length);
        }
        return sb.toString();
    }

    public static String getFromAssets(Context context, String fileName) throws IOException {
        return getFromAssets(context.getAssets(), fileName);
    }

    public static String getFromAssets(AssetManager assetmanager, String fileName) throws
            IOException {
        return getFromInputStream(assetmanager.open(fileName));
    }

    public static String getFromRaw(Context context, @RawRes int id) throws IOException {
        return getFromInputStream(context.getResources().openRawResource(id));
    }

    public static String getFromRaw(Resources resources, @RawRes int id) throws IOException {
        return getFromInputStream(resources.openRawResource(id));
    }

}
