package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * 用户登陆协议解析
 * @author chenjp
 *
 */
public class BindPhoneProtocolCoder extends AProtocolCoder<BindPhoneProtocol>{

	@Override
	protected byte[] encode(BindPhoneProtocol ptl) {
		/**
		 * 设置请求body
		 */
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(BindPhoneProtocol ptl)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		Logger.d("BindPhoneProtocolCoder", "decode >>> result = " + result);

		try {
			if (!TextUtils.isEmpty(result)) {
				BaseJSONObject jsonObject = new BaseJSONObject(result);
				if (jsonObject.has("error")) {
					ptl.resp_errCode = jsonObject.getString("error");
				}
				if (jsonObject.has("msg")) {
					ptl.resp_errMsg = jsonObject.getString("msg");
				}
				if (jsonObject.has("xy")) {
					String str = jsonObject.getString("xy").toUpperCase();
					DES3.setIv(jsonObject.getString("iv"));
					Logger.d("BindPhoneProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
					if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
						BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
						ptl.resp_userId = jsonObject2.getString("user_id").trim();
						ptl.resp_userName = jsonObject2.getString("nickname").trim();
						ptl.resp_phoneNum = jsonObject2.getString("mobile").trim();
						ptl.resp_userImage = jsonObject2.getString("img").trim();
						ptl.resp_avatar = jsonObject2.getString("avatar").trim();
						ptl.resp_realName = jsonObject2.getString("realname").trim();
						ptl.resp_type = jsonObject2.getString("type").trim();
						ptl.resp_agentId = jsonObject2.getString("agent_id").trim();
						ptl.resp_startTime = jsonObject2.getString("start_at").trim();
						ptl.resp_endTime = jsonObject2.getString("end_at").trim();
						ptl.resp_stockSum = jsonObject2.getString("shares_acount").trim();
						ptl.resp_userInvertType = jsonObject2.getString("user_invest_type").trim();
						ptl.resp_WxSubScribed = jsonObject2.getString("weixin_subscribed").trim();
					}
				} else {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
