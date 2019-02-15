package com.romaway.common.protocol.yj;

import com.romaway.common.protocol.AProtocol;

public class InfoContentClearProtocol extends AProtocol{

	/**
	 * 用户标识符
	 */
	public String req_identifier;
	/**
	 * 1 股票预警，其他预警，（暂时只有股票预警类型）
	 */
	public String req_typeCode;
	/**
	 * 当前时间
	 */
	public String req_time;

	
	public InfoContentClearProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/alarm/clear/";
	}

}
