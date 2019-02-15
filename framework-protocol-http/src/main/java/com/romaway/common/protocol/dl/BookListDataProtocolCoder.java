package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.BookQswtItemEntity;
import com.romaway.common.protocol.dl.entity.BookQswyEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/18.
 */
public class BookListDataProtocolCoder extends AProtocolCoder<BookListDataProtocol> {
    @Override
    protected byte[] encode(BookListDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(BookListDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("BookListDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("BookListDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    // 金牌股票池
                    if (jsonObject1.has("jpgpc")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("jpgpc"));
                        if (jsonObject2.has("title")) {
                            protocol.resp_gold_title = jsonObject2.getString("title");
                        }
                        if (jsonObject2.has("sub_title")) {
                            protocol.resp_gold_sub_title = jsonObject2.getString("sub_title");
                        }
                        if (jsonObject2.has("in_num")) {
                            protocol.resp_gold_in_num = jsonObject2.getString("in_num");
                        }
                        if (jsonObject2.has("new_sum")) {
                            protocol.resp_gold_new_sum = jsonObject2.getString("new_sum");
                        }
                        if (jsonObject2.has("month_sum")) {
                            protocol.resp_gold_month_sum = jsonObject2.getString("month_sum");
                        }
                        if (jsonObject2.has("year_sum")) {
                            protocol.resp_gold_year_sum = jsonObject2.getString("year_sum");
                        }
                        if (jsonObject2.has("quarter_sum")) {
                            protocol.resp_gold_quarter_sum = jsonObject2.getString("quarter_sum");
                        }
                        if (jsonObject2.has("less_time")) {
                            protocol.resp_gold_less_time = jsonObject2.getString("less_time");
                        }
                    }
                    // 趋势稳盈
                    if (jsonObject1.has("qswy")) {
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject1.getString("qswy"));
                        ArrayList<BookQswyEntity> list = new ArrayList<BookQswyEntity>();
                        Logger.d("BookListDataProtocolCoder", "jsonArray.length() = " + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            BookQswyEntity entity = new BookQswyEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
                            }
                            if (jsonObject2.has("sub_title")) {
                                entity.setSub_title(jsonObject2.getString("sub_title"));
                            }
                            if (jsonObject2.has("less_time")) {
                                entity.setLess_time(jsonObject2.getString("less_time"));
                            }
                            if (jsonObject2.has("intro")) {
                                entity.setIntro(jsonObject2.getString("intro"));
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
                            if (jsonObject2.has("space_rate")) {
                                entity.setSpace_rate(jsonObject2.getString("space_rate"));
                            }
                            if (jsonObject2.has("items")) {
                                BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("items"));
                                if (jsonObject3.has("new_in_option")) {
                                    BaseJSONArray jsonArray1 = new BaseJSONArray(jsonObject3.getString("new_in_option"));
                                    ArrayList<BookQswtItemEntity> list_item = new ArrayList<BookQswtItemEntity>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        BaseJSONObject jsonObject4 = jsonArray1.getJSONObject(j);
                                        BookQswtItemEntity itemEntity = new BookQswtItemEntity();
                                        if (jsonObject4.has("id")) {
                                            itemEntity.setId(jsonObject4.getString("id"));
                                        }
                                        if (jsonObject4.has("type")) {
                                            itemEntity.setType(jsonObject4.getString("type"));
                                        }
                                        if (jsonObject4.has("title")) {
                                            itemEntity.setTitle(jsonObject4.getString("title"));
                                        }
                                        if (jsonObject4.has("code")) {
                                            itemEntity.setCode(jsonObject4.getString("code"));
                                        }
                                        if (jsonObject4.has("price")) {
                                            itemEntity.setPrice(jsonObject4.getString("price"));
                                        }
                                        if (jsonObject4.has("time")) {
                                            itemEntity.setTime(jsonObject4.getString("time"));
                                        }
                                        if (jsonObject4.has("increase")) {
                                            itemEntity.setIncrease(jsonObject4.getString("increase"));
                                        }
                                        if (jsonObject4.has("now_increase")) {
                                            itemEntity.setNow_increase(jsonObject4.getString("now_increase"));
                                        }
                                        if (jsonObject4.has("cangwei")) {
                                            itemEntity.setCangwei(jsonObject4.getString("cangwei"));
                                        }
                                        list_item.add(itemEntity);
                                    }
                                    entity.setList(list_item);
                                }
                            }
                            list.add(entity);
                        }
                        protocol.setList(list);
                    }
                    // 涨停点睛
                    if (jsonObject1.has("ztdj")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("ztdj"));
                        if (jsonObject2.has("title")) {
                            protocol.resp_limit_title = jsonObject2.getString("title");
                        }
                        if (jsonObject2.has("sub_title")) {
                            protocol.resp_limit_sub_title = jsonObject2.getString("sub_title");
                        }
                        if (jsonObject2.has("in_num")) {
                            protocol.resp_limit_in_num = jsonObject2.getString("in_num");
                        }
                        if (jsonObject2.has("new_sum")) {
                            protocol.resp_limit_new_sum = jsonObject2.getString("new_sum");
                        }
                        if (jsonObject2.has("month_sum")) {
                            protocol.resp_limit_month_sum = jsonObject2.getString("month_sum");
                        }
                        if (jsonObject2.has("year_sum")) {
                            protocol.resp_limit_year_sum = jsonObject2.getString("year_sum");
                        }
                        if (jsonObject2.has("five_sum")) {
                            protocol.resp_limit_five_sum = jsonObject2.getString("five_sum");
                        }
                    }
                    // AI股票池
                    if (jsonObject1.has("aijqr")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("aijqr"));
                        if (jsonObject2.has("title")) {
                            protocol.resp_ai_title = jsonObject2.getString("title");
                        }
                        if (jsonObject2.has("sub_title")) {
                            protocol.resp_ai_sub_title = jsonObject2.getString("sub_title");
                        }
                        if (jsonObject2.has("in_num")) {
                            protocol.resp_ai_in_num = jsonObject2.getString("in_num");
                        }
                        if (jsonObject2.has("new_sum")) {
                            protocol.resp_ai_new_sum = jsonObject2.getString("new_sum");
                        }
                        if (jsonObject2.has("month_sum")) {
                            protocol.resp_ai_month_sum = jsonObject2.getString("month_sum");
                        }
                        if (jsonObject2.has("year_sum")) {
                            protocol.resp_ai_year_sum = jsonObject2.getString("year_sum");
                        }
                        if (jsonObject2.has("quarter_sum")) {
                            protocol.resp_ai_quarter_sum = jsonObject2.getString("quarter_sum");
                        }
                        if (jsonObject2.has("less_time")) {
                            protocol.resp_ai_less_time = jsonObject2.getString("less_time");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
