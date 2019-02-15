package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2017/6/8.
 */
public class HoldStockListEntity {

    private String shareid;
    private String sharename;
    private String sharenum;
    private String dealprice;
    private String nowprice;
    private String earningsratetoday;
    private String earningsrateall;

    public String getShareid() {
        return shareid;
    }

    public void setShareid(String shareid) {
        this.shareid = shareid;
    }

    public String getSharename() {
        return sharename;
    }

    public void setSharename(String sharename) {
        this.sharename = sharename;
    }

    public String getSharenum() {
        return sharenum;
    }

    public void setSharenum(String sharenum) {
        this.sharenum = sharenum;
    }

    public String getDealprice() {
        return dealprice;
    }

    public void setDealprice(String dealprice) {
        this.dealprice = dealprice;
    }

    public String getNowprice() {
        return nowprice;
    }

    public void setNowprice(String nowprice) {
        this.nowprice = nowprice;
    }

    public String getEarningsratetoday() {
        return earningsratetoday;
    }

    public void setEarningsratetoday(String earningsratetoday) {
        this.earningsratetoday = earningsratetoday;
    }

    public String getEarningsrateall() {
        return earningsrateall;
    }

    public void setEarningsrateall(String earningsrateall) {
        this.earningsrateall = earningsrateall;
    }
}
