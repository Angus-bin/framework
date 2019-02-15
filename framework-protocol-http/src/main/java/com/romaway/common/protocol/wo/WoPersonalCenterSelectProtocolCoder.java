package com.romaway.common.protocol.wo;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class WoPersonalCenterSelectProtocolCoder extends AProtocolCoder<WoPersonalCenterSelectProtocol>{

	@Override
	protected byte[] encode(WoPersonalCenterSelectProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		/**
		 * 设置请求header
		 */
		HashMap<String, String> sendHeads = new HashMap<String, String>();
		sendHeads.put("phone_num", ptl.req_phoneNum);
		sendHeads.put("device_id", ptl.req_deviceId);
		ptl.setSendHeader(sendHeads);
		
		json.put("bacc", ptl.req_bacc);
		json.put("device_id", ptl.req_deviceId);
		
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
			Logger.d("NetMsgEncodeEncode", "encode >>> result = "+result);
			//result = temp.getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	@Override
	protected void decode(WoPersonalCenterSelectProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		
		String result = r.getString();
		if (StringUtils.isEmpty(result)) {
			return;
		}
		Logger.d("NetMsgEncodeDecode", "decode >>> result = "+result);
		try {
		JSONObject json = new JSONObject(result);
			ptl.serverErrCode = json.getInt("errCode");
			ptl.serverMsg = json.getString("errMsg");
			JSONObject jsonObject = json.getJSONObject("info");
			ptl.resp_clientId = jsonObject.getString("CLIENT_ID");	
			ptl.resp_clientName = jsonObject.getString("CLIENT_NAME");
			ptl.resp_branchNo = jsonObject.getString("BRANCH_NO");
			ptl.resp_managerId = jsonObject.getString("MANAGER_ID");
			ptl.resp_managerName = jsonObject.getString("MANAGER_NAME");
			ptl.resp_managerType = jsonObject.getString("MANAGER_TYPE");
			ptl.resp_managerTel = jsonObject.getString("MANAGER_TEL");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
