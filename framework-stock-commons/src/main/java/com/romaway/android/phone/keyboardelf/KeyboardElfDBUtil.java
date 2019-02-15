/**
 * 
 */
package com.romaway.android.phone.keyboardelf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.romaway.android.phone.R;
import com.romaway.android.phone.SharedPreferenceConstants;
import com.romaway.android.phone.utils.RegexpUtils;
import com.romaway.android.phone.utils.StockCacheInfo;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.db.DbUtils;
import com.romaway.commons.log.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 键盘精灵操作
 * 
 * @author duminghui
 * 
 */
public class KeyboardElfDBUtil {
	private SQLiteOpenHelper sqliteOpenHelper;
	private List<String> hkTypeList;

	public KeyboardElfDBUtil(Context context) {
		// 【Bug】使用ApplicationContext, 避免Activity被回收, 导致空指针问题;
		sqliteOpenHelper = new KeyboardElfSQLiteHelper(OriginalContext.getContext());

		hkTypeList = new ArrayList<String>();
		hkTypeList.add(String.valueOf(ProtocolConstant.SE_GG));
		hkTypeList.add(String.valueOf(ProtocolConstant.SE_HGT));
		hkTypeList.add(String.valueOf(ProtocolConstant.SE_SGT));
	}

	/**
	 * 获取可写数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getWdb() {
		return sqliteOpenHelper.getReadableDatabase();
	}

	/**
	 * 检查键盘精灵数据库文件是否存在
	 * 
	 * @param assets_name
	 * @param appPackage
	 * @return
	 */
	public static boolean checkDbExist(String assets_name, String appPackage) {
		String dbFile = String.format("/data/data/%s/databases/%s", appPackage,
				assets_name);

		return (DbUtils.checkDataBase(dbFile));
	}

	/**
	 * 初始化键盘精灵数据 1.首先检查本地是否有键盘精灵数据库，如果有，则直接返回false，否则返回true
	 * 2.如果本地还未有键盘精灵数据，则复制预制的键盘精灵数据库，并更新版本。版本日期 为预制的键盘精灵度对应的日期。
	 * 3.成功则返回true,失败，则返回false.。
	 * 
	 * @return
	 */
	public static boolean copyDbFile(Context context, String assets_name,
			String appPackage) {
		boolean result = false;

		String dbFile = String.format("/data/data/%s/databases/%s", appPackage,
				assets_name);
		Logger.d("Login.First", String.format("copy键盘精灵:%s", dbFile));
		try {
			// 20150418增加,若无数据库则需先添加目录后复制
			// KeyboardElfDBUtil dbutil = new KeyboardElfDBUtil(context);
			// SQLiteDatabase db = dbutil.getWdb();

			DbUtils.copyDataBase(context, assets_name, dbFile);
			result = true;
		} catch (IOException exception) {
			exception.printStackTrace();
			Logger.e("Login.First", exception.getMessage());
			result = false;
		}

		if (result) {
			// 复制成功，则写数据库版本日期
			SharedPreferenceUtils.setPreference(
					SharedPreferenceUtils.DATA_USER, String.format(
							SharedPreferenceConstants.KEYBOARDELF_UPDATE_TIME,
							ProtocolConstant.SE_SS), Res
							.getInteger(R.dimen.stockdb_datever)); // Res.getInteger(R.integer.stockdb_datever)
		}

		return result;
	}

	/**
	 * 添加数据
	 * 
	 * @param py
	 * @param marektId
	 * @param stockCode
	 * @param stockName
	 */
	public void add(SQLiteDatabase db, String[] stockCode, String[] stockName,
			String[] py, int[] marektId, int[] codeType) {
		for (int index = 0; index < py.length; index++) {
			db.execSQL(KeyboardElfSQLiteHelper.DATA_INSERT, new Object[] {
					stockCode[index], stockName[index],
					py[index].toUpperCase(), marektId[index], codeType[index] });
		}
	}
	
