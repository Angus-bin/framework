package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 利润表
 * @author chenjp
 *
 */
public class ZXF10LRBProtocol extends AProtocol{
	
	/**
	 * 返回的完整json数据
	 */
	public String resp_news;
	/**
	 * 其中：营业成本
	 */
	public String resp_FA01;
	/**
	 * 营业税金及附加
	 */
	public String resp_FA02;
	/**
	 * 销售费用
	 */
	public String resp_FA03;
	/**
	 * 管理费用
	 */
	public String resp_FA04;
	/**
	 * 财务费用
	 */
	public String resp_FA05;
	/**
	 * 资产减值损失
	 */
	public String resp_FA06;
	/**
	 * 公允价值变动净收益
	 */
	public String resp_FA07;
	/**
	 * 投资净收益
	 */
	public String resp_FA08;
	/**
	 * 其中:对联营合营企业的投资收益
	 */
	public String resp_FA09;
	/**
	 * 营业利润
	 */
	public String resp_FB01;
	/**
	 * 加:营业外收入
	 */
	public String resp_FB02;
	/**
	 * 减:营业外支出
	 */
	public String resp_FB03;
	/**
	 * 其中:非流动资产处置净损失
	 */
	public String resp_FB04;
	/**
	 * 利润总额
	 */
	public String resp_FC01;
	/**
	 * 减:所得税费用
	 */
	public String resp_FC02;
	/**
	 * 净利润
	 */
	public String resp_FD01;
	/**
	 * 归属于母公司所有者的净利润
	 */
	public String resp_FD02;
	/**
	 * 少数股东损益
	 */
	public String resp_FD03;
	//每股收益
	/**
	 * 基本每股收益
	 */
	public String resp_FE01;
	/**
	 * 稀释每股收益
	 */
	public String resp_FE02;

	public ZXF10LRBProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/lrb-";
	}

}
