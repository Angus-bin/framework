package com.romalibs.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by wanlh on 2016/9/18.
 */
public class ROMA_ActivityUtils {

    /**
     * 跳转至界面<BR>
     * 如果目标activity已经存在，则重用，不会生成新的activity
     *
     * @param activity
     *            当前
     * @param targetActivity
     *            目标Activity
     * @param bundle
     * @param requestCode
     *            请求码，默认值为-1
     * @param closeCurrentActivity
     *            是否关闭当前activity
     */
    public static void switchWindow(Activity activity,
                                    Class<? extends Activity> targetActivity, Bundle bundle,
                                    int requestCode, boolean closeCurrentActivity) {
        if (activity == null) {
//            Logger.d("tag:  ", activity + "");
            return;
        }
//        Logger.d("tag:	", "进来了");
        Intent i = new Intent();
        i.setClass(activity, targetActivity);
        // i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        if (bundle != null) {
            i.putExtras(bundle);
        }
        if (requestCode > 0) {
            activity.startActivityForResult(i, requestCode);
//            Logger.d("tag:	", "跳转了1");
        } else {
//            Logger.d("tag:	", "跳转了");
            activity.startActivity(i);
        }

        if (closeCurrentActivity) {
            activity.finish();
        }
    }

    /**
     * 常用于启动从一个模块中启动另一个模块中的activity
     *
     * @param activity
     * @param startActivityPCName
     *            被启动的Activity的包名+类名
     * @param bundle
     * @param requestCode
     * @param closeCurrentActivity
     */
    public static void switchWindow(Activity activity,
                                    String startActivityPCName, Bundle bundle, int requestCode,
                                    boolean closeCurrentActivity) {
        if (activity == null) {
            return;
        }
        Intent i = new Intent();
        // i.setClass(currentAct, targetActivity);
        // i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        ComponentName componentName = new ComponentName(
                activity.getPackageName(), startActivityPCName);
        i.setComponent(componentName);

        if (bundle != null) {
            i.putExtras(bundle);
        }
        if (requestCode > 0) {
            activity.startActivityForResult(i, requestCode);
        } else {
            activity.startActivity(i);
        }

        if (closeCurrentActivity) {
            activity.finish();
        }
    }

    /**
     * 启动 web 原生页面接口类
     * @param activity
     * @param classs
     * @param hasTitle
     * @param webUrl
     * @param closeCurrentActivity
     */
    public static void switchWebWindow(Activity activity, Class<?> classs,
                                       String hasTitle, String webUrl, boolean closeCurrentActivity){
        if(!TextUtils.isEmpty(webUrl)){

            Intent intent = new Intent();
            intent.putExtra("key_h5url", webUrl);

            if(!TextUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageNoHead"))
                intent.putExtra("key_titleVisibility", View.GONE);
            else if(!TextUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageWithHead"))
                intent.putExtra("key_titleVisibility", View.VISIBLE);

            intent.setClass(activity, classs);
            activity.startActivity(intent);

            if (closeCurrentActivity) {
                activity.finish();
            }
        }
    }

    /**
     * 启动 web 原生页面接口类
     * @param activity
     * @param startActivityPCName
     * @param hasTitle
     * @param webUrl
     * @param closeCurrentActivity
     */
    public static void switchWebWindow(Activity activity, String startActivityPCName,
                                       String hasTitle, String webUrl, boolean closeCurrentActivity){
        if(!TextUtils.isEmpty(webUrl)){

            Intent i = new Intent();
            i.putExtra("key_h5url", webUrl);
            if(!TextUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageNoHead"))
                i.putExtra("key_titleVisibility", View.GONE);
            else if(!TextUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageWithHead"))
                i.putExtra("key_titleVisibility", View.VISIBLE);

            ComponentName componentName = new ComponentName(
                    activity.getPackageName(), startActivityPCName);
            i.setComponent(componentName);
            activity.startActivity(i);

            if (closeCurrentActivity) {
                activity.finish();
            }
        }
    }
}
