package com.romaway.android.phone;

import android.text.TextUtils;

import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.viewcontrol.UserLoginControl;
import com.romaway.commons.lang.StringUtils;

/**
 * 账号信息，即初始化认证登录用户账号信息
 * 
 * @author duminghui
 * 
 */
public class UserAccount {
	public static final String pName = "user_data";

	public static final String keyName = "key_name";

	public static final String keyPwd = "key_pwd";
	/**
	 * 用户特征码key
	 */
	public static final String keyUid = "key_uid";

	private static String username = "";
	private static String userpasswd = "";

	/** 客户号 */
	public static long qwUserID = 0;

	private UserAccount() {
		username = "";
		userpasswd = "";
	}

	/**
	 * 读取已保存的用户信息
	 * 
	 * @param defaultValue
	 * @return
	 */
	public static String getUsername() {
		if (StringUtils.isEmpty(username)) {
			username = ViewParams.bundle
					.getString(BundleKeyValue.USER_LOGIN_NAME);
		}
		return username;

	}

	/**
	 * 设置账号功能 同时设置游客状态为非游客
	 * 
	 * @param value
	 */
	public static void setUsername(String value) {
		username = value;
		if (!StringUtils.isEmpty(value)) {
			setIsGuest(false);
		}
	}

	/**
	 * 获取用户密码信息
	 * 
	 * @return
	 */
	public static String getUserpasswd() {
		if (StringUtils.isEmpty(userpasswd)) {
			userpasswd = ViewParams.bundle
					.getString(BundleKeyValue.USER_LOGIN_PSW);
		}
		return userpasswd;
	}

	public static void setUserpasswd(String value) {
		userpasswd = value;
	}

	/**
	 * 保存用户信息
	 */
	public static void saveData() {

		SharedPreferenceUtils.setPreference(UserLoginControl.pName, keyName,
				username);
		SharedPreferenceUtils.setPreference(UserLoginControl.pName, keyPwd,
				userpasswd);
		SharedPreferenceUtils.setPreference(UserLoginControl.pName, keyUid,
				qwUserID);
	}

	/**
	 * 保存uid
	 */
	public static void saveUID() {

		SharedPreferenceUtils.setPreference(UserLoginControl.pName, keyUid,
				qwUserID);
	}

	/*************** kdsId ******************/
	private static String kdsId;

	private static final String kdsPName = "kdsPName";
	private static final String kdsKey = "kdsKey";

	/** 设置kdsId */
	public static void setKdsId(String id) {
		SharedPreferenceUtils.setPreference(kdsPName, kdsKey, id);
		kdsId = id;
	}

	/** 取kdsId */
	public static String getKdsId() {

		kdsId = SharedPreferenceUtils.getPreference(kdsPName, kdsKey, "");
		return kdsId;
	}

	/******************************************/
	/**
	 * 设置是否游客
	 * 
	 * @param isGuest
	 *            true游客，false非游客
	 */
	public static void setIsGuest(boolean isGuest) {
		if (isGuest) {// 是游客
			SharedPreferenceUtils.setPreference(UserLoginControl.IsGuestPName,
					UserLoginControl.IsGuestkeyName,
					UserLoginControl.IsGuestkeyName_True);
		} else {// 不是游客
			SharedPreferenceUtils.setPreference(UserLoginControl.IsGuestPName,
					UserLoginControl.IsGuestkeyName,
					UserLoginControl.IsGuestkeyName_Flase);
		}

	}

	/**
	 * 是否游客
	 * 
	 * @return true 游客，false，非游客
	 */
	public static boolean isGuest() {

		String value = SharedPreferenceUtils.getPreference(
				UserLoginControl.IsGuestPName, UserLoginControl.IsGuestkeyName,
				"");
		if (StringUtils.isEmpty(value)
				|| value.equals(UserLoginControl.IsGuestkeyName_True)) {
			return true;
		} else if (value.equals(UserLoginControl.IsGuestkeyName_Flase)) {
			return false;
		}
		return false;
	}

	public static void saveGuestName(String name) {
		SharedPreferenceUtils.setPreference(UserLoginControl.GuestPName,
				UserLoginControl.GuestkeyName, name);
	}

	/**
	 * @return 第一次进入为“”,
	 */
	public static String getGuestName() {
		String guestName = SharedPreferenceUtils.getPreference(
				UserLoginControl.GuestPName, UserLoginControl.GuestkeyName, "");
		return guestName;
	}

