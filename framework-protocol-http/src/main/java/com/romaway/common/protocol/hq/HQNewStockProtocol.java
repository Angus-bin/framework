package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;

public class HQNewStockProtocol extends AProtocol {
	
	public String stock_code;
	public String stock_mark;
	public String stock_market;
	public String stock_name;
	public String stock_pinyin;
	public String stock_type;
	
	public int req_wCount;

	public HQNewStockProtocol(String flag) {
		super(flag, false);
		// TODO Auto-generated constructor stub
		isJson = true;
	}

}
