package com.romaway.common.protocol.hq.cache;

import java.io.Serializable;

/**
 * 行情缓存基类
 * @author wala
 *
 */
public class HQProtocolBaseCache implements Serializable{

	/**
	 * 标记
	 */
	public String flag;
	
	/**
	 * 消息版本
	 */
	public int cmdVersion = 0;
}
