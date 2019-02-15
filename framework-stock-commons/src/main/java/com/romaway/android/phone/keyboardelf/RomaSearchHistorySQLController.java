package com.romaway.android.phone.keyboardelf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.romaway.common.android.base.OriginalContext;

public class RomaSearchHistorySQLController {

	private RomaSearchHistorySQLHelper helper;

	public RomaSearchHistorySQLController(Context context) {
		// 【BUG】修复从键盘精灵跳转至个股, 因传递Activity对象被回收导致崩溃空指针;
		helper = new RomaSearchHistorySQLHelper(OriginalContext.getContext());
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
			String marketId, String codeType, String stockMark) {
		db.execSQL(RomaSearchHistorySQLHelper.DATA_INSERT, new Object[] {
				stockCode, stockName, marketId, codeType, stockMark});
	}

	/**
	 * 删除所有数据
	 * @param db
	 */
	public void delete(SQLiteDatabase db) {
		db.execSQL(RomaSearchHistorySQLHelper.DATA_DELETE_ALL);
	}

	/**
	 * 查询所有数据
	 * @param db
	 * @return
	 */
	public String[][] selectAll(SQLiteDatabase db) {
		String[][] datas = null;
		Cursor cursor = db
				.rawQuery(RomaSearchHistorySQLHelper.DATA_SELECT, null);
		try {
			if (cursor.getCount() > 0) {
				datas = new String[cursor.getCount()][8];
				for (int i = cursor.getCount() - 1; cursor.moveToNext(); i--) {
					datas[i][0] = cursor.getString(0);
					datas[i][1] = cursor.getString(1);
					datas[i][4] = cursor.getString(2);
					datas[i][3] = cursor.getString(3);
					datas[i][5] = cursor.getString(4);
					datas[i][6] = "";

					datas[i][7] = "";


				}
			}
		} finally {
			cursor.close();
			db.close();
		}
		return datas;
	}
	
	/**
	 * 删除第一条数据
	 * @param db
	 */
	public void deleteFirst(SQLiteDatabase db){
		Cursor cursor = db.rawQuery(RomaSearchHistorySQLHelper.DATA_SELECT, null);
		try {
			cursor.moveToFirst();
			String stockCode = cursor.getString(0);
			db.execSQL(RomaSearchHistorySQLHelper.DATA_DELETE_BY_CODE, new Object[]{stockCode});
		} finally {
			cursor.close();
			db.close();
		}
	}

	/**
	 * 获取记录总数
	 * @param db
	 * @return
	 */
	public int getCount(SQLiteDatabase db) {
		int count = 0;
		Cursor cursor = db
				.rawQuery(RomaSearchHistorySQLHelper.DATA_SELECT, null);
		try {
			count = cursor.getCount();
		} finally {
			cursor.close();
			db.close();
		}
		return count;
	}

}
