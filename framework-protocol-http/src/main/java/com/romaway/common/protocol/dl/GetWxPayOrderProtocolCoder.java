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
 * Created by hongrb on 2017/5/12.
 */
public class GetWxPayOrderProtocolCoder extends AProtocolCoder<GetWxPayOrderProtocol> {
    @Override
    protected byte[] encode(GetWxPayOrderProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(GetWxPayOrderProtocol ptl) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData() == null ? new byte[0] : ptl.getReceiveData());
        String result = r.getString();

        Logger.d("GetWxPayOrderProtocolCoder", "decode >>> result body = " + result);

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
                    Logger.d("GetWxPayOrderProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                        if (jsonObject2.has("appid")) {
                            ptl.resp_appid = jsonObject2.getString("appid").trim();
                        }
                        if (jsonObject2.has("partnerid")) {
                            ptl.resp_partnerid = jsonObject2.getString("partnerid").trim();
                        }
                        if (jsonObject2.has("device_info")) {
                            ptl.resp_device_info = jsonObject2.getString("device_info").trim();
                        }
                        if (jsonObject2.has("package")) {
                            ptl.resp_package = jsonObject2.getString("package").trim();
                        }
                        if (jsonObject2.has("partnerid")) {
                            ptl.resp_partnerid = jsonObject2.getString("partnerid").trim();
                        }
                        if (jsonObject2.has("nonce_str")) {
                            ptl.resp_nonce_str = jsonObject2.getString("nonce_str").trim();
                        }
                        if (jsonObject2.has("sign")) {
                            ptl.resp_sign = jsonObject2.getString("sign").trim();
                        }
                        if (jsonObject2.has("timestamp")) {
                            ptl.resp_timestamp = jsonObject2.getString("timestamp").trim();
                        }
                        if (jsonObject2.has("prepayid")) {
                            ptl.resp_prepayid = jsonObject2.getString("prepayid").trim();
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
