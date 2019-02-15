package com.romaway.common.protocol.zx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

/**
 * F10主营构成
 * @author chenjp
 * @version 2015年7月14日 下午2:05:31
 */
public class ZXF10ZYGCProtocolCoder extends AProtocolCoder<ZXF10ZYGCProtocol>{

	@Override
	protected byte[] encode(ZXF10ZYGCProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10ZYGCProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10ZYGCProtocolCoder", "decode >>> result body = " + result);
		try {
			JSONObject json = new JSONObject(result);
			if (json.has("news")) {
				protocol.resp_news = result;
				JSONArray news = json.getJSONArray("news");
				int count = news.length();
				protocol.resp_count = count;
				protocol.resp_ywmc = new String[count];
				protocol.resp_ywsr = new String[count];
				for (int i = 0; i < count; i++) {
					JSONObject item = news.getJSONObject(i);
					protocol.resp_ywmc[i] = item.getString("ywmc");
					protocol.resp_ywsr[i] = item.getString("ywsr");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
