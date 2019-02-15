package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockProfitEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/6.
 */
public class StockDetailProfitDataProtocolCoder extends AProtocolCoder<StockDetailProfitDataProtocol> {
    @Override
    protected byte[] encode(StockDetailProfitDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailProfitDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailProfitDataProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockProfitEntity> list = new ArrayList<StockProfitEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockProfitEntity entity = new StockProfitEntity();
                        if (jsonObject1.has("P_No")) {
                            entity.setP_No(jsonObject1.getString("P_No"));
                        }
                        if (jsonObject1.has("P_SNo")) {
                            entity.setP_SNo(jsonObject1.getString("P_SNo"));
                        }
                        if (jsonObject1.has("P_Create")) {
                            entity.setP_Create(jsonObject1.getString("P_Create"));
                        }
                        if (jsonObject1.has("P_Yysr")) {
                            entity.setP_Yysr(jsonObject1.getString("P_Yysr"));
                        }
                        if (jsonObject1.has("P_Yyzc")) {
                            entity.setP_Yyzc(jsonObject1.getString("P_Yyzc"));
                        }
                        if (jsonObject1.has("P_Yylr")) {
                            entity.setP_Yylr(jsonObject1.getString("P_Yylr"));
                        }
                        if (jsonObject1.has("P_Syzjlr")) {
                            entity.setP_Syzjlr(jsonObject1.getString("P_Syzjlr"));
                        }
                        if (jsonObject1.has("P_Grab")) {
                            entity.setP_Grab(jsonObject1.getString("P_Grab"));
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
