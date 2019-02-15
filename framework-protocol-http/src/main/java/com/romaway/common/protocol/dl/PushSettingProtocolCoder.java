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
 * Created by hongrb on 2017/10/10.
 */
public class PushSettingProtocolCoder extends AProtocolCoder<PushSettingProtocol> {
    @Override
    protected byte[] encode(PushSettingProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(PushSettingProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("PushSettingProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("PushSettingProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("setData")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("setData"));
                        if (jsonObject2.has("is_jinpai")) {
                            protocol.resp_is_jinpai = jsonObject2.getString("is_jinpai");
                        }
                        if (jsonObject2.has("is_zhangting")) {
                            protocol.resp_is_zhangting = jsonObject2.getString("is_zhangting");
                        }
                        if (jsonObject2.has("is_qushi")) {
                            protocol.resp_is_qushi = jsonObject2.getString("is_qushi");
                        }
                        if (jsonObject2.has("is_article")) {
                            protocol.resp_is_article = jsonObject2.getString("is_article");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
