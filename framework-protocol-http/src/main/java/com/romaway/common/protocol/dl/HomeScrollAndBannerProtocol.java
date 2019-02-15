package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeScrollAndBannerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongrb on 2017/6/6.
 */
public class HomeScrollAndBannerProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_adpicture;

    public String resp_adweburl;

    private List<HomeScrollAndBannerEntity> list = new ArrayList<HomeScrollAndBannerEntity>();

    public List<HomeScrollAndBannerEntity> getList() {
        return list;
    }

    public void setList(List<HomeScrollAndBannerEntity> list) {
        this.list = list;
    }

    public HomeScrollAndBannerProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/ad";
    }

}
