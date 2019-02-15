package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockShareBonusEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/8.
 */
public class StockDetailShareBonusDataProtocolCoder extends AProtocolCoder<StockDetailShareBonusDataProtocol> {
    @Override
    protected byte[] encode(StockDetailShareBonusDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailShareBonusDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailTenPartnerDateProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockShareBonusEntity> list = new ArrayList<StockShareBonusEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockShareBonusEntity entity = new StockShareBonusEntity();
                        if (jsonObject1.has("B_No")) {
                            entity.setB_No(jsonObject1.getString("B_No"));
                        }
                        if (jsonObject1.has("B_SNo")) {
                            entity.setB_SNo(jsonObject1.getString("B_SNo"));
                        }
                        if (jsonObject1.has("B_Create")) {
                            entity.setB_Create(jsonObject1.getString("B_Create"));
                        }
                        if (jsonObject1.has("B_Fhfa")) {
                            entity.setB_Fhfa(jsonObject1.getString("B_Fhfa"));
                        }
                        if (jsonObject1.has("B_Fajd")) {
                            entity.setB_Fajd(jsonObject1.getString("B_Fajd"));
                        }
                        if (jsonObject1.has("B_Grab")) {
                            entity.setB_Grab(jsonObject1.getString("B_Grab"));
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
