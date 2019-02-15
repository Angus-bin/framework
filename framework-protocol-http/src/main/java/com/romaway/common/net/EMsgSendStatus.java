/**
 * 
 */
package com.romaway.common.net;

import android.support.annotation.Keep;

/**
 * 详细发送状态，共有以下几个状态<br>
 * wait:等待发送<br>
 * sending:发送中<br>
 * success:发送完成<br>
 * sentTimeout:发送超时<br>
 * sendDrop:发送终止<br>
 * netError:网络错误<br>
 * connError:服务器连接出错<br>
 * socketClosed:socket已经关闭<br>
 * @author duminghui
 * 
 */
@Keep
public enum EMsgSendStatus
{
	/**
	 * 等待发送
	 */
	wait,
	/**
	 * 发送中
	 */
	sending,
	/**
	 * 发送完成
	 */
	success,
	/**
	 * 发送超时
	 */
	sentTimeout,
	/**
	 * 发送终止
	 */
	sendDrop,
	/**
	 * 网络错误
	 */
	netError,

	/**
	 * 服务器连接出错
	 */
	connError,

	/**
	 * socket已经关闭
	 */
	socketClosed;
}
