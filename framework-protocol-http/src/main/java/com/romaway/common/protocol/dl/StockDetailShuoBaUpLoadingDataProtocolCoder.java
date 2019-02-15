package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;

/**
 * Created by Administrator on 2018/3/23.
 */
public class StockDetailShuoBaUpLoadingDataProtocolCoder extends AProtocolCoder<StockDetailShuoBaUpLoadingDataProtocol> {
    @Override
    protected byte[] encode(StockDetailShuoBaUpLoadingDataProtocol protocol) {
        return new byte[0];
    }

    @Override
    protected void decode(StockDetailShuoBaUpLoadingDataProtocol protocol) throws ProtocolParserException {

    }
}
