package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeNewsListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/6/6.
 */
public class HomeNewsListProtocolCoder extends AProtocolCoder<HomeNewsListProtocol> {
    @Override
    protected byte[] encode(HomeNewsListProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeNewsListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeNewsListProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("HomeNewsListProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
//                    BaseJSONArray jsonArray = new BaseJSONArray(DES3.decode(DES3.decodeHex(str)));
//                    ArrayList<HomeNewsListEntity> list = new ArrayList<HomeNewsListEntity>();
//                    int count = jsonArray.length();
//                    for (int i = 0; i < count; i++) {
//                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        HomeNewsListEntity item = new HomeNewsListEntity();
//                        if (jsonObject1.has("articletitle")) {
//                            item.setArticletitle(jsonObject1.getString("articletitle"));
//                        }
//                        if (jsonObject1.has("articleid")) {
//                            item.setArticleid(jsonObject1.getString("articleid"));
//                        }
//                        if (jsonObject1.has("url")) {
//                            item.setUrl(jsonObject1.getString("url"));
//                        }
//
//                        list.add(item);
//                    }
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("items")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                        ArrayList<HomeNewsListEntity> list = new ArrayList<HomeNewsListEntity>();
                        protocol.resp_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            HomeNewsListEntity entity = new HomeNewsListEntity();
                            if (jsonObject2.has("prosandcons")) {
                                entity.setProsandcons(jsonObject2.getString("prosandcons"));
                            }
                            if (jsonObject2.has("newsid")) {
                                entity.setNewsid(jsonObject2.getString("newsid"));
                            }
                            if (jsonObject2.has("newsname")) {
                                entity.setNewsname(jsonObject2.getString("newsname"));
                            }
                            if (jsonObject2.has("newsintro")) {
                                entity.setNewsintro(jsonObject2.getString("newsintro"));
                            }
                            if (jsonObject2.has("newsimage")) {
                                entity.setNewsimage(jsonObject2.getString("newsimage"));
                            }
                            if (jsonObject2.has("weburl")) {
                                entity.setWeburl(jsonObject2.getString("weburl"));
                            }
                            if (jsonObject2.has("time")) {
                                entity.setTime(jsonObject2.getString("time"));
                            }
                            if (jsonObject2.has("look")) {
                                entity.setLook(jsonObject2.getString("look"));
                            }
                            if (jsonObject2.has("is_read")) {
                                entity.setIsread(jsonObject2.getString("is_read"));
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
