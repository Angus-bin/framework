package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/10/20.
 */
public class StockFeedBackProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public StockFeedBackProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "feedback/subStockFeedback";
    }

}
