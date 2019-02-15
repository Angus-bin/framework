package com.romaway.common.protocol.zx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class ZXF10GDYJProtocolCoder extends AProtocolCoder<ZXF10GDYJProtocol>{

	@Override
	protected byte[] encode(ZXF10GDYJProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10GDYJProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10GDYJProtocolCoder", "decode >>> result body = " + result);
		
		try {
			JSONObject gdyj = new JSONObject(result);
			if (gdyj.has("news")) {
				protocol.resp_news = result;
				JSONObject news = gdyj.getJSONObject("news");
				//前十大股东
				JSONArray gd = news.getJSONArray("1");
				int count1 = gd.length();
				protocol.resp_count1 = count1;
				protocol.resp_gdpm1 = new String[count1];
				protocol.resp_gdmc1 = new String[count1];
				protocol.resp_zgbbl1 = new String[count1];
				protocol.resp_agbl1 = new String[count1];
				protocol.resp_cgbd1 = new String[count1];
				for (int i = 0; i < count1; i++) {
					JSONObject item = gd.getJSONObject(i);
					protocol.resp_gdpm1[i] = item.getString("gdpm");
					protocol.resp_gdmc1[i] = item.getString("gdmc");
					protocol.resp_zgbbl1[i] = item.getString("zgbbl");
					protocol.resp_agbl1[i] = item.getString("agbl");
					protocol.resp_cgbd1[i] = item.getString("cgbd");
				}
				//前十大流通股东
				JSONArray ltgd = news.getJSONArray("2");
				int count2 = ltgd.length();
				protocol.resp_count2 = count2;
				protocol.resp_gdpm2 = new String[count2];
				protocol.resp_gdmc2 = new String[count2];
				protocol.resp_zgbbl2 = new String[count2];
				protocol.resp_agbl2 = new String[count2];
				protocol.resp_cgbd2 = new String[count2];
				for (int i = 0; i < count2; i++) {
					JSONObject item = ltgd.getJSONObject(i);
					protocol.resp_gdpm2[i] = item.getString("gdpm");
					protocol.resp_gdmc2[i] = item.getString("gdmc");
					protocol.resp_zgbbl2[i] = item.getString("zgbbl");
					protocol.resp_agbl2[i] = item.getString("agbl");
					protocol.resp_cgbd2[i] = item.getString("cgbd");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
