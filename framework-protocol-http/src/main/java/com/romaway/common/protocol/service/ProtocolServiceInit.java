/**
 * 
 */
package com.romaway.common.protocol.service;

import com.romaway.common.net.NetInit;
import com.romaway.common.net.conn.IConnectionManager;
import com.romaway.common.net.proxy.ANetProxyInfoFactory;
import com.romaway.common.net.receiver.ANetMsgReceiver;
import com.romaway.common.net.sender.ANetMsgSender;
import com.romaway.common.protocol.ProtocolCoderInit;
import com.romaway.commons.log.Logger;

/**
 * @author duminghui
 * 
 */
public class ProtocolServiceInit
{
	/**
	 * 初始化协议服务
	 * @param sender  消息发送对象
	 * @param receiver  消息接收对象
	 * @param connectionManager  连接管理器
	 * @param proxyInfoFactory  
	 */
	public final static void init(ANetMsgSender sender,
	        ANetMsgReceiver receiver, IConnectionManager connectionManager,
	        ANetProxyInfoFactory proxyInfoFactory)
	{
		Logger.d("ServiceInit", "ServiceInit:start");
		ProtocolCoderInit.init();
		NetInit.init(sender, receiver, connectionManager, proxyInfoFactory);
		Logger.d("ServiceInit", "ServiceInit:end");
	}
}
