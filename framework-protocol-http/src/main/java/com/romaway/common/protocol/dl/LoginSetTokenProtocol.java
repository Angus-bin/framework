package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/5/3.
 */
public class LoginSetTokenProtocol extends AProtocol {


    public String req_userID;

    public String req_token;

    public String resp_errorCode;

    public String resp_errorMsg;


    public LoginSetTokenProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "user/setToken";
    }
}
