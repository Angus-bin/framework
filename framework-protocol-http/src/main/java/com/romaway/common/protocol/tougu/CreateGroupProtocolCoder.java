package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.StockGroupEntity;
import com.romaway.common.protocol.tougu.entity.StockInfoEntity;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 创建组合/调仓协议
 * Created by chenjp on 2016/7/9.
 */
public class CreateGroupProtocolCoder extends AProtocolCoder<CreateGroupProtocol> {
	@Override
	protected byte[] encode(CreateGroupProtocol protocol) {

		BaseJSONObject jsonObject = new BaseJSONObject();
		jsonObject.put("opter", protocol.req_opter);
		jsonObject.put("userid", protocol.req_userID);
		jsonObject.put("name", protocol.req_groupName);
		JSONArray array = new JSONArray();
		List<StockInfoEntity> list = protocol.list;
		int count = list == null ? 0 : list.size();
		for (int i = 0; i < count; i++) {
			BaseJSONObject object = new BaseJSONObject();
			StockInfoEntity entity = list.get(i);
			if ("0".equals(entity.getAmount()) || "0.00".equals(entity.getAmount())){
				continue;
			}
			object.put("marketCode", entity.getMarketID());
			object.put("stockCode", entity.getStockCode());
			object.put("stockName", entity.getStockName());
			object.put("BKCode", entity.getBkCode());
			object.put("BKName", entity.getBkName());
			object.put("cash", entity.getAmount());
			object.put("curPrice", entity.getLatestPrice());
			object.put("tcqbfb", entity.getTcqbfb());
			object.put("tchbfb", entity.getTchbfb());
			object.put("mmfx", entity.getBSgoto());
			array.put(object);
		}
		jsonObject.put("stockgroup", array);

		Logger.d("CreateGroupProtocolCoder",
				"encode >>> json.toString() = " + jsonObject.toString());
		byte[] result = new byte[1024];
		try {
			result = jsonObject.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Logger.d("CreateGroupProtocolCoder",
				"encode >>> result.toString() = " + result);
		return result;
	}

	@Override
	protected void decode(CreateGroupProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("CreateGroupProtocolCoder", "decode >>> result body = " + result);

		try {
			Logger.d("resultback", "result:  " + result);
			if (!TextUtils.isEmpty(result)) {
				JSONObject jsonObject = new JSONObject(result);
				protocol.resp_errCode = jsonObject.optString("errCode");
				protocol.resp_errMsg = jsonObject.optString("errMsg");
				protocol.resp_GroupID = jsonObject.optString("gid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
