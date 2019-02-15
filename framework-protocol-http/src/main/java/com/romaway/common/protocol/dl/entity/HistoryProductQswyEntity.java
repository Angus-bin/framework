package com.romaway.common.protocol.dl.entity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/19.
 */
public class HistoryProductQswyEntity {

    private String time;
    private List<HistoryProductQswyListEntity> list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<HistoryProductQswyListEntity> getList() {
        return list;
    }

    public void setList(List<HistoryProductQswyListEntity> list) {
        this.list = list;
    }
}
