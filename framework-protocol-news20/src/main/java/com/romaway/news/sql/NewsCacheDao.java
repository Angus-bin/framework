package com.romaway.news.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsCacheDao
{
	private int version = CacheSqlHelper.DATABASE_VERSION;
	private CacheSqlHelper helper;

	public NewsCacheDao(Context context, String tabName)
	{
		helper = new CacheSqlHelper(context, tabName, version); 
	}

	private SQLiteDatabase getWdb()
	{
		if(helper == null){
			throw new NullPointerException("缓存控制器没有创建实例，请在StockNewsInit.java文件中进行初始化创建！");
		}
		
		return helper.getWritableDatabase();
	}

	private SQLiteDatabase getRdb()
	{
		if(helper == null){
			throw new NullPointerException("缓存控制器没有创建实例，请在StockNewsInit.java文件中进行初始化创建！");
		}
		
		return helper.getReadableDatabase();
	}

	/**
	 * 插入带有用户ID的数据
	 * @param userId
	 * @param funType
	 * @param newsId
	 * @param itemDatas
	 * @param uptime
	 */
	public synchronized void insertForUserId(
			String userId,
			String funType,
			String newsId,
			String itemDatas,
			String details,
			String uptime)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "insert into "
	        + helper.name
	        + " (userId,fun_type,newsId,item_datas,details,uptime) values(?,?,?,?,?,?)";
			
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql, 
					new Object[] { userId,funType,newsId,itemDatas,details,uptime });
	
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		}finally{
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}
	
	/**
	 * 插入数据操作
	 * @param funType
	 * @param newsId
	 * @param stockCode
	 * @param itemDatas
	 * @param uptime
	 */
	public synchronized void insert(
			String funType,
			String newsId,
			String stockCode,
			String itemDatas,
			String uptime)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "insert into "
	        + helper.name
	        + " (fun_type,newsId,stockCode,item_datas,uptime) values(?,?,?,?,?)";
			
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql, 
					new Object[] { funType,newsId,stockCode,itemDatas,uptime });
	
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		}finally{
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}

	/**
	 * 插入数据操作
	 * @param funType
	 * @param newsId
	 * @param itemDatas
	 * @param uptime
	 */
	public synchronized void insert(
			String funType,
			String newsId,
			String itemDatas,
			String uptime)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "insert into "
	        + helper.name
	        + " (fun_type,newsId,item_datas,uptime) values(?,?,?,?)";
			
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql, 
					new Object[] { funType,newsId,itemDatas,uptime });
	
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		}finally{
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}
	
	/**
	 * 插入数据操作
	 * @param funType
	 * @param newsId
	 * @param details
	 * @param uptime
	 * @param readFlag
	 */
	public synchronized void insert1(
			String funType,
			String newsId,
			String details,
			String uptime,
			String readFlag)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "insert into "
	        + helper.name
	        + " (fun_type,newsId,details,uptime,read_flag) values(?,?,?,?,?)";
			
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql, 
					new Object[] { funType,newsId,details,uptime,readFlag });
	
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		}finally{
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}

	/**
	 * 删除数据操作
	 * @param userId
	 * @param funType
	 * @param newsId
     */
	public synchronized void deleteForUserId(
			String userId,
			String funType,
			String newsId)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "delete from "
	        + helper.name
	        + " where userId=? and fun_type=? and newsId=?";
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql, 
					new Object[] { userId,funType,newsId});
	
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		}finally{
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}
	/**
	 * 删除数据操作
	 * @param funType
	 * @param newsId
	 */
	public synchronized void deleteForNewsId(String funType,
			String newsId)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "delete from "
					+ helper.name
					+ " where fun_type=? and newsId=?";
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql,
					new Object[] { funType,newsId});

			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}

    /**
     * 删除数据操作
     */
    public synchronized void deleteAll(String userId)
    {
        SQLiteDatabase db = null;
        try{
            String sql = "delete from "
                    + helper.name
                    + " where userId=?";
            db = getWdb();
            db.beginTransaction();
            db.execSQL(sql,
                    new Object[] {userId});

            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(db != null){
                db.endTransaction();
                db.close();
                db = null;
            }
        }
    }
	/**
	 * 根据newsId 更新 read_flag
	 * @param read_flag
	 * @param newsId
	 */
	public synchronized void updateReadFlag(String newsId, String read_flag)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "update "
			        + helper.name
			        + " set read_flag=? where newsId=?";
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql, new Object[] { read_flag,newsId });
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally {
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}
	
	/**
	 * 根据newsId 更新 details
	 * @param funType
	 * @param newsId
	 * @param details
	 */
	public synchronized void updateDetails(String funType,String newsId, String details){
		SQLiteDatabase db = null;
		try{
			String sql = "update "
	        + helper.name
	        + " set details=? where fun_type=? and newsId=?";
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql,new Object[] { details,funType,newsId });
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally {
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}
	/**
	 * 根据newsId 更新 details
	 * @param funType
	 * @param newsId
	 * @param json
	 */
	public synchronized void updateItemData(String funType,String newsId, String json){
		SQLiteDatabase db = null;
		try{
			String sql = "update "
					+ helper.name
					+ " set item_datas =? where fun_type=? and newsId=?";
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql,new Object[] { json,funType,newsId });
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}

	/**
	 * 根据funType, newsId 更新数据
	 * @param funType
	 * @param newsId
	 * @param msgType
	 * @param stockCode
	 * @param itemDatas
	 * @param uptime
	 */
	public synchronized void updateItemData(
			String funType,
			String newsId,
			String msgType,
			String stockCode,
			String itemDatas,
			String uptime)
	{
		SQLiteDatabase db = null;
		try{
			String sql = "update "
					+ helper.name
					+ " set item_datas =?, stockCode =?, uptime =?, msgType =? where fun_type=? and newsId=?";
			db = getWdb();
			db.beginTransaction();
			db.execSQL(sql,new Object[] { itemDatas, stockCode, uptime, msgType, funType, newsId });
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
	}

	/**
	 * 查询不带userId的接口
	 * @param funType
	 * @param newsId
	 * @return
	 */
	public synchronized String[] selectAll(String funType, String newsId) {
		return selectAll("", funType, newsId);
	}
	
	/**
	 * 
	 * @param userId
	 * @param funType
	 * @param newsId
	 * @return  result[0]： 选项的内容；result[1]： 已读未读标记；result[2]： 详情数据；
	 */
	public synchronized String[] selectAll(String userId, String funType, String newsId) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String[] result = null;
		String[] where = null;
		
		if(TextUtils.isEmpty(userId))
		  where = new String[]{ funType,newsId };
		else
			where = new String[]{ userId,funType,newsId };
		
		try{
			String sql = "select item_datas,read_flag,details from "
			        + helper.name;
			StringBuilder  builder = new StringBuilder();
			builder.append(" where");
			if(!TextUtils.isEmpty(userId))
				builder.append(" userId=? and");
			builder.append(" fun_type=? and newsId=?");
			sql += builder.toString();
			
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql,where);
			result = new String[3];
			for (; cursor.moveToNext();){
				result[0] = cursor.getString(0);//选项内容
				result[1] = cursor.getString(1);//读取标志
				result[2] = cursor.getString(2);//详情数据
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally
		{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return result;
	}
	
	public synchronized String selectDetails(String funType, String newsId) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String result = null;
		try{
			String sql = "select details from "
			        + helper.name
			        + " where fun_type=? and newsId=?";
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql,
					new String[] { funType,newsId });
			for (; cursor.moveToNext();){
				result = cursor.getString(0);
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally
		{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return result;
	}
	
	public synchronized String[][] selectMultipleFromCodeMart(String funType, String stockcodeMarket) {
		if(stockcodeMarket == null)
			return null;
		
		String[] code = stockcodeMarket.split("_");
		int count = code.length;
		List<String[]> resultList = new ArrayList<String[]>();
		
		for(int i = 0; i < count; i++)
			selectSingleFromCodeMart(resultList, funType, code[i]);
		
		int count1 = resultList.size();
		String[][] result = null;
		if(count1 > 0){
			result = new String[count1][3]; 
			for(int i = 0; i < count1; i++) {
				result[i] = resultList.get(i);
			}
		}
		
		return result;
	}

	/**
	 * 根据funType 和 股票代码市场ID查询选项数据
	 * @param resultList
	 * @param funType
	 * @param stockCode
     */
	private synchronized void selectSingleFromCodeMart(List<String[]> resultList, String funType, String stockCode) {
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			String sql = "select fun_type, item_datas,read_flag from "
			        + helper.name
			        + " where fun_type=? and stockCode=?";
			
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, 
					new String[]{funType,stockCode});
			if (cursor.getCount() > 0){
				for (; cursor.moveToNext();){
					String[] s = new String[3];
					s[0] = cursor.getString(0);// funType
					s[1] = cursor.getString(1);// itemDatas
					s[2] = cursor.getString(2);// flag
					resultList.add(s);
				}
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally {
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return ;
	}

	/**
	 * 查询下拉刷新或者点击刷新按钮时的缓存数据
	 * @param funType
	 * @param stockcodeMarket
	 * @param reqCount
     * @return
     */
	public synchronized String[][] selectPullRefreshData(String funType, String stockcodeMarket, String msgType, int reqCount) {
		String[][] s = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String[] where = null;
		
		try{
			List<String> whereList = new ArrayList<String>();
			whereList.add(funType);
			if (!TextUtils.isEmpty(stockcodeMarket))
				whereList.add(stockcodeMarket);
			if (!TextUtils.isEmpty(msgType))
				whereList.add(msgType);
			where = whereList.toArray(new String[whereList.size()]);

			String sql = "select fun_type, item_datas,read_flag from "
			        + helper.name;
			StringBuilder  builder = new StringBuilder();
			builder.append(" where");
			builder.append(" fun_type=?");
			if(!TextUtils.isEmpty(stockcodeMarket))
				builder.append(" and stockCode=?");
			if(!TextUtils.isEmpty(msgType))
				builder.append(" and msgType=?");
			builder.append(" order by newsId desc limit 0,");
			builder.append(reqCount);
			sql += builder.toString();
			
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, where);
			int getCount = 0;
			if ((getCount = cursor.getCount()) > 0){
				s = new String[getCount][3];
				int index = 0;
				for (; cursor.moveToNext();){
					s[index][0] = cursor.getString(0);// funType
					s[index][1] = cursor.getString(1);// itemDatas
					s[index][2] = cursor.getString(2);// flag
					index++;
				}
				
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally
		{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return s;
	}

	/**
	 * 查询下拉刷新或者点击刷新按钮时的缓存数据
	 * @param funType
	 * @param stockcodeMarket
	 * @param reqCount
     * @return
     */
	public synchronized String[][] selectPullRefreshDataForUptime(String funType, String stockcodeMarket,int reqCount) {
		String[][] s = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String[] where = null;

		try{
			if(TextUtils.isEmpty(stockcodeMarket))
				where = new String[]{ funType };
			else
				where = new String[]{ funType,stockcodeMarket };

			String sql = "select fun_type, item_datas,read_flag from "
					+ helper.name;
			StringBuilder  builder = new StringBuilder();
			builder.append(" where");
			builder.append(" fun_type=?");
			if(!TextUtils.isEmpty(stockcodeMarket))
				builder.append(" and stockCode=?");
			builder.append(" order by uptime desc limit 0,");
			builder.append(reqCount);
			sql += builder.toString();

			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, where);
			int getCount = 0;
			if ((getCount = cursor.getCount()) > 0){
				s = new String[getCount][3];
				int index = 0;
				for (; cursor.moveToNext();){
					s[index][0] = cursor.getString(0);// funType
					s[index][1] = cursor.getString(1);// itemDatas
					s[index][2] = cursor.getString(2);// flag
					index++;
				}

			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		} finally
		{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return s;
	}

	/**
	 * 查询上拉加载更多时的缓存数据
	 * @param funType 资讯栏目功能类型
	 * @param reqCount //查询的个数
	 * @return
	 */
	public synchronized String[][] selectLoadMoreData(String funType, String stockcodeMarket, String msgType, String newsId, int reqCount) {
		String[][] s = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String[] where = null;
		
		try{
			List<String> whereList = new ArrayList<String>();
			whereList.add(funType);
			if (!TextUtils.isEmpty(stockcodeMarket))
				whereList.add(stockcodeMarket);
			if (!TextUtils.isEmpty(msgType))
				whereList.add(msgType);
			whereList.add(newsId);
			where = whereList.toArray(new String[whereList.size()]);

			String sql = "select fun_type, item_datas,read_flag from "
			        + helper.name;
			StringBuilder  builder = new StringBuilder();
			builder.append(" where fun_type=?");
			if(!TextUtils.isEmpty(stockcodeMarket))
				builder.append(" and stockCode=?");
			if (!TextUtils.isEmpty(msgType))
				builder.append(" and msgType=?");
			builder.append(" and newsId<?");
			builder.append(" order by newsId desc limit 0,");
			builder.append(reqCount);
			sql += builder.toString();
			
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, where);
			int getCount = 0;
			if ((getCount = cursor.getCount()) > 0){
				s = new String[getCount][3];
				int index = 0;
				for (; cursor.moveToNext();){
					s[index][0] = cursor.getString(0);// funType
					s[index][1] = cursor.getString(1);// itemDatas
					s[index][2] = cursor.getString(2);// flag
					index++;
				}
				
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally
		{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return s;
	}

	/**
	 * 查询下拉刷新或者点击刷新按钮时的缓存数据
	 * @param startIndex
	 * @param reqCount
     * @return
     */
	public synchronized String[][] selectSql(int startIndex, int reqCount) {
		String[][] s = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		
		try{
			String sql = "select fun_type, item_datas,read_flag from "
			        + helper.name ;
			StringBuilder  builder = new StringBuilder();
			builder.append(" limit "+startIndex+","+reqCount);
			sql += builder.toString();
			
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, null);
			int getCount = 0;
			if ((getCount = cursor.getCount()) > 0){
				s = new String[getCount][3];
				int index = 0;
				for (; cursor.moveToNext();){
					s[index][0] = cursor.getString(0);// funType
					s[index][1] = cursor.getString(1);// itemDatas
					s[index][2] = cursor.getString(2);// flag
					index++;
				}
				
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally
		{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return s;
	}
	
	public synchronized boolean isExistFunTypeNewsIdAndStockCode(String funType, String newsId,String stockCode){
		boolean result = false;
		Cursor cursor = null;
		SQLiteDatabase db = null;
        
		try {
			String sql = "select * from "
			        + helper.name
			        + " where fun_type=? and newsId=? and stockCode=?";
			
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, 
					new String[]{funType,newsId,stockCode});
			if(cursor.getCount() > 0){
				result = true;
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally {
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return result;
	}
	
	public synchronized boolean isExistFunTypeNewsId(String funType, String newsId){
		boolean result = false;
		Cursor cursor = null;
		SQLiteDatabase db = null;
        
		try {
			String sql = "select * from "
			        + helper.name
			        + " where fun_type=? and newsId=?";
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, 
					new String[]{funType,newsId});
			if(cursor.getCount() > 0){
				result = true;
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally {
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return result;
	}

	public synchronized boolean isExistFunTypeNewsId(String funType, String newsId, String msgType){
		boolean result = false;
		Cursor cursor = null;
		SQLiteDatabase db = null;

		try {
			String sql = "select * from "
					+ helper.name
					+ " where fun_type=? and newsId=? and msgType=?";
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql,
					new String[]{funType,newsId,msgType});
			if(cursor.getCount() > 0){
				result = true;
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return result;
	}
	
	/**
	 * 查询已读未读标志
	 * @param funType
	 * @param newsId
	 * @return
	 */
	public synchronized String selectReadFlag(String funType, String newsId){
		String result = null;
		Cursor cursor = null;
		SQLiteDatabase db = null;
        
		try {
			String sql = "select read_flag from "
			        + helper.name
			        + " where fun_type=? and newsId=?";
			db = getRdb();
			db.beginTransaction();
			cursor = db.rawQuery(sql, 
					new String[]{funType,newsId});
			if(cursor.moveToNext()){
				result = cursor.getString(0);
			}
			db.setTransactionSuccessful();
		}catch(Exception e){ 
			e.printStackTrace();
		} finally {
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
			if(db != null){
				db.endTransaction();
				db.close();
				db = null;
			}
		}
		return result;
	}
	
}
