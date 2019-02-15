package com.romaway.android.phone.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RomaMyfootprintSQLController {

	private RomaMyfootprintSQLHelper helper;

	public RomaMyfootprintSQLController(Context context) {
		helper = new RomaMyfootprintSQLHelper(context, 1);
	}

	public SQLiteDatabase getWdb() {
		return helper.getWritableDatabase();
	}

	public SQLiteDatabase getRdb() {
		return helper.getReadableDatabase();
	}

	/**
	 * 插入一条数据
	 * @param db
	 * @param stockCode
	 * @param stockName
	 * @param marketId
	 */
	public void insert(SQLiteDatabase db, String stockCode, String stockName,
			String marketId) {
		db.execSQL(RomaMyfootprintSQLHelper.DATA_INSERT, new Object[] {
				stockCode, stockName, marketId });
	}

	/**
	 * 删除所有数据
	 * @param db
	 */
	public void delete(SQLiteDatabase db) {
		db.execSQL(RomaMyfootprintSQLHelper.DATA_DELETE_ALL);
	}

	/**
	 * 查询所有数据
	 * @param db
	 * @return
	 */
	public String[][] selectAll(SQLiteDatabase db) {
		String[][] datas = null;
		Cursor cursor = db
				.rawQuery(RomaMyfootprintSQLHelper.DATA_SELECT, null);
		try {
			if (cursor.getCount() > 0) {
				datas = new String[cursor.getCount()][3];
				for (int i = 0; cursor.moveToNext(); i++) {
					datas[i][0] = cursor.getString(0);//stockCode
					datas[i][1] = cursor.getString(1);//stockName
					datas[i][2] = cursor.getString(2);//marketId
				}
			}
		} finally {
			cursor.close();
			db.close();
		}
		return datas;
	}

	/**
	 * 获取记录总数
	 * @param db
	 * @return
	 */
	public int getCount(SQLiteDatabase db) {
		int count = 0;
		Cursor cursor = db
				.rawQuery(RomaMyfootprintSQLHelper.DATA_SELECT, null);
		try {
			count = cursor.getCount();
		} finally {
			cursor.close();
			db.close();
		}
		return count;
	}
	
	/**
	 * 删除第一条记录
	 * @param db
	 */
	public void deleteFirst(SQLiteDatabase db){
		Cursor cursor = db
				.rawQuery(RomaMyfootprintSQLHelper.DATA_SELECT, null);
		try {
			cursor.moveToFirst();
			String stockCode = cursor.getString(0);
			db.execSQL(RomaMyfootprintSQLHelper.DATA_DELETE_BY_CODE, new Object[]{stockCode});
		} finally{
			cursor.close();
			db.close();
		}
	}

}
