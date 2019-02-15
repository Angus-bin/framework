package com.romaway.android.phone;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongrb on 2017/6/14.
 */
public class ActivityManager {

    private static ActivityManager activityManager;

    private static Map<String, Activity> map;

    // 上下文对象
    private Context context;

    private ActivityManager(Context context) {
        this.context = context;
    }

    public static ActivityManager getInstance(Context context) {

        if (activityManager == null) {
            activityManager = new ActivityManager(context);
            map = new HashMap<String, Activity>();
        }
        return activityManager;
    }

    public void addActivity(String key, Activity activity) {
        map.put(key, activity);
    }

    public Activity getActivity(String key) {
        Activity activity;
        activity = map.get(key);
        return activity;
    }

    public void removeActivity(String key) {
        Activity activity;
        activity = map.get(key);
        if (activity != null) {
            map.remove(key);
        }
    }

}
