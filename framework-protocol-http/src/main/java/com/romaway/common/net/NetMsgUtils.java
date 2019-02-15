/**
 * 
 */
package com.romaway.common.net;

/**
 * @author duminghui
 * 
 */
public class NetMsgUtils
{
	/**
	 * 判断消息是否超时
	 * 
	 * @param netMsg
	 * @return
	 */
	public static final boolean isTimeout(ANetMsg netMsg)
	{
		return netMsg.getTimeout() > 0
		        && netMsg.getSendTime() + netMsg.getTimeout() < System
		                .currentTimeMillis();
	}

	/**
	 * 设置消息重新发送，设置一些初始化状态
	 * 
	 * @param msg
	 * @return
	 */
	public static final ANetMsg obtainResendMsg(ANetMsg msg)
	{
		ANetMsg reSendMsg = (ANetMsg) msg.clone();
		reSendMsg.setSendStatus(EMsgSendStatus.wait);
		reSendMsg.setReceiveStatus(EMsgReceiveStatus.wait);
		reSendMsg.setMsgFlag(reSendMsg.getMsgFlag() + ":R");
		return reSendMsg;
	}
}
