/**
 * 
 */
package com.romaway.common.net;

import android.support.annotation.Keep;

/**
 * 消息等级<br>
 * level的值必须从0开始，加1递增，否则会引起程序错误 <br>
 * level的值越小优先级别越高
 * 
 * @author duminghui
 * 
 */
@Keep
public enum EMsgLevel
{

	/**
	 * 此级别的线程，无论是否同一个Msg,都会更新成最新的
	 */
	high(0),
	/**
	 * 前端
	 */
	normal(1),
	/**
	 * 后台
	 */
	low(2);
	private int level;

	private EMsgLevel(int level)
	{
		this.level = level;
	}

	public int getLevel()
	{
		return level;
	}

}
