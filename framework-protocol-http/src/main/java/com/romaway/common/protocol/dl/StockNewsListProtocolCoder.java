package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockNewsListEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/1/19.
 */
public class StockNewsListProtocolCoder extends AProtocolCoder<StockNewsListProtocol> {
    @Override
    protected byte[] encode(StockNewsListProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockNewsListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockNewsListProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockNewsListEntity> list = new ArrayList<StockNewsListEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockNewsListEntity entity = new StockNewsListEntity();
                        if (jsonObject1.has("id")) {
                            entity.setId(jsonObject1.getString("id"));
                        }
                        if (jsonObject1.has("source")) {
                            entity.setSource(jsonObject1.getString("source"));
                        }
                        if (jsonObject1.has("title")) {
                            entity.setTitle(jsonObject1.getString("title"));
                        }
                        if (jsonObject1.has("time")) {
                            entity.setTime(jsonObject1.getString("time"));
                        }
                        if (jsonObject1.has("no")) {
                            entity.setNo(jsonObject1.getString("no"));
                        }
                        if (jsonObject1.has("grab")) {
                            entity.setGrab(jsonObject1.getString("grab"));
                        }
                        if (jsonObject1.has("type")) {
                            entity.setType(jsonObject1.getString("type"));
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
