package com.romaway.common.protocol.zx;

import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXF10GBQKProtocolCoder extends AProtocolCoder<ZXF10GBQKProtocol>{

	@Override
	protected byte[] encode(ZXF10GBQKProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10GBQKProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10GBQKProtocolCoder", "decode >>> result body = " + result);
		try {
			protocol.resp_news = result;
			JSONObject json = new JSONObject(result);
			JSONObject news = json.getJSONObject("news");
			protocol.resp_zgb = news.getString("zgb");
			protocol.resp_ltag = news.getString("ltag");
			protocol.resp_gdrs = news.getString("gdrs");
			protocol.resp_rjcg = news.getString("rjcg");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
