package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockPartnerEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/7.
 */
public class StockDetailGdrsDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockPartnerEntity> list;

    public List<StockPartnerEntity> getList() {
        return list;
    }

    public void setList(List<StockPartnerEntity> list) {
        this.list = list;
    }

    public StockDetailGdrsDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getPartnerChange";
    }

}
