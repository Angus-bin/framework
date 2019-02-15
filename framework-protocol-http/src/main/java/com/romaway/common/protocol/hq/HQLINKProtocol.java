package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 关联股票行情请求协议
 * 
 * @author xueyan
 * 
 */
public class HQLINKProtocol extends AProtocol
{
	public static final short HQ_LINK = 8;
	// 请求
	/*
	 * 股票代码,如果有多个股票，采用逗号作为分
	 */
	public String req_pszCode;
	/**
	 * 市场代码,如果有多个市场，采用逗号作为分隔符
	 */
	public String req_marketCode;

	// 响应
	/**
	 * 数据结构的真实数目
	 */
	public short resp_wCount;
	/**
	 * 交易所代码（enum StockExchanges）
	 */
	public short[] resp_wMarketId_s;
	/**
	 * 商品类型
	 */
	public short[] resp_wType_s;
	/**
	 * 股票代码
	 */
	public String[] resp_pszCode_s;
	/**
	 * 股票代码名称
	 */
	public String[] resp_pszName_s;
	/**
	 * 昨收
	 */
	public int[] resp_nZrsp_s;
	/**
	 * 今日开盘
	 */
	public int[] resp_nJrkp_s;
	/**
	 * 最高成交
	 */
	public int[] resp_nZgcj_s;
	/**
	 * 最低成交
	 */
	public int[] resp_nZdcj_s;
	/**
	 * 最近成交
	 */
	public int[] resp_nZjcj_s;
	/**
	 * 成交数量
	 */
	public int[] resp_nCjss_s;
	/**
	 * 成交金额
	 */
	public int[] resp_nCjje_s;
	/**
	 * 涨跌值
	 */
	public int[] resp_nZd_s;
	/**
	 * 停牌标示 1为真 0为假
	 */
	public byte[] resp_bSuspended_s;

	/**
	 * 股票代码类型,如”债券”,”权证”
	 */
	public String[] resp_sCodeType_s;

	/**
	 * @param flag
	 * @param cmdVersion
	 */
	public HQLINKProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_LINK, cmdVersion, true, false);
		// TODO Auto-generated constructor stub
	}

}
