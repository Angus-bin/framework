package com.romaway.common.utils;

import com.romalibs.utils.SharedPreferenceUtils;

/**
 * Created by hongrb on 2017/10/17.
 */
public class RomaSysConfig {

    public static void setIp(String Ip) {
        SharedPreferenceUtils.setPreference("Ip", "Ip", Ip);
    }

    public static String getIp() {
        return SharedPreferenceUtils.getPreference("Ip", "Ip", "");
    }

}
