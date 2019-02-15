/**
 * 
 */
package com.romaway.common.net.receiver;

import android.support.annotation.Keep;

import com.romaway.common.net.ANetMsg;

/**
 * @author duminghui
 * 
 */
@Keep
public interface INetReceiveListener
{
	/**
	 * 处理返回的数据
	 * 
	 * @param msg
	 */
	void onReceive(ANetMsg msg);
}
