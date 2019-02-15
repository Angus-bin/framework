/**
 * 
 */
package com.romaway.common.net.conn;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgReceiveStatus;
import com.romaway.common.net.EMsgSendStatus;
import com.romaway.common.net.receiver.NetMsgReceiverProxy;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.EProtocolStatus;
import com.romaway.common.protocol.dl.LoginForOld2NewProtocol;
import com.romaway.common.protocol.dl.LoginProtocol;
import com.romaway.common.protocol.dl.LoginProtocolNew;
import com.romaway.common.utils.Base64And3DesUtils;
import com.romaway.commons.log.Logger;

/**
 * @author duminghui
 * 
 */
public abstract class AConnection {
	protected ANetMsg netMsg;
	protected ConnInfo connInfo;

	public void setNetMsg(ANetMsg netMsg) {
		this.netMsg = netMsg;
		this.connInfo = netMsg.getConnInfo();
		connInfo.setConn(this);
	}

	protected abstract void send() throws ConnException;

	public abstract void abort();

	protected abstract byte[] getServerReceiveData();
	
	protected abstract int getStatusCode();

	protected abstract void saveCookie();

	/**
	 * 根据错误信息，更改网络消息的状态
	 * 
	 * @see EMsgSendStatus
	 * @param ex
	 */
	protected abstract void catchException(ConnException ex);

