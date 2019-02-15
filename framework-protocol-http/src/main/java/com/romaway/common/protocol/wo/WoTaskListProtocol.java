package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/5/17.
 */
public class WoTaskListProtocol extends AProtocol {

    public String req_userID;

    public String resp_task_img;

    public String resp_userID;

    public String resp_errorCode;

    public String resp_errorMsg;

    public WoTaskListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "user/getTaskImg";
    }
}
