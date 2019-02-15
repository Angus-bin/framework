package com.romaway.common.protocol.zx;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXIPOProtocolCoder extends AProtocolCoder<ZXIPOProtocol>{

	@Override
	protected byte[] encode(ZXIPOProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXIPOProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXIPOProtocolCoder", "decode >>> result body = " + result);
		protocol.resp_ipo= result;
	}

}
