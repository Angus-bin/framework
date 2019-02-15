package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

public class SmsRegisterProtocol extends AProtocol{

	//请求数据
	/**
	 * 用户手机号
	 */
	public String req_phoneNum;
	/**
	 * 设备号
	 */
	public String req_deviceID;
	/**
	 * 券商ID
	 */
	public String req_cpid;
	//返回数据
	public String resp_secCode;

	public SmsRegisterProtocol(String msgFlag){
		super(msgFlag, false);
		isJson = true;
		subFunUrl = "/api/auth/regist/";
	}
}
