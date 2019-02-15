package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.ZtdjWeekDataEntity;

import java.util.List;

/**
 * Created by hongrb on 2018/2/6.
 */
public class ZtdjWeekDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    private List<ZtdjWeekDataEntity> list;

    public List<ZtdjWeekDataEntity> getList() {
        return list;
    }

    public void setList(List<ZtdjWeekDataEntity> list) {
        this.list = list;
    }

    /**
     *
     * @param flag
     */
    public ZtdjWeekDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "article/getRecommendInfo";
    }

}
