package com.romaway.common.android.base;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 工具类
 * 
 * @author qinyn
 * 
 */
public class ActivityUtils
{
	/**
	 * 检测软件是否在前台运行
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isTopActivity(Context activity)
	{
		// String packageName = "com.romaway.android.phone";
		String packageName = activity.getPackageName();
		ActivityManager activityManager = (ActivityManager) activity
		        .getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0)
		{
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity
			        .getPackageName()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 软件是否运行着
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAppRunning(Context context)
	{
		// String packageName = "com.romaway.android.phone";
		String packageName = context.getPackageName();
		ActivityManager activityManager = (ActivityManager) context
		        .getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(100);
		for (RunningTaskInfo info : tasksInfo)
		{
			if (info.topActivity.getPackageName().equals(packageName)
			        && info.baseActivity.getPackageName().equals(packageName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到入口程序名
	 * 
	 * @param context
	 * @return
	 */
	public static String getMainActivity(Context context)
	{
		PackageManager pmPack = context.getPackageManager();
		String pkgName = context.getPackageName();
		List<PackageInfo> packinfo = pmPack
		        .getInstalledPackages(PackageManager.GET_ACTIVITIES);// 得到所有应用的包信息
		for (PackageInfo packinfos : packinfo)
		{
			if (packinfos.packageName.equals(pkgName))
			{
				return packinfos.activities[0].name;
			}
		}
		return "";
	}

	/**
	 * 获取顶部activity的名字
	 * 
	 * @param context
	 * @return
	 */
	public static String getRunningActivityName(Context context)
	{
		ActivityManager activityManager = (ActivityManager) context
		        .getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity ="";
		//对话是爆，添加try catch
		try
        {
			runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
					.getClassName();
	        
        } catch (Exception e)
        {
	        // TODO: handle exception
        }
		return runningActivity;
	}

}
