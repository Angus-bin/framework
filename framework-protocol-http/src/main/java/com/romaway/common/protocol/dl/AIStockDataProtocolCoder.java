package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.AIStockDataEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/18.
 */
public class AIStockDataProtocolCoder extends AProtocolCoder<AIStockDataProtocol> {
    @Override
    protected byte[] encode(AIStockDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(AIStockDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("AIStockDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)){
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("data")) {
                    String str = jsonObject.getString("data");
                    Logger.d("AIStockDataProtocolCoder", "decode >>> result body = " + str);
                    if (!TextUtils.isEmpty(str)) {
                        BaseJSONArray jsonArray = new BaseJSONArray(str);
                        ArrayList<AIStockDataEntity> list = new ArrayList<AIStockDataEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            AIStockDataEntity entity = new AIStockDataEntity();
                            if (jsonObject1.has("id")) {
                                entity.setId(jsonObject1.getString("id"));
                            }
                            if (jsonObject1.has("stock_name")) {
                                entity.setStock_name(jsonObject1.getString("stock_name"));
                            }
                            if (jsonObject1.has("stock_code")) {
                                entity.setStock_code(jsonObject1.getString("stock_code"));
                            }
                            if (jsonObject1.has("level")) {
                                entity.setLevel(jsonObject1.getString("level"));
                            }
                            if (jsonObject1.has("created_at")) {
                                entity.setCreated_at(jsonObject1.getString("created_at"));
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
