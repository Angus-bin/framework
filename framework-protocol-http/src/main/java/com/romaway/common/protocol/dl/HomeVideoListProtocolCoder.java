package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeVideoEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/15.
 */
public class HomeVideoListProtocolCoder extends AProtocolCoder<HomeVideoListProtocol> {
    @Override
    protected byte[] encode(HomeVideoListProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeVideoListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeVideoListProtocolCoder", "decode >>> result body = " + result);
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
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("items")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                        ArrayList<HomeVideoEntity> list = new ArrayList<HomeVideoEntity>();
                        protocol.resp_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            HomeVideoEntity entity = new HomeVideoEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
                            }
                            if (jsonObject2.has("intro")) {
                                entity.setIntro(jsonObject2.getString("intro"));
                            }
                            if (jsonObject2.has("image")) {
                                entity.setImage(jsonObject2.getString("image"));
                            }
                            if (jsonObject2.has("publishtime")) {
                                entity.setPublishtime(jsonObject2.getString("publishtime"));
                            }
                            if (jsonObject2.has("readcounts")) {
                                entity.setReadcounts(jsonObject2.getString("readcounts"));
                            }
                            if (jsonObject2.has("heatlevel")) {
                                entity.setHeatlevel(jsonObject2.getString("heatlevel"));
                            }
                            if (jsonObject2.has("goodnum")) {
                                entity.setGoodnum(jsonObject2.getString("goodnum"));
                            }
                            if (jsonObject2.has("videourl")) {
                                entity.setVideourl(jsonObject2.getString("videourl"));
                            }
                            if (jsonObject2.has("isgood")) {
                                entity.setIsgood(jsonObject2.getString("isgood"));
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
