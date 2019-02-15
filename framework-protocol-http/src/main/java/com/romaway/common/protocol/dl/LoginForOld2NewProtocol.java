package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * 
 * @author wanlh 用来从老版本升级到新版本的协议
 */
public class LoginForOld2NewProtocol extends AProtocol {

	// 请求数据
	/**
	 * 手机号码
	 */
	public String req_phoneNum;

	/**
	 * 密码
	 */
	public String req_password;

	/**
	 * 设备号
	 */
	public String req_deviceID;
	/**
	 * 登录类型
	 */
	public String req_type;
	// 返回数据
	/**
	 * 认证令牌
	 */
	public String resp_sign_token;

	public LoginForOld2NewProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/auth/switch/";
	}

}
