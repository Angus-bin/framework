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

public class WoFeedbackSelectProtocolCoder extends AProtocolCoder<WoFeedbackSelectProtocol>{

	@Override
	protected byte[] encode(WoFeedbackSelectProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("device_id", ptl.req_deviceId);
		json.put("appid", ptl.req_appId);
		
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
	protected void decode(WoFeedbackSelectProtocol ptl) throws ProtocolParserException {
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
			ptl.resp_jsonArray = json.getJSONArray("feedback");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
