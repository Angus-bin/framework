package com.romaway.common.protocol.zx;

import java.io.StreamCorruptedException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXF10JJCGProtocolCoder extends AProtocolCoder<ZXF10JJCGProtocol>{

	@Override
	protected byte[] encode(ZXF10JJCGProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10JJCGProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10JJCGProtocolCoder", "decode >>> result body = " + result);
		
		try {
			JSONObject jjcg = new JSONObject(result);
			if (jjcg.has("news")) {
				protocol.resp_news = result;
				JSONArray news = jjcg.getJSONArray("news");
				int count = news.length();
				protocol.resp_count = count;
				protocol.resp_jjmc = new String[count];
				protocol.resp_zgbl = new String[count];
				protocol.resp_cgbd = new String[count];
				for (int i = 0; i < count; i++) {
					JSONObject item = news.getJSONObject(i);
					protocol.resp_jjmc[i] = item.getString("jjmc");
					protocol.resp_cgbd[i] = item.getString("cgbd");
					protocol.resp_zgbl[i] = item.getString("zgbl");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
