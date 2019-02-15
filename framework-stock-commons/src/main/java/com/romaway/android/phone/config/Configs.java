/**
 * 
 */
package com.romaway.android.phone.config;

import java.io.File;

import roma.romaway.commons.android.h5download.H5Info;

import android.content.Context;

import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.R;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * 系统设置
 * 
 * @author duminghui
 * 
 */
public class Configs {

	public final static int LOGIN_NOLOGIN = 0;
	public final static int LOGIN_NORMAL = 1;
	public final static int LOGIN_SMS = 2;
	public final static int LOGIN_ONEKEY_REG = 3;
	private final static Configs instance = new Configs();
	public static long lastTradeTime = 0;
	public static long rzrq_lastTradeTime = 0;

	public static String jiaoyiURL = "";// 只取一次，为避免当次下次完成后生效，让它下次启动再生效

	private Configs() {
		init();
	}

	public static final Configs getInstance() {
		return instance;
	}

	/** 初始化，如果第一次，则行情默认为15秒，交易默认3分钟 */
	private void init() {

	}

	/**
	 * 得到刷新时间，并根据是否是跑马灯做判断
	 * 
	 * @param isMq
	 * @return
	 */
//	public int getHqRefreshTimes(boolean isMq) {
//		if (isMq) {
//			if (getHqRefreshTimes() == 100000000) {
//				return 15000;
//			} else {
//				return getHqRefreshTimes();
//			}
//		}
//		return getHqRefreshTimes();
//	}

	/** 更新最新交易时间 */
	public static void updateLastTradeTime() {
		lastTradeTime = System.currentTimeMillis();
	}

//	public boolean tradeMonitor() {
//		boolean isTimeOut = false;
//		if (jyOutTime > 0/* && TradeUserMgr.isLogined()*/) {
//			if (jyOutTime == 14) {// 当用户设置为14时，为交易永不超时
//				return isTimeOut = false;
//			}
//			isTimeOut = System.currentTimeMillis() - lastTradeTime > jyOutTime * 60 * 1000;
//			Logger.d("Trade", String.format("超时监控,最后活动时间:%s,超时比较结果:%s",
//					lastTradeTime, isTimeOut));
//
//		}
//		return isTimeOut;
//	}

	// -------------------------------融资融券------------
	/** 更新最新融资融券交易时间 */
	public static void updateRZRQLastTradeTime() {
		rzrq_lastTradeTime = System.currentTimeMillis();
	}

//	public boolean rzrq_tradeMonitor() {
//		boolean isTimeOut = false;
//		// Logger.d("Trade", String.format("超时监控,超时设置:%s",
//		// jyOutTime));
//		if (jyOutTime > 0/* && TradeUserMgr.rzrq_isLogined()*/) {
//			if (jyOutTime == 14) {// 当用户设置为14时，为交易永不超时
//				return isTimeOut = false;
//			}
//			isTimeOut = System.currentTimeMillis() - rzrq_lastTradeTime > jyOutTime * 60 * 1000;
//			Logger.d("Trade", String.format("超时监控,最后活动时间:%s,超时比较结果:%s",
//					rzrq_lastTradeTime, isTimeOut));
//
//		}
//		return isTimeOut;
//	}

	public static final int URL_TYPE_ASSETS = 0;
	public static final int URL_TYPE_SYSTEM_DATA_FOLDER = 1;
	public static final int URL_TYPE_SDCARD = 2;
	public static final int URL_TYPE_SDCARD_UPDATE_H5 = 3;

	/**
	 * 
	 * @param context
	 * @param urlType
	 *            url类型：0：代表assets目录下；1：代表手机内部存储目录下；2：代表SDK目录下；
	 * @param url
	 *            交易模块的url 最前一位一定需要带"/"
	 * @return
	 */
	public static String getJiaoYiUrl(Context context, String url) {
		Logger.d("tag", "Configs getJiaoYiUrl:" + jiaoyiURL + url);
		return jiaoyiURL + url;
	}

