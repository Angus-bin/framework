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
 * Created by hongrb on 2017/11/16.
 */
public class HomeTitleProductDataProtocolCoder extends AProtocolCoder<HomeTitleProductDataProtocol> {
    @Override
    protected byte[] encode(HomeTitleProductDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeTitleProductDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeTitleProductDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("HomeTitleProductDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("jinpaigupiaochi")) {
                        protocol.resp_jinpaigupiaochi = jsonObject1.getString("jinpaigupiaochi");
                    }
                    if (jsonObject1.has("zhangtingdianjing")) {
                        protocol.resp_zhangtingdianjing = jsonObject1.getString("zhangtingdianjing");
                    }
                    if (jsonObject1.has("zhuangjiajingzhun")) {
                        protocol.resp_zhuangjiajingzhun = jsonObject1.getString("zhuangjiajingzhun");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
