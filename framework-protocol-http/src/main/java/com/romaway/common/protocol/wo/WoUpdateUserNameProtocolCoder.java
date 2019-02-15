package com.romaway.common.protocol.wo;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.LoginProtocolNew;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/4/19.
 */
public class WoUpdateUserNameProtocolCoder extends AProtocolCoder<WoUpdateUserNameProtocol> {
    @Override
    protected byte[] encode(WoUpdateUserNameProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WoUpdateUserNameProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

        String result = r.getString();
        Logger.d("LoginProtocolCoder", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("LoginProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                        if (jsonObject2.has("error")) {
                            protocol.resp_errCode = jsonObject2.getString("error").trim();
                        }
                        if (jsonObject2.has("error")) {
                            protocol.resp_errMsg = jsonObject2.getString("msg").trim();
                        }
                    }
                } else {
                    protocol.resp_errCode = jsonObject.getString("error").trim();
                    protocol.resp_errMsg = jsonObject.getString("msg").trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
