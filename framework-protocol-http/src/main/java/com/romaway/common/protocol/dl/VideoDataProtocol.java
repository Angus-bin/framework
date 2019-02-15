package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/12/2.
 */
public class VideoDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_content;

    public VideoDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "school/videoInfo";
    }

}
