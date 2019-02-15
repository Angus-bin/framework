package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.MsgListDataEntity;
import com.romaway.common.protocol.dl.entity.MsgMainListDataEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/28.
 */
public class MsgMainListDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    private List<MsgMainListDataEntity> list;

    public List<MsgMainListDataEntity> getList() {
        return list;
    }

    public void setList(List<MsgMainListDataEntity> list) {
        this.list = list;
    }

    /**
     *
     * @param flag
     */
    public MsgMainListDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "system/messageType";
    }

}
