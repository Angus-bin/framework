package com.romaway.common.protocol.yj;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class YuJingCXProtocolCoder extends AProtocolCoder<YuJingCXProtocol>{

	@Override
	protected byte[] encode(YuJingCXProtocol ptl) {		
		BaseJSONObject json = new BaseJSONObject();
		json.put("identifierType", ptl.req_identifierType);
		json.put("identifier", ptl.req_identifier);
		json.put("orderId", ptl.req_orderId);
		json.put("serviceId", ptl.req_serviceId);		
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
			//result = temp.getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	@Override
	protected void decode(YuJingCXProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		
		String result = r.getString();
		if (StringUtils.isEmpty(result)) {
			return;			
		}
		Logger.d("NetMsgEncodeDecode", "decode >>> result = "+result);
		try {
			JSONObject jsonObject=new JSONObject(result);
			ptl.serverErrCode=Integer.parseInt(jsonObject.getString("errCode"));
			ptl.serverMsg=jsonObject.getString("errMsg");
			
			JSONArray jsonArray=jsonObject.getJSONArray("order");
			int count=jsonArray.length();
			ptl.resp_count=count;
			if(count>0){
				ptl.resp_orderId = new String[count];
				ptl.resp_serviceId = new String[count];
				ptl.resp_productType = new String[count];
				ptl.resp_marketId = new String[count];
				ptl.resp_stockCode = new String[count];
				
				ptl.resp_stockName = new String[count];
				ptl.resp_price= new String[count];
				ptl.resp_up = new String[count];
			for(int i=0;i<count;i++){
				JSONObject jsonObject1=(JSONObject) jsonArray.get(i);				
				ptl.resp_orderId[i] = jsonObject1.getString("orderId");
				ptl.resp_serviceId[i] = jsonObject1.getString("serviceId");
				ptl.resp_productType[i] = jsonObject1.getString("productType");
				ptl.resp_marketId[i] = jsonObject1.getString("marketId");
				ptl.resp_stockCode[i] = jsonObject1.getString("stockCode");
				
				ptl.resp_stockName[i] = jsonObject1.getString("stockName");
				ptl.resp_price[i] = jsonObject1.getString("price");
				ptl.resp_up[i] = jsonObject1.getString("up");
			}
		}
			
		} catch (JSONException e) {
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
