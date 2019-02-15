package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hrb on 2016/8/25.
 */
public class GroupNameSensitiveProtocol extends AProtocol {

    public String req_groupName;

    public String resp_errCode;

    public String resp_errMsg;

    public GroupNameSensitiveProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "/api/tg-service/portfolio/mgcgl/select/";
    }
}
