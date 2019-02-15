package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * 用户登陆协议
 * @author chenjp
 *
 */
public class LoginProtocolNew extends AProtocol{

	//请求数据
	/**
	 * 手机号码
	 */
	public String req_phoneNum;
	/**
	 * 验证码
	 */
	public String req_secCode;
	/**
	 * 朋友推荐ID或者手机号码
	 */
	public String invitation_code;
	//返回数据
	/**
	 * 认证令牌
	 */
	public String resp_sign_token;

	public String resp_userId;

	public String resp_userName;

	public String resp_phoneNum;

	public String resp_userImage;

	public String resp_avatar;

	public String resp_type;

	public String resp_agentId;

	public String resp_startTime;

	public String resp_endTime;

	public String resp_stockSum;

	public String resp_userInvertType;

	public String resp_WxSubScribed;

	public String resp_realName;

	public String resp_xiaoshou_img;

	public String resp_share_user_id;

	public String resp_errorCode;

	public String resp_errorMsg;

	public int resp_times;


	public LoginProtocolNew(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "account/login";
	}

}
