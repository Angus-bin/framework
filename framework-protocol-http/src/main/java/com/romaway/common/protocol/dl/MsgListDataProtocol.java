package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.MsgListDataEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/28.
 */
public class MsgListDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    private List<MsgListDataEntity> list;

    public List<MsgListDataEntity> getList() {
        return list;
    }

    public void setList(List<MsgListDataEntity> list) {
        this.list = list;
    }

    /**
     *
     * @param flag
     */
    public MsgListDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "system/getMessage";
    }

}
