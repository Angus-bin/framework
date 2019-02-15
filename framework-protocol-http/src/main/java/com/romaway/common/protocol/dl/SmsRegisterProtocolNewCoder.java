package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 获取验证码
 * @author chenjp
 *
 */
public class SmsRegisterProtocolNewCoder extends
		AProtocolCoder<SmsRegisterProtocolNew> {

	@Override
	protected byte[] encode(SmsRegisterProtocolNew ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("phone_num", ptl.req_phoneNum);
		json.put("device_id", ptl.req_deviceID);
		json.put("cpid", ptl.req_cpid);

		Logger.d("SmsRegisterProtocolCoder",
				"encode >>> json.toString() = " + json.toString());

		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void decode(SmsRegisterProtocolNew ptl)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData() == null ? new byte[0] : ptl.getReceiveData());

		String result = r.getString();
		Logger.d("SmsRegisterProtocolCoder", "decode >>> result = " + result);
		if (StringUtils.isEmpty(result)) {
			return;
		}
		
		try {
			JSONObject json = new JSONObject(result);
			ptl.serverErrCodeStr = json.optString("errCode");
			ptl.serverMsg = json.optString("errMsg");
			if (json.has("sec_code")) {
				ptl.resp_secCode = json.optString("sec_code");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//ptl.serverErrCode = -1;
			//ptl.serverMsg = "网络请求超时";
		}

	}

}
