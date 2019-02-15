package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * 用户登陆协议
 * @author chenjp
 *
 */
public class LoginProtocol extends AProtocol{
	
	//请求数据
	/**
	 * 手机号码
	 */
	public String req_phoneNum;
	/**
	 * 设备号
	 */
	public String req_deviceID;
	/**
	 * 验证码
	 */
	public String req_secCode;
	/**
	 * 登录类型
	 */
	public String req_type;
	/**
	 * 朋友推荐ID或者手机号码
	 */
	public String invitation_code;
	//返回数据
	/**
	 * 认证令牌
	 */
	public String resp_sign_token;

	public LoginProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/auth/login/";
	}

}
