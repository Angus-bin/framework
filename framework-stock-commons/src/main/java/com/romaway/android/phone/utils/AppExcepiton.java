package com.romaway.android.phone.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.format.Time;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.commons.log.Logger;

/**
 * 捕捉崩溃信息写到文件
 * 
 * @author zhuft
 * @version 1.0 2013-4-18
 */
public class AppExcepiton implements UncaughtExceptionHandler
{

	// 获取application 对象；
	private Context mContext;

	private Thread.UncaughtExceptionHandler defaultExceptionHandler;
	// 单例声明CustomException;
	private static AppExcepiton appException;

	private AppExcepiton()
	{
		// mContext = CA.getApplication();
//		mContext = Application.getApplication();
		mContext = OriginalContext.getContext();
		init(mContext);
	}

	private Activity mActivity;

	public static AppExcepiton getInstance(Activity activity)
	{
		Logger.i("AppExcepiton", "Start");

		/*if (activity instanceof HomeActivity)
		{
			*//**
			 * //TODO wangyj SplashActivity 有重现创建的风险
			 *//*
			appException = null;
		}*/
		if (appException == null)
		{
			appException = new AppExcepiton();
		}
		appException.setCurrentActivity(activity);
		return appException;
	}

	private void setCurrentActivity(Activity activity)
	{
		mActivity = activity;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception)
	{
		// TODO Auto-generated method stub
		String path = null;
		// System.out.println("----------------------uncaughtException");
		try
		{
			if (defaultExceptionHandler != null)
			{
				String state = Environment.getExternalStorageState();
				// 判断SdCard是否存在并且是可用的
				if (Environment.MEDIA_MOUNTED.equals(state))
				{
					path = Environment.getExternalStorageDirectory().getPath();
				}
				// path=/mnt/sdcard/ 是不对的
				// 创建一个logcat目录
				// path = path + "/hundsunstock/log";
				path = "mnt/sdcard/kdsstocklog/";
				File file = new File(path);
				if (!file.exists())
				{
					file.mkdir();
				}
				// 删除过期文件
				deleteOldFile(path);

				String time = getCurrentTime();
				String fileName = time.substring(0, 10);
				File myFile = new File(path + "/" + fileName + ".log");
				Logger.i("pass", path + "/" + fileName + ".log");
				StackTraceElement[] sts = exception.getStackTrace();

				StringBuffer sb = new StringBuffer();
				sb.append("\n" + time + "-------------------->" + "["
				        + exception.getLocalizedMessage() + "]\n");
				for (StackTraceElement s : sts)
				{
					sb.append(s.toString() + "\n");
				}
				sb.append("\n");
				try
				{
					FileWriter fw = new FileWriter(myFile, true);
					fw.write(sb.toString());
					fw.flush();
					fw.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				exception.printStackTrace();
				// 不懂 int is_open_umeng = Res.getDimen("is_open_umeng");
				// if(is_open_umeng == 1){
				// MobclickAgent.onKillProcess( CA.getActivity() );
				// }

				// 将异常抛出，则应用会弹出异常对话框.这里先注释掉
				defaultExceptionHandler.uncaughtException(thread, exception);
				if (mActivity != null)
				{
					mActivity.finish();
				}
				// System.out.println("----------------killProcess");
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public String getCurrentTime()
	{
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		int day = t.monthDay;
		int hour = t.hour;
		int minute = t.minute;
		int second = t.second;
		String time = year + "-" + month + "-" + day + " " + hour + ":"
		        + minute + ":" + second;
		return time;

	}

	public void init(Context context)
	{
		mContext = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 删除超过一个月的日志
	 * 
	 * @param path
	 */
	public void deleteOldFile(final String path)
	{
		File file = new File(path);
		file.list(new FilenameFilter()
		{

			@Override
			public boolean accept(File dir, String filename)
			{
				// TODO Auto-generated method stub
				File file = new File(path + "/" + filename);
				Long ago = file.lastModified();
				Long now = System.currentTimeMillis();
				// 如果最后一次修改时间超过一年：3153600秒
				if ((now - ago) > 31536000 / 12)
				{
					file.delete();
				}
				return false;
			}
		});

	}

}
