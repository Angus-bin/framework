package com.romaway.android.phone.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.Configs;
import com.romaway.common.android.base.Res;
import com.romaway.commons.log.Logger;

import roma.romaway.commons.android.webkit.WebkitSherlockFragment;

public class JYStatusUtil {

	/**
	 * 交易未登录状态
	 */
	public static final int JY_LOGIN_STATUS_NONE = 0;
	/**
	 * 交易登录状态
	 */
	public static final int JY_LOGIN_STATUS_OK = 1;
	/**
	 * 融资融券登录状态
	 */
	public static final int RZRQ_LOGIN_STATUS_NONE = 10;
	/**
	 * 融资融券登录状态
	 */
	public static final int RZRQ_LOGIN_STATUS_OK = 11;
	/** 普通交易登录类型 */
	public static final String JY_LOGIN_TYPE = "ptjy";
	/** 融资融券登录类型 */
	public static final String RZRQ_LOGIN_TYPE = "rzrq";
	/** 普通交易风险等级类型*/
	public static final String JY_LOGIN_RISK_TYPE = "ptjy_risk";
	/** 融资融券风险等级类型*/
	public static final String RZRQ_LOGIN_RISK_TYPE = "rzrq_risk";

	public static String loginType = JY_LOGIN_TYPE;
	public static int loginStatus = JY_LOGIN_STATUS_NONE;

	private Context mContext;

	private WebView webView;
	
	/**
	 * 当前交易首页Fragment
	 */
	public static WebkitSherlockFragment ptjyHomePageFragment = null;
	
	public static WebkitSherlockFragment currentRzrqFragment = null;
	/**
	 * 是否切换了普通交易地址
	 */
	public static boolean isChangePTJYUrl = false;
	/**
	 * 是否切换了普通交易地址
	 */
	public static boolean isChangeRZRQUrl = false;

	/**
	 * 是否第一次启动清理过登录信息
	 */
	public static boolean isClearLoginInfo = false;
	
	@SuppressLint("NewApi")
	public JYStatusUtil(Context context, boolean isSupSetting) {
		mContext = context;
	}

	/**
	 * 设置 level2 权限
	 * @param s
     */
	public static void setLevel(String s) {
		if (ptjyHomePageFragment != null)
			ptjyHomePageFragment.getKdsWebView().loadJsMethodUrl("javascript:setLevel('"+ s +"')");
	}

	public interface OnLoginAccountListener {
		public void onLoginAccount(int status, String loginAccount);
	}

	public static OnLoginAccountListener mOnLoginAccountListener = null;

	public void setOnLoginAccountListener(
			OnLoginAccountListener onLoginAccountListener) {
		this.setOnLoginAccountListener(onLoginAccountListener, JY_LOGIN_TYPE);
	}

	public void setOnLoginAccountListener(
			OnLoginAccountListener onLoginAccountListener, String tradeLoginType) {
		mOnLoginAccountListener = onLoginAccountListener;
		loginType = tradeLoginType;
		resetLoginAccount();
	}

	/** 注册手机号, 传递手机号码给交易网页模块(要求主界面交易模块已启动) */
	public void registerPhoneNumAccount() {
		Logger.d("JavascriptInterface", "[交易状态]: 注册手机号, 传递手机号码给交易网页模块");
		try {
			String telephone = RomaUserAccount.getUsername();
			if (ptjyHomePageFragment != null) {
				ptjyHomePageFragment.getKdsWebView().loadJsMethodUrl(
						"javascript:setIsRegister('" + telephone + "')");// 传递手机号码给交易网页模块
			} else if (currentRzrqFragment != null) {
				currentRzrqFragment.getKdsWebView().loadJsMethodUrl(
						"javascript:setIsRegister('" + telephone + "')");// 传递手机号码给交易网页模块
			}
		} catch (Exception e) {
			Logger.e("JYStatusUtil", e.getMessage());
		}
	}

