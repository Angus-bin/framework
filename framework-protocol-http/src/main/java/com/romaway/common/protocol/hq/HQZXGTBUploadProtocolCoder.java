package com.romaway.common.protocol.hq;

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

public class HQZXGTBUploadProtocolCoder extends AProtocolCoder<HQZXGTBUploadProtocol>{

	@Override
	protected byte[] encode(HQZXGTBUploadProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
//		json.put("key_type", ptl.req_keyType);
//		json.put("key_value", ptl.req_keyValue);
		json.put("favors", ptl.req_favors);
		json.put("group", ptl.req_group);
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
	protected void decode(HQZXGTBUploadProtocol ptl) throws ProtocolParserException {
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
