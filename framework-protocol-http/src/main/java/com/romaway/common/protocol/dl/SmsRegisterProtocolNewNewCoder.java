package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/4/19.
 */
public class SmsRegisterProtocolNewNewCoder extends AProtocolCoder<SmsRegisterProtocolNewNew> {
    @Override
    protected byte[] encode(SmsRegisterProtocolNewNew protocol) {
        /**
         * 设置请求body
         */
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(SmsRegisterProtocolNewNew protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("SmsRegisterProtocolNewNew", "decode >>> result body = " + result);
    }
}
