package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

import org.json.JSONObject;

/** 关注/取消关注协议
 * Created by chenjp on 2016/7/9.
 */
public class AddAttentionProtocolCoder extends AProtocolCoder<AddAttentionProtocol> {
	@Override
	protected byte[] encode(AddAttentionProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(AddAttentionProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("AddAttentionProtocolCoder", "decode >>> result body = " + result);

		try {
			if (!TextUtils.isEmpty(result)) {
				JSONObject jsonObject = new JSONObject(result);
				protocol.resp_errCode = jsonObject.optString("errCode");
				protocol.resp_errMsg = jsonObject.optString("errMsg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
