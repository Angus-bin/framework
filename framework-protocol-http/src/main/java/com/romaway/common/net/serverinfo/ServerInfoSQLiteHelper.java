package com.romaway.common.net.serverinfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Keep;


@Keep
public class ServerInfoSQLiteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "ServerInfo";
	private static final String TABLE_CREATE = "CREATE TABLE serverinfo(serverid INTEGER UNIQUE,serverFlag VARCHAR(200),serverType INTEGER,serverName VARCHAR,serverUrl VARCHAR(200),keepAlive INTEGER, httpsPort INTEGER)";
	//private static final String INDEX_CREATE = "CREATE INDEX idx_serverinfo_servertype ON serverinfo(serverType)";
	public static final String SQL_SELECT = "SELECT serverFlag,serverType,serverName,serverUrl,httpsPort,keepAlive FROM serverinfo WHERE serverType=?";
	public static final String SQL_SELECT_ALL = "SELECT serverFlag,serverType,serverName,serverUrl,httpsPort,keepAlive FROM serverinfo";
	public static final String SQL_INSERT = "INSERT OR REPLACE INTO serverinfo(serverid,serverFlag,serverType,serverName,serverUrl,httpsPort,keepAlive) VALUES (?,?,?,?,?,?,?)";
	public static final String SQL_SELECT_BY_URL = "SELECT serverFlag,serverType,serverName,serverUrl,httpsPort,keepAlive FROM serverinfo WHERE serverUrl=?";
	public static final String SQL_SELECT_SERVERID_MAX_VALUE = "SELECT MAX(serverid) FROM serverinfo";
	public static final String SQL_DELETE_BY_SERVER_TYPE = "DELETE FROM serverinfo WHERE serverType=?";
	public static final String SQL_DELETE_ALL = "DELETE FROM serverinfo";
	
	public ServerInfoSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_CREATE);
		//db.execSQL(INDEX_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion == DATABASE_VERSION) {
			db.execSQL("alter table serverinfo add serverType INTEGER");
			//db.execSQL("if not exists (select * from sqlite_master where name='ServerInfo' and sql like serverType) alter table serverinfo add serverType INTEGER");
		}
	}
}
