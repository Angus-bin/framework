package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/10/9.
 */
public class StockDetailProtocolNew extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_id;
    public String resp_title;
    public String resp_code;
    public String resp_nowprice;
    public String resp_in_price;
    public String resp_in_time;
    public String resp_out_price;
    public String resp_out_time;
    public String resp_increase;
    public String resp_length;
    public String resp_pro_title;
    public String resp_pro_id;

    public StockDetailProtocolNew(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/optionInfo";
    }

}
