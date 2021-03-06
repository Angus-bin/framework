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
public enum EProtocolStatus
{
	/**
	 * 等待处理
	 */
	wait,
	/**
	 * 成功
	 */
	success,
	/**
	 * 服务器出错
	 */
	serverError,
	/**
	 * 解析服务器返回的数据出错
	 */
	parseError;
}
