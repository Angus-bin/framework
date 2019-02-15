package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockShuoBaReplyCommentListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */
public class StockShuoBaDetailCommentListProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_id;

    public String resp_user_id;

    public String resp_key_id;

    public String resp_content;

    public String resp_created_at;

    public String resp_type;

    public String resp_praise;

    public String resp_source;

    public String resp_other;

    public String resp_is_praise;

    public String resp_avatar;

    public String resp_name;

    private List<StockShuoBaReplyCommentListEntity> list;

    public List<StockShuoBaReplyCommentListEntity> getList() {
        return list;
    }

    public void setList(List<StockShuoBaReplyCommentListEntity> list) {
        this.list = list;
    }

    public StockShuoBaDetailCommentListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "comment/replyList";
    }

}
