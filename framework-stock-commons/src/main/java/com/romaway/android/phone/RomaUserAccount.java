package com.romaway.android.phone;

import android.app.Activity;
import android.text.TextUtils;

import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.umeng.socialize.net.stats.ShareboardStatsRequest;

import roma.romaway.commons.android.tougu.TouguUserInfo;

/**
 * 用户信息
 * 
 * @author chenjp
 * 
 */
public class RomaUserAccount {
	public static final String pName = "user_data";
	public static final String keyName = "key_name";
	public static final String keySignToken = "key_sign_token";
	public static final String mobileLoginNumber = "mobile_login_number";
	public static final String userId = "user_id";
	// 绑定的资金账号
	public static final String keyBindAccount = "key_bind_account";
	public static final String pBindAccount = "bind_account";
	
	/**
	 * 登陆认证令牌
	 */
	public static String signToken;

	/** 老版本的用户名,用来处理从老版本升级到新版本问题 */
	public static final String oldKeyName = "old_key_name";
	/** 老版本的密码,用来处理从老版本升级到新版本问题 */
	public static final String oldKeyPwd = "old_key_pwd";
	/**
	 * 老版本融资融券账号
	 */
	public static String oldRZRQAcount = "rzrqJZZH";
	/**
	 * 融资融券账号列表
	 */
	public static String oldRZRQAcountList = "rzrqLszhList";
	/**
	 * 老版本普通交易账号
	 */
	public static String oldPTJYAcount = "ptjyJZZH";
	/**
	 * 普通交易账号列表
	 */
	public static String oldPTJYAcountList = "ptjyLszhList";

	/**
	 * 清空各种用户信息，退出手机登录接口
	 */
	public static void clearUserAccountAllData(Activity activity){
		// 清空手机号的用户信息
		setFriendID("");
		setUsername("");
		setSignToken("");

		TouguUserInfo.clearUserAccount();
		JYStatusUtil.clearPtjyLoginOnNative(activity);
		JYStatusUtil.clearRzrqLoginOnNative(activity);
	}

	/**
	 * 设置老版本的用户名
	 * 
	 * @param value
	 */
	public static void setOldUsername(String value) {
		SharedPreferenceUtils.setPreference(pName, oldKeyName, value);
	}

	/**
	 * 获取老版本的用户名
	 * 
	 * @return
	 */
	public static String getOldUsername() {
		return SharedPreferenceUtils.getPreference(pName, oldKeyName, "");
	}

	/** 老版本的用户名,用来处理从老版本升级到新版本问题 */
	public static final String oldUserStockKeyName = "old_user_stock_key_name";

	/**
	 * 设置老版本的自选股数据
	 * 
	 * @param favors
	 */
	public static void setOldUserStock(String favors) {
		SharedPreferenceUtils.setPreference(pName, oldUserStockKeyName, favors);
	}

	/**
	 * 获取老版本的自选股
	 * 
	 * @return
	 */
	public static String getOldUserStock() {
		return SharedPreferenceUtils.getPreference(pName, oldUserStockKeyName,
				"");
	}

	/**
	 * 设置老版本的用户密码
	 * 
	 * @param value
	 */
	public static void setOldUserPwd(String value) {
		SharedPreferenceUtils.setPreference(pName, oldKeyPwd, value);
	}

	/**
	 * 获取老版本的用户密码
	 * 
	 * @return
	 */
	public static String getOldUserPwd() {
		return SharedPreferenceUtils.getPreference(pName, oldKeyPwd, "");
	}

	/**
	 * 设置用户名
	 *
	 * @param value
	 */
	public static void setUsername(String value) {
		SharedPreferenceUtils.setPreference(pName, keyName, value);
	}

	/**
	 * 获取用户名
	 *
	 * @return
	 */
	public static String getUsername() {
		return SharedPreferenceUtils.getPreference(pName, keyName, "");
	}

	/**
	 * 设置手机注册推荐人ID
	 * 
	 * @param friendID
	 */
	public static void setFriendID(String friendID) {
		SharedPreferenceUtils.setPreference(pName, "friendID", friendID);
	}

	/**
	 * 获取手机注册推荐人ID
	 * 
	 * @return
	 */
	public static String getFriendID() {
		return SharedPreferenceUtils.getPreference(pName, "friendID", "");
	}
	
