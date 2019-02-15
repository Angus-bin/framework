package com.romaway.common.protocol.yj;

import com.romaway.common.protocol.AProtocol;

public class KDSInfoContentCXProtocol extends AProtocol{

	/**
	 * 用户标识符
	 */
	public String req_identifier;
	/**
	 * 消息类型
	 */
	public String req_typeCode;
	/**
	 * 请求消息开始位置
	 */
	public String req_start;
	/**
	 * 请求条数
	 */
	public String req_num;

	//返回数据
	/**
	 * 信息列表
	 */
	public String rep_smsArray;


	public KDSInfoContentCXProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/msg-service/get_num_push_sms";
	}

}
