/**
 * 
 */
package com.romaway.common.net;

import com.romaway.common.net.conn.NetConnectionManagerProxy;
import com.romaway.common.net.sender.NetMsgSenderProxy;

/**
 * @author duminghui
 * 
 */
public class NetDestroy
{
	public static final void destroy()
	{
		NetMsgSenderProxy.getInstance().shutdown();
		NetConnectionManagerProxy.getInstance().destroy();
	}
}
