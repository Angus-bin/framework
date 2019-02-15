package com.romaway.android.phone;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

/** 第三方统计接口类 */
public abstract class RomaAgentInterface {

    /**
     * 统计初始化
     * @param context
     * @param appKey        统计App Key,统计平台申请;
     * @param channelId     渠道标识符, 可为空
     */
    public abstract void init(Context context, String appKey, String channelId);

    /** 是否为Debug模式统计: 友盟支持 */
    public abstract void setDebugMode(boolean isDebug);

    /** 是否开启自动异常捕获(会自动上传至统计平台) */
    public abstract void setCatchUncaughtExceptions(boolean enabled);

    /** Activity等页面恢复可见 统计 */
    public abstract void onResume(Activity activity);

    /** Activity等页面变为不可见 统计 */
    public abstract void onPause(Activity activity);

    /** Activity等页面恢复可见 统计 */
    public abstract void onPageStart(String pageName);

    /** 页面变为不可见 统计 */
    public abstract void onPageEnd(String pageName);

    /** 点击/页面切换事件 计数统计 */
    public abstract void onEvent(Context context, String pageName);

    /**
     * 点击/页面事件统计(talkingdata)
     * @param EVENT_ID      可为简单事件, 或以Event ID为目录名，而Label标签用于区分事件
     * @param EVENT_LABEL   事件标签(子事件)
     */
    public abstract void onEvent(Context context, String EVENT_ID, String EVENT_LABEL);

    /**
     * 点击/页面事件统计(talkingdata)
     * @param EVENT_ID      可为简单事件, 或以Event ID为目录名，而Label标签用于区分事件
     * @param EVENT_LABEL   事件标签(子事件)
     * @param kv            数据统计记录Map
     */
    public abstract void onEvent(Context context, String EVENT_ID, String EVENT_LABEL, Map<String, Object> kv);

    /**
     * 统计数值型变量的值的分布 计数统计(友盟等)
     * @param context
     * @param id        事件ID
     * @param m         当前事件的属性和取值
     * @param du        当前事件的数值为当前事件的数值，取值范围是-2,147,483,648 到 +2,147,483,647 之间的有符号整数，即int 32类型，如果数据超出了该范围，会造成数据丢包，影响数据统计的准确性
     */
    public abstract void onEventValue(Context context, String id, Map<String, String> m, int du);

    /** 应用关闭/杀死进程前需调用，友盟等用于保存统计数据 */
    public abstract void onKillProcess(Context context);

    /** 是否禁止默认的页面统计方式，即不会自动统计Activity(友盟有效) */
    public void openActivityDurationTrack(boolean enabled){}

    /** 在线参数回调接口(友盟, 要求是入口Activity使用) */
    public void updateOnlineConfig(Context context){}

    /**
     * 用户数据唯一标识等统计(talkingdata)
     * @param EVENT_ID      特殊事件标识标签
     * @param EVENT_LABEL   标识内容
     */
    public void onUserDataEvent(Context context, String EVENT_ID, String EVENT_LABEL){}
}
