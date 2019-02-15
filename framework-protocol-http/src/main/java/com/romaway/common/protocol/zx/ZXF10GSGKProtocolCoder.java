package com.romaway.common.protocol.zx;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXF10GSGKProtocolCoder extends AProtocolCoder<ZXF10GSGKProtocol>{

	@Override
	protected byte[] encode(ZXF10GSGKProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10GSGKProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10GSGKProtocolCoder", "decode >>> result body = " + result);
		try {
			JSONObject json = new JSONObject(result);
			if (json.has("news")) {
				protocol.resp_news = result;
				JSONObject news = json.getJSONObject("news");
				protocol.resp_gsmc = news.getString("gsmc");
				protocol.resp_ssrq = news.getString("ssrq");
				protocol.resp_fxjg = news.getString("fxjg");
				protocol.resp_fxsl = news.getString("fxsl");
				protocol.resp_ssdq = news.getString("ssdq");
				protocol.resp_sshy = news.getString("sshy");
				protocol.resp_zyyw = news.getString("zyyw");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
