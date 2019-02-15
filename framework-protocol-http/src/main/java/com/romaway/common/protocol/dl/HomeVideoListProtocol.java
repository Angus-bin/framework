package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.HomeVideoEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/15.
 */
public class HomeVideoListProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    /**
     * 列表总数
     */
    public int resp_count;

    private List<HomeVideoEntity> list;

    public List<HomeVideoEntity> getList() {
        return list;
    }

    public void setList(List<HomeVideoEntity> list) {
        this.list = list;
    }

    public HomeVideoListProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "video/apiList";
    }

}
