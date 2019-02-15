package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * F10 基金持股
 * @author chenjp
 * @version 2015年7月14日 下午4:44:00
 */
public class ZXF10JJCGProtocol extends AProtocol{
	
	/**
	 * 返回的完整json数据
	 */
	public String resp_news;
	/**
	 * 数据个数
	 */
	public int resp_count;
	/**
	 * 基金名称
	 */
	public String[] resp_jjmc;
	/**
	 * 占股比例
	 */
	public String[] resp_zgbl;
	/**
	 * 持股变动
	 */
	public String[] resp_cgbd;

	/**
	 * F10 基金持股
	 * @param flag
	 * @param autoRefreshStatus
	 */
	public ZXF10JJCGProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/jjcg-";
	}

}
