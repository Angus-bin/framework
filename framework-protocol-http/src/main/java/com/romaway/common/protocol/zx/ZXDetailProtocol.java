package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 资讯详情
 * @author chenjp
 *
 */
public class ZXDetailProtocol extends AProtocol{
	
	/**
	 * 资讯完整内容，用于缓存
	 */
	public String resp_new;
	/**
	 * 资讯时间
	 */
	public String resp_time;
	/**
	 * 资讯标题
	 */
	public String resp_title;
	/**
	 * 资讯ID
	 */
	public String resp_titleId;
	/**
	 * 资讯内容
	 */
	public String resp_content;
	/**
	 * 资讯来源
	 */
	public String resp_source;
	/**
	 * 类别代码
	 */
	public String resp_categoryCode;
	/**
	 * 类别名称
	 */
	public String resp_categoryName;

	public ZXDetailProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/news/content/";
	}

}
