package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.CommentListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/6/8.
 */
public class StockDetailCommentProtocolCoder extends AProtocolCoder<StockDetailCommentProtocol> {
    @Override
    protected byte[] encode(StockDetailCommentProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailCommentProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailCommentProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("StockDetailCommentProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("list")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("list");
                        ArrayList<CommentListEntity> list = new ArrayList<CommentListEntity>();
                        int count = jsonArray.length();
                        for (int i = 0; i < count; i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            CommentListEntity item = new CommentListEntity();
                            if (jsonObject2.has("user_id")) {
                                item.setUser_id(jsonObject2.getString("user_id"));
                            }
                            if (jsonObject2.has("userphoto")) {
                                item.setUserphoto(jsonObject2.getString("userphoto"));
                            }
                            if (jsonObject2.has("username")) {
                                item.setUsername(jsonObject2.getString("username"));
                            }
                            if (jsonObject2.has("time")) {
                                item.setCommentime(jsonObject2.getString("time"));
                            }
                            if (jsonObject2.has("good_num")) {
                                item.setCommentpoint(jsonObject2.getString("good_num"));
                            }
                            if (jsonObject2.has("reply_nums")) {
                                item.setCommentcount(jsonObject2.getString("reply_nums"));
                            }
                            if (jsonObject2.has("content")) {
                                item.setComment(jsonObject2.getString("content"));
                            }
                            if (jsonObject2.has("is_good")) {
                                item.setIspoint(jsonObject2.getString("is_good"));
                            }
                            if (jsonObject2.has("id")) {
                                item.setFeedbackid(jsonObject2.getString("id"));
                            }
                            list.add(item);
                        }
                        protocol.setList(list);
                    }
                    if (jsonObject1.has("item")) {
                        BaseJSONArray jsonArray = jsonObject1.getJSONArray("item");
                        ArrayList<CommentListEntity> list = new ArrayList<CommentListEntity>();
                        int count = jsonArray.length();
                        for (int i = 0; i < count; i++) {
                            BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            CommentListEntity item = new CommentListEntity();
                            if (jsonObject2.has("user_id")) {
                                item.setUser_id(jsonObject2.getString("user_id"));
                            }
                            if (jsonObject2.has("userphoto")) {
                                item.setUserphoto(jsonObject2.getString("userphoto"));
                            }
                            if (jsonObject2.has("username")) {
                                item.setUsername(jsonObject2.getString("username"));
                            }
                            if (jsonObject2.has("time")) {
                                item.setCommentime(jsonObject2.getString("time"));
                            }
                            if (jsonObject2.has("good_num")) {
                                item.setCommentpoint(jsonObject2.getString("good_num"));
                            }
                            if (jsonObject2.has("reply_nums")) {
                                item.setCommentcount(jsonObject2.getString("reply_nums"));
                            }
                            if (jsonObject2.has("content")) {
                                item.setComment(jsonObject2.getString("content"));
                            }
                            if (jsonObject2.has("is_good")) {
                                item.setIspoint(jsonObject2.getString("is_good"));
                            }
                            if (jsonObject2.has("id")) {
                                item.setFeedbackid(jsonObject2.getString("id"));
                            }
                            list.add(item);
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
