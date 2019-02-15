package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/6/8.
 */
public class StockDetailCommentPointProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public StockDetailCommentPointProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "feedback/point";
    }

}
