package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romalibs.utils.SharedPreferenceUtils;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeProductCleverListEntity;
import com.romaway.common.protocol.dl.entity.HomeProductSpecialistEntity;
import com.romaway.common.protocol.dl.entity.HomeProductSpecialistListEntity;
import com.romaway.common.protocol.dl.entity.HomeProductUnStartListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/11/17.
 */
public class HomeProductDataListProtocolCoder extends AProtocolCoder<HomeProductDataListProtocol> {
    @Override
    protected byte[] encode(HomeProductDataListProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeProductDataListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeProductDataListProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("HomeProductDataProtocolNewCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("item")) {
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject1.getString("item"));
                        if ("0".equals(protocol.type)) {
                            ArrayList<HomeProductSpecialistListEntity> list = new ArrayList<HomeProductSpecialistListEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                HomeProductSpecialistListEntity entity = new HomeProductSpecialistListEntity();
                                if (jsonObject2.has("id")) {
                                    entity.setId(jsonObject2.getString("id"));
                                }
                                if (jsonObject2.has("zhiying")) {
                                    entity.setZhiying(jsonObject2.getString("zhiying"));
                                }
                                if (jsonObject2.has("zhisun")) {
                                    entity.setZhisun(jsonObject2.getString("zhisun"));
                                }
                                if (jsonObject2.has("increase")) {
                                    entity.setIncrease(jsonObject2.getString("increase"));
                                }
                                if (jsonObject2.has("day")) {
                                    entity.setDay(jsonObject2.getString("day"));
                                }
                                if (jsonObject2.has("being_day")) {
                                    entity.setBeing_day(jsonObject2.getString("being_day"));
                                }
                                if (jsonObject2.has("subscribe_nums")) {
                                    entity.setSubscribe_nums(jsonObject2.getString("subscribe_nums"));
                                }
                                if (jsonObject2.has("start_at")) {
                                    entity.setStart_at(jsonObject2.getString("start_at"));
                                }
                                if (jsonObject2.has("end_at")) {
                                    entity.setEnd_at(jsonObject2.getString("end_at"));
                                }
                                if (jsonObject2.has("title")) {
                                    entity.setTitle(jsonObject2.getString("title"));
                                }
                                if (jsonObject2.has("time_type")) {
                                    entity.setTime_type(jsonObject2.getString("time_type"));
                                }
                                if (jsonObject2.has("price")) {
                                    entity.setPrice(jsonObject2.getString("price"));
                                }
                                if (jsonObject2.has("is_subscribe")) {
                                    entity.setIs_subscribe(jsonObject2.getString("is_subscribe"));
                                }
                                if (jsonObject2.has("is_new")) {
                                    entity.setIs_new(jsonObject2.getString("is_new"));
                                }
                                if (jsonObject2.has("is_hot")) {
                                    entity.setIs_hot(jsonObject2.getString("is_hot"));
                                }
                                list.add(entity);
                            }
                            protocol.setList_specialist(list);
                        } else if ("3".equals(protocol.type)) {
                            ArrayList<HomeProductCleverListEntity> list = new ArrayList<HomeProductCleverListEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                HomeProductCleverListEntity entity = new HomeProductCleverListEntity();
                                if (jsonObject2.has("id")) {
                                    entity.setId(jsonObject2.getString("id"));
                                }
                                if (jsonObject2.has("zhiying")) {
                                    entity.setZhiying(jsonObject2.getString("zhiying"));
                                }
                                if (jsonObject2.has("zhisun")) {
                                    entity.setZhisun(jsonObject2.getString("zhisun"));
                                }
                                if (jsonObject2.has("increase")) {
                                    entity.setIncrease(jsonObject2.getString("increase"));
                                }
                                if (jsonObject2.has("day")) {
                                    entity.setDay(jsonObject2.getString("day"));
                                }
                                if (jsonObject2.has("being_day")) {
                                    entity.setBeing_day(jsonObject2.getString("being_day"));
                                }
                                if (jsonObject2.has("subscribe_nums")) {
                                    entity.setSubscribe_nums(jsonObject2.getString("subscribe_nums"));
                                }
                                if (jsonObject2.has("start_at")) {
                                    entity.setStart_at(jsonObject2.getString("start_at"));
                                }
                                if (jsonObject2.has("end_at")) {
                                    entity.setEnd_at(jsonObject2.getString("end_at"));
                                }
                                if (jsonObject2.has("title")) {
                                    entity.setTitle(jsonObject2.getString("title"));
                                }
                                if (jsonObject2.has("time_type")) {
                                    entity.setTime_type(jsonObject2.getString("time_type"));
                                }
                                if (jsonObject2.has("price")) {
                                    entity.setPrice(jsonObject2.getString("price"));
                                }
                                if (jsonObject2.has("is_subscribe")) {
                                    entity.setIs_subscribe(jsonObject2.getString("is_subscribe"));
                                }
                                if (jsonObject2.has("is_new")) {
                                    entity.setIs_new(jsonObject2.getString("is_new"));
                                }
                                if (jsonObject2.has("is_hot")) {
                                    entity.setIs_hot(jsonObject2.getString("is_hot"));
                                }
                                list.add(entity);
                            }
                            protocol.setList_clever(list);
                        } else {
                            ArrayList<HomeProductUnStartListEntity> list = new ArrayList<HomeProductUnStartListEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                HomeProductUnStartListEntity entity = new HomeProductUnStartListEntity();
                                if (jsonObject2.has("id")) {
                                    entity.setId(jsonObject2.getString("id"));
                                }
                                if (jsonObject2.has("zhiying")) {
                                    entity.setZhiying(jsonObject2.getString("zhiying"));
                                }
                                if (jsonObject2.has("zhisun")) {
                                    entity.setZhisun(jsonObject2.getString("zhisun"));
                                }
                                if (jsonObject2.has("increase")) {
                                    entity.setIncrease(jsonObject2.getString("increase"));
                                }
                                if (jsonObject2.has("day")) {
                                    entity.setDay(jsonObject2.getString("day"));
                                }
                                if (jsonObject2.has("being_day")) {
                                    entity.setBeing_day(jsonObject2.getString("being_day"));
                                }
                                if (jsonObject2.has("subscribe_nums")) {
                                    entity.setSubscribe_nums(jsonObject2.getString("subscribe_nums"));
                                }
                                if (jsonObject2.has("start_at")) {
                                    entity.setStart_at(jsonObject2.getString("start_at"));
                                }
                                if (jsonObject2.has("end_at")) {
                                    entity.setEnd_at(jsonObject2.getString("end_at"));
                                }
                                if (jsonObject2.has("title")) {
                                    entity.setTitle(jsonObject2.getString("title"));
                                }
                                if (jsonObject2.has("time_type")) {
                                    entity.setTime_type(jsonObject2.getString("time_type"));
                                }
                                if (jsonObject2.has("price")) {
                                    entity.setPrice(jsonObject2.getString("price"));
                                }
                                if (jsonObject2.has("is_subscribe")) {
                                    entity.setIs_subscribe(jsonObject2.getString("is_subscribe"));
                                }
                                if (jsonObject2.has("is_new")) {
                                    entity.setIs_new(jsonObject2.getString("is_new"));
                                }
                                if (jsonObject2.has("is_hot")) {
                                    entity.setIs_hot(jsonObject2.getString("is_hot"));
                                }
                                list.add(entity);
                            }
                            protocol.setList_un_start(list);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
