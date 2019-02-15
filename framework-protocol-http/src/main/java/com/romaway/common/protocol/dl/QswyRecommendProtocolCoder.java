package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.QswyRecommendEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/12/1.
 */
public class QswyRecommendProtocolCoder extends AProtocolCoder<QswyRecommendProtocol> {
    @Override
    protected byte[] encode(QswyRecommendProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(QswyRecommendProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeBrandDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)){
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
                    Logger.d("HomeBrandDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("item")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("item");
                        ArrayList<QswyRecommendEntity> list = new ArrayList<QswyRecommendEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            QswyRecommendEntity entity = new QswyRecommendEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
                            }
                            if (jsonObject2.has("day")) {
                                entity.setDay(jsonObject2.getString("day"));
                            }
                            if (jsonObject2.has("in_come")) {
                                entity.setIn_come(jsonObject2.getString("in_come"));
                            }
                            if (jsonObject2.has("in_num")) {
                                entity.setIn_num(jsonObject2.getString("in_num"));
                            }
                            if (jsonObject2.has("time_type")) {
                                entity.setTime_type(jsonObject2.getString("time_type"));
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
