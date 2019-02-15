package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockPartnerTenEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/7.
 */
public class StockDetailPartnerTenDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockPartnerTenEntity> list;

    public List<StockPartnerTenEntity> getList() {
        return list;
    }

    public void setList(List<StockPartnerTenEntity> list) {
        this.list = list;
    }

    public StockDetailPartnerTenDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getPartnerTen";
    }

}
