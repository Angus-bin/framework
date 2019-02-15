package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2018/3/23.
 */
public class StockDetailShuoBaUpLoadingDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public StockDetailShuoBaUpLoadingDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "topic/add";
    }

}
