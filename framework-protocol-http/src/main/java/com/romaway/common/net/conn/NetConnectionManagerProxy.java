/**
 * 
 */
package com.romaway.common.net.conn;

/**
 * @author duminghui
 * 
 */
public class NetConnectionManagerProxy
{
	private final static NetConnectionManagerProxy instance = new NetConnectionManagerProxy();
	private IConnectionManager mgr;

	public static final NetConnectionManagerProxy getInstance()
	{
		return instance;
	}

	public void setManager(IConnectionManager mgr)
	{
		this.mgr = mgr;
	}

	public AConnection create(ConnInfo connInfo)
	{
		return mgr.crate(connInfo);
	}

	public final void init()
	{
		mgr.init();
	}

	public final void destroy()
	{
		mgr.destroy();
	}
}
