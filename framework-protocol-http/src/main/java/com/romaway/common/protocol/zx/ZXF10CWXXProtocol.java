package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * F10 财务信息
 * @author chenjp
 * @version 2015年7月14日 下午4:56:03
 */
public class ZXF10CWXXProtocol extends AProtocol{
	
	/**
	 * 返回的完整json数据
	 */
	public String resp_news;
	/**
	 * 截止日期
	 */
	public String resp_jzrq;
	/**
	 * 每股收益
	 */
	public String resp_mgsy;
	/**
	 * 营业收入
	 */
	public String resp_yysr;
	/**
	 * 主营收入增长率
	 */
	public String resp_zysrzzl;
	/**
	 * 营业利润
	 */
	public String resp_yylr;
	/**
	 * 投资收益
	 */
	public String resp_tzsy;
	/**
	 * 净利润
	 */
	public String resp_jlr;
	/**
	 * 每股净资产
	 */
	public String resp_mgjzc;
	/**
	 * 净资产收益率
	 */
	public String resp_jzcsyl;
	/**
	 * 资产总计
	 */
	public String resp_zczj;
	/**
	 * 负债总计
	 */
	public String resp_fzzj;
	/**
	 * 股东权益
	 */
	public String resp_gdqy;
	/**
	 * 每股现金流
	 */
	public String resp_mgxjl;
	/**
	 * 经营现金流净额
	 */
	public String resp_jyxjlje;
	/**
	 * 投资现金流净额
	 */
	public String resp_tzxjlje;
	/**
	 * 筹资现金流净额
	 */
	public String resp_czxjlje;

	/**
	 * F10 财务信息
	 * @param flag
	 * @param autoRefreshStatus
	 */
	public ZXF10CWXXProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		subFunUrl = "/api/news/f10/cwxx-";
	}

}
