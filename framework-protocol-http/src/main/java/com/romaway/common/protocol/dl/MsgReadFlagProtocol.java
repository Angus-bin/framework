package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/11/30.
 */
public class MsgReadFlagProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    /**
     *
     * @param flag
     */
    public MsgReadFlagProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "system/updateReadStatus";
    }

}
