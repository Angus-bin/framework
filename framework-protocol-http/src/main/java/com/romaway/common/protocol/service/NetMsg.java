/**
 * 
 */
package com.romaway.common.protocol.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.EMsgSendStatus;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.EProtocolStatus;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * @author duminghui
 * 
 */
public class NetMsg extends ANetMsg
{
	@SuppressWarnings("rawtypes")
	private AProtocol protocol;

	/**
	 * 构造函数
	 * @param msgFlag 消息标记
	 * @param msgLevel 消息等级
	 * @param protocol 业务协议
	 * @param connInfo 连接信息
	 * @param isResendOnError 出错时是否重发
	 * @param receiveListener 接收监听器
	 */
	public NetMsg(String msgFlag, EMsgLevel msgLevel, AProtocol protocol,
	        ConnInfo connInfo, boolean isResendOnError,
	        INetReceiveListener receiveListener)
	{
		super(msgFlag, msgLevel, connInfo, isResendOnError, receiveListener);
		
		this.protocol = protocol;
	}
	
	public NetMsg(String msgFlag, EMsgLevel msgLevel, AProtocol protocol,
	        ConnInfo connInfo, boolean isResendOnError, boolean changeSite,
	        INetReceiveListener receiveListener){
		super(msgFlag, msgLevel, connInfo, isResendOnError, changeSite, receiveListener);
		this.protocol = protocol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.szkingdom.common.net.ANetMsg#initSendData()
	 */
	@Override
	public void initSendData()
	{
		protocol.encode();
	}

	static Object saveCacheLock = new Object();
	@Override
	public void saveCache() {
		// TODO Auto-generated method stub
		synchronized(saveCacheLock) {
			if(protocol.isJson){
				String key = protocol.getFlag();
				if(!StringUtils.isEmpty(key) && 
						protocol.isNetLoad && 
						protocol.isHasMemory){//当Key不为空并且是从网络中获取的数据就保存
				//	System.out.println("2----- protocolFlag::"+protocol.getFlag());
					protocol.saveCache(key, protocol);
				}else
					Logger.d("本地数据缓存", "保存--数据缓存功能key 等于 null");
			}
		}
	}
	
	@Override
	public AProtocol getProtocol() {
		return protocol;
	}
	
	static Object readCacheLock = new Object();
	@SuppressWarnings("unchecked")
	@Override
	public AProtocol readCache() {
		// TODO Auto-generated method stub
		synchronized(readCacheLock) {
			AProtocol tProtocol = null;
			if(protocol.isJson){
				String key = protocol.getFlag();
				if(!StringUtils.isEmpty(key)){
					tProtocol = protocol.readCache(key);
					if(tProtocol != null){
						protocol = tProtocol;
						this.setSendStatus(EMsgSendStatus.success);
						protocol.setStatus(EProtocolStatus.success);
						protocol.isNetLoad = false;//设置为本地缓存类型
					}
				}else
					Logger.d("本地数据缓存", "读取--数据缓存功能key 等于 null");
			}
			return tProtocol;
		}
	}
	
	@Override
	public byte[] getSendData()
	{
		return protocol.getSendData();
	}

	static Object decodeLock = new Object();
	@Override
	public void setServerReceiveData(byte[] datas)
	{
		synchronized(decodeLock) {
			protocol.setServerReceiveData(datas);
			protocol.decode();
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					saveCache();
				}
			}).start();
		}
	}
	
	/**
	 * 当服务器下发错误时，取错误信息
	 */
	@Override
	public String getServerMsg()
	{
		if (protocol != null)
			return protocol.getServerMsg();
		else
			return "";
	}
	
	/**
	 * 当服务器下发错误时，取错误码
	 */
	@Override
	public int getServerErrCode(){
	    if (protocol != null)
            return protocol.getServerErrorCode();
        else
            return 0;
	}
	
	/**
	 * 是否 来自自动刷新的请求，自动刷新将不提示网络错误信息
	 * @return true 表示是
	 */
	public boolean isAutoRefresh()
	{
		if (protocol == null)
			return false;
		return protocol.isAutoRefresh();
	}

	@Override
    public String toString()
    {
	    // TODO Auto-generated method stub
	    return protocol.getServerMsg();
    }

	@Override
	public HashMap<String, String> getSendHeader() {
		// TODO Auto-generated method stub
		return protocol.getSendHeader();
	}

	@Override
	public String[] getResponseHeader() {
		// TODO Auto-generated method stub
		return protocol.getResponseHeader();
	}

	@Override
	public void setRespHeaderValue(HashMap<String, String> header) {
		// TODO Auto-generated method stub
		protocol.setRespHeaderValue(header);
	}
}
