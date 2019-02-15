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
 * Created by hongrb on 2017/4/20.
 */
public class WoHistoryProtocolCoder extends AProtocolCoder<WoHistoryProtocol> {
    @Override
    protected byte[] encode(WoHistoryProtocol protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WoHistoryProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData());
        String result = r.getString();

        Logger.d("WoHistoryProtocolCoder", "decode >>> result body = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                String str = jsonObject.getString("xy").toUpperCase();
                DES3.setIv(jsonObject.getString("iv"));
                Logger.d("WoHistoryProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                protocol.resp_errCode = jsonObject.getString("error").trim();
                protocol.resp_errMsg = jsonObject.getString("msg").trim();
                if (!TextUtils.isEmpty(DES3.decode(DES3.decodeHex(str)))) {
                    BaseJSONObject jsonObject2 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    protocol.resp_sum = jsonObject2.getString("sum").trim();
                    protocol.resp_data_month_gain_sum = jsonObject2.getString("data_month_gain_sum").trim();
                    protocol.resp_month = jsonObject2.getString("month").trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
