package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2018/1/31.
 */
public class StockShareToSeeProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public StockShareToSeeProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "user/addStockShareLog";
    }

}
