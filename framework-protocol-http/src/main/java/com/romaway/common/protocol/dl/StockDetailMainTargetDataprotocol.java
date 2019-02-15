package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockMainTargetEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/5.
 */
public class StockDetailMainTargetDataprotocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockMainTargetEntity> list;

    public List<StockMainTargetEntity> getList() {
        return list;
    }

    public void setList(List<StockMainTargetEntity> list) {
        this.list = list;
    }

    public StockDetailMainTargetDataprotocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getMainTargetList";
    }

}