	/**
	 * 是否是游客
	 * 
	 * @return
	 */
	public static boolean isGuest() {
		if (StringUtils.isEmpty(RomaUserAccount.getUserID().equals("0") ? "" : RomaUserAccount.getUserID())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否登录交易账号
	 * @return
	 */
	public static boolean isLoginTradingAccount() {
		return !TextUtils.isEmpty(RomaUserAccount.getTradeAccount());
	}

	/**
	 * 获取登陆认证令牌
	 * 
	 * @return
	 */
	public static String getSignToken() {
		return SharedPreferenceUtils.getPreference(pName, keySignToken, "");
	}

	/**
	 * 设置登陆认证令牌
	 * 
	 * @param signToken
	 */
	public static void setSignToken(String signToken) {
		SharedPreferenceUtils.setPreference(pName, keySignToken, signToken);
	}

	/**
	 * 获取H5交易登录保存的资金账号(最近登录资金账号)
	 */
	public static String getTradeAccount() {
		return getAccountAsTradeType("ptjyLszhList");
	}

	/**
	 * 获取H5交易登录保存的资金账号(最近登录资金账号)
	 */
	public static String getRZRQTradeAccount() {
		return getAccountAsTradeType("rzrqLszhList");
	}

	/**
	 * 获取H5交易登录保存的资金账号(最近登录资金账号)
	 * @param tradeType			普通交易/ptjyLszhList, 融资融券/rzrqLszhList, 普通交易记住/ptjyJZZH, 融资融券记住/rzrqJZZH;
	 * @return
     */
	public static String getAccountAsTradeType(String tradeType) {
		// ptjyLszhList : "["80316041"]"
		String account = "";
		String tmpAccount = SharedPreferenceUtils.getPreference(Res.getString(R.string.localName), tradeType, "");
		if(!TextUtils.isEmpty(tmpAccount)) {
			tmpAccount = tmpAccount.substring(tmpAccount.indexOf("[") + 1, tmpAccount.indexOf("]"));
			String[] accounts = tmpAccount.split(",");
			if (accounts.length > 0){
				account = accounts[accounts.length-1].replace("\"", "");
			}
		}
		return account;
	}

	/**
	 * 设置绑定的资金账号
	 * 
	 * @param bindAccount
	 */
	public static void setBindAccount(String bindAccount) {
		SharedPreferenceUtils.setPreference(pBindAccount, keyBindAccount,
				bindAccount);
	}

	/**
	 * 获取绑定的资金账号
	 */
	public static String getBindAccount() {
		return SharedPreferenceUtils.getPreference(pBindAccount,
				keyBindAccount, "");
	}

	/**
	 * 设置老版本融资融券账号
	 * @param account
	 */
	public static void setOldRZRQAcount(String account){
		SharedPreferenceUtils.setPreference(Res.getString(R.string.localName), oldRZRQAcount, account);
	}
	
	/**
	 * 获取老版本融资融券账号
	 * @return
	 */
	public static String getOldRZRQAcount(){
		return SharedPreferenceUtils.getPreference(Res.getString(R.string.localName), oldRZRQAcount, "");
	}
	
	/**
	 * 设置老版本普通交易账号
	 * @param account
	 */
	public static void setOldPTJYAcount(String account){
		SharedPreferenceUtils.setPreference(Res.getString(R.string.localName), oldPTJYAcount, account);
	}
	
	/**
	 * 获取老版本普通交易账号
	 * @return
	 */
	public static String getOldPTJYAcount(){
		return SharedPreferenceUtils.getPreference(Res.getString(R.string.localName), oldPTJYAcount, "");
	}
	
	/**
	 * 获取老版本融资融券账号列表
	 * @return
	 */
	public static String getOldRZRQAcountList() {
		return SharedPreferenceUtils.getPreference(Res.getString(R.string.localName), oldRZRQAcountList, "");
	}

	/**
	 * 设置老版本融资融券账号列表
	 * @param list
	 */
	public static void setOldRZRQAcountList(String[] list) {
		String accountList = "";
		for (int i = 0; i < list.length; i++) {
			accountList += "\"" + list[i] + "\",";
		}
		//去掉最后一个逗号
		accountList = accountList.substring(0, accountList.length() - 1);
		accountList = "[" + accountList + "]";
		Logger.d("oldVerToNewVersion", "===old rzrq account list===" + accountList);
		SharedPreferenceUtils.setPreference(Res.getString(R.string.localName), oldRZRQAcountList, accountList);
	}

	/**
	 * 获取老版本普通交易账号列表
	 * @return
	 */
	public static String getOldPTJYAcountList() {
		return SharedPreferenceUtils.getPreference(Res.getString(R.string.localName), oldPTJYAcountList, "");
	}

	/**
	 * 设置老版本普通交易账号列表
	 * @param list
	 */
	public static void setOldPTJYAcountList(String[] list) {
		String accountList = "";
		for (int i = 0; i < list.length; i++) {
			accountList += "\"" + list[i] + "\",";
		}
		//去掉最后一个逗号
		accountList = accountList.substring(0, accountList.length() - 1);
		accountList = "[" + accountList + "]";
		Logger.d("", "===old ptjy account list===" + accountList);
		SharedPreferenceUtils.setPreference(Res.getString(R.string.localName), oldPTJYAcountList, accountList);
	}

	/**
	 * 设置用户ID
	 *
	 * @param value
	 */
	public static void setUserID(String value) {
		SharedPreferenceUtils.setPreference(pName, userId, value);
	}

	/**
	 * 获取用户名
	 *
	 * @return
	 */
	public static String getUserID() {
		return SharedPreferenceUtils.getPreference(pName, userId, "0");
	}

	/**
	 * 设置微信登录标志
	 */
	public static void setWxLogin(boolean wxLogin) {
		SharedPreferenceUtils.setPreference("wxLogin", "wxLogin", wxLogin);
	}

	/**
	 * 获取微信登录标志
	 */
	public static boolean getWxLogin() {
		return SharedPreferenceUtils.getPreference("wxLogin", "wxLogin", false);
	}

	/**
	 * 设置手机号
	 */
	public static void setMobileLoginNumber(String mobileLoginNumber) {
		SharedPreferenceUtils.setPreference("mobileLoginNumber", "mobileLoginNumber", mobileLoginNumber);
	}

	/**
	 * 获取手机号
	 */
	public static String getMobileLoginNumber() {
		return SharedPreferenceUtils.getPreference("mobileLoginNumber", "mobileLoginNumber", "");
	}

	/**
	 * 设置微信用户信息
	 */
	public static void setWxUserInfo(String wxUserInfo) {
		SharedPreferenceUtils.setPreference("wxUserInfo", "wxUserInfo", wxUserInfo);
	}

	/**
	 * 获取微信用户信息
	 */
	public static String getWxUserInfo() {
		return SharedPreferenceUtils.getPreference("wxUserInfo", "wxUserInfo", "");
	}

	/**
	 * 设置微信昵称
	 */
	public static void setWxName(String wxName) {
		SharedPreferenceUtils.setPreference("wxUserInfo", "wxName", wxName);
	}

	/**
	 * 获取微信昵称
	 */
	public static String getWxName() {
		return SharedPreferenceUtils.getPreference("wxUserInfo", "wxName", "");
	}

	/**
	 * 设置用户类别  0:用户,1:销售,2:管理,3:技术
	 * @param userType
     */
	public static void setUserType(String userType) {
		SharedPreferenceUtils.setPreference("userType", "userType", userType);
	}

	public static String getUserType() {
		return SharedPreferenceUtils.getPreference("userType", "userType", "");
	}

	/**
	 * 清空用户数据，退出
	 */
	public static void clear() {
		setUsername("");
		setWxName("");
		setMobileLoginNumber("");
		setWxUserInfo("");
		setUserID("0");
		setUserType("");
		RomaSysConfig.setUserImageUrl("");
		RomaSysConfig.setHistorySum("");
		RomaSysConfig.setHistoryDataMonth("");
		RomaSysConfig.setWxImageUrl("");
//		setToken("");
		setWxLogin(false);
		setClearUserInfo(true);
		setShareTimes(0);
		setIsVip(0);
		setQuestionUserType("");
	}

	/**
	 * 判断点击了安全退出
	 */
	public static boolean isClearUserInfo() {
		return clearUserInfo;
	}

	public static void setClearUserInfo(boolean clearUserInfo) {
		RomaUserAccount.clearUserInfo = clearUserInfo;
	}

	private static boolean clearUserInfo = false;

	public static void setToken(String token) {
		SharedPreferenceUtils.setPreference("token", "token", token);
	}

	public static String getToken() {
		return SharedPreferenceUtils.getPreference("token", "token", "");
	}

	public static void setShareTimes(int times) {
		SharedPreferenceUtils.setPreference("times", "times", times);
	}

	public static int getShareTimes() {
		return SharedPreferenceUtils.getPreference("times", "times", 0);
	}

	public static void setIsVip(int is_vip) {
		SharedPreferenceUtils.setPreference("vip", "vip", is_vip);
	}

	public static int getIsVip() {
		return SharedPreferenceUtils.getPreference("vip", "vip", 0);
	}

	public static void setIsShowEndDate(int showEndDate) {
		SharedPreferenceUtils.setPreference("showEndDate", "showEndDate", showEndDate);
	}

	public static int getIsShowEndDate() {
		return SharedPreferenceUtils.getPreference("showEndDate", "showEndDate", 0);
	}

	public static void setQuestionUserType(String question_user_type) {
		SharedPreferenceUtils.setPreference("question_user_type", "question_user_type", question_user_type);
	}

	public static String getQuestionUserType() {
		return SharedPreferenceUtils.getPreference("question_user_type", "question_user_type", "");
	}

	public static void setUnLookMsg(String unLookMsg) {
		SharedPreferenceUtils.setPreference("unLookMsg", "unLookMsg", unLookMsg);
	}

	public static String getUnLookMsg() {
		return SharedPreferenceUtils.getPreference("unLookMsg", "unLookMsg", "");
	}

	/**
	 * 设置用户性别
	 *
	 * @param userSex
	 */
	public static void setUserSex(String userSex) {
		SharedPreferenceUtils.setPreference("userSex", "userSex", userSex);
	}

	/**
	 * 获取用户性别
	 *
	 * @return
	 */
	public static String getUserSex() {
		return SharedPreferenceUtils.getPreference("userSex", "userSex", "0");
	}

	/**
	 * 设置用户年龄
	 *
	 * @param userAge
	 */
	public static void setUserAge(String userAge) {
		SharedPreferenceUtils.setPreference("userAge", "userAge", userAge);
	}

	/**
	 * 获取用户年龄
	 *
	 * @return
	 */
	public static String getUserAge() {
		return SharedPreferenceUtils.getPreference("userAge", "userAge", "0");
	}

	/**
	 * 设置定位省份
	 *
	 * @param province
	 */
	public static void setProvince(String province) {
		SharedPreferenceUtils.setPreference("province", "province", province);
	}

	/**
	 * 获取定位省份
	 *
	 * @return
	 */
	public static String getProvince() {
		return SharedPreferenceUtils.getPreference("province", "province", "");
	}

	/**
	 * 设置定位城市
	 *
	 * @param city
	 */
	public static void setCity(String city) {
		SharedPreferenceUtils.setPreference("city", "city", city);
	}

	/**
	 * 获取定位城市
	 *
	 * @return
	 */
	public static String getCity() {
		return SharedPreferenceUtils.getPreference("city", "city", "");
	}

	/**
	 * 设置投资风格
	 *
	 * @param userStyle
	 */
	public static void setUserStyle(String userStyle) {
		SharedPreferenceUtils.setPreference("userStyle", "userStyle", userStyle);
	}

	/**
	 * 获取投资风格
	 *
	 * @return
	 */
	public static String getUserStyle() {
		return SharedPreferenceUtils.getPreference("userStyle", "userStyle", "0");
	}

	/**
	 * 设置投资风格
	 *
	 * @param userMoney
	 */
	public static void setUserMoney(String userMoney) {
		SharedPreferenceUtils.setPreference("userMoney", "userMoney", userMoney);
	}

	/**
	 * 获取投资风格
	 *
	 * @return
	 */
	public static String getUserMoney() {
		return SharedPreferenceUtils.getPreference("userMoney", "userMoney", "0");
	}

}
