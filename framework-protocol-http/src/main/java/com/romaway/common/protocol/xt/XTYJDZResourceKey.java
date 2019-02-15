package com.romaway.common.protocol.xt;

import android.support.annotation.Keep;

/**
 * 预警定制请求参数
 * 
 * @author qinyn
 * 
 */
@Keep
public class XTYJDZResourceKey
{
	/** 股价涨跌 **/
	public static final String ServiceId_STOCK_PRICE = "8201";
	/** 股票涨跌幅 **/
	public static final String ServiceId_STOCK_ZDF = "8202";
	/** 股票涨跌停 **/
	public static final String ServiceId_STOCK_ZDT = "8207";

	// ***************************************************
	/** 指数涨跌 **/
	public static final String ServiceId_INDEX_POINT = "8222";
	/** 指数涨跌幅 **/
	public static final String ServiceId_INDEX_ZDF = "8223";

}
