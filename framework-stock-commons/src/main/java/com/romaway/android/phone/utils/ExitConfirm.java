/**
 * 
 */
package com.romaway.android.phone.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.romaway.activity.basephone.ActivityStackMgr;
import com.romaway.android.phone.RomaAgentMgr;
import com.romaway.android.phone.R;
import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.LogcatHelper;
import com.romaway.commons.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import cn.magicwindow.Session;

/**
 * @author duminghui
 * 
 */
public class ExitConfirm
{
	public static final void confirmExit(final Context context)
	{
		//友盟统计接口方法，如果开发者调用 Process.kill 或者 System.exit 之类的方法杀死进程，
		//请务必在此之前调用此方法，用来保存统计数据
		RomaAgentMgr.onKillProcess(context);

		Dialog imageDialog = DialogFactory.getIconDialog(context, Res.getString(R.string.roma_out_app_dialog_title),
				StringUtils.replaceString(Res.getString(R.string.roma_exit_dialog_content), "@appName", Res.getString(R.string.app_name)),
				DialogFactory.DIALOG_TYPE_NO_ICON, null, new OnClickDialogBtnListener() {
					@Override
					public void onClickButton(View view) {

					}
				}, null, new OnClickDialogBtnListener() {
					@Override
					public void onClickButton(View view) {
						finishProcess(context);
					}
				});
		imageDialog.show();
	}

	/**
	 * 结束整个进程
	 * @param context
	 */
	public static void finishProcess(Context context){
		try {
			// [Bug] 修复在M811设备上调用JNI nativeRemoveAllCookie()方法因WebView未创建过, 发生IllegalStateException异常崩溃;
			CookieSyncManager.createInstance(OriginalContext.getContext());
			//清除所有cookie
			CookieManager.getInstance().removeAllCookie();
			LogcatHelper.getInstance(context).stop();
			ActivityStackMgr.exitActivityHistory();
			Activity activity = (Activity) context;
			//解决从首页点击返回由于入栈的原因到了交易页面
			if (activity instanceof com.romaway.framework.app.RomaWayFragmentActivity) {
				((com.romaway.framework.app.RomaWayFragmentActivity) activity).finishActivity();
			} else {
				activity.finish();
			}

			//[兼容需求] 如果修改过认证地址, 那么退出app时需销毁进程(兼容集成消息推送功能, 退出App时不销毁进程的情况)
			if(SharedPreferenceUtils.getPreference(SharedPreferenceUtils.DATA_CONFIG, "AuthAddress_changed_killProcess", false)) {
				// 已杀死过进程, 重置为下次无需销毁:
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, "AuthAddress_changed_killProcess", false);
				Session.onKillProcess();
				killProcess();
			}
			Session.onKillProcess();
//			killProcess();
			// killProcess(context);
		}catch (Exception e){
			Log.e("ExitConfirm", e.getMessage());
		}
	}

	private static void killProcess() {
		try {
			Runtime r = Runtime.getRuntime();
			//[Bug]通过系统Api直接获取当前App所在进程, 并"kill -15 pid"正常退出进程, 退出前可以被阻塞或回调处理(是Linux缺省的程序中断信号):
			r.exec("kill -15 " + android.os.Process.myPid());
		} catch (IOException ex) {
			Logger.e("ExitConfirm", ex.getMessage());
		}
	}
	
	private static void killProcess(Context ctxt)
	{
		String packageName = ctxt.getPackageName();
		String processId = "";
		try
		{
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("ps");
			BufferedReader br = new BufferedReader(new InputStreamReader(
			        p.getInputStream()));
			String inline;
			while ((inline = br.readLine()) != null)
			{
				if (inline.contains(packageName))
				{
					break;
				}
			}
			br.close();
			StringTokenizer processInfoTokenizer = new StringTokenizer(inline);
			int count = 0;
			while (processInfoTokenizer.hasMoreTokens())
			{
				count++;
				processId = processInfoTokenizer.nextToken();
				if (count == 2)
				{
					break;
				}
			}
			// Log.e(TAG, "kill process : " + processId);
			r.exec("kill -15 " + processId);
		} catch (IOException ex)
		{
			// Log.e(TAG, "" + ex.getStackTrace());
		}
	}

}
