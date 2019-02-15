package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2017/9/14.
 */
public class BannerDetailEntity {

    private String id;
    private String jump_url;
    private String pic_url;
    private String funCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }
}
