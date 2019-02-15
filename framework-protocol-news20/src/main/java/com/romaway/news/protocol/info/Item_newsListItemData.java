package com.romaway.news.protocol.info;

import android.support.annotation.Keep;

/** 
 * @author  万籁唤
 * 创建时间：2016年3月24日 下午2:47:15
 * @version 1.0 
 */
@Keep
public class Item_newsListItemData {
	
	/**
	 * 所属的栏目
	 */
	public String funType;
	
	/**
	 * 资讯时间
	 */
	public String time;//资讯时间
	
	/**
	 * 放在界面上展示的时间字符串
	 */
	public String timeShow;
	
	/**
	 * 资讯标题
	 */
	public String title;//标题
	
	/**
	 * 资讯的重要性 1：重要；0：不重要
	 */
    public String important;	
	/**
	 * 资讯ID
	 */
	public String newsId;//资讯ID
	
	/**
	 * 股票代码和市场代码
	 */
	public String code;
	
	/**
	 * 股票名称
	 */
	public String name;
	
	/**
	 * 资讯摘要
	 */
	public String digest;//摘要内容
	/**
	 * 图片类型
	 */
	public String imgType;
	
	/**
	 * 选项布局类型
	 */
	public String layout;// 布局类型：0：无图； 1：左一张图 2:一张大图 3:三张图 4:右一张图 5:右一张图并且是视频
	
	/**
	 * 图片的url
	 */
	public String imgsrc1;//图片url 
	/**
	 * 图片的url
	 */
	public String imgsrc2;//图片url
	/**
	 * 图片的url
	 */
	public String imgsrc3;//图片url
	
	/**
	 * 资讯的来源
	 */
	public String source;//资讯来源
	/**
	 * 资讯类型 
	 */
	public String newsType;//类型：0：正常类型；1：独家;2：专题； 3:推广
	
	/**
	 *列表已读未读标志
	 */
	public String readFlag;
	
	/**
	 * 排序ID
	 */
	//public String orderId;
	
	/**
	 * 专题标记，数据请求之用
	 */
	public String topic;
	/**
	 * 摘要
	 */
	public String descrip;
	/**
	 * 有财报专用，0 正常  1 删除
	 */
	public String active;
}
