package com.romaway.common.protocol.zx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXNewStockProtocolCoder extends AProtocolCoder<ZXNewStockProtocol>{

	@Override
	protected byte[] encode(ZXNewStockProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXNewStockProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXNewStockProtocolCoder", "decode >>> result body = " + result);
		try {
			JSONObject json = new JSONObject(result);
			protocol.resp_code = json.getInt("code");
			protocol.resp_message = json.getString("message");
			if (json.has("stock")) {
				JSONArray newsList = json.getJSONArray("stock");
				int count = newsList.length();
				protocol.resp_count = count;
				for (int i = 0; i < count; i++) {
					JSONObject news = newsList.getJSONObject(i);
					protocol.resp_jysdm[i] = news.getString("jysdm");
					protocol.resp_zqdm[i] = news.getString("zqdm");
					protocol.resp_zqmc[i] = news.getString("zqmc");
					protocol.resp_sgdm[i] = news.getString("sgdm");
					protocol.resp_fxrq[i] = news.getString("fxrq");
					protocol.resp_zfxsl[i] = news.getString("zfxsl");
					protocol.resp_fxjg[i] = news.getString("fxjg");
					protocol.resp_sgxe[i] = news.getString("sgxe");
					protocol.resp_zqgbrq[i] = news.getString("zqgbrq");
					protocol.resp_wsfxsl[i] = news.getString("wsfxsl");
					protocol.resp_fxsyl[i] = news.getString("fxsyl");
					protocol.resp_zjjdrq[i] = news.getString("zjjdrq");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
