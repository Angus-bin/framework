package com.romaway.android.phone.cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.hq.HQFSZHProtocol;

public class MinuteCacheDao
{
	private MinuteCacheSQLHelper helper;
	private static MinuteCacheDao dao = new MinuteCacheDao();

	private ReadWriteLock lock = new ReentrantReadWriteLock(true);
	private Lock readLock = lock.readLock();
	private Lock writeLock = lock.writeLock();

	public MinuteCacheDao()
	{
		if (helper == null)
			helper = new MinuteCacheSQLHelper(OriginalContext.getContext(), 1);
	}

	public static MinuteCacheDao getInstance()
	{
		return dao;
	}

	/**
	 * 可读写数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getWdb()
	{
		return helper.getWritableDatabase();
	}

	/**
	 * 可读数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getRdb()
	{
		return helper.getReadableDatabase();
	}

	public void add(SQLiteDatabase db, int date, String stock_code,
	        String stock_name, int market_id, int type, HQFSZHProtocol ptl)
	{
		int count = ptl.resp_dwTime_s.length - 1;
		for (int i = 0; i < ptl.resp_dwTime_s.length; i++)
		{
			if (i > 0 && ptl.resp_dwTime_s[i - 1] == ptl.resp_dwTime_s[i])
			{ // 经常有俩个数据是重复的，eg：1030,1030; 故如果dwtime一样时，要update
				db.execSQL(MinuteCacheSQLHelper.UPDATE_ZJCJ_CJJJ, new Object[] {
				        ptl.resp_nZjcj_s[i], ptl.resp_nZdf_s[i],
				        ptl.resp_nCjss_s[i], ptl.resp_nCjje_s[i],
				        ptl.resp_nCjjj_s[i], ptl.resp_nCcl_s[i],
				        ptl.resp_nLb_s[i], date, stock_code, market_id });

			} else if (i == count && !CacheUtils.isEven(ptl.resp_dwTime_s[i]))
			{// 最后一个 dwtime为奇数时，不加进去
				return;
			} else
			{
				db.execSQL(MinuteCacheSQLHelper.INSERT_DATA, new Object[] {
				        date, stock_code, stock_name, market_id, type,
				        ptl.resp_dwTime_s[i], ptl.resp_nZjcj_s[i],
				        ptl.resp_nZdf_s[i], ptl.resp_nCjss_s[i],
				        ptl.resp_nCjje_s[i], ptl.resp_nCjjj_s[i],
				        ptl.resp_nCcl_s[i], ptl.resp_nLb_s[i], ptl.resp_nZrsp,
				        ptl.resp_nZgcj, ptl.resp_nZdcj });
			}
		}
	}

	/**
	 * 删除单只股票数据
	 * 
	 * @param db
	 * @param date
	 * @param stock_code
	 * @param market_id
	 */
	public void del_stock_data(SQLiteDatabase db, int date, String stock_code,
	        int market_id)
	{
		db.execSQL(MinuteCacheSQLHelper.DELETE_STOCK_DATA, new Object[] { date,
		        stock_code, market_id });
	}

	/**
	 * 返回 ：id,date,stock_code,stock_name,market_id,type,dwtime,zrsp,zgcj,zdcj,
	 * zjcj
	 * 
	 * @param db
	 * @param date
	 * @param stock_code
	 * @param market_id
	 * @return
	 */
	public Cursor sel(SQLiteDatabase db, int date, String stock_code,
	        int market_id)
	{
		if (!db.isOpen())
			return null;
		Cursor cursor = db.rawQuery(MinuteCacheSQLHelper.SELECT_ALL,
		        new String[] { date + "", stock_code, market_id + "" });
		return cursor;
	}

	public Cursor sel_dwtime(SQLiteDatabase db, int date, String stock_code,
	        int market_id)
	{
		if (!db.isOpen() || market_id==-1)
			return null;
		Cursor cursor = db.rawQuery(MinuteCacheSQLHelper.SELECT_DWTIME,
		        new String[] { date + "", stock_code, market_id + "" });
		return cursor;
	}

}
