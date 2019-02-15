package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;

public class HQGgqqProtocol extends AProtocol{
	/**
	 * 排序记录个数
	 */
	public int resp_wCount;
	/**
	 * 涨跌幅
	 */
	public String[] change_percent;
	/**
	 * 涨跌值
	 */
	public String[] change_value;
	/**
	 * 现价
	 */
	public String[] cur_price;
	/**
	 * 到期日
	 */
	public String[][] resp_date;
	/**
	 * 剩余天数
	 */
	public String[][] resp_days;
	/**
	 * 证券代码
	 */
	public String[] stock_code;
	/**
	 * 市场代码
	 */
	public String[] stock_market;
	/**
	 * 证券名称
	 */
	public String[] stock_name;
	/**
	 * 昨收
	 */
	public String[] resp_last_close_price;
	/**
	 * 总量
	 */
	public String[] resp_total_volume;
	/**
	 * 金额
	 */
	public String[] resp_total_amount;
	/**
	 * 今开
	 */
	public String[] resp_open_price;
	/**
	 * 最高
	 */
	public String[] resp_high_price;
	/**
	 * 最低
	 */
	public String[] resp_low_price;
	/**
	 * 市盈率
	 */
	public String[] resp_SYL;
	/**
	 * 换手
	 */
	public String[] resp_change_hands;
	
	public HQGgqqProtocol(String flag) {
		super(flag, false);
		// TODO Auto-generated constructor stub
		isJson = true;
		subFunUrl = "/api/quote/t_list";
	}
}
