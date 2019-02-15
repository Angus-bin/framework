/**
 * 
 */
package com.romaway.android.phone.netreq;

import com.romaway.android.phone.R;
import com.romaway.common.android.base.Res;
import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolUtils;
import com.romaway.common.protocol.dl.StockDetailGsgkDataProtocol;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol2;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocol2;
import com.romaway.common.protocol.service.HQServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.utils.RomaSysConfig;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;

/**
 * 行情类的数据请求
 * 
 * @author duminghui
 * 
 */
public class HQReq
{
	public static final void req(int wMarketId, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, String pszBKCode,
	        INetReceiveListener listener, String msgFlag, int cmdVersion,
	        boolean isAutoRefresh)
	{
		NetMsg msg = HQServices.hq_px(wMarketId, wType, bSort, bDirect, wFrom,
		        wCount, pszBKCode, listener, EMsgLevel.normal, msgFlag,
		        cmdVersion, true, isAutoRefresh);
		NetMsgSend.sendMsg(msg);
	}
	
	public static final void reqHQ(int wMarketId, int wType, int[] autoRefreshArray,int[] bSort,
			int[] bDirect, int wFrom, int wCount, String pszBKCode,
			INetReceiveListener listener, String msgFlag, int cmdVersion,
			boolean isAutoRefresh)
	{
		NetMsg msg = HQServices.hq_px_for_first_interface(wMarketId, wType, autoRefreshArray,bSort, bDirect, wFrom,
				wCount, pszBKCode, listener, EMsgLevel.normal, msgFlag,
				cmdVersion, true, isAutoRefresh);
		NetMsgSend.sendMsg(msg);
	}

    /**
     * 把多次排序请求封装成数组一次性请求
     * @param wMarketId
     * @param wType
     * @param bSort
     * @param bDirect
     * @param wFrom
     * @param wCount
     * @param pszBKCode
     * @param listener
     * @param msgFlag
     * @param cmdVersion
     * @param isAutoRefresh
     */
    public static final void reqArray(short[] wMarketId, short[] wType, int[] bSort,
                                      int[] bDirect, short[] wFrom, short[] wCount, String pszBKCode,
                                      INetReceiveListener listener, String msgFlag, int cmdVersion,
                                      boolean isAutoRefresh)
    {
        NetMsg msg = HQServices.hq_px_array(wMarketId, wType, bSort, bDirect, wFrom,
                wCount, pszBKCode, listener, EMsgLevel.normal, msgFlag,
                cmdVersion, true, isAutoRefresh);
        NetMsgSend.sendMsg(msg);

    }
	/**
	 * 预警设置请求
	 * 
	 * @author duminghui
	 * 
	 */
		public static final void reqYuJing(String subFunUrl,JSONArray allOrder,String req_identifierType,String req_identifier,String pushId,String appType,INetReceiveListener listener,
				String msgFlag)
		{
//			NetMsg msg = HQServices.yuJIngSet(subFunUrl,allOrder, req_identifierType, req_identifier, pushId, appType, listener, EMsgLevel.normal, msgFlag, true);
//			NetMsgSend.sendMsg(msg);
		}
		/**
		 * 预警查询
		 * 
		 * @author duminghui
		 * 
		 */
		public static final void reqYuJingCX(String subFunUrl,String identifierType,String identifier,int orderId,INetReceiveListener listener,
				String msgFlag)
		{
//			NetMsg msg = HQServices.yuJIngQuery(subFunUrl,identifierType, identifier, orderId, listener, EMsgLevel.normal, msgFlag, true);
//			NetMsgSend.sendMsg(msg);
		}
		/**
		 * 消息中心查询
		 * 
		 * @author duminghui
		 * 
		 */
		public static final void reqInfoCenterCX(String identifier,INetReceiveListener listener,
				String msgFlag,boolean isAutoRefresh)
		{
//			NetMsg msg = HQServices.infoCenterQuery(identifier, listener, EMsgLevel.normal, msgFlag, true);
//			NetMsgSend.sendMsg(msg);
		}
		/**
		 * kds消息中心查询
		 *
		 * @author duminghui
		 *
		 */
		public static final void reqInfoCenterCX(String identifier,String typeCode,INetReceiveListener listener,
				String msgFlag)
		{
//			NetMsg msg = HQServices.infoCenterQuery(identifier, typeCode,listener, EMsgLevel.normal, msgFlag, true);
//			NetMsgSend.sendMsg(msg);
		}
		/**
		 * 消息内容查询
		 * 
		 * @author wusq
		 * 
		 */
		public static final void reqInfoContentrCX(String identifier,String time,int num,INetReceiveListener listener,
				String msgFlag,boolean isAutoRefresh)
		{
//			NetMsg msg = HQServices.infoContentQuery(identifier, time, num, listener, EMsgLevel.normal, msgFlag, true);
//			NetMsgSend.sendMsg(msg);
		}
		/**
		 * kds消息内容查询
		 *
		 * @author wusq
		 *
		 */
		public static final void reqInfoContentrCX(String identifier,String typeCode,String start,String num,
												   INetReceiveListener listener, String msgFlag)
		{
//			NetMsg msg = HQServices.infoContentQuery(identifier, typeCode, start,num, listener, EMsgLevel.normal, msgFlag, true);
//			NetMsgSend.sendMsg(msg);
		}
		/**
		 * 消息内容清除
		 * 
		 * @author wusq
		 * 
		 */
		public static final void reqInfoContentrClear(String subFunUrl,String identifier,String typeCode,String time,INetReceiveListener listener,
				String msgFlag,boolean isAutoRefresh)
		{
//			NetMsg msg = HQServices.infoContentClear(subFunUrl,identifier, typeCode, time, listener, EMsgLevel.normal, msgFlag, true);
//			NetMsgSend.sendMsg(msg);
		}

