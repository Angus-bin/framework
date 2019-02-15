/**
 * 
 */
package com.romaway.common.net.conn.httpclient;

import android.text.TextUtils;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.net.EMsgSendStatus;
import com.romaway.common.net.NetLogs;
import com.romaway.common.net.conn.AConnection;
import com.romaway.common.net.conn.ConnException;
import com.romaway.common.net.proxy.NetProxyInfo;
import com.romaway.common.net.proxy.NetProxyInfoProxy;
import com.romaway.commons.db.PersistentCookieStore;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 使用HttpClient实现网络操作。
 * 
 * @author duminghui
 * 
 */
class HttpPostClientConnection extends AConnection {
	private byte[] serverReceiveData = null;
	private HttpPost httpPost;
	private HttpGet httpGet;
	private int statusCode = HttpStatus.SC_BAD_REQUEST;
	private List<Cookie> cookies;

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

		httpPost = new HttpPost(url);
		NetProxyInfo proxyInfo = NetProxyInfoProxy.getInstance()
				.getNetProxyInfo();
		NetLogs.d_netstep(netMsg, this, "HttpPostClientConnection", "",
				"open->Create HttpClinet");
		DefaultHttpClient httpClient = null;
		
		if (netMsg.sendByHttps) {
			httpClient = getHttpsClient();
		} else {
			httpClient = HttpClientMgr.getHttpClient();
		}

