/**
 * 
 */
package com.romaway.android.phone.keyboardelf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 键盘精灵数据存储
 * 
 * @author duminghui
 * 
 */
class KeyboardElfSQLiteHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "KeyBoardElf";
	// private static final String TABLE_CREATE =
	// "CREATE TABLE keyboardelf(stockCode VARCHAR UNIQUE,stockName VARCHAR,py VARCHAR,marketId INTEGER,codeType INTEGER)";
	/** stockcode和marketid组合为唯一约束 **/
	private static final String TABLE_CREATE = "CREATE TABLE keyboardelf(stockCode VARCHAR ,stockName VARCHAR,py VARCHAR,marketId INTEGER,codeType INTEGER,stockMark VARCHAR, constraint stockCode_marketId unique(stockCode,marketId))";
	private static final String INDEX_CREATE = "CREATE INDEX index_keyboardelf_py ON keyboardelf(py)";
	public static final String DATA_SELECT = "SELECT stockCode,stockName,py,codeType from keyboardelf where (py like ? or stockcode like ?) and marketId=?";
	/* 支持股票名称搜索 */ public static final String DATA_SELECT_NAME = "SELECT stockCode,stockName,py,codeType from keyboardelf where (py like ? or stockcode like ? or stockName like ?) and marketId=?";
	/**
	 * 根据拼音或代码查询全部
	 */
	public static final String DATA_SELECT_ALL = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where py like ? or stockcode like ? order by stockcode";
	/* 支持股票名称搜索 */ public static final String DATA_SELECT_ALL_NAME = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where py like ? or stockName like ? or stockCode like ? order by stockcode";

	/**
	 * 根据代码查询全部
	 */
	public static final String DATA_SELECT_ALL_CODE = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where stockcode like ? or py like ? order by case when stockcode like ? then 0 else 1 end, stockcode asc";
	public static final String DATA_SELECT_HSA_CODE = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where (stockcode like ? or py like ?) and (codeType like ? or codeType like ? or codeType like ? or codeType like ? or codeType like ? or codeType like ?) order by case when stockcode like ? then 0 else 1 end, stockcode asc";
	public static final String DATA_SELECT_HSA_PY = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where py like ? and (codeType like ? or codeType like ? or codeType like ? or codeType like ? or codeType like ? or codeType like ?) order by case when stockcode like ? then 0 else 1 end, stockcode asc";
	/* 支持股票名称搜索 */
	public static final String DATA_SELECT_HSA_PY_NAME = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where (py like ? or stockName like ?) and (codeType like ? or codeType like ? or codeType like ? or codeType like ? or codeType like ? or codeType like ?) order by case when stockName like ? then 0 else 1 end, stockName asc";

	public static final String DATA_INSERT = "INSERT OR REPLACE INTO keyboardelf(stockCode,stockName,py,marketId,codeType) values(?,?,?,?,?)";
	public static final String DATA_INSERT_WITH_MARK = "INSERT OR REPLACE INTO keyboardelf(stockCode,stockName,py,marketId,codeType, stockMark) values(?,?,?,?,?,?)";
	public static final String DATA_DELETE = "DELETE FROM keyboardelf where stockCode=?";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS keyboardelf";
	public static final String DATA_DELETE_WITH_MARKETID = "DELETE FROM keyboardelf where stockCode=? and marketId=?";
	/** 股票预警的检索 **/
	public static final String DATA_SELECT_ALL_GPYJ = "SELECT stockCode,stockName from keyboardelf where stockcode like ?";
	/** 股票代码检索市场代码 **/
	public static final String DATA_SELECT_MARKETID = "SELECT marketId from keyboardelf where stockcode = ?";
	
	public static final String DATA_SELECT_ALL_GangGuCODE = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where (marketId=33 or marketId=32) and (stockcode like ? or py like ?) order by stockcode";
	public static final String DATA_SELECT_ALL_GangGu = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where (marketId=33 or marketId=32) and (py like ? or stockcode like ?) order by stockcode";
	/* 支持股票名称搜索 */ public static final String DATA_SELECT_ALL_NAME_GangGu = "SELECT stockCode,stockName,py,codeType,marketId,stockMark from keyboardelf where marketId=33 and (py like ? or stockcode like ? or stockName like ?) order by stockcode";

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public KeyboardElfSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		db.execSQL(INDEX_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion == DATABASE_VERSION) {
			db.execSQL("alter table KeyBoardElf add stockMark VARCHAR");
		}
	}
}
