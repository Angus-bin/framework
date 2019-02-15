package com.romaway.common.protocol.yj;

import com.romaway.common.protocol.AProtocol;

public class YuJingSetProtocol extends AProtocol{
	/**
	 * 订单数
	 */
	public int req_count;
	/**
	 * 用户标识类型
	 */
	public String req_identifierType;
	/**
	 * 用户标识符
	 */
	public String req_identifier;
	/**
	 * 推送ID,这里用别名  推送系统注册别名等
	 */
	public String req_pushId;
	/**
	 * 客户端类别   1-android, 2-ios
	 */
	public String req_appType;
	/**
	 * 订单号
	 */
	public String req_orderId[];
	/**
	 * 定制类型
	 */
	public String req_orderType[];
	/**
	 * 预警类型
	 */
	public String req_serviceId[];
	/**
	 * 市场代码
	 */
	public String req_marketId[];
	/**
	 * 商品代码
	 */
	public String req_productType[];
	/**
	 *股票代码 
	 */
	public String req_stockCode[];
	/**
	 * 股票名称
	 */
	public String req_stockName[];
	/**
	 * 价格
	 */
	public String req_price[];
	/**
	 * 涨跌
	 */
	public String req_up[];
	
	//返回数据
	/**
	 * 设置返回数量
	 */
	public int resp_count;
	/**
	 * 设置结果订单
	 */
	public int resp_orderId[];

	public YuJingSetProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/alarm/order/";
	}

}
