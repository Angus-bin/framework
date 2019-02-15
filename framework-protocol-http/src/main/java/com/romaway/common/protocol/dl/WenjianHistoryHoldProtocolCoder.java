package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HistoryHoldEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/11/5.
 */
public class WenjianHistoryHoldProtocolCoder extends AProtocolCoder<WenjianHistoryHoldProtocol> {
    @Override
    protected byte[] encode(WenjianHistoryHoldProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(WenjianHistoryHoldProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("WenjianHistoryHoldProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("WenjianHistoryHoldProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("items")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                        ArrayList<HistoryHoldEntity> list = new ArrayList<HistoryHoldEntity>();
                        protocol.resp_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            HistoryHoldEntity entity = new HistoryHoldEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
                            }
                            if (jsonObject2.has("code")) {
                                entity.setCode(jsonObject2.getString("code"));
                            }
                            if (jsonObject2.has("price")) {
                                entity.setPrice(jsonObject2.getString("price"));
                            }
                            if (jsonObject2.has("increase")) {
                                entity.setIncrease(jsonObject2.getString("increase"));
                            }
                            if (jsonObject2.has("date")) {
                                entity.setDate(jsonObject2.getString("date"));
                            }
                            if (jsonObject2.has("gegu_income")) {
                                entity.setGegu_income(jsonObject2.getString("gegu_income"));
                            }
                            if (jsonObject2.has("leiji_cangwei")) {
                                entity.setLeiji_cangwei(jsonObject2.getString("leiji_cangwei"));
                            }
                            list.add(entity);
                        }
                        protocol.setList(list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
