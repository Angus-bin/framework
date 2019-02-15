package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.BannerInfoEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/5/31.
 */
public class BannerInfoProtocolCoder extends AProtocolCoder<BannerInfoProtocol> {
    @Override
    protected byte[] encode(BannerInfoProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(BannerInfoProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("BannerInfoProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("BannerInfoProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("items")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                        ArrayList<BannerInfoEntity> list = new ArrayList<BannerInfoEntity>();
                        protocol.resp_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            BannerInfoEntity entity = new BannerInfoEntity();
                            if (jsonObject2.has("imgurl")) {
                                entity.setImgUrl(jsonObject2.getString("imgurl"));
                            }
                            if (jsonObject2.has("weburl")) {
                                entity.setWebUrl(jsonObject2.getString("weburl"));
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
