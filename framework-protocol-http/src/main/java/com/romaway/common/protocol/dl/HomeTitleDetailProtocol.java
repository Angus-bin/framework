package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/6/5.
 */
public class HomeTitleDetailProtocol extends AProtocol {

    public HomeTitleDetailProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/ApiTop";
    }

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_zsy_month;

    public String resp_zsy_year;

    public String resp_zrs_service;

    public String resp_zsy_new;

}
