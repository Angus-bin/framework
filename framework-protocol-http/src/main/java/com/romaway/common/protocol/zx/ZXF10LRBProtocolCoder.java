package com.romaway.common.protocol.zx;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

/**
 * 利润表
 * @author chenjp
 *
 */
public class ZXF10LRBProtocolCoder extends AProtocolCoder<ZXF10LRBProtocol>{

	@Override
	protected byte[] encode(ZXF10LRBProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10LRBProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10LRBProtocolCoder", "decode >>> result body = " + result);
		
		try {
			JSONObject lrb = new JSONObject(result);
			if (lrb.has("news")) {
				protocol.resp_news = result;
				JSONObject news = lrb.getJSONObject("news");
				protocol.resp_FA01 = news.getString("FA01");
				protocol.resp_FA02 = news.getString("FA02");
				protocol.resp_FA03 = news.getString("FA03");
				protocol.resp_FA04 = news.getString("FA04");
				protocol.resp_FA05 = news.getString("FA05");
				protocol.resp_FA06 = news.getString("FA06");
				protocol.resp_FA07 = news.getString("FA07");
				protocol.resp_FA08 = news.getString("FA08");
				protocol.resp_FA09 = news.getString("FA09");
				
				protocol.resp_FB01 = news.getString("FB01");
				protocol.resp_FB02 = news.getString("FB02");
				protocol.resp_FB03 = news.getString("FB03");
				protocol.resp_FB04 = news.getString("FB04");
				
				protocol.resp_FC01 = news.getString("FC01");
				protocol.resp_FC02 = news.getString("FC02");
				
				protocol.resp_FD01 = news.getString("FD01");
				protocol.resp_FD02 = news.getString("FD02");
				protocol.resp_FD03 = news.getString("FD03");
				
				protocol.resp_FE01 = news.getString("FE01");
				protocol.resp_FE02 = news.getString("FE02");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
