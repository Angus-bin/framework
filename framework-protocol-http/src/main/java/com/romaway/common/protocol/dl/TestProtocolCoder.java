package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/14.
 */
public class TestProtocolCoder extends AProtocolCoder<TestProtocol> {
    @Override
    protected byte[] encode(TestProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(TestProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("TestProtocolCoder", "decode >>> result body = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                String str = jsonObject.getString("xy").toUpperCase();
                DES3.setIv(jsonObject.getString("iv"));
                Logger.d("TestProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.d("", "");

    }
}
