package com.romaway.common.protocol.yj;

import com.romaway.common.protocol.AProtocol;

public class InfoCenterCXProtocol extends AProtocol{

	/**
	 * 用户标识符
	 */
	public String req_identifier;
	
	//返回数据
	/**
	 * 信息列表
	 */
	public String smsArray;

	
	public InfoCenterCXProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/alarm/smslist/";
	}

}
