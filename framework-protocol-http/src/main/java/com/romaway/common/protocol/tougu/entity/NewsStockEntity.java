package com.romaway.common.protocol.tougu.entity;

/**
 * Created by Administrator on 2018/11/23.
 */
public class NewsStockEntity {

    private String title;
    private String code;
    private String published_at;
    private String max_increase;
    private String is_secret;

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

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getMax_increase() {
        return max_increase;
    }

    public void setMax_increase(String max_increase) {
        this.max_increase = max_increase;
    }

    public String getIs_secret() {
        return is_secret;
    }

    public void setIs_secret(String is_secret) {
        this.is_secret = is_secret;
    }
}