	/**
	 * 大盘
	 * 
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqDaPan(int wType, int bSort, int bDirect,
	        int wFrom, int wCount, INetReceiveListener listener,
	        String msgFlag, boolean isAutoRefresh)
	{
		req(ProtocolConstant.SE_SS, wType, bSort, bDirect, wFrom, wCount, "",
				listener, msgFlag, Res.getInteger(R.integer.dapanzhishu_inland_version), isAutoRefresh);
	}

	/**
	 * 请求市场
	 * 
	 * @param wMarketId
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqShiChang(int wMarketId, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, INetReceiveListener listener,
	        String msgFlag, boolean isAutoRefresh)
	{
		req(wMarketId, wType,bSort, bDirect, wFrom, wCount, "", listener,
		        msgFlag, Res.getInteger(R.integer.market_quotation_version), isAutoRefresh);
	}
	/**
	 * 请求市场,针对市场首页
	 * 
	 * @param wMarketId
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqShiChangForFirst(int wMarketId, int wType, int[] autoRefreshArray,int[] bSort,
			int[] bDirect, int wFrom, int wCount, INetReceiveListener listener,
			String msgFlag, boolean isAutoRefresh)
	{
		reqHQ(wMarketId, wType, autoRefreshArray,bSort, bDirect, wFrom, wCount, "", listener,
				msgFlag, Res.getInteger(R.integer.market_quotation_version), isAutoRefresh);
	}

	/**
	 * 中证债券
	 */
	public static final void reqZZZQ(int wMarketId, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, int cmdVersion,
	        INetReceiveListener listener, String msgFlag, boolean isAutoRefresh)
	{
		NetMsg msg = HQServices.hq_zzzq(wMarketId, wType, bSort, bDirect,
		        wFrom, wCount, listener, EMsgLevel.normal, msgFlag, cmdVersion,
		        false, isAutoRefresh);
		NetMsgSend.sendMsg(msg);
	}

