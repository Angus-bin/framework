package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

public class WoUserInfoSelectProtocol extends AProtocol{

	//请求
	/**
	 * 用户ID
	 */
	public String req_userId;
	//响应
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

	public int resp_times;

	public int resp_vip;

	public int resp_end_at_show;

	public String resp_jinpai;

	public String resp_zhangting;

	public String resp_question_user_type;

	public String resp_jinpaigupiaochi;

	public String resp_zhangtingdianjing;

	public String resp_weixin;

	public String resp_unlook_message_num;

	public String resp_province;

	public String resp_city;

	public String resp_sex;

	public String resp_user_invite_type_sel;

	public String resp_old;

	public String resp_ziben;

	public String resp_errorCode;

	public String resp_errorMsg;

	public boolean isCatch = false;
	
	public WoUserInfoSelectProtocol(String flag){
		super(flag, false);
		isJson = true;
		subFunUrl = "account/getUserInfo";
	}
}
