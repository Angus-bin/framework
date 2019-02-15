package com.romaway.common.protocol.wo;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

public class WoUserInfoUpdateProtocolCoder extends AProtocolCoder<WoUserInfoUpdateProtocol>{

	@Override
	protected byte[] encode(WoUserInfoUpdateProtocol protocol) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("level", protocol.req_level);
		json.put("name", protocol.req_name);
		json.put("fundId", protocol.req_fundId);
		json.put("userId", protocol.req_userId);
		json.put("mobileId", protocol.req_mobileId);
		json.put("avatar", protocol.req_avatar);
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.d("WoUserInfoUpdateProtocolCoder", "encode >>> result = "+ json.toString());
		return result;
	}
	
	@Override
	protected void decode(WoUserInfoUpdateProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());
		String result = r.getString();

		Logger.d("WoUserInfoUpdateProtocolCoder", "decode >>> result body = " + result);
		
		try {
			JSONObject json = new JSONObject(result);
			protocol.resp_message = json.optString("message");
			protocol.resp_status = json.optInt("status");
			JSONObject resultJons = json.getJSONObject("result");
			protocol.resp_returnCode = resultJons.optInt("returnCode");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
