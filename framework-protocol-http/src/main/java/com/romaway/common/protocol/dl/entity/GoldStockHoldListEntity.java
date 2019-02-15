package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2017/9/12.
 */
public class GoldStockHoldListEntity {

    private String id;
    private String type;
    private String title;
    private String code;
    private String price;
    private String now_price;
    private String time;
    private String increase;
    private String total_increase;
    private String today_increase;
    private String now_increase;
    private String cangwei;
    private String allow_look;
    private String times;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getNow_price() {
        return now_price;
    }

    public void setNow_price(String now_price) {
        this.now_price = now_price;
    }

    public String getTotal_increase() {
        return total_increase;
    }

    public void setTotal_increase(String total_increase) {
        this.total_increase = total_increase;
    }

    public String getToday_increase() {
        return today_increase;
    }

    public void setToday_increase(String today_increase) {
        this.today_increase = today_increase;
    }

    public String getNow_increase() {
        return now_increase;
    }

    public void setNow_increase(String now_increase) {
        this.now_increase = now_increase;
    }

    public String getCangwei() {
        return cangwei;
    }

    public void setCangwei(String cangwei) {
        this.cangwei = cangwei;
    }

    public String getAllow_look() {
        return allow_look;
    }

    public void setAllow_look(String allow_look) {
        this.allow_look = allow_look;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
