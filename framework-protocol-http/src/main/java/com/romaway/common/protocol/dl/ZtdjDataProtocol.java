package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.GoldStockOperationListEntity;
import com.romaway.common.protocol.dl.entity.LimitStockOperationListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/14.
 */
public class ZtdjDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_id;

    public String resp_new_date;

    private List<LimitStockOperationListEntity> list_operation;

    public List<LimitStockOperationListEntity> getList_operation() {
        return list_operation;
    }

    public void setList_operation(List<LimitStockOperationListEntity> list_operation) {
        this.list_operation = list_operation;
    }

    /**
     *
     * @param flag
     */
    public ZtdjDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/recentOption";
    }

}
