package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/4/20.
 */
public class WoUpdateUserImageProtocol extends AProtocol {

    public String req_userID;

    public String req_userImage;

    public String req_xy;

    public String req_iv;

    public String resp_errCode;
    public String resp_errMsg;

    public WoUpdateUserImageProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "account/changeImg";
    }
}
