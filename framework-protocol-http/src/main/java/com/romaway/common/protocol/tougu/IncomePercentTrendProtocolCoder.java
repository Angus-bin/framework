package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.IncomePercentTrendEntity;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** 收益率走势
 * Created by chenjp on 2016/7/9.
 */
public class IncomePercentTrendProtocolCoder extends AProtocolCoder<IncomePercentTrendProtocol> {
	@Override
	protected byte[] encode(IncomePercentTrendProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(IncomePercentTrendProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("IncomePercentTrendProtocolCoder", "decode >>> result body = " + result);

		try {
			if (!TextUtils.isEmpty(result)){
				JSONArray jsonArray = new JSONArray(result);
				List<IncomePercentTrendEntity> list = new ArrayList<IncomePercentTrendEntity>();
				for (int i = 0; i < jsonArray.length(); i++) {
					IncomePercentTrendEntity entity = new IncomePercentTrendEntity();
					JSONObject object = jsonArray.getJSONObject(i);
					entity.setClosePrice(object.optString("closePrice"));
					entity.setDayNetWorth(object.optString("dayNetWorth"));
					entity.setHsDate(object.optString("hs_date"));
					entity.setSyDate(object.optString("sy_date"));
					list.add(entity);
				}
				protocol.setList(list);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
