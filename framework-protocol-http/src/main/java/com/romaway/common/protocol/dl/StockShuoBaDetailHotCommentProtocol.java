package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockShuoBaHotCommentEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaDetailHotCommentProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<StockShuoBaHotCommentEntity> list;

    public List<StockShuoBaHotCommentEntity> getList() {
        return list;
    }

    public void setList(List<StockShuoBaHotCommentEntity> list) {
        this.list = list;
    }

    public StockShuoBaDetailHotCommentProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "topic/netHotReplyList";
    }

}
