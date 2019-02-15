package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/5/12.
 */
public class GetWxPayOrderProtocol extends AProtocol {

    public String req_userID;

    public String req_device_id;

    public String req_type_id;

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_appid;

    public String resp_partnerid;

    public String resp_device_info;

    public String resp_package;

    public String resp_nonce_str;

    public String resp_sign;
    public String resp_timestamp;
    public String resp_prepayid;

    public GetWxPayOrderProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "weixinPay/CreateOrder";
    }

}
