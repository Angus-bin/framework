package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.WenjianListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/10/8.
 */
public class WenjianListProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    private List<WenjianListEntity> list;

    public List<WenjianListEntity> getList() {
        return list;
    }

    public void setList(List<WenjianListEntity> list) {
        this.list = list;
    }

    public WenjianListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/historyQuShiWenYing";
    }

}
