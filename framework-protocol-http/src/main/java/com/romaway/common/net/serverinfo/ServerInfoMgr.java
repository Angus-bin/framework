/**
 * 
 */
package com.romaway.common.net.serverinfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Keep;
import android.text.TextUtils;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 服务器信息管理<br>
 * 以服务器type为Key
 * 
 * @author duminghui xueyan:2012.4.16修改getDefaultServerInfo（）方法
 */
@Keep
public class ServerInfoMgr {
	SQLiteOpenHelper sqliteOpenHelper;
	public static final String KEY_SERVERFLAG = "flag";
	public static final String KEY_SERVERNAME = "name";
	public static final String KEY_SERVERTYPE = "type";
	public static final String KEY_SERVERURL = "url";
	public static final String KEY_KEEPALIVE = "keepalive";

	private final static ServerInfoMgr instance = new ServerInfoMgr();

	//private ServerInfo defaultServer;
	// private List<ServerInfo> defaultList = new ArrayList<ServerInfo>();
	//private List<ServerInfo> serverList = new ArrayList<ServerInfo>();
	/**
	 * 每个类型的站点存放一个，作为默认站点
	 */
	private Map<Integer, ServerInfo> mapDefaultServers = new HashMap<Integer, ServerInfo>();
	/**
	 * 每个类型的站点存放在一个数组里，用于存放所有的站点
	 */
	private Map<Integer, List<ServerInfo>> mapServers = new HashMap<Integer, List<ServerInfo>>();

	private ServerInfoMgr() {
		
	}

	public final static ServerInfoMgr getInstance() {
		return instance;
	}

	/**
	 * 切换服务器,当切换成功后，返回true,否则，返回false;
	 * 
	 * @param currentServerInfo
	 */
	public final boolean changeServer(ServerInfo currentServerInfo) {
		int serverType = currentServerInfo.getServerType();
		String url = currentServerInfo.getAddress();
		if (!StringUtils.isEmpty(url) && url.contains("https")) {
			url = url.replace("https", "http");
		}
		int currIndex = url.lastIndexOf(":");
		if (currIndex > 5) {
			url = url.substring(0, currIndex);
		}
		if (mapServers == null || mapServers.isEmpty()) {
			return false;
		}
		List<ServerInfo> lstServerInfos = mapServers.get(serverType);
		if (lstServerInfos == null || lstServerInfos.size() < 1) {
			return false; // 若只有1个服务器，则返回
		}
		if (lstServerInfos.size() == 1 && lstServerInfos.get(0).getAddress().equals(currentServerInfo.getAddress())) {
			return false; // 若只有1个服务器，则返回
		}
		boolean hasFound = false;
		int idx = 0;
		ServerInfo tmpServerInfo = currentServerInfo;
		boolean useFirst = false;
		for (ServerInfo s : lstServerInfos) {
			if (idx == 0) {
				tmpServerInfo = s;
				useFirst = true;
			}
			if (hasFound) {
				setDefaultServerInfo(s);
				return true;
			}
			String tempUrl = s.getUrl();
			int index = tempUrl.lastIndexOf(":");
			if (index > 5) {
				tempUrl = tempUrl.substring(0, index);
			}
			if (tempUrl.equalsIgnoreCase(url)) {
				hasFound = true; // 服务器相同
			}
			idx++;
		}

		// 若是最后1个服务器，则使用第1条。
		if (useFirst) {
			setDefaultServerInfo(tmpServerInfo);
		}
		return useFirst;
	}

	/**
	 * 清空缓存mapDefaultServers,mapServers中的数据
	 */
	public final void clearServerCache() {
		mapDefaultServers.clear();
	    mapServers.clear();
	}
	
	/**
	 * 清空mapServers中的数据
	 */
	public final void clearServer(){
		mapServers.clear();
	}

