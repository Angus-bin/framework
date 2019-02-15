package com.romaway.common.protocol.wo;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.MsgMainListDataProtocol;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by Administrator on 2018/4/27.
 */
public class MsgAddCommentProtocolCoder extends AProtocolCoder<MsgAddCommentProtocol> {
    @Override
    protected byte[] encode(MsgAddCommentProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(MsgAddCommentProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("MsgAddCommentProtocolCoder", "decode >>> result body = " + result);
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

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
