package com.romaway.common.protocol.zx;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXDetailProtocolCoder extends AProtocolCoder<ZXDetailProtocol>{

	@Override
	protected byte[] encode(ZXDetailProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXDetailProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXDetailProtocolCoder", "decode >>> result body = " + result);
		try {
			protocol.resp_new = result;
			JSONObject json = new JSONObject(result);
			protocol.resp_time = json.getString("time");
			protocol.resp_title = json.getString("title");
			protocol.resp_titleId = json.getString("titleID");
			protocol.resp_content = json.getString("content");
			protocol.resp_source = json.getString("source");
			protocol.resp_categoryCode = json.getString("categoryCode");
			protocol.resp_categoryName = json.getString("categoryName");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