	/**
	 * 添加服务器信息
	 * 
	 * @param serverInfo
	 */
	public final void addServerInfo(ServerInfo serverInfo) {
		if (mapDefaultServers.get(serverInfo.getServerType()) == null) {
			mapDefaultServers.put(serverInfo.getServerType(), serverInfo);
		}
		List<ServerInfo> lstServerInfos = mapServers.get(serverInfo
				.getServerType());
		if (lstServerInfos == null) {
			lstServerInfos = new ArrayList<ServerInfo>();
			mapServers.put(serverInfo.getServerType(), lstServerInfos);
		}
		boolean addSuccess = false;
		if (!lstServerInfos.contains(serverInfo)) {
			lstServerInfos.add(serverInfo);
			addSuccess = true;
		}
		Logger.d("Login.First",
				String.format(
						"ServerInfoMgr.addServerInfo:[st:%s][sf:%s][sn:%s][url:%s][kl:%s][success:%s]",
						serverInfo.getServerType(), serverInfo.getServerFlag(),
						serverInfo.getServerName(), serverInfo.getUrl(),
						serverInfo.isKeepAlive(), addSuccess));
	}

	public void setIP(int serverType, ServerInfo serverInfo) {
		if (serverInfo.getUrl() == null || serverInfo.getUrl().length() < 9) {
			return;
		}
		mapDefaultServers.put(serverType, serverInfo);
	}

	/**
	 * 获取默认服务器
	 * 
	 * @return
	 */
	public final ServerInfo getDefaultServerInfo(int serverType) {
		ServerInfo s = null;
		if (mapDefaultServers != null && !mapDefaultServers.isEmpty()) {
			s = mapDefaultServers.get(serverType);
		}

		// 若为空，或者不存在url,则说明，内存中的数据已销毁，则需要重新从暂存地址内加载数据，并
		if (s == null || StringUtils.isEmpty(s.getUrl())) {
			s = this.getServerInfo(serverType);
		}

		// [兼容处理]如果当前serverType类型站点未配置, 则取认证站点地址:
		if ((s == null || StringUtils.isEmpty(s.getUrl())) && serverType != ProtocolConstant.SERVER_FW_AUTH) {
			s = getDefaultServerInfo(ProtocolConstant.SERVER_FW_AUTH);
		}
		return s;
	}

	/**
	 * 设置默认服务器信息
	 * 
	 */
	public final void setDefaultServerInfo(ServerInfo serverInfo) {
		mapDefaultServers.put(serverInfo.getServerType(), serverInfo);
	}

	/**
	 * 获取服务器列表
	 * 
	 * @return
	 */
	public final List<ServerInfo> getServerInfos(int serverType) {
		return mapServers.get(serverType);
	}

	/**
	 * 清除默认服务器<br>
	 * 此方法有竞争条件:如果线程A清空了Default
	 * ServerInfo，还没有进入添加ServerInfo的操作，此时线程B读取默认服务器信息，会读到null值
	 * 
	 * @param serverType
	 */
	public final void clearDefaultServerInfo(int serverType) {
		List<ServerInfo> lstServers = mapServers.get(serverType);
		if (lstServers != null) {
			lstServers.clear();
		}
		mapDefaultServers.remove(serverType);
		Logger.d("ServerInfoMgr",
				String.format("clearServers:[st:%s]", serverType));
	}

	// 数据库操作

