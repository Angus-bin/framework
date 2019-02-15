package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.QswyZxtcListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/10/8.
 */
public class QswyZxtcDataProtocolCoder extends AProtocolCoder<QswyZxtcDataProtocol> {
    @Override
    protected byte[] encode(QswyZxtcDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(QswyZxtcDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("QswyZxtcDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("QswyZxtcDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("id")) {
                        protocol.resp_id = jsonObject1.getString("id");
                    }
                    if (jsonObject1.has("items")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("items"));
                        if (jsonObject2.has("new_date")) {
                            protocol.resp_new_date = jsonObject2.getString("new_date");
                        }
                        if (jsonObject2.has("new_option")) {
                            BaseJSONArray jsonArray = jsonObject2.getJSONArray("new_option");
                            ArrayList<QswyZxtcListEntity> list = new ArrayList<QswyZxtcListEntity>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject3 = jsonArray.getJSONObject(i);
                                QswyZxtcListEntity entity = new QswyZxtcListEntity();
                                if (jsonObject3.has("id")) {
                                    entity.setId(jsonObject3.getString("id"));
                                }
                                if (jsonObject3.has("type")) {
                                    entity.setType(jsonObject3.getString("type"));
                                }
                                if (jsonObject3.has("title")) {
                                    entity.setTitle(jsonObject3.getString("title"));
                                }
                                if (jsonObject3.has("code")) {
                                    entity.setCode(jsonObject3.getString("code"));
                                }
                                if (jsonObject3.has("price")) {
                                    entity.setPrice(jsonObject3.getString("price"));
                                }
                                if (jsonObject3.has("increase")) {
                                    entity.setIncrease(jsonObject3.getString("increase"));
                                }
                                if (jsonObject3.has("time")) {
                                    entity.setTime(jsonObject3.getString("time"));
                                }
                                if (jsonObject3.has("is_hold")) {
                                    entity.setIs_hold(jsonObject3.getString("is_hold"));
                                }
                                list.add(entity);
                            }
                            protocol.setList(list);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
