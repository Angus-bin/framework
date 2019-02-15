package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockShuoBaEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/19.
 */
public class StockDetailShuoBaDataProtocolCoder extends AProtocolCoder<StockDetailShuoBaDataProtocol> {
    @Override
    protected byte[] encode(StockDetailShuoBaDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailShuoBaDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailShuoBaDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("total")) {
                    protocol.resp_total = jsonObject.getString("total");
                }
                if (jsonObject.has("data")) {
                    BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("data"));
                    ArrayList<StockShuoBaEntity> list = new ArrayList<StockShuoBaEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockShuoBaEntity entity = new StockShuoBaEntity();
                        if (jsonObject1.has("id")) {
                            entity.setId(jsonObject1.getString("id"));
                        }
                        if (jsonObject1.has("scode")) {
                            entity.setScode(jsonObject1.getString("scode"));
                        }
                        if (jsonObject1.has("user_id")) {
                            entity.setUser_id(jsonObject1.getString("user_id"));
                        }
                        if (jsonObject1.has("created_at")) {
                            entity.setCreated_at(jsonObject1.getString("created_at"));
                        }
                        if (jsonObject1.has("title")) {
                            entity.setTitle(jsonObject1.getString("title"));
                        }
                        if (jsonObject1.has("intro")) {
                            entity.setIntro(jsonObject1.getString("intro"));
                        }
                        if (jsonObject1.has("imgs")) {
                            entity.setImgs(jsonObject1.getString("imgs"));
                        }
                        if (jsonObject1.has("no")) {
                            entity.setNo(jsonObject1.getString("no"));
                        }
                        if (jsonObject1.has("type")) {
                            entity.setType(jsonObject1.getString("type"));
                        }
                        if (jsonObject1.has("look")) {
                            entity.setLook(jsonObject1.getString("look"));
                        }
                        if (jsonObject1.has("comment")) {
                            entity.setComment(jsonObject1.getString("comment"));
                        }
                        if (jsonObject1.has("praise")) {
                            entity.setPraise(jsonObject1.getString("praise"));
                        }
                        if (jsonObject1.has("is_praise")) {
                            entity.setIs_praise(jsonObject1.getString("is_praise"));
                        }
                        if (jsonObject1.has("other")) {
                            entity.setOther(jsonObject1.getString("other"));
                        }
                        if (jsonObject1.has("comment_at")) {
                            entity.setComment_at(jsonObject1.getString("comment_at"));
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
