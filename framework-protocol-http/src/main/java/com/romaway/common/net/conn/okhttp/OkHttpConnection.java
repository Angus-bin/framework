package com.romaway.common.net.conn.okhttp;

import com.romaway.common.net.EMsgSendStatus;
import com.romaway.common.net.conn.AConnection;
import com.romaway.common.net.conn.ConnException;
import com.romaway.commons.log.Logger;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 使用HttpClient实现网络操作。
 * 
 * @author duminghui
 * 
 */
class OkHttpConnection extends AConnection {

	private byte[] serverReceiveData = null;

	@Override
	protected void send() throws ConnException {

		if (netMsg.getConnInfo().getServerInfo() == null) {
			return;
		}

		// 使用Get请求
		if (netMsg.sendByGet) {
			sendByGet();
			return;
		}

		String url = netMsg.getConnInfo().getServerInfo().getUrl();

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(JSON, netMsg.getSendData());
		HashMap<String, String> sendHeader = netMsg.getSendHeader();
		Request request = null;
		if (sendHeader != null && sendHeader.size() > 0) {
			request = new Request.Builder().url(url).headers(Headers.of(netMsg.getSendHeader())).post(body).build();
		}else{
			request = new Request.Builder().url(url).post(body).build();
		}
		try {
			Response response = OkHttpUtils.getInstance().getOkHttpClient().newCall(request).execute();

			int statusCode = response.code();
			String[] respHeadersKey = netMsg.getResponseHeader();
			if (statusCode == 200 || statusCode == 401 || statusCode == 403) {
				if (respHeadersKey != null && respHeadersKey.length > 0) {
					HashMap<String, String> respHeader = new HashMap<String, String>();
					for (String aRespHeadersKey : respHeadersKey) {
						String header = response.header(aRespHeadersKey);
						if (header != null) {
							respHeader.put(aRespHeadersKey, header);
						} else {
							respHeader.put(aRespHeadersKey, "");
						}
					}
					netMsg.setRespHeaderValue(respHeader);
				}

			}

			InputStream inputStream = response.body().byteStream();
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int rLen = 1024;
			byte[] buff = new byte[rLen];
			int readSize = -1;
			while ((readSize = bis.read(buff, 0, rLen)) != -1) {
				baos.write(buff, 0, readSize);
			}
			bis.close();
			baos.close();
			serverReceiveData = baos.toByteArray();
			netMsg.reSetResendStatus();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void abort() {
	}

	@Override
	protected byte[] getServerReceiveData() {
		return serverReceiveData;
	}

	@Override
	protected int getStatusCode() {
		return 400;
	}

	@Override
	protected void catchException(ConnException ex) {
		String msg = ex.getMessage();
		Logger.e("HttpPostClientConnection",
				String.format("catchException,msgflag:%s,sendStatus:%s", netMsg.getMsgFlag(), netMsg.getSendStatus()));
		ex.printStackTrace();
		if (netMsg.getSendStatus() == EMsgSendStatus.sending) {
			if (msg != null && msg.indexOf("Connection timed out") > -1) {
				Logger.e("HttpPostClientConnection", "Connection timed out");
				netMsg.setSendStatus(EMsgSendStatus.sentTimeout);
			} else if (msg != null && msg.indexOf("Connection refused") > -1) {
				Logger.e("HttpPostClientConnection", "Connection refused");
				netMsg.setSendStatus(EMsgSendStatus.connError);
			} else if (msg != null
					&& (msg.indexOf("Socket is closed") > -1 || msg
					.indexOf("Socket closed") > -1)) {
				Logger.e("HttpPostClientConnection", "Socket is closed");
				netMsg.setSendStatus(EMsgSendStatus.socketClosed);
			} else if (msg != null
					&& msg.indexOf("Request already aborted") > -1) {
				Logger.e("HttpPostClientConnection", "Request already aborted");
				netMsg.setSendStatus(EMsgSendStatus.sendDrop);
			} else {
				Logger.e("HttpPostClientConnection", "unknow error");
				netMsg.setSendStatus(EMsgSendStatus.netError);
			}
		}
	}

	private void sendByGet() throws ConnException {

		String url = netMsg.getConnInfo().getServerInfo().getUrl();
		try {
			Response response = OkHttpUtils.get().headers(netMsg.getSendHeader()).url(url).build().execute();

			int statusCode = response.code();
			String[] respHeadersKey = netMsg.getResponseHeader();
			if (statusCode == 200 || statusCode == 401 || statusCode == 403) {
				if (respHeadersKey != null && respHeadersKey.length > 0) {
					HashMap<String, String> respHeader = new HashMap<String, String>();
					for (String aRespHeadersKey : respHeadersKey) {
						String header = response.header(aRespHeadersKey);
						if (header != null) {
							respHeader.put(aRespHeadersKey, header);
						} else {
							respHeader.put(aRespHeadersKey, "");
						}
					}
					netMsg.setRespHeaderValue(respHeader);
				}

			}

			InputStream inputStream = response.body().byteStream();
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int rLen = 1024;
			byte[] buff = new byte[rLen];
			int readSize = -1;
			while ((readSize = bis.read(buff, 0, rLen)) != -1) {
				baos.write(buff, 0, readSize);
			}
			bis.close();
			baos.close();
			serverReceiveData = baos.toByteArray();
			netMsg.reSetResendStatus();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void saveCookie() {

	}
}
