package com.romaway.android.phone.cache;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.romaway.android.phone.config.KConfigs;
import com.romaway.common.protocol.hq.HQFSZHProtocol;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * 分时增量缓存
 * 
 * @author qinyn
 * 
 */
public class MinuteCacheUtil
{
	private static MinuteCacheUtil instance = new MinuteCacheUtil();

	public static MinuteCacheUtil getInstance()
	{
		return instance;
	}

	/**
	 * 拼接数据
	 * 
	 * @param context
	 * @param stock_code
	 * @param market_id
	 * @param ptl
	 */
	public HQFSZHProtocol splicingData(String stock_code, int market_id,
	        HQFSZHProtocol ptl)
	{
		if (!KConfigs.hasHQCache){
			return ptl;
		}
		MinuteCacheDao dao = MinuteCacheDao.getInstance();
		SQLiteDatabase db = dao.getRdb();
		int date = CacheUtils.getServerDate();
		Cursor cursor = dao.sel(db, date, stock_code, market_id);
		int count_local = cursor.getCount();
		try
		{
			if (count_local == 0)
			{// ************没有本地数据***************************
				Logger.i("", "---------count_local------" + count_local);
				return ptl;
			} else
			{
				// *********本地取数据*****************************
				int[] dwTime_s_local = new int[count_local];
				int[] nZjcj_s_local = new int[count_local];
				int[] nZdf_s_local = new int[count_local];
				int[] nCjss_s_local = new int[count_local];
				int[] nCjje_s_local = new int[count_local];
				int[] nCjjj_s_local = new int[count_local];
				int[] nCcl_s_local = new int[count_local];
				int[] nLb_s_local = new int[count_local];
				int i = 0;
				while (cursor.moveToNext())
				{// 取出本地数据
					dwTime_s_local[i] = cursor.getInt(5);
					nZjcj_s_local[i] = cursor.getInt(6);
					nZdf_s_local[i] = cursor.getInt(7);
					nCjss_s_local[i] = cursor.getInt(8);
					nCjje_s_local[i] = cursor.getInt(9);
					nCjjj_s_local[i] = cursor.getInt(10);
					nCcl_s_local[i] = cursor.getInt(11);
					nLb_s_local[i] = cursor.getInt(12);
					i++;
				}
				cursor.moveToPrevious();
				int nZrsp_local = cursor.getInt(13);
				int nZgcj_local = cursor.getInt(14);
				int nZdcj_local = cursor.getInt(15);
				// *********************************************
				if (ptl.resp_wFSDataCount <= 0)
				{// 如果服务器无数据,直接拿本地数据
					ptl.resp_dwTime_s = dwTime_s_local;
					ptl.resp_nZjcj_s = nZjcj_s_local;
					ptl.resp_nZdf_s = nZdf_s_local;
					ptl.resp_nCjss_s = nCjss_s_local;
					ptl.resp_nCjje_s = nCjje_s_local;
					ptl.resp_nCjjj_s = nCjjj_s_local;
					ptl.resp_nCcl_s = nCcl_s_local;
					ptl.resp_nLb_s = nLb_s_local;
					ptl.resp_nZrsp = nZrsp_local;
					ptl.resp_nZgcj = nZgcj_local;
					ptl.resp_nZdcj = nZdcj_local;

				} else
				{
					// 服务器返回来数据会返回几个与本地数据相同的，故得出数据索引，在此数据索引之前的全部去掉
					int length_resp_dwTime = ptl.resp_dwTime_s.length;
					int[] dwtime_s_server = new int[] {};
					int[] nZjcj_s_server = new int[] {};
					int[] nZdf_s_server = new int[] {};
					int[] nCjss_s_server = new int[] {};
					int[] nCjje_s_server = new int[] {};
					int[] nCjjj_s_server = new int[] {};
					int[] nCcl_s_server = new int[] {};
					int[] nLb_s_server = new int[] {};
					int index = 0;
					for (int k = length_resp_dwTime - 1; k >= 0; k--)
					{// 记住一定要逆序，因为eg: 传过去1012 服务器会有返回1010 1012 1014 1014这种情况
						if (ptl.resp_dwTime_s[k] == dwTime_s_local[count_local - 1])
						{
							index = k;
							break;
						}
					}
					if (index == length_resp_dwTime - 1)
					{// 本地最后一个跟服务器返回最后一个是相同的，则说明此次返回数据无用

					} else
					{
						int length1 = length_resp_dwTime - 1 - index;
						dwtime_s_server = new int[length1];
						nZjcj_s_server = new int[length1];
						nZdf_s_server = new int[length1];
						nCjss_s_server = new int[length1];
						nCjje_s_server = new int[length1];
						nCjjj_s_server = new int[length1];
						nCcl_s_server = new int[length1];
						nLb_s_server = new int[length1];
						for (int k = 0; k < length_resp_dwTime; k++)
						{// 将数据索引之前的数据去掉，得到跟本地不重复的数据
							if (k > index)
							{
								int xx = k - index - 1;
								dwtime_s_server[xx] = ptl.resp_dwTime_s[k];
								nZjcj_s_server[xx] = ptl.resp_nZjcj_s[k];
								nZdf_s_server[xx] = ptl.resp_nZdf_s[k];
								nCjss_s_server[xx] = ptl.resp_nCjss_s[k];
								nCjje_s_server[xx] = ptl.resp_nCjje_s[k];
								nCjjj_s_server[xx] = ptl.resp_nCjjj_s[k];
								nCcl_s_server[xx] = ptl.resp_nCcl_s[k];
								nLb_s_server[xx] = ptl.resp_nLb_s[k];
							}
						}
					}

					// ****将本地数据和服务器来的数据拼接*******************
					int length_server = dwtime_s_server.length;
					if (length_server == 0)
					{// 这种情况是15.30后，还是会返回15.28 ,15.30俩个数据点
						ptl.resp_dwTime_s = dwTime_s_local;
						ptl.resp_nZjcj_s = nZjcj_s_local;
						ptl.resp_nZdf_s = nZdf_s_local;
						ptl.resp_nCjss_s = nCjss_s_local;
						ptl.resp_nCjje_s = nCjje_s_local;
						ptl.resp_nCjjj_s = nCjjj_s_local;
						ptl.resp_nCcl_s = nCcl_s_local;
						ptl.resp_nLb_s = nLb_s_local;

					} else if (!CacheUtils
					        .isEven(dwtime_s_server[length_server - 1]))
					{// 如果采样点最后是奇数，要去掉
						dwtime_s_server = CacheUtils
						        .delArrayLastValue(dwtime_s_server);
						nZjcj_s_server = CacheUtils
						        .delArrayLastValue(nZjcj_s_server);
						nZdf_s_server = CacheUtils
						        .delArrayLastValue(nZdf_s_server);
						nCjss_s_server = CacheUtils
						        .delArrayLastValue(nCjss_s_server);
						nCjje_s_server = CacheUtils
						        .delArrayLastValue(nCjje_s_server);
						nCjjj_s_server = CacheUtils
						        .delArrayLastValue(nCjjj_s_server);
						nCcl_s_server = CacheUtils
						        .delArrayLastValue(nCcl_s_server);
						nLb_s_server = CacheUtils
						        .delArrayLastValue(nLb_s_server);
						ptl.resp_dwTime_s = CacheUtils.splicingIntArray(
						        dwTime_s_local, dwtime_s_server);// 时间拼接
						ptl.resp_nZjcj_s = CacheUtils.splicingIntArray(
						        nZjcj_s_local, nZjcj_s_server);// 最近成交拼接
						ptl.resp_nZdf_s = CacheUtils.splicingIntArray(
						        nZdf_s_local, nZdf_s_server);
						ptl.resp_nCjss_s = CacheUtils.splicingIntArray(
						        nCjss_s_local, nCjss_s_server);
						ptl.resp_nCjje_s = CacheUtils.splicingIntArray(
						        nCjje_s_local, nCjje_s_server);
						ptl.resp_nCjjj_s = CacheUtils.splicingIntArray(
						        nCjjj_s_local, nCjjj_s_server);
						ptl.resp_nCcl_s = CacheUtils.splicingIntArray(
						        nCcl_s_local, nCcl_s_server);
						ptl.resp_nLb_s = CacheUtils.splicingIntArray(
						        nLb_s_local, nLb_s_server);

					} else
					{
						if (dwtime_s_server.length > 1
						        && dwtime_s_server[dwtime_s_server.length - 1] == dwtime_s_server[dwtime_s_server.length - 2])
						{ // 最后俩个是相同的话
							dwtime_s_server = CacheUtils
							        .delArrayLastValue(dwtime_s_server);
							nZjcj_s_server = CacheUtils
							        .delArrayLastValue(nZjcj_s_server);
							nZdf_s_server = CacheUtils
							        .delArrayLastValue(nZdf_s_server);
							nCjss_s_server = CacheUtils
							        .delArrayLastValue(nCjss_s_server);
							nCjje_s_server = CacheUtils
							        .delArrayLastValue(nCjje_s_server);
							nCjjj_s_server = CacheUtils
							        .delArrayLastValue(nCjjj_s_server);
							nCcl_s_server = CacheUtils
							        .delArrayLastValue(nCcl_s_server);
							nLb_s_server = CacheUtils
							        .delArrayLastValue(nLb_s_server);
						}
						ptl.resp_dwTime_s = CacheUtils.splicingIntArray(
						        dwTime_s_local, dwtime_s_server);// 时间拼接
						ptl.resp_nZjcj_s = CacheUtils.splicingIntArray(
						        nZjcj_s_local, nZjcj_s_server);// 最近成交拼接
						ptl.resp_nZdf_s = CacheUtils.splicingIntArray(
						        nZdf_s_local, nZdf_s_server);
						ptl.resp_nCjss_s = CacheUtils.splicingIntArray(
						        nCjss_s_local, nCjss_s_server);
						ptl.resp_nCjje_s = CacheUtils.splicingIntArray(
						        nCjje_s_local, nCjje_s_server);
						ptl.resp_nCjjj_s = CacheUtils.splicingIntArray(
						        nCjjj_s_local, nCjjj_s_server);
						ptl.resp_nCcl_s = CacheUtils.splicingIntArray(
						        nCcl_s_local, nCcl_s_server);
						ptl.resp_nLb_s = CacheUtils.splicingIntArray(
						        nLb_s_local, nLb_s_server);
					}
				}
				ptl.resp_wFSDataCount = (short) ptl.resp_dwTime_s.length;
			}
			return ptl;
		} finally
		{
			cursor.close();
			db.close();
		}
	}

