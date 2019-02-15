package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockShuoBaHotCommentEntity;
import com.romaway.common.protocol.dl.entity.StockShuoBaHotReplyCommentEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaDetailHotCommentProtocolCoder extends AProtocolCoder<StockShuoBaDetailHotCommentProtocol> {
    @Override
    protected byte[] encode(StockShuoBaDetailHotCommentProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockShuoBaDetailHotCommentProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockShuoBaDetailHotCommentProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockShuoBaHotCommentEntity> list = new ArrayList<StockShuoBaHotCommentEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockShuoBaHotCommentEntity entity = new StockShuoBaHotCommentEntity();
                        if (jsonObject1.has("id")) {
                            entity.setId(jsonObject1.getString("id"));
                        }
                        if (jsonObject1.has("user_id")) {
                            entity.setUser_id(jsonObject1.getString("user_id"));
                        }
                        if (jsonObject1.has("key_id")) {
                            entity.setKey_id(jsonObject1.getString("key_id"));
                        }
                        if (jsonObject1.has("content")) {
                            entity.setContent(jsonObject1.getString("content"));
                        }
                        if (jsonObject1.has("created_at")) {
                            entity.setCreated_at(jsonObject1.getString("created_at"));
                        }
                        if (jsonObject1.has("type")) {
                            entity.setType(jsonObject1.getString("type"));
                        }
                        if (jsonObject1.has("praise")) {
                            entity.setPraise(jsonObject1.getString("praise"));
                        }
                        if (jsonObject1.has("source")) {
                            entity.setSource(jsonObject1.getString("source"));
                        }
                        if (jsonObject1.has("other")) {
                            entity.setOther(jsonObject1.getString("other"));
                        }
                        if (jsonObject1.has("is_praise")) {
                            entity.setIs_praise(jsonObject1.getString("is_praise"));
                        }
                        if (jsonObject1.has("avatar")) {
                            entity.setAvatar(jsonObject1.getString("avatar"));
                        }
                        if (jsonObject1.has("name")) {
                            entity.setName(jsonObject1.getString("name"));
                        }
                        if (jsonObject1.has("reply")) {
                            BaseJSONArray jsonArray1 = new BaseJSONArray(jsonObject1.getString("reply"));
                            ArrayList<StockShuoBaHotReplyCommentEntity> list_reply = new ArrayList<StockShuoBaHotReplyCommentEntity>();
                            int count2 = jsonArray1.length();
                            for (int j = 0; j < count2; j++) {
                                BaseJSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                StockShuoBaHotReplyCommentEntity entity1 = new StockShuoBaHotReplyCommentEntity();
                                if (jsonObject2.has("id")) {
                                    entity1.setId(jsonObject2.getString("id"));
                                }
                                if (jsonObject2.has("p_id")) {
                                    entity1.setP_id(jsonObject2.getString("p_id"));
                                }
                                if (jsonObject2.has("from_id")) {
                                    entity1.setFrom_id(jsonObject2.getString("from_id"));
                                }
                                if (jsonObject2.has("to_id")) {
                                    entity1.setTo_id(jsonObject2.getString("to_id"));
                                }
                                if (jsonObject2.has("content")) {
                                    entity1.setContent(jsonObject2.getString("content"));
                                }
                                if (jsonObject2.has("created_at")) {
                                    entity1.setCreated_at(jsonObject2.getString("created_at"));
                                }
                                if (jsonObject2.has("from_name")) {
                                    entity1.setFrom_name(jsonObject2.getString("from_name"));
                                }
                                if (jsonObject2.has("from_avatar")) {
                                    entity1.setFrom_avatar(jsonObject2.getString("from_avatar"));
                                }
                                if (jsonObject2.has("is_praise")) {
                                    entity1.setIs_praise(jsonObject2.getString("is_praise"));
                                }
                                if (jsonObject2.has("praise")) {
                                    entity1.setPraise(jsonObject2.getString("praise"));
                                }
                                if (jsonObject2.has("to_name")) {
                                    entity1.setTo_name(jsonObject2.getString("to_name"));
                                }
                                if (jsonObject2.has("to_avatar")) {
                                    entity1.setTo_avatar(jsonObject2.getString("to_avatar"));
                                }
                                list_reply.add(entity1);
                            }
                            entity.setList_reply(list_reply);
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
