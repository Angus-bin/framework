package com.romaway.android.phone.utils;

import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author wanlaihuan
 * 股票行情列表临时缓存机制，用于存储股票行情列表数据，左右滑页会用到该机制。
 */
public class StockCacheInfo {
	private static List<StockCacheInfo> cacheStockInfoList = new ArrayList<StockCacheInfo>();
	 
	public static void addToCacheList(StockCacheInfo stockInfo){
		cacheStockInfoList.add(stockInfo);
	}
	
	public static void clearCacheList(){
		cacheStockInfoList.clear();
	}
	public static List<StockCacheInfo> getCacheList(){
		return cacheStockInfoList;
	}
	public static int getCacheListCount(){
		return 	cacheStockInfoList.size();
	}
	
	/***
	 * 
	 * @param hqData
	 * @param dataIndex hqData[][dataIndex]的下标 0:股票名称索引；1：股票代码索引；2：市场ID索引；3：股票类型索引
	 */
	public static void saveListToCache(String[][] hqData, int[] dataIndex){
		if(hqData != null){
			for(int i = 0; i < hqData.length;i++){
				if(hqData[i] != null){
					if(StringUtils.isEmpty(hqData[i][dataIndex[0]]) && 
							StringUtils.isEmpty(hqData[i][dataIndex[1]]))
						continue;
					StockCacheInfo si = new StockCacheInfo();
					si.stockName = hqData[i][dataIndex[0]];
					si.stockCode = hqData[i][dataIndex[1]];
					si.marketId = (short) NumberUtils.toInt(hqData[i][dataIndex[2]]);
					si.stockType = (short) NumberUtils.toInt(hqData[i][dataIndex[3]]);
					StockCacheInfo.addToCacheList(si);
				}
			}
    	}
	}
	
	/***
	 * @param hqData
	 * @param dataIndex hqData[][dataIndex]的下标 0:股票名称索引；1：股票代码索引；2：市场ID索引；3：股票类型索引
	 * @param range		传递的数组范围(起始角标, 数量)
	 */
	public static void saveListToCache(String[][] hqData, int[] dataIndex, int[] range){
		if(hqData != null){
			int startIndex = range[0];
			int count = range[1];
			try{
				if(startIndex >= 0 && startIndex < hqData.length){
					//      20         4								//  100      20         109
					count = range[1] > hqData.length ? hqData.length : (startIndex + range[1] > hqData.length ? hqData.length - startIndex : range[1]);
					for (int i = startIndex; i < startIndex + count; i++) {
						if(hqData[i] != null){
							if(StringUtils.isEmpty(hqData[i][dataIndex[0]]) && 
									StringUtils.isEmpty(hqData[i][dataIndex[1]]))
								continue;
							StockCacheInfo si = new StockCacheInfo();
							si.stockName = hqData[i][dataIndex[0]];
							si.stockCode = hqData[i][dataIndex[1]];
							si.marketId = (short) NumberUtils.toInt(hqData[i][dataIndex[2]]);
							si.stockType = (short) NumberUtils.toInt(hqData[i][dataIndex[3]]);
							StockCacheInfo.addToCacheList(si);
						}
					}
				}
			}catch(Exception e){
				Logger.w("StockCacheInfo", "传递参数值异常"+e.getMessage());
			}
		}
	}
	
	/**
	 * 将某条列表记录保存到列表缓存中
	 * @param stockName
	 * @param stockCode
	 * @param marketId
	 * @param stockType
	 */
	public static void saveToCacheList(String stockName,String stockCode,
		String marketId,String stockType){
		StockCacheInfo si = new StockCacheInfo();
		si.stockName = stockName;
		si.stockCode = stockCode;
		si.marketId = (short) NumberUtils.toInt(marketId);
		si.stockType = (short) NumberUtils.toInt(stockType);
		StockCacheInfo.addToCacheList(si);
	}

	/**
	 * 获取列表缓存对象:
	 * @param stockName
	 * @param stockCode
	 * @param marketId
	 * @param stockType
	 */
	public static StockCacheInfo getStockCacheInfo(String stockName,String stockCode,
									   String marketId,String stockType){
		StockCacheInfo si = new StockCacheInfo();
		si.stockName = stockName;
		si.stockCode = stockCode;
		si.marketId = (short) NumberUtils.toInt(marketId);
		si.stockType = (short) NumberUtils.toInt(stockType);
		return si;
	}
	
	public String stockName;
	public String stockCode;
	public short stockType;
	public short marketId;
}
