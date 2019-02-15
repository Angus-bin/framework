package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.QswyZxtcListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/10/8.
 */
public class QswyZxtcDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_id;

    public String resp_new_date;

    private List<QswyZxtcListEntity> list;

    public List<QswyZxtcListEntity> getList() {
        return list;
    }

    public void setList(List<QswyZxtcListEntity> list) {
        this.list = list;
    }

    /**
     *
     * @param flag
     */
    public QswyZxtcDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/recentOption";
    }

}
