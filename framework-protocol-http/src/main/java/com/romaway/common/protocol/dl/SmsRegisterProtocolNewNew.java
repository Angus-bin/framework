package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/4/19.
 */
public class SmsRegisterProtocolNewNew extends AProtocol {

    /**
     * 用户手机号
     */
    public String req_phoneNum;

    /**
     * 券商ID
     */
    public String req_typeId;

    public SmsRegisterProtocolNewNew(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "sms/send";
    }
}
