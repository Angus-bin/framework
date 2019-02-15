package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockHxtcEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/8.
 */
public class StockDetailHxtcDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockHxtcEntity> list;

    public List<StockHxtcEntity> getList() {
        return list;
    }

    public void setList(List<StockHxtcEntity> list) {
        this.list = list;
    }

    public StockDetailHxtcDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "cli/hxtc/";
    }

}
