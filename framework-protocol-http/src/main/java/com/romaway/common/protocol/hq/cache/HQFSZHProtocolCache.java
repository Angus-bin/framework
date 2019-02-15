package com.romaway.common.protocol.hq.cache;

import java.io.Serializable;

public class HQFSZHProtocolCache extends HQProtocolBaseCache implements Serializable {

	private static final long serialVersionUID = -394571005215872045L;
	
	/** 交易所代码 */
	public short req_wMarketID;
	/** 代码 */
	public String req_sPszCode;
	/** 分时数据日期，0代表最近一天，其它日期采用20040317格式来描述 */
	public int req_dwFSDate;
	/** 采样间隔，最小采样单位的倍数（目前最小采样单位为1分钟 */
	public short req_wFSFreq;
	/** 分时数据起始时间,0表示全天的数据,其它时间采用HHMM(0935)的格式描述 */
	public int req_dwFSTime;

	// 返回数据

	/** 交易所代码 */
	public short resp_wMarketID;

	/** 商品类型 */
	public short resp_wType;

	/** 代码 */
	public String resp_pszCode;

	/** 代码名称 */
	public String resp_pszName;
	
	/**
	 * 股票状态
	 * 0-无状态 1-开盘前 2-集合竞价 3-交易中 4-午间休市 5-已收盘 6-已收市：非交易日
	 */
	public short resp_wStockStatus;

	/** 分时数据日期 */
	public int resp_dwDate;

	/** 采样点总数 */
	public short resp_wCYDZS;

	/** 昨收 */
	public int resp_nZrsp;

	/** 今日开盘 */
	public int resp_nJrkp;

	/** 最高成交 */
	public int resp_nZgcj;

	/** 最低成交 */
	public int resp_nZdcj;

	/** 最近成交 */
	public int resp_nZjcj;
	
	/**
	 * 最近成交时间
	 */
	public int resp_dwLastTime;

	/** 数据中最大成交量 */
	public int resp_nMaxVol;

	/** 核算价 */
	public int resp_nHsj;

	/** 涨跌 */
	public int resp_nZd;

	/** 涨跌幅 */
	public int resp_nZdf;

	/** 振幅 */
	public int resp_nZf;

	/** 总成交数量 */
	public int resp_nCjss;

	/** 总成交金额 */
	public int resp_nCjje;

	/** 涨家数 */
	public short resp_wZjs;

	/** 跌家数 */
	public short resp_wDjs;

	/** 所属板块代码 */
	public String resp_sBKCode;

	/** 所属板块名称 */
	public String resp_wsBKName;

	/** 板块涨跌幅 */
	public int resp_nBKZF;

	/** 委比 */
	public int resp_nWb;

	/** 委差 */
	public int resp_nWc;

	/** 量比 */
	public int resp_nLb;

	/** 买盘 */
	public int resp_nBuyp;

	/** 卖盘 */
	public int resp_nSelp;

	/** 涨停价 */
	public int resp_nLimUp;

	/** 跌停价 */
	public int resp_nLimDown;

	/** 是否有关联股票, '1'为有,'0' 为没有 */
	public String resp_sLinkFlag;

	/** 停牌标示, 1为真 0为假 */
	public byte resp_bSuspended;

	/** 买卖方案数据集数 */
	public short resp_wMMFADataCount;
	/** 买(i)价格 */
	public int[] resp_nBjg_s;
	/** 买(i)数量 */
	public int[] resp_nBsl_s;
	/** 卖(i)价格 */
	public int[] resp_nSjg_s;
	/** 卖(i)数量 */
	public int[] resp_nSsl_s;

	/** 指数数据集数 */
	public short resp_wZSDataCount;
	/** 指数代码 */
	public String[] resp_sZSPszCode_s;
	/** 指数名称 */
	public String[] resp_wsZSPszName_s;
	/** 指数现价 */
	public int[] resp_nZSXj_s;
	/** 指数涨跌幅 */
	public int[] resp_nZSZdf_s;
	/** 指数昨收 */
	public int[] resp_nZSZrsp_s;

	/** 分笔数据集数 */
	public short resp_wFBDataCount;
	/** 分笔时间 */
	public int[] resp_dwFBTime_s;
	/** 分笔成交类别 */
	public byte[] resp_bFBCjlb_s;
	/** 分笔成交价格 */
	public int[] resp_nFBZjcj_s;
	/** 分笔成交数量 */
	public int[] resp_nFBCjss_s;

	/** 分时数据集数 */
	public short resp_wFSDataCount;
	/** 采样的时间点0930(时分) */
	public int[] resp_dwTime_s;
	/** 成交价 */
	public int[] resp_nZjcj_s;
	/** 涨跌幅 */
	public int[] resp_nZdf_s;
	/** 成交量 */
	public int[] resp_nCjss_s;
	/** 成交金额 */
	public int[] resp_nCjje_s;
	/** 成交均价 */
	public int[] resp_nCjjj_s;
	/** 信息地雷标识 */
	public short[] resp_sXxgg_s;
	/** 持仓量 */
	public int[] resp_nCcl_s;
	/** 量比 */
	public int[] resp_nLb_s;
	// ---version=3----对深圳成指和上证指数添加涨家数、跌家数----------
	/** 涨家数 **/
	public int[] resp_zNum_s;
	/** 跌家数 **/
	public int[] resp_dNum_s;

	/** 每股收益 */
	public int resp_nSY;
	/** 每股收益扣除 */
	public int resp_nSYKC;
	/** 每股净资产 */
	public int resp_nJZC;
	/** 净资产收益率 */
	public int resp_nJZCSYL;
	/** 资本公积金 */
	public int resp_nZBGJJ;
	/** 未分配利润 */
	public int resp_nWFPLY;
	/** 现金流量 */
	public int resp_nXJLL;
	/** 净利润 */
	public int resp_nJLY;
	/** 股东权益 */
	public int resp_nGDQY;

	// ****取得此值协议版本号必须=1）*******
	/** 现量 */
	// public String resp_sXL;
	/** 总市值 */
	// public String resp_sZSZ;
	/** 均价 */
	// public String resp_sJJ;
	/** 加权均价 */
	// public String resp_sJQJJ;
	/** 平盘 */
	// public String resp_sPP;
	/** 换手率 */
	// public String resp_sHSL;
	/** 市盈率 */
	// public String resp_sSYL;
	/** 流通盘 */
	// public String resp_sLTP;

	/** 现量 */
	public int resp_iXL;
	/** 总市值 */
	public int resp_iZSZ;
	/** 均价 */
	public int resp_iJJ;
	/** 加权均价 */
	public int resp_iJQJJ;
	/** 平盘 */
	public int resp_iPP;
	/** 换手率 */
	public int resp_iHSL;
	/** 市盈率 */
	public int resp_iSYL;
	/** 流通盘 */
	public int resp_iLTP;
	/** 中证债券估值价格 **/
	public String resp_sZZZQValPrice;
	/** 增加融资标志 **/
	public byte resp_sbRZBD;
	/** 增加融券标志 **/
	public byte resp_sbRQBD;

}
