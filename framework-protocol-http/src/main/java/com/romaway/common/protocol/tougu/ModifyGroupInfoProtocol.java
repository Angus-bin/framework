package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by chenjp on 2016/7/9.
 */
public class ModifyGroupInfoProtocol extends AProtocol {

	public String req_userID;
	public String req_groupId;
	public String req_groupHide;
	public String req_groupName;
	public String req_addvice;

	public String resp_errCode;
	public String resp_errMsg;

	public ModifyGroupInfoProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/tg-service/portfolio/zhxx/update/";
	}
}
