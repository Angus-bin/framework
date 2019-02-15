package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2018/9/19.
 */
public class GoodStockDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public GoodStockDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "article/getRecommendInfo";
    }

}
