package com.romaway.common.protocol.zx;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.zx.entity.NewsTopBarListEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3.
 */
public class NewsTopBarListProtocolCoder extends AProtocolCoder<NewsTopBarListProtocol> {
    @Override
    protected byte[] encode(NewsTopBarListProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(NewsTopBarListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("NewsTopBarListProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)){
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("item")) {
                    BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("item"));
                    ArrayList<NewsTopBarListEntity> list = new ArrayList<NewsTopBarListEntity>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        NewsTopBarListEntity entity = new NewsTopBarListEntity();
                        if (jsonObject1.has("title")) {
                            entity.setTitle(jsonObject1.getString("title"));
                        }
                        if (jsonObject1.has("id")) {
                            entity.setId(jsonObject1.getString("id"));
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
