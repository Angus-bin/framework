/**
 *
 */
package com.romaway.android.phone.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimeZone;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.utils.ChannelUtils;
import com.romaway.android.phone.viewcontrol.UserLoginControl;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.R;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.coder.KCodeEngine;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.conn.util.InetAddressUtils;

import roma.romaway.commons.android.h5download.H5Info;

/**
 * @author duminghui
 *
 */
public class SysConfigs {
	/**
	 * 设置中心数据库名称
	 */
	public static String DB_CONFIG_NAME = "kds_configs";

	public static String KEY_LOGINSERVER_NAME = "key_loginserver_name";
	public static String KEY_LOGINSERVER_ADDRESS = "key_loginserver_address";
	public static String KEY_LOGINSERVER_HTTPS_PORT = "key_loginserver_https_port";

	/**
	 * 所选择的认证服务器索引
	 */
	public static int currentLoginServerIdx = 0;

	/**
	 * 所选择的行情服务器索引
	 */
	public static int currentHangqingServerIdx = 0;

	/**
	 * 所选择的交易服务器索引
	 */

	public static int currentTradeServerIdx = 0;

	public static String CPID;

	/**
	 * 第一次是否出现帮助提示
	 */
	public static boolean FIRSTTIMECHOSEHELP = false;
	/**
	 * 第一次是否添加默认自选股
	 */
	public static boolean FIRSTADDDEFAULTSTOCK = false;
	/**
	 * 是否是节假日
	 */
	public static boolean mIsHoliday = false;

	/** 小时时间偏移，用于调整小时 */
	public static int hourFix = 0;
	/** 时间分钟偏移，用于调整分钟 */
	public static int minuteFix = 0;
	public static boolean isHkStock = false;
	public static String APPID;

	//[需求] 添加软件类型 用于初始化协议入参 wanlh 2105/11/30
	public static String SOFT_TYPE;

	/**
	 * 初始化配置文件即读取配置项信息
	 */
	public static final void init(Context context) {
		CPID = Res.getString(R.string.cpid);
		APPID = Res.getString(R.string.appid);
		//[需求] 添加软件类型 用于初始化协议入参 wanlh 2105/11/30
		SOFT_TYPE = getClientSoftType();

		ServerInfo.setCpid(CPID);
		// 初始化保存userID
		KCodeEngine.setUserID(SharedPreferenceUtils.getPreference(
				UserLoginControl.pName, UserLoginControl.keyUserid, 0L));

		// 获取配置信息 第一次安装是否进入帮助说明
		String firstTimeChoseHelp = Res.getString(R.string.firstTimeChoseHelp);
		if (firstTimeChoseHelp.equals("0")) {
			FIRSTTIMECHOSEHELP = false;
		} else if (firstTimeChoseHelp.equals("1")) {
			FIRSTTIMECHOSEHELP = true;
		}

		// 获取配置信息 第一次安装是否添加默认自选股
		String firstTimeAddDefaultStock = Res
				.getString(R.string.firstTimeAddDefaultStock);
		if (firstTimeAddDefaultStock.equals("0")) {
			FIRSTADDDEFAULTSTOCK = false;
		} else if (firstTimeAddDefaultStock
				.equals("1")) {
			FIRSTADDDEFAULTSTOCK = true;
		}

		// 留痕
		initNickedInfo(context);
	}

	/**
	 * 判断是否支持自定义字体大小
	 *
	 * @return
	 */
	public static boolean isSupportCustomFont() {
		return true;
	}

	/**
	 * 判断是否节假日
	 *
	 * @return
	 */
	public static boolean isHoliday() {
		return mIsHoliday;
	}

	/**
	 * 设置是否节假日
	 *
	 * @param isHoliday
	 */
	public static void setHoliday(boolean isHoliday) {
		mIsHoliday = isHoliday;
	}

	/**
	 * 设置 时间偏移，以校正本地时间
	 *
	 * @param t
	 */
	public static void setTimeFix(String t) {
		String time = t;
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		while (time.length() < 6)
			time = "0" + time;
		int sHour = Integer.parseInt(time.substring(0, 2));
		int sMinute = Integer.parseInt(time.substring(2, 4));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		// hourFix = 24 + sHour - hour;
		// minuteFix = sMinute - minute;

		// 偏差纠正如，1、服务器时间9:57,本地时间时间10:01; 2:服务器时间10:01,本地时间 9:57
		int tmpMinute = sMinute - minute;
		if (tmpMinute >= 50) {
			// 第一种情形
			hourFix = 24 + sHour - hour + 1;
			minuteFix = tmpMinute - 60;
		} else if (tmpMinute <= -50) {
			// 第2种情形
			hourFix = 24 + sHour - hour - 1;
			minuteFix = tmpMinute + 60;
		} else { // 默认，不存在较大偏差
			hourFix = 24 + sHour - hour;
			minuteFix = tmpMinute;
		}

		// 校正联网时间，按最大延时2分钟计算
		minuteFix = tmpMinute - 2;
	}

