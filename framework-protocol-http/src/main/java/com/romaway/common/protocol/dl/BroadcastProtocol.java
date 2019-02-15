package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeBroadCastEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/11.
 */
public class BroadcastProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    /**
     * 广播列表总数
     */
    public int resp_count;

    private List<HomeBroadCastEntity> list;

    public List<HomeBroadCastEntity> getList() {
        return list;
    }

    public void setList(List<HomeBroadCastEntity> list) {
        this.list = list;
    }


    public BroadcastProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/subscribeHistory";
    }

}
