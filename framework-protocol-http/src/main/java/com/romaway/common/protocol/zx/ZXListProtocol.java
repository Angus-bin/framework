package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 综合资讯列表
 * @author chenjp
 *
 */
public class ZXListProtocol extends AProtocol{
	/**
	 * 资讯列表总数
	 */
	public int resp_count;
	/**
	 * 资讯时间
	 */
	public String[] resp_time;
	/**
	 * 资讯标题
	 */
	public String[] resp_title;
	/**
	 * 资讯ID
	 */
	public String[] resp_titleId;
	/**
	 * json数据完整内容，用于缓存
	 */
	public String resp_news;

	public ZXListProtocol(String flag) {
		super(flag, false);
		isJson = true;
	}

}
