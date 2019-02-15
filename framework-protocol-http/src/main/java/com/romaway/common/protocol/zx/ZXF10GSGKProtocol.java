package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * F10 公司概况
 * @author chenjp
 * @version 2015年7月10日 下午4:04:40
 */
public class ZXF10GSGKProtocol extends AProtocol{
	
	/**
	 * 返回完整json数据
	 */
	public String resp_news;
	/**
	 * 公司名称
	 */
	public String resp_gsmc;
	/**
	 * 上市日期
	 */
	public String resp_ssrq;
	/**
	 * 发行价格
	 */
	public String resp_fxjg;
	/**
	 * 发行数量
	 */
	public String resp_fxsl;
	/**
	 * 所属地区
	 */
	public String resp_ssdq;
	/**
	 * 所属行业
	 */
	public String resp_sshy;
	/**
	 * 主营业务
	 */
	public String resp_zyyw;

	/**
	 * 
	 * @param flag
	 * @param autoRefreshStatus
	 */
	public ZXF10GSGKProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/gsgk-";
	}

}