	public final synchronized void sendMsg() {
		if (netMsg.getSendStatus() != EMsgSendStatus.sendDrop) {
			netMsg.setSendStatus(EMsgSendStatus.sending);
			netMsg.setSendTime();
			netMsg.initSendData();
			
			if(netMsg.getProtocol().isLoadCache) {
				if(netMsg.readCache() != null)
					NetMsgReceiverProxy.getInstance().receiveMsg(netMsg);//缓存中存在就先刷新到界面上去
			}
			
			sendReq();

		}else
			NetMsgReceiverProxy.getInstance().receiveMsg(netMsg);
	}

	
	/**
	 * 发送请求
	 */
	@SuppressWarnings("unchecked")
	private void sendReq() {
		try {    
			netMsg.setReceiveStatus(EMsgReceiveStatus.wait);
			send();
			netMsg.setSendStatus(EMsgSendStatus.success);
			if (getServerReceiveData() != null) {
				netMsg.setServerReceiveData(getServerReceiveData());
				netMsg.getProtocol().isNetLoad = true;
				AProtocol ptl = netMsg.getProtocol();
				//只有登录请求才保存cookie
				if (ptl instanceof LoginProtocol || ptl instanceof LoginForOld2NewProtocol) {
					if (ptl.serverErrCode == 0) { //为0表示登录成功，才保存cookie
						saveCookie();
					}
				} else if (ptl instanceof LoginProtocolNew) {
					String errCode = ptl.serverErrCodeStr;
					try {
						errCode = Base64And3DesUtils.dualDecode(errCode);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if ("0".equals(errCode)) { //为0表示登录成功，才保存cookie
						saveCookie();
					}
				}
				NetMsgReceiverProxy.getInstance().receiveMsg(netMsg);
			}
		} catch (ConnException ex) {
			System.out.println("切换");
			catchException(ex);
			// 判断出是否需要重新发包
			if (netMsg.isCanResendOnError()) {
				if (netMsg.getSendStatus() == EMsgSendStatus.connError
						|| netMsg.getSendStatus() == EMsgSendStatus.socketClosed
						|| netMsg.getSendStatus() == EMsgSendStatus.netError) {
					// 切换服务器并重新发包
					changeServer();
				} else {
					netMsg.setSendStatus(EMsgSendStatus.netError);
					netMsg.getProtocol().setStatus(EProtocolStatus.serverError);
					System.out.println("不切换");
				}
			} else {
				netMsg.setSendStatus(EMsgSendStatus.netError);
				netMsg.getProtocol().setStatus(EProtocolStatus.serverError);
				Logger.d("HttpPostClientConnection", "该请求不做站点切换");
			}

		}
		NetMsgReceiverProxy.getInstance().receiveMsg(netMsg);
	}

	/**
	 * 重新发包
	 */
	private final void reSendMsg() {
		Logger.d("HttpPostClientConnection", "reSendMsg");
		netMsg.setSendStatus(EMsgSendStatus.sending);
		netMsg.setSendTime();
		netMsg.initSendData();
		try {
			Logger.d("HttpPostClientConnection", "重发");
			send();
			netMsg.setSendStatus(EMsgSendStatus.success);
			if(getServerReceiveData() != null){
				netMsg.setServerReceiveData(getServerReceiveData());
				netMsg.getProtocol().isNetLoad = true;
				NetMsgReceiverProxy.getInstance().receiveMsg(netMsg);
			}
			
		} catch (ConnException ex) {
			catchException(ex);
			netMsg.setSendStatus(EMsgSendStatus.netError);
			netMsg.getProtocol().setStatus(EProtocolStatus.serverError);
			changeServer();
		}

	}
	
	private int changeCount = 0;

	/**
	 * 切换服务器，重新发包
	 */
	private final void changeServer() {
		Logger.d("HttpPostClientConnection", "切换服务器");
		ServerInfo oldServerInfo = netMsg.getConnInfo().getServerInfo();
		int HttpsPort = oldServerInfo.getHttpsPort();
		String subFunUrl = oldServerInfo.getSubFunUrl();
		boolean isHttps = false;
		if (oldServerInfo.getAddress().contains("https")) {
			isHttps = true;
		}
		int serverType = netMsg.getConnInfo().getServerInfo().getServerType();
		changeCount++;
		if (changeCount == ServerInfoMgr.getInstance().getServerInfoCount(serverType)) {
			Logger.d("HttpPostClientConnection", "已切换过所有服务器，不再做切换");
			netMsg.setSendStatus(EMsgSendStatus.connError);
			return;
		}
		if (ServerInfoMgr.getInstance().changeServer(
				netMsg.getConnInfo().getServerInfo())) {
			ServerInfo newServerInfo = ServerInfoMgr.getInstance().getDefaultServerInfo(serverType);
			ServerInfo tempServerInfo = new ServerInfo(newServerInfo.getServerFlag(), serverType, newServerInfo.getServerName(), newServerInfo.getAddress(), true, newServerInfo.getHttpsPort());
			tempServerInfo.setCheckCertificate(newServerInfo.isCheckCertificate());
			tempServerInfo.setSSLPublicKey(newServerInfo.getSslPublicKeyIs());
			tempServerInfo.setSSLPublicKeyPath(newServerInfo.getSslPublicKeyPath());
			String url = tempServerInfo.getUrl();
			if (isHttps && !url.contains("https")) {//将http替换为https，去掉端口号
				url = url.replace("http", "https");
				int index = url.lastIndexOf(":");
				if (index > 5) {
					url = url.substring(0, index);
				}
				url = url + ":" + HttpsPort;
				tempServerInfo.setUrl(url);
			}
			tempServerInfo.setSubFunUrl(subFunUrl);
			netMsg.getConnInfo().setServerInfo(tempServerInfo);
		} else {
			return;
		}
		netMsg.setSendStatus(EMsgSendStatus.sending);
		netMsg.setSendTime();
		netMsg.initSendData();
		try {
			Logger.d("HttpPostClientConnection", "切换");
			send();
			netMsg.setSendStatus(EMsgSendStatus.success);
			if(getServerReceiveData() != null){
				netMsg.setServerReceiveData(getServerReceiveData());
				netMsg.getProtocol().isNetLoad = true;
				NetMsgReceiverProxy.getInstance().receiveMsg(netMsg);
			}
		} catch (ConnException ex) {
			System.out.println("切换");
			catchException(ex);
			// 判断出是否需要重新发包
			if (netMsg.isCanResendOnError()) {
				if (netMsg.getSendStatus() == EMsgSendStatus.connError
						|| netMsg.getSendStatus() == EMsgSendStatus.socketClosed
						|| netMsg.getSendStatus() == EMsgSendStatus.netError) {
					// 切换服务器并重新发包
					changeServer();
				} else {
					netMsg.setSendStatus(EMsgSendStatus.netError);
					netMsg.getProtocol().setStatus(EProtocolStatus.serverError);
				}
			} else {
				netMsg.setSendStatus(EMsgSendStatus.netError);
				netMsg.getProtocol().setStatus(EProtocolStatus.serverError);
			}

		}
	}

}
