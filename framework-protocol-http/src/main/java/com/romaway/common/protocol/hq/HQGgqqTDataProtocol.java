package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;

public class HQGgqqTDataProtocol extends AProtocol {

	public String[] ExercisePrice;
	public String[] C_TotalLongPosition ;
	public String[] C_buy_1_price;
	public String[] C_buy_1_volume;
	public String[] C_change_percent;
	public String[] C_cur_price;
	public String[] C_sell_1_price;
	public String[] C_sell_1_volume;
	public String[] C_total_volume;
	public String[] C_last_close_price;
	public String[] P_TotalLongPosition ;
	public String[] P_buy_1_price;
	public String[] P_buy_1_volume;
	public String[] P_change_percent;
	public String[] P_cur_price;
	public String[] P_sell_1_price;
	public String[] P_sell_1_volume;
	public String[] P_total_volume;
	public String[] P_last_close_price;
	public String[][] P_stock_code;
	public String[][] C_stock_code;
	public String[][] P_stock_market;
	public String[][] C_stock_market;
	public String stock_code;
	public String stock_market;
	public String stock_name;
	public String change_percent;
	public String cur_price;
	
	public int req_wCount;
	public int ExpireDate_wCount;
	public int ExpireDate_index;
	
	public HQGgqqTDataProtocol(String flag) {
		super(flag, false);
		isJson = true;
	}

}
