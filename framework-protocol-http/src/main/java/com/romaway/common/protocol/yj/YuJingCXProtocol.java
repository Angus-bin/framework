package com.romaway.common.protocol.yj;

import com.romaway.common.protocol.AProtocol;

public class YuJingCXProtocol extends AProtocol{
	/**
	 * 用户标识类型
	 */
	public String req_identifierType;
	/**
	 * 用户标识符
	 */
	public String req_identifier;
	/**
	 * 订单号
	 */
	public String req_orderId;
	/**
	 * 预警类型 8201-个股到价，8202-个股涨跌幅，8207-个股涨跌停 8310-订阅
	 */
	public String req_serviceId;
	
	//返回数据
	/**
	 * 查询结果数量
	 */
	public int resp_count;
	/**
	 * 订单号
	 */
	public String resp_orderId[];
	/**
	 * 预警类型 8201-个股到价，8202-个股涨跌幅，8207-个股涨跌停 8310-订阅
	 */
	public String resp_serviceId[];
	/**
	 * 商品代码
	 */
	public String resp_productType[];
	/**
	 * 市场代码
	 */
	public String resp_marketId[];
	/**
	 *  股票代码
	 */
	public String resp_stockCode[];
	/**
	 * 股票名称
	 */
	public String resp_stockName[];	
	/**
	 *价格
	 */
	public String resp_price[];
	/**
	 * 涨跌 
	 */
	public String resp_up[];
	
	public YuJingCXProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/alarm/query/";
	}

}
