package com.romaway.common.protocol.wo;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

public class WoUserInfoSelectProtocolCoder extends AProtocolCoder<WoUserInfoSelectProtocol>{

	@Override
	protected byte[] encode(WoUserInfoSelectProtocol protocol) {
		/**
		 * 设置请求body
		 */
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}
	
	@Override
	protected void decode(WoUserInfoSelectProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		String result = r.getString();

		Logger.d("WoUserInfoSelectProtocolCoder", "decode >>> result body = " + result);
		ptl.isCatch = false;
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
					Logger.d("WoUserInfoSelectProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
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
						if (jsonObject2.has("times")) {
							ptl.resp_times = jsonObject2.getInt("times");
						}
						if (jsonObject2.has("is_vip")) {
							ptl.resp_vip = jsonObject2.getInt("is_vip");
						}
						if (jsonObject2.has("end_at_show")) {
							ptl.resp_end_at_show = jsonObject2.getInt("end_at_show");
						}
						if (jsonObject2.has("jinpai")) {
							ptl.resp_jinpai = jsonObject2.getString("jinpai");
						}
						if (jsonObject2.has("zhangting")) {
							ptl.resp_zhangting = jsonObject2.getString("zhangting");
						}
						if (jsonObject2.has("question_user_type")) {
							ptl.resp_question_user_type = jsonObject2.getString("question_user_type");
						}
						if (jsonObject2.has("jinpaigupiaochi")) {
							ptl.resp_jinpaigupiaochi = jsonObject2.getString("jinpaigupiaochi");
						}
						if (jsonObject2.has("zhangtingdianjing")) {
							ptl.resp_zhangtingdianjing = jsonObject2.getString("zhangtingdianjing");
						}
						if (jsonObject2.has("weixin")) {
							ptl.resp_weixin = jsonObject2.getString("weixin");
						}
						if (jsonObject2.has("unlook_message_num")) {
							ptl.resp_unlook_message_num = jsonObject2.getString("unlook_message_num");
						}
						if (jsonObject2.has("province")) {
							ptl.resp_province = jsonObject2.getString("province");
						}
						if (jsonObject2.has("city")) {
							ptl.resp_city = jsonObject2.getString("city");
						}
						if (jsonObject2.has("sex")) {
							ptl.resp_sex = jsonObject2.getString("sex");
						}
						if (jsonObject2.has("user_invite_type_sel")) {
							ptl.resp_user_invite_type_sel = jsonObject2.getString("user_invite_type_sel");
						}
						if (jsonObject2.has("old")) {
							ptl.resp_old = jsonObject2.getString("old");
						}
						if (jsonObject2.has("ziben")) {
							ptl.resp_ziben = jsonObject2.getString("ziben");
						}
//					ptl.resp_share_user_id = jsonObject2.getString("share_user_id").trim();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ptl.isCatch = true;
			e.printStackTrace();
		}
	}
}
