package com.romaway.common.protocol.hq.cache;

public class HQZXProtocolCache extends HQProtocolBaseCache{

	private static final long serialVersionUID = 2649465927726723455L;
	
	/**
	 * 请求的商品个数
	 */
	public short req_wCount;
	/**
	 * 代码列表，','分隔的字符串，以'\0'结尾
	 */
	public String req_pszCodes;
	/**
	 * 排序方式，见enumPXType
	 */
	public byte req_bSort;
	/**
	 * 排序方式，见enumPXType
	 */
	public byte req_bDirect;
	/**
	 * 开始序号
	 */
	public short req_wFrom;
	/**
	 * 自选股票市场代码列表pszCodes[0]对应的市场代码列表，“,”分隔的字符串，以'\0'结尾
	 */
	public String req_marketList;

	// 返回数据
	
	/*开始序号*/
	public short resp_wFrom;
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
	 * 名称
	 */
	public String[] resp_pszName_s;
	/**
	 * 特殊股票标识
	 * HK 港股
	 * HGT 港股通
	 * R 融资融券
	 */
	public String[] resp_pszMark_s;
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
	 * 涨停价
	 */
	public int[] resp_nLimUp_s;
	/**
	 * 跌停价
	 */
	public int[] resp_nLimDown_s;

	/**
	 * 买(1)价格
	 */
	public int[] resp_nBjg1_s;
	/**
	 * 买(1)数量
	 */
	public int[] resp_nBss1_s;
	/**
	 * 买(2)价格
	 */
	public int[] resp_nBjg2_s;
	/**
	 * 买(2)数量
	 */
	public int[] resp_nBss2_s;
	/**
	 * 买(3)价格
	 */
	public int[] resp_nBjg3_s;
	/**
	 * 买3)数量
	 */
	public int[] resp_nBss3_s;
	/**
	 * 买(4)价格
	 */
	public int[] resp_nBjg4_s;
	/**
	 * 买(4)数量
	 */
	public int[] resp_nBss4_s;
	/**
	 * 买(5)价格
	 */
	public int[] resp_nBjg5_s;
	/**
	 * 买(5)数量
	 */
	public int[] resp_nBss5_s;

	/**
	 * 卖(1)价格
	 */
	public int[] resp_nSjg1_s;
	/**
	 * 卖(1)数量
	 */
	public int[] resp_nSss1_s;
	/**
	 * 卖(2)价格
	 */
	public int[] resp_nSjg2_s;
	/**
	 * 卖(2)数量
	 */
	public int[] resp_nSss2_s;
	/**
	 * 卖(3)价格
	 */
	public int[] resp_nSjg3_s;
	/**
	 * 卖(3)数量
	 */
	public int[] resp_nSss3_s;
	/**
	 * 卖(4)价格
	 */
	public int[] resp_nSjg4_s;
	/**
	 * 卖(4)数量
	 */
	public int[] resp_nSss4_s;
	/**
	 * 卖(5)价格
	 */
	public int[] resp_nSjg5_s;
	/**
	 * 卖(5)数量
	 */
	public int[] resp_nSss5_s;

	/**
	 * 涨跌值
	 */
	public int[] resp_nZd_s;
	/**
	 * 涨跌幅
	 */
	public int[] resp_nZdf_s;
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
	 * 成交均价
	 */
	public int[] resp_nCjjj_s;
	/**
	 * 停牌标示 1为真 0为假
	 */
	public byte[] resp_bSuspended_s;
	/**
	 * //换手率
	 */
	public int[] resp_nHsl_s;
	/**
	 * //市盈率
	 */
	public int[] resp_nSyl_s;
	/**
	 * 买盘
	 */
	public int[] resp_nBP_s;
	/**
	 * 卖盘
	 */
	public int[] resp_nSP_s;
	/**
	 * 判断是否有关联股票,如B股/权证/债券/H股等 0 表示没有 1表示有
	 */
	public String[] resp_sLinkFlag_s;
	/**
	 * 中证债券估值价格(建议以后不再使用该值
	 */
	public int[] resp_nZZZQValPrice;
	/**
	 * 中证债券估值价格 增加中证债券估值价格（太平洋证券提出要小数点4位的值，因此多加一个string的值）
	 */
	public String[] resp_sZZZQValPrice;

	// version>=5
	/** 所属板块代码 **/
	public String[] resp_pszBKCode_s;
	/** 所属板块名称 **/
	public String[] resp_pszBKName_s;
	/** 所属板块涨跌幅 **/
	public int[] resp_nBKzdf_s;

}
