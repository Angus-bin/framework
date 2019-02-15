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
 * Created by hongrb on 2017/10/9.
 */
public class StockDetailProtocolCoderNew extends AProtocolCoder<StockDetailProtocolNew> {
    @Override
    protected byte[] encode(StockDetailProtocolNew protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailProtocolNew protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailProtocolCoderNew", "decode >>> result body = " + result);
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
                    Logger.d("StockDetailProtocolCoderNew", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("item")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("item"));
                        if (jsonObject2.has("id")) {
                            protocol.resp_id = jsonObject2.getString("id");
                        }
                        if (jsonObject2.has("title")) {
                            protocol.resp_title = jsonObject2.getString("title");
                        }
                        if (jsonObject2.has("code")) {
                            protocol.resp_code = jsonObject2.getString("code");
                        }
                        if (jsonObject2.has("pro_id")) {
                            protocol.resp_pro_id = jsonObject2.getString("pro_id");
                        }
                        if (jsonObject2.has("in")) {
                            BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("in"));
                            if (jsonObject3.has("price")) {
                                protocol.resp_in_price = jsonObject3.getString("price");
                            }
                            if (jsonObject3.has("time")) {
                                protocol.resp_in_time = jsonObject3.getString("time");
                            }
                        }
                        if (jsonObject2.has("out")) {
                            BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("out"));
                            if (jsonObject3.has("price")) {
                                protocol.resp_out_price = jsonObject3.getString("price");
                            }
                            if (jsonObject3.has("time")) {
                                protocol.resp_out_time = jsonObject3.getString("time");
                            }
                        }
                        if (jsonObject2.has("increase")) {
                            protocol.resp_increase = jsonObject2.getString("increase");
                        }
                        if (jsonObject2.has("length")) {
                            protocol.resp_length = jsonObject2.getString("length");
                        }
                        if (jsonObject2.has("pro_title")) {
                            protocol.resp_pro_title = jsonObject2.getString("pro_title");
                        }
                        if (jsonObject2.has("now_price")) {
                            protocol.resp_nowprice = jsonObject2.getString("now_price");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
