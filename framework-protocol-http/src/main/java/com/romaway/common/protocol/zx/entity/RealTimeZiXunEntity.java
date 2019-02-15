package com.romaway.common.protocol.zx.entity;

import java.io.Serializable;

/**
 * Created by hongrb on 2017/9/7.
 */
public class RealTimeZiXunEntity implements Serializable {

    private String prosandcons;
    private String title;
    private String intro;
    private String weburl;
    private String publishtime;

    public String getProsandcons() {
        return prosandcons;
    }

    public void setProsandcons(String prosandcons) {
        this.prosandcons = prosandcons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }
}
