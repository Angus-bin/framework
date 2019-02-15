package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2017/9/19.
 */
public class HistoryProductLimitListEntity {

    private String id;
    private String title;
    private String code;
    private String in_price;
    private String first_increase;
    private String max_increase;
    private String date;
    private String times;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIn_price() {
        return in_price;
    }

    public void setIn_price(String in_price) {
        this.in_price = in_price;
    }

    public String getFirst_increase() {
        return first_increase;
    }

    public void setFirst_increase(String first_increase) {
        this.first_increase = first_increase;
    }

    public String getMax_increase() {
        return max_increase;
    }

    public void setMax_increase(String max_increase) {
        this.max_increase = max_increase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
