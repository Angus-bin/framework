package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.TextAdsEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/14.
 */
public class TextAdsDetailProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    /**
     * 列表总数
     */
    public int resp_count;

    private List<TextAdsEntity> list;

    public List<TextAdsEntity> getList() {
        return list;
    }

    public void setList(List<TextAdsEntity> list) {
        this.list = list;
    }

    public TextAdsDetailProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/indexTextAd";
    }

}
