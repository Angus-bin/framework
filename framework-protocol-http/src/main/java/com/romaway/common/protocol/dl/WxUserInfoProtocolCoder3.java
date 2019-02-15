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
public class WxUserInfoProtocolCoder3 extends AProtocolCoder<WxUserInfoProtocol3> {
    @Override
    protected byte[] encode(WxUserInfoProtocol3 protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WxUserInfoProtocol3 protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

        String result = r.getString();
        Logger.d("WxUserInfoProtocol2", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                protocol.resp_errCode = jsonObject.getString("errcode").trim();
                protocol.resp_errMsg = jsonObject.getString("errmsg").trim();
//                protocol.resp_access_token = jsonObject.getString("access_token");
//                protocol.resp_expires_in = jsonObject.getString("expires_in");
//                protocol.resp_refresh_token = jsonObject.getString("refresh_token");
//                protocol.resp_openid = jsonObject.getString("openid");
//                protocol.resp_scope = jsonObject.getString("scope");
//                protocol.resp_unionid = jsonObject.getString("unionid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
