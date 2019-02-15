package com.romaway.android.phone.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.R;
import com.romaway.commons.log.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {

	/* 下载中 */
	public static final int DOWNLOAD = 1;
	/* 下载结束 */
	public static final int DOWNLOAD_FINISH = 2;
	//下载失败
	public static final int DOWNLOAD_FAIL = 0;
	private static final int NOTIFY_ID = 10000;
	private boolean cancelled = false;
	private Context mContext = this;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private String downurl;
	private String mSavePath;
	private String fileName = "roma_gphone.apk";
	private float fileSize;//MB
	private long fileLength;//字节
	private DownloadApkThread downloadApkThread;
	private Intent intent = new Intent("com.example.communication.RECEIVER");
	private Intent intent1 = new Intent("com.example.communication.RECEIVER1");

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DOWNLOAD:
				int rate = msg.arg1;
				float size = (Float) msg.obj;
				// 更新进度
				if(rate <= 100){
					RemoteViews contentView = mNotification.contentView;
					contentView.setTextViewText(R.id.txt_notification_title, "下载中...　" + rate + "%" + "　" + Double.toString(size/100.0) + "MB/" + Double.toString(fileSize/100.0) + "MB");
					contentView.setProgressBar(R.id.pb_notification, 100, rate, false);
					intent1.putExtra("content", "下载中... " + rate + "%" + " " + Double.toString(size/100.0) + "MB/" + Double.toString(fileSize/100.0) + "MB");
					sendBroadcast(intent1);
				}
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			case DOWNLOAD_FINISH:
				// 下载完成取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				String path = mSavePath + "/" + fileName;
				//发送广播,判断签名，是否安装
				intent.putExtra("path", path);
				intent.putExtra("fileLength", fileLength);
				sendBroadcast(intent);
				stopSelf();// 停掉服务
				break;
			case DOWNLOAD_FAIL:
				RomaToast.showMessage(mContext, "下载失败，请稍后再试！");
				// 取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				stopSelf();// 停掉服务
				break;
			}
		};
	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Logger.d("DownloadService", "DownloadService onCreate");
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		Logger.d("DownloadService", "DownloadService onStart");
		super.onStart(intent, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Logger.d("DownloadService", "DownloadService onStartCommand");
		//创建通知
		initNotification();
		try {
			String flag = intent.getExtras().getString("ROMA_UPGRADE_TYPE", "release");
			Logger.d("DownloadService", "upgrade flag = " + flag);
			if ("beta".equals(flag)) {
				downurl = SharedPreferenceUtils.getPreference("user_data", "ROMA_DOWNLOAD_URL_BETA", "");
			} else if ("release".equals(flag)) {
				downurl = SharedPreferenceUtils.getPreference("user_data", "ROMA_DOWNLOAD_URL_RELEASE", "");
				downurl = RomaSysConfig.getUpDataUrl();
			}
			if (downloadApkThread == null ) {
				downloadApkThread = new DownloadApkThread();
			}
			if ( !downloadApkThread.isAlive()) {
				downloadApkThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Logger.d("DownloadService", "DownloadService onDestroy");
	}
	
	private void initNotification(){
		int icon = R.drawable.roma_notification_icon;
		CharSequence tickerText = "开始下载...";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		//放置在正在进行栏目中
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		
		RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.roma_download_notification_layout);
		mNotification.contentView = contentView;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	private class DownloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				Logger.d("DownloadService", "SD ===" + Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED));
//				if (Environment.getExternalStorageState().equals(
//						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					Logger.d("DownloadService", "Download url===" + downurl);
					URL url = new URL(downurl);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					fileLength = length;
					// 创建输入流
					InputStream is = conn.getInputStream();
					
					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, fileName);
					FileOutputStream fos = new FileOutputStream(apkFile);

					// 缓存
					byte buf[] = new byte[1024];
					int count = 0;
					int progress = 0;
					int oldProgress = progress;
					fileSize = Math.round((float) length / 1000000*100);
					float curSize = 0;
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;

						// 计算进度条位置
						curSize = Math.round((float) count / 1000000*100);
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						Message msg = handler.obtainMessage();
						msg.what = DOWNLOAD;
						msg.arg1 = progress;
						msg.obj = curSize;
						if(progress != oldProgress){//防止频繁的通知更新
							handler.sendMessage(msg);
						}
						
						if (numread <= 0) {
							// 下载完成
							handler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
						oldProgress = progress;
					} while (!cancelled);
					fos.close();
					is.close();
//				}
			} catch (Exception e) {
				handler.sendEmptyMessage(DOWNLOAD_FAIL);
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
