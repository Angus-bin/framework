package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.BannerInfoEntity;
import com.romaway.common.protocol.zx.entity.RealTimeZiXunEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/7.
 */
public class RealTimeZiXunProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<RealTimeZiXunEntity> list;

    public List<RealTimeZiXunEntity> getList() {
        return list;
    }

    public void setList(List<RealTimeZiXunEntity> list) {
        this.list = list;
    }

    /**
     * 实时资讯列表总数
     */
    public int resp_count;

    public RealTimeZiXunProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "article/realInfo";
    }

}
