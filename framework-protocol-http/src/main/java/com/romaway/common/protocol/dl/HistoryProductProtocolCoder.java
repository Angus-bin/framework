package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romalibs.utils.SharedPreferenceUtils;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HistoryProductGoldEntity;
import com.romaway.common.protocol.dl.entity.HistoryProductGoldListEntity;
import com.romaway.common.protocol.dl.entity.HistoryProductLimitEntity;
import com.romaway.common.protocol.dl.entity.HistoryProductLimitListEntity;
import com.romaway.common.protocol.dl.entity.HistoryProductQswyEntity;
import com.romaway.common.protocol.dl.entity.HistoryProductQswyListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/19.
 */
public class HistoryProductProtocolCoder extends AProtocolCoder<HistoryProductProtocol> {
    @Override
    protected byte[] encode(HistoryProductProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HistoryProductProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HistoryProductProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("HistoryProductProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("five_sum")) {
                        protocol.resp_five_sum = jsonObject1.getString("five_sum");
                    }
                    if (jsonObject1.has("items")) {
                        if (protocol.req_productID.equals(SharedPreferenceUtils.getPreference("jpgpc_id", "jpgpc_id", ""))) {
                            BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                            ArrayList<HistoryProductGoldEntity> list = new ArrayList<HistoryProductGoldEntity>();
                            protocol.resp_count = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                HistoryProductGoldEntity entity = new HistoryProductGoldEntity();
                                if (jsonObject2.has("is_in")) {
                                    entity.setIs_in(jsonObject2.getString("is_in"));
                                }
                                if (jsonObject2.has("increase")) {
                                    entity.setIncrease(jsonObject2.getString("increase"));
                                }
                                if (jsonObject2.has("is_gain")) {
                                    entity.setIs_gain(jsonObject2.getString("is_gain"));
                                }
                                if (jsonObject2.has("time")) {
                                    entity.setTime(jsonObject2.getString("time"));
                                }
                                if (jsonObject2.has("is_hold")) {
                                    entity.setIs_hold(jsonObject2.getString("is_hold"));
                                }
                                if (jsonObject2.has("data")) {
                                    BaseJSONArray jsonArray1 = new BaseJSONArray(jsonObject2.getString("data"));
                                    ArrayList<HistoryProductGoldListEntity> list_item = new ArrayList<HistoryProductGoldListEntity>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        BaseJSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                        HistoryProductGoldListEntity listEntity = new HistoryProductGoldListEntity();
                                        if (jsonObject3.has("id")) {
                                            listEntity.setId(jsonObject3.getString("id"));
                                        }
                                        if (jsonObject3.has("title")) {
                                            listEntity.setTitle(jsonObject3.getString("title"));
                                        }
                                        if (jsonObject3.has("code")) {
                                            listEntity.setCode(jsonObject3.getString("code"));
                                        }
                                        if (jsonObject3.has("type")) {
                                            listEntity.setType(jsonObject3.getString("type"));
                                        }
                                        if (jsonObject3.has("price")) {
                                            listEntity.setPrice(jsonObject3.getString("price"));
                                        }
                                        if (jsonObject3.has("increase")) {
                                            listEntity.setIncrease(jsonObject3.getString("increase"));
                                        }
                                        if (jsonObject3.has("date_len")) {
                                            listEntity.setDate_len(jsonObject3.getString("date_len"));
                                        }
                                        if (jsonObject3.has("is_hold")) {
                                            listEntity.setIs_hold(jsonObject3.getString("is_hold"));
                                        }
                                        if (jsonObject3.has("date")) {
                                            listEntity.setDate(jsonObject3.getString("date"));
                                        }
                                        if (jsonObject3.has("times")) {
                                            listEntity.setTimes(jsonObject3.getString("times"));
                                        }
                                        list_item.add(listEntity);
                                    }
                                    entity.setList(list_item);
                                }
                                list.add(entity);
                            }
                            protocol.setList_gold(list);
                        } else if (protocol.req_productID.equals(SharedPreferenceUtils.getPreference("ztdj_id", "ztdj_id", ""))) {
                            BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                            ArrayList<HistoryProductLimitEntity> list = new ArrayList<HistoryProductLimitEntity>();
                            protocol.resp_count = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                HistoryProductLimitEntity entity = new HistoryProductLimitEntity();
                                if (jsonObject2.has("total")) {
                                    entity.setTotal(jsonObject2.getString("total"));
                                }
                                if (jsonObject2.has("time")) {
                                    entity.setTime(jsonObject2.getString("time"));
                                }
                                if (jsonObject2.has("data")) {
                                    BaseJSONArray jsonArray1 = new BaseJSONArray(jsonObject2.getString("data"));
                                    ArrayList<HistoryProductLimitListEntity> list_item = new ArrayList<HistoryProductLimitListEntity>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        BaseJSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                        HistoryProductLimitListEntity listEntity = new HistoryProductLimitListEntity();
                                        if (jsonObject3.has("id")) {
                                            listEntity.setId(jsonObject3.getString("id"));
                                        }
                                        if (jsonObject3.has("title")) {
                                            listEntity.setTitle(jsonObject3.getString("title"));
                                        }
                                        if (jsonObject3.has("code")) {
                                            listEntity.setCode(jsonObject3.getString("code"));
                                        }
                                        if (jsonObject3.has("in_price")) {
                                            listEntity.setIn_price(jsonObject3.getString("in_price"));
                                        }
                                        if (jsonObject3.has("first_increase")) {
                                            listEntity.setFirst_increase(jsonObject3.getString("first_increase"));
                                        }
                                        if (jsonObject3.has("max_increase")) {
                                            listEntity.setMax_increase(jsonObject3.getString("max_increase"));
                                        }
                                        if (jsonObject3.has("date")) {
                                            listEntity.setDate(jsonObject3.getString("date"));
                                        }
                                        if (jsonObject3.has("times")) {
                                            listEntity.setTimes(jsonObject3.getString("times"));
                                        }
                                        list_item.add(listEntity);
                                    }
                                    entity.setList(list_item);
                                }
                                list.add(entity);
                            }
                            protocol.setList_limit(list);
                        } else if (protocol.req_productID.equals(SharedPreferenceUtils.getPreference("ai_id", "ai_id", "63"))) {
                            BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                            ArrayList<HistoryProductGoldEntity> list = new ArrayList<HistoryProductGoldEntity>();
                            protocol.resp_count = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                HistoryProductGoldEntity entity = new HistoryProductGoldEntity();
                                if (jsonObject2.has("is_in")) {
                                    entity.setIs_in(jsonObject2.getString("is_in"));
                                }
                                if (jsonObject2.has("increase")) {
                                    entity.setIncrease(jsonObject2.getString("increase"));
                                }
                                if (jsonObject2.has("is_gain")) {
                                    entity.setIs_gain(jsonObject2.getString("is_gain"));
                                }
                                if (jsonObject2.has("time")) {
                                    entity.setTime(jsonObject2.getString("time"));
                                }
                                if (jsonObject2.has("is_hold")) {
                                    entity.setIs_hold(jsonObject2.getString("is_hold"));
                                }
                                if (jsonObject2.has("data")) {
                                    BaseJSONArray jsonArray1 = new BaseJSONArray(jsonObject2.getString("data"));
                                    ArrayList<HistoryProductGoldListEntity> list_item = new ArrayList<HistoryProductGoldListEntity>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        BaseJSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                        HistoryProductGoldListEntity listEntity = new HistoryProductGoldListEntity();
                                        if (jsonObject3.has("id")) {
                                            listEntity.setId(jsonObject3.getString("id"));
                                        }
                                        if (jsonObject3.has("title")) {
                                            listEntity.setTitle(jsonObject3.getString("title"));
                                        }
                                        if (jsonObject3.has("code")) {
                                            listEntity.setCode(jsonObject3.getString("code"));
                                        }
                                        if (jsonObject3.has("type")) {
                                            listEntity.setType(jsonObject3.getString("type"));
                                        }
                                        if (jsonObject3.has("price")) {
                                            listEntity.setPrice(jsonObject3.getString("price"));
                                        }
                                        if (jsonObject3.has("increase")) {
                                            listEntity.setIncrease(jsonObject3.getString("increase"));
                                        }
                                        if (jsonObject3.has("date_len")) {
                                            listEntity.setDate_len(jsonObject3.getString("date_len"));
                                        }
                                        if (jsonObject3.has("is_hold")) {
                                            listEntity.setIs_hold(jsonObject3.getString("is_hold"));
                                        }
                                        if (jsonObject3.has("date")) {
                                            listEntity.setDate(jsonObject3.getString("date"));
                                        }
                                        if (jsonObject3.has("times")) {
                                            listEntity.setTimes(jsonObject3.getString("times"));
                                        }
                                        list_item.add(listEntity);
                                    }
                                    entity.setList(list_item);
                                }
                                list.add(entity);
                            }
                            protocol.setList_gold(list);
                        } else {
                            BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                            ArrayList<HistoryProductQswyEntity> list = new ArrayList<HistoryProductQswyEntity>();
                            protocol.resp_count = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                HistoryProductQswyEntity entity = new HistoryProductQswyEntity();
                                if (jsonObject2.has("time")) {
                                    entity.setTime(jsonObject2.getString("time"));
                                }
                                if (jsonObject2.has("data")) {
                                    BaseJSONArray jsonArray1 = new BaseJSONArray(jsonObject2.getString("data"));
                                    ArrayList<HistoryProductQswyListEntity> list_item = new ArrayList<HistoryProductQswyListEntity>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        BaseJSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                        HistoryProductQswyListEntity listEntity = new HistoryProductQswyListEntity();
                                        if (jsonObject3.has("id")) {
                                            listEntity.setId(jsonObject3.getString("id"));
                                        }
                                        if (jsonObject3.has("title")) {
                                            listEntity.setTitle(jsonObject3.getString("title"));
                                        }
                                        if (jsonObject3.has("code")) {
                                            listEntity.setCode(jsonObject3.getString("code"));
                                        }
                                        if (jsonObject3.has("type")) {
                                            listEntity.setType(jsonObject3.getString("type"));
                                        }
                                        if (jsonObject3.has("price")) {
                                            listEntity.setPrice(jsonObject3.getString("price"));
                                        }
                                        if (jsonObject3.has("time")) {
                                            listEntity.setTime(jsonObject3.getString("time"));
                                        }
                                        if (jsonObject3.has("increase")) {
                                            listEntity.setIncrease(jsonObject3.getString("increase"));
                                        }
                                        if (jsonObject3.has("date")) {
                                            listEntity.setDate(jsonObject3.getString("date"));
                                        }
                                        if (jsonObject3.has("is_hold")) {
                                            listEntity.setIs_hold(jsonObject3.getString("is_hold"));
                                        }
                                        if (jsonObject3.has("times")) {
                                            listEntity.setTimes(jsonObject3.getString("times"));
                                        }
                                        list_item.add(listEntity);
                                    }
                                    entity.setList(list_item);
                                }
                                list.add(entity);
                            }
                            protocol.setList_qswy(list);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
