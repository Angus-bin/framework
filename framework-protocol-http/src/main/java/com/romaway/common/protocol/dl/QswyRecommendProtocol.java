package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.QswyRecommendEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/12/1.
 */
public class QswyRecommendProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    private List<QswyRecommendEntity> list;

    public List<QswyRecommendEntity> getList() {
        return list;
    }

    public void setList(List<QswyRecommendEntity> list) {
        this.list = list;
    }

    public QswyRecommendProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/recom";
    }

}
