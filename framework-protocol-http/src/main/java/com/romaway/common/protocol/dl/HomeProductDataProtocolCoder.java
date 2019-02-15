package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeProductDataEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/12.
 */
public class HomeProductDataProtocolCoder extends AProtocolCoder<HomeProductDataProtocol> {
    @Override
    protected byte[] encode(HomeProductDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeProductDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeProductDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("HomeProductDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("items")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                        ArrayList<HomeProductDataEntity> list = new ArrayList<HomeProductDataEntity>();
                        protocol.resp_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            HomeProductDataEntity entity = new HomeProductDataEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
                            }
                            if (jsonObject2.has("sub_title")) {
                                entity.setSub_title(jsonObject2.getString("sub_title"));
                            }
                            if (jsonObject2.has("intro")) {
                                entity.setIntro(jsonObject2.getString("intro"));
                            }
                            if (jsonObject2.has("qishu")) {
                                entity.setQishu(jsonObject2.getString("qishu"));
                            }
                            if (jsonObject2.has("days")) {
                                entity.setDays(jsonObject2.getString("days"));
                            }
                            if (jsonObject2.has("price")) {
                                entity.setPrice(jsonObject2.getString("price"));
                            }
                            if (jsonObject2.has("allow_num")) {
                                entity.setAllow_num(jsonObject2.getString("allow_num"));
                            }
                            if (jsonObject2.has("remain_num")) {
                                entity.setRemain_num(jsonObject2.getString("remain_num"));
                            }
                            if (jsonObject2.has("service_time")) {
                                entity.setService_time(jsonObject2.getString("service_time"));
                            }
                            if (jsonObject2.has("less_time")) {
                                entity.setLess_time(jsonObject2.getString("less_time"));
                            }
                            if (jsonObject2.has("is_subscribe")) {
                                entity.setIs_subscribe(jsonObject2.getString("is_subscribe"));
                            }
                            if (jsonObject2.has("new_sum")) {
                                entity.setNew_sum(jsonObject2.getString("new_sum"));
                            }
                            if (jsonObject2.has("pro_sum")) {
                                entity.setPro_sum(jsonObject2.getString("pro_sum"));
                            }
                            if (jsonObject2.has("sort_sum")) {
                                entity.setSort_sum(jsonObject2.getString("sort_sum"));
                            }
                            if (jsonObject2.has("total_income")) {
                                entity.setTotal_income(jsonObject2.getString("total_income"));
                            }
                            if (jsonObject2.has("space_rate")) {
                                entity.setSpace_rate(jsonObject2.getString("space_rate"));
                            }
                            if (jsonObject2.has("data")) {
                                BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("data"));
                                if (jsonObject3.has("id")) {
                                    entity.setData_id(jsonObject3.getString("id"));
                                }
                                if (jsonObject3.has("type")) {
                                    entity.setData_type(jsonObject3.getString("type"));
                                }
                                if (jsonObject3.has("title")) {
                                    entity.setData_title(jsonObject3.getString("title"));
                                }
                                if (jsonObject3.has("code")) {
                                    entity.setData_code(jsonObject3.getString("code"));
                                }
                                if (jsonObject3.has("price")) {
                                    entity.setData_price(jsonObject3.getString("price"));
                                }
                                if (jsonObject3.has("time")) {
                                    entity.setData_time(jsonObject3.getString("time"));
                                }
                                if (jsonObject3.has("increase")) {
                                    entity.setData_increase(jsonObject3.getString("increase"));
                                }
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
