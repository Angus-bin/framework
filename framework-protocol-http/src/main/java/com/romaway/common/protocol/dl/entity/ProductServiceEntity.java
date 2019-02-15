package com.romaway.common.protocol.dl.entity;

/**
 * Created by Administrator on 2018/8/30.
 */
public class ProductServiceEntity {

    private String funCode;
    private String img_url;
    private String link_url;
    private String isBook;

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getIsBook() {
        return isBook;
    }

    public void setIsBook(String isBook) {
        this.isBook = isBook;
    }
}
