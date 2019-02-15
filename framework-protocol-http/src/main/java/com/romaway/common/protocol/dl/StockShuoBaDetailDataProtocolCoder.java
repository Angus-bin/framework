package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaDetailDataProtocolCoder extends AProtocolCoder<StockShuoBaDetailDataProtocol> {
    @Override
    protected byte[] encode(StockShuoBaDetailDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockShuoBaDetailDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockShuoBaDetailDataProtocolCoder", "decode >>> result body = " + result);
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
                    BaseJSONObject jsonObject1 = new BaseJSONObject(jsonObject.getString("data"));
                    if (jsonObject1.has("id")) {
                        protocol.resp_id = jsonObject1.getString("id");
                    }
                    if (jsonObject1.has("scode")) {
                        protocol.resp_scode = jsonObject1.getString("scode");
                    }
                    if (jsonObject1.has("scode")) {
                        protocol.resp_scode = jsonObject1.getString("scode");
                    }
                    if (jsonObject1.has("user_id")) {
                        protocol.resp_user_id = jsonObject1.getString("user_id");
                    }
                    if (jsonObject1.has("created_at")) {
                        protocol.resp_created_at = jsonObject1.getString("created_at");
                    }
                    if (jsonObject1.has("title")) {
                        protocol.resp_title = jsonObject1.getString("title");
                    }
                    if (jsonObject1.has("intro")) {
                        protocol.resp_intro = jsonObject1.getString("intro");
                    }
                    if (jsonObject1.has("content")) {
                        protocol.resp_content = jsonObject1.getString("content");
                    }
                    if (jsonObject1.has("grab")) {
                        protocol.resp_grab = jsonObject1.getString("grab");
                    }
                    if (jsonObject1.has("imgs")) {
                        protocol.resp_imgs = jsonObject1.getString("imgs");
                    }
                    if (jsonObject1.has("no")) {
                        protocol.resp_no = jsonObject1.getString("no");
                    }
                    if (jsonObject1.has("type")) {
                        protocol.resp_no = jsonObject1.getString("type");
                    }
                    if (jsonObject1.has("other")) {
                        protocol.resp_other = jsonObject1.getString("other");
                    }
                    if (jsonObject1.has("comment_at")) {
                        protocol.resp_comment_at = jsonObject1.getString("comment_at");
                    }
                    if (jsonObject1.has("look")) {
                        protocol.resp_look = jsonObject1.getString("look");
                    }
                    if (jsonObject1.has("comment")) {
                        protocol.resp_comment = jsonObject1.getString("comment");
                    }
                    if (jsonObject1.has("praise")) {
                        protocol.resp_praise = jsonObject1.getString("praise");
                    }
                    if (jsonObject1.has("avatar")) {
                        protocol.resp_avatar = jsonObject1.getString("avatar");
                    }
                    if (jsonObject1.has("nickname")) {
                        protocol.resp_nickname = jsonObject1.getString("nickname");
                    }
                    if (jsonObject1.has("is_praise")) {
                        protocol.resp_is_praise = jsonObject1.getString("is_praise");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