	/**
	 * 判断是否允许刷新行情数据
	 *
	 * @return
	 */
	public static boolean enableAutoRefresHQ(long refreshTime) {
		if (refreshTime == 0)
			return false;

		return duringTradeTime();
	}

	/**
	 * 获取调整后的当前日期
	 *
	 * @return
	 */
	public static Date getFixedDate() {
		Calendar c = Calendar.getInstance();
		Date date = new Date();

		c.setTime(date);
		c.add(Calendar.MONTH, hourFix);
		c.add(Calendar.MINUTE, minuteFix);

		return c.getTime();
	}

	/**
	 * 判断当前是否交易时间，交易时间则允许刷新，非交易时间段不允许刷新
	 *
	 * @param refresheshTime
	 *            用户设置的行情刷新间隔
	 * @return
	 */
	public static boolean duringTradeTime() { // 是否在交易时间
		if (mIsHoliday)
			return false;
		boolean isDuringTradeTime = false;
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		// int weekDay = calendar.get((Calendar.DAY_OF_WEEK));
		int hour = (calendar.get(Calendar.HOUR_OF_DAY) + hourFix) % 24;
		int minute = calendar.get(Calendar.MINUTE) + minuteFix;
		calendar = null;
		if (minute >= 60) {
			hour = (hour + 1) % 24;
			minute = minute % 60;
		} else if (minute < 0) {
			hour = (hour - 1) % 24;
			minute = (minute + 60) % 60;
		}
		if (mIsHoliday) { // weekDay==1 || weekDay==7
			isDuringTradeTime = false;
			// 中国沪深: 周一至五9:30-11:30 PM1:00-3:00	(9:15 -- 9:25集合竞价)
			// 中国香港: 周一至五9:30-12:00 PM1:00-4:00	(9:00 -- 9:15集合竞价)
			// 美国股票: 夏21:30—4:00，冬令22:30—5:00
			// 欧洲股市: 夏15:00-23:30，冬16:00-0:30
		} else if ((hour == 9 && minute >= 10) || (hour > 9 && hour < 12)) {
			isDuringTradeTime = true;
			//20151118,刷新时间延长5分钟
		} else if ((hour == 11 && minute <= 35) || (hour >= 13 && hour < 15) || (hour == 15 && minute < 05)) {
			isDuringTradeTime = true;
		} /*else if (isHKStock() && ((hour ==8 && minute >=50) || (hour>=9 && hour <12) || ( hour == 12 &&(minute <= 10 || minute>=50) || (hour >=13 && hour < 16) || (hour == 16 && minute <=10)))){
			isDuringTradeTime = true;
		}*/ else {
			isDuringTradeTime = false;
		}
		Logger.d("Refresh", String.format("duringTradeTime()=>系统时间为,[%s]:[%s]",
				hour, minute));
		Logger.d("Refresh", String.format("duringTradeTime()=>是否是交易时间:[%s]",
				isDuringTradeTime));
		return isDuringTradeTime;
	}

	/**
	 * 判断是否在行情初始化
	 * @return
     */
	public static boolean duringCallAuction() { // 是否在行情初始化
		if (mIsHoliday)
			return false;
		boolean isDuringCallAuction = false;
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		// int weekDay = calendar.get((Calendar.DAY_OF_WEEK));
		int hour = (calendar.get(Calendar.HOUR_OF_DAY) + hourFix) % 24;
		int minute = calendar.get(Calendar.MINUTE) + minuteFix;
		calendar = null;
		if (minute >= 60) {
			hour = (hour + 1) % 24;
			minute = minute % 60;
		} else if (minute < 0) {
			hour = (hour - 1) % 24;
			minute = (minute + 60) % 60;
		}
		if (mIsHoliday) { // weekDay==1 || weekDay==7
			isDuringCallAuction = false;
			// 中国沪深: 周一至五9:30-11:30 PM1:00-3:00	(9:15 -- 9:25集合竞价)
		} else if ((hour == 9 && minute <= 14)) {
			isDuringCallAuction = true;
			//20151118,刷新时间延长5分钟
		} else {
			isDuringCallAuction = false;
		}
		Logger.d("Refresh", String.format("duringCallAuction()=>系统时间为,[%s]:[%s]",
				hour, minute));
		Logger.d("Refresh", String.format("duringCallAuction()=>是否是集合竞价:[%s]",
				isDuringCallAuction));
		return isDuringCallAuction;
	}


