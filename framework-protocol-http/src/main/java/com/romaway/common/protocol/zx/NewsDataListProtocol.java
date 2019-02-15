package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.StockNewsListEntity;
import com.romaway.common.protocol.tougu.entity.GroupInfoEntity;
import com.romaway.common.protocol.zx.entity.NewsDataListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/3.
 */
public class NewsDataListProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String req_sort_id;

    private List<GroupInfoEntity> list;

    public List<GroupInfoEntity> getList() {
        return list;
    }

    public void setList(List<GroupInfoEntity> list) {
        this.list = list;
    }

    private List<StockNewsListEntity> list_stock;

    public List<StockNewsListEntity> getList_stock() {
        return list_stock;
    }

    public void setList_stock(List<StockNewsListEntity> list_stock) {
        this.list_stock = list_stock;
    }

    public NewsDataListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "article/getArticleList";
    }

}
