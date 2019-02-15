package com.romaway.android.phone.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RomaMyfootprintSQLHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_NAME = "myfootprint";
	public static final String TABLE_CREATE = "create table if not exists "+ TABLE_NAME + "(stockCode VARCHAR, stockName VARCHAR, marketId VARCHAR, constraint stockCode_marketId unique(stockCode,marketId))";
	public static final String DATA_SELECT = "select * from " + TABLE_NAME;
	public static final String DATA_INSERT = "insert or replace into " + TABLE_NAME + " values (?, ?, ?)";
	public static final String DATA_DELETE_ALL = "delete from " + TABLE_NAME;
	public static final String DATA_DELETE_BY_CODE = "delete from " + TABLE_NAME + " where stockCode = ?";
	public boolean firstCreateDB = false;

	public RomaMyfootprintSQLHelper(Context context, int version)
	{
		super(context, TABLE_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_CREATE);
		firstCreateDB = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
