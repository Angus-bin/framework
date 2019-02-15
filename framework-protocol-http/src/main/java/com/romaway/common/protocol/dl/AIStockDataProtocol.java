package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.AIStockDataEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
public class AIStockDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<AIStockDataEntity> list;

    public List<AIStockDataEntity> getList() {
        return list;
    }

    public void setList(List<AIStockDataEntity> list) {
        this.list = list;
    }

    public AIStockDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "selfStock/gongzhen";
    }

}
