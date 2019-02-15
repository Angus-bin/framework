package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockShareBonusEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/3/8.
 */
public class StockDetailShareBonusDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockShareBonusEntity> list;

    public List<StockShareBonusEntity> getList() {
        return list;
    }

    public void setList(List<StockShareBonusEntity> list) {
        this.list = list;
    }

    public StockDetailShareBonusDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "api/getShareBonus";
    }

}
