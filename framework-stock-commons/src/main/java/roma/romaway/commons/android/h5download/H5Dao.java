package roma.romaway.commons.android.h5download;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class H5Dao {

	private static H5Dao dao = null;
	private Context context;

	private H5Dao(Context context) {
		this.context = context;
	}

	public static H5Dao getInstance(Context context) {
		if (dao == null) {
			dao = new H5Dao(context);
		}
		return dao;
	}

	private SQLiteDatabase getConnection() {
		SQLiteDatabase sqliteDatabase = null;
		try {
			sqliteDatabase = new H5DBHelper(context).getReadableDatabase();
		} catch (Exception e) {
		}
		return sqliteDatabase;
	}

	/**
	 * 查看数据库中是否有数据
	 */
	public synchronized boolean isHasInfors(String version) {
		SQLiteDatabase database = getConnection();
		int count = -1;
		Cursor cursor = null;
		try {
			String sql = "select count(*)  from download_info where version=?";
			cursor = database.rawQuery(sql, new String[] { version });
			if (cursor.moveToFirst()) {
				count = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}
		return count == 0;
	}

	/**
	 * 保存 下载的具体信息
	 */
	public synchronized void saveInfos(List<DownloadInfo> infos) {
		SQLiteDatabase database = getConnection();
		try {
			for (DownloadInfo info : infos) {
				String sql = "insert into download_info(thread_id,start_pos, end_pos,compelete_size,url,version,state,upzip_file) values (?,?,?,?,?,?,?,?)";
				Object[] bindArgs = { info.getThreadId(), info.getStartPos(),
						info.getEndPos(), info.getCompeleteSize(),
						info.getUrl(),info.getVersion(),info.getState(),info.getUpzip_file() };
				database.execSQL(sql, bindArgs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}

	/**
	 * 得到下载具体信息
	 */
	public synchronized List<DownloadInfo> getInfos(String version) {
		List<DownloadInfo> list = new ArrayList<DownloadInfo>();
		SQLiteDatabase database = getConnection();
		Cursor cursor = null;
		try {
			String sql = "select thread_id, start_pos, end_pos,compelete_size,url,version,state,upzip_file from download_info where version=?";
			cursor = database.rawQuery(sql, new String[] { version });
			while (cursor.moveToNext()) {
				DownloadInfo info = new DownloadInfo(cursor.getInt(0),
						cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
						cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7));
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}
		return list;
	}

	/**
	 * 更新数据库中的下载信息
	 */
	public synchronized void updataInfos( int compeleteSize,int state,
			String version,String upzip_file) {
		SQLiteDatabase database = getConnection();
		try {
			String sql = "update download_info set compelete_size=? , state=? , upzip_file=? where version=?";
			Object[] bindArgs = { compeleteSize,state, upzip_file,version };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}

	/**
	 * 下载完成后删除数据库中的数据
	 */
	public synchronized void delete(String version) {
		SQLiteDatabase database = getConnection();
		try {
			database.delete("download_info", "version=?", new String[] { version });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}
	
	/**
	 * 取到最新更新插入的一条数据
	 * 表没有数据，返回null
	 */
	public DownloadInfo getLastUpdateVersion(){
		
		SQLiteDatabase database = getConnection();
		Cursor cursor = null;
		DownloadInfo info = null;
		try {
		//	select * from sensor order by id desc limit 0,1;
			String sql = "select thread_id, start_pos, end_pos,compelete_size,url,version,state,upzip_file from download_info order by id desc limit 0,1";
			cursor = database.rawQuery(sql, null);
			if(cursor.moveToNext()) {
				 info = new DownloadInfo(cursor.getInt(0),
						cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
						cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
		return info;
	}
}