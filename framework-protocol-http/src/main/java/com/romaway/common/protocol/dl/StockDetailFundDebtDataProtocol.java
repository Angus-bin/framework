package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockFundDebtEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/5.
 */
public class StockDetailFundDebtDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockFundDebtEntity> list;

    public List<StockFundDebtEntity> getList() {
        return list;
    }

    public void setList(List<StockFundDebtEntity> list) {
        this.list = list;
    }

    public StockDetailFundDebtDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getFundDebtList";
    }

}
