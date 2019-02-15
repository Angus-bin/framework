package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeProductCleverEntity;
import com.romaway.common.protocol.dl.entity.HomeProductSpecialistEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/11/16.
 */
public class HomeProductDataProtocolNewCoder extends AProtocolCoder<HomeProductDataProtocolNew> {
    @Override
    protected byte[] encode(HomeProductDataProtocolNew protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeProductDataProtocolNew protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeProductDataProtocolNewCoder", "decode >>> result body = " + result);
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
                    if (jsonObject1.has("zhuanjiaxuangu")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("zhuanjiaxuangu"));
                        if (jsonObject2.has("info")) {
                            BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("info"));
                            if (jsonObject3.has("title")) {
                                protocol.resp_specialist_title = jsonObject3.getString("title");
                            }
                            if (jsonObject3.has("subtitle")) {
                                protocol.resp_specialist_subtitle = jsonObject3.getString("subtitle");
                            }
                            if (jsonObject3.has("id")) {
                                protocol.resp_specialist_id = jsonObject3.getString("id");
                            }
                        }
                        if (jsonObject2.has("item")) {
                            BaseJSONArray jsonArray = jsonObject2.getJSONArray("item");
                            ArrayList<HomeProductSpecialistEntity> list = new ArrayList<HomeProductSpecialistEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject3 = jsonArray.getJSONObject(i);
                                HomeProductSpecialistEntity entity = new HomeProductSpecialistEntity();
                                if (jsonObject3.has("id")) {
                                    entity.setId(jsonObject3.getString("id"));
                                }
                                if (jsonObject3.has("zhiying")) {
                                    entity.setZhiying(jsonObject3.getString("zhiying"));
                                }
                                if (jsonObject3.has("zhisun")) {
                                    entity.setZhisun(jsonObject3.getString("zhisun"));
                                }
                                if (jsonObject3.has("increase")) {
                                    entity.setIncrease(jsonObject3.getString("increase"));
                                }
                                if (jsonObject3.has("day")) {
                                    entity.setDay(jsonObject3.getString("day"));
                                }
                                if (jsonObject3.has("being_day")) {
                                    entity.setBeing_day(jsonObject3.getString("being_day"));
                                }
                                if (jsonObject3.has("subscribe_nums")) {
                                    entity.setSubscribe_nums(jsonObject3.getString("subscribe_nums"));
                                }
                                if (jsonObject3.has("start_at")) {
                                    entity.setStart_at(jsonObject3.getString("start_at"));
                                }
                                if (jsonObject3.has("end_at")) {
                                    entity.setEnd_at(jsonObject3.getString("end_at"));
                                }
                                if (jsonObject3.has("title")) {
                                    entity.setTitle(jsonObject3.getString("title"));
                                }
                                if (jsonObject3.has("time_type")) {
                                    entity.setTime_type(jsonObject3.getString("time_type"));
                                }
                                if (jsonObject3.has("price")) {
                                    entity.setPrice(jsonObject3.getString("price"));
                                }
                                list.add(entity);
                            }
                            protocol.setList(list);
                        }
                    }
                    if (jsonObject1.has("zhinengxuangu")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("zhinengxuangu"));
                        if (jsonObject2.has("info")) {
                            BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("info"));
                            if (jsonObject3.has("title")) {
                                protocol.resp_clever_title = jsonObject3.getString("title");
                            }
                            if (jsonObject3.has("subtitle")) {
                                protocol.resp_clever_subtitle = jsonObject3.getString("subtitle");
                            }
                            if (jsonObject3.has("id")) {
                                protocol.resp_clever_id = jsonObject3.getString("id");
                            }
                        }
                        if (jsonObject2.has("item")) {
                            BaseJSONArray jsonArray = jsonObject2.getJSONArray("item");
                            ArrayList<HomeProductCleverEntity> list = new ArrayList<HomeProductCleverEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject3 = jsonArray.getJSONObject(i);
                                HomeProductCleverEntity entity = new HomeProductCleverEntity();
                                if (jsonObject3.has("id")) {
                                    entity.setId(jsonObject3.getString("id"));
                                }
                                if (jsonObject3.has("zhiying")) {
                                    entity.setZhiying(jsonObject3.getString("zhiying"));
                                }
                                if (jsonObject3.has("zhisun")) {
                                    entity.setZhisun(jsonObject3.getString("zhisun"));
                                }
                                if (jsonObject3.has("increase")) {
                                    entity.setIncrease(jsonObject3.getString("increase"));
                                }
                                if (jsonObject3.has("day")) {
                                    entity.setDay(jsonObject3.getString("day"));
                                }
                                if (jsonObject3.has("being_day")) {
                                    entity.setBeing_day(jsonObject3.getString("being_day"));
                                }
                                if (jsonObject3.has("subscribe_nums")) {
                                    entity.setSubscribe_nums(jsonObject3.getString("subscribe_nums"));
                                }
                                if (jsonObject3.has("start_at")) {
                                    entity.setStart_at(jsonObject3.getString("start_at"));
                                }
                                if (jsonObject3.has("end_at")) {
                                    entity.setEnd_at(jsonObject3.getString("end_at"));
                                }
                                if (jsonObject3.has("title")) {
                                    entity.setTitle(jsonObject3.getString("title"));
                                }
                                if (jsonObject3.has("time_type")) {
                                    entity.setTime_type(jsonObject3.getString("time_type"));
                                }
                                if (jsonObject3.has("price")) {
                                    entity.setPrice(jsonObject3.getString("price"));
                                }
                                list.add(entity);
                            }
                            protocol.setList_clever(list);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
