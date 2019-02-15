package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 期货分时综合协议
 * 
 * @author qinyn
 * 修改日志：
 * 1. 2014.2.19 期货分时主功能号由行情ProtocolConstant.MF_HQ改为期货ProtocolConstant.MF_HQ_FUTURES
 */
public class HQQHFSZHProtocol extends AProtocol
{
	public final static short HQ_QHFSZH = 17;

	// 请求数据
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

	/** 分时数据日期 */
	public int resp_dwDate;

	/** 采样点总数 */
	public short resp_wCYDZS;

	/** 昨收 */
	public int resp_nZrsp;

	/** 昨核算 **/
	public int resp_nZhsj;

	/** 今日开盘 */
	public int resp_nJrkp;

	/** 最高成交 */
	public int resp_nZgcj;

	/** 最低成交 */
	public int resp_nZdcj;

	/** 最近成交 */
	public int resp_nZjcj;

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

	/** 成交均价 **/
	public int resp_nCjjj;

	/** 持仓量 **/
	public int resp_nCcl;

	/** 仓差 **/
	public int resp_nCc;

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
	/** 分笔仓差（增仓） **/
	public int[] resp_nFBCc_s;
	/** 成交性质 **/
	public byte[] resp_nFBCjxj_s;

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
	public String[] resp_sXxgg_s;
	/** 持仓量 */
	public int[] resp_nCcl_s;
	/** 量比 */
	public int[] resp_nLb_s;

	public HQQHFSZHProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ/*MF_HQ_FUTURES*/, HQ_QHFSZH, cmdVersion, true, false);
	}

}
