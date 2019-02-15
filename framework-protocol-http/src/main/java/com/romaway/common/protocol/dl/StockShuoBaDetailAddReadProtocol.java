package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaDetailAddReadProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public StockShuoBaDetailAddReadProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "read/add";
    }

}
