package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.BannerInfoEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/5/31.
 */
public class BannerInfoProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<BannerInfoEntity> list;

    public List<BannerInfoEntity> getList() {
        return list;
    }

    public void setList(List<BannerInfoEntity> list) {
        this.list = list;
    }

    /**
     * 图片列表总数
     */
    public int resp_count;

    public BannerInfoProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "article/Banner";
    }

}
