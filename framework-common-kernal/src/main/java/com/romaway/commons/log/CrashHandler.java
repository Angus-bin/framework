package com.romaway.commons.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.romalibs.utils.Res;
import com.romalibs.utils.SharedPreferenceUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.utils.HttpUtils2;
import com.romaway.utilslibs.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 系统崩溃Handler，记录全局性的系统崩溃日志
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler
{
	public static final String TAG = "CrashHandler";

	private String CPID;
	
	/**软件类型*/
	private  String SoftType;
	
	/**内部版本号*/
	private  String InnerVersionName;
	
	
	/**app id*/
	private  String APPID;
	
	/**软件名称*/
	private  String AppName;
	
	/**
	 * 系统默认的 UncaughtExceptionHandler处理类
	 */
	private UncaughtExceptionHandler mDefaultHandler;

	/**
	 * CrashHandler实例
	 */
	private static CrashHandler INSTANCE = new CrashHandler();

	private Context mContext;

	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler()
	{
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance()
	{
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context, String AppName, String InnerVersionName, 
			String CPID, String APPID, String SoftType)
	{
		mContext = context;
		this.CPID = CPID;
		this.SoftType = SoftType;
		this.APPID = APPID;
		this.InnerVersionName = InnerVersionName;
		this.AppName = AppName;
		
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		if (!handleException(ex) && mDefaultHandler != null)
		{
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else
		{
			try
			{
				Thread.sleep(3000);
			} catch (InterruptedException e)
			{
				Log.e(TAG, "error : ", e);
			}
			// 退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
//			killProcess(mContext);
			System.exit(0);
			System.gc();
		}

	}
	public static void killProcess(Context mAct) {

	    String packageName = mAct.getPackageName();

	    String processId = "";

	    try {

	    Runtime r = Runtime.getRuntime();

	    Process p = r.exec("ps");

	    BufferedReader br = new BufferedReader(new InputStreamReader(p

	    .getInputStream()));

	    String inline;

	    while ((inline = br.readLine()) != null) {

	    if (inline.contains(packageName)) {

	    break;

	    }

	    }

	    br.close();

	    StringTokenizer processInfoTokenizer = new StringTokenizer(inline);

	    int count = 0;

	    while (processInfoTokenizer.hasMoreTokens()) {

	    count++;

	    processId = processInfoTokenizer.nextToken();

	    if (count == 2) {

	    break;

	    }

	    }

	    r.exec("kill -15 " + processId);

	    } catch (IOException ex) {

	    }

	    }
	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex)
	{
		if (ex == null)
		{
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread()
		{
			@Override
			public void run()
			{
				Looper.prepare();
				Toast.makeText(
				        mContext,
				        "很抱歉,程序出现未知异常,已记录日志[日志文件件/sdcard/roma/crash_log/目录].",
				        // "程序异常,即将重启...",
				        Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo(mContext); 
		// 保存日志文件
		saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx)
	{
		try
		{
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
			        PackageManager.GET_ACTIVITIES);
			if (pi != null)
			{
				String versionName = pi.versionName == null ? "null"
				        : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("cpid", CPID);
				infos.put("APPID", APPID);
				infos.put("AppName", AppName);
				infos.put("SoftType", SoftType);
				infos.put("InnerVersionName", InnerVersionName);
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
				infos.put("AndroidSysVersion", Build.VERSION.RELEASE);
				infos.put("user_id", SharedPreferenceUtils.getPreference("user_data", "user_id", "0"));
				long timestamp = System.currentTimeMillis();
				String time = formatter.format(new Date());
				infos.put("user_time", "crash-" + time + "-" + timestamp + ".txt");
			}
		} catch (NameNotFoundException e)
		{
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields)
		{
			try
			{
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e)
			{
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex)
	{

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
			Logger.d("init_info", "crash = " + key + "=" + value);
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter); 
		//if(Logger.getDebugMode())
		//	ex.printStackTrace();
		Throwable cause = ex.getCause();
		while (cause != null)
		{
			cause.printStackTrace(printWriter);  
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		if (!Logger.getDebugMode()) {
			Logger.d("init_info", "crash = " + result);
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(SharedPreferenceUtils.getPreference("Ip", "Ip", ""))) {
				ip = SharedPreferenceUtils.getPreference("Ip", "Ip", "");
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			final Map<String, String> params = new HashMap<String, String>();
			params.put("content", sb.toString());
			final String finalIp = ip;
			new Thread(new Runnable() {
				@Override
				public void run() {
					String result1 = HttpUtils2.submitPostData(finalIp + "app/addLog", params, "utf-8");
					Logger.d("init_info", "result1 = " + result1);
				}
			}).start();
		}
		try
		{
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "crash-" + time + "-" + timestamp + ".txt";
			if (Environment.getExternalStorageState().equals(
			        Environment.MEDIA_MOUNTED))
			{
				String path = getCacheRootDir(mContext, "crash_log").getPath()+"/";//"/sdcard/kds/crash_log";
				// String path = "/mnt/sdcard/kdsstocklog/";
				File dir = new File(path);
				if (!dir.exists())
				{
					dir.mkdirs();
				}
				deleteOldFile(path);// 删除过期文件
				
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e)
		{
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
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
	
	private File getCacheRootDir(Context context, String dirOrFile){
		File cacheRootDir = null;
		
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
		    
			//String childPath = "Android/KDS/"+context.getPackageName()+"/"+
			//        (dirOrFile == null? "" : dirOrFile);
			String  childPath = "roma"+"/"+
		                    (dirOrFile == null? "" : dirOrFile);
			cacheRootDir = createChildsDir(Environment.getExternalStorageDirectory(), childPath);
				//	"Android/data/"+context.getPackageName()+"/cache");
		}else {
		    
		    String childPath = (dirOrFile == null? "" : dirOrFile);
		    
			cacheRootDir = createChildsDir(context.getCacheDir(), childPath);
		}
		
		return cacheRootDir;
	}
	
	
	@SuppressLint("NewApi")
	private File createChildsDir(File parentDir, String childPath){
		
		File file = null;
		if(parentDir.isDirectory()){
			String childDirPath = parentDir.getPath();
			String[] path = childPath.split("/");
			for(int i = 0; i < path.length;i++){
				
				childDirPath += "/" + path[i];
				
				file = new File(childDirPath);
				if(!file.exists()) {//考虑已经存在同样名字的文件或者目录，
					file.mkdir();
					file.setExecutable(true, false);
					file.setReadable(true, false);
					file.setWritable(true, false);
				}
			}
		}
		return file;
	}

}
