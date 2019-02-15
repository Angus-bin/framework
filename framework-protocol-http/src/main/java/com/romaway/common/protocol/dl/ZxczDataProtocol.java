package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.GoldStockHoldListEntity;
import com.romaway.common.protocol.dl.entity.GoldStockOperationListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/14.
 */
public class ZxczDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_id;

    private List<GoldStockOperationListEntity> list_operation;

    public List<GoldStockOperationListEntity> getList_operation() {
        return list_operation;
    }

    private List<GoldStockHoldListEntity> list_hold;

    public List<GoldStockHoldListEntity> getList_hold() {
        return list_hold;
    }

    public void setList_hold(List<GoldStockHoldListEntity> list_hold) {
        this.list_hold = list_hold;
    }

    public void setList_operation(List<GoldStockOperationListEntity> list_operation) {
        this.list_operation = list_operation;
    }

    /**
     *
     * @param flag
     */
    public ZxczDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/recentOption";
    }

}
