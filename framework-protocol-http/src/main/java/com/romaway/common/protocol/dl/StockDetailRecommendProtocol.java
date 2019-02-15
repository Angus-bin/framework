package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.RecommendListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/10/9.
 */
public class StockDetailRecommendProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<RecommendListEntity> list;

    public List<RecommendListEntity> getList() {
        return list;
    }

    public void setList(List<RecommendListEntity> list) {
        this.list = list;
    }

    public StockDetailRecommendProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/optionPusReason";
    }

}
