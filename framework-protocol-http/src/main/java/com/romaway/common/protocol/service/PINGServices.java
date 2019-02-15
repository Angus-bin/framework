package com.romaway.common.protocol.service;

import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.ping.PINGProtocol;
import com.romaway.commons.lang.URLUtils;

public class PINGServices {
	public static NetMsg mf_test(String msgFlag, ServerInfo serverInfo, String appid,
			INetReceiveListener listener, EMsgLevel level, boolean reSend, boolean hasIP) {
		PINGProtocol ptl = new PINGProtocol(msgFlag);
		if (hasIP) {
			serverInfo.setSubFunUrl(ptl.subFunUrl + URLUtils.getHost(serverInfo.getAddress()) + "/" + appid + "/");
		} else {
			serverInfo.setSubFunUrl(ptl.subFunUrl + appid + "/");
		}
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(serverInfo);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, false, listener);
		//msg.changeSite = false;
		msg.sendByGet = true;// 以GET方式请求
		return msg;
	}
}
