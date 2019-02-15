package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockPartnerTenEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/7.
 */
public class StockDetailPartnerTenDataProtocolCoder extends AProtocolCoder<StockDetailPartnerTenDataProtocol> {
    @Override
    protected byte[] encode(StockDetailPartnerTenDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailPartnerTenDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailPartnerTenDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("data")) {
                    BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("data"));
                    ArrayList<StockPartnerTenEntity> list = new ArrayList<StockPartnerTenEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockPartnerTenEntity entity = new StockPartnerTenEntity();
                        if (jsonObject1.has("T_No")) {
                            entity.setT_No(jsonObject1.getString("T_No"));
                        }
                        if (jsonObject1.has("T_SNo")) {
                            entity.setT_SNo(jsonObject1.getString("T_SNo"));
                        }
                        if (jsonObject1.has("T_Create")) {
                            entity.setT_Create(jsonObject1.getString("T_Create"));
                        }
                        if (jsonObject1.has("T_1")) {
                            entity.setT_1(jsonObject1.getString("T_1"));
                        }
                        if (jsonObject1.has("T_2")) {
                            entity.setT_2(jsonObject1.getString("T_2"));
                        }
                        if (jsonObject1.has("T_3")) {
                            entity.setT_3(jsonObject1.getString("T_3"));
                        }
                        if (jsonObject1.has("T_4")) {
                            entity.setT_4(jsonObject1.getString("T_4"));
                        }
                        if (jsonObject1.has("T_5")) {
                            entity.setT_5(jsonObject1.getString("T_5"));
                        }
                        if (jsonObject1.has("T_6")) {
                            entity.setT_6(jsonObject1.getString("T_6"));
                        }
                        if (jsonObject1.has("T_7")) {
                            entity.setT_7(jsonObject1.getString("T_7"));
                        }
                        if (jsonObject1.has("T_8")) {
                            entity.setT_8(jsonObject1.getString("T_8"));
                        }
                        if (jsonObject1.has("T_9")) {
                            entity.setT_9(jsonObject1.getString("T_9"));
                        }
                        if (jsonObject1.has("T_10")) {
                            entity.setT_10(jsonObject1.getString("T_10"));
                        }
                        if (jsonObject1.has("T_Grab")) {
                            entity.setT_Grab(jsonObject1.getString("T_Grab"));
                        }
                        list.add(entity);
                    }
                    protocol.setList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
