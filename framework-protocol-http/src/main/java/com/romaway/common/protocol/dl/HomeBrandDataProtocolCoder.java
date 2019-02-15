package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeBrandEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/11/24.
 */
public class HomeBrandDataProtocolCoder extends AProtocolCoder<HomeBrandDataProtocol> {
    @Override
    protected byte[] encode(HomeBrandDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeBrandDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeBrandDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("HomeBrandDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONArray jsonArray = new BaseJSONArray(DES3.decode(DES3.decodeHex(str)));
                    ArrayList<HomeBrandEntity> list = new ArrayList<HomeBrandEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HomeBrandEntity entity = new HomeBrandEntity();
                        if (jsonObject1.has("title")) {
                            entity.setTitle(jsonObject1.getString("title"));
                        }
                        if (jsonObject1.has("intro")) {
                            entity.setIntro(jsonObject1.getString("intro"));
                        }
                        if (jsonObject1.has("url")) {
                            entity.setUrl(jsonObject1.getString("url"));
                        }
                        if (jsonObject1.has("img")) {
                            entity.setImg(jsonObject1.getString("img"));
                        }
                        list.add(entity);
                    }
                    protocol.setList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
