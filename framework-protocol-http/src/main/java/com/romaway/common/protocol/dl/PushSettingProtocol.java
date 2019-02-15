package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/10/10.
 */
public class PushSettingProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_is_jinpai;

    public String resp_is_zhangting;

    public String resp_is_qushi;

    public String resp_is_article;

    /**
     *
     * @param flag
     */
    public PushSettingProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "system/SetUserNoticeConf";
    }

}
