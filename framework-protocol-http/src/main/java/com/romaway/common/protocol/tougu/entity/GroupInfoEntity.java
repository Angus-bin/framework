package com.romaway.common.protocol.tougu.entity;

import java.io.Serializable;

/**
 * Created by chenjp on 2016/7/9.
 */
public class GroupInfoEntity implements Serializable {

	private String prosandcons;
	private String newsname;
	private String newscomment;
	private String newsimage;
	private String publishtime;
	private String readcounts;
	private String weburl;
	private String newsid;
	private String heatlevel;
	private String goodnum;
	private String isgood;
	private String stock;
	private String isread;

	public GroupInfoEntity(){

	}

	public String getProsandcons() {
		return prosandcons;
	}

	public void setProsandcons(String prosandcons) {
		this.prosandcons = prosandcons;
	}

	public String getNewsname() {
		return newsname;
	}

	public void setNewsname(String newsname) {
		this.newsname = newsname;
	}

	public String getNewscomment() {
		return newscomment;
	}

	public void setNewscomment(String newscomment) {
		this.newscomment = newscomment;
	}

	public String getNewsimage() {
		return newsimage;
	}

	public void setNewsimage(String newsimage) {
		this.newsimage = newsimage;
	}

	public String getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}

	public String getReadcounts() {
		return readcounts;
	}

	public void setReadcounts(String readcounts) {
		this.readcounts = readcounts;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public String getNewsid() {
		return newsid;
	}

	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}

	public String getHeatlevel() {
		return heatlevel;
	}

	public void setHeatlevel(String heatlevel) {
		this.heatlevel = heatlevel;
	}

	public String getGoodnum() {
		return goodnum;
	}

	public void setGoodnum(String goodnum) {
		this.goodnum = goodnum;
	}

	public String getIsgood() {
		return isgood;
	}

	public void setIsgood(String isgood) {
		this.isgood = isgood;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}
}
