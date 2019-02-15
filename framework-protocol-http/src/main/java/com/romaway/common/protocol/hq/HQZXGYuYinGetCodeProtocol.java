package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by faith on 2016/7/27.
 */
public class HQZXGYuYinGetCodeProtocol extends AProtocol {
    /**
     * 用户手机号
     */
    public String req_phoneNum;
    /**
     * 设备号
     */
    public String req_deviceID;
    //返回数据
    public String resp_secCode;

    public HQZXGYuYinGetCodeProtocol(String flag) {
        super(flag, true);
        subFunUrl = "/api/system/vvcode/rl_ytx/v1.0/";
        isJson = true;

    }
}
