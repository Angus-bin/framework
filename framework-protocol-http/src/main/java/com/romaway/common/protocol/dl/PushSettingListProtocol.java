package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/10/10.
 */
public class PushSettingListProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_article_notice;

    public String resp_video_notice;

    /**
     *
     * @param flag
     */
    public PushSettingListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "system/userNoticeConf";
    }

}
