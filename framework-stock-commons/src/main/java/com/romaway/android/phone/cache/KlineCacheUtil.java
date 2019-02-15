package com.romaway.android.phone.cache;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.romaway.android.phone.config.KConfigs;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.hq.HQKXProtocol;
import com.romaway.common.protocol.hq.HQKXZHProtocol;

/**
 * k线增量缓存
 * 
 * @author qinyn
 * 
 */
public class KlineCacheUtil
{
	private static KlineCacheUtil instance = new KlineCacheUtil();

	public static KlineCacheUtil getInstance()
	{
		return instance;
	}

	/** k线类型 日线 **/
	public static final int KLINE_TYPE_DATE = 0;
	/** k线类型 分钟线 **/
	public static final int KLINE_TYPE_MINUTE = 1;

	/**
	 * 判断k线类型为日线还是分钟线
	 * 
	 * @param klType
	 * @return KLINE_TYPE_DATE or KLINE_TYPE_MINUTE
	 */
	public int getKlineType(short klType)
	{
		int type = 0;
		switch (klType)
		{
			case ProtocolConstant.KX_5MIN:
			case ProtocolConstant.KX_15MIN:
			case ProtocolConstant.KX_30MIN:
			case ProtocolConstant.KX_60MIN:
				type = KLINE_TYPE_MINUTE;
				break;
			case ProtocolConstant.KX_DAY:
			case ProtocolConstant.KX_WEEK:
			case ProtocolConstant.KX_MONTH:
				type = KLINE_TYPE_DATE;
				break;
		}
		return type;
	}

	/** 数据拼接 */
	public HQKXProtocol splicingData(String stock_code, int market_id,
	        short kline_type, HQKXProtocol ptl)
	{
		if (!KConfigs.hasHQCache){
			return ptl;
		}
		KlineCacheDao dao = KlineCacheDao.getInstance();
		SQLiteDatabase db = dao.getRdb();
		Cursor cursor = dao.sel(db, stock_code, market_id, kline_type);
		try
		{
			int count_local = cursor.getCount();
			if (count_local == 0)
			{
				return ptl;
			} else
			{
				int[] dwTime_s_local = new int[count_local];
				int[] nYClose_s_local = new int[count_local];
				int[] nOpen_s_local = new int[count_local];
				int[] nZgcj_s_local = new int[count_local];
				int[] nZdcj_s_local = new int[count_local];
				int[] nClose_s_local = new int[count_local];
				int[] nZdf_s_local = new int[count_local];
				int[] nCjje_s_local = new int[count_local];
				int[] nCjss_s_local = new int[count_local];
				int[] nCcl_s_local = new int[count_local];
				int[] nHsl_s_local = new int[count_local];
				int[] nSyl_s_local = new int[count_local];
				int[] nMA1_s_local = new int[count_local];
				int[] nMA2_s_local = new int[count_local];
				int[] nMA3_s_local = new int[count_local];
				int[] nTech1_s_local = new int[count_local];
				int[] nTech2_s_local = new int[count_local];
				int[] nTech3_s_local = new int[count_local];
				int[] nHsj_s_local = new int[count_local];
				int[] nZd_s_local = new int[count_local];
				String[] wsXXgg_s_local = new String[count_local];
				byte[] bFlag_s_local = new byte[count_local];
				int[][] data_local = new int[][] { dwTime_s_local,
				        nYClose_s_local, nOpen_s_local, nZgcj_s_local,
				        nZdcj_s_local, nClose_s_local, nZdf_s_local,
				        nCjje_s_local, nCjss_s_local, nCcl_s_local,
				        nHsl_s_local, nSyl_s_local, nMA1_s_local, nMA2_s_local,
				        nMA3_s_local, nTech1_s_local, nTech2_s_local,
				        nTech3_s_local, nHsj_s_local, nZd_s_local, };
				int[][] data_server = new int[][] { ptl.resp_dwTime_s,
				        ptl.resp_nYClose_s, ptl.resp_nOpen_s, ptl.resp_nZgcj_s,
				        ptl.resp_nZdcj_s, ptl.resp_nClose_s, ptl.resp_nZdf_s,
				        ptl.resp_nCjje_s, ptl.resp_nCjss_s, ptl.resp_nCcl_s,
				        /*ptl.resp_nHsl_s, ptl.resp_nSyl_s,*/ ptl.resp_nMA1_s,
				        ptl.resp_nMA2_s, ptl.resp_nMA3_s, ptl.resp_nTech1_s,
				        ptl.resp_nTech2_s, ptl.resp_nTech3_s, /*ptl.resp_nHsj_s,*/
				        ptl.resp_nZd_s, };
				//String[] wsXXgg_s_server = ptl.resp_wsXxgg_s;
				//byte[] bFlag_s_server = ptl.resp_bFlag_s;
				// *********取出本地数据**********************************
				int i = 0;
				while (cursor.moveToNext())
				{
					for (int x = 0; x < data_local.length; x++)
					{
						data_local[x][i] = cursor.getInt(x + 5);
					}
					wsXXgg_s_local[i] = cursor.getString(19);
					bFlag_s_local[i] = (byte) cursor.getInt(20);
					i++;
				}
				// *********开始拼接***************************************
				if (ptl.resp_wKXDataCount <= 0)
				{
					for (int j = 0; j < data_local.length; j++)
					{
						data_server[j] = data_local[j];
					}
					//wsXXgg_s_server = wsXXgg_s_local;
					//bFlag_s_server = bFlag_s_local;
				} else
				{
					if (ptl.resp_dwTime_s[0] == dwTime_s_local[count_local - 1])
					{// 没有返回新数据情况,直接拿本地数据
						for (int j = 0; j < data_local.length; j++)
						{
							data_server[j] = data_local[j];
						}
						//wsXXgg_s_server = wsXXgg_s_local;
						//bFlag_s_server = bFlag_s_local;
					} else
					{// 拼接
						int splicingPos = -1;
						for(int ii = count_local-2; ii>=0; ii--){
							if (ptl.resp_dwTime_s[0] == dwTime_s_local[ii]){
								splicingPos = ii;
								break;
							}
						}
						
						for (int j = 0; j < data_local.length; j++)
						{
							data_server[j] = CacheUtils.splicingIntArray(
							        data_local[j], data_server[j],splicingPos);
						}
						/*wsXXgg_s_server = CacheUtils.splicingStringArray(
						        wsXXgg_s_local, wsXXgg_s_server,splicingPos);
						bFlag_s_server = CacheUtils.splicingByteArray(
						        bFlag_s_local, bFlag_s_server,splicingPos);*/
					}
				}
				// *******赋值****************************
				ptl.resp_dwTime_s = data_server[0];
				ptl.resp_nYClose_s = data_server[1];
				ptl.resp_nOpen_s = data_server[2];
				ptl.resp_nZgcj_s = data_server[3];
				ptl.resp_nZdcj_s = data_server[4];
				ptl.resp_nClose_s = data_server[5];
				ptl.resp_nZdf_s = data_server[6];
				ptl.resp_nCjje_s = data_server[7];
				ptl.resp_nCjss_s = data_server[8];
				ptl.resp_nCcl_s = data_server[9];
				//ptl.resp_nHsl_s = data_server[10];
				//ptl.resp_nSyl_s = data_server[11];
				ptl.resp_nMA1_s = data_server[12];
				ptl.resp_nMA2_s = data_server[13];
				ptl.resp_nMA3_s = data_server[14];
				ptl.resp_nTech1_s = data_server[15];
				ptl.resp_nTech2_s = data_server[16];
				ptl.resp_nTech3_s = data_server[17];
				//ptl.resp_nHsj_s = data_server[18];
				ptl.resp_nZd_s = data_server[19];
				//ptl.resp_wsXxgg_s = wsXXgg_s_server;
				//ptl.resp_bFlag_s = bFlag_s_server;
				ptl.resp_wKXDataCount = (short) ptl.resp_dwTime_s.length;
			}
			return ptl;
		} finally
		{
			cursor.close();
			db.close();
		}
	}