	/**
	 * 从数据库中查找数据
	 * 
	 * @return
	 */
	public ServerInfo getServerInfo(int serverType) {

		SQLiteDatabase db = getSQLiteOpenHelper().getReadableDatabase();

		ServerInfo s = null;
		try {
			Cursor cursor = db
					.rawQuery(ServerInfoSQLiteHelper.SQL_SELECT, new String[]{serverType + ""});
			int count = cursor.getCount();
			if (cursor.moveToNext()) {
				s = new ServerInfo(cursor.getString(0), cursor.getInt(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getInt(5) == 1 ? true : false, cursor.getInt(4));
				this.addServerInfo(s);
			}
		} catch (Exception e) {
			// System.out.println("查询出错"+e.getMessage());
		} finally {
			db.close();
		}
		return s;
	}

	/**
	 * 获取单独的站点列表， 与站点类型无关
	 * @return
	 */
	public List<ServerInfo> getServerInfoList() {
		List<ServerInfo> list = new ArrayList<ServerInfo>();
		SQLiteDatabase db = getSQLiteOpenHelper().getReadableDatabase();
		try {
			Cursor cursor = db
					.rawQuery(ServerInfoSQLiteHelper.SQL_SELECT_ALL, null);
			while (cursor.moveToNext()) {
				ServerInfo s = new ServerInfo(cursor.getString(0), cursor.getInt(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getInt(5) == 1 ? true : false, cursor.getInt(4));
				if (!list.contains(s)) {
					list.add(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("查询出错"+e.getMessage());
		} finally {
			db.close();
		}
		//若数据库没有，则从设置的默认服务器中取
		if (list.isEmpty()) {
			for (int i = 0; i < mapDefaultServers.size(); i++) {
				Set<Integer> keys = mapDefaultServers.keySet();
				for (Integer key : keys) {
					ServerInfo s = mapDefaultServers.get(key);
					if (!list.contains(s)) {
						list.add(s);
					}
				}
			}
		}
		return list;
	}
	
	public List<ServerInfo> getServerInfoList(int serverType){
		List<ServerInfo> list = new ArrayList<ServerInfo>();
		SQLiteDatabase db = getSQLiteOpenHelper().getReadableDatabase();
		try {
			Cursor cursor = db
					.rawQuery(ServerInfoSQLiteHelper.SQL_SELECT, new String[]{serverType+""});
			while (cursor.moveToNext()) {
				ServerInfo s = new ServerInfo(cursor.getString(0), cursor.getInt(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getInt(5) == 1 ? true : false, cursor.getInt(4));
				if (!list.contains(s)) {
					list.add(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("查询出错"+e.getMessage());
		} finally {
			db.close();
		}
		//若数据库没有，则从设置的默认服务器中取
		if (list.isEmpty()) {
			return getServerInfos(serverType);
		}
		return list;
		
	}

	/**
	 * 获取认证服务器的 IP 地址
	 * @return
	 */
	public static String getServerIp(int serverType){
		String ip;
		ServerInfo severInfo = ServerInfoMgr.getInstance().getServerInfo(serverType/*ProtocolConstant.SERVER_FW_AUTH*/);
		if (severInfo != null ) {
			ip = severInfo.getUrl();
		} else {
			severInfo = ServerInfoMgr.getInstance().getDefaultServerInfo(serverType/*ProtocolConstant.SERVER_FW_AUTH*/);
			ip = severInfo.getUrl();
		}

		return ip;
	}

	public int getServerInfoCount(int serverType){
		int count = 0;
		SQLiteDatabase db = null;
		try {
			db = getSQLiteOpenHelper().getReadableDatabase();
			Cursor cursor = db
					.rawQuery(ServerInfoSQLiteHelper.SQL_SELECT, new String[]{serverType + ""});
			count = cursor.getCount();
		} catch (Exception e) {
			
		} finally {
			if(db != null)
			db.close();
		}
		if (count == 0) {
			count = mapServers.get(serverType) == null ? 0 : mapServers.get(serverType).size();
		}
		return count;
	}

	/**
	 * 清除数据库中的服务器地址信息，当认证取到服务器数据时，才进行清理
	 */
	public final void clearAllServers() {
		SQLiteDatabase db = getWdb();
		try {
			db.execSQL(ServerInfoSQLiteHelper.SQL_DELETE_ALL);
		} finally {
			// db.endTransaction();
			db.close();
		}
	}

	/**
	 * 清除数据库中的指定服务器类型的服务器地址信息:
	 * @param serverType
	 */
	public final void clearServersByType(int serverType) {
		SQLiteDatabase db = getWdb();
		try {
			db.execSQL(ServerInfoSQLiteHelper.SQL_DELETE_BY_SERVER_TYPE, new Object[]{serverType});
		} finally {
			// db.endTransaction();
			db.close();
		}
	}

	/**
	 * 添加指定服务器地址信息到数据库中
	 * 
	 * @param s
	 */
	public final void insertServerInfo(ServerInfo s) {
		SQLiteDatabase db = getWdb();
		db.beginTransaction();
		try {
			Cursor cursor = db.rawQuery(ServerInfoSQLiteHelper.SQL_SELECT_SERVERID_MAX_VALUE, null);
			int maxId = 0;

			while (cursor.moveToNext()) {
				if(!TextUtils.isEmpty(cursor.getString(0))){
					maxId = Integer.parseInt(cursor.getString(0));
				}
			}
			db.execSQL(
					ServerInfoSQLiteHelper.SQL_INSERT,
					new Object[] { maxId + 1, s.getServerFlag(),
							s.getServerType(), s.getServerName(),
							s.getUrl(), s.getHttpsPort(),
							s.isKeepAlive() ? 1 : 0 });
			db.setTransactionSuccessful();
		} catch (Exception e){
			Logger.e("ServerInfoMgr", e.getMessage());
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	/**
	 * 添加服务器地址信息到数据库中
	 */
	public final void insertServerInfo() {
		if (mapServers.isEmpty()) {
			return;
		}
		SQLiteDatabase db = getWdb();
		db.beginTransaction();
		Iterator<Integer> iter = mapServers.keySet().iterator();
		int i = 0;
		try {
			while (iter.hasNext()) {
				int key = (Integer) iter.next();
				List<ServerInfo> lstServers = mapServers.get(key);
				for (ServerInfo s : lstServers) {
					i++;
					db.execSQL(
							ServerInfoSQLiteHelper.SQL_INSERT,
							new Object[] { i, s.getServerFlag(),
									s.getServerType(), s.getServerName(),
									s.getUrl(), s.getHttpsPort(),
									s.isKeepAlive() ? 1 : 0 });
				}
			}
			db.setTransactionSuccessful();
		}

		finally {
			db.endTransaction();
			db.close();
		}
	}
	
	/**
	 * 通过url 查询不同类型站点
	 * @param url
	 * @return
	 */
	public final List<ServerInfo> getServerInfosByUrl(String url){
		List<ServerInfo> list = new ArrayList<ServerInfo>();
		SQLiteDatabase db = getSQLiteOpenHelper().getReadableDatabase();
		try {
			Cursor cursor = db
					.rawQuery(ServerInfoSQLiteHelper.SQL_SELECT_BY_URL, new String[]{url});
			while (cursor.moveToNext()) {
				ServerInfo s = new ServerInfo(cursor.getString(0), cursor.getInt(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getInt(5) == 1 ? true : false, cursor.getInt(4));
				list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("查询出错"+e.getMessage());
		} finally {
			db.close();
		}
		return list;
	}
	
	public final void addFromDBToCache(){
		SQLiteDatabase db = getSQLiteOpenHelper().getReadableDatabase();
		try {
			Cursor cursor = db
					.rawQuery(ServerInfoSQLiteHelper.SQL_SELECT_ALL, null);
			if (cursor.getCount() > 0) {
				mapServers.clear();
			}
			while (cursor.moveToNext()) {
				ServerInfo s = new ServerInfo(cursor.getString(0), cursor.getInt(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getInt(5) == 1 ? true : false, cursor.getInt(4));
				List<ServerInfo> lstServerInfos = mapServers.get(s.getServerType());
				if (lstServerInfos == null) {
					lstServerInfos = new ArrayList<ServerInfo>();
					mapServers.put(s.getServerType(), lstServerInfos);
				}
				if (!lstServerInfos.contains(s)) {
					lstServerInfos.add(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("查询出错"+e.getMessage());
		} finally {
			db.close();
		}
	}

	/**
	 * 清楚数据库中的服务器地址信息，建议启动时及退出时做1次清理
	 * 
	 * @param context
	 */
	public final void clearAllServers(Context context) {
		SQLiteDatabase db = getWdb();
		try {
			db.execSQL(ServerInfoSQLiteHelper.SQL_DELETE_ALL);
		} finally {
			// db.endTransaction();
			db.close();
		}
	}

	private SQLiteOpenHelper getSQLiteOpenHelper() {
		if (sqliteOpenHelper == null)
			sqliteOpenHelper = new ServerInfoSQLiteHelper(
					OriginalContext.getContext());
		return sqliteOpenHelper;
	}

	/**
	 * 获取可写数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getWdb() {
		return getSQLiteOpenHelper().getWritableDatabase();
	}
	
	/**
	 * 删除数据库
	 * @param context
	 */
	public void deleteDB(Context context){
		context.deleteDatabase(ServerInfoSQLiteHelper.DATABASE_NAME);
	}
	
}
