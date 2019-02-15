package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/6/5.
 */
public class HomeTitleDetailProtocolCoder extends AProtocolCoder<HomeTitleDetailProtocol> {
    @Override
    protected byte[] encode(HomeTitleDetailProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeTitleDetailProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeTitleDetailProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
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
                    Logger.d("HomeTitleDetailProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("totalrevencemouth")) {
                        protocol.resp_zsy_month = jsonObject1.getString("totalrevencemouth");
                    }
                    if (jsonObject1.has("totalrevenceyear")) {
                        protocol.resp_zsy_year = jsonObject1.getString("totalrevenceyear");
                    }
                    if (jsonObject1.has("totalrevenceday")) {
                        protocol.resp_zsy_new = jsonObject1.getString("totalrevenceday");
                    }
                    if (jsonObject1.has("totalpopulation")) {
                        protocol.resp_zrs_service = jsonObject1.getString("totalpopulation");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
