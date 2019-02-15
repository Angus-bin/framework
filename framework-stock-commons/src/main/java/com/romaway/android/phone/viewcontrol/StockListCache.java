package com.romaway.android.phone.viewcontrol;

/**
 * 证券列表缓存数据，用于在分时K线等上下切换证券
 * @author dumh
 *
 */
public class StockListCache
{
	public int count = 0;
	
	/**
	 * 当前资讯数据的请求key
	 */
	public String resourceKey;
	
	/**
	 * 证券代码
	 */
	public String[] stockCodes;
	
	/**
	 * 证券名称
	 */
	public String[] stockNames;
	
	/**
	 * 市场类型
	 */
	public String[] marketTypes;
	
	
	public void destroy()
	{
		int tmp = 0;
		if (stockCodes !=null && stockCodes.length>0)
		{
			tmp = stockCodes.length;
			for (int i=0;i<tmp / 2;i++)
			{
				stockCodes[i] = null;
				stockCodes[tmp -1 - i] = null;
			}
			stockCodes = null;
		}
		
		if (stockNames != null && stockNames.length>0)
		{
			tmp = stockNames.length;
			for (int i=0;i<tmp / 2;i++)
			{
				stockNames[i] = null;
				stockNames[tmp -1 - i] = null;
			}
			stockNames = null;
		}
		
		if (marketTypes != null && marketTypes.length>0)
		{
			tmp = marketTypes.length;
			for (int i=0;i<tmp / 2;i++)
			{
				marketTypes[i] = null;
				marketTypes[tmp -1 - i] = null;
			}
			marketTypes = null;
		}
			
	}
	
	
}
