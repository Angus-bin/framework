package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/6/7.
 */
public class StockDetailProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_sharename;

    public String resp_sharenum;
    public String resp_nowprice;
    public String resp_in_price;
    public String resp_out_price;
    public String resp_in_time;
    public String resp_out_time;
    public String resp_earningsrate;
    public String resp_ishold;
    public String resp_intro;


    public StockDetailProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "dayOptionLog/apiView";
    }

}
