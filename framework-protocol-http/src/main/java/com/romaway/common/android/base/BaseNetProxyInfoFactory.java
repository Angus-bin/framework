/**
 * 
 */
package com.romaway.common.android.base;

import com.romaway.common.android.netstatus.NetStatus;
import com.romaway.common.net.proxy.ANetProxyInfoFactory;
import com.romaway.common.net.proxy.NetProxyInfo;

/**
 * @author duminghui
 * 
 */
public class BaseNetProxyInfoFactory extends ANetProxyInfoFactory
{
	@Override
	public NetProxyInfo getNetProxyInfo()
	{
		NetStatus netStatus = NetStatus.getInstance();
		return new NetProxyInfo(netStatus.getProxyHost(),
		        netStatus.getProxyPort());
	}

}
