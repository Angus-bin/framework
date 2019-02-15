package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

public class WoFeedbackAddProtocol extends AProtocol{
	/**
	 * 设备ID
	 */
	public String req_userID;
	/**
	 * 反馈内容
	 */
	public String req_feedback;

	public String resp_errCode;
	public String resp_errMsg;


	public WoFeedbackAddProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "account/feedback";
	}

}
