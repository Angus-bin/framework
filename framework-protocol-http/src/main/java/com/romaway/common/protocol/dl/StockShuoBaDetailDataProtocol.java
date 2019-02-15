package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaDetailDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_id;

    public String resp_scode;

    public String resp_title;

    public String resp_intro;

    public String resp_user_id;

    public String resp_content;

    public String resp_avatar;

    public String resp_imgs;

    public String resp_grab;

    public String resp_no;

    public String resp_nickname;

    public String resp_is_praise;

    public String resp_other;

    public String resp_comment_at;

    public String resp_created_at;

    public String resp_look;

    public String resp_praise;

    public String resp_comment;

    public StockShuoBaDetailDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "topic/detail";
    }

}
