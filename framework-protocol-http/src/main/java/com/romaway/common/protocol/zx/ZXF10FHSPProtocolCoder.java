package com.romaway.common.protocol.zx;

import org.json.JSONArray;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

/**
 * F10 分红送配
 * @author chenjp
 * @version 2015年7月14日 下午2:18:33
 */
public class ZXF10FHSPProtocolCoder extends AProtocolCoder<ZXF10FHSPProtocol>{

	@Override
	protected byte[] encode(ZXF10FHSPProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10FHSPProtocol protocol)
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
				protocol.resp_fhfa = new String[count];
				protocol.resp_fhnd = new String[count];
				protocol.resp_cqrq = new String[count];
				for (int i = 0; i < count; i++) {
					JSONObject item = news.getJSONObject(i);
					protocol.resp_fhfa[i] = item.getString("fhfa");
					protocol.resp_fhnd[i] = item.getString("fhnd");
					protocol.resp_cqrq[i] = item.getString("cqrq");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
