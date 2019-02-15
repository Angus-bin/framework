package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.ProductServiceEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30.
 */
public class ProduceServiceListDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<ProductServiceEntity> list;

    public List<ProductServiceEntity> getList() {
        return list;
    }

    public void setList(List<ProductServiceEntity> list) {
        this.list = list;
    }

    public ProduceServiceListDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/themeList";
    }

}
