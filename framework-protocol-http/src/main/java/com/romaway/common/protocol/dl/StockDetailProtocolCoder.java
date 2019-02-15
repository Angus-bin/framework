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
 * Created by hongrb on 2017/6/7.
 */
public class StockDetailProtocolCoder extends AProtocolCoder<StockDetailProtocol> {
    @Override
    protected byte[] encode(StockDetailProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("StockDetailProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("sharename")) {
                        protocol.resp_sharename = jsonObject1.getString("sharename");
                    }
                    if (jsonObject1.has("sharenum")) {
                        protocol.resp_sharenum = jsonObject1.getString("sharenum");
                    }
                    if (jsonObject1.has("nowprice")) {
                        protocol.resp_nowprice = jsonObject1.getString("nowprice");
                    }
                    if (jsonObject1.has("in_price")) {
                        protocol.resp_in_price = jsonObject1.getString("in_price");
                    }
                    if (jsonObject1.has("out_price")) {
                        protocol.resp_out_price = jsonObject1.getString("out_price");
                    }
                    if (jsonObject1.has("in_time")) {
                        protocol.resp_in_time = jsonObject1.getString("in_time");
                    }
                    if (jsonObject1.has("out_time")) {
                        protocol.resp_out_time = jsonObject1.getString("out_time");
                    }
                    if (jsonObject1.has("earningsrate")) {
                        protocol.resp_earningsrate = jsonObject1.getString("earningsrate");
                    }
                    if (jsonObject1.has("ishold")) {
                        protocol.resp_ishold = jsonObject1.getString("ishold");
                    }
                    if (jsonObject1.has("intro")) {
                        protocol.resp_intro = jsonObject1.getString("intro");
                    }
                }
            }
        } catch (Exception e) {

        }

    }
}
