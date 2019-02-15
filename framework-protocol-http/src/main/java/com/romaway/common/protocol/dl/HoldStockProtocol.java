package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HoldStockListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/6/7.
 */
public class HoldStockProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<HoldStockListEntity> list;

    public List<HoldStockListEntity> getList() {
        return list;
    }

    public void setList(List<HoldStockListEntity> list) {
        this.list = list;
    }

    public HoldStockProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/holdShare";
    }
}
