/**
 * 
 */
package com.romaway.common.net;

import android.support.annotation.Keep;

import java.util.ArrayList;
import java.util.HashMap;

import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.AProtocol;

/**
 * 网络消息
 */
@Keep
public abstract class ANetMsg
{
	/**
	 * 消息发送状态
	 */
	private volatile EMsgSendStatus sendStatus = EMsgSendStatus.wait;

	/**
	 * 消息接收状态
	 */
	private volatile EMsgReceiveStatus receiveStatus = EMsgReceiveStatus.wait;
	/**
	 * 消息标记
	 */
	private String msgFlag;
	/**
	 * 消息等级
	 */
	private EMsgLevel msgLevel = EMsgLevel.normal;

	/**
	 * 连接信息
	 */
	private ConnInfo connInfo;

	/**
	 * 超时时间
	 */
	private int timeout;
	/**
	 * 发送时间
	 */
	private long sendTime;

	/**
	 * 出错时是否重发请求
	 */
	private boolean isResendOnError;
	
	/**
	 * 是否允许错误重发
	 */
	private boolean enableResendOnError;

	/**
	 * 接收监听器
	 */
	private INetReceiveListener receiveListener;

	/**
	 * 消息所有者
	 */
	private INetMsgOwner owner;

	/**
	 * 是否连续发送
	 */
	private boolean isContinuous;
	/**
	 * 是否切换站点
	 * 2014.6.10
	 * zhao
	 * 用于修改站点测速bug
	 */
	public static boolean changeSite = true;
	/**
	 * 请求方式
	 */
	public static boolean sendByGet = false;
	/**
	 * HTTPS请求
	 */
	public static boolean sendByHttps = false;

	/**
	 * 构造函数
	 * @param msgFlag 消息标记
	 * @param msgLevel 消息状态
	 * @param connInfo 连接信息
	 * @param isResendOnError 出错时是否重发
	 * @param receiveListener 接收监听器
	 */
	public ANetMsg(String msgFlag, EMsgLevel msgLevel, ConnInfo connInfo,
	        boolean isResendOnError, INetReceiveListener receiveListener)
	{
		this.timeout = 5000;
		this.msgFlag = msgFlag;
		this.msgLevel = msgLevel;
		this.connInfo = connInfo;
		this.isResendOnError = true;
		if (connInfo!=null && connInfo.getServerInfo()!=null){
            enableResendOnError = this.isResendOnError = true;
        } else{
            enableResendOnError = this.isResendOnError = false;
        }
		this.receiveListener = receiveListener;
		
		sendByGet = false;
		sendByHttps = false;
	}
	
	public ANetMsg(String msgFlag, EMsgLevel msgLevel, ConnInfo connInfo,
	        boolean isResendOnError, boolean changeSite, INetReceiveListener receiveListener)
	{
		this.timeout = 5000;
		this.msgFlag = msgFlag;
		this.msgLevel = msgLevel;
		this.connInfo = connInfo;
		this.changeSite = changeSite;
		this.isResendOnError = true;
		if (connInfo!=null && connInfo.getServerInfo()!=null){
            enableResendOnError = this.isResendOnError = true;
        } else{
            enableResendOnError = this.isResendOnError = false;
        }
		if(!this.changeSite){
			enableResendOnError = false;
            this.isResendOnError = false;
            this.changeSite=true;
		}
		this.receiveListener = receiveListener;
		
		sendByGet = false;
		sendByHttps = false;
	}
	

	public void setOwner(INetMsgOwner owner)
	{
		this.owner = owner;
	}

	public INetMsgOwner getOwner()
	{
		return owner;
	}

	public EMsgSendStatus getSendStatus()
	{
		return sendStatus;
	}

	public void setSendStatus(EMsgSendStatus sendStatus)
	{
		this.sendStatus = sendStatus;
	}

	public EMsgReceiveStatus getReceiveStatus()
	{
		return receiveStatus;
	}

	public void setReceiveStatus(EMsgReceiveStatus receiveStatus)
	{
		this.receiveStatus = receiveStatus;
	}

	public String getMsgFlag()
	{
		return msgFlag;
	}

	public ConnInfo getConnInfo()
	{
		return connInfo;
	}

	public long getSendTime()
	{
		return sendTime;
	}

	public void setSendTime()
	{
		this.sendTime = System.currentTimeMillis();
	}

	public INetReceiveListener getReceiveListener()
	{
		return receiveListener;
	}

	public boolean isReceiveListenerNull()
	{
		return receiveListener == null;
	}

	public void onReceiveListener()
	{
		if(receiveListener != null){
			receiveListener.onReceive(this);
		}
	}
	
	public void removeOnReceiveListener(){
		if(receiveListener != null){
			receiveListener = null;
		}
	}
	
	public EMsgLevel getMsgLevel()
	{
		return msgLevel;
	}

	/**
	 * 出错时是否可以重发
	 * @return
	 */
	public boolean isCanResendOnError()
	{
		return enableResendOnError && isResendOnError;
	}
	
	/**
	 * 出错时是否允许重复（原始设置）
	 * @return
	 */
	public boolean isEnableResendOnError()
	{
		return isResendOnError;
	}
	
	public void reSetResendStatus()
	{
		isResendOnError = true;
	}

	public int getTimeout()
	{
		return timeout;
	}

	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	public void setMsgFlag(String msgFlag)
	{
		this.msgFlag = msgFlag;
	}

	public boolean isContinuous()
	{
		return isContinuous;
	}

	public void setContinuous(boolean isContinuous)
	{
		this.isContinuous = isContinuous;
	}
	
	public abstract String getServerMsg();
	
	/**
	 * 获取错误码
	 * @return
	 */
	public abstract int getServerErrCode();

	/**
	 * 初始化待发送的数据
	 */
	public abstract void initSendData();

	/**
	 * 获取当前协议对象
	 */
	public abstract AProtocol getProtocol();
	
	/**
	 * 缓存数据回调
	 */
	public abstract AProtocol readCache();
	
	/**
	 * 缓存数据保存
	 */
	public abstract void saveCache();
	
	/**
	 * 获取发送数据
	 */
	public abstract byte[] getSendData();
	
	/**
	 * 获取JSON请求Header名字及内容
	 * @return
	 */
	public abstract HashMap<String, String> getSendHeader();
	
	/**
	 * 获取JSON响应Header名字
	 * @return
	 */
	public abstract String[] getResponseHeader();
	/**
	 * 设置JSON响应Header内容
	 * @param data
	 */
	public abstract void setRespHeaderValue(HashMap<String, String> data);

	/**
	 * 设置服务器返回的数据
	 * 
	 * @param datas
	 */
	public abstract void setServerReceiveData(byte[] datas);

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ANetMsg)
		{
			ANetMsg msg = (ANetMsg) obj;
			// Logger.getLogger()
			// .d("",
			// String.format("%s:%s,%s:%s,%s", "equals",
			// msg.getMsgFlag(), this.msgFlag,
			// msg.getSendStatus(), this.sendStatus));
			// if (msg.getMsgFlag().equals(this.msgFlag)
			// && msg.getSendStatus() == this.sendStatus) {
			if (msg.getMsgFlag().equals(this.msgFlag))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	protected Object clone()
	{
		try
		{
			ANetMsg msg = (ANetMsg) super.clone();
			return msg;
		} catch (CloneNotSupportedException e)
		{
			return null;
		}
	}
}
