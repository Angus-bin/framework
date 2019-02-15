package com.romaway.common.protocol.wo;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/11/29.
 */
public class WoWxPayDataProtocolCoder extends AProtocolCoder<WoWxPayDataProtocol> {
    @Override
    protected byte[] encode(WoWxPayDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WoWxPayDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

        String result = r.getString();
        Logger.d("WoWxPayDataProtocolCoder", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)){
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("WoWxPayDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("id")) {
                        protocol.resp_id = jsonObject1.getString("id");
                    }
                    if (jsonObject1.has("title")) {
                        protocol.resp_title = jsonObject1.getString("title");
                    }
                    if (jsonObject1.has("sub_title")) {
                        protocol.resp_sub_title = jsonObject1.getString("sub_title");
                    }
                    if (jsonObject1.has("start_at")) {
                        protocol.resp_start_at = jsonObject1.getString("start_at");
                    }
                    if (jsonObject1.has("end_at")) {
                        protocol.resp_end_at = jsonObject1.getString("end_at");
                    }
                    if (jsonObject1.has("price")) {
                        protocol.resp_price = jsonObject1.getString("price");
                    }
                    if (jsonObject1.has("qishu")) {
                        protocol.resp_qishu = jsonObject1.getString("qishu");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
