/**
 * 
 */
package com.romaway.common.net.sender;

import android.support.annotation.Keep;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgSendStatus;
import com.romaway.common.net.NetLogs;
import com.romaway.common.net.conn.AConnection;
import com.romaway.common.net.conn.NetConnectionManagerProxy;
import com.romaway.common.net.receiver.NetMsgReceiverProxy;
import com.romaway.common.net.sendingqueue.NetMsgSendingQueue;

/**
 * 网络信息发送接口，具体实现由平台业务实现
 * 
 * @author duminghui
 * 
 */
@Keep
public abstract class ANetMsgSender
{
	/**
	 * 停止
	 */
	public abstract void shutdown();

	public abstract void send(ANetMsg msg);

	protected final void toSend(ANetMsg msg)
	{

		boolean isValidMsg = isValidMsg(msg);
		NetLogs.d_msginfo(msg, "NetMsgSender", "mgr", String.format(
		        "lr=null%s,vm:%s", msg.isReceiveListenerNull(), isValidMsg));
		if (isValidMsg)
		{
			if (!msg.isReceiveListenerNull())
			{
				NetMsgSendingQueue.getInstance().addMsg(msg);
				AConnection netConnection = NetConnectionManagerProxy
				        .getInstance().create(msg.getConnInfo());
				netConnection.setNetMsg(msg);
				netConnection.sendMsg();
			}
		} else
		{
			msg.setSendStatus(EMsgSendStatus.sendDrop);
			if (!msg.isReceiveListenerNull())
			{
				NetMsgReceiverProxy.getInstance().receiveMsg(msg);
			}

		}
	}

	protected abstract boolean isValidMsg(ANetMsg msg);
}
