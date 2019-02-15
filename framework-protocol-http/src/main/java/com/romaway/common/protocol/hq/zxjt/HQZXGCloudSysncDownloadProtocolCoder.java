package com.romaway.common.protocol.hq.zxjt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HQZXGCloudSysncDownloadProtocolCoder extends AProtocolCoder<HQZXGCloudSysncDownloadProtocol>{

	@Override
	protected byte[] encode(HQZXGCloudSysncDownloadProtocol ptl) {
		BaseJSONObject json = new BaseJSONObject();
		json.put("userId", ptl.req_userId);
		json.put("userCategory", ptl.req_userCategory);
		json.put("clientName", ptl.req_clientName);
		json.put("clientVersion", ptl.req_clientVersion);

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
	protected void decode(HQZXGCloudSysncDownloadProtocol ptl) throws ProtocolParserException {
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
			ptl.resp_portfolio = new Portfolio();

			JSONObject jsonObject = json.optJSONObject("portfolio");
			if(jsonObject != null) {
				JSONArray groupList = jsonObject.optJSONArray("groupList");
				PorStockGroup stockGroup = new PorStockGroup();
				if (groupList != null && groupList.length() > 0) {
					JSONObject zxgGroup = groupList.optJSONObject(0);
					stockGroup.groupName = zxgGroup.optString("groupName");
					stockGroup.shortName = zxgGroup.optString("shortName");
					JSONArray stockList = zxgGroup.optJSONArray("stockList");

					stockGroup.stockList = new ArrayList<PorStockInfo>();
					if (stockList != null && stockList.length() > 0) {
						PorStockInfo stockInfo;
						JSONObject stockObj;
						for (int i = 0; i < stockList.length(); i++) {
							stockObj = stockList.optJSONObject(i);
							if (stockObj != null) {
								stockInfo = new PorStockInfo();
								stockInfo.marketCode = stockObj.optString("marketCode");
								stockInfo.stockCode = stockObj.optString("stockCode");
								stockInfo.stockNote = stockObj.optString("stockNote");
								stockGroup.stockList.add(stockInfo);
							}
						}
					}
					ptl.resp_portfolio.groupList = new ArrayList<PorStockGroup>();
					ptl.resp_portfolio.groupList.add(stockGroup);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			ptl.serverErrCode = -1;
			ptl.serverMsg = "网络请求失败！";
		}
	}
}
