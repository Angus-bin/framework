package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * K线综合协议
 * 
 * @author xueyan
 * 
 */

public class HQKXZHProtocol extends AProtocol
{

	/** 子功能号 */
	public final static short HQ_KXZH = 15;

	// 请求
	/** 交易所代码 */
	public short req_wMarketID;

	/** 股票代码 */
	public String req_sPszCode;

	/** K线开始日期 */
	public int req_dwKXDate;

	/** K线类型 */
	public short req_wKXType;

	/** K线个数 */
	public short req_wKXCount;

	// version>4
	/** 增加K线开始时间（k线增量需要此时间来确定分钟级k线的增量） **/
	public int req_dwKXTime;

	// 返回数据

	/** 交易所代码 */
	public short resp_wMarketID;

	/** 商品类型 */
	public short resp_wType;

	/** 代码 */
	public String resp_sPszCode;

	/** 代码名称 */
	public String resp_wsPszName;

	/** 昨收 */
	public int resp_nZrsp;

	/** 今日开盘 */
	public int resp_nJrkp;

	/** 最高成交 */
	public int resp_nZgcj;

	/** 最低成交 */
	public int resp_nZdcj;

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

	/** 数据中最高价 */
	public int resp_nMaxPrice;

	/** 数据中最低价 */
	public int resp_nMinPrice;

	/** 数据中最大成交量 */
	public int resp_nMaxVol;

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

	/** K线数据集数 */
	public short resp_wKXDataCount;
	/** 时间 */
	public int[] resp_dwTime_s;
	/** 昨日收盘 */
	public int[] resp_nYClose_s;
	/** 开盘价格 */
	public int[] resp_nOpen_s;
	/** 最高价格 */
	public int[] resp_nZgcj_s;
	/** 最低价格 */
	public int[] resp_nZdcj_s;
	/** 收盘价格 */
	public int[] resp_nClose_s;
	/** 涨跌幅 */
	public int[] resp_nZdf_s;
	/** 成交金额 */
	public int[] resp_nCjje_s;
	/** 成交数量 */
	public int[] resp_nCjss_s;
	/** 持仓量 */
	public int[] resp_nCcl_s;
	/** 换手率 */
	public int[] resp_nHsl_s;
	/** 市盈率 */
	public int[] resp_nSyl_s;
	/** 移动平均线1 */
	public int[] resp_nMA1_s;
	/** 移动平均线2 */
	public int[] resp_nMA2_s;
	/** 移动平均线3 */
	public int[] resp_nMA3_s;
	/** 技术指标值1 */
	public int[] resp_nTech1_s;
	/** 技术指标值2 */
	public int[] resp_nTech2_s;
	/** 技术指标值3 */
	public int[] resp_nTech3_s;
	/** 信息公告 */
	public String[] resp_wsXxgg_s;
	/** 核算价 */
	public int[] resp_nHsj_s;
	/** 买卖点 */
	public byte[] resp_bFlag_s;
	/** 涨跌 */
	public int[] resp_nZd_s;

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
	/**
	 * 现量
	 */
	public int resp_iXL;

	/**
	 * 总市值
	 */
	public int resp_iZSZ;
	/**
	 * 均价
	 */
	public int resp_iJJ;
	/**
	 * 加权均价
	 */
	public int resp_iJQJJ;
	/**
	 * 平盘
	 */
	public int resp_iPP;
	/**
	 * 流通盘
	 */
	public int resp_iLTP;
	/**
	 * 现量
	 */
	public String resp_sXL;

	/**
	 * 总市值
	 */
	public String resp_sZSZ;
	/**
	 * 均价
	 */
	public String resp_sJJ;
	/**
	 * 加权均价
	 */
	public String resp_sJQJJ;
	/**
	 * 平盘
	 */
	public String resp_sPP;
	/**
	 * 流通盘
	 */
	public String resp_sLTP;

	/** 增加中证债券估值价格 **/
	public String resp_sZZZQValPrice;

	public HQKXZHProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_KXZH, cmdVersion, true, false);
		this.subFunUrl = "/api/quote/kxzh";
	}
}
