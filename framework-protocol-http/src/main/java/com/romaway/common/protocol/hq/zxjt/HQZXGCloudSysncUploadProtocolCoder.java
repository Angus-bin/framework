package com.romaway.common.protocol.hq.zxjt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class HQZXGCloudSysncUploadProtocolCoder extends AProtocolCoder<HQZXGCloudSysncUploadProtocol>{

	@Override
	protected byte[] encode(HQZXGCloudSysncUploadProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("userId", ptl.req_userId);
		json.put("userCategory", ptl.req_userCategory);
		json.put("clientName", ptl.req_clientName);
		json.put("clientVersion", ptl.req_clientVersion);

		BaseJSONObject portfolioObj = new BaseJSONObject();														// --- portfolio start ---
		portfolioObj.put("version", ptl.req_portfolio.version);

		List<PorStockGroup> groupList = ptl.req_portfolio.groupList;		// --- groupList start ---
		BaseJSONArray groupJsonList = new BaseJSONArray();

		PorStockGroup stockGroup = groupList.get(0);
		BaseJSONObject groupListObj = new BaseJSONObject();
		groupListObj.put("groupName", stockGroup.groupName);
		groupListObj.put("shortName", stockGroup.shortName);
		List<PorStockInfo> stockList = stockGroup.stockList;	// --- stockList start ---

		BaseJSONArray stockJsonList = new BaseJSONArray();
		if (stockList != null && stockList.size() > 0) {
			PorStockInfo stockInfo;
			BaseJSONObject stockObj;
			for (int i = 0; i < stockList.size(); i++) {
				stockInfo = stockList.get(i);
				stockObj = new BaseJSONObject();
				stockObj.put("marketCode", stockInfo.marketCode);
				stockObj.put("stockCode", stockInfo.stockCode);
				stockObj.put("stockNote", stockInfo.stockNote);
				stockJsonList.put(stockObj);
			}
		}
		groupListObj.put("stockList", stockJsonList);															// --- stockList end ---
		groupJsonList.put(groupListObj);

		portfolioObj.put("groupList", groupJsonList);															// --- groupList end ---
		json.put("portfolio", portfolioObj);																	// --- portfolio end ---

		Logger.d("NetMsgEncodeDecode", "encode >>> json.toString() = "+json.toString());
		
		byte[] result = new byte[1024];
		try {
			result = json.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void decode(HQZXGCloudSysncUploadProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		
		String result = r.getString();
		if (StringUtils.isEmpty(result)) {
			return;
		}
		Logger.d("NetMsgEncodeDecode", "decode >>> result = "+result);
		try {
			JSONObject json = new JSONObject(result);
			ptl.serverErrCode = json.getInt("errCode");
			ptl.serverMsg = json.getString("errMsg");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
