/**
 * 
 */
package com.romaway.common.net.proxy;

/**
 * 代理网关获取代理
 * 
 * @author duminghui
 * 
 */
public class NetProxyInfoProxy
{
    private final static NetProxyInfoProxy instance = new NetProxyInfoProxy();
    private ANetProxyInfoFactory factory;

    private NetProxyInfoProxy()
    {

    }

    public final static NetProxyInfoProxy getInstance()
    {
        return instance;
    }

    public final void setNetProxyInfoFactory(ANetProxyInfoFactory factory)
    {
        this.factory = factory;
    }

    public final NetProxyInfo getNetProxyInfo()
    {
        return factory.getNetProxyInfo();
    }
}
