/**
 * 
 */
package com.romaway.common.android.base;

import android.content.Context;

/**
 * @author duminghui
 * 
 */
public class OriginalContext
{
	private static Context context;

	public static void setContext(Context context)
	{
		OriginalContext.context = context;
	}

	public static Context getContext()
	{
		if(context == null)
			throw new NullPointerException("请先在Application 中调用 StockServices.init()进行初始化...");
		
		return context;
	}
}
