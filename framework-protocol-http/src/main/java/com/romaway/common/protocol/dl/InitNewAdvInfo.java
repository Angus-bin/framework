package com.romaway.common.protocol.dl;

import android.support.annotation.Keep;

@Keep
public class InitNewAdvInfo {
	/**
	 * 广告数量
	 */
	public int resp_advtCount;
	
	/**
	 * 广告标题
	 */
	public String[] resp_advtTitle;
	/**
	 * 广告位置
	 */
	public String[] resp_advtPosition;
	/**
	 * 广告类型
	 */
	public int[] resp_advtType;
	/**
	 * 广告文字内容
	 */
	public String[] resp_advtContent;
	/**
	 * 广告图片地址
	 */
	public String[] resp_advtPicUrl;
	/**
	 * 广告链接
	 */
	public String[] resp_advtLinked;
	/**
	 * 广告备注信息
	 */
	public String[] resp_advtMemo;
	
	/**
	 * 广告展示是否需要原生Title
	 */
	public String[] resp_advSrcTitleVisibility;
	
	/**
	 * 广告展示跳转的登录方式 0:无需任何登录注册；1：仅仅手机号码注册； 大于等于2：必须要手机号码和资金账号登录
	 */
	public String[] resp_advWebViewLoginFlag;
	
	/**
	 * 广告结束时间
	 */
	public String[] resp_endtime;
	/**
	 * 广告开始时间
	 */
	public String[] resp_starttime;
}
