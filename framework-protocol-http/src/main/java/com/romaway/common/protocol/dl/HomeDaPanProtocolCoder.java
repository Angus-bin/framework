package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/9/12.
 */
public class HomeDaPanProtocolCoder extends AProtocolCoder<HomeDaPanProtocol> {
    @Override
    protected byte[] encode(HomeDaPanProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeDaPanProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeDaPanProtocolCoder", "decode >>> result body = " + result);
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
                    String str = jsonObject.getString("xy");
                    Logger.d("HomeDaPanProtocolCoder", "decode >>> result body = " + str);
                    BaseJSONObject jsonObject1 = new BaseJSONObject(str);
                    if (jsonObject1.has("shangzheng")) {
                        protocol.resp_shangzheng = jsonObject1.getString("shangzheng");
                    }
                    if (jsonObject1.has("shenzhen")) {
                        protocol.resp_shenzhen = jsonObject1.getString("shenzhen");
                    }
                    if (jsonObject1.has("chuangye")) {
                        protocol.resp_chuangye = jsonObject1.getString("chuangye");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
