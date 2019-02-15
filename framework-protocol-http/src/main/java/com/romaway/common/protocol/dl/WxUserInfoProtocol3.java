package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/4/20.
 */
public class WxUserInfoProtocol3 extends AProtocol {

    public String req_code;

    public String req_appid;

    public String req_secret;

    public String req_grant_type;

    // 返回

    public String resp_access_token;
    public String resp_expires_in;
    public String resp_refresh_token;
    public String resp_openid;
    public String resp_scope;
    public String resp_unionid;

    public String resp_errCode;
    public String resp_errMsg;


    public WxUserInfoProtocol3(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "sns/auth?";
    }
}
