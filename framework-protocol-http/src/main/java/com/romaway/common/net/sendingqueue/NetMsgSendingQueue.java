/**
 * 
 */
package com.romaway.common.net.sendingqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgSendStatus;
import com.romaway.common.net.INetMsgOwner;
import com.romaway.common.net.NetMsgUtils;
import com.romaway.common.net.receiver.NetMsgReceiverProxy;
import com.romaway.commons.log.Logger;

/**
 * 正在发送消息队列
 * 
 * @author duminghui
 * 
 */
public class NetMsgSendingQueue
{
	private static final NetMsgSendingQueue instance = new NetMsgSendingQueue();
	private volatile List<ANetMsg> sendingMsgs = new CopyOnWriteArrayList<ANetMsg>();

	private NetMsgSendingQueue()
	{

	}

	public static final NetMsgSendingQueue getInstance()
	{
		return instance;
	}

	/**
	 * 添加消息到消息队列，如果同类的消息已经存在，则停掉之前的消息
	 * 
	 * @param msg
	 */
	public void addMsg(ANetMsg msg)
	{
		if (sendingMsgs.contains(msg))
		{
			int i = sendingMsgs.indexOf(msg);
			int _mSize = sendingMsgs.size();
			Logger.d("NET", "sendingMsgs.size()=" + _mSize);
			if (i>=0 && i<_mSize){ //kevin 解决bug:SJZJ-24628 加入判断条件
				Logger.d("NET", "sendingMsgs.size()=" + sendingMsgs.size());
				try{
					ANetMsg abortMsg = sendingMsgs.get(sendingMsgs.indexOf(msg));
					abort(abortMsg);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
		sendingMsgs.add(msg);
		Logger.d(
		        "NetMsgSendingQueue",
		        String.format("[%s]%s,%s", "add", msg.getMsgFlag(),
		                Integer.toHexString(msg.hashCode())));
	}

	/**
	 * 停止正在发送的所有消息
	 */
	public void abortAll()
	{
		List<ANetMsg> msgs = new ArrayList<ANetMsg>();
		msgs.addAll(sendingMsgs);
		for (ANetMsg msg : msgs)
		{
			abort(msg);
		}
	}

	/**
	 * 停止该owner集合下的所有消息
	 * 
	 * @param owners
	 */
	public void abortAll(List<INetMsgOwner> owners)
	{
		List<ANetMsg> msgs = new ArrayList<ANetMsg>();
		msgs.addAll(sendingMsgs);
		for (ANetMsg msg : msgs)
		{
			if (owners.contains(msg.getOwner()))
			{
				abort(msg);
			}
		}
	}

	/**
	 * 停止该owner下的消息
	 * 
	 * @param owner
	 */
	public void abort(INetMsgOwner owner)
	{
		List<ANetMsg> msgs = new ArrayList<ANetMsg>();
		msgs.addAll(sendingMsgs);
		for (ANetMsg msg : msgs)
		{
			if (owner.equals(msg.getOwner()))
			{
				abort(msg);
			}
		}
	}

	/**
	 * 停止消息 <br>
	 * TODO 在消息abort时的错误还要处理
	 * 
	 * @param msg
	 */
	public void abort(ANetMsg msg)
	{
		Logger.d(
		        "NetMsgSendingQueue",
		        String.format("[%s]%s,%s", "abort", msg.getMsgFlag(),
		                Integer.toHexString(msg.hashCode())));
		if (msg.getConnInfo().getConn() != null)
		{
			msg.getConnInfo().getConn().abort();
		}
		msg.setSendStatus(EMsgSendStatus.sendDrop);
		NetMsgReceiverProxy.getInstance().receiveMsg(msg);
	}

	public void remove(ANetMsg msg)
	{
		boolean remove = sendingMsgs.remove(msg);
		Logger.d(
		        "NetMsgSendingQueue",
		        String.format("[%s]%s,%s,%s", "remove", remove,
		                msg.getMsgFlag(), Integer.toHexString(msg.hashCode())));
	}

	public void timeouts()
	{
		List<ANetMsg> msgs = new ArrayList<ANetMsg>();
		msgs.addAll(sendingMsgs);
		for (ANetMsg msg : msgs)
		{
			if (NetMsgUtils.isTimeout(msg))
			{
				abort(msg);
				msg.setSendStatus(EMsgSendStatus.sentTimeout);
				NetMsgReceiverProxy.getInstance().receiveMsg(msg);
			}
		}
	}

	/**
	 * 列出owner的消息
	 * 
	 * @param owner
	 * @return
	 */
	public List<ANetMsg> listMsgs(INetMsgOwner owner)
	{
		List<ANetMsg> msgs = new ArrayList<ANetMsg>();
		msgs.addAll(sendingMsgs);
		List<ANetMsg> rMsg = new ArrayList<ANetMsg>();
		for (ANetMsg msg : msgs)
		{
			if (owner.equals(msg.getOwner()))
			{
				rMsg.add(msg);
			}
		}
		return rMsg;
	}

	/**
	 * 列出所有的消息
	 * 
	 * @return
	 */
	public List<ANetMsg> listMsgs()
	{
		List<ANetMsg> msgs = new ArrayList<ANetMsg>();
		msgs.addAll(sendingMsgs);
		return msgs;
	}
}
