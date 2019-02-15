package com.romaway.common.protocol.yj;

import com.romaway.common.protocol.AProtocol;

public class InfoContentCXProtocol extends AProtocol{

	/**
	 * 用户标识符
	 */
	public String req_identifier;
	/**
	 * 时间
	 */
	public String req_time;
	/**
	 * 请求条数
	 */
	public int req_num;
	
	//返回数据
	/**
	 * 信息列表
	 */
	public String smsArray;

	
	public InfoContentCXProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/alarm/smscontent/";
	}

}
