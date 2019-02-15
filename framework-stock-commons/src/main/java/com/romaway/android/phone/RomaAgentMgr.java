package com.romaway.android.phone;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

/**
 * Created by Edward on 2016/2/4.
 * 各券商使用不同第三方统计统一管理类
 */
public class RomaAgentMgr {

    private static RomaAgentInterface mRomaAgentInterface;
    public static void initAgent(RomaAgentInterface romaAgentInterface){
        mRomaAgentInterface = romaAgentInterface;
    }

    public static void init(Context context, String appKey, String channelId){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.init(context, appKey, channelId);
    }

    /** 是否为Debug模式, 友盟支持(同一AppId,可将统计数据分开为正式与Debug) */
    public static void setDebugMode(boolean isDebug){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.setDebugMode(isDebug);
    }

    /** 是否开启异常捕获 */
    public static void setCatchUncaughtExceptions(boolean enabled){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.setCatchUncaughtExceptions(enabled);
    }

    /** Activity等页面恢复可见 统计 */
    public static void onResume(Activity activity){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onResume(activity);
    }

    /** Activity等页面变为不可见 统计 */
    public static void onPause(Activity activity){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onPause(activity);
    }

    /** Activity等页面恢复可见 统计 */
    public static void onPageStart(String pageName){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onPageStart(pageName);
    }

    /** 页面变为不可见 统计 */
    public static void onPageEnd(String pageName){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onPageEnd(pageName);
    }

    /** 点击/页面切换事件 计数统计 */
    public static void onEvent(Context context, String pageName){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onEvent(context, pageName);
    }

    /** 点击/页面切换事件 计数统计 */
    public static void onEvent(Context context, String EVENT_ID, String EVENT_LABEL){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onEvent(context, EVENT_ID, EVENT_LABEL);
    }

    /** 点击/页面切换事件 计数统计 */
    public static void onEvent(Context context, String EVENT_ID, String EVENT_LABEL, Map<String, Object> kv){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onEvent(context, EVENT_ID, EVENT_LABEL, kv);
    }

    /** 用户数据唯一标识等统计(talkingdata) */
    public static void onUserDataEvent(Context context, String EVENT_ID, String EVENT_LABEL){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onUserDataEvent(context, EVENT_ID, EVENT_LABEL);
    }

    /** 点击/页面切换事件 计数统计 */
    public static void onEventValue(Context context, String id, Map<String,String> m, int du){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onEventValue(context, id, m, du);
    }

    /** 应用关闭/杀死进程前需调用，友盟等用于保存统计数据 */
    public static void onKillProcess(Context context){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.onKillProcess(context);
    }

    /** 是否禁止默认的页面统计方式，即不会自动统计Activity(友盟有效) */
    public static void openActivityDurationTrack(boolean enabled){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.openActivityDurationTrack(enabled);
    }

    /** 在线参数回调接口(友盟, 要求是入口Activity使用) */
    public static void updateOnlineConfig(Context context){
        if(mRomaAgentInterface != null)
            mRomaAgentInterface.updateOnlineConfig(context);
    }
}
