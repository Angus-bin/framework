package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockPartnerEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/7.
 */
public class StockDetailGdrsDataProtocolCoder extends AProtocolCoder<StockDetailGdrsDataProtocol> {
    @Override
    protected byte[] encode(StockDetailGdrsDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailGdrsDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailGdrsDataProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockPartnerEntity> list = new ArrayList<StockPartnerEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockPartnerEntity entity = new StockPartnerEntity();
                        if (jsonObject1.has("P_No")) {
                            entity.setP_No(jsonObject1.getString("P_No"));
                        }
                        if (jsonObject1.has("P_SNo")) {
                            entity.setP_SNo(jsonObject1.getString("P_SNo"));
                        }
                        if (jsonObject1.has("P_Create")) {
                            entity.setP_Create(jsonObject1.getString("P_Create"));
                        }
                        if (jsonObject1.has("P_Sum")) {
                            entity.setP_Sum(jsonObject1.getString("P_Sum"));
                        }
                        if (jsonObject1.has("P_PEqual")) {
                            entity.setP_PEqual(jsonObject1.getString("P_PEqual"));
                        }
                        if (jsonObject1.has("P_Share")) {
                            entity.setP_Share(jsonObject1.getString("P_Share"));
                        }
                        if (jsonObject1.has("P_Ten_Partne")) {
                            entity.setP_Ten_Partne(jsonObject1.getString("P_Ten_Partne"));
                        }
                        if (jsonObject1.has("P_Ten_LPartne")) {
                            entity.setP_Ten_LPartne(jsonObject1.getString("P_Ten_LPartne"));
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
