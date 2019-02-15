package com.romaway.android.phone.netreq;

import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.protocol.service.WoService;

public class WoReq {

	/**
	 * 请求用户信息
	 * @param userId
	 * @param listener
	 * @param msgFlag
	 */
	public static final void req_userInfo_select(int userId, INetReceiveListener listener, String msgFlag){
//		NetMsg msg = WoService.wo_userInfo_slelect(userId, listener, msgFlag);
//		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 上传用户信息
	 * @param level
	 * @param name
	 * @param fundId
	 * @param userId
	 * @param moblieId
	 * @param avatar
	 * @param listener
	 * @param msgFlag
	 */
	public static final void req_userInfo_update(String level, String name, String fundId, String userId, String moblieId, String avatar, INetReceiveListener listener,String msgFlag){
		NetMsg msg = WoService.wo_userInfo_update(level, name, fundId, userId, moblieId, avatar, listener, msgFlag);
		NetMsgSend.sendMsg(msg);
	}
}
