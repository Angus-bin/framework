package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2018/3/28.
 */
public class StockDetailShuoBaPointProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public StockDetailShuoBaPointProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "praise/add";
    }

}