	/**
	 * TODO
	 * 
	 * @param wMarketID
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param pszBKCode
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqBanKuai(int wMarketID, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, String pszBKCode,
	        INetReceiveListener listener, String msgFlag, boolean isAutoRefresh)
	{
		req(wMarketID, wType, bSort, bDirect, wFrom, wCount, pszBKCode,
				listener, msgFlag, Res.getInteger(R.integer.plate_quotation_version), isAutoRefresh);

	}

	/**
	 * 港股
	 * 
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqGanggu(int wType, int bSort, int bDirect,
	        int wFrom, int wCount, INetReceiveListener listener,
	        String msgFlag, boolean isAutoRefresh)
	{
		req(ProtocolConstant.SE_GG, wType, bSort, bDirect, wFrom, wCount, "",
		        listener, msgFlag, Res.getInteger(R.integer.hk_stock_quotation_version), isAutoRefresh);
	}

	public static final void reqGangGuFL(int wMarketId, int wType, int bSort,
		   int bDirect, int wFrom, int wCount, INetReceiveListener listener,
		   String msgFlag, boolean isAutoRefresh)
	{
		req(wMarketId, wType, bSort, bDirect, wFrom, wCount, "", listener,
				msgFlag,1, isAutoRefresh);
	}
	
	/**
	 * 沪港通
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 * @param isAutoRefresh
	 */
	public static final void reqHGT(int wType, int bSort, int bDirect,
	        int wFrom, int wCount, INetReceiveListener listener,
	        String msgFlag, boolean isAutoRefresh){
		req(ProtocolConstant.SE_HGT, wType, bSort, bDirect, wFrom, wCount, "",
		        listener, msgFlag, Res.getInteger(R.integer.hgt_stock_quotation_version), isAutoRefresh);
	}

    public static final void reqHGT(int marketId, int wType, int bSort, int bDirect,
                                    int wFrom, int wCount, INetReceiveListener listener,
                                    String msgFlag, boolean isAutoRefresh){
        req(marketId, wType, bSort, bDirect, wFrom, wCount, "",
                listener, msgFlag, Res.getInteger(R.integer.hgt_stock_quotation_version), isAutoRefresh);
    }

	/**
	 * 全球股指
	 * 
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqQuanQiuGuZhi(int bSort, int bDirect, int wFrom,
	        int wCount, INetReceiveListener listener, String msgFlag, boolean isAutoRefresh)
	{
		NetMsg msg = HQServices.hq_gg_px(ProtocolConstant.SE_QQ, ProtocolConstant.STOCKTYPES_QQ_WORLD,
		        bSort, bDirect, wFrom, wCount, "", listener, EMsgLevel.normal, msgFlag, Res.getInteger(R.integer.global_stock_index_version), true, false);
		NetMsgSend.sendMsg(msg);
	}
	/**
	 * 全球股指分类
	 * 
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqQuanQiuGuZhiFenLei(int bSort,int wType, int bDirect, int wFrom,
	        int wCount, INetReceiveListener listener, String msgFlag, boolean isAutoRefresh)
	{
		NetMsg msg = HQServices.hq_gg_px(ProtocolConstant.SE_QQ,wType,
		        bSort, bDirect, wFrom, wCount, "", listener, EMsgLevel.normal, msgFlag, Res.getInteger(R.integer.global_stock_index_fenlei_version), true, false);
		NetMsgSend.sendMsg(msg);
	}
	/**
	 * 股转
	 * @param wMarketId
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 * @param isAutoRefresh
	 */
	public static final void reqGuZhuan(int wMarketId, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, INetReceiveListener listener,
	        String msgFlag, boolean isAutoRefresh)
	{
		req(wMarketId, wType, bSort, bDirect, wFrom, wCount, "", listener,
		        msgFlag, Res.getInteger(R.integer.sanban_market_version), isAutoRefresh);
	}
	
