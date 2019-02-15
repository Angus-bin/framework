package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
/**
 * 查询绑定的资金账号
 * @author dell
 *
 */
public class HQZXGTBSelectBindProtocol extends AProtocol{

	/**
	 * 设备ID
	 */
	public String req_deviceId;
	
	//返回数据
	
	/**
	 * 绑定资金账号
	 */
	public String resp_bacc;
	/**
	 * 手机号码
	 */
	public String resp_phoneNum;

	public HQZXGTBSelectBindProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/system/bacc/select/";
	}
}
