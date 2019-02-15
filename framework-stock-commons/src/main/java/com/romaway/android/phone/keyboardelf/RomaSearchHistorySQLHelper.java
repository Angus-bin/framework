package com.romaway.android.phone.keyboardelf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RomaSearchHistorySQLHelper extends SQLiteOpenHelper{
	
	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "SearchHistory";
	
	public static final String TABLE_CREATE = "create table searchHistory(stockCode VARCHAR, stockName VARCHAR, marketId VARCHAR, codeType VARCHAR, stockMark VARCHAR, constraint stockCode_marketId unique(stockCode,marketId))";
	public static final String DATA_SELECT = "select * from searchHistory";
	public static final String DATA_INSERT = "insert or replace into searchHistory values (?, ?, ?, ?, ?)";
	public static final String DATA_DELETE_ALL = "delete from searchHistory";
	public static final String DATA_DELETE_BY_CODE = "delete from searchHistory where stockCode = ?";

	public RomaSearchHistorySQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion == DATABASE_VERSION) {
			db.execSQL("alter table SearchHistory add stockMark VARCHAR");
		}
	}

}
