package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockShuoBaEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */
public class StockDetailShuoBaDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_total;

    private List<StockShuoBaEntity> list;

    public List<StockShuoBaEntity> getList() {
        return list;
    }

    public void setList(List<StockShuoBaEntity> list) {
        this.list = list;
    }

    public StockDetailShuoBaDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "topic/netHotList";
    }

}
