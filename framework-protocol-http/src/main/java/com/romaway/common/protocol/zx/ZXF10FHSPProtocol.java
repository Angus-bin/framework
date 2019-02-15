package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * F10 分红送配
 * @author chenjp
 * @version 2015年7月14日 下午2:15:51
 */
public class ZXF10FHSPProtocol extends AProtocol{
	
	/**
	 * 返回完整json数据
	 */
	public String resp_news;
	/**
	 * 数据总数
	 */
	public int resp_count;
	/**
	 * 分红方案
	 */
	public String[]	resp_fhfa;
	/**
	 * 分红年度
	 */
	public String[] resp_fhnd;
	/**
	 * 除权日期
	 */
	public String[] resp_cqrq;

	/**
	 * F10 分红送配
	 * @param flag
	 * @param autoRefreshStatus
	 */
	public ZXF10FHSPProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/fhsp-";
	}

}