	/**
	 * 个股期权T
	 * @param flag
	 * @param listener
	 */
	public static final void reqGgQq(String flag, INetReceiveListener listener)
	{
		NetMsg msg = HQServices.hq_ggqqT(listener, flag, true, EMsgLevel.normal);
		NetMsgSend.sendMsg(msg);
	}
	/**
	 * 个股期权T详情
	 * @param marketID
	 * @param codes
	 * @param ExpireDate
	 * @param flag
	 * @param listener
	 * @param ExpireDateLength
	 */
	public static final void reqGgQqTData(int marketID, String codes,
			int ExpireDate, String flag, INetReceiveListener listener, int ExpireDateLength, int index){
		NetMsg msg = HQServices.hq_ggqqTData("/api/quote/t_details/?"
				+ marketID + "&" + codes + "&" + ExpireDate, listener, flag,
				true, EMsgLevel.normal, ExpireDateLength, index);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 请求新股数据
	 * @param codes
	 * @param flag
	 * @param listener
	 */
	public static final void reqNewStockData(String codes, String flag,
			INetReceiveListener listener) {
		NetMsg msg = HQServices.hq_newStock("/api/quote/cloud_dm/?" + codes,
				listener, flag, true, EMsgLevel.normal);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 外汇
	 * 
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqWaihui(int bSort, int bDirect, int wFrom,
	        int wCount, INetReceiveListener listener, String msgFlag,
	        boolean isAutoRefresh)
	{
//		req(ProtocolConstant.SE_HK, ProtocolConstant.STOCKTYPES_ST_WH, bSort,
//		        bDirect, wFrom, wCount, "", listener, msgFlag, 5, isAutoRefresh);
		NetMsg msg = HQServices.hq_gg_px(ProtocolConstant.SE_GG, ProtocolConstant.STOCKTYPES_ST_WH,
		        bSort, bDirect, wFrom, wCount, "", listener, EMsgLevel.normal, msgFlag, Res.getInteger(R.integer.waihui_market_version), true, false);
		NetMsgSend.sendMsg(msg);
	}

	/**
	 * 期货
	 * 
	 * @param wMarketID
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqQiHuo(int wMarketID, int wType, int bSort,
	        int bDirect, int wFrom, int wCount, INetReceiveListener listener,
	        String msgFlag, boolean isAutoRefresh)
	{
		int version = Res.getInteger(R.integer.futures_market_version);
		NetMsg msg = HQServices.hq_qhpx(wMarketID, wType, bSort, bDirect,
		        wFrom, wCount, listener, EMsgLevel.normal, msgFlag, version,
		        false, isAutoRefresh);
		NetMsgSend.sendMsg(msg);
	}

	/**
	 * 请求分时数据
	 * 
	 * @param listener
	 * @param stockCode
	 * @param stockType
	 * @param dwtime
	 */
	public static final void reqFs(INetReceiveListener listener,
	        String stockCode, int stockType, int marketId, int time, boolean isAutoRefresh)
	{
		ANetMsg msg = null;
		if (stockType == ProtocolUtils.INDEX
		        || stockType == ProtocolUtils.STOCK
		        || stockType == ProtocolUtils.BOND_SHENZHEN
		        || stockType == ProtocolUtils.BOND_SHANGZHEN
		        || stockType == ProtocolUtils.BH_STOCK
		        || stockType == ProtocolUtils.SANBAN)
		{// 一般股票、指数
			int version = Res.getInteger(R.dimen.hangqing_fs_version);
//			msg = HQServices.hq_fszh(marketId, stockCode, 0, (short) 2, time,
//			        listener, EMsgLevel.normal, "hq_fszh", version, isAutoRefresh, true);

		} else if (stockType == ProtocolUtils.FUTURES)
		{
			// 期货
			int version = Res.getInteger(R.dimen.hangqing_fs_version);
			msg = HQServices.hq_qhfszh(ProtocolConstant.SE_ZG_QH, stockCode, 0,
			        (short) 1, 0, listener, EMsgLevel.normal, "hq_qhfszh", version,isAutoRefresh,
			        true);
		} else if (stockType == ProtocolUtils.HK_STOCK
		        || stockType == ProtocolUtils.HK_INDEX)
		{
			// 港股
			int hkversion = Res.getInteger(R.dimen.hangqing_hkfs_version);
//			msg = HQServices.hq_fszh(/*ProtocolConstant.SE_HK*/marketId, stockCode, 0,
//			        (short) 1, time, listener, EMsgLevel.normal, "hq_gg_fszh", hkversion,isAutoRefresh,
//			        true);
		} else if (stockType == ProtocolUtils.HGT_STOCK || stockType == ProtocolUtils.SGT_STOCK){
			// 沪港通
			int hkversion = Res.getInteger(R.dimen.hangqing_hkfs_version);
//			msg = HQServices.hq_fszh(/*ProtocolConstant.SE_HK*/marketId, stockCode, 0,
//					(short) 1, time, listener, EMsgLevel.normal, "hq_gg_fszh", hkversion,isAutoRefresh,
//					true);
		} else if (stockType == ProtocolUtils.OPTION) { //个股期权
//			msg = HQServices.hq_fszh(marketId, stockCode, 0, (short) 2, time,
//			        listener, EMsgLevel.normal, "hq_fszh", Res.getInteger(R.integer.option_fenshi_market_version), isAutoRefresh, true);
		} else if (stockType == ProtocolUtils.ST_FUND) {
			int version = Res.getInteger(R.dimen.hangqing_fs_version);
//			msg = HQServices.hq_fszh(marketId, stockCode, 0, (short) 2, time,
//					listener, EMsgLevel.normal, "hq_fszh", version, isAutoRefresh, true);
		} else if (stockType == ProtocolUtils.ST_FXJS) {
			int version = Res.getInteger(R.dimen.hangqing_fs_version);
//			msg = HQServices.hq_fszh(marketId, stockCode, 0, (short) 2, time,
//			        listener, EMsgLevel.normal, "hq_fszh", version, isAutoRefresh, true);
		}
		if (msg != null)
		{
			NetMsgSenderProxy.getInstance().send(msg);
		}

	}
	/**
	 * 请求K线数据
	 * 
	 * @param listener
	 * @param stockCode
	 * @param type
	 * @param kLineType
	 */
	public static final void reqKLine(INetReceiveListener listener,
	        short marketId, String stockCode, int type, short kLineType,
	        int dwKXDate, int dwKXTime, boolean isAutoRefresh)
	{
		ANetMsg msg = null;
		// 一般股票、指数
		if (type == ProtocolUtils.INDEX || type == ProtocolUtils.STOCK
		        || type == ProtocolUtils.BOND_SHENZHEN
		        || type == ProtocolUtils.BOND_SHANGZHEN
		        || type == ProtocolUtils.SANBAN)
		{
		
			msg = HQServices.hq_kxzh(marketId, stockCode, dwKXDate, kLineType,
					(short) 144, dwKXTime, listener, EMsgLevel.normal,
			        "hq_klzh_gg", 5, isAutoRefresh, true);

		} else if (type == ProtocolUtils.FUTURES)
		{
			// 期货
			msg = HQServices.hq_qhkxzh(marketId, stockCode, 0, kLineType,
			        (short) 146, listener, EMsgLevel.normal, "hq_klzh_qh", 2,isAutoRefresh,
			        true);
		} else if (type == ProtocolUtils.HK_INDEX
		        || type == ProtocolUtils.HK_STOCK)
		{
			// 港股
			msg = HQServices.hq_ggkxzh(marketId, stockCode, 0, kLineType,
			        (short) 146, listener, EMsgLevel.normal, "hq_klzh_hk", 2,isAutoRefresh,
			        true);
		}
		if (msg != null)
		{
			NetMsgSenderProxy.getInstance().send(msg);
		}
	}
	/**
	 * 综合排名，上证A股
	 * 
	 * @param wType
	 * @param bSort
	 * @param bDirect
	 * @param wFrom
	 * @param wCount
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqHAG(int mid, int wType, int bSort, int bDirect,
	        int wFrom, int wCount, INetReceiveListener listener,
	        String msgFlag, boolean isAutoRefresh)
	{
		req(mid, wType, bSort, bDirect, wFrom, wCount, "", listener, msgFlag,
		        1, isAutoRefresh);
	}
	
	/**
	 * K线
	 * @param pszCode
	 * @param nDate
	 * @param kLineType
	 * @param wCount
	 * @param wMarketID
	 * @param nTime
	 * @param listener
	 * @param cmdVersion
	 * @param isAutoRefresh
	 */
	public static final void reqKLine(String pszCode, short wMarketID, short kLineType, int nTime, int nDate,
			int FQType, INetReceiveListener listener,int cmdVersion, boolean isAutoRefresh, boolean isFirst, boolean kds_hq_kline_Landscape_is_support_history, boolean isHistoryAuto){
		ANetMsg msg = null;
		//服务器K线做了增量下发， 沪深 服务器目前最多存168， 沪港通是 186 条k线；所以K线数量大于等于186
		short count = 186;
		if(kds_hq_kline_Landscape_is_support_history){
			if(isFirst){
				count = 120;
			}else{
				if (isHistoryAuto) {
					count = 10;
				} else {
					count = 186;
				}
			}
		}
		Logger.d("", "----------------------tag = " + count);
//		msg = HQServices.hq_kx( pszCode, nDate, kLineType,
//				count, wMarketID, nTime, FQType, listener, EMsgLevel.normal,
//		        "hq_kx", cmdVersion,  isAutoRefresh);
		if (msg != null){
			NetMsgSenderProxy.getInstance().send(msg);
		}
	}

	/**
	 * 获取港股通额度
	 * @param flag
	 * @param listener
	 */
	public static final void reqGGTEDU(String flag, INetReceiveListener listener){
		NetMsg msg = HQServices.hq_ggt_edu(listener, flag, false, EMsgLevel.normal);
		NetMsgSend.sendMsg(msg);
	}
    /**
     * 获取港股通额度
     * @param flag
     * @param listener
     */
    public static final void reqGGTEDU(String flag, INetReceiveListener listener, String market){
        NetMsg msg = HQServices.hq_ggt_edu(listener, flag, false, EMsgLevel.normal, market);
        NetMsgSend.sendMsg(msg);
    }
    /**
     * 沪港通和深港通涨跌榜
     * @param wType
     * @param bSort
     * @param bDirect
     * @param wFrom
     * @param wCount
     * @param listener
     * @param msgFlag
     * @param isAutoRefresh
     */
    public static final void reqArraySGT(short[] marketArray,short[] wType, int[] bSort, int[] bDirect,
                                         short[] wFrom, short[] wCount, INetReceiveListener listener,
                                         String msgFlag, boolean isAutoRefresh){
        reqArray(marketArray, wType, bSort, bDirect, wFrom, wCount, "",
                listener, msgFlag, 1, isAutoRefresh);
    }
    /**
     * 深港通
     * @param wType
     * @param bSort
     * @param bDirect
     * @param wFrom
     * @param wCount
     * @param listener
     * @param msgFlag
     * @param isAutoRefresh
     */
    public static final void reqSGT(int wType, int bSort, int bDirect,
                                    int wFrom, int wCount, INetReceiveListener listener,
                                    String msgFlag, boolean isAutoRefresh){
        req(ProtocolConstant.SE_SGT, wType, bSort, bDirect, wFrom, wCount, "",
                listener, msgFlag, 1, isAutoRefresh);
    }

	public static final void reqStockCodeData(INetReceiveListener listener,String msgFlag) {
		HQNewCodeListProtocol2 ptl = new HQNewCodeListProtocol2(msgFlag);
		try {
			String ip = "https://share.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl);
			ServerInfo temp = new ServerInfo("get_stock_code", ProtocolConstant.SERVER_FW_AUTH, "get_stock_code", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockZXGDown(String userID, INetReceiveListener listener,String msgFlag) {
		HQZXGTBSelectProtocol2 ptl = new HQZXGTBSelectProtocol2(msgFlag);
		try {
			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?user_id=" + userID);
			ServerInfo temp = new ServerInfo("get_stock_zxg", ProtocolConstant.SERVER_FW_AUTH, "get_stock_zxg", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?user_id=" + userID);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
