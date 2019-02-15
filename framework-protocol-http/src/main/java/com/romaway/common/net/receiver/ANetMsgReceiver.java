/**
 * 
 */
package com.romaway.common.net.receiver;

import android.support.annotation.Keep;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgReceiveStatus;
import com.romaway.common.net.NetLogs;
import com.romaway.common.net.sendingqueue.NetMsgSendingQueue;

/**
 * 网络消息回复者
 * 
 * @author duminghui
 * 
 */
@Keep
public abstract class ANetMsgReceiver
{
	/**
	 * 回传消息
	 * 
	 * @param msg
	 */
	protected final void receive(ANetMsg msg)
	{
		boolean isListenerNull = msg.isReceiveListenerNull();
		NetMsgSendingQueue.getInstance().remove(msg);
		boolean isValidMsg = isValidMsg(msg);
		EMsgReceiveStatus receiveStatus = msg.getReceiveStatus();
		if (!isListenerNull && isValidMsg
		        && receiveStatus == EMsgReceiveStatus.wait)
		{
			log(msg, isValidMsg, isListenerNull, receiveStatus);
			msg.setReceiveStatus(EMsgReceiveStatus.received);
			listenerOnReceive(msg);
		} else
		{
			log(msg, isValidMsg, isListenerNull, receiveStatus);
		}
	}

	protected abstract boolean isValidMsg(ANetMsg msg);

	/**
	 * 在回传消息之前根据平台的处理,如异步回传消息
	 */
	protected abstract void listenerOnReceive(ANetMsg msg);

	private void log(ANetMsg msg, boolean isValidMsg, boolean isListenerNull,
	        EMsgReceiveStatus receiveStatus)
	{
		NetLogs.d_msginfo(msg, "NetMsgReceiver", "rer", String.format(
		        "lr=nul:%s,vm:%s,oldRS:%s,usetime:%s", isListenerNull,
		        isValidMsg, receiveStatus,
		        System.currentTimeMillis() - msg.getSendTime()));
	}
}