	/**
	 * 判断当前是否港股通交易时间，交易时间则允许刷新，非交易时间段不允许刷新
	 *
	 * @param refresheshTime
	 *            用户设置的行情刷新间隔
	 * @return
	 */
	public static boolean duringGGTTradeTime() { // 是否在交易时间
		if (mIsHoliday)
			return false;
		boolean isDuringTradeTime = false;
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		// int weekDay = calendar.get((Calendar.DAY_OF_WEEK));
		int hour = (calendar.get(Calendar.HOUR_OF_DAY) + hourFix) % 24;
		int minute = calendar.get(Calendar.MINUTE) + minuteFix;
		calendar = null;
		if (minute >= 60) {
			hour = (hour + 1) % 24;
			minute = minute % 60;
		} else if (minute < 0) {
			hour = (hour - 1) % 24;
			minute = (minute + 60) % 60;
		}
		// 港股通行情、交易界面自动刷新时间为8：50-12：10 12：50-16：10
		if (mIsHoliday) { // weekDay==1 || weekDay==7
			isDuringTradeTime = false;
		} else if ((hour == 8 && minute >= 50) || (hour >= 9 && hour < 12)
				|| (hour == 12 && minute <= 10)) {
			isDuringTradeTime = true;
		} else if ((hour == 12 && minute >= 50) || (hour >= 13 && hour < 16)
				|| (hour == 16 && minute <= 10)) {
			isDuringTradeTime = true;
		} else {
			isDuringTradeTime = false;
		}
		Logger.d("Refresh", String.format("duringGGTTradeTime()=>系统时间为,[%s]:[%s]",
				hour, minute));
		Logger.d("Refresh", String.format("duringGGTTradeTime()=>是否是交易时间:[%s]",
				isDuringTradeTime));
		return isDuringTradeTime;
	}

	/**
	 * 是否港股
	 *
	 * @return
	 */
	public static boolean isHKStock() {
		return isHkStock;
	}

	// 留痕
	/**
	 * MAC地址
	 */
	public static String MAC;

	/**
	 * IP地址
	 */
	public static String IP;

	/**
	 * IMEI号
	 */
	public static String IMEI;
	/**
	 * IMSI号
	 */
	public static String IMSI;
	/**
	 * 手机号
	 */
	public static String TELNUMBER;
	/**
	 * 机器唯一标示
	 */
	public static String ANDROID_ID;

	/**
	 * 客户端留痕信息
	 */
	public static String DEVICE_ADDRESS;
	/**
	 * 唯一标识
	 */
	public static String DEVICE_ID;

