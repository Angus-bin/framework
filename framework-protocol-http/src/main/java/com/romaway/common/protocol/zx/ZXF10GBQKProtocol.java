package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * F10 股本情况
 * @author chenjp
 * @version 2015年7月14日 下午3:45:52
 */
public class ZXF10GBQKProtocol extends AProtocol{

	/**
	 * 返回的完整json数据
	 */
	public String resp_news;
	/**
	 * 总股本
	 */
	public String resp_zgb;
	/**
	 * 流通A股
	 */
	public String resp_ltag;
	/**
	 * 股东人数
	 */
	public String resp_gdrs;
	/**
	 * 人均持股
	 */
	public String resp_rjcg;
	/**
	 * F10 股本情况
	 * @param flag
	 * @param autoRefreshStatus
	 */
	public ZXF10GBQKProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/gbqk-";
	}

}
