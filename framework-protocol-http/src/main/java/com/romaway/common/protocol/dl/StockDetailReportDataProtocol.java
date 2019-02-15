package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockReportEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/1.
 */
public class StockDetailReportDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockReportEntity> list;

    public List<StockReportEntity> getList() {
        return list;
    }

    public void setList(List<StockReportEntity> list) {
        this.list = list;
    }

    public StockDetailReportDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getResearchReportList";
    }

}
