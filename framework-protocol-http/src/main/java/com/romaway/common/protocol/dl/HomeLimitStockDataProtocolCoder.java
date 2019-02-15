package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HomeLimitStockEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/19.
 */
public class HomeLimitStockDataProtocolCoder extends AProtocolCoder<HomeLimitStockDataProtocol> {
    @Override
    protected byte[] encode(HomeLimitStockDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HomeLimitStockDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HomeLimitStockDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("artilce_url")) {
                    protocol.resp_artilce_url = jsonObject.getString("artilce_url");
                }
                if (jsonObject.has("stock")) {
                    String str = jsonObject.getString("stock");
                    Logger.d("HomeLimitStockDataProtocolCoder", "decode >>> result str = " + str);
                    if (!TextUtils.isEmpty(str)) {
                        BaseJSONArray jsonArray = new BaseJSONArray(str);
                        ArrayList<HomeLimitStockEntity> list = new ArrayList<HomeLimitStockEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HomeLimitStockEntity entity = new HomeLimitStockEntity();
                            if (jsonObject1.has("title")) {
                                entity.setTitle(jsonObject1.getString("title"));
                            }
                            if (jsonObject1.has("code")) {
                                entity.setCode(jsonObject1.getString("code"));
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
