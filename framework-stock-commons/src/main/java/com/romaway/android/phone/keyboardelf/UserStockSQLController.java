package com.romaway.android.phone.keyboardelf;

import java.util.List;

import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserStockSQLController {
private UserStockSQLHelper helper;
	
	public UserStockSQLController(Context context){
		helper = new UserStockSQLHelper(context, UserStockSQLHelper.USER_STOCK_TABLE_VERSION_NUMBER);
	}
	
	public void closeHelper(){
		if(helper != null){
			helper.close();
			helper = null;
		}
	}
	public SQLiteDatabase getWdb(){
		SQLiteDatabase writableDatabase = null ;
		try {
			writableDatabase= helper.getWritableDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return writableDatabase;
	}
	
	public SQLiteDatabase getRdb(){
		return helper.getReadableDatabase();
	}
	
	public void insert(SQLiteDatabase db,String stock_name,String stock_code,String market_id,String stock_type,String group_name,String stock_warning,String code_mark){
		try{
			db.execSQL(UserStockSQLHelper.INSERT_DATA, new Object[]{stock_name,stock_code,market_id,stock_type,group_name,stock_warning, code_mark});
		}catch(Exception e){
			
		}
	}
	
	public void delete(SQLiteDatabase db) {
		db.execSQL(UserStockSQLHelper.DELETE_DATA);
	}
	
	public void deleteById(SQLiteDatabase db,int _id){
		db.execSQL(UserStockSQLHelper.DELETE_FROM__ID, new Object[]{_id});
	}
	
	public void deleteByStockCode(SQLiteDatabase db,String stock_code, String market_id){
		db.execSQL(UserStockSQLHelper.DELETE_FROM_STOCK_CODE, new Object[]{stock_code, market_id});
	}
	
	public void deleteByGroupName(SQLiteDatabase db,String group_name){
		db.execSQL(UserStockSQLHelper.DELETE_FROM_GROUP_NAME, new Object[]{group_name});
	}
	
	public String[][] selectAll(SQLiteDatabase db){
		String s[][] = null;
		Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_ALL, null);
		try{
			if(cursor.getCount()>0){
				s = new String[cursor.getCount()][8];
				int length = cursor.getCount();
				for(int i = 0;cursor.moveToNext();i++){
					s[i][0] = cursor.getString(0);//_id
					s[i][1] = cursor.getString(1);//stock_name
					s[i][2] = cursor.getString(2);//stock_code
					s[i][3] = cursor.getString(3);//market_id
					s[i][4] = cursor.getString(4);//stock_type
					s[i][5] = cursor.getString(5);//group_name
					s[i][6] = cursor.getString(6);//stock_warning
					s[i][7] = cursor.getString(7);//code_mark
				}
			}
		}finally{
			cursor.close();
			db.close();
			closeHelper();
		}
		return s;
	}
	
	public String getStockCodes(SQLiteDatabase db){
		String s = "";
		Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_ALL, null);
		try{
			if(cursor.getCount()>0){
				int length = cursor.getCount();
				for(int i = 0;cursor.moveToNext();i++){
					if(StringUtils.isEmpty(s)){
						s += cursor.getString(2);//stock_code
					}else{
						s += "," + cursor.getString(2);//stock_code
					}
				}
			}
		}finally{
			cursor.close();
			db.close();
			closeHelper();
		}
		return s;
	}
	
	/**
	 * 倒序查询
	 * @param db
	 * @return
	 */
	public String[][] selectAllInvert(SQLiteDatabase db){
		String s[][] = null;
		Cursor cursor;
		cursor = db.rawQuery(UserStockSQLHelper.SELECT_ALL, null);
		db.beginTransaction();
		try{
			if(cursor.getCount()>0){
				s = new String[cursor.getCount()][8];
				int length = cursor.getCount();
				for(int i = 0;cursor.moveToNext();i++){
					Logger.d("UserStockTBServer", "  " + cursor.getString(0));
					Logger.d("UserStockTBServer", "  " + cursor.getString(1));
					Logger.d("UserStockTBServer", "  " + cursor.getString(2));
					Logger.d("UserStockTBServer", "  " + cursor.getString(3));
					Logger.d("UserStockTBServer", "  " + cursor.getString(4));
					Logger.d("UserStockTBServer", "  " + cursor.getString(5));
					Logger.d("UserStockTBServer", "  " + cursor.getString(6));
					Logger.d("UserStockTBServer", "  " + cursor.getString(7));
					s[length - i - 1][0] = cursor.getString(0);//_id
					s[length - i - 1][1] = cursor.getString(1);//stock_name
					s[length - i - 1][2] = cursor.getString(2);//stock_code
					s[length - i - 1][3] = cursor.getString(3);//market_id
					s[length - i - 1][4] = cursor.getString(4);//stock_type
					s[length - i - 1][5] = cursor.getString(5);//group_name
					s[length - i - 1][6] = cursor.getString(6);//stock_warning
					s[length - i - 1][7] = cursor.getString(7);//stock_mark
				}
			}
		}finally{
		    db.setTransactionSuccessful();
            db.endTransaction();
			cursor.close();
			db.close();
			closeHelper();
		}
		return s;
	}
	
	/**
	 * 倒序查询
	 * @param db
	 * @return
	 */
	public String selectAllInvert1(SQLiteDatabase db){
		//Map<String, String> maps = new HashMap<String,String>();
		Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_ALL, null);
		db.beginTransaction();
		String stockCodeMarket = "";
		try{
			if(cursor.getCount()>0){
				//s = new String[cursor.getCount()][8];
				//int length = cursor.getCount();
				for(int i = 0;cursor.moveToNext();i++){
					//maps.put(cursor.getString(2)+ cursor.getString(3), cursor.getString(1));
					if(i == 0)
						stockCodeMarket = cursor.getString(2) + "-" + cursor.getString(3);
					else 
						stockCodeMarket += ("_" + (cursor.getString(2) + "-" + cursor.getString(3)));
					
//					s[length - i - 1][0] = cursor.getString(0);//_id
//					s[length - i - 1][1] = cursor.getString(1);//stock_name
//					s[length - i - 1][2] = cursor.getString(2);//stock_code
//					s[length - i - 1][3] = cursor.getString(3);//market_id
//					s[length - i - 1][4] = cursor.getString(4);//stock_type
//					s[length - i - 1][5] = cursor.getString(5);//group_name
//					s[length - i - 1][6] = cursor.getString(6);//stock_warning
//					s[length - i - 1][7] = cursor.getString(7);//stock_mark
				}
				//maps.put("stockCodeMarket", stockCodeMarket);
			}
		}finally{
		    db.setTransactionSuccessful();
            db.endTransaction();
			cursor.close();
			db.close();
			closeHelper();
		}
		return stockCodeMarket;
	}
	
	public String[][] selectByGroupName(SQLiteDatabase db , String group_name){
		String s[][] = null;
		Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_GROUP, new String[]{group_name});
		try{
			if(cursor.getCount()>0){
				s = new String[cursor.getCount()][6];
				for(int i = 0;cursor.moveToNext();i++){
					s[i][0] = cursor.getString(0);//_id
					s[i][1] = cursor.getString(1);//stock_name
					s[i][2] = cursor.getString(2);//stock_code
					s[i][3] = cursor.getString(3);//market_id
					s[i][4] = cursor.getString(4);//stock_type
					s[i][5] = cursor.getString(5);//group_name
				}
			}
		}finally{
			cursor.close();
			db.close();
			closeHelper();
		}
		return s;
	}
	
	public String[] selectStockCodes(SQLiteDatabase db){
		String[] stockCode = new String[]{"",""};//存储证券代码和市场代码
			String[][] result = selectAll(db);
			if(result!=null){
				for(int i = 0;i<result.length;i++){
					if(stockCode[0].length()==0){
						stockCode[0] = result[i][2];//stock_code
						stockCode[1] = result[i][3];//market_id
					}else{
						stockCode[0] += "," + result[i][2];//stock_code
						stockCode[1] += "," + result[i][3];//market_id
					}
				}
			}else{
				
			}
		return stockCode;
	}
	
	/**
	 * 反序
	 * @param db
	 * @return
	 */
	public String[] selectStockCodesInvert(SQLiteDatabase db){
		String[] stockCode = new String[]{"",""};//存储证券代码和市场代码
			String[][] result = selectAllInvert(db);
			if(result!=null){
				for(int i = 0;i<result.length;i++){
					if(stockCode[0].length()==0){
						stockCode[0] = result[i][2];//stock_code
						stockCode[1] = result[i][3];//market_id
					}else{
						stockCode[0] += "," + result[i][2];//stock_code
						stockCode[1] += "," + result[i][3];//stock_code
					}
				}
			}else{
				
			}
		return stockCode;
	}
	
	public static void updateUserStockDb(Context context, List<String> stockNamelist,
			List<String> stockCodelist, List<String> stockMarketlist,List<String> stockTypelist,
			List<String> groupName,List<String> stockWarninglist,List<String> stockMarklist) {

		UserStockSQLController ussc =  new UserStockSQLController(context);
        SQLiteDatabase db = ussc.getWdb();
        Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_ALL, null);
        try{
			int id = -1;
			int count = 0;
			while (cursor.moveToNext()) {
				id = cursor.getInt(cursor.getColumnIndex("_id"));
		        db.execSQL(UserStockSQLHelper.UPDATE_ALL,
		    	        new Object[] { stockNamelist.get(count),stockCodelist.get(count),
		    	        		stockMarketlist.get(count),stockTypelist.get(count),
		    	        		groupName.get(count),stockWarninglist.get(count),stockMarklist.get(count),id });
		        
				count++;
			}
        }finally{
			cursor.close();
			db.close();
			ussc.closeHelper();
        }
	}
	
	public static void updateUserStockDbInvert(Context context, List<String> stockNamelist,
			List<String> stockCodelist, List<String> stockMarketlist,List<String> stockTypelist,
			List<String> groupName,List <String> stockWarninglist,List <String> stockMarklist) {

		UserStockSQLController ussc =  new UserStockSQLController(context);
        SQLiteDatabase db = ussc.getWdb();
        Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_ALL, null);
        try{
			int id = -1;
			int count = stockNamelist.size() - 1;
			while (cursor.moveToNext()) {
				id = cursor.getInt(cursor.getColumnIndex("_id"));
		        db.execSQL(UserStockSQLHelper.UPDATE_ALL, 
		    	        new Object[] { stockNamelist.get(count),stockCodelist.get(count),
		    	        		stockMarketlist.get(count),stockTypelist.get(count),
		    	        		groupName.get(count),stockWarninglist.get(count),stockMarklist.get(count), id });
		        
				count--;
			}
        }finally{
			cursor.close();
			db.close();
			ussc.closeHelper();
        }
	}
	
	public void updateStockWarningByStockCode(SQLiteDatabase db,String stock_warning,String stock_code){
		db.execSQL(UserStockSQLHelper.UPDATE_STOCK_WARNING_FROM_STOCK_CODE, new Object[]{stock_warning,stock_code});
	}
	
	public boolean isExistStock(SQLiteDatabase db, String stockCode, String marketId){
		boolean result = false;
		Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_BY_STOCKCODE, new String[]{stockCode, marketId});
		try {
			if(cursor.getCount() > 0){
				result = true;
			}
		} finally {
			cursor.close();
			db.close();
			closeHelper();
		}
		return result;
	}
	
	public int getUserStockCount(SQLiteDatabase db){
		int count  = 0;
		Cursor cursor;
		cursor = db.rawQuery(UserStockSQLHelper.GET_COUNT, null);
		try{
			if(cursor.moveToNext()){
		          count = cursor.getInt(0);
		   }
			
		}finally{
			cursor.close();
			db.close();
			closeHelper();
		}
		return count;
	}
	
	public String selectStockCodes_MarketId(SQLiteDatabase db){
		String s = "";
		Cursor cursor = db.rawQuery(UserStockSQLHelper.SELECT_ALL, null);
		try{
			if(cursor.getCount()>0){
				int length = cursor.getCount();
				for(int i = 0;cursor.moveToNext();i++){
					String temp = cursor.getString(2) + "-" + cursor.getString(3);//stockCode-marketId
					if(StringUtils.isEmpty(s)){
						s += temp;
					}else{
						s += "," + temp;
					}
				}
			}
		}finally{
			cursor.close();
			db.close();
			closeHelper();
		}
		return s;
	}
}
