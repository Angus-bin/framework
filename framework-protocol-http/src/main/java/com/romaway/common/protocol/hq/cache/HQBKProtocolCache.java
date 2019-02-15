package com.romaway.common.protocol.hq.cache;

/**
 * 板块行情分析协议缓存
 * @author wala
 *
 */
public class HQBKProtocolCache extends HQProtocolBaseCache {

	private static final long serialVersionUID = 5100745440462782039L;

	/**
	 * 请求数据
	 */
	/**
	 * 交易所代码
	 */
	public short req_wMarketID;

	/**
	 * 商品类型，该值是由服务器下发,主要是证券会概念板块、地区板块、行业板块对应的值
	 */
	public short req_wType;

	/**
	 * 排序方式，见enumPXType
	 */
	public byte req_bSort;

	/**
	 * 正向/逆向： 0--从小到大， 1--从大到小
	 */
	public byte req_bDirect;

	/**
	 * 开始序号
	 */
	public short req_wFrom;

	/**
	 * 每组最大返回的股票数据量
	 */
	public short req_wCount;

	/**
	 * 板块代码
	 */
	public String req_pszBKCode;

	/**
	 * 响应数据
	 */
	/**
	 * 开始序号
	 */
	public short resp_wFrom;
	/**
	 * 数据集数量
	 */
	public short resp_wCount;
	/*
	 * 交易所代码
	 */
	public short[] resp_wMarketID_s;
	/*
	 * 商品类型
	 */
	public short[] resp_wType_s;
	/*
	 * 板块涨跌幅
	 */
	public int[] resp_nBKzdf_s;
	/*
	 * 板块代码
	 */
	public String[] resp_pszBKCode_s;
	/*
	 * 板块名称
	 */
	public String[] resp_pszBKName_s;
	/*
	 * 领涨股票代码
	 */
	public String[] resp_nLzStockCode_s;
	/*
	 * 领涨股票代码名称
	 */
	public String[] resp_nLZStockName_s;
	/*
	 * 领涨股涨跌幅
	 */
	public int[] resp_nFirstZdf_s;
	/*
	 * 领涨股最新价
	 */
	public int[] resp_nFirstPrice_s;
	/*
	 * 记录总数
	 */
	public short resp_wTotalCount;
	
}