	public void resetLoginAccount() {
		Logger.d("JavascriptInterface", "[交易状态]:开始获取交易状态");
		try{
			if(RZRQ_LOGIN_TYPE.equals(loginType)){
				if (currentRzrqFragment != null) {
					currentRzrqFragment.getKdsWebView().loadJsMethodUrl(
							"javascript:getLoginAccount('"+ RZRQ_LOGIN_TYPE +"')");
				} else {
					loginStatus = RZRQ_LOGIN_STATUS_NONE;
					Logger.d("JavascriptInterface", "[交易状态]:交易模块暂未启动！");
					if (mOnLoginAccountListener != null) {
						Logger.d("JavascriptInterface", "[交易状态]:已设置状态监听器!");
						mOnLoginAccountListener.onLoginAccount(loginStatus, null);
					}
				}
			}else{
				if (ptjyHomePageFragment != null) {
					ptjyHomePageFragment.getKdsWebView().loadJsMethodUrl(
							"javascript:getLoginAccount('"+ JY_LOGIN_TYPE +"')");
				} else {
					loginStatus = JY_LOGIN_STATUS_NONE;
					Logger.d("JavascriptInterface", "[交易状态]:交易模块暂未启动！");
					if (mOnLoginAccountListener != null) {
						Logger.d("JavascriptInterface", "[交易状态]:已设置状态监听器!");
						mOnLoginAccountListener.onLoginAccount(loginStatus, null);
					}
				}
			}
		}catch(Exception e){
			
		}
	}

	/**
	 * 可用于退出交易登录
	 */
	@SuppressLint("NewApi")
	public static void clearLoginInfo() {
		// 通知交易模块退出普通交易登录:
		clearPtjyLoginInfo();

		// 通知交易模块退出融资融券交易登录:
		clearRzrqLoginInfo();
	}

	/** 通知交易模块退出普通交易登录 */
	public static void clearPtjyLoginInfo(){
		if (ptjyHomePageFragment != null) {
			Logger.d("JavascriptInterface", "[交易状态]:执行清空操作:"+ptjyHomePageFragment.getKdsWebView().getUrl());
			ptjyHomePageFragment.getKdsWebView().loadJsMethodUrl(
					"javascript:ClearLoginInfo()");
		}
	}

	/** 通知交易模块退出融资融券交易登录 */
	public static void clearRzrqLoginInfo(){
		if(currentRzrqFragment != null){
			Logger.d("JavascriptInterface", "[交易状态]:执行清空操作:"+currentRzrqFragment.getKdsWebView().getUrl());
			currentRzrqFragment.getKdsWebView().loadJsMethodUrl(
					"javascript:ClearLoginInfo()");
		}
	}

	/** 退出普通交易登录, 刷新界面 */
	public static void clearPtjyLoginOnNative(Activity mActivity){
		// 通知交易模块退出普通交易登录:
		clearPtjyLoginInfo();

		// 通知原生刷新回普通交易 未登录交易界面:
		clearNativePtjyLogin(mActivity);
	}

