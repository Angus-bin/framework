package com.romaway.common.protocol.hq.cache;

import java.io.Serializable;

public class HQPXProtocolOldCache extends HQProtocolBaseCache implements Serializable {

	/**
	 * 交易所代码
	 */
	public short req_wMarketID;
	/**
	 * 商品类型
	 */
	public short req_wType;
	/**
	 * 排序方式
	 */
	public byte req_bSort;
	/**
	 * 正向/逆向
	 */
	public byte req_bDirect;
	/**
	 * 开始序号
	 */
	public short req_wFrom;
	/**
	 * 返回个数
	 */
	public short req_wCount;

	// dwVersion>=3,增加版块代码
	/**
	 * 板块代码
	 */
	public String req_pszBKCode;

	// 返回数据
	/**
	 * 排序记录个数
	 */
	public short resp_wCount;

	/**
	 * 交易所代码
	 */
	public short[] resp_wMarketID_s;
	/**
	 * 商品类型
	 */
	public short[] resp_wType_s;
	/**
	 * 代码
	 */
	public String[] resp_pszCode_s;
	/**
	 * 证券名称
	 */
	public String[] resp_pszName_s;
	/**
	 * 昨收
	 */
	public int[] resp_nZrsp_s;
	/**
	 * 昨核算
	 */
	public int[] resp_nZhsj_s;
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
	 * 持仓量
	 */
	public int[] resp_nCcl_s;
	/**
	 * 核算价
	 */
	public int[] resp_nHsj_s;
	/**
	 * 买入(买一)价格
	 */
	public int[] resp_nBjg1_s;
	/**
	 * 卖出(卖一)价格
	 */
	public int[] resp_nSjg1_s;
	/**
	 * 涨跌值
	 */
	public int[] resp_nZd_s;
	/**
	 * 涨跌幅
	 */
	public int[] resp_nZdf_s;
	/**
	 * 震幅
	 */
	public int[] resp_nZf_s;
	/**
	 * 增量
	 */
	public int[] resp_nZl_s;

	/**
	 * 委比
	 */
	public int[] resp_nWb_s;
	/**
	 * 量比
	 */
	public int[] resp_nLb_s;
	/**
	 * 5分钟涨跌幅
	 */
	public int[] resp_n5Min_s;
	/**
	 * 停牌标示
	 */
	public byte[] resp_bSuspended_s;
	// dwVersion>=1,增加换手率、市盈率
	/**
	 * 换手率
	 */
	public int[] resp_nHsl_s;
	/**
	 * 市盈率
	 */
	public int[] resp_nSyl_s;
	/**
	 * 保留
	 */
	public int[] resp_nReserved_s;
	// dwVersion>=2,增加买盘、卖盘
	/**
	 * 买盘
	 */
	public int[] resp_nBP_s;
	/**
	 * 卖盘
	 */
	public int[] resp_nSP_s;
	// dwVersion>=3,增加是否关联股票
	/**
	 * 是否有关联股票
	 */
	public String[] resp_sLinkFlag_s;
	// dwVersion>=4,增加样本数量、样本均价、平均股本、总市值、占比、指数级别标识
	/**
	 * 样本数量
	 */
	public int[] resp_dwsampleNum_s;
	/**
	 * 样本均价
	 */
	public int[] resp_nsampleAvgPrice_s;
	/**
	 * 平均股本
	 */
	public int[] resp_navgStock_s;
	/**
	 * 总市值
	 */
	public int[] resp_nmarketValue_s;
	/**
	 * 占比%
	 */
	public int[] resp_nzb_s;
	/**
	 * 指数级别标识
	 */
	public String[] resp_slevelFlag_s;
	/**
	 * 记录总数
	 */
	public int resp_totalCount = 0;

}
