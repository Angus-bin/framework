/**
 * 
 */
package com.romaway.common.protocol;

import android.support.annotation.Keep;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络信息编解码器管理类<br>
 * 1.根据Protocol组装网络字节数组包
 * 
 * @author duminghui
 * 
 */
@Keep
public class ProtocolCoderMgr
{
	private static final ProtocolCoderMgr instance = new ProtocolCoderMgr();

	private Map<Class<? extends AProtocol>, AProtocolCoder<?>> aProtocolCoders = new HashMap<Class<? extends AProtocol>, AProtocolCoder<?>>();

	private ProtocolCoderMgr()
	{
	}

	public final static ProtocolCoderMgr getInstance()
	{
		return instance;
	}

	public final void putCoder(Class<? extends AProtocol> cls,
	        AProtocolCoder<? extends AProtocol> coder)
	{
		aProtocolCoders.put(cls, coder);
	}

	public final AProtocolCoder<? extends AProtocol> getCoder(
	        Class<? extends AProtocol> cls)
	{
		return aProtocolCoders.get(cls);
	}
}
