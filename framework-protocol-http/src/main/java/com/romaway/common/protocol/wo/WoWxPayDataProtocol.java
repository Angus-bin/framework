package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/11/29.
 */
public class WoWxPayDataProtocol extends AProtocol {

    public String resp_errorCode;
    public String resp_errorMsg;

    public String resp_id;
    public String resp_title;
    public String resp_sub_title;
    public String resp_start_at;
    public String resp_end_at;
    public String resp_price;
    public String resp_qishu;

    public WoWxPayDataProtocol(String flag) {
        super(flag, true);
        isJson = true;
        subFunUrl = "order/orderInfo";
    }

}
