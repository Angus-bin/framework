package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeProductDataEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/12.
 */
public class HomeProductDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    private List<HomeProductDataEntity> list;

    public List<HomeProductDataEntity> getList() {
        return list;
    }

    public void setList(List<HomeProductDataEntity> list) {
        this.list = list;
    }

    /**
     * 广播列表总数
     */
    public int resp_count;


    public HomeProductDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/indexQuShiWenYing";
    }
}