	public void add(SQLiteDatabase db, String[] stockCode, String[] stockName,
			String[] py, int[] marektId, int[] codeType, String[] stockMark) {
		for (int index = 0; index < py.length; index++) {
			db.execSQL(KeyboardElfSQLiteHelper.DATA_INSERT_WITH_MARK, new Object[] {
					stockCode[index], stockName[index],
					py[index].toUpperCase(), marektId[index], codeType[index], stockMark[index] });
		}
	}

	/**
	 * 根据stockCode删除数据
	 * 
	 * @param stockCode
	 */
	public void del(SQLiteDatabase db, String[] stockCode, int[] marketId) {
		boolean _marketId = true;
		if (marketId == null || marketId.length == 0) {
			_marketId = false;
		}
		for (int index = 0; index < stockCode.length; index++) {
			if (_marketId) {
				db.execSQL(KeyboardElfSQLiteHelper.DATA_DELETE_WITH_MARKETID,
						new Object[] { stockCode[index], marketId[index] });
			} else {
				db.execSQL(KeyboardElfSQLiteHelper.DATA_DELETE,
						new Object[] { stockCode[index] });
			}
		}
	}

	/**
	 * 从特定的市场查询
	 * 
	 * @param py
	 * @param wMarketId
	 * @return 0:股票代码,1:股票名称,2:拼音,3:股票类型
	 */
	public String[][] search(String py, int wMarketId) {
		SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
		py = String.format("%s%%", py.toUpperCase());
		String[][] result;
		try {
			Cursor cursor;
//			if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_search_cn))
//				cursor = db.rawQuery(KeyboardElfSQLiteHelper.DATA_SELECT_NAME,
//					new String[] { py, py, py, String.valueOf(wMarketId) });
//			else
				cursor = db.rawQuery(KeyboardElfSQLiteHelper.DATA_SELECT,
					new String[] { py, py, String.valueOf(wMarketId) });
			int count = cursor.getCount();
			result = new String[count][4];
			for (int index = 0; cursor.moveToNext(); index++) {
				result[index][0] = cursor.getString(0);
				result[index][1] = cursor.getString(1);
				result[index][2] = cursor.getString(2);
				result[index][3] = cursor.getString(3);
			}
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * 查找从所有市场查询
	 * 
	 * @param py
	 * @return 0:股票代码,1:股票名称,2:拼音,3:股票类型,4:市场代码
	 */
	public String[][] search(String py) {
		SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
		String[][] result;
		String searchContent = py;
		try {
			Cursor cursor;
			String pxFirst = String.format("%s%%", py);
			if (RegexpUtils.isValidStockCode(py)) {
				py = String.format("%%%s%%", py);
				if (isSearchHSAgu()){
					cursor = db.rawQuery(
							KeyboardElfSQLiteHelper.DATA_SELECT_HSA_CODE,
							new String[]{py, py, "1", "16","128", "256", "2048", "8192", pxFirst});
				} else {
					cursor = db.rawQuery(
							KeyboardElfSQLiteHelper.DATA_SELECT_ALL_CODE,
							new String[]{py, py, pxFirst});
				}
			} else {
				py = String.format("%%%s%%", py);
				if (isSearchHSAgu()){
					if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_search_cn))
						cursor = db.rawQuery(
								KeyboardElfSQLiteHelper.DATA_SELECT_HSA_PY_NAME,
								new String[]{py, py, "1", "16","128", "256", "2048", "8192", pxFirst});
					else
						cursor = db.rawQuery(
								KeyboardElfSQLiteHelper.DATA_SELECT_HSA_PY,
								new String[]{py, "1", "16", "128", "256", "2048", "8192", pxFirst});
				} else {
					if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_search_cn))
						cursor = db.rawQuery(
								KeyboardElfSQLiteHelper.DATA_SELECT_ALL_NAME,
								new String[]{py, py, py});
					else
						cursor = db.rawQuery(KeyboardElfSQLiteHelper.DATA_SELECT_ALL,
								new String[]{py, py});
				}
			}
			// add by wanlaihuan for save stock list to cache
			StockCacheInfo.clearCacheList();

