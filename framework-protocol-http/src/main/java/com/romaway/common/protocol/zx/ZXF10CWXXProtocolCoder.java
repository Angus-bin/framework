package com.romaway.common.protocol.zx;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXF10CWXXProtocolCoder extends AProtocolCoder<ZXF10CWXXProtocol>{

	@Override
	protected byte[] encode(ZXF10CWXXProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10CWXXProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10CWXXProtocolCoder", "decode >>> result body = " + result);
		
		try {
			JSONObject cwxx = new JSONObject(result);
			if (cwxx.has("news")) {
				protocol.resp_news = result;
				JSONObject news = cwxx.getJSONObject("news");
				protocol.resp_jzrq = news.getString("jzrq");
				protocol.resp_mgsy = news.getString("mgsy");
				protocol.resp_yysr = news.getString("yysr");
				protocol.resp_zysrzzl = news.getString("zysrzzl");
				protocol.resp_yylr = news.getString("yylr");
				protocol.resp_tzsy = news.getString("tzsy");
				protocol.resp_jlr = news.getString("jlr");
				protocol.resp_mgjzc = news.getString("mgjzc");
				protocol.resp_jzcsyl = news.getString("jzcsyl");
				protocol.resp_zczj = news.getString("zczj");
				protocol.resp_fzzj = news.getString("fzzj");
				protocol.resp_gdqy = news.getString("gdqy");
				protocol.resp_mgxjl = news.getString("mgxjl");
				protocol.resp_jyxjlje = news.getString("jyxjlje");
				protocol.resp_tzxjlje = news.getString("tzxjlje");
				protocol.resp_czxjlje = news.getString("czxjlje");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
