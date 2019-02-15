package com.romaway.common.protocol.wo;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2018/4/27.
 */
public class MsgAddCommentProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    /**
     *
     * @param flag
     */
    public MsgAddCommentProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "feedback/addCommon";
    }

}
