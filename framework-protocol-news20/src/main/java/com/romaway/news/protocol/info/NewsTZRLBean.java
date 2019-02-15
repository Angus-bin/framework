package com.romaway.news.protocol.info;

/**
 * Created by dell on 2016/8/17.
 */
public class NewsTZRLBean {
        private String resp_id;
        private String resp_date;
        private String resp_field;
        private String resp_stock;
        private String resp_title;
        /**
         * 今天 0
         * 其他 1
         */
        private String resp_type;

    public String getResp_id() {
        return resp_id;
    }

    public void setResp_id(String resp_id) {
        this.resp_id = resp_id;
    }

    public String getResp_date() {
        return resp_date;
    }

    public void setResp_date(String resp_date) {
        this.resp_date = resp_date;
    }

    public String getResp_field() {
        return resp_field;
    }

    public void setResp_field(String resp_field) {
        this.resp_field = resp_field;
    }

    public String getResp_stock() {
        return resp_stock;
    }

    public void setResp_stock(String resp_stock) {
        this.resp_stock = resp_stock;
    }

    public String getResp_title() {
        return resp_title;
    }

    public void setResp_title(String resp_title) {
        this.resp_title = resp_title;
    }

    public String getResp_type() {
        return resp_type;
    }

    public void setResp_type(String resp_type) {
        this.resp_type = resp_type;
    }
}
