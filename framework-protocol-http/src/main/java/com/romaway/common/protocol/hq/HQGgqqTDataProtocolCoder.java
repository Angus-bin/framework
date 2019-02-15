package com.romaway.common.protocol.hq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

public class HQGgqqTDataProtocolCoder extends AProtocolCoder<HQGgqqTDataProtocol>{

	@Override
	protected byte[] encode(HQGgqqTDataProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQGgqqTDataProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		protocol.P_stock_code = new String[protocol.ExpireDate_wCount][];
		protocol.C_stock_code = new String[protocol.ExpireDate_wCount][];
		protocol.P_stock_market = new String[protocol.ExpireDate_wCount][];
		protocol.C_stock_market = new String[protocol.ExpireDate_wCount][];
		
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				if (i == 0) {
					JSONObject obj_stock = array.getJSONObject(i);
					protocol.stock_name = obj_stock.getString("stock_name");
					protocol.stock_code = obj_stock.getString("stock_code");
					protocol.cur_price = obj_stock.getString("cur_price");
					protocol.change_percent = obj_stock
							.getString("change_percent");
				} else {
					JSONObject obj_option = array.getJSONObject(i);
					JSONArray array_data = obj_option
							.getJSONArray("optionData");
					protocol.C_TotalLongPosition = new String[array_data
							.length()];
					protocol.C_buy_1_price = new String[array_data.length()];
					protocol.C_buy_1_volume = new String[array_data.length()];
					protocol.C_change_percent = new String[array_data.length()];
					protocol.C_cur_price = new String[array_data.length()];
					protocol.C_sell_1_price = new String[array_data.length()];
					protocol.C_sell_1_volume = new String[array_data.length()];
					protocol.C_total_volume = new String[array_data.length()];
					protocol.C_last_close_price = new String[array_data.length()];
					protocol.P_TotalLongPosition = new String[array_data
							.length()];
					protocol.P_buy_1_price = new String[array_data.length()];
					protocol.P_buy_1_volume = new String[array_data.length()];
					protocol.P_change_percent = new String[array_data.length()];
					protocol.P_cur_price = new String[array_data.length()];
					protocol.P_sell_1_price = new String[array_data.length()];
					protocol.P_sell_1_volume = new String[array_data.length()];
					protocol.P_total_volume = new String[array_data.length()];
					protocol.P_last_close_price = new String[array_data.length()];
					protocol.ExercisePrice = new String[array_data.length()];
					protocol.req_wCount = array_data.length();
					
					protocol.P_stock_code[protocol.ExpireDate_index] = new String[protocol.req_wCount];
					protocol.C_stock_code[protocol.ExpireDate_index] = new String[protocol.req_wCount];
					protocol.P_stock_market[protocol.ExpireDate_index] = new String[protocol.req_wCount];
					protocol.C_stock_market[protocol.ExpireDate_index] = new String[protocol.req_wCount];
					for (int j = 0; j < array_data.length(); j++) {
						protocol.req_wCount = array_data.length();
						JSONObject obj_date = array_data.getJSONObject(j);
						protocol.C_TotalLongPosition[j] = obj_date
								.getString("C_TotalLongPosition");
						protocol.C_buy_1_price[j] = obj_date
								.getString("C_buy_1_price");
						protocol.C_buy_1_volume[j] = obj_date
								.getString("C_buy_1_volume");
						protocol.C_change_percent[j] = obj_date
								.getString("C_change_percent");
						protocol.C_cur_price[j] = obj_date
								.getString("C_cur_price");
						protocol.C_last_close_price[j] = obj_date
								.getString("C_last_close_price");
						protocol.C_sell_1_price[j] = obj_date
								.getString("C_sell_1_price");
						protocol.C_sell_1_volume[j] = obj_date
								.getString("C_sell_1_volume");
						protocol.C_total_volume[j] = obj_date
								.getString("C_total_volume");
						protocol.P_TotalLongPosition[j] = obj_date
								.getString("P_TotalLongPosition");
						protocol.P_buy_1_price[j] = obj_date
								.getString("P_buy_1_price");
						protocol.P_buy_1_volume[j] = obj_date
								.getString("P_buy_1_volume");
						protocol.P_change_percent[j] = obj_date
								.getString("P_change_percent");
						protocol.P_cur_price[j] = obj_date
								.getString("P_cur_price");
						protocol.P_last_close_price[j] = obj_date
								.getString("P_last_close_price");
						protocol.P_sell_1_price[j] = obj_date
								.getString("P_sell_1_price");
						protocol.P_sell_1_volume[j] = obj_date
								.getString("P_sell_1_volume");
						protocol.P_total_volume[j] = obj_date
								.getString("P_total_volume");
						protocol.P_stock_code[protocol.ExpireDate_index][j] = obj_date
								.getString("P_stock_code");
						protocol.C_stock_code[protocol.ExpireDate_index][j] = obj_date
								.getString("C_stock_code");
						protocol.P_stock_market[protocol.ExpireDate_index][j] = obj_date
								.getString("P_stock_market");
						protocol.C_stock_market[protocol.ExpireDate_index][j] = obj_date
								.getString("P_stock_market");
						protocol.ExercisePrice[j] = obj_date
								.getString("ExercisePrice");
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
