package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockShuoBaReplyCommentListEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/8.
 */
public class StockShuoBaDetailCommentListProtocolCoder extends AProtocolCoder<StockShuoBaDetailCommentListProtocol> {
    @Override
    protected byte[] encode(StockShuoBaDetailCommentListProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockShuoBaDetailCommentListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockShuoBaDetailCommentListProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockShuoBaReplyCommentListEntity> list = new ArrayList<StockShuoBaReplyCommentListEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockShuoBaReplyCommentListEntity entity = new StockShuoBaReplyCommentListEntity();
                        if (jsonObject1.has("id")) {
                            entity.setId(jsonObject1.getString("id"));
                        }
                        if (jsonObject1.has("p_id")) {
                            entity.setP_id(jsonObject1.getString("p_id"));
                        }
                        if (jsonObject1.has("from_id")) {
                            entity.setFrom_id(jsonObject1.getString("from_id"));
                        }
                        if (jsonObject1.has("to_id")) {
                            entity.setTo_id(jsonObject1.getString("to_id"));
                        }
                        if (jsonObject1.has("content")) {
                            entity.setContent(jsonObject1.getString("content"));
                        }
                        if (jsonObject1.has("created_at")) {
                            entity.setCreated_at(jsonObject1.getString("created_at"));
                        }
                        if (jsonObject1.has("from_name")) {
                            entity.setFrom_name(jsonObject1.getString("from_name"));
                        }
                        if (jsonObject1.has("from_avatar")) {
                            entity.setFrom_avatar(jsonObject1.getString("from_avatar"));
                        }
                        if (jsonObject1.has("is_praise")) {
                            entity.setIs_praise(jsonObject1.getString("is_praise"));
                        }
                        if (jsonObject1.has("praise")) {
                            entity.setPraise(jsonObject1.getString("praise"));
                        }
                        if (jsonObject1.has("to_name")) {
                            entity.setTo_name(jsonObject1.getString("to_name"));
                        }
                        if (jsonObject1.has("to_avatar")) {
                            entity.setTo_avatar(jsonObject1.getString("to_avatar"));
                        }
                        list.add(entity);
                    }
                    protocol.setList(list);
                }
                if (jsonObject.has("comment")) {
                    BaseJSONObject jsonObject1 = new BaseJSONObject(jsonObject.getString("comment"));
                    if (jsonObject1.has("id")) {
                        protocol.resp_id = jsonObject1.getString("id");
                    }
                    if (jsonObject1.has("user_id")) {
                        protocol.resp_user_id = jsonObject1.getString("user_id");
                    }
                    if (jsonObject1.has("key_id")) {
                        protocol.resp_key_id = jsonObject1.getString("key_id");
                    }
                    if (jsonObject1.has("content")) {
                        protocol.resp_content = jsonObject1.getString("content");
                    }
                    if (jsonObject1.has("created_at")) {
                        protocol.resp_created_at = jsonObject1.getString("created_at");
                    }
                    if (jsonObject1.has("type")) {
                        protocol.resp_type = jsonObject1.getString("type");
                    }
                    if (jsonObject1.has("praise")) {
                        protocol.resp_praise = jsonObject1.getString("praise");
                    }
                    if (jsonObject1.has("source")) {
                        protocol.resp_source = jsonObject1.getString("source");
                    }
                    if (jsonObject1.has("other")) {
                        protocol.resp_other = jsonObject1.getString("other");
                    }
                    if (jsonObject1.has("is_praise")) {
                        protocol.resp_is_praise = jsonObject1.getString("is_praise");
                    }
                    if (jsonObject1.has("avatar")) {
                        protocol.resp_avatar = jsonObject1.getString("avatar");
                    }
                    if (jsonObject1.has("name")) {
                        protocol.resp_name = jsonObject1.getString("name");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
