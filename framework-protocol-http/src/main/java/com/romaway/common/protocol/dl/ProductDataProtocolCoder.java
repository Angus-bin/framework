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
 * Created by hongrb on 2017/9/12.
 */
public class ProductDataProtocolCoder extends AProtocolCoder<ProductDataProtocol> {
    @Override
    protected byte[] encode(ProductDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(ProductDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("ProductDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("ProductDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("items")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("items"));
                        // 基本数据
                        if (jsonObject2.has("base")) {
                            BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("base"));
                        }
                        if (jsonObject2.has("id")) {
                            protocol.resp_id = jsonObject2.getString("id");
                        }
                        if (jsonObject2.has("title")) {
                            protocol.resp_title = jsonObject2.getString("title");
                        }
                        if (jsonObject2.has("sub_title")) {
                            protocol.resp_sub_title = jsonObject2.getString("sub_title");
                        }
                        if (jsonObject2.has("zhuanantese")) {
                            protocol.resp_zhuanantese = jsonObject2.getString("zhuanantese");
                        }
                        if (jsonObject2.has("zhisun")) {
                            protocol.resp_zhisun = jsonObject2.getString("zhisun");
                        }
                        if (jsonObject2.has("intro")) {
                            protocol.resp_intro = jsonObject2.getString("intro");
                        }
                        if (jsonObject2.has("qishu")) {
                            protocol.resp_qishu = jsonObject2.getString("qishu");
                        }
                        if (jsonObject2.has("days")) {
                            protocol.resp_days = jsonObject2.getString("days");
                        }
                        if (jsonObject2.has("price")) {
                            protocol.resp_price = jsonObject2.getString("price");
                        }
                        if (jsonObject2.has("allow_num")) {
                            protocol.resp_allow_num = jsonObject2.getString("allow_num");
                        }
                        if (jsonObject2.has("in_num")) {
                            protocol.resp_in_num = jsonObject2.getString("in_num");
                        }
                        if (jsonObject2.has("service_time")) {
                            protocol.resp_service_time = jsonObject2.getString("service_time");
                        }
                        if (jsonObject2.has("less_time")) {
                            protocol.resp_less_time = jsonObject2.getString("less_time");
                        }
                        if (jsonObject2.has("is_subscribe")) {
                            protocol.resp_is_subscribe = jsonObject2.getString("is_subscribe");
                        }
                        if (jsonObject2.has("new_sum")) {
                            protocol.resp_new_sum = jsonObject2.getString("new_sum");
                        }
                        if (jsonObject2.has("quarter_sum")) {
                            protocol.resp_quarter_sum = jsonObject2.getString("quarter_sum");
                        }
                        if (jsonObject2.has("month_sum")) {
                            protocol.resp_month_sum = jsonObject2.getString("month_sum");
                        }
                        if (jsonObject2.has("year_sum")) {
                            protocol.resp_year_sum = jsonObject2.getString("year_sum");
                        }
                        if (jsonObject2.has("five_sum")) {
                            protocol.resp_five_sum = jsonObject2.getString("five_sum");
                        }
                        if (jsonObject2.has("pro_sum")) {
                            protocol.resp_pro_sum = jsonObject2.getString("pro_sum");
                        }
                        if (jsonObject2.has("sort_sum")) {
                            protocol.resp_sort_sum = jsonObject2.getString("sort_sum");
                        }
                        if (jsonObject2.has("total_space")) {
                            protocol.resp_total_space = jsonObject2.getString("total_space");
                        }
                        if (jsonObject2.has("time_type")) {
                            protocol.resp_time_type = jsonObject2.getString("time_type");
                        }
                        if (jsonObject2.has("start_at")) {
                            protocol.resp_start_at = jsonObject2.getString("start_at");
                        }
                        if (jsonObject2.has("end_at")) {
                            protocol.resp_end_at = jsonObject2.getString("end_at");
                        }
                        if (jsonObject2.has("service_desc")) {
                            protocol.resp_service_desc = jsonObject2.getString("service_desc");
                        }
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
                                if (jsonObject5.has("price")) {
                                    entity.setPrice(jsonObject5.getString("price"));
                                }
                                if (jsonObject5.has("time")) {
                                    entity.setTime(jsonObject5.getString("time"));
                                }
                                if (jsonObject5.has("increase")) {
                                    entity.setIncrease(jsonObject5.getString("increase"));
                                }
                                if (jsonObject5.has("now_increase")) {
                                    entity.setNow_increase(jsonObject5.getString("now_increase"));
                                }
                                if (jsonObject5.has("cangwei")) {
                                    entity.setCangwei(jsonObject5.getString("cangwei"));
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