	public void add(HQFSZHProtocol ptl, String stock_code, String stock_name,
	        int market_id, int type)
	{
		if (!KConfigs.hasHQCache){
			return;
		}
		MinuteCacheDao dao = MinuteCacheDao.getInstance();
		SQLiteDatabase db = dao.getWdb();
		try
		{
			db.beginTransaction();
			dao.add(db, ptl.resp_dwDate, stock_code, stock_name, market_id,
			        type, ptl);
			db.setTransactionSuccessful();
		} finally
		{
			db.endTransaction();
			db.close();
		}
	}

	/**
	 * 1.如果无当天数据，则为第一次，返回0； 2.如果有当天数据，则返回最后一个采样点的时间格式：如 1030
	 */
	public int selLastDwtime(String stock_code, int market_id)
	{
		if (!KConfigs.hasHQCache){
			return 0;
		}
		if (market_id == -1 || StringUtils.isEmpty(stock_code)){
			return 0;
		}
		MinuteCacheDao dao = MinuteCacheDao.getInstance();
		SQLiteDatabase db = dao.getRdb();
		int date = CacheUtils.getServerDate();
		Cursor cursor = dao.sel_dwtime(db, date, stock_code, market_id);
		try
		{
			if (cursor == null || cursor.getCount() == 0)
			{
				return 0;
			}
			cursor.moveToLast();
			return cursor.getInt(0);
		} catch(Exception e){
			return 0;
		}finally
		{
			cursor.close();
			db.close();
		}
	}

	/** 删除今天的数据 */
	public void delStockData(String stock_code, int market_id)
	{
		MinuteCacheDao dao = MinuteCacheDao.getInstance();
		SQLiteDatabase db = dao.getWdb();
		int date = CacheUtils.getServerDate();
		try
		{
			dao.del_stock_data(db, date, stock_code, market_id);
		} finally
		{
			db.close();
		}
	}

}
