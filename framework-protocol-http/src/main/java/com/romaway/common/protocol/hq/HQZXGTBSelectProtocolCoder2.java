package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HQZXGTBSelectProtocolCoder2 extends AProtocolCoder<HQZXGTBSelectProtocol2>{

	@Override
	protected byte[] encode(HQZXGTBSelectProtocol2 ptl) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}
	
	@Override
	protected void decode(HQZXGTBSelectProtocol2 ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData() == null ? new byte[0] : ptl.getReceiveData());
		String result = r.getString();

		Logger.d("HQZXGTBSelectProtocolCoder2", "decode >>> result body = " + result);
		try {
			JSONObject json = new JSONObject(result);
			ptl.serverErrCode = json.getInt("error");
			ptl.serverMsg = json.getString("msg");
			ptl.resp_favors = json.getString("data");
			Logger.d("HQZXGTBSelectProtocolCoder2", "decode >>> resp_favors = " + ptl.resp_favors);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
