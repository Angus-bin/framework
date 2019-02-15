package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockTenPartnerEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/7.
 */
public class StockDetailTenPartnerDateProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockTenPartnerEntity> list;

    public List<StockTenPartnerEntity> getList() {
        return list;
    }

    public void setList(List<StockTenPartnerEntity> list) {
        this.list = list;
    }

    public StockDetailTenPartnerDateProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getPartnerTenDate";
    }

}
