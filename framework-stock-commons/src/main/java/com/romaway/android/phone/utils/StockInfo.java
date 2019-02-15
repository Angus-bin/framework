package com.romaway.android.phone.utils;

public class StockInfo
{

	private static String stockName;
	private static String stockCode;
	public StockInfo()
    {
	    
    }
	public static void getData(String stockN,String stockC){
		stockName = stockN;
		  stockCode = stockC;
	}
	
	
}
