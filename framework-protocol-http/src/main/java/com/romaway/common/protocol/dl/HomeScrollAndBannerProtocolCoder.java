package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeScrollAndBannerEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/6/6.
 */
public class HomeScrollAndBannerProtocolCoder extends AProtocolCoder<HomeScrollAndBannerProtocol> {
    @Override
    protected byte[] encode(HomeScrollAndBannerProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeScrollAndBannerProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeScrollAndBannerProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("HomeScrollAndBannerProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("adpicture")) {
                        protocol.resp_adpicture = jsonObject1.getString("adpicture");
                    }
                    if (jsonObject1.has("adweburl")) {
                        protocol.resp_adweburl = jsonObject1.getString("adweburl");
                    }
                    BaseJSONArray jsonArray = jsonObject1.getJSONArray("list");
                    ArrayList<HomeScrollAndBannerEntity> list = new ArrayList<HomeScrollAndBannerEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        HomeScrollAndBannerEntity item = new HomeScrollAndBannerEntity();
                        if (jsonObject2.has("bannerpicture")) {
                            item.setBannerpicture(jsonObject2.getString("bannerpicture"));
                        }
                        if (jsonObject2.has("bannerweburl")) {
                            item.setBannerweburl(jsonObject2.getString("bannerweburl"));
                        }
                        list.add(item);
                    }
                    protocol.setList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
