package com.romaway.common.protocol.hq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class HQGgqqProtocolCoder extends AProtocolCoder<HQGgqqProtocol>{

	private int index;
	
	@Override
	protected byte[] encode(HQGgqqProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQGgqqProtocol protocol)
			throws ProtocolParserException {
		// TODO Auto-generated method stub
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		Logger.d("result_hrb", result);		
		try {
			JSONArray array = new JSONArray(result);
			int count = array.length();
			protocol.resp_wCount = count;
			protocol.resp_SYL = new String[count];
			protocol.resp_change_hands = new String[count];
			protocol.change_percent = new String[count];
			protocol.change_value = new String[count];
			protocol.cur_price = new String[count];
			protocol.resp_date = new String[count][];
			protocol.resp_days = new String[count][];
			protocol.resp_high_price = new String[count];
			protocol.resp_last_close_price = new String[count];
			protocol.resp_low_price = new String[count];
			protocol.resp_open_price = new String[count];
			protocol.stock_code = new String[count];
			protocol.stock_market = new String[count];
			protocol.stock_name = new String[count];
			protocol.resp_total_amount = new String[count];
			protocol.resp_total_volume = new String[count];
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				protocol.resp_SYL[i] = object.getString("SYL");
				protocol.resp_change_hands[i] = object.getString("change_hands");
				protocol.change_percent[i] = object.getString("change_percent");
				protocol.change_value[i] = object.getString("change_value");
				protocol.cur_price[i] = object.getString("cur_price");
				JSONArray date_list = object.getJSONArray("date_list");
				index = date_list.length();
				protocol.resp_date[i] = new String[index];
				protocol.resp_days[i] = new String[index];
				for (int j = 0; j < date_list.length(); j++) {
					JSONObject obj_date = date_list.getJSONObject(j);
					protocol.resp_date[i][j] = obj_date.getString("ExpireDate");
					protocol.resp_days[i][j] = obj_date.getString("SurplusDays");
				}
				protocol.resp_high_price[i] = object.getString("high_price");
				protocol.resp_last_close_price[i] = object.getString("last_close_price");
				protocol.resp_low_price[i] = object.getString("low_price");
				protocol.resp_open_price[i] = object.getString("open_price");
				protocol.stock_code[i] = object.getString("stock_code");
				protocol.stock_market[i] = object.getString("stock_market");
				protocol.stock_name[i] = object.getString("stock_name");
				protocol.resp_total_amount[i] = object.getString("total_amount");
				protocol.resp_total_volume[i] = object.getString("total_volume");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
