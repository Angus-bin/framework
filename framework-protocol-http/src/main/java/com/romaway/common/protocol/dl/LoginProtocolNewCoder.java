package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * 用户登陆协议解析
 * @author chenjp
 *
 */
public class LoginProtocolNewCoder extends AProtocolCoder<LoginProtocolNew>{

	@Override
	protected byte[] encode(LoginProtocolNew ptl) {
		/**
		 * 设置请求body
		 */
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(LoginProtocolNew ptl)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		Logger.d("LoginProtocolCoder", "decode >>> result = " + result);

		try {
			if (!TextUtils.isEmpty(result)) {
				BaseJSONObject jsonObject = new BaseJSONObject(result);
				if (jsonObject.has("error")) {
					ptl.resp_errorCode = jsonObject.getString("error");
				}
				if (jsonObject.has("msg")) {
					ptl.resp_errorMsg = jsonObject.getString("msg");
				}
				if (jsonObject.has("xy")) {
					String str = jsonObject.getString("xy").toUpperCase();
					DES3.setIv(jsonObject.getString("iv"));
					Logger.d("LoginProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
					if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
						BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
						if (jsonObject2.has("user_id")) {
							ptl.resp_userId = jsonObject2.getString("user_id").trim();
						}
						if (jsonObject2.has("nickname")) {
							ptl.resp_userName = jsonObject2.getString("nickname").trim();
						}
						if (jsonObject2.has("mobile")) {
							ptl.resp_phoneNum = jsonObject2.getString("mobile").trim();
						}
						if (jsonObject2.has("img")) {
							ptl.resp_userImage = jsonObject2.getString("img").trim();
						}
						if (jsonObject2.has("avatar")) {
							ptl.resp_avatar = jsonObject2.getString("avatar").trim();
						}
						if (jsonObject2.has("realname")) {
							ptl.resp_realName = jsonObject2.getString("realname").trim();
						}
						if (jsonObject2.has("type")) {
							ptl.resp_type = jsonObject2.getString("type").trim();
						}
						if (jsonObject2.has("agent_id")) {
							ptl.resp_agentId = jsonObject2.getString("agent_id").trim();
						}
						if (jsonObject2.has("start_at")) {
							ptl.resp_startTime = jsonObject2.getString("start_at").trim();
						}
						if (jsonObject2.has("end_at")) {
							ptl.resp_endTime = jsonObject2.getString("end_at").trim();
						}
						if (jsonObject2.has("shares_acount")) {
							ptl.resp_stockSum = jsonObject2.getString("shares_acount").trim();
						}
						if (jsonObject2.has("user_invest_type")) {
							ptl.resp_userInvertType = jsonObject2.getString("user_invest_type").trim();
						}
						if (jsonObject2.has("weixin_subscribed")) {
							ptl.resp_WxSubScribed = jsonObject2.getString("weixin_subscribed").trim();
						}
						if (jsonObject2.has("xiaoshou_img")) {
							ptl.resp_xiaoshou_img = jsonObject2.getString("xiaoshou_img").trim();
						}
						if (jsonObject2.has("share_user_id")) {
							ptl.resp_share_user_id = jsonObject2.getString("share_user_id").trim();
						}
						if (jsonObject2.has("times")) {
							ptl.resp_times = jsonObject2.getInt("times");
						}
					}
				} else {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
