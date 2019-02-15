package com.romalibs.utils.gson;

import android.support.annotation.Keep;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;
import com.romaway.utilslibs.BuildConfig;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wesley Lin on 9/7/15.
 */
@Keep
public class GsonHelper {


	 private static final String TAG = "GsonHelper";


    public static Gson buildGson() {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapter(Date.class, new TypeAdapter<Date>() {

            @Override
            public void write(com.google.gson.stream.JsonWriter writer, Date value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                    return;
                }

                long num = value.getTime();
                num /= 1000;
                writer.value(num);
            }

            @Override
            public Date read(com.google.gson.stream.JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }

                long value = reader.nextLong();
                return new Date(value * 1000);
            }

        });
        return b.create();
    }


    public static <T> List<Map<String, T>> dataMapsFromJson(String dataString) {
        if (TextUtils.isEmpty(dataString))
            return new ArrayList<Map<String, T>>();

        try {
            Type listType = new TypeToken<List<Map<String, T>>>(){}.getType();
            return GsonHelper.buildGson().fromJson(dataString, listType);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "failed to read json" + e.toString());
            return new ArrayList<Map<String, T>>();
        }
    }

    public static <T> String dataMapstoJson(List<Map<String, T>> dataMaps) {
        try {
            return GsonHelper.buildGson().toJson(dataMaps);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "failed to write json" + e.toString());
            return "[]";
        }
    }

    public static <T> T objectFromJson(String dataString, Type t) {
        try {
            return GsonHelper.buildGson().fromJson(dataString, t);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.v(TAG, "failed to read json" + e.toString());
            return null;
        }
    }

    public static <T> String objectToJson(T o) {
        try {
            return GsonHelper.buildGson().toJson(o);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.v(TAG, "failed to write json" + e.toString());
            return null;
        }
    }

    public static <T> Map<String, T> dataMapFromJson(String dataString) {
        if (TextUtils.isEmpty(dataString))
            return new HashMap<String,T>();

        try {
            Type t =  new TypeToken<Map<String, T>>(){}.getType();
            return GsonHelper.buildGson().fromJson(dataString, t);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.v(TAG, "failed to read json" + e.toString());
            return new HashMap<String,T>();
        }
    }

    public static <T> String dataMaptoJson(Map<String, T> dataMap) {
        try {
            return GsonHelper.buildGson().toJson(dataMap);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.v(TAG, "failed to write json" + e.toString());
            return "{}";
        }
    }

}
