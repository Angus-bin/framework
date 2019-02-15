/**
 * 
 */
package com.romaway.android.phone.netreq;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.common.android.base.Res;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.service.DLServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.log.Logger;

/**
 * @author chenjp
 * 
 */
public class LoginReq {

	/**
	 * 初始化认证
	 * 
	 * @param phoneNum
	 * @param msgFlag
	 * @param cmdVersion
	 * @param activity
	 * @param listener
	 */
	@SuppressLint("ShowToast")
	public static void req_init(String msgFlag, Activity activity,
			INetReceiveListener listener) {
		NetMsg msg = null;
		String signToken = RomaUserAccount.getSignToken();
		String phone = RomaUserAccount.getUsername();
		String deviceId = SysConfigs.DEVICE_ID;
		String appType = SysConfigs.SOFT_TYPE;
		String cpid = SysConfigs.CPID;
		//[需求] 添加软件类型 用于初始化协议入参 wanlh 2105/11/30
		String softtype = SysConfigs.SOFT_TYPE + "/";
		String appid = SysConfigs.APPID;
		
		//++[需求]添加统一认证版本控制 wanlh 2015/12/08
		switch(Res.getInteger(R.integer.system_server_version)){
			case 1:
				softtype = "";
				if(Logger.getDebugMode())
					Toast.makeText(activity, 
							"[警示]:客户端统一认证是没带软件类型的！", 
							Toast.LENGTH_LONG);
				break;
			case 2:
				softtype = SysConfigs.getClientSoftType() +"/";
				break;
		}
		//--[需求]添加统一认证版本控制 wanlh 2015/12/08
		
		msg = DLServices.rz_dl(phone, appType, signToken, deviceId, cpid,
				softtype, appid, listener, EMsgLevel.normal, msgFlag, true);

		if (msg != null) {
			NetMsgSend.send(activity, msg);
		}

	}

	/**
	 * 注册获取验证码
	 * 
	 * @param phoneNum
	 * @param msgFlag
	 * @param activity
	 * @param listener
	 */
	public static void req_register(String phoneNum, String msgFlag,
			Activity activity, INetReceiveListener listener) {
		NetMsg msg = null;
		String deviceId = SysConfigs.DEVICE_ID;
		String cpid = SysConfigs.CPID;
		String appid = SysConfigs.APPID;

		msg = DLServices.rz_smsRegister(phoneNum, deviceId, cpid, appid,
				listener, EMsgLevel.normal, msgFlag, true);

		if (msg != null) {
			NetMsgSend.send(activity, msg);
		}
	}

	/**
	 * 语音验证码
	 *
	 * @param phoneNum
	 * @param msgFlag
	 * @param activity
	 * @param listener
	 */
	public static void req_yuyin(String phoneNum, String msgFlag,
									Activity activity, INetReceiveListener listener) {
		NetMsg msg = null;
		String deviceId = SysConfigs.DEVICE_ID;
		String appid = SysConfigs.APPID;

		msg = DLServices.rz_yuyinGetCode(phoneNum, deviceId, appid,
				listener, EMsgLevel.normal, msgFlag, true);

		if (msg != null) {
			NetMsgSend.send(activity, msg);
		}
	}

	/**
	 * 验证码登录
	 * 
	 * @param phoneNum
	 * @param secCode
	 * @param type
	 * @param msgFlag
	 * @param activity
	 * @param listener
	 */
	public static void req_login(String phoneNum, String secCode, String type,String invitation_code,
			String msgFlag, Activity activity, INetReceiveListener listener) {
		NetMsg msg = null;
		String deviceId = SysConfigs.DEVICE_ID;
		String appid = SysConfigs.APPID;

		msg = DLServices.rz_smsLogin(phoneNum, deviceId, secCode, type,appid, invitation_code,
				listener, EMsgLevel.normal, msgFlag, true);

		if (msg != null) {
			NetMsgSend.send(activity, msg);
		}
	}

	/**
	 * 验证码登录,从老版本升级到新版本
	 * 
	 * @param phoneNum
	 * @param password
	 * @param type
	 * @param msgFlag
	 * @param activity
	 * @param listener
	 */
	public static void req_loginForOld2New(String phoneNum, String password,
			String type, String msgFlag, Activity activity,
			INetReceiveListener listener) {
		NetMsg msg = null;
		String deviceId = SysConfigs.DEVICE_ID;
		String appid = SysConfigs.APPID;

		msg = DLServices.rz_smsLoginForOld2New(phoneNum, deviceId, password,
				type, appid, listener, EMsgLevel.normal, msgFlag, true);

		if (msg != null) {
			NetMsgSend.send(activity, msg);
		}
	}
	
	/**
	 * 获取升级状态信息
	 * @param phoneNum
	 * @param msgFlag
	 * @param activity
	 * @param listener
	 */
	public static void req_upgrade_state(String phoneNum, String msgFlag, Activity activity, INetReceiveListener listener){
		String appid = SysConfigs.APPID;
		NetMsg msg = DLServices.rz_upgrade_state(phoneNum, appid, listener, msgFlag);
		if (msg != null) {
			NetMsgSend.send(activity, msg);
		}
	}

	public static void req_test(String userID, String msgFlag, Activity activity, INetReceiveListener listener) {
//		NetMsg msg = DLServices.rz_test(userID, listener, msgFlag);
//		if (msg != null) {
//			NetMsgSend.send(activity, msg);
//		}
	}

}