	/********* 注意，这里是保存kds的用户名和密码 *************************/

	/** 保存kds的用户名和密码 */
	public static void saveKdsUserData(String name, String psw) {
		SharedPreferenceUtils.setPreference(UserAccountValue.kdsUserDataPName,
				UserAccountValue.kdsUserKeyName, name);
		SharedPreferenceUtils.setPreference(UserAccountValue.kdsUserDataPName,
				UserAccountValue.kdsPswKeyName, psw);
	}

	/** 如果是第一次，则为“ ”空 */
	public static String getKdsUserName() {
		return SharedPreferenceUtils.getPreference(
				UserAccountValue.kdsUserDataPName,
				UserAccountValue.kdsUserKeyName, "");
	}

	private static String user_login_accounts = "";
	private final static String DATA_SAVE_LOGACCOUNT_KEY = "DATA_SAVE_LOGACCOUNT_KEY";
	private final static String DATA_SAVE_LOGACCOUNT_VL = "DATA_SAVE_LOGACCOUNT_VL";
	private static String[] split(String text, String expression) {
		return TextUtils.split(text, expression);
	}
	public static String[] get_user_login_accounts() {
		if (StringUtils.isEmpty(user_login_accounts)) {
			user_login_accounts = SharedPreferenceUtils.getPreference(
					DATA_SAVE_LOGACCOUNT_KEY, DATA_SAVE_LOGACCOUNT_VL, "");
			return split(user_login_accounts, ",");
		}
		return split(user_login_accounts, ",");

	}
	public static String[] get_old_shiji_user_login_accounts() {
		if (StringUtils.isEmpty(user_login_accounts)) {
			user_login_accounts = SharedPreferenceUtils.getPreference("mf_system_data", "KeyAccount","");
			return split(user_login_accounts, ",");
		}
		return split(user_login_accounts, ",");
		
	}
	public static String get_old_shiji_LatistAccount() {
		if (get_old_shiji_user_login_accounts().length <= 0) {
			return "";
		}
		return get_old_shiji_user_login_accounts()[0];
	}
	public static String getLatistAccount() {
		if (get_user_login_accounts().length <= 0) {
			return "";
		}
		return get_user_login_accounts()[0];
	}
	private static String rzrq_user_login_accounts = "";
	private final static String RZRQ_DATA_SAVE_LOGACCOUNT_KEY = "RZRQ_DATA_SAVE_LOGACCOUNT_KEY";
	private final static String RZRQ_DATA_SAVE_LOGACCOUNT_VL = "RZRQ_DATA_SAVE_LOGACCOUNT_VL";
	
	private static String[] rzrq_split(String text, String expression) {
		return TextUtils.split(text, expression);
	}
	public static String[] get_rzrq_old_shiji_user_login_accounts() {
		if (StringUtils.isEmpty(rzrq_user_login_accounts)) {
			rzrq_user_login_accounts =  SharedPreferenceUtils.getPreference("mf_system_data", "RzrqKeyAccount","");
			return rzrq_split(rzrq_user_login_accounts, ",");
		}
		return rzrq_split(rzrq_user_login_accounts, ",");
		
	}
	public static String get_old_shiji_LatistRZRQAccount() {
		if (get_rzrq_old_shiji_user_login_accounts().length <= 0) {
			return "";
		}
		return get_rzrq_old_shiji_user_login_accounts()[0];
	}
	public static String[] get_rzrq_user_login_accounts() {
		if (StringUtils.isEmpty(rzrq_user_login_accounts)) {
			rzrq_user_login_accounts = SharedPreferenceUtils.getPreference(
					RZRQ_DATA_SAVE_LOGACCOUNT_KEY,
					RZRQ_DATA_SAVE_LOGACCOUNT_VL, "");
			return rzrq_split(rzrq_user_login_accounts, ",");
		}
		return rzrq_split(rzrq_user_login_accounts, ",");

	}
	public static String getLatistRZRQAccount() {
		if (get_rzrq_user_login_accounts().length <= 0) {
			return "";
		}
		return get_rzrq_user_login_accounts()[0];
	}
	/**
	 * 获取下发的UserId，转换为byte型，用于ANetMsg()中值。
	 */
	public static byte[] longToByte() {
		long temp = UserAccount.qwUserID;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}
}
