package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocol;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/4/20.
 */
public class WxSetUserInfoProtocolCoder extends AProtocolCoder<WxSetUserInfoProtocol> {
    @Override
    protected byte[] encode(WxSetUserInfoProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WxSetUserInfoProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());
        String result = r.getString();

        Logger.d("WxSetUserInfoProtocolCoder", "decode >>> result body = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errCode = jsonObject.getString("error").trim();
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errMsg = jsonObject.getString("msg").trim();
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("WxSetUserInfoProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                        if (jsonObject2.has("user_id")) {
                            protocol.resp_userID = jsonObject2.getString("user_id").trim();
                        }
                        if (jsonObject2.has("mobile")) {
                            protocol.resp_mobile = jsonObject2.getString("mobile").trim();
                        }
                    }
                } else {
                    if (jsonObject.has("user_id")) {
                        protocol.resp_userID = jsonObject.getString("user_id").trim();
                    }
                    if (jsonObject.has("mobile")) {
                        protocol.resp_mobile = jsonObject.getString("mobile").trim();
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
