package com.romaway.common.protocol.ping;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

public class PINGProtocolCoder extends AProtocolCoder<PINGProtocol> {

	@Override
	protected byte[] encode(PINGProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		String[] respHeaders = new String[]{"X-KDS-UA-ISP"};
		protocol.setResponseHeader(respHeaders);
		return reqCoder.getData();
	}

	@Override
	protected void decode(PINGProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		Logger.d("PINGProtocolCoder", "decode >>> result body = " + result
				+ " header=" + ptl.getRespHeaderValue());
		HashMap<String, String> respHeaders = ptl.getRespHeaderValue();
		if (respHeaders !=null && respHeaders.size() > 0) {
			ptl.resp_curr_ip_operator = respHeaders.get("X-KDS-UA-ISP");
		}
		
		try {
			JSONObject json = new JSONObject(result);
			ptl.resp_station_ip = json.optString("stationIp");
			ptl.resp_station_port = json.optString("stationPort");
			ptl.resp_station_name = json.optString("stationName");
			ptl.resp_station_weight = json.optString("stationWeight");
			ptl.resp_station_load = json.optString("stationLoad");
			ptl.resp_station_https_port = json.optString("stationHttpsPort");
			if (json.has("modulelist")) {
				JSONArray moduleList = json.getJSONArray("modulelist");
				int count = moduleList.length();
				ptl.resp_station_moduleList = new String[count];
				for (int i = 0; i < count; i++) {
					ptl.resp_station_moduleList[i] = moduleList.optString(i);
				}
			}
			if (json.has("carrieroperator")) {
				ptl.resp_station_carrieroperator = json.optString("carrieroperator");
			}
			ptl.serverErrCode = json.optInt("errCode");
			ptl.serverMsg = json.optString("errMsg");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			if(Logger.getDebugMode()){
				ptl.serverErrCode = -1;
				String exLog = ptl.getJSONExceptionInfo(e);
				//NO VALUE FOR STATIONNAME
				int startIndex = exLog.indexOf("NO VALUE FOR");
				int endIndex = exLog.indexOf("AT ORG.JSON.JSONOBJECT");
				
				if(exLog.contains("STATIONNAME"))
					ptl.serverMsg = "[警告]服务端没有配置stationName，导致解析失败，测速失败!";
				//else
				//	ptl.serverMsg = "网络请求超时";
				
			}else{
				ptl.serverErrCode = -1;
				ptl.serverMsg = "";
			}
		}
	}

}
