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

public class GYShiDianAndNoticeListProtocolCoder extends AProtocolCoder<GYShiDianAndNoticeListProtocol>{

	@Override
	protected byte[] encode(GYShiDianAndNoticeListProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("typeCode", ptl.req_typeCode);
		json.put("begin", ptl.req_begin);
		json.put("start", ptl.req_start);
		json.put("num", ptl.req_num);
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void decode(GYShiDianAndNoticeListProtocol ptl)
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
			ptl.resp_smsArray=result;
		} catch (JSONException e) {
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}

}
