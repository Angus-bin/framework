/**
 * 
 */
package com.romaway.android.phone.netreq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.romaway.android.phone.widget.dialog.RomaImageDialog;
import com.romaway.activity.basephone.ActivityStackMgr;
import com.romaway.android.phone.R;
import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.netstatus.NetStatus;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.protocol.service.NetMsg;

/**
 * @author duminghui
 * 
 */
public class NetMsgSend {

	private static RomaImageDialog dialog;
	/**
	 * 在Activity中发送网络请求，判断网络是否可用
	 * 
	 * @param activity
	 * @param msg
	 */
	public static void send(final Activity activity, NetMsg msg) {
		// TODO 此处Bug该优化处理, 网络无连接时如何关闭Fragment的正在请求框;
		if (NetStatus.getInstance().isConn(activity)) {
			NetMsgSenderProxy.getInstance().send(msg);
		} else {
			if (activity != null) {
				// [Bug] 网络未连接异常处理弹框同时, 应撤销/关闭请求接口加载框(蒙层效果);
				if (activity instanceof SherlockFragmentActivity){
					SherlockFragmentActivity sherlockFragmentActivity = (SherlockFragmentActivity) activity;
					if (sherlockFragmentActivity.getSherlockFragment() != null){
						sherlockFragmentActivity.getSherlockFragment().hideNetReqDialog();
					}
				}

				String tipsContent;
				// [Bug] 如缓存Dialog对象不属于当前Activity, 则置空重新创建, 避免WindowManager$BadTokenException异常;
				if(dialog != null && dialog.mContext instanceof Activity){
					Activity dialogAct = (Activity)dialog.mContext;
					if(dialogAct.isFinishing() || !activity.equals(dialogAct))
						dialog = null;
				}

				if(dialog == null) {
					OnClickDialogBtnListener leftListener = null;
//					if (!Res.getBoolean(R.bool.kconfigs_isInitLoginFailStayCurrent)){
//						tipsContent = Res.getString(R.string.roma_init_without_network);
//						leftListener = new OnClickDialogBtnListener() {
//							@Override
//							public void onClickButton(View view) {
//								dialog.dismiss();
//								Intent mIntent;
//								// 【优化】修复部分设备跳转至错误网络设置界面;
//								if (android.os.Build.VERSION.SDK_INT > 10) {
//									mIntent = new Intent(Settings.ACTION_SETTINGS);
//								} else {
//									mIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//								}
//								activity.startActivity(mIntent);
//								// exit(activity);
//							}
//						};
//					}else {
						tipsContent = Res.getString(R.string.err_net_conn);
//					}

					dialog = (RomaImageDialog) DialogFactory.getIconDialog(activity,
							tipsContent,
							DialogFactory.DIALOG_TYPE_ERROR,
							null, leftListener,
							null, new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									dialog.dismiss();
//									if(!Res.getBoolean(R.bool.kconfigs_isInitLoginFailStayCurrent)) {
//										exit(activity);
//									}
								}
							});
					dialog.setCancelable(false);
				}
				dialog.show();
//				if(!Res.getBoolean(R.bool.kconfigs_isInitLoginFailStayCurrent))
					dialog.setLeftButtonText(Res.getString(R.string.roma_err_net_dialog_left_text));

				dialog.setRightButtonText(Res.getString(R.string.roma_err_net_dialog_right_text));
			}
		}
	}
	public static void showDialogExit(final Activity activity) {
			if (activity != null) {
				String tipsContent;
				if(dialog == null) {
					OnClickDialogBtnListener leftListener = null;
//					if (!Res.getBoolean(R.bool.kconfigs_isInitLoginFailStayCurrent)){
//						tipsContent = Res.getString(R.string.roma_init_without_network);
//						leftListener = new OnClickDialogBtnListener() {
//							@Override
//							public void onClickButton(View view) {
//								dialog.dismiss();
//								Intent mIntent;
//								// 【优化】修复部分设备跳转至错误网络设置界面;
//								if (android.os.Build.VERSION.SDK_INT > 10) {
//									mIntent = new Intent(Settings.ACTION_SETTINGS);
//								} else {
//									mIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//								}
//								activity.startActivity(mIntent);
//								// exit(activity);
//							}
//						};
//					}else {
						tipsContent = Res.getString(R.string.err_net_conn);
//					}

					dialog = (RomaImageDialog) DialogFactory.getIconDialog(activity,
							tipsContent,
							DialogFactory.DIALOG_TYPE_ERROR,
							null, leftListener,
							null, new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									dialog.dismiss();
//									if(!Res.getBoolean(R.bool.kconfigs_isInitLoginFailStayCurrent)) {
//										exit(activity);
//									}
								}
							});
					dialog.setCancelable(false);
				}
				dialog.show();
//				if(!Res.getBoolean(R.bool.kconfigs_isInitLoginFailStayCurrent))
					dialog.setLeftButtonText(Res.getString(R.string.roma_err_net_dialog_left_text));

				dialog.setRightButtonText(Res.getString(R.string.roma_err_net_dialog_right_text));
			}

	}
	private static void exit(Activity activity) {
		ActivityStackMgr.exitActivityHistory();
		activity.finish();
		killProcess(activity);
	}

	private static void killProcess(Context ctxt) {
		String packageName = ctxt.getPackageName();
		String processId = "";
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("ps");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String inline;
			while ((inline = br.readLine()) != null) {
				if (inline.contains(packageName)) {
					break;
				}
			}
			br.close();
			StringTokenizer processInfoTokenizer = new StringTokenizer(inline);
			int count = 0;
			while (processInfoTokenizer.hasMoreTokens()) {
				count++;
				processId = processInfoTokenizer.nextToken();
				if (count == 2) {
					break;
				}
			}
			// Log.e(TAG, "kill process : " + processId);
			r.exec("kill -15 " + processId);
		} catch (IOException ex) {
			// Log.e(TAG, "" + ex.getStackTrace());
		}
	}

	/**
	 * 发送消息<BR>
	 * 对msg的Listener类型进行判断是否显示登录过程的对话框
	 * 
	 * @param msg
	 */
	public static final void sendMsg(NetMsg msg) {
		NetMsgSenderProxy.getInstance().send(msg);
	}
}
