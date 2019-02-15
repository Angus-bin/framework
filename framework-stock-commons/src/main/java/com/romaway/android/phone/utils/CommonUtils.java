package com.romaway.android.phone.utils;

import java.lang.reflect.Method;
import java.security.MessageDigest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.Toast;

import com.romaway.android.phone.RomaUserAccount;
import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.framework.view.SysInfo;

/** 通用方法工具类 */
public class CommonUtils {

	private final static String TAG = "CommonUtils";
	/** 
	 * 调用在线客服服务
	 * @param mActivity	用于创建触发在线客服的WebView
	 * @param webUrl	券商客服企业QQ号的Url地址
	 */
	public static void callOnlineService(Context mActivity, String webUrl) {
		WebView webView = new WebView(mActivity);
		webView.getSettings().setJavaScriptEnabled(true);
		String qqPackageName = "com.tencent.mobileqq";
		if (YBHelper.hasInstallApp(OriginalContext.getContext(), qqPackageName)) {
			if (webView != null)
				webView.loadUrl(webUrl);
		} else {
			SysInfo.showMessage(OriginalContext.getContext(),
					Res.getString(R.string.roma_commons_qq_uninstall_notice));
			if(executeListener != null)
				executeListener.onFailure();
		}
	}
	
	public static String getFragmentArgument(Activity current, String pageName, String[] pageInfos) {
		try {
			Class<?> youWenUtils = Class.forName("com.szkingdom.android.phone.utils.YouWenUtils");
			Method method = youWenUtils.getMethod("getFragmentArgument", 
					new Class[]{ Activity.class, String.class, String[].class });
			return (String)method.invoke(null, new Object[]{ current, pageName, pageInfos });
		} catch (Exception e) {
			if(Logger.getDebugMode())
				Toast.makeText(current, "请检查应用配置, 当前券商版本不支持此功能", 0).show();
		}
		return "";
	}
	
	
	public static void callYouWenPage(Activity current, String pageName, String[] pageInfos) {
		try {
			Class<?> youWenUtils = Class
					.forName("com.szkingdom.android.phone.utils.YouWenUtils");
			Method callYouWenPage = youWenUtils.getMethod("callYouWenPage", 
					new Class[] { Activity.class, String.class, String[].class });
			callYouWenPage.invoke(null, new Object[] {current, pageName, pageInfos});
		} catch (Exception e) {
			if(Logger.getDebugMode())
				Toast.makeText(current, "请检查应用配置, 当前券商版本不支持此功能", 0).show();
		}
	}
	
	// 公共回调:
	private static onExecuteListener executeListener;
	public static void setExecuteListener(onExecuteListener mExecuteListener) {
		executeListener = mExecuteListener;
	}
	public static onExecuteListener getExecuteListener() {
		return executeListener;
	}
	
	public interface onExecuteListener{
		public void onSucceed();
		public void onFailure();
	}
	
	/** 
	 * 获取Android应用32位签名(用于微信/新浪分享等);
	 * @dec 使用获取请打包签名, 否则获取到的为debug签名(例:e43af425609721a9e4b1647d1b43d982)
	 * @param context		
	 * @param packageName	已签名的应用包名
	 */
	public static String getSignKey(Context context, String packageName) {
		Signature[] arrayOfSignature = getRawSignature(context, packageName);
		if ((arrayOfSignature == null) || (arrayOfSignature.length == 0)){
			Logger.d(TAG, "signs is null");
			return "";
		}

		String signKey = getMessageDigest(arrayOfSignature[0].toByteArray());
		System.out.println(packageName + " 应用签名Sign Key is : " + signKey);
		return signKey;
	}

