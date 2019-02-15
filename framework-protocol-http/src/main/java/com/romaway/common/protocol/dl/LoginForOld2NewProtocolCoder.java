package com.romaway.common.protocol.dl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * 用户登陆协议解析
 * 
 * @author chenjp
 * 
 */
public class LoginForOld2NewProtocolCoder extends
		AProtocolCoder<LoginForOld2NewProtocol> {

	@Override
	protected byte[] encode(LoginForOld2NewProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("phone_num", ptl.req_phoneNum);
		json.put("device_id", ptl.req_deviceID);
		json.put("password", ptl.req_password);
		json.put("type", ptl.req_type);

		Logger.d("LoginProtocolCoder",
				"encode >>> json.toString() = " + json.toString());
		String[] respHeaders = new String[] { "sign_token" };
		ptl.setResponseHeader(respHeaders);
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void decode(LoginForOld2NewProtocol ptl)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		Logger.d("LoginProtocolCoder", "decode >>> result = " + result);
		HashMap<String, String> respHeaders = ptl.getRespHeaderValue();
		if (respHeaders != null && respHeaders.size() > 0) {
			ptl.resp_sign_token = respHeaders.get("sign_token");
		}
		if (StringUtils.isEmpty(result)) {
			return;
		}

		try {
			JSONObject json = new JSONObject(result);
			ptl.serverErrCode = json.getInt("errCode");
			ptl.serverMsg = json.getString("errMsg");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
