package com.romaway.common.protocol.service;

import android.content.Context;

import com.romaway.common.android.base.BaseNetMsgReceiver;
import com.romaway.common.android.base.BaseNetMsgSender;
import com.romaway.common.android.base.BaseNetProxyInfoFactory;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.net.conn.httpclient.HttpClientConnectionMgr;
import com.romaway.common.net.conn.okhttp.OkHttpConnectionMgr;

public class StockServices {

	public static void init(Context context){
		OriginalContext.setContext(context);
		ProtocolServiceInit.init(new BaseNetMsgSender(),
		        new BaseNetMsgReceiver(), new HttpClientConnectionMgr(),
		        new BaseNetProxyInfoFactory());
	}

	public static void init(Context context,boolean isOkHttp){
		if (isOkHttp) {
			OriginalContext.setContext(context);
			ProtocolServiceInit.init(new BaseNetMsgSender(),
					new BaseNetMsgReceiver(), new OkHttpConnectionMgr(),
					new BaseNetProxyInfoFactory());
		}else{
			init(context);
		}
	}
}
