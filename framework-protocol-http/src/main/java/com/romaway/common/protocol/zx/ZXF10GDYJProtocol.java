package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * F10 股东研究
 * @author chenjp
 * @version 2015年7月14日 下午3:55:46
 */
public class ZXF10GDYJProtocol extends AProtocol{
	
	/**
	 * 返回的完整json数据
	 */
	public String resp_news;
	/**
	 * 前十大股东数量
	 */
	public int resp_count1;
	/**
	 * 股东排名
	 */
	public String[] resp_gdpm1;
	/**
	 * 股东名称
	 */
	public String[] resp_gdmc1;
	/**
	 * 占总股本比例
	 */
	public String[] resp_zgbbl1;
	/**
	 * 占流通A股比例
	 */
	public String[] resp_agbl1;
	/**
	 * 持股变动
	 */
	public String[] resp_cgbd1;
	
	/**
	 * 前十流通股东
	 */
	public int resp_count2;
	/**
	 * 股东排名
	 */
	public String[] resp_gdpm2;
	/**
	 * 股东名称
	 */
	public String[] resp_gdmc2;
	/**
	 * 占总股本比例
	 */
	public String[] resp_zgbbl2;
	/**
	 * 占流通A股比例
	 */
	public String[] resp_agbl2;
	/**
	 * 持股变动
	 */
	public String[] resp_cgbd2;
	

	/**
	 * F10 股东研究
	 * @param flag
	 * @param autoRefreshStatus
	 */
	public ZXF10GDYJProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/gdyj-";
	}

}
