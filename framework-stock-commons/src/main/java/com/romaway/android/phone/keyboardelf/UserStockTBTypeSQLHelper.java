package com.romaway.android.phone.keyboardelf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.romaway.commons.log.Logger;


public class UserStockTBTypeSQLHelper extends SQLiteOpenHelper{

	/** 自选股数据表版本号 */
	public final static int USER_STOCK_TABLE_VERSION_NUMBER = 2;
	public final static String name = "userstocktype_table";
	//20150922增加唯一约束，防止自选股重复问题
	public final static String CTREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
	        + name
	        + " (_id INTEGER  primary key autoincrement,stock_code VARCHAR,market_id VARCHAR,"
	        + "stock_type VARCHAR"
	        + ", constraint stockCode_marketId unique(stock_code,market_id))";
	public final static String INSERT_DATA = "insert into "
	        + name
	        + " (stock_code,market_id,stock_type) values(?,?,?)";

	public final static String DELETE_DATA = "delete from " + name;

	public final static String DELETE_FROM_STOCK_CODE = "delete from "
	        + name
	        + " where stock_code=? and market_id=?";

	public final static String DELETE_FROM__ID = "delete from "
	        + name
	        + " where _id=?";

	public final static String DELETE_FROM__TYPE = "delete from "
			+ name
			+ " where stock_type=?";

	public final static String SELECT_STOCKCODES = "select stock_code from "
	        + name;

	public final static String SELECT_ALL = "select * from "+ name;

	public final static String GET_COUNT = "select count(*) from "+ name;//获取总数

	public final static String UPDATE_ALL = "update "
	        + name
	        + " set stock_code=?,market_id=?,stock_type=?";

	public final static String SELECT_BY_STOCKCODE = "select * from " + name +" where stock_code=? and market_id=?";
	public boolean firstCreateDB = false;
	public UserStockTBTypeSQLHelper(Context context, int version)
	{
		super(context, name, null, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CTREATE_TABLE);
		firstCreateDB = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Logger.i("UserStockSQLHelper", "onUpgrade: "+oldVersion +" --> "+newVersion);
		for (int j = oldVersion; j < newVersion; j++) {
            switch (j) {
            case 1:			// 版本1-->2: 增加code_mark字段列;
            	db.execSQL("alter table "+ name+ " add code_mark VARCHAR");
            	break;
            default:
                break;
            }
        }
	}

}
