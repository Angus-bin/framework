/**
 * 
 */
package com.romaway.android.phone.keyboardelf;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.romaway.android.phone.SharedPreferenceConstants;
import com.romaway.android.phone.netreq.NetMsgSend;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.R;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol2;
import com.romaway.common.protocol.service.HQServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.lang.StringUtils;

/**
 * 键盘精灵网络数据
 * 
 * @author duminghui
 * 
 */
public class KeyboardElfContorl
{
	/** 清空本地键盘精灵指令 */
	private static final String INIT_CODE_LIST = "initCodeList";
   
	/**
	 * 请求键盘精灵数据
	 * 
	 * @param wMarketID
	 * @param date
	 * @param listener
	 * @param msgFlag
	 */
	public static final void req(int wMarketID, INetReceiveListener listener,
	        String msgFlag)
	{
		int date = SharedPreferenceUtils.getPreference(
		        SharedPreferenceUtils.DATA_USER, String.format(
		                SharedPreferenceConstants.KEYBOARDELF_UPDATE_TIME,
		                wMarketID), 0);
		String uuid = SharedPreferenceUtils.getPreference(
				SharedPreferenceUtils.DATA_USER, SharedPreferenceConstants.UUID, "");
		NetMsg msg = HQServices.hq_newcodelist((int) date, wMarketID, listener,
		        EMsgLevel.normal, msgFlag, Res.getInteger(R.integer.keyboardelf_version), false, uuid);
		NetMsgSend.sendMsg(msg);
	}

	/**
	 * 添加协议内容到数据库内
	 * 
	 * @param activity
	 * @param ptl
	 */
	public static final void parseProtocol(Activity activity,
	        HQNewCodeListProtocol ptl)
	{
		KeyboardElfDBUtil dbutil = new KeyboardElfDBUtil(activity);
		SQLiteDatabase db = dbutil.getWdb();
		db.beginTransaction();
		int version = ptl.getCmdServerVersion();
		try
		{
			if(version >= 1 && ptl.resp_pszDelCode_s.length == 1
					&& INIT_CODE_LIST.equalsIgnoreCase(ptl.resp_pszDelCode_s[0])){
				// 仅当下发删除CodeList为1条且为指定删除指令时清空本地键盘精灵数据;
				dbutil.clearTableData();
			}else{
				dbutil.del(db, ptl.resp_pszDelCode_s,ptl.resp_del_wMarketID);
			}
			if (version == 2) {// 插入股票标识
				dbutil.add(db, ptl.resp_pszAddCode_s, ptl.resp_pszName_s,
				        ptl.resp_pszPYCode_s, ptl.resp_wMarketID_s,
				        ptl.resp_wCodeType_s, ptl.resp_pszMark_s);
			} else {
				dbutil.add(db, ptl.resp_pszAddCode_s, ptl.resp_pszName_s,
				        ptl.resp_pszPYCode_s, ptl.resp_wMarketID_s,
				        ptl.resp_wCodeType_s);
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			
		}
		finally
		{
			db.endTransaction();
			db.close();
		}
		//只有当要添加和删除的有数据时才改变本地存储的日期
		if (ptl.resp_nAddCodeNum + ptl.resp_nDelCodeNum > 0) {
			SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER,
			        String.format(
			                SharedPreferenceConstants.KEYBOARDELF_UPDATE_TIME,
			                ptl.req_wMarketID), ptl.resp_nDate);
		}
		if(!StringUtils.isEmpty(ptl.resp_sUuid)){
			SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER,SharedPreferenceConstants.UUID, ptl.resp_sUuid);
			SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER,
					String.format(
							SharedPreferenceConstants.KEYBOARDELF_UPDATE_TIME,
							ptl.req_wMarketID), 0);
		} else {
			SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER,SharedPreferenceConstants.UUID, "");
		}
	}

	/**
	 * 添加协议内容到数据库内
	 *
	 * @param activity
	 * @param ptl
	 */
	public static final void parseProtocol(Activity activity,
										   HQNewCodeListProtocol2 ptl)
	{
		KeyboardElfDBUtil dbutil = new KeyboardElfDBUtil(activity);
		SQLiteDatabase db = dbutil.getWdb();
		db.beginTransaction();
		int version = ptl.getCmdServerVersion();
		try
		{
			dbutil.clearTableData();
			dbutil.add(db, ptl.resp_pszAddCode_s, ptl.resp_pszName_s,
					ptl.resp_pszPYCode_s, ptl.resp_wMarketID_s,
					ptl.resp_wCodeType_s);
			db.setTransactionSuccessful();
		}catch(Exception e){

		}
		finally
		{
			db.endTransaction();
			db.close();
		}
	}

}
