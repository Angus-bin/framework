package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeProductCleverListEntity;
import com.romaway.common.protocol.dl.entity.HomeProductSpecialistListEntity;
import com.romaway.common.protocol.dl.entity.HomeProductUnStartListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/11/17.
 */
public class HomeProductDataListProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String productID = "";;

    public String type = "";

    private List<HomeProductSpecialistListEntity> list_specialist;

    public List<HomeProductSpecialistListEntity> getList_specialist() {
        return list_specialist;
    }

    public void setList_specialist(List<HomeProductSpecialistListEntity> list_specialist) {
        this.list_specialist = list_specialist;
    }

    private List<HomeProductCleverListEntity> list_clever;

    public List<HomeProductCleverListEntity> getList_clever() {
        return list_clever;
    }

    public void setList_clever(List<HomeProductCleverListEntity> list_clever) {
        this.list_clever = list_clever;
    }

    private List<HomeProductUnStartListEntity> list_un_start;

    public List<HomeProductUnStartListEntity> getList_un_start() {
        return list_un_start;
    }

    public void setList_un_start(List<HomeProductUnStartListEntity> list_un_start) {
        this.list_un_start = list_un_start;
    }

    public HomeProductDataListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/qushiwenyingList";
    }

}
