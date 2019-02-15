package com.romaway.android.phone.keyboardelf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RomaSearchHistorySQLMgr {
	
	/**
	 * 删除第一条记录
	 * @param context
	 */
	public static void deleteFirst(Context context){
		RomaSearchHistorySQLController controller = new RomaSearchHistorySQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			controller.deleteFirst(db);
		} finally{
			db.close();
		}
	}
	
	/**
	 * 插入数据
	 * @param context
	 * @param stockCode
	 * @param stockName
	 * @param marketId
	 */
	public static void insertData(Context context, String stockCode, String stockName, String codeType, String marketId, String stockMark){
		RomaSearchHistorySQLController controller = new RomaSearchHistorySQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			controller.insert(db, stockCode, stockName, marketId, codeType, stockMark);
		} finally {
			db.close();
		}
	}
	
	/**
	 * 删除所有记录
	 * @param context
	 */
	public static void deleteAll(Context context){
		RomaSearchHistorySQLController controller = new RomaSearchHistorySQLController(context);
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
		RomaSearchHistorySQLController controller = new RomaSearchHistorySQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			result = controller.selectAll(db);
			
		} catch(Exception e){
		    e.printStackTrace();
		}
		finally {
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
		RomaSearchHistorySQLController controller = new RomaSearchHistorySQLController(context);
		SQLiteDatabase db = controller.getWdb();
		try {
			count = controller.getCount(db);
		} finally {
			db.close();
		}
		return count;
	}
	
}
