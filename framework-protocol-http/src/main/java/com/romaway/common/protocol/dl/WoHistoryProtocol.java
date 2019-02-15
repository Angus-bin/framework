package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/4/20.
 */
public class WoHistoryProtocol extends AProtocol {

    public String req_userID;

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_sum;

    public String resp_data_month_gain_sum;

    public String resp_month;


    public WoHistoryProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "account/browseLogs";
    }
}
