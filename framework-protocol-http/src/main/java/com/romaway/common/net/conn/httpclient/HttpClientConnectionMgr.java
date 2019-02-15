/**
 * 
 */
package com.romaway.common.net.conn.httpclient;

import com.romaway.common.net.conn.AConnection;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.conn.IConnectionManager;

/**
 * @author duminghui
 * 
 */
public class HttpClientConnectionMgr implements IConnectionManager
{
	public HttpClientConnectionMgr()
	{
	}

	@Override
	public AConnection crate(ConnInfo connInfo)
	{
		return new HttpPostClientConnection();
	}

	@Override
	public void init()
	{
		HttpClientMgr.init();
	}

	@Override
	public void destroy()
	{
		HttpClientMgr.shutdown();
	}

}
