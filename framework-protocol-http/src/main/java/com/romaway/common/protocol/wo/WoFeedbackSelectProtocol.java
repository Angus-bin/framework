package com.romaway.common.protocol.wo;

import org.json.JSONArray;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

public class WoFeedbackSelectProtocol extends AProtocol{
	/**
	 * 设备ID
	 */
	public String req_deviceId;
	/**
	 * 应用ID
	 */
	public String req_appId;
	
	//返回数据
	/**
	 * 查询结果
	 */
	public JSONArray resp_jsonArray;

	public WoFeedbackSelectProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/system/feedback/select/";
	}

}
