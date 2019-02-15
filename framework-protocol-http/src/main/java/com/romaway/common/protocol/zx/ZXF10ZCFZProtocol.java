package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 资产负债表
 * @author chenjp
 *
 */
public class ZXF10ZCFZProtocol extends AProtocol{
	
	/**
	 * 返回的完整Json数据
	 */
	public String resp_news;
	//流动资产
	/**
	 * 货币资金
	 */
	public String resp_FA01;
	/**
	 * 交易性金融资产
	 */
	public String resp_FA02;
	/**
	 * 应收票据
	 */
	public String resp_FA03;
	/**
	 * 应收股利
	 */
	public String resp_FA04;
	/**
	 * 应收利息
	 */
	public String resp_FA05;
	/**
	 * 应收账款
	 */
	public String resp_FA06;
	/**
	 * 其他应收款
	 */
	public String resp_FA07;
	/**
	 * 预付款项
	 */
	public String resp_FA08;
	/**
	 * 存货
	 */
	public String resp_FA09;
	/**
	 * 一年内到期的非流动资产
	 */
	public String resp_FA10;
	/**
	 * 其他流动资产
	 */
	public String resp_FA11;
	/**
	 * 流动资产合计
	 */
	public String resp_FA12;
	
	//非流动资产
	/**
	 * 可供出售金融资产
	 */
	public String resp_FB01;
	/**
	 * 持有至到期投资
	 */
	public String resp_FB02;
	/**
	 * 投资性房地产
	 */
	public String resp_FB03;
	/**
	 * 长期股权投资
	 */
	public String resp_FB04;
	/**
	 * 长期应收款
	 */
	public String resp_FB05;
	/**
	 * 固定资产
	 */
	public String resp_FB06;
	/**
	 * 工程物资
	 */
	public String resp_FB07;
	/**
	 * 在建工程
	 */
	public String resp_FB08;
	/**
	 * 固定资产清理
	 */
	public String resp_FB09;
	/**
	 * 生产性生物资产
	 */
	public String resp_FB10;
	/**
	 * 油气资产
	 */
	public String resp_FB11;
	/**
	 * 无形资产
	 */
	public String resp_FB12;
	/**
	 * 开发支出
	 */
	public String resp_FB13;
	/**
	 * 商誉
	 */
	public String resp_FB14;
	/**
	 * 长期待摊费用
	 */
	public String resp_FB15;
	/**
	 * 递延所得税资产
	 */
	public String resp_FB16;
	/**
	 * 其他非流动资产
	 */
	public String resp_FB17;
	/**
	 * 非流动资产合计
	 */
	public String resp_FB18;
	
	//资产总计
	/**
	 * 资产总计
	 */
	public String resp_FC01;
	
	//流动负债
	/**
	 * 短期借款
	 */
	public String resp_FD01;
	/**
	 * 交易性金融负债
	 */
	public String resp_FD02;
	/**
	 * 应付票据
	 */
	public String resp_FD03;
	/**
	 * 应付账款
	 */
	public String resp_FD04;
	/**
	 * 应付短期债券
	 */
	public String resp_FD05;
	/**
	 * 预收款项
	 */
	public String resp_FD06;
	/**
	 * 应付职工薪酬
	 */
	public String resp_FD07;
	/**
	 * 应付股利
	 */
	public String resp_FD08;
	/**
	 * 应交税费
	 */
	public String resp_FD09;
	/**
	 * 应付利息
	 */
	public String resp_FD10;
	/**
	 * 其他应付款
	 */
	public String resp_FD11;
	/**
	 * 一年内到期的非流动负债
	 */
	public String resp_FD12;
	/**
	 * 其他流动负债
	 */
	public String resp_FD13;
	/**
	 * 流动负债合计
	 */
	public String resp_FD14;
	
	//非流动负债
	/**
	 * 长期借款
	 */
	public String resp_FE01;
	/**
	 * 应付债券
	 */
	public String resp_FE02;
	/**
	 * 长期应付款
	 */
	public String resp_FE03;
	/**
	 * 专项应付款
	 */
	public String resp_FE04;
	/**
	 * 递延所得税负债
	 */
	public String resp_FE05;
	/**
	 * 其他非流动负债
	 */
	public String resp_FE06;
	/**
	 * 非流动负债合计
	 */
	public String resp_FE07;
	
	//负债合计
	/**
	 * 负债合计
	 */
	public String resp_FF01;
	
	//所有者权益（或股东权益）
	/**
	 * 实收资本（或股本）
	 */
	public String resp_FG01;
	/**
	 * 资本公积
	 */
	public String resp_FG02;
	/**
	 * 减：库存股
	 */
	public String resp_FG03;
	/**
	 * 盈余公积
	 */
	public String resp_FG04;
	/**
	 * 未分配利润
	 */
	public String resp_FG05;
	/**
	 * 归属母公司股东权益合计
	 */
	public String resp_FG06;
	/**
	 * 少数股东权益
	 */
	public String resp_FG07;
	/**
	 * 所有者权益（或股东权益）合计
	 */
	public String resp_FG08;
	/**
	 * 负债和所有者权益（或股东权益）总计
	 */
	public String resp_FG09;

	public ZXF10ZCFZProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/zcfz-";
	}

}
