package com.romaway.android.phone.cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.hq.HQKXProtocol;
import com.romaway.common.protocol.hq.HQKXZHProtocol;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class KlineCacheDao
{
	private KlineCacheSQLHelper helper;
	private static KlineCacheDao instance;
	// ********数据库加锁*************************
	private ReadWriteLock lock = new ReentrantReadWriteLock(true);
	private Lock readLock = lock.readLock();
	private Lock writeLock = lock.writeLock();

	public KlineCacheDao()
	{
		if (helper == null)
			helper = new KlineCacheSQLHelper(OriginalContext.getContext(), 1);
	}

	public static KlineCacheDao getInstance()
	{
		// if (instance == null)
		// {
		// synchronized (KlineCacheDao.class)
		// {
		// if (instance == null)
		// {
		instance = new KlineCacheDao();
		// }
		// }
		// }
		return instance;
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

	public void add_all(SQLiteDatabase db, String stock_code,
	        String stock_name, int market_id, int stock_type, short kline_type,
	        HQKXProtocol ptl)
	{
		if (ptl.resp_dwTime_s == null)
			return;
		for (int i = 0; i < ptl.resp_dwTime_s.length; i++)
		{
			db.execSQL(KlineCacheSQLHelper.INSERT_DATA, new Object[] {
			        stock_code, stock_name, market_id, stock_type, kline_type,
			        ptl.resp_dwTime_s[i], ptl.resp_nYClose_s[i],
			        ptl.resp_nOpen_s[i], ptl.resp_nZgcj_s[i],
			        ptl.resp_nZdcj_s[i], ptl.resp_nClose_s[i],
			        ptl.resp_nZdf_s[i], ptl.resp_nCjje_s[i],
			        ptl.resp_nCjss_s[i], ptl.resp_nCcl_s[i],
			        /*ptl.resp_nHsl_s[i], ptl.resp_nSyl_s[i],*/ ptl.resp_nMA1_s[i],
			        ptl.resp_nMA2_s[i], ptl.resp_nMA3_s[i],
			        ptl.resp_nTech1_s[i], ptl.resp_nTech2_s[i],
			        ptl.resp_nTech3_s[i], /*ptl.resp_nHsj_s[i],*/
			        ptl.resp_nZd_s[i]/*, ptl.resp_wsXxgg_s[i],
			        ptl.resp_bFlag_s[i]*/ });
		}
	}

	public void del_stock_data(SQLiteDatabase db, String stock_code,
	        int market_id, short kline_type)
	{
		db.execSQL(KlineCacheSQLHelper.DELETE_STOCK_DATE, new Object[] {
		        stock_code, market_id, kline_type });
	}

	public Cursor sel(SQLiteDatabase db, String stock_code, int market_id,
	        short kline_type)
	{
		Cursor cursor = db.rawQuery(KlineCacheSQLHelper.SELECT_ALL,
		        new String[] { stock_code, market_id + "", kline_type + "" });
		return cursor;
	}

	public Cursor sel_dwtime(SQLiteDatabase db, String stock_code,
	        int market_id, short kline_type)
	{
		if (!db.isOpen())
			return null;
		Cursor cursor = db.rawQuery(KlineCacheSQLHelper.SELECT_DWTIME,
		        new String[] { stock_code, market_id + "", kline_type + "" });
		return cursor;
	}

}
