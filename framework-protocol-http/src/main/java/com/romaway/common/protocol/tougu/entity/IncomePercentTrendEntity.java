package com.romaway.common.protocol.tougu.entity;

/** 收益率走势
 * Created by chenjp on 2016/7/9.
 */
public class IncomePercentTrendEntity {

	private String closePrice;
	private String dayNetWorth;
	private String hsDate;
	private String syDate;

	public String getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}

	public String getDayNetWorth() {
		return dayNetWorth;
	}

	public void setDayNetWorth(String dayNetWorth) {
		this.dayNetWorth = dayNetWorth;
	}

	public String getHsDate() {
		return hsDate;
	}

	public void setHsDate(String hsDate) {
		this.hsDate = hsDate;
	}

	public String getSyDate() {
		return syDate;
	}

	public void setSyDate(String syDate) {
		this.syDate = syDate;
	}
}