	/**
	 * 初始化留痕信息
	 *
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void initNickedInfo(Context context) {
		try {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			if (info != null) {
				IP = getNetworkIP(context);
				MAC = info.getMacAddress();
			} else {
				IP = "";
				MAC = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		if (tm != null) {
			IMEI = tm.getDeviceId();
			IMSI = tm.getSubscriberId();
			String androidID = android.provider.Settings.Secure.getString(
					context.getContentResolver(),
					android.provider.Settings.Secure.ANDROID_ID);
			// TELNUMBER = tm.getLine1Number();//androi中是无法通过该函数直接获取手机号码的
			TELNUMBER = RomaUserAccount.getUsername();

		} else {
			IMEI = "";
			IMSI = "";
			TELNUMBER = "";
		}
		ANDROID_ID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		setDeviceAddress();

		if (!StringUtils.isEmpty(IMEI)) {
			DEVICE_ID = IMEI;
		} else if (!StringUtils.isEmpty(ANDROID_ID)) {
			DEVICE_ID = ANDROID_ID;
		} else {
			DEVICE_ID = UUID.randomUUID().toString();
		}

	}

	private static String getNetworkIP(Context context) {
		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null)
			return null;

		if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifi.getConnectionInfo();
			return intToIp(wifiInfo.getIpAddress());
		} else {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface
						.getNetworkInterfaces(); en.hasMoreElements();) {
					NetworkInterface intf = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = intf
							.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						// [需求]交易留痕仅获取Android设备IPv4地址(避免返回ipv6地址)
						if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
							return inetAddress.getHostAddress();
						}
					}
				}
			} catch (SocketException ex) {
				Logger.e("WifiPreference IpAddress", ex.toString());
			}
		}
		return null;
	}

	private static String intToIp(int ipAddress) {
		try {
			return InetAddress.getByName(
					String.format("%d.%d.%d.%d", (ipAddress & 0xff),
							(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
							(ipAddress >> 24 & 0xff))).getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Logger.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	/**
	 * 获取项目Apk文件对应的渠道号:
	 * @param channelName
	 * @return
	 */
	public static String getChannelNum() {
		String channelName = Res.getString(R.string.umeng_channel_Id);
		//[优化]增加美团多渠道打包方案获取 多渠道号 Channel :
		if (!TextUtils.isEmpty(ChannelUtils.getChannel(OriginalContext.getContext()))){
			channelName = ChannelUtils.getChannel(OriginalContext.getContext());
			Logger.d("SysConfigs", "---------- channelName: " + channelName + " ----------");
		}else {
			try {
				ApplicationInfo appInfo = OriginalContext.getContext().getPackageManager()
						.getApplicationInfo(OriginalContext.getContext().getPackageName(),
								PackageManager.GET_META_DATA);
				if (!TextUtils.isEmpty(appInfo.metaData.getString("STATISTICS_CHANNEL")))
					channelName = appInfo.metaData.getString("STATISTICS_CHANNEL");
			} catch (PackageManager.NameNotFoundException e) {
				Logger.d("SysConfigs", e.getMessage());
			}
		}
		return channelName;
	}