			// 过滤排序搜索结果, 将港股类数据放置搜索列表前面:
			result = sortStockList(cursor, searchContent);
		} finally {
			db.close();
		}
		return result;
	}

	// stockCode,stockName,py,codeType,marketId,stockMark
	public String[][] sortStockList(Cursor cursor, String searchContent){
		try {
			List<String[]> hkStockList = new ArrayList<String[]>();
			List<String[]> otherStockList = new ArrayList<String[]>();
			List<StockCacheInfo> otherStockCacheList = new ArrayList<StockCacheInfo>();

			String[] stockInfo;
			boolean isSearchHKLength = searchContent.length() > 4;
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					stockInfo = new String[8];
					stockInfo[0] = cursor.getString(0);
					stockInfo[1] = cursor.getString(1);
					stockInfo[2] = cursor.getString(2);
					stockInfo[3] = cursor.getString(3);
					stockInfo[4] = cursor.getString(4);
					stockInfo[5] = cursor.getString(5);
					if (isSearchHKLength && hkTypeList.contains(stockInfo[4])) {            // marketId
						hkStockList.add(stockInfo);
						// add by wanlaihuan for save stock list to cache
						StockCacheInfo.saveToCacheList(stockInfo[1],
								stockInfo[0], stockInfo[4], stockInfo[3]);
					}else {
						otherStockList.add(stockInfo);
						otherStockCacheList.add(StockCacheInfo.getStockCacheInfo(
								stockInfo[1], stockInfo[0], stockInfo[4], stockInfo[3]));
					}
				}
				hkStockList.addAll(otherStockList);
				// save stock list to cache
				StockCacheInfo.getCacheList().addAll(otherStockCacheList);
				return hkStockList.toArray(new String[cursor.getCount()][8]);
			}
		}catch (Exception e){
			Logger.e("TAG", e.getMessage());
		}finally {
			if (cursor != null)
				cursor.close();
		}
		return new String[0][8];
	}

	/**
	 * 查找从所有市场查询
	 *
	 * @param py
	 * @return 0:股票代码,1:股票名称,2:拼音,3:股票类型,4:市场代码
	 */
	public String[][] searchLast(String py) {
		SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
		String[][] result;
		String searchContent = py;
		try {
			Cursor cursor;
			String pxFirst = String.format("%s%%", py);
			if (RegexpUtils.isValidStockCode(py)) {
				py = String.format("%%%s%%", py);
				if (isSearchHSAgu()){
					cursor = db.rawQuery(
							KeyboardElfSQLiteHelper.DATA_SELECT_HSA_CODE,
							new String[]{py, py, "1", "128", "256", "2048", "8192", pxFirst});
				} else {
					cursor = db.rawQuery(
							KeyboardElfSQLiteHelper.DATA_SELECT_ALL_CODE,
							new String[]{py, py, pxFirst});
				}
			} else {
				py = String.format("%%%s%%", py);
				if (isSearchHSAgu()){
//					if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_search_cn))
//						cursor = db.rawQuery(
//								KeyboardElfSQLiteHelper.DATA_SELECT_HSA_PY_NAME,
//								new String[]{py, py, "1", "128", "256", "2048", "8192", pxFirst});
//					else
						cursor = db.rawQuery(
								KeyboardElfSQLiteHelper.DATA_SELECT_HSA_PY,
								new String[]{py, "1", "128", "256", "2048", "8192"});
				} else {
//					if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_search_cn))
//						cursor = db.rawQuery(
//								KeyboardElfSQLiteHelper.DATA_SELECT_ALL_NAME,
//								new String[]{py, py, py});
//					else
						cursor = db.rawQuery(KeyboardElfSQLiteHelper.DATA_SELECT_ALL,
								new String[]{py, py});
				}
			}
			// add by wanlaihuan for save stock list to cache
			StockCacheInfo.clearCacheList();

			// 过滤排序搜索结果, 将港股类数据放置搜索列表前面:
			result = sortStockList(cursor, searchContent);
		} finally {
			db.close();
		}
		return result;
	}

	public void setIsSearchHSAgu(boolean isSearchHSAgu) {
		this.isSearchHSAgu = isSearchHSAgu;
	}

	public boolean isSearchHSAgu() {
		return isSearchHSAgu;
	}

	/**
	 * 是否只查询沪深A股（投顾）
	 */
	private boolean isSearchHSAgu = false;


	
	/**
	 * 查找港股
	 * 
	 * @param py
	 * @return 0:股票代码,1:股票名称,2:拼音,3:股票类型,4:市场代码
	 */
	public String[][] searchGangGu(String py) {
		SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
		String[][] result;
		String searchContent = py;
		try {
			Cursor cursor;
			if (RegexpUtils.isValidStockCode(py)) {
				py = String.format("%%%s%%", py);
				cursor = db.rawQuery(
						KeyboardElfSQLiteHelper.DATA_SELECT_ALL_GangGuCODE,
						new String[] { py, py });
			} else {
				py = String.format("%%%s%%", py);
//				if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_search_cn))
//						cursor = db.rawQuery(KeyboardElfSQLiteHelper.DATA_SELECT_ALL_NAME_GangGu,
//						new String[] { py, py, py});
//				else
					cursor = db.rawQuery(KeyboardElfSQLiteHelper.DATA_SELECT_ALL_GangGu,
							new String[] { py, py });
			}
			// add by wanlaihuan for save stock list to cache
			StockCacheInfo.clearCacheList();

			// 过滤排序搜索结果, 将港股类数据放置搜索列表前面:
			result = sortStockList(cursor, searchContent);
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * 股票预警专用查询
	 * 
	 * @param code
	 * @return 0:股票代码,1:股票名称
	 */
	public String[][] search_gpyj(String code) {
		SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
		code = String.format("%s%%", code);
		String[][] result;
		try {
			Cursor cursor = db.rawQuery(
					KeyboardElfSQLiteHelper.DATA_SELECT_ALL_GPYJ,
					new String[] { code });
			int count = cursor.getCount();
			result = new String[count][2];
			for (int index = 0; cursor.moveToNext(); index++) {
				result[index][0] = cursor.getString(0);
				result[index][1] = cursor.getString(1);
			}
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * 通过股票代码查找出多市场ID
	 * 
	 * @param stockCode
	 * @return 0:股票代码,1:股票名称
	 */
	public List<String> searchMarketId(String stockCode) {
		List<String> marketIdList = new ArrayList<String>();
		SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
		
		try {
			Cursor cursor = db.rawQuery(
					KeyboardElfSQLiteHelper.DATA_SELECT_MARKETID,
					new String[] { stockCode });

			for (int index = 0; cursor.moveToNext(); index++){
				marketIdList.add(""+cursor.getInt(0));
				//Logger.d("多市场搜索", "marketId["+index+"]="+marketIdList.get(index));
			}

		} finally {
			db.close();
		}
		return marketIdList;
	}

	/**
	 * 获取A股键盘精灵版本
	 * 
	 * @return
	 */
	public static int getVer() {
		return SharedPreferenceUtils.getPreference(
				SharedPreferenceUtils.DATA_USER, String.format(
						SharedPreferenceConstants.KEYBOARDELF_UPDATE_TIME,
						ProtocolConstant.SE_SS), 0);
	}

	/**
	 * 清空数据库所有记录, 同时将自增的id值归零
	 * @return
	 */
	public void clearTableData() {
//		delete from '表名'; 
//		select * from sqlite_sequence;  
//		update sqlite_sequence set seq=0 where name='表名';
		
		SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
		db.execSQL(KeyboardElfSQLiteHelper.DROP_TABLE);
		sqliteOpenHelper.onCreate(db);
	}
}
