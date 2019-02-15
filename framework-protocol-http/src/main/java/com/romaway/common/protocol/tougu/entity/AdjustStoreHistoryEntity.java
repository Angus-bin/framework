package com.romaway.common.protocol.tougu.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chenjp on 2016/7/9.
 */
public class AdjustStoreHistoryEntity {

    private String dayandtimes;
    private String earningsrates;
    private String isholds;
    private String status;
    private String sharename;
    private String sharenum;
    private String shareid;
    private String dealprice;
    private String sharetype;
    private String earningsrate;
    private String ishold;
    private String holdday;

    private ArrayList<AdjustStoreHistoryEntity2> list;

    public String getDayandtimes() {
        return dayandtimes;
    }

    public void setDayandtimes(String dayandtimes) {
        this.dayandtimes = dayandtimes;
    }

    public String getEarningsrates() {
        return earningsrates;
    }

    public void setEarningsrates(String earningsrates) {
        this.earningsrates = earningsrates;
    }

    public String getIsholds() {
        return isholds;
    }

    public void setIsholds(String isholds) {
        this.isholds = isholds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getShareid() {
        return shareid;
    }

    public void setShareid(String shareid) {
        this.shareid = shareid;
    }

    public String getDealprice() {
        return dealprice;
    }

    public void setDealprice(String dealprice) {
        this.dealprice = dealprice;
    }

    public String getSharetype() {
        return sharetype;
    }

    public void setSharetype(String sharetype) {
        this.sharetype = sharetype;
    }

    public String getEarningsrate() {
        return earningsrate;
    }

    public void setEarningsrate(String earningsrate) {
        this.earningsrate = earningsrate;
    }

    public String getIshold() {
        return ishold;
    }

    public void setIshold(String ishold) {
        this.ishold = ishold;
    }

    public String getHoldday() {
        return holdday;
    }

    public void setHoldday(String holdday) {
        this.holdday = holdday;
    }

    private HashMap<Integer, List<AdjustStoreHistoryEntity2>> datas;

    public HashMap<Integer, List<AdjustStoreHistoryEntity2>> getDatas() {
        return datas;
    }

    public void setDatas(HashMap<Integer, List<AdjustStoreHistoryEntity2>> datas) {
        this.datas = datas;
    }
}
