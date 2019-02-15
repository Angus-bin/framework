package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;

/** 关注/取消关注协议
 * Created by kds on 2016/7/9.
 */
public class AddAttentionProtocol extends AProtocol {

	public String resp_errCode;
	public String resp_errMsg;

	public AddAttentionProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/tg-service/portfolio/gzzt/update/";
	}
}
