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
public class WxUserInfoProtocolCoder4 extends AProtocolCoder<WxUserInfoProtocol4> {
    @Override
    protected byte[] encode(WxUserInfoProtocol4 protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WxUserInfoProtocol4 protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

        String result = r.getString();
        Logger.d("WxUserInfoProtocol4", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("errcode")) {
                    protocol.resp_errCode = jsonObject.getString("errcode").trim();
                }
                jsonObject.put("device_id", "1");
                protocol.resp_result = jsonObject.toString();
                Logger.d("WxUserInfoProtocol4", "decode >>> protocol.result = " + protocol.resp_result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
