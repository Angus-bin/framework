package com.romaway.common.protocol.hq;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.commons.log.Logger;

public class HQGGTEDProtocolCoder extends AProtocolCoder<HQGGTEDProtocol>{

	@Override
	protected byte[] encode(HQGGTEDProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQGGTEDProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXDetailProtocolCoder", "decode >>> result body = " + result);
		try {
			protocol.resp_ed = result;
			JSONObject json = new JSONObject(result);
			protocol.resp_amountFlag = json.getString("amountFlag");
			KFloat kInitAmount = new KFloat(json.getInt("initAmount"));
			protocol.resp_initAmount = kInitAmount.toString();
			KFloat kLastAmount = new KFloat(json.getInt("lastAmount"));
			protocol.resp_lastAmount = kLastAmount.toString();
			protocol.resp_mkStatus = json.getString("mkStatus");
			protocol.resp_updateDate = json.getString("updateDate");
			protocol.resp_updateTime = json.getString("updateTime");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
