package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockProfitEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/6.
 */
public class StockDetailProfitDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockProfitEntity> list;

    public List<StockProfitEntity> getList() {
        return list;
    }

    public void setList(List<StockProfitEntity> list) {
        this.list = list;
    }

    public StockDetailProfitDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getProfitList";
    }

}
