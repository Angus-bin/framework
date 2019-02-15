package com.romaway.android.phone.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RomaMyfootprintSQLMgr {
	
	public static void insertData(Context context, String stockCode, String stockName, String marketId){
		int count = getCount(context);
		if (count <= 20) {
			add(context, stockCode, stockName, marketId);
		} else {
			deleteFirst(context);
			add(context, stockCode, stockName, marketId);
		}
	}
	
	/**
	 * 插入数据
	 * @param context
	 * @param stockCode
	 * @param stockName
	 * @param marketId
	 */
	public static void add(Context context, String stockCode, String stockName, String marketId){
		RomaMyfootprintSQLController controller = new RomaMyfootprintSQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			controller.insert(db, stockCode, stockName, marketId);
		} finally {
			db.close();
		}
	}
	
	/**
	 * 删除所有记录
	 * @param context
	 */
	public static void deleteAll(Context context){
		RomaMyfootprintSQLController controller = new RomaMyfootprintSQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			controller.delete(db);
		} finally {
			db.close();
		}
	}
	
	/**
	 * 查询所有数据
	 * @param context
	 * @return
	 */
	public static String[][] queryAll(Context context){
		String[][] result = null;
		RomaMyfootprintSQLController controller = new RomaMyfootprintSQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			result = controller.selectAll(db);
		} finally {
			db.close();
		}
		return result;
	}
	
	/**
	 * 获取记录总数
	 * @param context
	 * @return
	 */
	public static int getCount(Context context){
		int count = 0;
		RomaMyfootprintSQLController controller = new RomaMyfootprintSQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			count = controller.getCount(db);
		} finally {
			db.close();
		}
		return count;
	}
	
	public static void deleteFirst(Context context){
		RomaMyfootprintSQLController controller = new RomaMyfootprintSQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			controller.deleteFirst(db);
		} finally {
			db.close();
		}
	}
	
}
