/**
 * 
 */
package com.romaway.common.protocol;

import android.support.annotation.Keep;

/**
 * @author duminghui
 * 
 */
@Keep
public abstract class AProtocolCoder<T extends AProtocol>
{
	/**
	 * 购建消息体
	 * 
	 * @param protocol
	 * @return
	 */
	protected abstract byte[] encode(T protocol);

	/**
	 * 
	 * @param key 读取缓存的功能KEY
	 * @param protocol 将缓存数据修改协议之后又回传给框架
	 * @return
	 * @throws ProtocolParserException
	 */
	protected T readCache(String key, T protocol) throws ProtocolParserException{
		return null;
	};
	
	/**
	 * 保存缓存数据接口，将参数protocol中的数据以key-value的方式进行保存
	 * @param key
	 * @param protocol
	 * @throws ProtocolParserException
	 */
	protected void saveCache(String key, T protocol) throws ProtocolParserException{
		
	};
	
	/**
	 * 解析消息体
	 * 
	 * @param protocol
	 */
	protected abstract void decode(T protocol) throws ProtocolParserException;
}
