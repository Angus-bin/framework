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
 * Created by hongrb on 2017/5/17.
 */
public class WoTaskListProtocolCoder extends AProtocolCoder<WoTaskListProtocol> {
    @Override
    protected byte[] encode(WoTaskListProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WoTaskListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());

        String result = r.getString();
        Logger.d("WoTaskListProtocolCoder", "decode >>> result = " + result);

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
                    Logger.d("WoTaskListProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                        if (jsonObject2.has("user_id")) {
                            protocol.resp_userID = jsonObject2.getString("user_id").trim();
                        }
                        if (jsonObject2.has("task_img")) {
                            protocol.resp_task_img = jsonObject2.getString("task_img").trim();
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
