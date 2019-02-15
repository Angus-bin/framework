package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockNoticeEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/1.
 */
public class StockDetailNoticeDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockNoticeEntity> list;

    public List<StockNoticeEntity> getList() {
        return list;
    }

    public void setList(List<StockNoticeEntity> list) {
        this.list = list;
    }

    public StockDetailNoticeDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getNoticeList";
    }

}
