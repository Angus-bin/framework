package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.GoldStockHoldListEntity;
import com.romaway.common.protocol.dl.entity.GoldStockOperationListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/14.
 */
public class ZxczDataProtocolCoder extends AProtocolCoder<ZxczDataProtocol> {
    @Override
    protected byte[] encode(ZxczDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(ZxczDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("ZxczDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("ZxczDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("id")) {
                        protocol.resp_id = jsonObject1.getString("id");
                    }
                    if (jsonObject1.has("items")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("items"));
                        // 最新操作
                        if (jsonObject2.has("new_option")) {
                            BaseJSONArray jsonArray = jsonObject2.getJSONArray("new_option");
                            ArrayList<GoldStockOperationListEntity> list_operation = new ArrayList<GoldStockOperationListEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject4 = jsonArray.getJSONObject(i);
                                GoldStockOperationListEntity entity = new GoldStockOperationListEntity();
                                if (jsonObject4.has("id")) {
                                    entity.setId(jsonObject4.getString("id"));
                                }
                                if (jsonObject4.has("type")) {
                                    entity.setType(jsonObject4.getString("type"));
                                }
                                if (jsonObject4.has("title")) {
                                    entity.setTitle(jsonObject4.getString("title"));
                                }
                                if (jsonObject4.has("code")) {
                                    entity.setCode(jsonObject4.getString("code"));
                                }
                                if (jsonObject4.has("price")) {
                                    entity.setPrice(jsonObject4.getString("price"));
                                }
                                if (jsonObject4.has("time")) {
                                    entity.setTime(jsonObject4.getString("time"));
                                }
                                if (jsonObject4.has("increase")) {
                                    entity.setIncrease(jsonObject4.getString("increase"));
                                }
                                list_operation.add(entity);
                            }
                            protocol.setList_operation(list_operation);
                        }
                        // 当前持仓
                        if (jsonObject2.has("new_in_option")) {
                            BaseJSONArray jsonArray1 = jsonObject2.getJSONArray("new_in_option");
                            ArrayList<GoldStockHoldListEntity> list_hold = new ArrayList<GoldStockHoldListEntity>();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                BaseJSONObject jsonObject5 = jsonArray1.getJSONObject(i);
                                GoldStockHoldListEntity entity = new GoldStockHoldListEntity();
                                if (jsonObject5.has("id")) {
                                    entity.setId(jsonObject5.getString("id"));
                                }
                                if (jsonObject5.has("type")) {
                                    entity.setType(jsonObject5.getString("type"));
                                }
                                if (jsonObject5.has("title")) {
                                    entity.setTitle(jsonObject5.getString("title"));
                                }
                                if (jsonObject5.has("code")) {
                                    entity.setCode(jsonObject5.getString("code"));
                                }
                                if (jsonObject5.has("prime_price")) {
                                    entity.setPrice(jsonObject5.getString("prime_price"));
                                }
                                if (jsonObject5.has("time")) {
                                    entity.setTime(jsonObject5.getString("time"));
                                }
                                if (jsonObject5.has("now_price")) {
                                    entity.setNow_price(jsonObject5.getString("now_price"));
                                }
                                if (jsonObject5.has("total_increase")) {
                                    entity.setTotal_increase(jsonObject5.getString("total_increase"));
                                }
                                if (jsonObject5.has("today_increase")) {
                                    entity.setToday_increase(jsonObject5.getString("today_increase"));
                                }
                                if (jsonObject5.has("allow_look")) {
                                    entity.setAllow_look(jsonObject5.getString("allow_look"));
                                }
                                if (jsonObject5.has("times")) {
                                    entity.setTimes(jsonObject5.getString("times"));
                                }
                                list_hold.add(entity);
                            }
                            protocol.setList_hold(list_hold);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
