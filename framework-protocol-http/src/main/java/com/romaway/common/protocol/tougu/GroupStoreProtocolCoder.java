package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.StockGroupEntity;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/** 组合持仓情况
 * Created by chenjp on 2016/7/9.
 */
public class GroupStoreProtocolCoder extends AProtocolCoder<GroupStoreProtocol> {
	@Override
	protected byte[] encode(GroupStoreProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(GroupStoreProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("GroupStoreProtocolCoder", "decode >>> result body = " + result);

		try {
			if (!TextUtils.isEmpty(result)) {
				JSONArray jsonArray = new JSONArray(result);
				ArrayList<StockGroupEntity> list = new ArrayList<StockGroupEntity>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					StockGroupEntity entity = new StockGroupEntity();
					entity.setCash(item.optString("cash"));
					entity.setMarketID(item.optString("marketCode"));
					entity.setNumber(item.optString("number"));
					entity.setStockCode(item.optString("stockCode"));
					entity.setStockName(item.optString("stockName"));
					entity.setBkCode(item.optString("stockType"));
					entity.setBkName(item.optString("stockTypeName"));
					entity.setTradeNumber(item.optString("tradeNumber"));
					entity.setSfcj(item.optString("sfcj"));
					list.add(entity);
				}
				protocol.setList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