	public static void setDeviceAddress() {
		if(IMSI == null) IMSI = "";
		if(MAC == null) MAC = "";
		// MAC = null;

		StringBuilder strBuilder = new StringBuilder();
		TELNUMBER = RomaUserAccount.getMobileLoginNumber();
		// 交易留痕:  手机号＋手机IMEI串号+客户端版本号+IMSI(15位国际移动用户识别码,同SIM卡相关)
		// 				+ MAC地址(实为Wifi Mac地址) + Android设备物理唯一标识码 + IP地址
		StringBuilder clientVersion = new StringBuilder();
		clientVersion.append(SysConfigs.getClientDisplayVersion());
		if(Res.getBoolean(R.bool.kconfigs_deviceAddress_hasH5version)) {
			//[优化]增加美团多渠道打包方案获取 多渠道号 Channel :
			String channelName = getChannelNum();
			clientVersion.append("+").append(H5Info.getCurrVersion(OriginalContext.getContext()))
						 .append("+").append(channelName);
		}
		//过滤掉MAC地址中的非法字符
		String tempMac = "";
		String[] strArr = MAC.split(":");
		for (int i = 0; i < strArr.length; i++) {
			char[] charArr = strArr[i].toCharArray();
			for (int j = 0; j < charArr.length; j++) {
				if (Character.isDigit(charArr[j]) || Character.isLetter(charArr[j])){
					tempMac += charArr[j];
				} else {
					tempMac += '0';
				}
			}
			if (i != strArr.length - 1){
				tempMac += ":";
			}
		}
		MAC = tempMac;
		strBuilder.append(TELNUMBER).append(",").append(IMEI)
				.append(",").append(clientVersion.toString())
				.append(",").append(IMSI).append(",").append(MAC).append(",")
				.append(ANDROID_ID).append(",").append(StringUtils.optString(IP));
		char[] temp = strBuilder.toString().toCharArray();

		//由于服务器以\0作为分隔符，所以增加去\0处理--20160615,chenjp
		String deviceAddress = "";
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] != '\0') {
				deviceAddress += temp[i];
			} else {
				deviceAddress += '0';
			}
		}
		DEVICE_ADDRESS = deviceAddress;
		Logger.d("Trade", String.format("设置留痕:%s", DEVICE_ADDRESS));
	}

	/**
	 * 获取网络留痕信息
	 *
	 * @return
	 */
	public static String getDeviceAddress() {
		setDeviceAddress();
		Logger.d("Trade", String.format("读取留痕:%s", DEVICE_ADDRESS));
		return DEVICE_ADDRESS;
	}

	/**
	 * 首次使用是否显示软件指引
	 *
	 * @return
	 */
	public static boolean isDisplayHelpOnFirst() {
		return FIRSTTIMECHOSEHELP;
	}

	/**
	 * 设置：首次使用是否显示软件指引
	 *
	 * @param val
	 */
	public static void setDisplayHelpOnFirst(boolean val, boolean updateDB) {
		FIRSTTIMECHOSEHELP = val;
		if (updateDB) {
			SharedPreferenceUtils.setPreference(BundleKeyValue.USER_DB,
					BundleKeyValue.USERDB_HELP_ON_FIRST, val ? "1" : RomaSysConfig.getClientVersion(OriginalContext.getContext()));
		}
	}

	/**
	 * 首次使用是否添加默认自选股
	 *
	 * @return
	 */
	public static boolean isAddDefaultStockOnFirst() {
		return FIRSTADDDEFAULTSTOCK;
	}

	/**
	 * 设置：首次使用是否添加默认自选股
	 *
	 * @param val
	 */
	public static void setAddDefaultStockOnFirst(boolean val, boolean updateDB) {
		FIRSTADDDEFAULTSTOCK = val;
		if (updateDB) {
			SharedPreferenceUtils.setPreference(BundleKeyValue.USER_DB,
					BundleKeyValue.USERDB_ADD_DEFAULTSTOCK_ON_FIRST, val ? 1
							: 0);
		}
	}

	/**
	 * 读取客户端版本号，用于版本检查
	 *
	 * @return 短版本号如2.0.1
	 */
	public static String getClientVersion() {
		return Res.getString(R.string.config_version);
	}

	/**
	 * 读取客户端显示的版本号，用于关于中显示
	 *
	 * @return 长版本号 如：2.0.1.20130101.66078
	 */
	public static String getClientDisplayVersion() {
		return Res.getString(R.string.kds_inner_version_show);
	}

	/**
	 * 读取客户端配置的软件类型 ，常用于软件升级
	 *
	 * @return 软件类型如 66078
	 */
	public static String getClientSoftType() {
		return Res.getString(R.string.config_apptype);
	}

	/**
	 * 获取沪深市场情况
	 *
	 * @return
	 */
	public static String getMarketState() {
		String state = "";
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		int hour = (calendar.get(Calendar.HOUR_OF_DAY) + hourFix) % 24;
		int minute = calendar.get(Calendar.MINUTE) + minuteFix;
		calendar = null;
		if (minute >= 60) {
			hour = (hour + 1) % 24;
			minute = minute % 60;
		} else if (minute < 0) {
			hour = (hour - 1) % 24;
			minute = (minute + 60) % 60;
		}
		if (mIsHoliday) {
			state = "已收市";
		} else if (hour == 9 && minute <= 14) {
			state = "开盘前";
		} else if (hour == 9 && minute >= 15 && minute <= 30) {
			state = "集合竞价";
		} else if ((hour == 9 && minute >= 31) || (hour > 9 && hour < 12)
				|| (hour == 11 && minute <= 30) || (hour >= 13 && hour < 15)) {
			state = "交易中";
		} else if ((hour == 11 && minute >= 31) || hour == 12) {
			state = "午间休市";
		} else if ((hour == 15 && minute > 1) || hour > 15) {
			state = "已收盘";
		} else {
			state = "";
		}
		return state;
	}

	/**
	 * 获取港股市场情况
	 *
	 * @return
	 */
	public static String getGGMarketState() {
		String state = "已收市";
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		int hour = (calendar.get(Calendar.HOUR_OF_DAY) + hourFix) % 24;
		int minute = calendar.get(Calendar.MINUTE) + minuteFix;
		calendar = null;
		if (minute >= 60) {
			hour = (hour + 1) % 24;
			minute = minute % 60;
		} else if (minute < 0) {
			hour = (hour - 1) % 24;
			minute = (minute + 60) % 60;
		}
		if (mIsHoliday) {
			state = "已收市";
		} else if (hour == 9 && minute <= 15) {
			state = "盘前交易";
		} else if (hour == 9 && minute > 15 && minute <= 30) {
			state = "暂停交易";
		} else if ((hour == 9 && minute >= 31) || (hour > 9 && hour <= 12)
				|| (hour >= 13 && hour < 16)) {
			state = "延时";
		} else if (hour == 12 && minute > 0) {
			state = "午间休市";
		} else {
			state = "已收盘";
		}
		return state;
	}

}