package com.romaway.common.protocol.tougu.entity;

import java.io.Serializable;

/**
 * Created by hongrb on 2017/5/31.
 */
public class BannerInfoEntity implements Serializable {

    private String imgUrl;
    private String webUrl;

    public BannerInfoEntity() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
