package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.ProductServiceEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/30.
 */
public class ProduceServiceListDataProtocolCoder extends AProtocolCoder<ProduceServiceListDataProtocol> {
    @Override
    protected byte[] encode(ProduceServiceListDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(ProduceServiceListDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("ProduceServiceListDataProtocolCoder", "decode >>> result body = " + result);
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
                    String str = jsonObject.getString("data");
                    Logger.d("ProduceServiceListDataProtocolCoder", "str = " + str);
                    if (!TextUtils.isEmpty(str)) {
                        BaseJSONArray jsonArray = new BaseJSONArray(str);
                        ArrayList<ProductServiceEntity> list = new ArrayList<ProductServiceEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            ProductServiceEntity entity = new ProductServiceEntity();
                            if (jsonObject1.has("funCode")) {
                                entity.setFunCode(jsonObject1.getString("funCode"));
                            }
                            if (jsonObject1.has("img_url")) {
                                entity.setImg_url(jsonObject1.getString("img_url"));
                            }
                            if (jsonObject1.has("link_url")) {
                                entity.setLink_url(jsonObject1.getString("link_url"));
                            }
                            if (jsonObject1.has("isBook")) {
                                entity.setIsBook(jsonObject1.getString("isBook"));
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
