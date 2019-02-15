package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/11/16.
 */
public class HomeTitleProductDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_jinpaigupiaochi;

    public String resp_zhangtingdianjing;

    public String resp_zhuangjiajingzhun;

    public HomeTitleProductDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/getIndexData";
    }

}
