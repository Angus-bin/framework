package com.romaway.android.phone.netreq;

import com.romaway.android.phone.config.SysConfigs;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.service.HQServices;
import com.romaway.common.protocol.service.NetMsg;

public class FeedbackReq {

	/**
	 * 用户反馈查询
	 */
	public static final void reqFeedbackSelect(String deviceId,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.wo_feedback_select(deviceId, appid, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 用户反馈提交
	 */
	public static final void reqFeedbackAdd(String deviceId,String feedback,String phoneModel,
			String osType,String osVersion,String appType,String appVersion,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.wo_feedback_add(deviceId, appid, feedback, phoneModel, osType, 
				osVersion, appType, appVersion, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}

	/**
	 * 用户反馈提交(建投: 新增手机号及QQ号信息收集)
	 */
	public static final void reqFeedbackAdd(String deviceId,String feedback,String phoneModel,
											String osType,String osVersion,String appType,
											String appVersion, String phoneNumber, String qqNumber,
											INetReceiveListener listener, String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.wo_feedback_add(deviceId, appid, feedback, phoneModel, osType,
				osVersion, appType, appVersion, phoneNumber, qqNumber, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 个人中心信息查询
	 */
	public static final void reqPersonalInfoSelect(String bacc,String deviceId,String phoneNum,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.wo_personal_info_select(bacc,deviceId, appid,phoneNum, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
}
