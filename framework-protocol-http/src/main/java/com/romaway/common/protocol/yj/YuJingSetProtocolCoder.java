package com.romaway.common.protocol.yj;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class YuJingSetProtocolCoder extends AProtocolCoder<YuJingSetProtocol> {

	@Override
	protected byte[] encode(YuJingSetProtocol ptl) {
		BaseJSONObject json1 = new BaseJSONObject();
		json1.put("identifierType", ptl.req_identifierType);
		json1.put("identifier", ptl.req_identifier);
		json1.put("pushId", ptl.req_pushId);
		json1.put("appType", ptl.req_appType);
		BaseJSONArray jsonArray = new BaseJSONArray();
		int count = ptl.req_count;
		for (int i = 0; i < count; i++) {
			JSONObject json = new JSONObject();
			try {
				json.put("orderId", ptl.req_orderId[i]);
				json.put("orderType", ptl.req_orderType[i]);
				json.put("serviceId", ptl.req_serviceId[i]);
				json.put("marketId", ptl.req_marketId[i]);
				json.put("productType", ptl.req_productType[i]);

				json.put("stockCode", ptl.req_stockCode[i]);
				json.put("stockName", ptl.req_stockName[i]);
				json.put("price", ptl.req_price[i]);
				json.put("up", ptl.req_up[i]);
				jsonArray.put(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}			
		}
		json1.put("order", jsonArray);
		byte[] result = new byte[4096];
		try {
			result = json1.toString().getBytes(HTTP.UTF_8);
			// result = temp.getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void decode(YuJingSetProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		if (StringUtils.isEmpty(result)) {
			return;
		}
		Logger.d("NetMsgEncodeDecode", "decode >>> result = " + result);
		try {
			JSONObject jsonObject1 = new JSONObject(result);
			ptl.serverErrCode = Integer.parseInt(jsonObject1.getString("errCode"));
			ptl.serverMsg = jsonObject1.getString("errMsg");
			if((jsonObject1.getString("errCode")).equals("0")&&(jsonObject1.getString("errMsg").equals("ok"))){//"errCode": "0","errMsg": "ok",同时符合时候才进行下一步解析
				JSONArray jsonArray = jsonObject1.getJSONArray("order");
				int count = jsonArray.length();
				ptl.resp_count = count;
				if (count > 0) {
					ptl.resp_orderId = new int[count];
					for (int i = 0; i < count; i++) {
						JSONObject jsonObject = (JSONObject) jsonArray.get(i);
						ptl.resp_orderId[i] = jsonObject.getInt("orderId");
					}				
			  }			
			}else{
				ptl.resp_count =-1;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
