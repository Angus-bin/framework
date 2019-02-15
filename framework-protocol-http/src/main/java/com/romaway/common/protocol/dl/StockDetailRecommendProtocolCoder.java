package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.RecommendListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/10/9.
 */
public class StockDetailRecommendProtocolCoder extends AProtocolCoder<StockDetailRecommendProtocol> {
    @Override
    protected byte[] encode(StockDetailRecommendProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailRecommendProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailRecommendProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("StockDetailRecommendProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("item")) {
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject1.getString("item"));
                        ArrayList<RecommendListEntity> list = new ArrayList<RecommendListEntity>();
                        int count = jsonArray.length();
                        for (int i = 0; i < count; i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            RecommendListEntity entity = new RecommendListEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("time")) {
                                entity.setTime(jsonObject2.getString("time"));
                            }
                            if (jsonObject2.has("content")) {
                                entity.setContent(jsonObject2.getString("content"));
                            }
                            if (jsonObject2.has("img")) {
                                entity.setImg(jsonObject2.getString("img"));
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
