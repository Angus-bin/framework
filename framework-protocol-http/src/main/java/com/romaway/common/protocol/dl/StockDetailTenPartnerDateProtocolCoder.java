package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockTenPartnerEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/7.
 */
public class StockDetailTenPartnerDateProtocolCoder extends AProtocolCoder<StockDetailTenPartnerDateProtocol> {
    @Override
    protected byte[] encode(StockDetailTenPartnerDateProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailTenPartnerDateProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailTenPartnerDateProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("data")) {
                    BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("data"));
                    ArrayList<StockTenPartnerEntity> list = new ArrayList<StockTenPartnerEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockTenPartnerEntity entity = new StockTenPartnerEntity();
                        if (jsonObject1.has("date")) {
                            entity.setDate(jsonObject1.getString("date"));
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
