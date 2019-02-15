package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2017/9/18.
 */
public class BookQswtItemEntity {

    private String id;
    private String type;
    private String title;
    private String code;
    private String price;
    private String time;
    private String increase;
    private String now_increase;
    private String cangwei;

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
}
