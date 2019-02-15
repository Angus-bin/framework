package com.romaway.android.phone.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KlineCacheSQLHelper extends SQLiteOpenHelper
{
	private static final String name = "kline_cache_data";

	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
	        + name
	        + " (stock_code VARCHAR,stock_name VARCHAR,market_id INTEGER,stock_type INTEGER,kline_type SHORT,"
	        + "dwtime INTEGER,nYClose INTEGER,nOpen INTEGER,nZgcj INTEGER,nZdcj INTEGER,nClose INTEGER,nZdf INTEGER,nCjje INTEGER,"
	        + "nCjss INTEGER,nCcl INTEGER,nHsl INTEGER,nSyl INTEGER,nMA1 INTEGER,nMA2 INTEGER,nMA3 INTEGER,nTech1 INTEGER,nTech2 INTEGER,"
	        + "nTech3 INTEGER,nHsj INTEGER,nZd INTEGER,wsXxgg VARCHAR,bFlag INTEGER)";
//	        + " constraint code_id_date unique(stock_code,market_id,kline_type,dwtime))";

	public final static String INSERT_DATA = "insert into "
	        + name
	        + " (stock_code,stock_name,market_id,stock_type,kline_type,dwtime,nYClose,nOpen,nZgcj,nZdcj,nClose,nZdf,nCjje,"
	        + "nCjss,nCcl,nHsl,nSyl,nMA1,nMA2,nMA3,nTech1,nTech2,nTech3,nHsj,nZd,wsXxgg,bFlag) "
	        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";// 27

	public final static String SELECT_ALL = "select *from " + name
	        + " where stock_code=? and market_id=? and kline_type=?";

	public final static String SELECT_DWTIME = "select dwtime from " + name
	        + " where stock_code=? and market_id=? and kline_type=?";

	public final static String DELETE_STOCK_DATE = "delete from " + name
	        + " where stock_code=? and market_id=? and kline_type=?";

	public KlineCacheSQLHelper(Context context, int version)
	{
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}

}
