/**
 * 
 */
package com.romaway.common.net.conn.httpclient;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/**
 * @author duminghui
 * 
 */
public class HttpClientMgr {
	private static ClientConnectionManager cm;
	private static HttpParams params;

	public static final void init() {
		params = new BasicHttpParams();
		// 增加最大连接到200
		ConnManagerParams.setMaxTotalConnections(params, 200);
		// 增加每个路由的默认最大连接到20
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
		ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		cm = new ThreadSafeClientConnManager(params, schemeRegistry);
	}

	public static final DefaultHttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
		httpClient.getCookieSpecs().register("kds", new CookieSpecFactory() {

			@Override
			public CookieSpec newInstance(HttpParams params) {
				// TODO Auto-generated method stub
				return new RomaCookieSpec();
			}
		});
		//httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
        //        "kds");
		HttpClientParams.setCookiePolicy(httpClient.getParams(), "kds");
		return httpClient;
	}

	public static final void shutdown() {
		cm.shutdown();
	}
	
	public static final String[] DATE_PATTERNS = new String[] {
        "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz",
        "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z",
        "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z",
        "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z",
        "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z",
        "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z",
        "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z",
        "E, dd-MMM-yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz" }; 
	
	static class RomaCookieSpec extends BrowserCompatSpec {
		@SuppressWarnings("deprecation")
        public RomaCookieSpec() {
			super();
			registerAttribHandler(ClientCookie.EXPIRES_ATTR,
			        new BasicExpiresHandler(DATE_PATTERNS) {
                @Override
                public void parse(SetCookie cookie, String value)
                        throws MalformedCookieException {
                    super.parse(cookie, value);
                    
                    //Logger.d("tag", "RomaCookieSpec value:"+value);
                }
            });
		}
	}
}
