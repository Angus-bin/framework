package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.CommentListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/6/8.
 */
public class StockDetailCommentProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<CommentListEntity> list;

    public List<CommentListEntity> getList() {
        return list;
    }

    public void setList(List<CommentListEntity> list) {
        this.list = list;
    }

    public StockDetailCommentProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "feedback/optionLogFeedbackList";
    }

}
