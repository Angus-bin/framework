package com.romaway.android.phone.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.android.phone.widget.RomaDialog;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;

public class YBHelper {

	/**
	 * 检查手机密令是否安装
	 */
	public static boolean checkMilingExist(Activity context) {

		boolean result = false;

		try {
			Intent intent = new Intent();
			intent.setClassName("yibao.baoling", "otp.yb.PublicAPIActivity");
			if (context.getPackageManager().resolveActivity(intent, 0) != null) {
				result = true;
			}

		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 获取签名
	 */
	public static String getSign(String spid, String spkey, String reqtype) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		String date = sdf.format(new java.util.Date());
		String sourceStr = String
				.format("%s%s%s%s", spid, date, spkey, reqtype);
		String signStr = "";

		try {
			signStr = YBHelper.getSHA(sourceStr);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signStr;
	}

	/**
	 * 计算签名与返回的结果中的签名进行比对
	 */
	public static boolean checkResultSign(String sign, String otp, String spkey) {

		boolean result = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		String date = sdf.format(new java.util.Date());
		String sourceStr = String.format("%s%s%s", otp, date, spkey);
		String signStr = "";

		try {
			signStr = YBHelper.getSHA(sourceStr);
		} catch (NoSuchAlgorithmException e) {
		}

		result = signStr.equalsIgnoreCase(sign);
		if (!result) {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
			c.add(Calendar.MINUTE, -1);
			String date1 = df.format(c.getTime());
			String sourceStr1 = String.format("%s%s%s%s", otp, date1, spkey);
			String signStr1 = "";
			try {
				signStr1 = YBHelper.getSHA(sourceStr1);
			} catch (NoSuchAlgorithmException e) {
			}
			result = signStr1.equalsIgnoreCase(sign);
		}

		return result;
	}


	/**
	 * sha1 算法生成签名
	 */
	public static String getSHA(String val) throws NoSuchAlgorithmException {
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.update(val.getBytes());
		byte[] m = sha1.digest();//
		return getString(m);
	}

	private static String getString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
		}
		return sb.toString();
	}
	
	/**
	 * 检测是否安装某应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean hasInstallApp(Context context, String packageName){ 
//		 final PackageManager packageManager = context.getPackageManager();//获取packagemanager
//		 List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
//		 List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
//		 //从pinfo中将包名字逐一取出，压入pName list中
//		 if(pinfo != null){
//			 for(int i = 0; i < pinfo.size(); i++){
//				 String pn = pinfo.get(i).packageName;
//				 pName.add(pn);
//			 }
//		 }
//		 return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE

		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			return packageInfo != null;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 启动指定应用, 如未安装则下载;
	 * @param context
	 * @param packageName	应用包名;
	 * @param downloadUrl	下载地址;
	 */
	public static void launchOrDownApp(final Context context, String packageName, final String downloadUrl){
		if(TextUtils.isEmpty(packageName) || TextUtils.isEmpty(downloadUrl)) {
			RomaToast.showMessage(context, "找不到相关应用！");
			return;
		}

		if (!hasInstallApp(context, packageName)) {
			// 未安装, 下载确认：
				// 开户App提示框个性化配色:
				Dialog mDialog = DialogFactory.getIconDialog(context,
						Res.getString(R.string.roma_tips_title),
						Res.getString(R.string.roma_tips_message),
						DialogFactory.DIALOG_TYPE_QUESTION,
						null, null, Res.getString(R.string.roma_download_btn_text),
						new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {
								downApk(context, downloadUrl);
							}
						});
				mDialog.show();
//				mDialog.setRightButtonText(Res.getString(R.string.roma_download_btn_text));
//				mDialog.setPaintRightBtnColor(SkinManager.getColor("CustomConfirmBtnColor"));
//				mDialog.setPaintRightBtnPressedColor(SkinManager.getColor("CustomConfirmBtnPressedColor"));
//				mDialog.setPaintRightBtnFocusedColor(SkinManager.getColor("CustomConfirmBtnFocusedColor"));
		} else {
			launchApk(context, packageName);
		}
	}

	/**
	 * 调用浏览器下载安装()
	 * @param url		下载地址，以http://开头
	 */
	public static void downApk(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(intent);
	}

	/**
	 * 启动指定包名的第三方应用
	 * @param context
	 * @param packageName	启动的第三方应用包名
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void launchApk(Context context, String packageName) {
		Intent intent = new Intent();
		PackageManager packageManager = context.getPackageManager();
		intent = packageManager.getLaunchIntentForPackage(packageName);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}
