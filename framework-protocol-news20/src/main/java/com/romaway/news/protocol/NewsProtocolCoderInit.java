package com.romaway.news.protocol;

import android.content.Context;

import com.romaway.common.protocol.ProtocolCoderMgr;


/**
 * 资讯模块协议Coder初始化
 * @author 万籁唤  2016-03-22
 * 在启动资讯模块时需要先初始化该数据协议类，否则数据请求会出现异常
 */ 
public class NewsProtocolCoderInit {  
	public static void init(Context context) { 
		ProtocolCoderMgr mgr = ProtocolCoderMgr.getInstance();
		mgr.putCoder(NewsListProtocol.class, new NewsListProtocolCoder(context));
		mgr.putCoder(NewsDetailsProtocol.class, new NewsDetailsProtocolCoder(context));
		mgr.putCoder(NewsTopicProtocol.class, new NewsTopicProtocolCoder(context));
		mgr.putCoder(NewsUserStockProtocol.class,new NewsUserStockProtocolCoder(context));
		mgr.putCoder(NewsTZRLProtocol.class,new NewsTZRLProtocolCoder(context));
	}
}
