package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HistoryProductGoldEntity;
import com.romaway.common.protocol.dl.entity.HistoryProductLimitEntity;
import com.romaway.common.protocol.dl.entity.HistoryProductQswyEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/19.
 */
public class HistoryProductProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String req_productID;

    private List<HistoryProductGoldEntity> list_gold;

    private List<HistoryProductLimitEntity> list_limit;

    private List<HistoryProductQswyEntity> list_qswy;

    public List<HistoryProductGoldEntity> getList_gold() {
        return list_gold;
    }

    public void setList_gold(List<HistoryProductGoldEntity> list_gold) {
        this.list_gold = list_gold;
    }

    public List<HistoryProductLimitEntity> getList_limit() {
        return list_limit;
    }

    public void setList_limit(List<HistoryProductLimitEntity> list_limit) {
        this.list_limit = list_limit;
    }

    public List<HistoryProductQswyEntity> getList_qswy() {
        return list_qswy;
    }

    public void setList_qswy(List<HistoryProductQswyEntity> list_qswy) {
        this.list_qswy = list_qswy;
    }

    public String resp_five_sum;

    /**
     * 列表总数
     */
    public int resp_count;

    public HistoryProductProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/historyOptionNew";
    }

}
