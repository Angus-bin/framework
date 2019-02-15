package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockShuoBaCommentEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaDetailCommentProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockShuoBaCommentEntity> list;

    public List<StockShuoBaCommentEntity> getList() {
        return list;
    }

    public void setList(List<StockShuoBaCommentEntity> list) {
        this.list = list;
    }

    public StockShuoBaDetailCommentProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "comment/list";
    }

}
