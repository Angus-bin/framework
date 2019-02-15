package com.romaway.common.protocol.zx;

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

public class GYShiDianAndNoticeDetailProtocolCoder extends AProtocolCoder<GYShiDianAndNoticeDetailProtocol>{

	@Override
	protected byte[] encode(GYShiDianAndNoticeDetailProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("typeCode", ptl.req_typeCode);
		json.put("id", ptl.req_id);
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void decode(GYShiDianAndNoticeDetailProtocol ptl)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		if (StringUtils.isEmpty(result)) {
			return;
		}
		Logger.d("NetMsgEncodeDecode", "decode >>> result = "+result);
		try {
			JSONObject jsonObject=new JSONObject(result);
			ptl.serverErrCode=Integer.parseInt(jsonObject.optString("errCode"));
			ptl.serverMsg=jsonObject.optString("errMsg");
			ptl.resp_sms=result;
		} catch (JSONException e) {
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}

}