	private static Signature[] getRawSignature(Context paramContext, String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			Logger.d(TAG, "getSignature, packageName is null");
			return null;
		}
		PackageManager localPackageManager = paramContext.getPackageManager();
		PackageInfo localPackageInfo;
		try {
			localPackageInfo = localPackageManager.getPackageInfo(paramString,
					64);
			if (localPackageInfo == null) {
				Logger.d(TAG, "info is null, packageName = " + paramString);
				return null;
			}
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			Logger.e(TAG, "NameNotFoundException");
			return null;
		}
		return localPackageInfo.signatures;
	}
	
	public static final String getMessageDigest(byte[] paramArrayOfByte) {
		char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98,
				99, 100, 101, 102 };
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			int i = arrayOfByte.length;
			char[] arrayOfChar2 = new char[i * 2];
			int j = 0;
			int k = 0;
			while (true) {
				if (j >= i)
					return new String(arrayOfChar2);
				int m = arrayOfByte[j];
				int n = k + 1;
				arrayOfChar2[k] = arrayOfChar1[(0xF & m >>> 4)];
				k = n + 1;
				arrayOfChar2[n] = arrayOfChar1[(m & 0xF)];
				j++;
			}
		} catch (Exception localException) {
		}
		return null;
	}

	public static final byte[] getRawDigest(byte[] paramArrayOfByte) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			return arrayOfByte;
		} catch (Exception localException) {
		}
		return null;
	}

	/**
	 * 混淆手机号码显示:
	 * @param phoneNum 13800002003
	 * @return		   138****0203
	 */
	public static String encryptPhoneNum(String phoneNum){
		if(TextUtils.isEmpty(phoneNum) || phoneNum.length() < 11){
			return phoneNum;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(phoneNum.substring(0, 3)).append("****").append(phoneNum.substring(7, 11));
		return sb.toString();
	}

	/**
	 * 获取我要开户Web页面加载地址(建投, 无下发则取本地默认配置)
	 * @return
     */
	public static String getLoadOpenAccountWebUrl(){
		return "";
	}

	/**
	 * 获取中台初始化配置下发加载地址(建投, 无下发则取本地默认配置)
	 * @param paramsKey		中台下发Key
	 * @param resId			本地默认配置id
	 * @return				中台下发对应的Value
	 */
	public static String getLoadCommonConfig(String paramsKey, int resId){
		// 通过认证下发参数配置获得的:
		String loadConfigWebUrl = StringUtils.optString(RomaSysConfig.getParamsValue(paramsKey));
//		try {
//			if (TextUtils.isEmpty(loadConfigWebUrl))
//				loadConfigWebUrl = Res.getString(resId);
//		}catch (Exception e){
//			Logger.e(TAG, e.getMessage());
//		}
		return loadConfigWebUrl;
	}

	/**
	 * 获取中台初始化配置下发加载地址(建投, 无下发则取本地默认配置)
	 * @param paramsKey		中台下发Key
	 * @param resId			本地默认配置id
     * @return				加载WebUrl地址
     */
	public static String getLoadCommonConfigUrl(String paramsKey, int resId){
		// 通过认证下发参数配置获得的:
		String loadConfigWebUrl = getLoadCommonConfig(paramsKey, resId);
		return addAppTypeToTargetUrl(loadConfigWebUrl);
	}

	/**
	 * 给目标 targetUrl 地址添加appType类型, 当遇到锚点类型地址时需添加在锚点"#"前:
	 * @param targetUrl		原目标地址
	 * @return				添加appType后目标地址
	 */
	public static String addAppTypeToTargetUrl(String targetUrl){
		return addAppTypeToTargetUrl(targetUrl, new String[]{"appType", "accountMobileno"}, new String[]{"android", RomaUserAccount.getUsername()});
	}

	/**
	 * 给目标 targetUrl 地址添加appType类型, 当遇到锚点类型地址时需添加在锚点"#"前:
	 * @param targetUrl		原目标地址
	 * @return				添加appType后目标地址
	 */
	public static String addAppTypeToTargetUrl(String targetUrl, String[] keys, String[] values){
		if (TextUtils.isEmpty(targetUrl))
			return "";
		Logger.v("TAG", "addAppTypeToTargetUrl前: " + targetUrl);
		String appendStr = "";
		if (keys.length > 0 && values.length > 0 && keys.length == values.length){
			for (int i = 0; i < keys.length; i++) {
				if (i == 0)
					appendStr += keys[i] + "=" + values[i];
				else
					appendStr += "&" + keys[i] + "=" + values[i];
			}
		}
		if (!targetUrl.contains(appendStr)){
			if (targetUrl.contains("#")){
				StringBuilder sb = new StringBuilder();
				sb.append(targetUrl.substring(0, targetUrl.lastIndexOf("#")));

				if (sb.toString().contains("?"))
					sb.append("&").append(appendStr);
				else
					sb.append("?").append(appendStr);

				sb.append(targetUrl.substring(targetUrl.lastIndexOf("#"), targetUrl.length()));

				targetUrl = sb.toString();
			}else{
				if (targetUrl.contains("?"))
					targetUrl += ("&" + appendStr) ;
				else
					targetUrl += ("?" + appendStr);
			}
		}
		Logger.v("TAG", "addAppTypeToTargetUrl后: " + targetUrl);
		return targetUrl;
	}

	/**
	 * 根据功能 funKey 获取对应的中台/默认配置的 portalUrl、targetUrl 地址
	 * @param funKey		ROMA_TICKET_NO 类型的 功能Key
	 * @return				new String[]{portalUrl, targetUrl}
     */
	public static String[] getLoadCommonConfigUrls(String funKey) {
		int portalUrlId = -1, targetUrlId = -1;
		if ("KDS_TICKET_NO_XGJH".equalsIgnoreCase(funKey)) {
			portalUrlId = R.string.KDS_TICKET_NO_XGJH_PortalUrl;
			targetUrlId = R.string.KDS_TICKET_NO_XGJH_TargetUrl;
		} else if ("ROMA_TICKET_NO_TGCP".equalsIgnoreCase(funKey)) {
			portalUrlId = R.string.KDS_TICKET_NO_TGCP_PortalUrl;
			targetUrlId = R.string.KDS_TICKET_NO_TGCP_TargetUrl;
		} else if ("ROMA_TICKET_NO_GMJJ".equalsIgnoreCase(funKey)) {
			portalUrlId = R.string.KDS_TICKET_NO_GMJJ_PortalUrl;
			targetUrlId = R.string.KDS_TICKET_NO_GMJJ_TargetUrl;
		} else if ("KDS_PHONE_TICKET_NO_LEVEL2_FREE".equalsIgnoreCase(funKey)) {
			portalUrlId = R.string.KDS_PHONE_TICKET_NO_LEVEL2_FREE_PortalUrl;
			targetUrlId = R.string.KDS_PHONE_TICKET_NO_LEVEL2_FREE_TargetUrl;
		} else if ("KDS_PHONE_TICKET_NO_LEVEL2_BUY".equalsIgnoreCase(funKey)) {
			portalUrlId = R.string.KDS_PHONE_TICKET_NO_LEVEL2_BUY_PortalUrl;
			targetUrlId = R.string.KDS_PHONE_TICKET_NO_LEVEL2_BUY_TargetUrl;
		}
		return new String[]{CommonUtils.getLoadCommonConfigUrl(funKey + "_PortalUrl", portalUrlId),
							CommonUtils.getLoadCommonConfigUrl(funKey + "_TargetUrl", targetUrlId)};
	}
}
