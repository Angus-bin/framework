package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockFundDebtEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/5.
 */
public class StockDetailFundDebtDataProtocolCoder extends AProtocolCoder<StockDetailFundDebtDataProtocol> {
    @Override
    protected byte[] encode(StockDetailFundDebtDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailFundDebtDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailFundDebtDataProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockFundDebtEntity> list = new ArrayList<StockFundDebtEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockFundDebtEntity entity = new StockFundDebtEntity();
                        if (jsonObject1.has("F_No")) {
                            entity.setF_No(jsonObject1.getString("F_No"));
                        }
                        if (jsonObject1.has("F_SNo")) {
                            entity.setF_SNo(jsonObject1.getString("F_SNo"));
                        }
                        if (jsonObject1.has("F_Create")) {
                            entity.setF_Create(jsonObject1.getString("F_Create"));
                        }
                        if (jsonObject1.has("F_Zczj")) {
                            entity.setF_Zczj(jsonObject1.getString("F_Zczj"));
                        }
                        if (jsonObject1.has("F_Fzhj")) {
                            entity.setF_Fzhj(jsonObject1.getString("F_Fzhj"));
                        }
                        if (jsonObject1.has("F_Syzqyhj")) {
                            entity.setF_Syzqyhj(jsonObject1.getString("F_Syzqyhj"));
                        }
                        if (jsonObject1.has("F_Grab")) {
                            entity.setF_Grab(jsonObject1.getString("F_Grab"));
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
