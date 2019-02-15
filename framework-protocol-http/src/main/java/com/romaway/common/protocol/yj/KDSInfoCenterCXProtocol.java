package com.romaway.common.protocol.yj;

import com.romaway.common.protocol.AProtocol;

public class KDSInfoCenterCXProtocol extends AProtocol{

	/**
	 * 用户标识符
	 */
	public String req_identifier;
	/**
	 * 交易账号标识，针对需要登录交易之类的请求，比如中签，只有手机号是不够的
	 */
	public String req_tradeIdentifier;
	/**
	 * 1-预警提醒，2-系统公告，3-新股申购，4-活动推广，5-要闻资讯，6-个股公告,0-综合列表查询
	 */
	public String req_typeCode;

	//返回数据
	/**
	 * 信息列表
	 */
	public String rep_smsArray;


	public KDSInfoCenterCXProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/msg-service/get_last_msg";
	}

}
