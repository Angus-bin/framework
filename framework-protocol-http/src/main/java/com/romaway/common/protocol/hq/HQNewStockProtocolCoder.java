package com.romaway.common.protocol.hq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

public class HQNewStockProtocolCoder extends AProtocolCoder<HQNewStockProtocol> {

	@Override
	protected byte[] encode(HQNewStockProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQNewStockProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		try {
			JSONArray array = new JSONArray(result);
			protocol.req_wCount = array.length();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj_stock = array.getJSONObject(i);
				protocol.stock_code = obj_stock.getString("stock_code");
				protocol.stock_mark = obj_stock.getString("stock_mark");
				protocol.stock_market = obj_stock.getString("stock_market");
				protocol.stock_name = obj_stock.getString("stock_name");
				protocol.stock_pinyin = obj_stock.getString("stock_pinyin");
				protocol.stock_type = obj_stock.getString("stock_type");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
