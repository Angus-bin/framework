package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2017/11/5.
 */
public class HistoryHoldEntity {

    private String id;
    private String title;
    private String code;
    private String price;
    private String increase;
    private String date;
    private String gegu_income;
    private String leiji_cangwei;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGegu_income() {
        return gegu_income;
    }

    public void setGegu_income(String gegu_income) {
        this.gegu_income = gegu_income;
    }

    public String getLeiji_cangwei() {
        return leiji_cangwei;
    }

    public void setLeiji_cangwei(String leiji_cangwei) {
        this.leiji_cangwei = leiji_cangwei;
    }
}
