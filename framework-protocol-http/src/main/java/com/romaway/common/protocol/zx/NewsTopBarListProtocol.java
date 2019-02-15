package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.zx.entity.NewsTopBarListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/3.
 */
public class NewsTopBarListProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<NewsTopBarListEntity> list;

    public List<NewsTopBarListEntity> getList() {
        return list;
    }

    public void setList(List<NewsTopBarListEntity> list) {
        this.list = list;
    }

    public NewsTopBarListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "article/getSortList";
    }

}
