package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/6/9.
 */
public class StockDetailCommentSendProtocolCoder extends AProtocolCoder<StockDetailCommentSendProtocol> {
    @Override
    protected byte[] encode(StockDetailCommentSendProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailCommentSendProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailCommentSendProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
