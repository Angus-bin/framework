package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.BroadCastDetailEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/14.
 */
public class BroadcastDetailProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    /**
     * 广播列表总数
     */
    public int resp_count;

    private List<BroadCastDetailEntity> list;

    public List<BroadCastDetailEntity> getList() {
        return list;
    }

    public void setList(List<BroadCastDetailEntity> list) {
        this.list = list;
    }

    public BroadcastDetailProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/historyModelIncome";
    }

}
