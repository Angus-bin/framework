/**
 * 
 */
package com.romaway.common.net.sender;

import android.support.annotation.Keep;

import com.romaway.common.net.ANetMsg;

/**
 * 网络发送代理 <br>
 * 在系统初始化时要调用setNetMsgMessengerFactory方法设置具体发送工厂
 * 
 * @author duminghui
 * 
 */
@Keep
public class NetMsgSenderProxy
{
	private final static NetMsgSenderProxy instance = new NetMsgSenderProxy();
	private ANetMsgSender messenger;

	private NetMsgSenderProxy()
	{

	}

	public final static NetMsgSenderProxy getInstance()
	{
		return instance;
	}

	public final void setSender(ANetMsgSender messenger)
	{
		this.messenger = messenger;
	}

	public final void send(ANetMsg msg)
	{
		messenger.send(msg);
	}

	public final void shutdown()
	{
		messenger.shutdown();
	}
}
