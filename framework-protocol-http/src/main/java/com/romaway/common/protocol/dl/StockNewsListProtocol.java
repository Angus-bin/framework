package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockNewsListEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/1/19.
 */
public class StockNewsListProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public List<StockNewsListEntity> getList() {
        return list;
    }

    public void setList(List<StockNewsListEntity> list) {
        this.list = list;
    }

    private List<StockNewsListEntity> list;

    public StockNewsListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getNewsList";
    }

}
