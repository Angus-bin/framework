package com.romaway.common.protocol.dl.entity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/18.
 */
public class BookQswyEntity {

    private String id;
    private String title;
    private String sub_title;
    private String less_time;
    private String intro;
    private String new_sum;
    private String pro_sum;
    private String sort_sum;
    private List<BookQswtItemEntity> list;

    private String space_rate;

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

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getLess_time() {
        return less_time;
    }

    public void setLess_time(String less_time) {
        this.less_time = less_time;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNew_sum() {
        return new_sum;
    }

    public void setNew_sum(String new_sum) {
        this.new_sum = new_sum;
    }

    public String getPro_sum() {
        return pro_sum;
    }

    public void setPro_sum(String pro_sum) {
        this.pro_sum = pro_sum;
    }

    public String getSort_sum() {
        return sort_sum;
    }

    public void setSort_sum(String sort_sum) {
        this.sort_sum = sort_sum;
    }

    public List<BookQswtItemEntity> getList() {
        return list;
    }

    public void setList(List<BookQswtItemEntity> list) {
        this.list = list;
    }

    public String getSpace_rate() {
        return space_rate;
    }

    public void setSpace_rate(String space_rate) {
        this.space_rate = space_rate;
    }
}
