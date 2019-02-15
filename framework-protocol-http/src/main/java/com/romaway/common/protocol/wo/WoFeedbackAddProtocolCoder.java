package com.romaway.common.protocol.wo;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class WoFeedbackAddProtocolCoder extends AProtocolCoder<WoFeedbackAddProtocol>{

	@Override
	protected byte[] encode(WoFeedbackAddProtocol ptl) {
		/**
		 * 设置请求body
		 */
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}
	
	@Override
	protected void decode(WoFeedbackAddProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		Logger.d("WoFeedbackAddProtocolCoder", "decode >>> result = " + result);

		try {
			if (!TextUtils.isEmpty(result)) {
				BaseJSONObject jsonObject = new BaseJSONObject(result);
				if (jsonObject.has("xy")) {
					String str = jsonObject.getString("xy").toUpperCase();
					DES3.setIv(jsonObject.getString("iv"));
					Logger.d("WoFeedbackAddProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
					if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
						BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
						ptl.resp_errCode = jsonObject2.getString("error").trim();
						ptl.resp_errMsg = jsonObject2.getString("msg").trim();
					}
				} else {
					ptl.resp_errCode = jsonObject.getString("error").trim();
					ptl.resp_errMsg = jsonObject.getString("msg").trim();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
