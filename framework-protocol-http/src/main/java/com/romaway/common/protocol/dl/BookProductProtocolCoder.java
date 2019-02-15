package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/9/13.
 */
public class BookProductProtocolCoder extends AProtocolCoder<BookProductProtocol> {
    @Override
    protected byte[] encode(BookProductProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(BookProductProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("BookProductProtocolCoder", "decode >>> result body = " + result);
        try {
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
                Logger.d("BookProductProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                if (jsonObject1.has("is_success")) {
                    protocol.resp_is_success = jsonObject1.getString("is_success");
                }
                if (jsonObject1.has("msg")) {
                    protocol.resp_msg = jsonObject1.getString("msg");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
