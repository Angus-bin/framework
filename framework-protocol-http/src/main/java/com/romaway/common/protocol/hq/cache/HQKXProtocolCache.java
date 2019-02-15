package com.romaway.common.protocol.hq.cache;

import java.io.Serializable;

public class HQKXProtocolCache extends HQProtocolBaseCache implements Serializable {

	private static final long serialVersionUID = 4390490880572257401L;
	
	/**
	 * 交易所代码
	 */
	public short req_wMarketID;
	/**
	 * 代码，字符串最后为0（pszCode[MAX_CODE_LENGTH-1]=0）
	 */
	public String req_pszCode;

	/**
	 * 开始日期nDate = 0 代表当天
	 */
	public int req_nDate;

	/**
	 * K线类型(参见enum KXTypes)
	 */
	public short req_wkxType;

	/**
	 * K线个数
	 */
	public short req_wCount;
	/**
	 * K线开始时间（k线增量需要此时间来确定分钟级k线的增量）
	 */
	public int req_nTime;

	/**
	 * 应答数据
	 */

	/**
	 * 交易所代码
	 */
	public short resp_wMarketID;
	/**
	 * 商品类别
	 */
	public short resp_wType;
	/**
	 * K线类型
	 */
	public short resp_wKXType;

	/**
	 * 代码[char, MAX_CODE_LENGTH]
	 */
	public String resp_pszCode;

	/**
	 * 名称(根据客户端传过来的参数来决定是否要UNICODE编码，当为UNICODE编码时长度为MAX_NAME_LENGTH*2)
	 */
	public String resp_pszName;
	
	/**
	 * 股票状态
	 * 0-无状态 1-开盘前 2-集合竞价 3-交易中 4-午间休市 5-已收盘 6-已收市：非交易日
	 */
	public short resp_wStockStatus;
	/**
	 * 昨收
	 */
	public int resp_nZrsp;
	/**
	 * 今日开盘
	 */
	public int resp_nJrkp;
	/**
	 * 最高成交
	 */
	public int resp_nZgcj;
	/**
	 * 最低成交
	 */
	public int resp_nZdcj;
	/**
	 * 最近成交
	 */
	public int resp_nZjcj;
	/**
	 * 涨跌
	 */
	public int resp_nZd;
	/**
	 * 涨跌幅
	 */
	public int resp_nZdf;
	/**
	 * 振幅
	 */
	public int resp_nZf;
	/**
	 * 总成交数量
	 */
	public int resp_nCjss;
	/** 
	 * 总成交金额 
	 */
	public int resp_nCjje;
	/** 
	 * 涨家数 
	 */
	public short resp_wZjs;
	/** 
	 * 跌家数
	  */
	public short resp_wDjs;
	/**
	 * 平家数
	 */
	public short resp_wPjs;
	/**
	 * 买盘
	 */
	public int resp_nBuyp;
	/**
	 * 卖盘
	 */
	public int resp_nSelp;
	/**
	 * 换手率
	 */
	public int resp_sHSL;
	/**
	 * 市盈率
	 */
	public int resp_sSYL;
	/**
	 * 流通盘
	 */
	public int resp_sLTP;
    /**
	 * 总市值
	 * 总市值=总股本*当前价格
	 */
	public int resp_sZSZ;
	/**
	 * 最近成交时间
	 * MMHH
	 */
	public int resp_dwLastTime;
	/**
	 * 停牌标识
	 * 1为真 
	 * 0为假
	 */
	public byte resp_bSuspended;
	
	//K线数据集
	/**
	 * K线个数
	 */
	public short resp_wKXDataCount;
	/**
	 * 时间
	 */
	public int[] resp_dwDate_s;
	/**
	 * 时间
	 */
	public int[] resp_dwTime_s;
	/**
	 * 昨日收盘
	 */
	public int[] resp_nYClose_s;

	/**
	 * 开盘价格
	 */
	public int[] resp_nOpen_s;
	/**
	 * 最高价格
	 */
	public int[] resp_nZgcj_s;
	/**
	 * 最低价格
	 */
	public int[] resp_nZdcj_s;
	/**
	 * 收盘价格
	 */
	public int[] resp_nClose_s;
	/**
	 * 涨跌幅
	 */
	public int[] resp_nZdf_s;
	/**
	 * 成交金额
	 */
	public int[] resp_nCjje_s;
	/**
	 * 成交数量
	 */
	public int[] resp_nCjss_s;
	/**
	 * 持仓量
	 */
	public int[] resp_nCcl_s;
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
    /**
     * 涨跌
     */
    public int[] resp_nZd_s;
    /**
     * 融资标志
     * ‘T’表示是融资标的证券‘F’表示不是融资标的证券。
     */
	public byte resp_bRZBD;
	/**
	 * 融券标志
	 * ‘T’表示是融资标的证券‘F’表示不是融资标的证券。
	 */
	public byte resp_bRQBD;
}
