package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * @author qinyn
 * 行情分时协议
 */

public class HQFSProtocol extends AProtocol
{
	public static final short HQ_FS = 1;
	
	//请求信息
	/**
	 * 代码，字符串最后为0
	 */
	public String req_pszCode;
	/**
	 * 分时数据日期，0代表最近一天，其它日期采用20040317格式来描述
	 */
	public int req_nDate;
	/**
	 * 采样间隔，最小采样单位的倍数（目前最小采样单位为1分钟）
	 */
	public byte req_bFreq;
	/**
	 * 分时数据起始时间,0表示全天的数据,其它时间采用HHMM(0935)的格式描述
	 */
	public int req_nTime;
	/**
	 * 交易所代码
	 */
	public short req_wMarketID;

	
	// 应答信息
	/**
	 * 交易所代码
	 */
	public short resp_wMarketID;
	/**
	 * 商品类型
	 */
	public short resp_wType;
	/**
	 *  分时数据日期20070430(年月日)
	 */
	public int resp_nDate;
	/**
	 * 代码
	 */
	public String resp_pszCode;
	/**
	 * 名称
	 */
	public String resp_pszName;

	/**
	 * 昨收
	 */
	public int resp_nZrsp;
	/**
	 * 昨核算
	 */
	public int resp_nZhsj;
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
	 * 总的成交数量
	 */
	public int resp_nCjss;
	/**
	 * 总的成交金额
	 */
	public int resp_nCjje;
	/**
	 * 数据中最大成交量
	 */
	public int resp_nMaxVol;
	/**
	 * 核算价
	 */
	public int resp_nHsj;
	
	/**
	 * 换手率
	 */
	public int resp_nHsl;
	/**
	 * 市盈 率
	 */
	public int resp_nSyl;
	/**
	 * 总采样点数
	 */
	public short resp_wCount;
	
	// dwVersion>=3,将分时与报价合并，下列是原报价的字段
	/**
	 * 委比
	 */
	public int resp_nWb;
	/**
	 * 委差
	 */
	public int resp_nWc;
	/**
	 * 量比
	 */
	public int resp_nLb;
	/**
	 * 振幅
	 */
	public int resp_nZf;
	/**
	 * 买盘
	 */
	public int resp_nBuyp;
	/**
	 * 卖盘
	 */
	public int resp_nSelp;
	/**
	 * 涨停价
	 */
	public int resp_nLimUp;
	/**
	 * 跌停价
	 */
	public int resp_nLimDown;
	/**
	 *  买(1)价格
	 */
	public int resp_nBjg1;
	/**
	 *  买(1)数量
	 */
	public int resp_nBss1;
	/**
	 *  买(2)价格
	 */
	public int resp_nBjg2;
	/**
	 *  买(2)数量
	 */
	public int resp_nBss2;
	
	/**
	 * 买(3)价格
	 */
	public int resp_nBjg3;
	/**
	 *  买3)数量
	 */
	public int resp_nBss3;
	/**
	 * 买(4)价格
	 */
	public int resp_nBjg4;
	/**
	 *  买(4)数量
	 */
	public int resp_nBss4;
	/**
	 * 买(5)价格
	 */
	public int resp_nBjg5;
	/**
	 * 买(5)数量
	 */
	public int resp_nBss5;
	/**
	 *  卖(1)价格
	 */
	public int resp_nSjg1;
	/**
	 *  卖(1)数量
	 */
	public int resp_nSss1;
	/**
	 *  卖(2)价格
	 */
	public int resp_nSjg2;
	/**
	 *  卖(2)数量
	 */
	public int resp_nSss2;
	/**
	 *  卖(3)价格
	 */
	public int resp_nSjg3;
	/**
	 *  卖(3)数量
	 */
	public int resp_nSss3;
	/**
	 *  卖(4)价格
	 */
	public int resp_nSjg4;
	/**
	 *  卖(4)数量
	 */
	public int resp_nSss4;
	/**
	 *  卖(5)价格	
	 */
	public int resp_nSjg5;
	/**
	 *  卖(5)数量
	 */
	public int resp_nSss5;
	/**
	 *  停牌标示 
	 *	1为真 
	 *	0为假
	 */
	public byte resp_bSuspended;
	
	//dwVersion>=4, 增加净资产、股本、流通盘、收益、关联标识、所属板块代码、所属板块名称、板块涨幅
	
	/**
	 * 净资产
	 */
	public int resp_nJZC;
	/**
	 * 股本
	 */
	public int resp_nGB;
	/**
	 * 流通盘
	 */
	public int resp_nLTGB;
	/**
	 * 收益
	 */
	public int resp_nSY;
	/**
	 * 是否有关联股票
	   1为有 0 为没有
	 */
	public String resp_sLinkFlag;
	/**
	 * 所属板块代码
	 */
	public String resp_sBKCode;
	/**
	 * 所属板块名称
	 */
	public String resp_sBKName;
	/**
	 * 板块涨幅
	 */
	public int resp_nBKZf;
	
	//pData分时数据包结构tagFSDatas变量
	/**
	 *  采样的时间点0930(时分)
	 */
	public int[] resp_nTime_s;
	/**
	 * 成交价
	 */
	public int[] resp_nZjcj_s;
	/**
	 * 涨跌幅
	 */
	public int[] resp_nZdf_s;
	/**
	 * 成交量
	 */
	public int[] resp_nCjss_s;
	/**
	 * 成交金额
	 */
	public int[] resp_nCjje_s;
	/**
	 * 成交均价
	 */
	public int[] resp_nCjjj_s;
	/**
	 * 信息地雷标识
	 */
	public String[] resp_sXxgg_s;
	
	/**
	 * 持仓量
	 */
	public int[] resp_nCcl_s;
	/**
	 * 量比
	 */
	public int[] resp_nLb_s;

	/**
	 *
	 * @param flag
	 * @param cmdVersion
     */
	public HQFSProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_FS, cmdVersion, true, false);
	}

}
