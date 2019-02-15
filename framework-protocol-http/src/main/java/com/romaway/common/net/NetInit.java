/**
 * 
 */
package com.romaway.common.net;

import android.support.annotation.Keep;

import com.romaway.common.net.conn.IConnectionManager;
import com.romaway.common.net.conn.NetConnectionManagerProxy;
import com.romaway.common.net.proxy.ANetProxyInfoFactory;
import com.romaway.common.net.proxy.NetProxyInfoProxy;
import com.romaway.common.net.receiver.ANetMsgReceiver;
import com.romaway.common.net.receiver.NetMsgReceiverProxy;
import com.romaway.common.net.sender.ANetMsgSender;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.commons.log.Logger;

/**
 * 网络配置初始化
 * 
 * @author duminghui
 * 
 */
@Keep
public class NetInit
{

	public final static void init(ANetMsgSender sender,
	        ANetMsgReceiver receiver, IConnectionManager connectionManager,
	        ANetProxyInfoFactory proxyInfoFactory)
	{
		Logger.i("NetInit", "NetInit:start...");
		setSender(sender);
		setReceiver(receiver);
		setConnectionManager(connectionManager);
		setNetProxyInfoProxy(proxyInfoFactory);
		connectionManager.init();
		Logger.i("NetInit", "NetInit:end...");
	}

	public final static void setSender(ANetMsgSender sender)
	{
		NetMsgSenderProxy.getInstance().setSender(sender);
	}

	public final static void setReceiver(ANetMsgReceiver receiver)
	{
		NetMsgReceiverProxy.getInstance().setReceiver(receiver);
	}

	public final static void setConnectionManager(
	        IConnectionManager connectionManager)
	{
		NetConnectionManagerProxy.getInstance().setManager(connectionManager);
	}

	public final static void setNetProxyInfoProxy(
	        ANetProxyInfoFactory proxyInfoFactory)
	{
		NetProxyInfoProxy.getInstance()
		        .setNetProxyInfoFactory(proxyInfoFactory);
	}
}
