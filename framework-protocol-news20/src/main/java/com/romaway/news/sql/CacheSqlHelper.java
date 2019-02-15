package com.romaway.news.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheSqlHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 3;
	public String name = "";

	public CacheSqlHelper(Context context, String tabName, int version)
	{
		super(context, tabName, null, version); 
		name = tabName;
	}

	/**
	 * userId: 用户ID
	 * fun_type: 栏目类型
	 * newsId： 资讯唯一标示，最新的数据id最大，也即有从大到小的排序
	 * stockCode： 股票代码 代码+市场ID
	 * item_datas ： 选项的数据
	 * uptime ： 缓存的时间
	 * read_flag：已读标记
	 * details：资讯详情
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CREATE_TABLE = getCreateTableStr(name);
		db.execSQL(CREATE_TABLE);
	}

	/** 获取创建表SQL语句(数据库升级重用) */
	private String getCreateTableStr(String tableName){
		// [Bug] 字符串通过ASCII值比较大小, 但要求比较的字符串的 形式及长度 必须相同才能正确比较, 当 "999" 比 "1000"时会错误判断为 "999" > "1000";
		// 故用于排序的 newsId 字段必须为 INTEGER 类型;
		return "CREATE TABLE IF NOT EXISTS "
				+ tableName
				+ " (userId VARCHAR,fun_type VARCHAR,newsId INTEGER,stockCode VARCHAR,item_datas VARCHAR,"
				+ "uptime VARCHAR,orderId VARCHAR,read_flag VARCHAR,details VARCHAR, msgType VARCHAR)";
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 1) {
			// 1. 重命名, 创建临时表:
			String tempName = name + "_temp";
			db.execSQL("ALTER TABLE " + name + "RENAME TO" + tempName);
			// 2. 重新创建表:
			db.execSQL(getCreateTableStr(name));
			// 3. 将旧表的内容插入到新表中:
			String insertSQL = "INSERT INTO " + name + " (userId, fun_type, newsId, stockCode, item_datas, uptime, orderId, read_flag, details) " +
					"SELECT userId, fun_type, newsId, stockCode, item_datas, uptime, orderId, read_flag, details FROM " + tempName;
			db.execSQL(insertSQL);
			// 4. 删除旧表:
			db.execSQL("DROP TABLE " + tempName);
		}else if (oldVersion == 2){
			// 添加 资讯7*24小时 A股/国际 消息分类时 msgType 字段:	 [ ALTER TABLE 表名 ADD COLUMN 列名 数据类型 ]
			db.execSQL("ALTER TABLE " + name + "ADD COLUMN msgType VARCHAR");
		}
	}
}
