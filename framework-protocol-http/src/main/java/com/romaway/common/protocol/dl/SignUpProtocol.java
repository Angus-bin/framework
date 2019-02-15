package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/9/29.
 */
public class SignUpProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_is_success;

    public String resp_msg;

    /**
     *
     * @param flag
     */
    public SignUpProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/signup";
    }

}
