package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HQGGTEDProtocol extends AProtocol {
	// 返回数据
    /**
     * 市场代码，港股通不需要，深港通需要
     */
    public String req_market;
	/**
	 * 额度完整内容，用于缓存
	 */
	public String resp_ed;

	/**
	 * 额度状态: 1：额度用完或其他原因全市场禁止买入； 2：额度可用。
	 */
	public String resp_amountFlag;
	/**
	 * 每日初始额度单位人民币元
	 */
	public String resp_initAmount;

	/**
	 * 日中剩余额度，单位人民币元 ifloat类型
	 */
	public String resp_lastAmount;

	/**
	 * 第1位：‘0’表示全市场限制买入，‘1’表示正常无此限制。 第2位：‘0’表示全市场限制卖出，‘1’表示正常无此限制。
	 * 第3位：‘0’表示当日非港股通交易日，‘1’表示当日港股通交易日。
	 */
	public String resp_mkStatus;

	/**
	 * 更新日期
	 */
	public String resp_updateDate;

	/**
	 * 更新时间
	 */
	public String resp_updateTime;

	public HQGGTEDProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/quote/trdses";
	}

	/**
	 * 获取当日交易状态信息
	 * 
	 * @return
	 */
	public String getJYStatusMessage() {
		if (!StringUtils.isEmpty(resp_mkStatus) && this.resp_mkStatus.length() >= 3) {
			char status = this.resp_mkStatus.charAt(2);

			if (status == '0') {
				return "今日非交易日";
			} else {
				return "今日为交易日";
			}
		} else {
			Logger.d("HQGGTEDProtocol", "resp_mkStatus：" + this.resp_mkStatus);
		}

		return "";
	}

	/**
	 * 获取更新信息
	 * 
	 * @return
	 */
	public String getUpdateMessage(){
		String dateStr = this.resp_updateDate;
		String time = this.resp_updateTime;
		
		try {
			if (!StringUtils.isEmpty(dateStr)) {
				Date date = new SimpleDateFormat("yyyy/MM/dd").parse(this.resp_updateDate);
				dateStr = new SimpleDateFormat("yyyy/MM/dd").format(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		return String.format("更新于 %s %s", dateStr, time);
	}
	/**
	 * 获取更新信息
	 * 
	 * @return
	 */
	public String getUpdateMessage2(){
		String dateStr = this.resp_updateDate;
		String time = this.resp_updateTime;
		
		try {
			if (!StringUtils.isEmpty(dateStr)) {
				Date date = new SimpleDateFormat("yy/MM/dd").parse(this.resp_updateDate);
				dateStr = new SimpleDateFormat("yy/MM/dd").format(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return String.format("%s %s", dateStr, time);
	}
}