	/** 没有数据时，则返回0 */
	public int selLastDwtime(String stock_code, int market_id, short kline_type)
	{
		if (!KConfigs.hasHQCache){
			return 0;
		}
		KlineCacheDao dao = KlineCacheDao.getInstance();
		SQLiteDatabase db = dao.getRdb();

		Cursor cursor = dao.sel_dwtime(db, stock_code, market_id, kline_type);
		try
		{
			if (cursor == null || cursor.getCount() == 0)
			{
				return 0;
			}
			cursor.moveToLast();
			return cursor.getInt(0);

		}catch(Exception e){
			return 0;
		}
		finally
		{
			cursor.close();
			db.close();
		}
	}

	/** 加入数据库 */
	public void add(HQKXProtocol ptl, String stock_code, String stock_name,
	        int market_id, int type, short kline_type)
	{
		if (!KConfigs.hasHQCache){
			return;
		}
		KlineCacheDao dao = KlineCacheDao.getInstance();
		SQLiteDatabase db = dao.getWdb();
		try
		{
			db.beginTransaction();
			dao.add_all(db, stock_code, stock_name, market_id, type,
			        kline_type, ptl);
			db.setTransactionSuccessful();
		} finally
		{
			db.endTransaction();
			db.close();
		}
	}

	public void delStockData(String stock_code, int market_id, short kline_type)
	{
		KlineCacheDao dao = KlineCacheDao.getInstance();
		SQLiteDatabase db = dao.getWdb();
		try
		{
			dao.del_stock_data(db, stock_code, market_id, kline_type);

		} finally
		{
			db.close();
		}
	}

}
