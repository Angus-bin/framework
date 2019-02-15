package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeBrandEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/11/24.
 */
public class HomeBrandDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<HomeBrandEntity> list;

    public List<HomeBrandEntity> getList() {
        return list;
    }

    public void setList(List<HomeBrandEntity> list) {
        this.list = list;
    }

    public HomeBrandDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/adCommon";
    }

}
