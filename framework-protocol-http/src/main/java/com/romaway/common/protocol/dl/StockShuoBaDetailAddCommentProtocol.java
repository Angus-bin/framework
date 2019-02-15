package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaDetailAddCommentProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public StockShuoBaDetailAddCommentProtocol(String flag, boolean isReply) {
        super(flag, false);
        isJson = true;
        if (!isReply) {
            subFunUrl = "comment/add";
        } else {
            subFunUrl = "comment/reply";
        }
    }

}
