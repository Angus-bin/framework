package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/5/11.
 */
public class ShareTimesProtocol extends AProtocol {

    public String req_userID;

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_userID;

    public int resp_times;

    public ShareTimesProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "user/getShareTimes";
    }

}
