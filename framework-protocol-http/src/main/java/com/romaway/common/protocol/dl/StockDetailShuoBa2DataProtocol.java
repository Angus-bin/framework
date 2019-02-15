package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockShuoBa2Entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */
public class StockDetailShuoBa2DataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_total;

    private List<StockShuoBa2Entity> list;

    public List<StockShuoBa2Entity> getList() {
        return list;
    }

    public void setList(List<StockShuoBa2Entity> list) {
        this.list = list;
    }

    public StockDetailShuoBa2DataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "topic/list";
    }

}
