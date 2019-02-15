package com.romaway.common.protocol.zx;

import android.text.TextUtils;

import com.romalibs.utils.SharedPreferenceUtils;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.zx.entity.RealTimeZiXunEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/7.
 */
public class RealTimeZiXunProtocolCoder extends AProtocolCoder<RealTimeZiXunProtocol> {
    @Override
    protected byte[] encode(RealTimeZiXunProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(RealTimeZiXunProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("RealTimeZiXunProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("RealTimeZiXunProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("items")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
                        ArrayList<RealTimeZiXunEntity> list = new ArrayList<RealTimeZiXunEntity>();
                        protocol.resp_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            RealTimeZiXunEntity entity = new RealTimeZiXunEntity();
                            if (jsonObject2.has("prosandcons")) {
                                entity.setProsandcons(jsonObject2.getString("prosandcons"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
                            }
                            if (jsonObject2.has("intro")) {
                                entity.setIntro(jsonObject2.getString("intro"));
                            }
                            if (jsonObject2.has("weburl")) {
                                entity.setWeburl(jsonObject2.getString("weburl"));
                            }
                            if (jsonObject2.has("publishtime")) {
                                entity.setPublishtime(jsonObject2.getString("publishtime"));
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
