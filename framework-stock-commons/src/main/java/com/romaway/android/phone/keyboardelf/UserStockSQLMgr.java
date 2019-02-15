package com.romaway.android.phone.keyboardelf;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.romaway.commons.lang.StringUtils;

public class UserStockSQLMgr {
	
	/**
	 * 反序插入
	 * @param context
	 * @param list
	 */
	public static void insertDataInvert(Context context, List<String[]> list){
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		db.beginTransaction();
		try {
			for (int i = list.size() - 1; i >= 0; i--) {
				String[] item = list.get(i);
				ussc.insert(db, item[1], item[2], item[3], item[4], item[5], item[6], item[7]);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (db != null ) {
				db.setTransactionSuccessful();
				db.endTransaction();
				db.close();
			}
		}
	}
	
	public static void insertData(Context context, String stock_name,
			String stock_code, String market_id, String stock_type,
			String group_name, String stock_warning, String stock_mark) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			ussc.insert(db, stock_name, stock_code, market_id, stock_type,
					group_name, stock_warning, stock_mark);
		}catch(Exception e){
			
		}
		finally {
			if(db != null)
				db.close();
		}
	}

	public static void insertData(Context context, String[] stock_name,
			String[] stock_code, String[] market_id, String[] stock_type,
			String[] group_name, String[] stock_warning, String[] stock_mark) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		db.beginTransaction();
		try {
			for (int i = 0; i < stock_code.length; i++) {
				if (!StringUtils.isEmpty(market_id[i]))
					ussc.insert(db, stock_name[i], stock_code[i], market_id[i],
							stock_type[i], group_name[i], stock_warning[i], stock_mark[i]);
			}
		}catch(Exception e){
			
		} finally {
			if(db != null){
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();}
		}
	}

	public static void insertData(Context context, List<String> stock_nameList,
						  List<String> stock_codeList, List<String> market_idList,
						  List<String> stock_typeList, String group_name, String stock_warning, List<String> stockMarklist) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		db.beginTransaction();
		try {
			for (int i = 0; i < stock_codeList.size(); i++) {
				ussc.insert(db,
						stock_nameList==null? "" : stock_nameList.get(i),
						stock_codeList==null? "" : stock_codeList.get(i),
						market_idList==null? "" : market_idList.get(i),
						stock_typeList==null? "" : stock_typeList.get(i),
						group_name,
						stock_warning,
						stockMarklist==null? "" : stockMarklist.get(i));
			}
		}catch(Exception e){

		} finally {
			if(db != null){
				db.setTransactionSuccessful();
				db.endTransaction();
				db.close();
			}
		}
	}

	public static void insertDataInvert(Context context, List<String> stock_nameList,
			List<String> stock_codeList, List<String> market_idList,
			List<String> stock_typeList, String group_name, String stock_warning, List<String> stockMarklist) {

		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		db.beginTransaction();
		try {
			for (int i = stock_codeList.size() - 1; i >= 0; i--) {
				ussc.insert(db, 
					stock_nameList==null? "" : stock_nameList.get(i), 
					stock_codeList==null? "" : stock_codeList.get(i),
					market_idList==null? "" : market_idList.get(i), 
					stock_typeList==null? "" : stock_typeList.get(i),
					group_name, 
					stock_warning,
					stockMarklist==null? "" : stockMarklist.get(i));
			}
		}catch(Exception e){
			
		} finally {
			if(db != null){
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();
			}
		}
	}

	public static void deleteAll(Context context) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			ussc.delete(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
	}

	public static void deleteById(Context context, int _id) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			ussc.deleteById(db, _id);
		}catch(Exception e){
			
		}  finally {
			if(db != null)
				db.close();
		}
	}

	public static void deleteByStockCode(Context context, String stock_code, String market_id) {
		//增加异常处理，防止出现空指针;
		if (context == null || StringUtils.isEmpty(stock_code)) {
			return;
		}
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			ussc.deleteByStockCode(db, stock_code, market_id);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
	}

	public static void deleteByGroup(Context context, String group_name) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			ussc.deleteByGroupName(db, group_name);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
	}

	public static String[][] queryAll(Context context) {
		String[][] result = null;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			result = ussc.selectAll(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return result;
	}

	/**
	 * 倒序查全部
	 * 
	 * @param context
	 * @return
	 */
	public static String[][] queryAllInvert(Context context) {
		String[][] result = null;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			result = ussc.selectAllInvert(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return result;
	}

	public static String[][] queryByGroup(Context context, String group_name) {
		String[][] result = null;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			result = ussc.selectByGroupName(db, group_name);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return result;
	}

	public static String[] queryStockCodes(Context context) {
		String[] s = null;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			s = ussc.selectStockCodes(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return s;
	}

	public static String getStockCodes(Context context) {
		String s = "";
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			s = ussc.getStockCodes(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return s;
	}

	/**
	 * 反序取证券代码和市场ID
	 * 
	 * @param context
	 * @return
	 */
	public static String[] queryStockCodesInvert(Context context) {
		String[] s = null;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			s = ussc.selectStockCodesInvert(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return s;
	}

	public static String queryStockCodesInvert1(Context context) {
		String s = null;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			s = ussc.selectAllInvert1(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return s;
	}
	
	/**
	 * 获取数据库中所有数据到管理列表中
	 * 
	 * @return
	 */
	public static void queryUserStockDataToList(Context context,
			List<String> stockNamelist, List<String> stockCodelist,
			List<String> stockMarketlist, List<String> stockTypelist,
			List<String> groupNameList, List<String> stockWarninglist, List<String> stockMarklist) {
		String[][] result;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			result = ussc.selectAll(db);
			if (stockNamelist != null) {
				stockNamelist.clear();
			}
			if (stockCodelist != null) {
				stockCodelist.clear();
			}
			if (stockMarketlist != null) {
				stockMarketlist.clear();
			}
			if (stockTypelist != null) {
				stockTypelist.clear();
			}
			if (groupNameList != null) {
				groupNameList.clear();
			}
			if (stockWarninglist != null) {
				stockWarninglist.clear();
			}
			if (stockMarklist != null) {
				stockMarklist.clear();
			}
			for (int i = 0; i < result.length; i++) {
				if (stockNamelist != null) {
					stockNamelist.add(result[i][1]);
				}
				if (stockCodelist != null) {
					stockCodelist.add(result[i][2]);
				}
				if (stockMarketlist != null) {
					stockMarketlist.add(result[i][3]);
				}
				if (stockTypelist != null) {
					stockTypelist.add(result[i][4]);
				}
				if (groupNameList != null) {
					groupNameList.add(result[i][5]);
				}
				if (stockWarninglist != null) {
					stockWarninglist.add(result[i][6]);
				}
				if (stockMarklist != null) {
					stockMarklist.add(result[i][7]);
				}
			}
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
	}

	/**
	 * 获取数据库中所有数据到管理列表中
	 * @return
	 */
	public static String[][] queryUserStockDataToListInvert(Context context) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			return ussc.selectAllInvert(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
		return null;
	}

	public static void updateUserStockDb(Context context,
			List<String> stockNamelist, List<String> stockCodelist,
			List<String> stockMarketlist, List<String> stockTypelist,
			List<String> groupName, List<String> stockWarninglist, List<String> stockMarklist) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			UserStockSQLController.updateUserStockDb(context, stockNamelist,
					stockCodelist, stockMarketlist, stockTypelist, groupName,
					stockWarninglist, stockMarklist);
		}catch(Exception e){
			
		}
		finally {
			if(db != null)
				db.close();
		}
	}

	public static void updateUserStockDbInvert(Context context,
			List<String> stockNamelist, List<String> stockCodelist,
			List<String> stockMarketlist, List<String> stockTypelist,
			List<String> groupName, List<String> stockWarninglist, List<String> stockMarklist) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			UserStockSQLController.updateUserStockDbInvert(context,
					stockNamelist, stockCodelist, stockMarketlist,
					stockTypelist, groupName, stockWarninglist, stockMarklist);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
	}

	public static void updateStockWarningIdentify(Context context,
			String stock_warning, String stock_code) {
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			ussc.updateStockWarningByStockCode(db, stock_warning, stock_code);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}
	}

	public static boolean isExistStock(Context context, String stockCode,
			String marketId) {
		boolean result = false;
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			result = ussc.isExistStock(db, stockCode, marketId);

		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}

		return result;
	}

	public static int getUserStockCount(Context context) {
		int count = 0;
		if (context == null ) {
			return count;
		}
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			count = ussc.getUserStockCount(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}

		return count;
	}

	public static String selectStockCodes_Market(Context context) {
		String result = "";
		UserStockSQLController ussc = new UserStockSQLController(context);
		SQLiteDatabase db = ussc.getWdb();
		try {
			result = ussc.selectStockCodes_MarketId(db);
		}catch(Exception e){
			
		} finally {
			if(db != null)
				db.close();
		}

		return result;
	}
}
