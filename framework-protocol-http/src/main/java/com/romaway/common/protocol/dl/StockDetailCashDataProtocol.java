package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockCashEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/6.
 */
public class StockDetailCashDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockCashEntity> list;

    public List<StockCashEntity> getList() {
        return list;
    }

    public void setList(List<StockCashEntity> list) {
        this.list = list;
    }

    public StockDetailCashDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getCashFlow";
    }

}
