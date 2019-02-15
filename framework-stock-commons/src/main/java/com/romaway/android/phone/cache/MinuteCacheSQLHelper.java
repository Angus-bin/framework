package com.romaway.android.phone.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MinuteCacheSQLHelper extends SQLiteOpenHelper
{
	private static final String name = "minute_cache_data";

	public final static String CTREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
	        + name
	        + " (date INTEGER,stock_code VARCHAR,stock_name VARCHAR,market_id INTEGER,type INTEGER,dwtime INTEGER,zjcj INTEGER,zdf INTEGER,cjss INTEGER,cjje INTEGER,cjjj INTEGER,ccl INTEGER,lb INTEGER,kfZrsp INTEGER,kfZgcj INTEGER,kfZdcj INTEGER"
	        // +
			// ", constraint code_market_date_dwtime unique(stock_code,market_id,date,dwtime))";//服务器下发数据经常有问题，经常爆，去掉限制，也能画，可能是错的而已
	        + ")";
	public final static String INSERT_DATA = "insert into "
	        + name
	        + " (date,stock_code,stock_name,market_id,type,dwtime,zjcj,zdf,cjss,cjje,cjjj,ccl,lb,kfZrsp,kfZgcj,kfZdcj)"
	        + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";// 16

	public final static String UPDATE_ZJCJ_CJJJ = "update "
	        + name
	        + " SET zjcj=? and zdf=? and cjss=? and cjje=? and cjjj=? and ccl=? and lb=? where dwtime =? and date=? and stock_code=? and market_id=?";

	/** 删除单只股票数据 **/
	public final static String DELETE_STOCK_DATA = "delete from " + name
	        + " where date=? and stock_code=? and market_id=?";

	public final static String SELECT_ALL = "select *from " + name
	        + " where date=? and stock_code=? and market_id=?";

	public final static String SELECT_DWTIME = "select dwtime from " + name
	        + " where date=? and stock_code=? and market_id=?";

	public MinuteCacheSQLHelper(Context context, int version)
	{
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CTREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}

}
