package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeProductCleverEntity;
import com.romaway.common.protocol.dl.entity.HomeProductSpecialistEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/11/16.
 */
public class HomeProductDataProtocolNew extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    // 专家选股
    public String resp_specialist_title;
    public String resp_specialist_subtitle;
    public String resp_specialist_id;

    // 智能选股
    public String resp_clever_title;
    public String resp_clever_subtitle;
    public String resp_clever_id;

    private List<HomeProductSpecialistEntity> list_specialist;

    public List<HomeProductSpecialistEntity> getList() {
        return list_specialist;
    }

    public void setList(List<HomeProductSpecialistEntity> list) {
        this.list_specialist = list;
    }

    private List<HomeProductCleverEntity> list_clever;

    public List<HomeProductCleverEntity> getList_clever() {
        return list_clever;
    }

    public void setList_clever(List<HomeProductCleverEntity> list_clever) {
        this.list_clever = list_clever;
    }

    public HomeProductDataProtocolNew(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/getIndexQushiwenying";
    }

}
