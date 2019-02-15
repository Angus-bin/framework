package com.romaway.android.phone.netreq;

import com.romaway.android.phone.R;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.common.android.base.Res;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.service.PINGServices;
import com.romaway.common.protocol.service.NetMsg;

public class PINGReq {
	public static final void reqPing(INetReceiveListener listener,
			ServerInfo serverInfo, String msgFlag) {
		String appid = SysConfigs.APPID;
		NetMsg msg = PINGServices.mf_test(msgFlag, serverInfo, appid, listener,
				EMsgLevel.normal, false, true);
		NetMsgSend.sendMsg(msg);
	}
}
