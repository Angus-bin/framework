package com.romaway.news.protocol.info;

public class Item_newsTopic {
	
	/**
	 * 组名
	 */
	public String select;
	/**
	 * 资讯标题
	 */
	public String title;//标题
	
	/**
	 * 时间
	 */
	public String time;// 时间
	
	/**
	 * 用于在界面上展示的时间
	 */
	public String timeShow;
	/**
	 * 资讯ID
	 */
	public String newsId;//资讯ID
	
	/**
	 * 资讯摘要
	 */
	public String digest;//摘要内容
	
	/**
	 * 选项布局类型
	 */
	public String layout;// 布局类型：0：无图； 1：左一张图 2:一张大图 3:三张图 4:右一张图
	
	/**
	 * 图片的url
	 */
	public String imgsrc1;//图片url 
	
	/**
	 * 资讯的来源
	 */
	public String source;//资讯来源
	/**
	 * 资讯类型 
	 */
	public String newsType;//类型：0：正常类型；1：独家;2：专题； 3:推广
	
	/**
	 * 用来请求详情入参
	 */
	public String topic;
	
	/**
	 *列表已读未读标志
	 */
	public String readFlag;
}
