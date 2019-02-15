/**
 * 
 */
package com.romaway.common.net.conn;

import android.support.annotation.Keep;

import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.lang.URLUtils;
import com.romaway.commons.log.Logger;

/**
 * 服务器连接类,用于定义服务器类型，连接方法(post/get)，超时时间,连接对象
 * 
 * @author duminghui
 * 
 */
@Keep
public class ConnInfo
{
	public static final int CONN_METHOD_GET = 1;
	public static final int CONN_METHOD_POST = 2;

	/**
	 * 服务器类型
	 */
	private ServerInfo serverInfo;
	/**
	 * 连接方法
	 */
	private int connMethod;

	/**
	 * 设置超时时间
	 */
	private int timeOut;

	private AConnection conn;

	public ConnInfo(ServerInfo serverInfo, int connMethod, int timeOut)
	{
		this.serverInfo = serverInfo;
		this.connMethod = connMethod;
		this.timeOut = timeOut;
	}
	private int i;
	public static ConnInfo newConnectionInfoSockePost(int serverType)
	{
		ServerInfo serverInfo = ServerInfoMgr.getInstance()
		        .getDefaultServerInfo(serverType);
		return newConnectionInfoSockePost(serverInfo);
	}
	
	public static ConnInfo newConnectionInfoSockePost(int serverType, String subFunUrl)
	{
		ServerInfo serverInfo = ServerInfoMgr.getInstance()
		        .getDefaultServerInfo(serverType);
		//采用临时缓存，防止下一个请求使用相同地址
		if (serverInfo != null) {
			ServerInfo temp = new ServerInfo(serverInfo.getServerFlag(), serverType, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
			if(temp!=null){
				temp.setSubFunUrl(subFunUrl);
			}
			return newConnectionInfoSockePost(temp);
		} else {
			serverInfo = ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_AUTH);
			ServerInfo temp = new ServerInfo(serverInfo.getServerFlag(), serverType, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
			if (temp != null) {
				temp.setSubFunUrl(subFunUrl);
				temp.setCheckCertificate(serverInfo.isCheckCertificate());
				temp.setSSLPublicKey(serverInfo.getSslPublicKeyIs());
				temp.setSSLPublicKeyPath(serverInfo.getSslPublicKeyPath());
			}
			return newConnectionInfoSockePost(temp);
		}
	}
	
	public static ConnInfo newConnectionInfoSockePost(int serverType, String subFunUrl, boolean isHttps){
		ServerInfo serverInfo = ServerInfoMgr.getInstance()
		        .getDefaultServerInfo(serverType);
		//采用临时缓存，防止下一个请求使用相同地址
		if (serverInfo != null) {
			ServerInfo temp = new ServerInfo(serverInfo.getServerFlag(), serverType, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
			if (temp != null) {
				String url = temp.getUrl();
				if (isHttps && !url.contains("https")) {//将http替换为https，去掉端口号
					temp.setUrl(URLUtils.toHttps(url, temp.getHttpsPort()));
				}
				temp.setSubFunUrl(subFunUrl);
				temp.setCheckCertificate(serverInfo.isCheckCertificate());
				temp.setSSLPublicKey(serverInfo.getSslPublicKeyIs());
				temp.setSSLPublicKeyPath(serverInfo.getSslPublicKeyPath());
			}
			return newConnectionInfoSockePost(temp);
		} else {
			return newConnectionInfoSockePost(serverInfo);
		}
	}
	//自定义超时时间
	public static ConnInfo newConnectionInfoSockePost(int serverType, String subFunUrl, boolean isHttps, int timeOut ){
		ServerInfo serverInfo = ServerInfoMgr.getInstance()
				.getDefaultServerInfo(serverType);
		//采用临时缓存，防止下一个请求使用相同地址
		if (serverInfo != null) {
			ServerInfo temp = new ServerInfo(serverInfo.getServerFlag(), serverType, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
			if (temp != null) {
				String url = temp.getUrl();
				if (isHttps && !url.contains("https")) {//将http替换为https，去掉端口号
					temp.setUrl(URLUtils.toHttps(url, temp.getHttpsPort()));
				}
				temp.setSubFunUrl(subFunUrl);
				temp.setCheckCertificate(serverInfo.isCheckCertificate());
				temp.setSSLPublicKey(serverInfo.getSslPublicKeyIs());
				temp.setSSLPublicKeyPath(serverInfo.getSslPublicKeyPath());
			}
			return newConnectionInfoSockePost(temp, timeOut);
		} else {
			return newConnectionInfoSockePost(serverInfo, timeOut);
		}
	}

	public static ConnInfo newConnectionInfoSockePost(ServerInfo serverInfo)
	{
		int timeOut = 15000;
		return new ConnInfo(serverInfo, CONN_METHOD_POST, timeOut);
	}
	public static ConnInfo newConnectionInfoSockePost(ServerInfo serverInfo, int timeOut)
	{
		return new ConnInfo(serverInfo, CONN_METHOD_POST, timeOut);
	}
	public ServerInfo getServerInfo()
	{
		return serverInfo;
	}

  /**
	 * 重新设置服务器,常用于认证，行情切换服务器
	 * @param s
	 */
	public void setServerInfo(ServerInfo s)
	{
		serverInfo = s;
	}
  
	public int getConnMethod()
	{
		return connMethod;
	}

	public int getTimeOut()
	{
		Logger.d("Net",String.format("网络超时时间：%s", timeOut));
		return timeOut;
	}

	public AConnection getConn()
	{
		return conn;
	}

	public void setConn(AConnection conn)
	{
		this.conn = conn;
	}

}
