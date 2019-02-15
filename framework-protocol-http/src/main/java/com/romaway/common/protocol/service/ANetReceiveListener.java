/**
 * 
 */
package com.romaway.common.protocol.service;

import android.os.Handler;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgSendStatus;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.EProtocolStatus;
import com.romaway.commons.log.Logger;

/**
 * @author duminghui
 * 
 */
public abstract class ANetReceiveListener implements INetReceiveListener
{
	/**
	 * 未知状态
	 */
	public final static int ERROR_UNKNOW = -1;
	/**
	 * 无错误
	 */
	public final static int SUCCESS 		= 0;
	/**
	 * 服务端返回错误
	 */
	public final static int ERROR_SERVER 	= 1;
	/**
	 * 数据解析错误
	 */
	public final static int ERROR_PARSER 	= 2;
	/**
	 * 连接错误
	 */
	public final static int ERROR_CONN	= 3;
	/**
	 * 网络未知错误
	 */
	public final static int ERROR_NET		= 4;
	/**
	 * 发送超时
	 */
	public final static int ERROR_TIMEOUT	= 5;
	public int status;
	
	/**
	 * 网络请求时的正常网络状态（不包括缓存状态）
	 */
	public final static int NET_STATUS_NET_OK = 0x00;
	/**
	 * 网络请求时的正常网络状态（不包括缓存状态）
	 */
	public final static int NET_STATUS_NET_ERROR = 0x01;
	
	/**
	 * 数据状态（缓存数据与网络数据合并后的数据）为空状态
	 */
	public final static int DATA_STATUS_DATA_EMPTY = 0x00;
	/**
	 * 数据状态（缓存数据与网络数据合并后的数据）为有新数据
	 */
	public final static int DATA_STATUS_HAS_NEW_DATA = 0x01;
	/**
	 * 数据状态（缓存数据与网络数据合并后的数据）为满状态
	 */
	public final static int DATA_STATUS_DATA_FULL = 0x02;
	
	protected void onReceive_sub(final ANetMsg netMsg)
	{
		NetMsg msg = (NetMsg) netMsg;
		EMsgSendStatus sendStatus = msg.getSendStatus();
		int status = ERROR_UNKNOW;
		if (sendStatus == EMsgSendStatus.success)
		{
			AProtocol ptl = msg.getProtocol();
			EProtocolStatus epStatus = ptl.getStatus();
			Logger.d("ANetReceiverListener",
			                String.format("[flag:%s][status:%s]",
			                        netMsg.getMsgFlag(), epStatus));
			if (epStatus == EProtocolStatus.success)
			{
				status = SUCCESS;
				onSuccess(msg, ptl);
			} else if (epStatus == EProtocolStatus.serverError)
			{
				status = ERROR_SERVER;
				onServerError(msg);
			} else if (epStatus == EProtocolStatus.parseError)
			{
				status = ERROR_PARSER;
				onParseError(msg);
			}
		} else if (sendStatus == EMsgSendStatus.connError)
		{
			status = ERROR_CONN;
			onConnError(msg);
		} else if (sendStatus == EMsgSendStatus.netError)
		{
			status = ERROR_NET;
			onNetError(msg);
		} else if (sendStatus == EMsgSendStatus.sentTimeout)
		{
			status = ERROR_TIMEOUT;
			onSentTimeout(msg);
		}
		
		onShowStatus(status, msg, msg.getProtocol());
	}

	/**
	 * @param msg
	 * @param ptl
	 */
	protected void onSuccess(NetMsg msg, AProtocol ptl)
	{
	}

	protected void onServerError(NetMsg msg)
	{
	}

	protected void onParseError(NetMsg msg)
	{
	}

	protected void onConnError(NetMsg msg)
	{
	}

	protected void onNetError(NetMsg msg)
	{
	}

	protected void onSentTimeout(NetMsg msg)
	{
	}
	
	
	/**
	 * 显示状态及提示语，根据需要在具体Activity中实现
	 * @param status 状态码
	 * @param msg 消息
	 */
	protected void onShowStatus(int status,NetMsg msg)
	{
		
	}
	
	protected void onShowStatus(int status,NetMsg msg, AProtocol ptl)
	{
		onShowStatus(status, msg);
		
		int dataState = DATA_STATUS_DATA_EMPTY;
		if(ptl.isHasMemory){
			if(ptl.isDataLoadFull)
				dataState = DATA_STATUS_DATA_FULL;
			else
				dataState = DATA_STATUS_HAS_NEW_DATA;
		}else {
			dataState = DATA_STATUS_DATA_EMPTY;
		}
				
		// 延时处理，不立即展示网络情况
//		mHandler.removeCallbacks(mMyRunnable);
		if(msg.getProtocol().isNetLoad) {
			if(status == SUCCESS) // 网络状态的监听
				onNetAndDataStatus(NET_STATUS_NET_OK, dataState, msg, ptl);
			else {
				//if(status == ERROR_TIMEOUT) {
				onNetAndDataStatus(NET_STATUS_NET_ERROR, dataState, msg, ptl);
//					mMyRunnable.setDatas(NET_STATUS_NET_ERROR, dataState, msg, ptl);
//					mHandler.postDelayed(mMyRunnable, 500);
				//}
			}
		}
		
		// 有新数据
		if((status == SUCCESS || 
				ptl.isNetLoad == false) && 
				dataState == DATA_STATUS_HAS_NEW_DATA) {
			onSuccess2(dataState, msg, ptl);
		}
	}
	
	private Handler mHandler = new Handler();
	private MyRunnable mMyRunnable = new MyRunnable();
	class MyRunnable implements Runnable {

		private int netState;
		private int dataState;
		private NetMsg msg;
		private AProtocol ptl;
		
		public void setDatas(int netState, int dataState, NetMsg msg, AProtocol ptl){
			this.netState = netState;
			this.dataState = dataState;
			this.msg = msg;
			this.ptl = ptl;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			onNetAndDataStatus(netState, dataState, msg, ptl);
		}
	}
	protected void onSuccess2(int dataState, NetMsg msg, AProtocol ptl)
	{
	}
	
	/**
	 * 网络状态 和 数据状态的监听
	 * @param netStatus
	 * @param dataStatus
	 * @param msg
	 * @param ptl
	 */
	protected void onNetAndDataStatus(int netStatus,int dataStatus, NetMsg msg, AProtocol ptl) {
		
	}
}
