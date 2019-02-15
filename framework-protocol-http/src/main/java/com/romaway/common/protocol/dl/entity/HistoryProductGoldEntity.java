package com.romaway.common.protocol.dl.entity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/19.
 */
public class HistoryProductGoldEntity {

    private String is_in;
    private String increase;
    private String is_gain;
    private String time;
    private String is_hold;
    private List<HistoryProductGoldListEntity> list;

    public String getIs_in() {
        return is_in;
    }

    public void setIs_in(String is_in) {
        this.is_in = is_in;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getIs_gain() {
        return is_gain;
    }

    public void setIs_gain(String is_gain) {
        this.is_gain = is_gain;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIs_hold() {
        return is_hold;
    }

    public void setIs_hold(String is_hold) {
        this.is_hold = is_hold;
    }

    public List<HistoryProductGoldListEntity> getList() {
        return list;
    }

    public void setList(List<HistoryProductGoldListEntity> list) {
        this.list = list;
    }
}
