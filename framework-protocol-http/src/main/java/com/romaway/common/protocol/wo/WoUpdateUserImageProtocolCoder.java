package com.romaway.common.protocol.wo;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongrb on 2017/4/20.
 */
public class WoUpdateUserImageProtocolCoder extends AProtocolCoder<WoUpdateUserImageProtocol> {
    @Override
    protected byte[] encode(WoUpdateUserImageProtocol protocol) {
        BaseJSONObject jsonObject = new BaseJSONObject();
//        jsonObject.put("xy", protocol.req_xy);
//        jsonObject.put("iv", protocol.req_iv);

        jsonObject.put("user_id", "1234H");
//        jsonObject.put("img", "123");

        Logger.d("WoUpdateUserImageProtocol",
                "encode >>> json.toString() = " + jsonObject.toString());

        byte[] result = new byte[1024];
        try {
            result = jsonObject.toString().getBytes(HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Logger.d("WoUpdateUserImageProtocol",
                "encode >>> result.toString() = " + result);


        //参数
        Map<String,String> params = new HashMap<String,String>();
        params.put("user_id", "1234H");

        return result;
    }

    @Override
    protected void decode(WoUpdateUserImageProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

        String result = r.getString();

        Logger.d("WoUpdateUserImageProtocol", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("WoUpdateUserImageProtocol", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                        protocol.resp_errCode = jsonObject2.getString("error").trim();
                        protocol.resp_errMsg = jsonObject2.getString("msg").trim();
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
