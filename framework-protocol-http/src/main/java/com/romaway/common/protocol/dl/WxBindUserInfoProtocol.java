package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/4/20.
 */
public class WxBindUserInfoProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_userID;

    public String resp_mobile;

    public WxBindUserInfoProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "user/bangWeixin";
    }
}
