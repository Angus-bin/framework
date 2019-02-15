package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

public class ZXNewStockProtocol extends AProtocol{
	/**
	 * 返回码
	 */
	public int resp_code;
	/**
	 * 返回信息
	 */
	public String resp_message;
	/**
	 * 返回信息记录数
	 */
	public int resp_count;
	/**
	 * 交易所代码
	 */
	public String[] resp_jysdm;
	/**
	 * 证券代码
	 */
	public String[] resp_zqdm;
	/**
	 * 证券名称
	 */
	public String[] resp_zqmc;
	/**
	 * 申购代码
	 */
	public String[] resp_sgdm;
	/**
	 * 发行日期
	 */
	public String[] resp_fxrq;
	/**
	 * 总发行数量
	 */
	public String[] resp_zfxsl;
	/**
	 * 发行价格
	 */
	public String[] resp_fxjg;
	/**
	 * 申购限额
	 */
	public String[] resp_sgxe;
	/**
	 * 中签公布日期
	 */
	public String[] resp_zqgbrq;
	/**
	 * 网上发行数量
	 */
	public String[] resp_wsfxsl;
	/**
	 * 发行市盈率
	 */
	public String[] resp_fxsyl;
	/**
	 * 资金解冻日期
	 */
	public String[] resp_zjjdrq;

	public ZXNewStockProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/news/ipo/ipo.json";
	}

}
