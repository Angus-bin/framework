package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HistoryHoldEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/11/5.
 */
public class WenjianHistoryHoldProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<HistoryHoldEntity> list;

    public List<HistoryHoldEntity> getList() {
        return list;
    }

    public void setList(List<HistoryHoldEntity> list) {
        this.list = list;
    }

    /**
     * 列表总数
     */
    public int resp_count;

    public WenjianHistoryHoldProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/QushiwenyinHistoryLog";
    }

}
