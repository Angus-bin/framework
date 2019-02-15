package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2017/4/14.
 */
public class TestProtocol extends AProtocol {

    public TestProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "account/getInfo";
    }
}
