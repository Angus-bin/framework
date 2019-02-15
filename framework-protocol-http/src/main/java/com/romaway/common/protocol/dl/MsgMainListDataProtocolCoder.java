package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.MsgListDataEntity;
import com.romaway.common.protocol.dl.entity.MsgMainListDataEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/28.
 */
public class MsgMainListDataProtocolCoder extends AProtocolCoder<MsgMainListDataProtocol> {
    @Override
    protected byte[] encode(MsgMainListDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(MsgMainListDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("MsgMainListDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("MsgMainListDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("item")) {
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject1.getString("item"));
                        ArrayList<MsgMainListDataEntity> list = new ArrayList<MsgMainListDataEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            MsgMainListDataEntity entity = new MsgMainListDataEntity();
                            if (jsonObject2.has("sum")) {
                                entity.setSum(jsonObject2.getString("sum"));
                            }
                            if (jsonObject2.has("module_id")) {
                                entity.setModule_id(jsonObject2.getString("module_id"));
                            }
                            if (jsonObject2.has("content")) {
                                entity.setContent(jsonObject2.getString("content"));
                            }
                            if (jsonObject2.has("created_at")) {
                                entity.setCreated_at(jsonObject2.getString("created_at"));
                            }
                            if (jsonObject2.has("pro_id")) {
                                entity.setPro_id(jsonObject2.getString("pro_id"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
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
