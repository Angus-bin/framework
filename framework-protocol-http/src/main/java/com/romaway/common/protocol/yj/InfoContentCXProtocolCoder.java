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

public class InfoContentCXProtocolCoder extends AProtocolCoder<InfoContentCXProtocol>{

	@Override
	protected byte[] encode(InfoContentCXProtocol ptl) {		
		BaseJSONObject json = new BaseJSONObject();
		json.put("identifier", ptl.req_identifier);	
		json.put("time", ptl.req_time);	
		json.put("num", ptl.req_num);	
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
	protected void decode(InfoContentCXProtocol ptl) throws ProtocolParserException {
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
			ptl.smsArray=result;			
		} catch (JSONException e) {
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
