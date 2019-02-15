package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/5/3.
 */
public class LoginCleanTokenProtocolCoder extends AProtocolCoder<LoginCleanTokenProtocol> {
    @Override
    protected byte[] encode(LoginCleanTokenProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(LoginCleanTokenProtocol ptl) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

        String result = r.getString();
        Logger.d("LoginCleanTokenProtocolCoder", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    ptl.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    ptl.resp_errorMsg = jsonObject.getString("msg");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
