package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2017/9/11.
 */
public class InitProtocolNew extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_type;

    public String resp_version;

    public String resp_intro;

    public String resp_download_url;

    public String resp_jpgpc_id;

    public String resp_ztdj_id;

    public String resp_ai_id;

    public String[][] resp_menu;

    public String resp_menu_json;

    public String resp_site_ip;

    public String resp_is_holiday;

    public String resp_spread_img = "";

    public String resp_spread_id = "";

    public String resp_spread_url = "";

    public String[][] resp_advtInfo;

    public String resp_update_type = "1";

    /**
     *
     * @param flag
     */
    public InitProtocolNew(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "app/init";
    }

}
