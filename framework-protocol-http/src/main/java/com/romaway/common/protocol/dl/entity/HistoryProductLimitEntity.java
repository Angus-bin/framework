package com.romaway.common.protocol.dl.entity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/19.
 */
public class HistoryProductLimitEntity {

    private String total;
    private String time;
    private List<HistoryProductLimitListEntity> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<HistoryProductLimitListEntity> getList() {
        return list;
    }

    public void setList(List<HistoryProductLimitListEntity> list) {
        this.list = list;
    }
}