		NetLogs.d_netstep(netMsg, this, "HttpPostClientConnection", "",
				"Create HttpClient->setEntity");
		boolean usProxy = false;
		if (proxyInfo != null && !StringUtils.isEmpty(proxyInfo.getHost())) {
			HttpHost proxy = new HttpHost(proxyInfo.getHost(),
					proxyInfo.getPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			Logger.d(
					"HttpPostClientConnection",
					String.format("proxy=%s:%s", proxyInfo.getHost(),
							proxyInfo.getPort()));
			usProxy = true;
		}
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, connInfo.getTimeOut());
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				connInfo.getTimeOut());
		// 设置cookie
		PersistentCookieStore cookieStore = new PersistentCookieStore(
				OriginalContext.getContext());
		BasicCookieStore sendCookie = new BasicCookieStore();
		// 修改domian的值
		List<Cookie> cookieList = cookieStore.getCookies();
		if (cookieList != null && cookieList.size() > 0) {
			for (Cookie cookie : cookieList) {
				BasicClientCookie basicClientCookie = (BasicClientCookie) cookie;
				String address = netMsg.getConnInfo().getServerInfo().getAddress();
				//检查是否包含http:// 或者https://
				if (address.contains("https://") || address.contains("http://")) {
					address = address.replace("https://", "");
					address = address.replace("http://", "");
				}
				//检查是否包含:21800之类
				if (address.contains(":")) {
					int index = address.indexOf(":");
					address = address.substring(0, index);
				}
				Logger.d("HttpPostClientConnection", "requset cookie>>>>> "
						+ cookie.getName() + ": " + cookie.getValue() + " Domain: " + address);
				basicClientCookie.setDomain(address);
				sendCookie.addCookie(basicClientCookie);
			}
		}
		httpClient.setCookieStore(sendCookie);

		try {

			HttpEntity httpEntity = new ByteArrayEntity(netMsg.getSendData());
			httpPost.setEntity(httpEntity);// 请求httpRequest
			NetLogs.d_netstep(netMsg, this, "HttpPostClientConnection", "",
					"setEntity->execute Post");

			// json请求增加header的传递
			HashMap<String, String> sendHeaders = netMsg.getSendHeader();
			if (sendHeaders != null && sendHeaders.size() > 0) {
				Set<String> keys = sendHeaders.keySet();
				ArrayList<String> keyArray = new ArrayList<String>();
				for (String key : keys) {
					keyArray.add(key);
				}
				for (int i = 0; i < sendHeaders.size(); i++) {
					httpPost.setHeader(keyArray.get(i),
							sendHeaders.get(keyArray.get(i)));
					Logger.d("HttpPostClientConnection",
							"requset header>>>>> " + keyArray.get(i) + " :"
									+ sendHeaders.get(keyArray.get(i)));
				}
			}

			// 连接服务器并获取应答数据
			HttpResponse response = httpClient.execute(httpPost);
			// 获取保存cookie
			cookies = httpClient.getCookieStore().getCookies();

			NetLogs.d_netstep(netMsg, this, "HttpPostClientConnection", "",
					"execute Post->getResult");

			// 增加对返回码的判断，解决请求json格式数据未收到正确数据进行提示;
			statusCode = response.getStatusLine().getStatusCode();
			Logger.d("HttpPostClientConnection",
					"get response and statusCode>>>>>" + statusCode);

			String[] respHeadersKey = netMsg.getResponseHeader();
			//200、401、403不做站点切换
			if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_UNAUTHORIZED || statusCode == HttpStatus.SC_FORBIDDEN) {
				if (respHeadersKey != null && respHeadersKey.length > 0) {
					HashMap<String, String> respHeader = new HashMap<String, String>();
					for (int i = 0; i < respHeadersKey.length; i++) {
						Header header = response.getFirstHeader(respHeadersKey[i]);
						if (header != null) {
							String[] headArr = header.toString().split(":");
							respHeader.put(headArr[0].trim(), headArr[1].trim());
							Logger.d("HttpPostClientConnection",
									"get response and header>>>>>" + header);
						} else {
							respHeader.put(respHeadersKey[i], "");
						}
					}
					netMsg.setRespHeaderValue(respHeader);
				}
			} else {
				//抛出异常，进行站点切换
				throw new Exception("ServerException");
			}
			
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int rLen = 1024;
			byte[] buff = new byte[rLen];
			int readSize = -1;
			while ((readSize = bis.read(buff, 0, rLen)) != -1) {
				baos.write(buff, 0, readSize);
			}
			entity.consumeContent();
			serverReceiveData = baos.toByteArray();
			NetLogs.d_netstep(netMsg, this, "HttpPostClientConnection", "",
					"getResult");
			netMsg.reSetResendStatus();
		} catch (ClientProtocolException e) {
			System.out.println("-----ClientProtocolException-------");
			httpPost.abort();
			e.printStackTrace();
			throw new ConnException("ClientProtocolException," + e.getMessage());
		} catch (IOException e) {
			Logger.e("HttpPostClientConnection", "send():IOException");
			httpPost.abort();
			e.printStackTrace();
			throw new ConnException("IOException," + e.getMessage());
		} catch (Exception e) {
			System.out.println("-----Exception------");
			httpPost.abort();
			e.printStackTrace();
			throw new ConnException("Exception," + e.getMessage());
		} finally {
			httpClient.getConnectionManager().closeExpiredConnections();
			if (usProxy)
				httpClient.getConnectionManager().closeIdleConnections(1,
						TimeUnit.SECONDS);
		}
	}

	@Override
	public void abort() {
		if (httpPost != null) {
			httpPost.abort();
		}
		if (httpGet != null) {
			httpGet.abort();
		}
	}

	@Override
	protected byte[] getServerReceiveData() {
		return serverReceiveData;
	}

	@Override
	protected int getStatusCode() {
		// TODO Auto-generated method stub
		return statusCode;
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

		httpGet = new HttpGet(url);
		NetProxyInfo proxyInfo = NetProxyInfoProxy.getInstance()
				.getNetProxyInfo();
		NetLogs.d_netstep(netMsg, this, "HttpGetClientConnection", "",
				"open->Create HttpClinet");
		DefaultHttpClient httpClient = null;
		if (netMsg.sendByHttps) {
			httpClient = getHttpsClient();
		} else {
			httpClient = HttpClientMgr.getHttpClient();
		}
		NetLogs.d_netstep(netMsg, this, "HttpGetClientConnection", "",
				"Create HttpClient->setEntity");
		boolean usProxy = false;
		if (proxyInfo != null && !StringUtils.isEmpty(proxyInfo.getHost())) {
			HttpHost proxy = new HttpHost(proxyInfo.getHost(),
					proxyInfo.getPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			Logger.d(
					"HttpGetClientConnection",
					String.format("proxy=%s:%s", proxyInfo.getHost(),
							proxyInfo.getPort()));
			usProxy = true;
		}
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, connInfo.getTimeOut());
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				connInfo.getTimeOut());
		
		// 设置cookie
		PersistentCookieStore cookieStore = new PersistentCookieStore(
				OriginalContext.getContext());
		BasicCookieStore sendCookie = new BasicCookieStore();
		// 修改domian的值
		List<Cookie> cookieList = cookieStore.getCookies();
		if (cookieList != null && cookieList.size() > 0) {
			for (Cookie cookie : cookieList) {
				BasicClientCookie basicClientCookie = (BasicClientCookie) cookie;
				String address = netMsg.getConnInfo().getServerInfo().getAddress();
				if (address.contains("https://") || address.contains("http://")) {
					address = address.replace("https://", "");
					address = address.replace("http://", "");
				}
				//检查是否包含:21800之类
				if (address.contains(":")) {
					int index = address.indexOf(":");
					address = address.substring(0, index);
				}
				Logger.d("HttpGetClientConnection", "requset cookie>>>>> "
						+ cookie.getName() + ": " + cookie.getValue() + " Domain: " + address);
				basicClientCookie.setDomain(address);
				sendCookie.addCookie(basicClientCookie);
			}
		}
		httpClient.setCookieStore(sendCookie);
		// json请求增加header的传递
		HashMap<String, String> sendHeaders = netMsg.getSendHeader();
		if (sendHeaders != null && sendHeaders.size() > 0) {
			Set<String> keys = sendHeaders.keySet();
			ArrayList<String> keyArray = new ArrayList<String>();
			for (String key : keys) {
				keyArray.add(key);
			}
			for (int i = 0; i < sendHeaders.size(); i++) {
				httpGet.setHeader(keyArray.get(i),
						sendHeaders.get(keyArray.get(i)));
				Logger.d("HttpGetClientConnection",
						"requset header>>>>> " + keyArray.get(i) + " :"
								+ sendHeaders.get(keyArray.get(i)));
			}
		}
		
		try {

			HttpEntity httpEntity = new ByteArrayEntity(netMsg.getSendData());
			NetLogs.d_netstep(netMsg, this, "HttpGetClientConnection", "",
					"setEntity->execute Get");

			// 连接服务器并获取应答数据
			HttpResponse response = httpClient.execute(httpGet);

			NetLogs.d_netstep(netMsg, this, "HttpGetClientConnection", "",
					"execute Get->getResult");
			// 获取保存cookie
			cookies = httpClient.getCookieStore().getCookies();
			
			int statusCode = response.getStatusLine().getStatusCode();
			Logger.d("HttpGetClientConnection", "statusCode:" + statusCode);
			//保存header
			String[] respHeadersKey = netMsg.getResponseHeader();
			//200、401、403不做站点切换
			if (statusCode == HttpStatus.SC_OK  || statusCode == HttpStatus.SC_UNAUTHORIZED || statusCode == HttpStatus.SC_FORBIDDEN) {
				if (respHeadersKey != null && respHeadersKey.length > 0) {
					HashMap<String, String> respHeader = new HashMap<String, String>();
					for (int i = 0; i < respHeadersKey.length; i++) {
						Header header = response.getFirstHeader(respHeadersKey[i]);
						if (header != null) {
							String[] headArr = header.toString().split(":");
							respHeader.put(headArr[0].trim(), headArr[1].trim());
							Logger.d("HttpGetClientConnection",
									"get response and header>>>>>" + header);
						} else {
							respHeader.put(respHeadersKey[i], "");
						}
					}
					netMsg.setRespHeaderValue(respHeader);
				}
			} else {
				//抛出异常，进行站点切换
				throw new Exception("ServerException");
			}
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int rLen = 1024;
			byte[] buff = new byte[rLen];
			int readSize = -1;
			while ((readSize = bis.read(buff, 0, rLen)) != -1) {
				baos.write(buff, 0, readSize);
			}
			entity.consumeContent();
			serverReceiveData = baos.toByteArray();
			NetLogs.d_netstep(netMsg, this, "HttpGetClientConnection", "",
					"getResult");
			netMsg.reSetResendStatus();
		} catch (ClientProtocolException e) {
			System.out.println("-----ClientProtocolException-------");
			httpGet.abort();
			e.printStackTrace();
			throw new ConnException("ClientProtocolException," + e.getMessage());
		} catch (IOException e) {
			Logger.e("HttpGetClientConnection", "send():IOException");
			httpGet.abort();
			e.printStackTrace();
			throw new ConnException("IOException," + e.getMessage());
		} catch (Exception e) {
			System.out.println("-----Exception------");
			httpGet.abort();
			e.printStackTrace();
			throw new ConnException("Exception," + e.getMessage());
		} finally {
			httpClient.getConnectionManager().closeExpiredConnections();
			if (usProxy)
				httpClient.getConnectionManager().closeIdleConnections(1,
						TimeUnit.SECONDS);
		}

	}
	
	public DefaultHttpClient getHttpsClient(){
		// 证书验证的公钥
		InputStream sslPublicKeyIs = netMsg.getConnInfo().getServerInfo().getSslPublicKeyIs();

		//try {
		//	sslPublicKeyIs = OriginalContext.getContext().getAssets().open("certificate/ssl.pem");
		//}catch(Exception e){

		//}
		boolean isCheckCertificate = netMsg.getConnInfo().getServerInfo().isCheckCertificate();
		try {
			String path = netMsg.getConnInfo().getServerInfo().getSslPublicKeyPath();
			if (sslPublicKeyIs == null && !TextUtils.isEmpty(path))
				sslPublicKeyIs = (InputStream) new FileInputStream(path);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// 没有证书就绕过证书验证
		if(sslPublicKeyIs == null)
			isCheckCertificate = false;

		KeyStore trustStore = null;
		SSLSocketFactory sf = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			if (isCheckCertificate) {
				//使用券商提供的证书
				if(sslPublicKeyIs != null) {
					CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
					Certificate certificate = certificateFactory.generateCertificate(sslPublicKeyIs);
					trustStore.load(null, null);
					trustStore.setCertificateEntry("trust", certificate);
					sf = new SSLSocketFactory(trustStore);
					//判断是否是域名，若是域名则采用严格模式，不是则信任所有主机域名
					if (isDomain(netMsg.getConnInfo().getServerInfo().getAddress())){
						sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
					} else {
						sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					}
					Logger.d("HttpGetClientConnection", ">>>>> https 证书认证成功！");
				}else
					Logger.e("HttpGetClientConnection", ">>>>> https 缺少证书认证公钥！");
			} else{
				//信任所有主机，不做证书验证
		        trustStore.load(null, null);
		        sf = new MySSLSocketFactory(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

				Logger.d("HttpGetClientConnection",">>>>> https 绕过了证书认证！");
			}

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			Logger.d("HttpGetClientConnection",">>>>> https 证书认证异常！！");

			if(sslPublicKeyIs != null)
				Logger.d("HttpGetClientConnection",">>>>> https 证书认证公钥不正确！");

			e.printStackTrace();
			return new DefaultHttpClient();
		}
	}
	
	public static class MySSLSocketFactory extends SSLSocketFactory{

		SSLContext sslContext = SSLContext.getInstance("TLS");
		
		public MySSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);
			TrustManager tm = new X509TrustManager() {
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        };

	        sslContext.init(null, new TrustManager[] { tm }, null);
		}
		
		@Override
	    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
	        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	    }

	    @Override
	    public Socket createSocket() throws IOException {
	        return sslContext.getSocketFactory().createSocket();
	    }
	}

	private boolean isDomain(String address){
		boolean isDomain = false;
		try {
			URL url = new URL(address);
			String host = url.getHost();
			InetAddress inetAddress = InetAddress.getByName(host);
			if (host.equalsIgnoreCase(inetAddress.getHostAddress())){
				isDomain = false;
			} else {
				isDomain = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			return isDomain;
		}
	}

	@Override
	protected void saveCookie() {
		if (cookies != null  && cookies.size() > 0) {
			PersistentCookieStore cookieStore = new PersistentCookieStore(
					OriginalContext.getContext());
			for (Cookie cookie : cookies) {
				cookieStore.addCookie(cookie);
				Logger.d("HttpPostClientConnection",
						"get response and cookie>>>>>" + cookie.getName()
								+ " :" + cookie.getValue() + " Domain: " + cookie.getDomain());
			}
		}
	}
}
