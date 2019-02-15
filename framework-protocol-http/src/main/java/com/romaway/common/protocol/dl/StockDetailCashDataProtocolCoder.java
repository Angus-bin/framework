package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockCashEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/6.
 */
public class StockDetailCashDataProtocolCoder extends AProtocolCoder<StockDetailCashDataProtocol> {
    @Override
    protected byte[] encode(StockDetailCashDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailCashDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailCashDataProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockCashEntity> list = new ArrayList<StockCashEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockCashEntity entity = new StockCashEntity();
                        if (jsonObject1.has("C_No")) {
                            entity.setC_No(jsonObject1.getString("C_No"));
                        }
                        if (jsonObject1.has("C_SNo")) {
                            entity.setC_SNo(jsonObject1.getString("C_SNo"));
                        }
                        if (jsonObject1.has("C_Create")) {
                            entity.setC_Create(jsonObject1.getString("C_Create"));
                        }
                        if (jsonObject1.has("C_Jyxjllje")) {
                            entity.setC_Jyxjllje(jsonObject1.getString("C_Jyxjllje"));
                        }
                        if (jsonObject1.has("C_Tzxjllje")) {
                            entity.setC_Tzxjllje(jsonObject1.getString("C_Tzxjllje"));
                        }
                        if (jsonObject1.has("C_Tcxjllje")) {
                            entity.setC_Tcxjllje(jsonObject1.getString("C_Tcxjllje"));
                        }
                        if (jsonObject1.has("C_Xjjxjdjwjzje")) {
                            entity.setC_Xjjxjdjwjzje(jsonObject1.getString("C_Xjjxjdjwjzje"));
                        }
                        if (jsonObject1.has("C_Grab")) {
                            entity.setC_Grab(jsonObject1.getString("C_Grab"));
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