	/**
	 * 通知原生刷新回普通交易 未登录交易界面:
	 * @param mActivity
     */
	public static void clearNativePtjyLogin(Activity mActivity) {
		// 发送退出普通交易登录广播:
		String myurl = "";
		Intent jyIntent = new Intent(
				"action." + mActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl");
		jyIntent.putExtra("resultUrl", myurl);
		jyIntent.putExtra("resetLoadFlag", true);
		// 发送广播
		mActivity.sendBroadcast(jyIntent);
	}

	/** 退出融资融券交易登录, 刷新界面 */
	public static void clearRzrqLoginOnNative(Activity mActivity){
		// 通知交易模块退出融资融券交易登录:
		clearRzrqLoginInfo();

		// 通知原生刷新回融资融券 未登录交易界面:
		clearNativeRzrqLogin(mActivity);
	}

	/**
	 * 通知原生刷新回融资融券 未登录交易界面:
	 * @param mActivity
     */
	private static void clearNativeRzrqLogin(Activity mActivity) {
		// 发送退出融资融券交易登录广播:
		String rzrqUrl = Configs.getJiaoYiUrl(mActivity,
				"/kds519/view/rzrq/home/rzrq_header.html");
		Intent rzrqIntent = new Intent("action." + mActivity.getPackageName() + ".rzrq.homepage.resetLoadUrl");
		rzrqIntent.putExtra("resultUrl", rzrqUrl);
		rzrqIntent.putExtra("resetLoadFlag", true);
		mActivity.sendBroadcast(rzrqIntent);
	}

	/** 通知交易模块退出普通交易登录 */
	public static void changeJYModeSkin(String themeName){
		if (TextUtils.isEmpty(themeName))
			themeName = "";
		if (ptjyHomePageFragment != null) {
			Logger.d("JavascriptInterface", "[普通交易]:执行换肤操作: setAppTheme -> " + themeName);
			ptjyHomePageFragment.getKdsWebView().loadJsMethodUrl(
					"javascript:setAppTheme('"+ themeName +"')");
		}
		if(currentRzrqFragment != null){
			Logger.d("JavascriptInterface", "[融资融券]:执行换肤操作: setAppTheme -> " + themeName);
			currentRzrqFragment.getKdsWebView().loadJsMethodUrl(
					"javascript:setAppTheme('"+ themeName +"')");
		}
	}

	/**
	 * 实时刷新指定交易模块主页状态(已登录/未登录页面)
	 * @param tradeLoginType	普通交易/融资融券
     */
	public void syncRefreshJYHomePage(final String tradeLoginType){
		setOnLoginAccountListener(new JYStatusUtil.OnLoginAccountListener(){
			@Override
			public void onLoginAccount(int status, String loginAccount) {
				if (RZRQ_LOGIN_TYPE.equalsIgnoreCase(tradeLoginType)) {
					if (mContext instanceof SherlockFragmentActivity) {
						if (status == JYStatusUtil.RZRQ_LOGIN_STATUS_OK) {
							refreshNativeRzrqLogin((SherlockFragmentActivity) mContext);
						} else {
							clearNativeRzrqLogin((SherlockFragmentActivity) mContext);
						}
					}
				} else {
					if (mContext instanceof SherlockFragmentActivity) {
						if (status == JY_LOGIN_STATUS_OK) {
							refreshNativePtjyLogin((SherlockFragmentActivity) mContext);
						} else {
							clearNativePtjyLogin((SherlockFragmentActivity) mContext);
						}
					}
				}
			}
		}, tradeLoginType);
	}

	/**
	 * 通知原生刷新为普通交易 已登录交易界面:
	 * @param mActivity
	 */
	public static void refreshNativePtjyLogin(SherlockFragmentActivity mActivity) {
		// 发送广播,同时刷新主界面
		Intent mIntent = new Intent(
				"action." + mActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl");
		mIntent.putExtra("resultUrl", Configs.getJiaoYiUrl(mActivity,
				"/kds519/view/ptjy/home/ptjy_index.html"));
		mIntent.putExtra("resetLoadFlag", true);
		mActivity.sendBroadcast(mIntent);
	}

	/**
	 * 通知原生刷新为融资融券 已登录交易界面:
	 * @param mActivity
	 */
	public static void refreshNativeRzrqLogin(SherlockFragmentActivity mActivity) {
		// 发送广播,同时刷新主界面
		Intent mIntent = new Intent("action." + mActivity.getPackageName() + ".rzrq.homepage.resetLoadUrl");
		mIntent.putExtra("resultUrl", Configs.getJiaoYiUrl(mActivity,
				"/kds519/view/rzrq/home/rzrq_index.html"));
		mIntent.putExtra("resetLoadFlag", true);
		// 发送广播
		mActivity.sendBroadcast(mIntent);
	}
}
