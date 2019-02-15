package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2018/1/19.
 */
public class StockNewsListEntity {

    private String id;
    private String source;
    private String title;
    private String time;
    private String no;
    private String grab;
    private String type;
    private String C_SNo;
    private String S_Name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getGrab() {
        return grab;
    }

    public void setGrab(String grab) {
        this.grab = grab;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getC_SNo() {
        return C_SNo;
    }

    public void setC_SNo(String c_SNo) {
        C_SNo = c_SNo;
    }

    public String getS_Name() {
        return S_Name;
    }

    public void setS_Name(String s_Name) {
        S_Name = s_Name;
    }
}
