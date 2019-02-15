package com.romaway.common.protocol.service;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.hq.HQBKProtocol;
import com.romaway.common.protocol.hq.HQCXGCXProtocol;
import com.romaway.common.protocol.hq.HQDATAProtocol;
import com.romaway.common.protocol.hq.HQFBProtocol;
import com.romaway.common.protocol.hq.HQFSProtocol;
import com.romaway.common.protocol.hq.HQFSZHProtocol;
import com.romaway.common.protocol.hq.HQGGTEDProtocol;
import com.romaway.common.protocol.hq.HQGgqqProtocol;
import com.romaway.common.protocol.hq.HQGgqqTDataProtocol;
import com.romaway.common.protocol.hq.HQKXProtocol;
import com.romaway.common.protocol.hq.HQKXZHProtocol;
import com.romaway.common.protocol.hq.HQLINKProtocol;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol;
import com.romaway.common.protocol.hq.HQNewStockProtocol;
import com.romaway.common.protocol.hq.HQPXProtocol;
import com.romaway.common.protocol.hq.HQPYProtocol;
import com.romaway.common.protocol.hq.HQQHFBProtocol;
import com.romaway.common.protocol.hq.HQQHFSZHProtocol;
import com.romaway.common.protocol.hq.HQQHKXZHProtocol;
import com.romaway.common.protocol.hq.HQQHPXProtocol;
import com.romaway.common.protocol.hq.HQZXGTBAddProtocol;
import com.romaway.common.protocol.hq.HQZXGTBBindProtocol;
import com.romaway.common.protocol.hq.HQZXGTBDelProtocol;
import com.romaway.common.protocol.hq.HQZXGTBSelectBindProtocol;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocol;
import com.romaway.common.protocol.hq.HQZXGTBUploadProtocol;
import com.romaway.common.protocol.hq.HQZXProtocol;
import com.romaway.common.protocol.hq.HQZZZQProtocol;
import com.romaway.common.protocol.hq.zxjt.HQZXGCloudSysncDownloadProtocol;
import com.romaway.common.protocol.hq.zxjt.HQZXGCloudSysncUploadProtocol;
import com.romaway.common.protocol.hq.zxjt.Portfolio;
import com.romaway.common.protocol.wo.WoFeedbackAddProtocol;
import com.romaway.common.protocol.wo.WoFeedbackSelectProtocol;
import com.romaway.common.protocol.wo.WoPersonalCenterSelectProtocol;
import com.romaway.common.protocol.yj.InfoCenterCXProtocol;
import com.romaway.common.protocol.yj.InfoContentCXProtocol;
import com.romaway.common.protocol.yj.InfoContentClearProtocol;
import com.romaway.common.protocol.yj.KDSInfoCenterCXProtocol;
import com.romaway.common.protocol.yj.KDSInfoContentCXProtocol;
import com.romaway.common.protocol.yj.YuJingCXProtocol;
import com.romaway.common.protocol.yj.YuJingSetProtocol;
import com.romaway.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HQServices
{

	/**
	 * 分时数据请求
	 * 
	 * @param pszCode
	 *            股票代码
	 * @param nDate
	 *            日期0代表最近一天，其他日期彩用yyyyMMdd格式来描述
	 * @param bFreq
	 *            采样间隔，最小采样单位的倍数(目前最小采样单位分钟)
	 * @param nTime
	 *            分时数据起始时间,0：表示全天的数据，其它时间采用HHmm的格式描述
	 * @param wMarketID
	 *            交易所代码
	 * @param listener
	 * @param msgFlag
	 * @param cmdVersion
	 * @return
	 */
	public static ANetMsg hq_fs(String pszCode, int nDate, int bFreq,
	        int nTime, int wMarketID, INetReceiveListener listener,
	        EMsgLevel level, String msgFlag, int cmdVersion, boolean reSend)
	{

		HQFSProtocol ptl = new HQFSProtocol(msgFlag, cmdVersion);
		ptl.req_pszCode = pszCode;
		ptl.req_nDate = nDate;
		ptl.req_bFreq = (byte) bFreq;
		ptl.req_nTime = nTime;
		ptl.req_wMarketID = (short) wMarketID;
		
		if(wMarketID == ProtocolConstant.SE_HGT){
			ptl.setnMFuncNo(ProtocolConstant.MF_HGT);
		}
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}

	/**
	 * 分笔数据请求
	 */
	public static ANetMsg hq_fb(String pszCode, int nType, int nNum,
	        int nTimeS, int nTimeE, int wMarketID,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{
		HQFBProtocol ptl = new HQFBProtocol(msgFlag, cmdVersion);

		ptl.req_pszCode = pszCode;
		ptl.req_nType = (byte) nType;
		ptl.req_nNum = nNum;
		ptl.req_nTimeS = nTimeS;
		ptl.req_nTimeE = nTimeE;
		ptl.req_wMarketID = (short) wMarketID;

		if(wMarketID == ProtocolConstant.SE_HGT){
			ptl.setnMFuncNo(ProtocolConstant.MF_HGT);
		}
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}

	/**
	 * 期貨分笔数据请求
	 */
	public static ANetMsg hq_qhfb(String pszCode, byte nType, int nNum,
	        int nTimeS, int nTimeE, int wMarketID,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{
		HQQHFBProtocol ptl = new HQQHFBProtocol(msgFlag, cmdVersion);

		ptl.req_pszCode = pszCode;
		ptl.req_nType = nType;
		ptl.req_nNum = nNum;
		ptl.req_nTimeS = nTimeS;
		ptl.req_nTimeE = nTimeE;
		ptl.req_wMarketID = (short) wMarketID;

		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}

	/**
	 * 排序行情请求
	 *
	 * @param wMarketID
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param pszBKCode
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @return
	 */
	public static NetMsg hq_px(int wMarketID, int wType, int bSort,
							   int bDirect, int wFrom, int wCount, String pszBKCode,
							   INetReceiveListener listener, EMsgLevel level, String msgFlag,
							   int cmdVersion, boolean reSend, boolean isAutoRefresh) {
		return hq_px(-1, wMarketID, wType, bSort, bDirect, wFrom, wCount, pszBKCode,
				listener, level, msgFlag, cmdVersion, reSend, isAutoRefresh);
	}

	/**
	 * 排序行情请求
	 * @return
	 */
	public static NetMsg hq_px(long wFieldsBitMap, int wMarketID, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, String pszBKCode,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend, boolean isAutoRefresh)
	{
		HQPXProtocol ptl = new HQPXProtocol(msgFlag, cmdVersion+1);
		ptl.req_fieldsBitMap = wFieldsBitMap;
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = (short) wMarketID;
		ptl.req_wType = (short) wType;
		ptl.req_autoRefreshArray = null;
		ptl.req_bSort = bSort;
		ptl.req_bDirect = bDirect;
		ptl.req_wFrom = (short) wFrom;
		ptl.req_wCount = (short) wCount;
		ptl.req_pszBKCode = pszBKCode;
		
//	    ServerInfo serverInfo = ServerInfoMgr.getInstance()
//		        .getDefaultServerInfo(ProtocolConstant.SERVER_FW_NEWS);
//		//采用临时缓存，防止下一个请求使用相同地址
//		ServerInfo temp=null;
//		if (serverInfo != null) {
//			temp = new ServerInfo(serverInfo.getServerFlag(), ProtocolConstant.SERVER_FW_NEWS, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
//			if(temp!=null){
//				temp.setSubFunUrl(ptl.subFunUrl);
//				temp.setUrl("http://192.168.230.214:21800");//http://192.168.230.214:21800/api/quote/pb_stockRank
//			}
//		}
//		
//		NetMsg msg = new NetMsg(msgFlag, level, ptl, ConnInfo.newConnectionInfoSockePost(temp), reSend,
//		        listener);
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}

	/**
	 * 排序行情请求针对市场首页的涨跌幅，换手率等6项
	 *
	 * @param wMarketID
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param pszBKCode
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @return
	 */
	public static NetMsg hq_px_for_first_interface(int wMarketID, int wType, int[] autoRefreshArray, int[] bSort,
												   int[] bDirect, int wFrom, int wCount, String pszBKCode,
												   INetReceiveListener listener, EMsgLevel level, String msgFlag,
												   int cmdVersion, boolean reSend, boolean isAutoRefresh) {
		return hq_px_for_first_interface(-1, wMarketID, wType, autoRefreshArray, bSort, bDirect,
				wFrom, wCount, pszBKCode, listener, level, msgFlag, cmdVersion, reSend, isAutoRefresh);
	}

	/**
	 * 排序行情请求针对市场首页的涨跌幅，换手率等6项
	 * @return
	 */
	public static NetMsg hq_px_for_first_interface(long wFieldsBitMap, int wMarketID, int wType, int[] autoRefreshArray,int[] bSort,
			int[] bDirect, int wFrom, int wCount, String pszBKCode,
			INetReceiveListener listener, EMsgLevel level, String msgFlag,
			int cmdVersion, boolean reSend, boolean isAutoRefresh)
	{
		HQPXProtocol ptl = new HQPXProtocol(msgFlag, cmdVersion+1);
		ptl.req_fieldsBitMap = wFieldsBitMap;
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = (short) wMarketID;
		ptl.req_wType = (short) wType;
		ptl.req_autoRefreshArray = autoRefreshArray;
		ptl.req_bSort_new = bSort;
		ptl.req_bDirect_new = bDirect;
		ptl.req_wFrom = (short) wFrom;
		ptl.req_wCount = (short) wCount;
		ptl.req_pszBKCode = pszBKCode;
		
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		
		return msg;
	}

	public static NetMsg hq_gg_px(int wMarketID, int wType, int bSort,
								  int bDirect, int wFrom, int wCount, String pszBKCode,
								  INetReceiveListener listener, EMsgLevel level, String msgFlag,
								  int cmdVersion, boolean reSend, boolean isAutoRefresh) {
		return hq_gg_px(-1, wMarketID, wType, bSort, bDirect, wFrom, wCount,
				pszBKCode, listener, level, msgFlag, cmdVersion, reSend, isAutoRefresh);
	}

	public static NetMsg hq_gg_px(long wFieldsBitMap, int wMarketID, int wType, int bSort,
								  int bDirect, int wFrom, int wCount, String pszBKCode,
								  INetReceiveListener listener, EMsgLevel level, String msgFlag,
								  int cmdVersion, boolean reSend, boolean isAutoRefresh)
	{
		HQPXProtocol ptl = new HQPXProtocol(msgFlag, cmdVersion);
		ptl.req_fieldsBitMap = wFieldsBitMap;
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = (short) wMarketID;
		ptl.req_wType = (short) wType;
		ptl.req_bSort = (byte) bSort;
		ptl.req_bDirect = (byte) bDirect;
		ptl.req_wFrom = (short) wFrom;
		ptl.req_wCount = (short) wCount;
		ptl.req_pszBKCode = pszBKCode;

		ConnInfo connInfo = ConnInfo
				.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}


	/**
	 * 期货排序行情请求
	 * 
	 * @return
	 */
	public static NetMsg hq_qhpx(int wMarketID, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, INetReceiveListener listener,
	        EMsgLevel level, String msgFlag, int cmdVersion, boolean reSend,
	        boolean isAutoRefresh)
	{
		HQQHPXProtocol ptl = new HQQHPXProtocol(msgFlag, cmdVersion);
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = (short) wMarketID;
		ptl.req_wType = (short) wType;
		ptl.req_bSort = (byte) bSort;
		ptl.req_bDirect = (byte) bDirect;
		ptl.req_wFrom = (short) wFrom;
		ptl.req_wCount = (short) wCount;

		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}

	/**
	 * 自选行情选择
	 * 
	 * @param wCount
	 * @param pszCodes
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param marketList
	 * @param listener
	 *            接收者
	 * @param level
	 *            消息等级
	 * @param msgFlag
	 *            消息标识
	 * @param cmdVersion
	 *            业务协议版本号
	 * @param reSend
	 *            出错时是否重发
	 * @param isAutoRefresh
	 *            是否是自动刷新请求
	 * @return
	 */
	public static NetMsg hq_zx(short wCount, String pszCodes, byte bSort,
	        byte bDirect, short wFrom, String marketList,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend, boolean isAutoRefresh)
	{
		HQZXProtocol ptl = new HQZXProtocol(msgFlag, cmdVersion);

		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wCount = wCount;
		ptl.req_pszCodes = pszCodes;
		ptl.req_bSort = bSort;
		ptl.req_bDirect = bDirect;
		ptl.req_wFrom = wFrom;
		ptl.req_marketList = "";
		if (!StringUtils.isEmpty(marketList))
		{
			if (!marketList.equals("-1"))
			{
				ptl.req_marketList = marketList;
			}
		}
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}
	
	/**
	 * 港股通自选行情排序
	 * 
	 * @param wCount
	 * @param pszCodes
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param marketList
	 * @param listener
	 *            接收者
	 * @param level
	 *            消息等级
	 * @param msgFlag
	 *            消息标识
	 * @param cmdVersion
	 *            业务协议版本号
	 * @param reSend
	 *            出错时是否重发
	 * @param isAutoRefresh
	 *            是否是自动刷新请求
	 * @return
	 */
	public static NetMsg hq_zx_hgt(short wCount, String pszCodes, byte bSort,
	        byte bDirect, short wFrom, String marketList,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend, boolean isAutoRefresh)
	{
		HQZXProtocol ptl = new HQZXProtocol(msgFlag, cmdVersion);

		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wCount = wCount;
		ptl.req_pszCodes = pszCodes;
		ptl.req_bSort = bSort;
		ptl.req_bDirect = bDirect;
		ptl.req_wFrom = wFrom;
		ptl.req_marketList = "";
		// log.e("::::hq_zx----", !marketList.equals("-1") + "");
		if (!StringUtils.isEmpty(marketList))
		{
			if (!marketList.equals("-1"))
			{
				ptl.req_marketList = marketList;
			}
		}
		ptl.setnMFuncNo(ProtocolConstant.MF_HGT);
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}

	/**
	 * 自选行情选择
	 * 
	 * @param wCount
	 * @param pszCodes
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param marketList
	 * @param listener
	 *            接收者
	 * @param level
	 *            消息等级
	 * @param msgFlag
	 *            消息标识
	 * @param cmdVersion
	 *            业务协议版本号
	 * @param reSend
	 *            出错时是否重发
	 * @return
	 */
	public static NetMsg hq_zx(short wCount, String pszCodes, byte bSort,
	        byte bDirect, short wFrom, String marketList,long req_bitmap,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{
		HQZXProtocol ptl = new HQZXProtocol(msgFlag, cmdVersion);
		ptl.req_wCount = wCount;
		ptl.req_pszCodes = pszCodes;
		ptl.req_bSort = bSort;
		ptl.req_bDirect = bDirect;
		ptl.req_wFrom = wFrom;
		ptl.req_bitmap = req_bitmap;
		ptl.req_marketList = "";
		if (!StringUtils.isEmpty(marketList))
		{
			if (!marketList.equals("-1"))
			{
				ptl.req_marketList = marketList;
			}
		}
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	/**
	 * 行情分时综合
	 * 
	 * @param req_wMarketID
	 * @param req_sPszCode
	 * @param req_dwFSDate
	 * @param req_wFSFreq
	 * @param req_dwFSTime
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_fszh(int req_wMarketID, String req_sPszCode,
	        int req_dwFSDate, short req_wFSFreq, int req_dwFSTime,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{

		HQFSZHProtocol ptl = new HQFSZHProtocol(msgFlag, cmdVersion);
		ptl.req_wMarketID = (short) req_wMarketID;
		ptl.req_sPszCode = req_sPszCode;
		ptl.req_dwFSDate = req_dwFSDate;
		ptl.req_wFSFreq = req_wFSFreq;
		ptl.req_dwFSTime = req_dwFSTime;
		
		if(req_wMarketID == ProtocolConstant.SE_HGT){
			ptl.setnMFuncNo(ProtocolConstant.MF_HGT);
		}
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}
	
	/**
	 * 行情分时综合
	 * 
	 * @param req_wMarketID
	 * @param req_sPszCode
	 * @param req_dwFSDate
	 * @param req_wFSFreq
	 * @param req_dwFSTime
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_fszh(int req_wMarketID, String req_sPszCode,
	        int req_dwFSDate, short req_wFSFreq, int req_dwFSTime, int[] req_fieldsRes,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean isAutoRefresh, boolean reSend)
	{

		HQFSZHProtocol ptl = new HQFSZHProtocol(msgFlag, cmdVersion);
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = (short) req_wMarketID;
		ptl.req_sPszCode = req_sPszCode;
		ptl.req_dwFSDate = req_dwFSDate;
		ptl.req_wFSFreq = req_wFSFreq;
		ptl.req_dwFSTime = req_dwFSTime;
		ptl.req_fieldsRes = req_fieldsRes;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}
	/**
	 * 期货行情综合
	 * 
	 * @param req_wMarketID
	 * @param req_sPszCode
	 * @param req_dwFSDate
	 * @param req_wFSFreq
	 * @param req_dwFSTime
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static NetMsg hq_qhfszh(int req_wMarketID, String req_sPszCode,
	        int req_dwFSDate, short req_wFSFreq, int req_dwFSTime,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{

		HQQHFSZHProtocol ptl = new HQQHFSZHProtocol(msgFlag, cmdVersion);
		ptl.req_wMarketID = (short) req_wMarketID;
		ptl.req_sPszCode = req_sPszCode;
		ptl.req_dwFSDate = req_dwFSDate;
		ptl.req_wFSFreq = req_wFSFreq;
		ptl.req_dwFSTime = req_dwFSTime;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}
	/**
	 * 期货行情综合
	 * 
	 * @param req_wMarketID
	 * @param req_sPszCode
	 * @param req_dwFSDate
	 * @param req_wFSFreq
	 * @param req_dwFSTime
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static NetMsg hq_qhfszh(int req_wMarketID, String req_sPszCode,
	        int req_dwFSDate, short req_wFSFreq, int req_dwFSTime,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean isAutoRefresh, boolean reSend)
	{

		HQQHFSZHProtocol ptl = new HQQHFSZHProtocol(msgFlag, cmdVersion);
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = (short) req_wMarketID;
		ptl.req_sPszCode = req_sPszCode;
		ptl.req_dwFSDate = req_dwFSDate;
		ptl.req_wFSFreq = req_wFSFreq;
		ptl.req_dwFSTime = req_dwFSTime;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}
	/**
	 * 行情 k线综合
	 * 
	 * @param wMarketID
	 * @param sPszCode
	 * @param dwKXDate
	 * @param wKXType
	 * @param wKXCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_kxzh(short wMarketID, String sPszCode,
	        int dwKXDate, short wKXType, short wKXCount, int dwKXTime,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean isAutoRefresh, boolean reSend)
	{

		HQKXZHProtocol ptl = new HQKXZHProtocol(msgFlag, cmdVersion);
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = wMarketID;
		ptl.req_sPszCode = sPszCode;
		ptl.req_dwKXDate = dwKXDate;
		ptl.req_wKXType = wKXType;
		ptl.req_wKXCount = wKXCount;
		ptl.req_dwKXTime = dwKXTime;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}
	/**
	 * 行情 k线综合
	 *
	 * @param wMarketID
	 * @param sPszCode
	 * @param dwKXDate
	 * @param wKXType
	 * @param wKXCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_kxzh(short wMarketID, String sPszCode,
	        int dwKXDate, short wKXType, short wKXCount, int dwKXTime,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{

		HQKXZHProtocol ptl = new HQKXZHProtocol(msgFlag, cmdVersion);
		ptl.req_wMarketID = wMarketID;
		ptl.req_sPszCode = sPszCode;
		ptl.req_dwKXDate = dwKXDate;
		ptl.req_wKXType = wKXType;
		ptl.req_wKXCount = wKXCount;
		ptl.req_dwKXTime = dwKXTime;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}

	/**
	 * 行情 期货k线综合
	 * 
	 * @param wMarketID
	 * @param sPszCode
	 * @param dwKXDate
	 * @param wKXType
	 * @param wKXCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_qhkxzh(short wMarketID, String sPszCode,
	        int dwKXDate, short wKXType, short wKXCount,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{

		HQQHKXZHProtocol ptl = new HQQHKXZHProtocol(msgFlag, cmdVersion);
		ptl.req_wMarketID = wMarketID;
		ptl.req_sPszCode = sPszCode;
		ptl.req_dwKXDate = dwKXDate;
		ptl.req_wKXType = wKXType;
		ptl.req_wKXCount = wKXCount;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}
	/**
	 * 行情 期货k线综合
	 * 
	 * @param wMarketID
	 * @param sPszCode
	 * @param dwKXDate
	 * @param wKXType
	 * @param wKXCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_qhkxzh(short wMarketID, String sPszCode,
	        int dwKXDate, short wKXType, short wKXCount,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean isAutoRefresh, boolean reSend)
	{

		HQQHKXZHProtocol ptl = new HQQHKXZHProtocol(msgFlag, cmdVersion);
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = wMarketID;
		ptl.req_sPszCode = sPszCode;
		ptl.req_dwKXDate = dwKXDate;
		ptl.req_wKXType = wKXType;
		ptl.req_wKXCount = wKXCount;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}
	/**
	 * 行情 港股k线综合
	 * 
	 * @param wMarketID
	 * @param sPszCode
	 * @param dwKXDate
	 * @param wKXType
	 * @param wKXCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_ggkxzh(short wMarketID, String sPszCode,
	        int dwKXDate, short wKXType, short wKXCount,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{

		HQKXZHProtocol ptl = new HQKXZHProtocol(msgFlag, cmdVersion);
		ptl.req_wMarketID = wMarketID;
		ptl.req_sPszCode = sPszCode;
		ptl.req_dwKXDate = dwKXDate;
		ptl.req_wKXType = wKXType;
		ptl.req_wKXCount = wKXCount;
		//20141114 cjp 港股通股票采取独立主功能号
		if(wMarketID == ProtocolConstant.SE_HGT){
			ptl.setnMFuncNo(ProtocolConstant.MF_HGT);
		}
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}
	/**
	 * 行情 港股k线综合
	 * 
	 * @param wMarketID
	 * @param sPszCode
	 * @param dwKXDate
	 * @param wKXType
	 * @param wKXCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_ggkxzh(short wMarketID, String sPszCode,
	        int dwKXDate, short wKXType, short wKXCount,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean isAutoRefresh, boolean reSend)
	{

		HQKXZHProtocol ptl = new HQKXZHProtocol(msgFlag, cmdVersion);
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = wMarketID;
		ptl.req_sPszCode = sPszCode;
		ptl.req_dwKXDate = dwKXDate;
		ptl.req_wKXType = wKXType;
		ptl.req_wKXCount = wKXCount;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}
	
	/**
	 * 行情 港股通k线综合
	 * 
	 * @param wMarketID
	 * @param sPszCode
	 * @param dwKXDate
	 * @param wKXType
	 * @param wKXCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_ggtkxzh(short wMarketID, String sPszCode,
	        int dwKXDate, short wKXType, short wKXCount,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{

		HQKXZHProtocol ptl = new HQKXZHProtocol(msgFlag, cmdVersion);
		ptl.req_wMarketID = wMarketID;
		ptl.req_sPszCode = sPszCode;
		ptl.req_dwKXDate = dwKXDate;
		ptl.req_wKXType = wKXType;
		ptl.req_wKXCount = wKXCount;
		//20141114 cjp 港股通股票采取独立主功能号
		if(wMarketID == ProtocolConstant.SE_HGT){
			ptl.setnMFuncNo(ProtocolConstant.MF_HGT);
		}
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend,
		        listener);

		return msg;
	}
	
	/**
	 * 板块行情分析
	 * 
	 * @param wMarketID
	 *            交易所代码
	 * @param wType
	 *            商品类型
	 * @param bSort
	 *            排序方式
	 * @param bDirect
	 *            正向/逆向
	 * @param wFrom
	 *            开始序号
	 * @param wCount
	 *            每组最大返回的股票数据量
	 * @param pszBKCode
	 *            板块代码
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @param isAutoRefresh
	 *            是否是自动刷新请求
	 * @return
	 */
	public static NetMsg hq_bk(short wMarketID, short wType, byte bSort,
	        int bDirect, short wFrom, short wCount, String pszBKCode,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend, boolean isAutoRefresh)
	{
		HQBKProtocol bkhqpt = new HQBKProtocol(msgFlag, cmdVersion);
		bkhqpt.setAutoRefresh(isAutoRefresh);
		bkhqpt.req_wMarketID = wMarketID;
		bkhqpt.req_wType = wType;
		bkhqpt.req_bSort = bSort;
		bkhqpt.req_bDirect = bDirect;
		bkhqpt.req_wFrom = wFrom;
		bkhqpt.req_wCount = wCount;
		bkhqpt.req_pszBKCode = pszBKCode;
        
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, bkhqpt.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, bkhqpt, connInfo, reSend, listener);

		return msg;
	}

	/**
	 * 获取支持的行情类型数据
	 * 
	 * @param wMarketID
	 *            交易所代码,区分港股、期货、沪深A股
	 * @param wType
	 *            请求的数据类型
	 * @param bAll
	 *            判断是否获取指定市场所有支持的排序类型、板块分类、市场分类
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static NetMsg hq_data(short wMarketID, short wType, byte bAll,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{
		HQDATAProtocol dhqpt = new HQDATAProtocol(msgFlag, cmdVersion);
		dhqpt.req_wMarketID = wMarketID;
		dhqpt.req_wType = wType;
		dhqpt.req_bAll = bAll;

		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		NetMsg msg = new NetMsg(msgFlag, level, dhqpt, connInfo, reSend,
		        listener);

		return msg;
	}

	/**
	 * K线带指标请求协议
	 * 
	 * @param pszCode
	 *            代码，字符串最后为0
	 * @param nDate
	 *            开始日期nDate = 0 代表当天
	 * @param wkxType
	 *            K线类型
	 * @param wCount
	 *            K线个数
	 * @param wMarketID
	 *            交易所代码
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_kx(String pszCode, int nDate, short wkxType,
	        short wCount, short wMarketID, int nTime, 
	        int FQType, int[] fieldsRes, INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{
		HQKXProtocol kxhqpt = new HQKXProtocol(msgFlag, cmdVersion);
		kxhqpt.req_pszCode = pszCode;
		kxhqpt.req_nDate = nDate;
		kxhqpt.req_wkxType = wkxType;
		kxhqpt.req_wCount = wCount;
		kxhqpt.req_wMarketID = wMarketID;
		kxhqpt.req_nTime = nTime;
		kxhqpt.req_wFQType = FQType;
		kxhqpt.req_cmdVersion = cmdVersion;
		kxhqpt.req_fieldsRes = fieldsRes;
		
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, kxhqpt.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, kxhqpt, connInfo, reSend, listener);


		return msg;
	}

	/**
	 * 关联股票行情请求
	 * 
	 * @param pszCode
	 *            股票代码
	 * @param marketCode
	 *            市场代码
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_link(String pszCode, String marketCode,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend)
	{
		HQLINKProtocol linkhqpt = new HQLINKProtocol(msgFlag, cmdVersion);
		linkhqpt.req_pszCode = pszCode;
		linkhqpt.req_marketCode = marketCode;

		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, linkhqpt, connInfo, reSend,
		        listener);

		return msg;
	}

	/**
	 * 新键盘精灵
	 * 
	 * @param nDate
	 *            版本更新日期
	 * @param wMarketID
	 *            交易所代码
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static NetMsg hq_newcodelist(int nDate, int wMarketID,
	        INetReceiveListener listener, EMsgLevel level, String msgFlag,
	        int cmdVersion, boolean reSend, String uuid)
	{
		HQNewCodeListProtocol ncodehqpt = new HQNewCodeListProtocol(msgFlag,
		        cmdVersion);
		ncodehqpt.req_nDate = nDate;
		ncodehqpt.req_wMarketID = (short) wMarketID;
		ncodehqpt.req_sUuid = uuid;
		if(wMarketID == ProtocolConstant.SE_HGT){
			ncodehqpt.setnMFuncNo(ProtocolConstant.MF_HGT);
		}
		
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ncodehqpt.subFunUrl);
		NetMsg msg = new NetMsg(msgFlag, level, ncodehqpt, connInfo, reSend, listener);

		return msg;
	}

	/**
	 * 股票匹配协议
	 * 
	 * @param wType
	 *            类型
	 * @param pszPattern
	 *            查询串
	 * @param wMarketID
	 *            交易所代码
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 * @param reSend
	 * @return
	 */
	public static ANetMsg hq_py(short wType, String pszPattern,
	        short wMarketID, INetReceiveListener listener, EMsgLevel level,
	        String msgFlag, int cmdVersion, boolean reSend)
	{
		HQPYProtocol pyhqpt = new HQPYProtocol(msgFlag, cmdVersion);
		pyhqpt.req_wType = wType;
		pyhqpt.req_pszPattern = pszPattern;
		pyhqpt.req_wMarketID = wMarketID;
		ConnInfo connInfo = ConnInfo
		        .newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		ANetMsg msg = new NetMsg(msgFlag, level, pyhqpt, connInfo, reSend,
		        listener);

		return msg;
	}

	/**
	 * 中证债券行情请求
	 * 
	 * @param wMarketID
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param cmdVersion
	 */
	public static NetMsg hq_zzzq(int wMarketID, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, INetReceiveListener listener,
	        EMsgLevel level, String msgFlag, int cmdVersion, boolean reSend,
	        boolean isAutoRefresh)
	{
		HQZZZQProtocol ptl = new HQZZZQProtocol(msgFlag, cmdVersion);
		ptl.setAutoRefresh(isAutoRefresh);
		ptl.req_wMarketID = (short) wMarketID;
		ptl.req_wType = (short) wType;
		ptl.req_bSort = (byte) bSort;
		ptl.req_bDirect = (byte) bDirect;
		ptl.req_wFrom = (short) wFrom;
		ptl.req_wCount = (short) wCount;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

		return msg;
	}
	
	public static NetMsg hq_zxgtb_select(/*String keyType,String keyValue,*/
			String group, String appid, INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		HQZXGTBSelectProtocol ptl = new HQZXGTBSelectProtocol(msgFlag);
//		ptl.req_keyType = keyType;
//		ptl.req_keyValue = keyValue;
		ptl.req_group = group;
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg hq_zxgtb_upload(/*String keyType,String keyValue,*/String favors,
			String group, String appid, INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		HQZXGTBUploadProtocol ptl = new HQZXGTBUploadProtocol(msgFlag);
//		ptl.req_keyType = keyType;
//		ptl.req_keyValue = keyValue;
		ptl.req_favors = favors;
		ptl.req_group = group;
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}

	public static NetMsg hq_zxgtb_add(/*String keyType,String keyValue,*/String favors,
			String group, String appid, INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		HQZXGTBAddProtocol ptl = new HQZXGTBAddProtocol(msgFlag);
//		ptl.req_keyType = keyType;
//		ptl.req_keyValue = keyValue;
		ptl.req_favors = favors;
		ptl.req_group = group;
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg hq_zxgtb_del(/*String keyType,String keyValue,*/String favors,String group, String appid, 
			INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		HQZXGTBDelProtocol ptl = new HQZXGTBDelProtocol(msgFlag);
//		ptl.req_keyType = keyType;
//		ptl.req_keyValue = keyValue;
		ptl.req_favors = favors;
		ptl.req_group = group;
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg hq_zxgtb_bind(String deviceId,String bacc, String appid, 
			INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		HQZXGTBBindProtocol ptl = new HQZXGTBBindProtocol(msgFlag);
		ptl.req_deviceId = deviceId;
		ptl.req_bacc = bacc;
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg hq_zxgtb_select_bind(String deviceId, String appid, 
			INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		HQZXGTBSelectBindProtocol ptl = new HQZXGTBSelectBindProtocol(msgFlag);
		ptl.req_deviceId = deviceId;
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg wo_feedback_select(String deviceId, String appid, 
			INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		WoFeedbackSelectProtocol ptl = new WoFeedbackSelectProtocol(msgFlag);
		ptl.req_deviceId = deviceId;
		ptl.req_appId = appid;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg wo_feedback_add(String deviceId, String appid, 
			String feedback, String phoneModel, String osType, String osVersion,
			String appType, String appVersion, INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		WoFeedbackAddProtocol ptl = new WoFeedbackAddProtocol(msgFlag);
//		ptl.req_deviceId = deviceId;
//		ptl.req_appId = appid;
//		ptl.req_feedback = feedback;
//		ptl.req_phoneModel = phoneModel;
//		ptl.req_osType = osType;
//		ptl.req_osVersion = osVersion;
//		ptl.req_appType = appType;
//		ptl.req_appVersion = appVersion;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}

	/**
	 * 用户反馈添加接口(建投):
	 * 新增 手机号/phoneNumber 及 QQ号/qqNumber;
	 */
	public static NetMsg wo_feedback_add(String deviceId, String appid,
							 String feedback, String phoneModel, String osType, String osVersion,
							 String appType, String appVersion, String phoneNumber, String qqNumber,
							 INetReceiveListener listener,EMsgLevel level, String msgFlag, boolean reSend){
		WoFeedbackAddProtocol ptl = new WoFeedbackAddProtocol(msgFlag);
//		ptl.req_deviceId = deviceId;
//		ptl.req_appId = appid;
//		ptl.req_feedback = feedback;
//		ptl.req_phoneModel = phoneModel;
//		ptl.req_osType = osType;
//		ptl.req_osVersion = osVersion;
//		ptl.req_appType = appType;
//		ptl.req_appVersion = appVersion;
//		ptl.req_phoneNumber = phoneNumber;
//		ptl.req_qqNumber = qqNumber;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg wo_personal_info_select(String bacc, String deviceId, String appid, 
			String phoneNum,INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		WoPersonalCenterSelectProtocol ptl = new WoPersonalCenterSelectProtocol(msgFlag);
		ptl.req_bacc = bacc;
		ptl.req_deviceId = deviceId;
		ptl.req_phoneNum = phoneNum;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
//	预警定制
	public static NetMsg yuJIngSet(String subFunUrl,JSONArray allOrder,String req_identifierType,String req_identifier,String pushId,String appType,INetReceiveListener listener,EMsgLevel level,
			String msgFlag, boolean reSend){
		YuJingSetProtocol ptl = new YuJingSetProtocol(msgFlag);
		ptl.req_identifierType = req_identifierType;
		ptl.subFunUrl = subFunUrl;
		ptl.req_identifier =req_identifier;
		ptl.req_pushId =pushId;
		ptl.req_appType =appType;
		int len=allOrder.length();
		ptl.req_count=len;
		if(len>0){
			ptl.req_orderId = new String[len];
			ptl.req_orderType= new String[len];
			ptl.req_serviceId = new String[len];
			ptl.req_marketId = new String[len];
			ptl.req_productType = new String[len];
			
			ptl.req_stockCode= new String[len];
			ptl.req_stockName = new String[len];
			ptl.req_price = new String[len];
			ptl.req_up = new String[len];	
		for(int i=0;i<len;i++){
			JSONObject jsonObject;
			try {
				jsonObject = (JSONObject) allOrder.get(i);
				ptl.req_orderId[i] = jsonObject.getString("orderId");
				ptl.req_orderType[i] =jsonObject.getString("orderType");
				ptl.req_serviceId[i] = jsonObject.getString("serviceId");
				ptl.req_marketId[i] = jsonObject.getString("marketId");
				ptl.req_productType[i] =jsonObject.getString("productType");
				
				ptl.req_stockCode[i] =jsonObject.getString("stockCode");
				ptl.req_stockName[i] =jsonObject.getString("stockName");
				ptl.req_price[i] =jsonObject.getString("price");
				ptl.req_up[i] =jsonObject.getString("up");
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}	
		}
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS, ptl.subFunUrl);
//		ServerInfo serverInfo = ServerInfoMgr.getInstance()
//		        .getDefaultServerInfo(ProtocolConstant.SERVER_FW_NEWS);
//		//采用临时缓存，防止下一个请求使用相同地址
//		ServerInfo temp=null;
//		if (serverInfo != null) {
//			temp = new ServerInfo(serverInfo.getServerFlag(), ProtocolConstant.SERVER_FW_NEWS, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
//			if(temp!=null){
//				temp.setSubFunUrl(ptl.subFunUrl);
//				temp.setUrl("http://218.61.34.4:21800");
////				temp.setUrl("https://api.jpush.cn/v3/push");
//				//temp.setUrl("http://192.168.230.227:21800");
//			}
//		}
//		NetMsg msg = new NetMsg(msgFlag, level, ptl, ConnInfo.newConnectionInfoSockePost(temp), reSend, listener);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
//	预警查询
	public static NetMsg yuJIngQuery(String subFunUrl,String identifierType,String identifier,int orderId,INetReceiveListener listener,EMsgLevel level,
			String msgFlag, boolean reSend){
		YuJingCXProtocol ptl = new YuJingCXProtocol(msgFlag);
		ptl.subFunUrl = subFunUrl;
		ptl.req_identifierType=identifierType;
		ptl.req_identifier=identifier;
		ptl.req_orderId="0";
		ptl.req_serviceId="0";
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS, ptl.subFunUrl);
//		ServerInfo serverInfo = ServerInfoMgr.getInstance()
//		        .getDefaultServerInfo(ProtocolConstant.SERVER_FW_NEWS);
//		//采用临时缓存，防止下一个请求使用相同地址
//		ServerInfo temp=null;
//		if (serverInfo != null) {
//			temp = new ServerInfo(serverInfo.getServerFlag(), ProtocolConstant.SERVER_FW_NEWS, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
//			if(temp!=null){
//				temp.setSubFunUrl(ptl.subFunUrl);
//				temp.setUrl("http://218.61.34.4:21800");
//				//temp.setUrl("http://192.168.230.227:21800");
//			}
//		}
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
    //	消息中心查询
	public static NetMsg infoCenterQuery(String identifier,INetReceiveListener listener,EMsgLevel level, 
			String msgFlag, boolean reSend){
		InfoCenterCXProtocol ptl = new InfoCenterCXProtocol(msgFlag);
		ptl.req_identifier=identifier;
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS, ptl.subFunUrl);
//		ServerInfo serverInfo = ServerInfoMgr.getInstance()
//				.getDefaultServerInfo(ProtocolConstant.SERVER_FW_NEWS);
//		//采用临时缓存，防止下一个请求使用相同地址
//		ServerInfo temp=null;
//		if (serverInfo != null) {
//			temp = new ServerInfo(serverInfo.getServerFlag(), ProtocolConstant.SERVER_FW_NEWS, serverInfo.getServerName(), serverInfo.getAddress(), true, serverInfo.getHttpsPort());
//			if(temp!=null){
//				temp.setSubFunUrl(ptl.subFunUrl);
//				temp.setUrl("http://218.61.34.4:21800");
//				//temp.setUrl("http://192.168.230.227:21800");
//			}
//		}
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
    //	kds消息中心查询
	public static NetMsg infoCenterQuery(String identifier,String tradeIdentifier,String typeCode,INetReceiveListener listener,EMsgLevel level,
			String msgFlag, boolean reSend){
		KDSInfoCenterCXProtocol ptl = new KDSInfoCenterCXProtocol(msgFlag);
		ptl.req_identifier=identifier;
		ptl.req_tradeIdentifier=tradeIdentifier;
		ptl.req_typeCode=typeCode;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS, ptl.subFunUrl);

		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}

	 //	消息内容查询
		public static NetMsg infoContentQuery(String identifier,String time,int num,INetReceiveListener listener,EMsgLevel level, 
				String msgFlag, boolean reSend){
			InfoContentCXProtocol ptl = new InfoContentCXProtocol(msgFlag);
			ptl.req_identifier=identifier;
			ptl.req_time=time;
			ptl.req_num=num;
			
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS, ptl.subFunUrl);
			NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
			return msg;
		}
	 //	kds消息内容查询
		public static NetMsg infoContentQuery(String identifier,String typeCode,String start,String num,
											  INetReceiveListener listener,EMsgLevel level, String msgFlag,boolean reSend){
			KDSInfoContentCXProtocol ptl = new KDSInfoContentCXProtocol(msgFlag);
			ptl.req_identifier=identifier;
			ptl.req_typeCode=typeCode;
			ptl.req_start=start;
			ptl.req_num=num;

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS, ptl.subFunUrl);
			NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
			return msg;
		}
		//	消息内容清空
		public static NetMsg infoContentClear(String subFunUrl,String identifier,String typeCode,String time,INetReceiveListener listener,EMsgLevel level,
				String msgFlag, boolean reSend){
			InfoContentClearProtocol ptl = new InfoContentClearProtocol(msgFlag);
			ptl.subFunUrl=subFunUrl;
			ptl.req_identifier=identifier;
			ptl.req_typeCode=typeCode;
			ptl.req_time=time;
			
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS, ptl.subFunUrl);
			NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
			return msg;
		}
	
	/**
	 * 请求港股通额度
	 * @param listener
	 * @param flag
	 * @param reSend
	 * @param level
	 * @return
	 */
	public static NetMsg hq_ggt_edu(INetReceiveListener listener, String flag, boolean reSend, EMsgLevel level){
		HQGGTEDProtocol ptl = new HQGGTEDProtocol(flag);

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		
		NetMsg msg = new NetMsg(flag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
    /**
	 * 请求港股通额度
	 * @param listener
	 * @param flag
	 * @param reSend
	 * @param level
	 * @return
	 */
    public static NetMsg hq_ggt_edu(INetReceiveListener listener, String flag, boolean reSend, EMsgLevel level, String market){
        HQGGTEDProtocol ptl = new HQGGTEDProtocol(flag);
        ptl.req_market = market;
        if(!StringUtils.isEmpty(market)){
            ptl.subFunUrl = ptl.subFunUrl + "/?market="+market;
        }
        ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
        NetMsg msg = new NetMsg(flag, level, ptl, connInfo, reSend, listener);
        NetMsg.sendByGet = true;
        return msg;
    }
    /**
     * 排序行情请求
     *
     * @param wMarketID
     * @param wType
     * @param bSort
     * @param bDirect
     * @param wFrom
     * @param wCount
     * @param listener
     * @param msgFlag
     * @param cmdVersion
     * @return
     */
    public static NetMsg hq_px_array(short[] wMarketID, short[] wType, int[] bSort,
                                     int[] bDirect, short[] wFrom, short[] wCount, String pszBKCode,
                                     INetReceiveListener listener, EMsgLevel level, String msgFlag,
                                     int cmdVersion, boolean reSend, boolean isAutoRefresh)
    {
        HQPXProtocol ptl = new HQPXProtocol(msgFlag, cmdVersion);
        ptl.is_array_req = true;
        ptl.setAutoRefresh(isAutoRefresh);
        ptl.req_wMarketID_array = wMarketID;
        ptl.req_wType_array =  wType;
        ptl.req_bSort_array = bSort;
        ptl.req_bDirect_array = bDirect;
        ptl.req_wFrom_array = wFrom;
        ptl.req_wCount_array =  wCount;
        ptl.req_pszBKCode = pszBKCode;
        ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);

        NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);

        return msg;
    }

	/**
	 * 请求个股期权T型数据
	 * @param listener
	 * @param flag
	 * @param reSend
	 * @param level
	 * @return
	 */
	public static NetMsg hq_ggqqT(INetReceiveListener listener, String flag, boolean reSend, EMsgLevel level){
		HQGgqqProtocol ptl = new HQGgqqProtocol(flag);
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl);
		/*ServerInfo s = new ServerInfo("http://192.168.230.226:21800", ProtocolConstant.SERVER_FW_QUOTES, "226测试服务器", "http://192.168.230.226:21800", false, 21900);
		s.setSubFunUrl(ptl.subFunUrl);
		connInfo.setServerInfo(s);*/
		NetMsg msg = new NetMsg(flag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	public static NetMsg hq_ggqqTData(String subFunUrl, INetReceiveListener listener, String flag, boolean reSend, EMsgLevel level
			, int ExpireDateLength, int index){
		HQGgqqTDataProtocol ptl = new HQGgqqTDataProtocol(flag);
		
		ptl.ExpireDate_wCount = ExpireDateLength;
		ptl.ExpireDate_index = index;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, subFunUrl);
		/*ServerInfo s = new ServerInfo("http://192.168.230.226:21800", ProtocolConstant.SERVER_FW_QUOTES, "226测试服务器", "http://192.168.230.226:21800", false, 21900);
		s.setSubFunUrl(ptl.subFunUrl);
		connInfo.setServerInfo(s);*/
		NetMsg msg = new NetMsg(flag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	
	public static NetMsg hq_newStock(String subFunUrl, INetReceiveListener listener, String flag, boolean reSend, EMsgLevel level){
		HQNewStockProtocol ptl = new HQNewStockProtocol(flag);
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, subFunUrl);
		
		NetMsg msg = new NetMsg(flag, level, ptl, connInfo, reSend, listener);
		return msg;
	}


	/** 自选股云同步上传 */
	public static NetMsg hq_zxg_cloud_sysnc_upload(String userId, String userCategory, String clientName, String clientVersion,
												   String appid, Portfolio portfolio,
												   INetReceiveListener listener, EMsgLevel level, String msgFlag, boolean reSend){
		HQZXGCloudSysncUploadProtocol ptl = new HQZXGCloudSysncUploadProtocol(msgFlag);
		ptl.req_userId = userId;
		ptl.req_userCategory = userCategory;
		ptl.req_clientName = clientName;
		ptl.req_clientVersion = clientVersion;
		ptl.req_portfolio = portfolio;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, false);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}


	/** 自选股云同步下载 */
	public static NetMsg hq_zxg_cloud_sysnc_download(String userId, String userCategory, String clientName, String clientVersion, String appid,
													 INetReceiveListener listener, EMsgLevel level, String msgFlag, boolean reSend) {
		HQZXGCloudSysncDownloadProtocol ptl = new HQZXGCloudSysncDownloadProtocol(msgFlag);
		ptl.req_userId = userId;
		ptl.req_userCategory = userCategory;
		ptl.req_clientName = clientName;
		ptl.req_clientVersion = clientVersion;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, false);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		return msg;
	}
	/*次新股查询*/
	public static NetMsg hq_cxgcx(String from, String count, String flag, boolean reSend, EMsgLevel level,INetReceiveListener listener){
		HQCXGCXProtocol ptl = new HQCXGCXProtocol(flag, false);
		ptl.req_count = count;
		ptl.req_from = from;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_QUOTES, ptl.subFunUrl + "from="+from + "&count=" + count, false);
		NetMsg msg = new NetMsg(flag, level, ptl, connInfo, reSend, listener);
		return msg;
	}


}
