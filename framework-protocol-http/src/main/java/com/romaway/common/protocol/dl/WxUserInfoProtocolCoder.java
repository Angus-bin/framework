package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

import org.json.JSONObject;

/**
 * Created by hongrb on 2017/4/20.
 */
public class WxUserInfoProtocolCoder extends AProtocolCoder<WxUserInfoProtocol> {
    @Override
    protected byte[] encode(WxUserInfoProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WxUserInfoProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

        String result = r.getString();
        Logger.d("LoginProtocolCoder", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("errcode")) {
                    protocol.resp_errorCode = jsonObject.getString("errcode").trim();
                }
                if (jsonObject.has("access_token")) {
                    protocol.resp_access_token = jsonObject.getString("access_token").trim();
                }
                if (jsonObject.has("expires_in")) {
                    protocol.resp_expires_in = jsonObject.getString("expires_in").trim();
                }
                if (jsonObject.has("refresh_token")) {
                    protocol.resp_refresh_token = jsonObject.getString("refresh_token").trim();
                }
                if (jsonObject.has("openid")) {
                    protocol.resp_openid = jsonObject.getString("openid").trim();
                }
                if (jsonObject.has("scope")) {
                    protocol.resp_scope = jsonObject.getString("scope").trim();
                }
                if (jsonObject.has("unionid")) {
                    protocol.resp_unionid = jsonObject.getString("unionid").trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
