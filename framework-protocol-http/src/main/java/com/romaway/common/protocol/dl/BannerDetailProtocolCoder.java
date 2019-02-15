package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.BannerDetailEntity;
import com.romaway.common.protocol.tougu.BannerInfoProtocol;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/14.
 */
public class BannerDetailProtocolCoder extends AProtocolCoder<BannerDetailProtocol> {
    @Override
    protected byte[] encode(BannerDetailProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(BannerDetailProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("BannerDetailProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)){
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
                    Logger.d("BannerDetailProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("item")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("item");
                        ArrayList<BannerDetailEntity> list = new ArrayList<BannerDetailEntity>();
                        protocol.resp_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            BannerDetailEntity entity = new BannerDetailEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("jump_url")) {
                                entity.setJump_url(jsonObject2.getString("jump_url"));
                            }
                            if (jsonObject2.has("pic_url")) {
                                entity.setPic_url(jsonObject2.getString("pic_url"));
                            }
                            if (jsonObject2.has("funcode")) {
                                entity.setFunCode(jsonObject2.getString("funcode"));
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
