package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/9/12.
 */
public class HomeDaPanProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_shangzheng;

    public String resp_shenzhen;

    public String resp_chuangye;

    public HomeDaPanProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "stock/zhishu";
    }

}
