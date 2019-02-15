package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.GroupInfoDetailEntity;

import java.util.List;

/**
 * 组合信息
 * Created by hrb on 2016/8/4.
 */
public class GroupInfoDetailProtocol extends AProtocol {

    private List<GroupInfoDetailEntity> list;

    public List<GroupInfoDetailEntity> getList() {
        return list;
    }

    public void setList(List<GroupInfoDetailEntity> list) {
        this.list = list;
    }

    public GroupInfoDetailProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "/api/tg-service/portfolio/zhxx/select/";
    }
}
