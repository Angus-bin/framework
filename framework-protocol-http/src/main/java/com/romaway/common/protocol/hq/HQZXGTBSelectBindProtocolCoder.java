package com.romaway.common.protocol.hq;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class HQZXGTBSelectBindProtocolCoder extends AProtocolCoder<HQZXGTBSelectBindProtocol>{

	@Override
	protected byte[] encode(HQZXGTBSelectBindProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("device_id", ptl.req_deviceId);
		Logger.d("NetMsgEncodeDecode", "encode >>> json.toString() = "+json.toString());
		
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
			//result = temp.getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	@Override
	protected void decode(HQZXGTBSelectBindProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		
		String result = r.getString();
		if (StringUtils.isEmpty(result)) {
			return;
		}
		Logger.d("NetMsgEncodeDecode", "decode >>> result = "+result);
		try {
			JSONObject json = new JSONObject(result);
			ptl.resp_bacc = json.getString("bacc");
			ptl.resp_phoneNum = json.getString("phone_num");
			ptl.serverErrCode = json.getInt("errCode");
			ptl.serverMsg = json.getString("errMsg");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}

}
