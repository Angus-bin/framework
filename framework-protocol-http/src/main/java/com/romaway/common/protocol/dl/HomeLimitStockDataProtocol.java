package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeLimitStockEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/19.
 */
public class HomeLimitStockDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_artilce_url;

    private List<HomeLimitStockEntity> list;

    public List<HomeLimitStockEntity> getList() {
        return list;
    }

    public void setList(List<HomeLimitStockEntity> list) {
        this.list = list;
    }

    public HomeLimitStockDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "site/GetIndexZhangTing/";
    }

}
