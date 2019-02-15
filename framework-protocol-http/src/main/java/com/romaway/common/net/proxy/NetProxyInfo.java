/**
 * 
 */
package com.romaway.common.net.proxy;

/**
 * @author duminghui
 * 
 */
public class NetProxyInfo
{
    private String host;
    private int port;

    public NetProxyInfo(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    /**
     * @return the host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @return the port
     */
    public int getPort()
    {
        return port;
    }

}
