package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/4/19.
 */
public class WoUpdateUserNameProtocol extends AProtocol {

    public String req_userID;

    public String req_userName;

    public String resp_errCode;
    public String resp_errMsg;

    public WoUpdateUserNameProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "account/changeRealname";
    }
}
