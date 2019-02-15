package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeNewsListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/6/6.
 */
public class HomeNewsListProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    /**
     * 资讯列表总数
     */
    public int resp_count;

    private List<HomeNewsListEntity> list;

    public List<HomeNewsListEntity> getList() {
        return list;
    }

    public void setList(List<HomeNewsListEntity> list) {
        this.list = list;
    }

    public HomeNewsListProtocol(String flag) {
        super(flag, false);
        isJson = true;
//        subFunUrl = "site/apiNewsList";
        subFunUrl = "article/indexNewlist";
    }
}
