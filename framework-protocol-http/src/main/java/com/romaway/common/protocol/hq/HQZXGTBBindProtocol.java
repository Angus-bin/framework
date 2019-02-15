package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
/**
 * 绑定资金账号
 * @author dell
 *
 */
public class HQZXGTBBindProtocol extends AProtocol{

	/**
	 * 设备ID
	 */
	public String req_deviceId;
	/**
	 * 资金账号
	 */
	public String req_bacc;
	
	//返回数据

	public HQZXGTBBindProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/system/bacc/update/";
	}
}