	public static final String DATA_TRADE_TEST_URL = "DATA_TRADE_TEST_URL";

	public static String getTradeTestUrl() {
		return SharedPreferenceUtils.getPreference(
				SharedPreferenceUtils.DATA_CONFIG, DATA_TRADE_TEST_URL, "");
	}

	public static void setTradeTestUrl(String tradeTestUrl) {
		SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
				DATA_TRADE_TEST_URL, tradeTestUrl);
	}

	/**
	 * 重新生效，可做立即生效的接口
	 * @param context
	 */
	public static void resetEnable(Context context){
		initJiaoYiURL(context);
	}
	
	/**
	 * 前缀URL,注意这个方法为了jiaoyiURL重启后生效，只在启动app的时候调用一次
	 * 
	 * @param context
	 * @return
	 */
	public static String initJiaoYiURL(Context context) {

//		if (jiaoyiURL.length() > 0) {
//			return jiaoyiURL;
//		}

		int urlType = URL_TYPE_ASSETS;

		urlType = SharedPreferenceUtils.getPreference(
				SharedPreferenceUtils.DATA_CONFIG, "JIAO_YI_DEBUG_ONLINE",
				Configs.URL_TYPE_ASSETS);

		//++[需求] 修改H5版本使用规则，本地默认固化的版本与下载的版本比较，选择版本大的那个 wanlh 2015/12/09
		//如果本地固化版本大，则就采用本地固化的，即哪个版本大就用哪个
		if(stringCompareTo(H5Info.getAssetsCurrVersion(context),
				SharedPreferenceUtils.getPreference(
				SharedPreferenceUtils.DATA_CONFIG,"JIAO_YI_UPDATE_VERSION", "")) > 0){
			urlType = URL_TYPE_ASSETS;
		}
		//--[需求] 修改H5版本使用规则，本地默认固化的版本与下载的版本比较，选择版本大的那个 wanlh 2015/12/09
		
		Logger.d("tag", "Configs getJiaoYiUrl:" + urlType);
		
		if (urlType == URL_TYPE_ASSETS)// 本地存储位置
			jiaoyiURL = "file:///android_asset";

		else if (urlType == URL_TYPE_SDCARD)// SDK存储位置
			jiaoyiURL = "file://"
					+ FileSystem.getCacheRootDir(context, "H5_DEBUG").getPath();

		else if (urlType == URL_TYPE_SYSTEM_DATA_FOLDER)// 内部文件系统存储位置
			jiaoyiURL = "file://"
					+ FileSystem.getDataCacheRootDir(context, "").getPath();

		// lichuan 调试H5更新用的
		else if (urlType == URL_TYPE_SDCARD_UPDATE_H5)// 外部文件系统存储位置
		{
			File file = new File(context.getFilesDir() + "//kds519");
			if (file.exists()) {
				jiaoyiURL = "file://" + context.getFilesDir();
			} else {
				jiaoyiURL = "file:///android_asset";
			}

		}

		return jiaoyiURL;
	}

	/**
     * 比较两个字符串的大小
     * @param firstStr
     * @param secStr
     * @return
     */
	private static int stringCompareTo(String firstStr, String secStr){
    	
    	Logger.v("H5版本号比较", "本地默认固化版本："+firstStr+",更新的版本："+secStr);
    	
    	//if(Logger.getDebugMode() && StringUtils.isEmpty(firstStr))
    	//	Toast.makeText(context, "[警告]后台没有配置最新版本的H5交易模块！", Toast.LENGTH_LONG).show();
    	
    	if(StringUtils.isEmpty(firstStr) && StringUtils.isEmpty(secStr)){
    		return 0;
    	}
    	if(StringUtils.isEmpty(firstStr))
    		return -1;
    	if(StringUtils.isEmpty(secStr))
    		return 1;
//    	if(firstStr.length() > secStr.length())
//    		return 1;
//    	else if(firstStr.length() < secStr.length())
//    		return -1;
    	
    	return firstStr.compareTo(secStr);
    }
}
