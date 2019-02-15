package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by kds on 2016/7/9.
 */
public class ModifyGroupInfoProtocolCoder extends AProtocolCoder<ModifyGroupInfoProtocol> {
	@Override
	protected byte[] encode(ModifyGroupInfoProtocol protocol) {
		BaseJSONObject jsonObject = new BaseJSONObject();
		jsonObject.put("userid", protocol.req_userID);
		jsonObject.put("groupid", protocol.req_groupId);
		jsonObject.put("sfyc", protocol.req_groupHide);
		jsonObject.put("name", protocol.req_groupName);
		jsonObject.put("idea", protocol.req_addvice);

		Logger.d("ModifyGroupInfoProtocolCoder",
				"encode >>> json.toString() = " + jsonObject.toString());
		byte[] result = new byte[1024];
		try {
			result = jsonObject.toString().getBytes(HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void decode(ModifyGroupInfoProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ModifyGroupInfoProtocolCoder", "decode >>> result body = " + result);

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
