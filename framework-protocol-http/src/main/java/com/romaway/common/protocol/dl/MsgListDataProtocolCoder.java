package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.MsgListDataEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/9/28.
 */
public class MsgListDataProtocolCoder extends AProtocolCoder<MsgListDataProtocol> {
    @Override
    protected byte[] encode(MsgListDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(MsgListDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("MsgListDataProtocolCoder", "decode >>> result body = " + result);
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
                    Logger.d("MsgListDataProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("item")) {
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject1.getString("item"));
                        ArrayList<MsgListDataEntity> list = new ArrayList<MsgListDataEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            MsgListDataEntity entity = new MsgListDataEntity();
                            if (jsonObject2.has("id")) {
                                entity.setId(jsonObject2.getString("id"));
                            }
                            if (jsonObject2.has("item_id")) {
                                entity.setItem_id(jsonObject2.getString("item_id"));
                            }
                            if (jsonObject2.has("title")) {
                                entity.setTitle(jsonObject2.getString("title"));
                            }
                            if (jsonObject2.has("content")) {
                                entity.setContent(jsonObject2.getString("content"));
                            }
                            if (jsonObject2.has("url")) {
                                entity.setUrl(jsonObject2.getString("url"));
                            }
                            if (jsonObject2.has("time")) {
                                entity.setTime(jsonObject2.getString("time"));
                            }
                            if (jsonObject2.has("type")) {
                                entity.setType(jsonObject2.getString("type"));
                            }
                            if (jsonObject2.has("data")) {
                                String data = jsonObject2.getString("data");
                                if (!TextUtils.isEmpty(data) && StringUtils.isJsonObject(data)) {
                                    BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("data"));
                                    if (jsonObject3.has("stock_title")) {
                                        entity.setStock_title(jsonObject3.getString("stock_title"));
                                    }
                                    if (jsonObject3.has("stock_code")) {
                                        entity.setStock_code(jsonObject3.getString("stock_code"));
                                    }
                                    if (jsonObject3.has("stock_id")) {
                                        entity.setStock_id(jsonObject3.getString("stock_id"));
                                    }
                                    if (jsonObject3.has("pro_id")) {
                                        entity.setPro_id(jsonObject3.getString("pro_id"));
                                    }
                                    if (jsonObject3.has("allow_look")) {
                                        entity.setAllow_look(jsonObject3.getString("allow_look"));
                                    }
                                }
                            }
                            if (jsonObject2.has("qishu")) {
                                entity.setQishu(jsonObject2.getString("qishu"));
                            }
                            if (jsonObject2.has("status")) {
                                entity.setStatus(jsonObject2.getString("status"));
                            }
                            if (jsonObject2.has("is_self")) {
                                entity.setIs_self(jsonObject2.getString("is_self"));
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
