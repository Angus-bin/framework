package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/9/13.
 */
public class BookProductProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_is_success;

    public String resp_msg;

    /**
     *
     * @param flag
     */
    public BookProductProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/subscribe";
    }

}
