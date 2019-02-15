package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.BannerDetailEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/14.
 */
public class BannerDetailProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<BannerDetailEntity> list;

    public List<BannerDetailEntity> getList() {
        return list;
    }

    public void setList(List<BannerDetailEntity> list) {
        this.list = list;
    }

    /**
     * 图片列表总数
     */
    public int resp_count;

    public BannerDetailProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/indexLunboAd";
    }

}
