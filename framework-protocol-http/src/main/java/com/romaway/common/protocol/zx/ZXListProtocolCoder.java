package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZXListProtocolCoder extends AProtocolCoder<ZXListProtocol>{

	@Override
	protected byte[] encode(ZXListProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXListProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXListProtocolCoder", "decode >>> result body = " + result);
		
		try {
			JSONObject json = new JSONObject(result);
			if (json.has("news")) {
				protocol.resp_news = result;
				JSONArray newsList = json.getJSONArray("news");
				int count = newsList.length();
				protocol.resp_count = count;
				protocol.resp_time = new String[count];
				protocol.resp_title = new String[count];
				protocol.resp_titleId = new String[count];
				for (int i = 0; i < count; i++) {
					JSONObject mNew = newsList.getJSONObject(i);
					protocol.resp_time[i] = mNew.optString("time");
					protocol.resp_title[i] = mNew.optString("title");
					protocol.resp_titleId[i] = mNew.optString("titleID");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
