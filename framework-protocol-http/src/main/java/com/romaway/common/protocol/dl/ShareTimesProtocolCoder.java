
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
 * Created by hongrb on 2017/5/11.
 */
public class ShareTimesProtocolCoder extends AProtocolCoder<ShareTimesProtocol> {
    @Override
    protected byte[] encode(ShareTimesProtocol ptl) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(ShareTimesProtocol ptl) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

        String result = r.getString();
        Logger.d("ShareTimesProtocolCoder", "decode >>> result = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    ptl.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    ptl.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("ShareTimesProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                        if (jsonObject2.has("user_id")) {
                            ptl.resp_userID = jsonObject2.getString("user_id");
                        }
                        if (jsonObject2.has("times")) {
                            ptl.resp_times = jsonObject2.getInt("times");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
