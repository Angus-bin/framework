package com.romaway.android.phone.viewcontrol;

import com.romaway.android.phone.UserAccount;

public class UserLoginControl
{

	public static final String pName = "user_data";

	/**
	 * 用户账户key
	 */
	public static final String keyName = "key_name";

	/**
	 * 用户特征码key
	 */
	public static final String keyUserid = "key_uid";

	/**
	 * 用户密码key
	 */
	public static final String keyPwd = "key_pwd";
	
	/**
	 * 记住密码
	 */
	public static final String savePwd = "save_pwd";
	/**
	 * 买卖日期
	 */
	public static final String sellName = "sell_data";
	
	/**
	 * 保存内部版本号
	 */
	public static final String keyInnerVersionNumber = "keyInnerVersionNumber";
	
	/**
	 * 第一次启动标志，用来处理老版本升级新版本
	 */
	public static final String keyFirstAppFlagName = "keyFirstAppFlagName";
	
	/**
	 * 自动登录
	 */
	public static final String autoLogin = "auto_login";
	// ***************************************************
	public static final String GuestPName = "GuestPName";
	/** 游客账户key **/
	public static final String GuestkeyName = "GuestkeyName";
	// *****************************************************
	public static final String IsGuestPName = "IsGuestPName";
	/** 是否游客账户key **/
	public static final String IsGuestkeyName = "IsGuestkeyName";
	public static final String IsGuestkeyName_True = "0";
	public static final String IsGuestkeyName_Flase = "1";
	/**认证站点*/
	public static final String PRES_SYS_AUTH_SITE_INDEX = "PRES_SYS_AUTH_SITE_INDEX"; 
	/**认证站点key*/
	public static final String KEY_PRES_SYS_AUTH_SITE_INDEX = "KEY_PRES_SYS_AUTH_SITE_INDEX"; 
	
	/**
	 * 留痕信息保存的文件名
	 */
	public static final String liuhenSharedPreFileName = "LiuHenSharedPreference";
	/**
	 * 留痕手机号码key
	 */
	public static final String keyLiuHenPhoneNumber = "keyLiuHenPhoneNumber";
	
	/**
	 * 公告日期标志key
	 */
	public static final String keyGGDate = "keyGGDate";
	/**
	 * 新股提醒标志key
	 */
	public static final String keyIPODate = "keyIPODate";
	
	public static String getKdsId()
	{
		System.out.println("KdsId:  "+ UserAccount.getKdsId());
		return UserAccount.getKdsId();
	}
	
}
