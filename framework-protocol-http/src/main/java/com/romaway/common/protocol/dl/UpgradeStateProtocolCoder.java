package com.romaway.common.protocol.dl;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class UpgradeStateProtocolCoder extends AProtocolCoder<UpgradeStateProtocol>{

	@Override
	protected byte[] encode(UpgradeStateProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}
	
	@Override
	protected void decode(UpgradeStateProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

		String result = r.getString();
		Logger.d("UpgradeStateProtocolCoder", "decode >>> result = " + result);
		try {
			JSONObject json = new JSONObject(result);
			protocol.resp_flag = json.optString("flag");
			protocol.resp_type = json.optString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
