package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.AdjustStoreHistoryEntity;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/** 获取初始资金
 * Created by Edward on 2016/7/12.
 */
public class QueryInitAmountProtocolCoder extends AProtocolCoder<QueryInitAmountProtocol> {

	@Override
	protected byte[] encode(QueryInitAmountProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(QueryInitAmountProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("QueryInitAmountProtocolCoder", "decode >>> result body = " + result);
		try {
			if (!TextUtils.isEmpty(result)) {
				JSONObject jsonObject = new JSONObject(result);
				protocol.resp_errCode = jsonObject.optString("errCode");
				protocol.resp_errMsg = jsonObject.optString("errMsg");
				protocol.resp_initAmount = jsonObject.optString("initAmount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
