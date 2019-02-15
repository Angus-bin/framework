package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.ZtdjWeekDataEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/2/6.
 */
public class ZtdjWeekDataProtocolCoder extends AProtocolCoder<ZtdjWeekDataProtocol> {
    @Override
    protected byte[] encode(ZtdjWeekDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(ZtdjWeekDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("ZtdjWeekDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("stock_res")) {
                    BaseJSONArray jsonArray = jsonObject.getJSONArray("stock_res");
                    ArrayList<ZtdjWeekDataEntity> list = new ArrayList<ZtdjWeekDataEntity>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ZtdjWeekDataEntity entity = new ZtdjWeekDataEntity();
                        if (jsonObject1.has("increase")) {
                            entity.setIncrease(jsonObject1.getString("increase"));
                        }
                        if (jsonObject1.has("title")) {
                            entity.setTitle(jsonObject1.getString("title"));
                        }
                        if (jsonObject1.has("code")) {
                            entity.setCode(jsonObject1.getString("code"));
                        }
                        if (jsonObject1.has("days")) {
                            entity.setDays(jsonObject1.getString("days"));
                        }
                        if (jsonObject1.has("stock_id")) {
                            entity.setStockID(jsonObject1.getString("stock_id"));
                        }
                        if (jsonObject1.has("pro_sort_title")) {
                            entity.setPro_sort_title(jsonObject1.getString("pro_sort_title"));
                        }
                        if (jsonObject1.has("stock_code")) {
                            entity.setStock_code(jsonObject1.getString("stock_code"));
                        }
                        if (jsonObject1.has("pro_id")) {
                            entity.setPro_id(jsonObject1.getString("pro_id"));
                        }
                        if (jsonObject1.has("type")) {
                            entity.setType(jsonObject1.getString("type"));
                        }
                        if (jsonObject1.has("isHold")) {
                            entity.setIsHold(jsonObject1.getString("isHold"));
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
