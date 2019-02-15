package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockHxtcEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/8.
 */
public class StockDetailHxtcDataProtocolCoder extends AProtocolCoder<StockDetailHxtcDataProtocol> {
    @Override
    protected byte[] encode(StockDetailHxtcDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailHxtcDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailTenPartnerDateProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONArray jsonArray = new BaseJSONArray(result);
                ArrayList<StockHxtcEntity> list = new ArrayList<StockHxtcEntity>();
                int count = jsonArray.length();
                for (int i = 0; i < count; i++) {
                    BaseJSONObject jsonObject = jsonArray.getJSONObject(i);
                    StockHxtcEntity entity = new StockHxtcEntity();
                    if (jsonObject.has("title")) {
                        entity.setTitle(jsonObject.getString("title"));
                    }
                    if (jsonObject.has("content")) {
                        entity.setContent(jsonObject.getString("content"));
                    }
                    list.add(entity);
                }
                protocol.setList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
