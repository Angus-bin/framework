package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.GoldStockOperationListEntity;
import com.romaway.common.protocol.dl.entity.LimitStockOperationListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/14.
 */
public class ZtdjDataProtocolCoder extends AProtocolCoder<ZtdjDataProtocol> {
    @Override
    protected byte[] encode(ZtdjDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(ZtdjDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("ZtdjDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("ZtdjDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("id")) {
                        protocol.resp_id = jsonObject1.getString("id");
                    }
                    if (jsonObject1.has("items")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("items"));
                        if (jsonObject2.has("new_date")) {
                            protocol.resp_new_date = jsonObject2.getString("new_date");
                        }
                        // 涨停点睛
                        if (jsonObject2.has("new_option")) {
                            BaseJSONArray jsonArray = jsonObject2.getJSONArray("new_option");
                            ArrayList<LimitStockOperationListEntity> list_operation = new ArrayList<LimitStockOperationListEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject4 = jsonArray.getJSONObject(i);
                                LimitStockOperationListEntity entity = new LimitStockOperationListEntity();
                                if (jsonObject4.has("id")) {
                                    entity.setId(jsonObject4.getString("id"));
                                }
                                if (jsonObject4.has("title")) {
                                    entity.setTitle(jsonObject4.getString("title"));
                                }
                                if (jsonObject4.has("code")) {
                                    entity.setCode(jsonObject4.getString("code"));
                                }
                                if (jsonObject4.has("in_price")) {
                                    entity.setPrice(jsonObject4.getString("in_price"));
                                }
                                if (jsonObject4.has("now_price")) {
                                    entity.setNow_price(jsonObject4.getString("now_price"));
                                }
                                if (jsonObject4.has("increase")) {
                                    entity.setIncrease(jsonObject4.getString("increase"));
                                }
                                list_operation.add(entity);
                            }
                            protocol.setList_operation(list_operation);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
