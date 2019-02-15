package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

/**
 * 用户信息上传接口
 * @author chenjp
 *
 */
public class WoUserInfoUpdateProtocol extends AProtocol{
	
	//请求
	/**
	 * 参加股神大赛的标记
	 */
	public String req_level;
	/**
	 * 用户昵称
	 */
	public String req_name;
	/**
	 * 资金账号
	 */
	public String req_fundId;
	/**
	 * 用户ID
	 */
	public String req_userId;
	/**
	 * 用户手机
	 */
	public String req_mobileId;
	/**
	 * 头像
	 */
	public String req_avatar;
	//响应
	/**
	 * 返回信息
	 */
	public String resp_message;
	/**
	 * 执行状态
	 * 0-成功
	 * 1-失败
	 */
	public int resp_status;
	/**
	 * 结果
	 * 0-成功
	 * -1-失败
	 */
	public int resp_returnCode; 
	
	public WoUserInfoUpdateProtocol(String flag){
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/system/user/info/update/";
	}
}
