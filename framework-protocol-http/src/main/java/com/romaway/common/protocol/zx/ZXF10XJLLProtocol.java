package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 现金流量表
 * @author chenjp
 *
 */
public class ZXF10XJLLProtocol extends AProtocol{
	
	/**
	 * 返回的完整json数据
	 */
	public String resp_news;
	//一、经营活动产生的现金流量
	/**
	 * 销售商品、提供劳务收到的现金
	 */
	public String resp_FA01;
	/**
	 * 收到的税费返还
	 */
	public String resp_FA02;
	/**
	 * 收到其他与经营活动有关的现金
	 */
	public String resp_FA03;
	/**
	 * 经营活动现金流入小计
	 */
	public String resp_FA04;
	/**
	 * 购买商品、接受劳务支付的现金
	 */
	public String resp_FA05;
	/**
	 * 支付给职工以及为职工支付的现金
	 */
	public String resp_FA06;
	/**
	 * 支付的各项税费
	 */
	public String resp_FA07;
	/**
	 * 支付其他与经营活动有关的现金
	 */
	public String resp_FA08;
	/**
	 * 经营活动现金流出小计
	 */
	public String resp_FA09;
	/**
	 * 经营活动产生的现金流量净额
	 */
	public String resp_FA10;
	
	//二、投资活动产生的现金流量
	/**
	 * 收回投资收到的现金
	 */
	public String resp_FB01;
	/**
	 * 取得投资收益收到的现金
	 */
	public String resp_FB02;
	/**
	 * 处置固定资产、无形资产和其他长期资产收回的现金净额
	 */
	public String resp_FB03;
	/**
	 * 处置子公司及其他营业单位收到的现金净额
	 */
	public String resp_FB04;
	/**
	 * 收到其他与投资活动有关的现金
	 */
	public String resp_FB05;
	/**
	 * 投资活动现金流入小计
	 */
	public String resp_FB06;
	/**
	 * 购建固定资产、无形资产和其他长期资产支付的现金
	 */
	public String resp_FB07;
	/**
	 * 投资支付的现金
	 */
	public String resp_FB08;
	/**
	 * 取得子公司及其他营业单位支付的现金净额
	 */
	public String resp_FB09;
	/**
	 * 质押贷款净增加额
	 */
	public String resp_FB10;
	/**
	 * 支付其他与投资活动有关的现金
	 */
	public String resp_FB11;
	/**
	 * 投资活动现金流出小计
	 */
	public String resp_FB12;
	/**
	 * 投资活动产生的现金流量净额
	 */
	public String resp_FB13;
	
	//三、筹资活动产生的现金流量
	/**
	 * 吸收投资收到的现金
	 */
	public String resp_FC01;
	/**
	 * 取得借款收到的现金
	 */
	public String resp_FC02;
	/**
	 * 收到其他与筹资活动有关的现金
	 */
	public String resp_FC03;
	/**
	 * 筹资活动现金流入小计
	 */
	public String resp_FC04;
	/**
	 * 偿还债务支付的现金
	 */
	public String resp_FC05;
	/**
	 * 分配股利、利润或偿付利息支付的现金
	 */
	public String resp_FC06;
	/**
	 * 支付其他与筹资活动有关的现金
	 */
	public String resp_FC07;
	/**
	 * 筹资活动现金流出小计
	 */
	public String resp_FC08;
	/**
	 * 筹资活动产生的现金流量净额
	 */
	public String resp_FC09;
	
	//四、现金及现金等价物
	/**
	 * 汇率变动对现金及现金等价物的影响
	 */
	public String resp_FD01;
	/**
	 * 现金及现金等价物净增加额
	 */
	public String resp_FD02;
	/**
	 * 期末现金及现金等价物余额
	 */
	public String resp_FD03;
	
	//将净利润调节为经营活动的现金流量
	//1．将净利润调节为经营活动现金流量
	/**
	 * 净利润
	 */
	public String resp_FE01;
	/**
	 * 加:资产减值准备
	 */
	public String resp_FE02;
	/**
	 * 固定资产折旧
	 */
	public String resp_FE03;
	/**
	 * 无形资产摊销
	 */
	public String resp_FE04;
	/**
	 * 长期待摊费用摊销
	 */
	public String resp_FE05;
	/**
	 * 处置固定资产、无形资产和其他长期资产的损失
	 */
	public String resp_FE06;
	/**
	 * 固定资产报废损失
	 */
	public String resp_FE07;
	/**
	 * 公允价值变动损失
	 */
	public String resp_FE08;
	/**
	 * 财务费用
	 */
	public String resp_FE09;
	/**
	 * 投资损失
	 */
	public String resp_FE10;
	/**
	 * 递延所得税资产减少
	 */
	public String resp_FE11;
	/**
	 * 递延所得税负债增加
	 */
	public String resp_FE12;
	/**
	 * 存货的减少
	 */
	public String resp_FE13;
	/**
	 * 经营性应收项目的减少
	 */
	public String resp_FE14;
	/**
	 * 经营性应付项目的增加
	 */
	public String resp_FE15;
	/**
	 * 其他
	 */
	public String resp_FE16;
	/**
	 * (附注)经营活动产生的现金流量净额
	 */
	public String resp_FE17;
	//2．不涉及现金收支的投资和筹资活动
	/**
	 * 债务转为资本
	 */
	public String resp_FE18;
	/**
	 * 一年内到期的可转换公司债券
	 */
	public String resp_FE19;
	/**
	 * 融资租入固定资产
	 */
	public String resp_FE20;
	//3．现金及现金等价物净变动情况
	/**
	 * 现金的期末余额
	 */
	public String resp_FE21;
	/**
	 * 减:现金的期初余额
	 */
	public String resp_FE22;
	/**
	 * 加:现金等价物的期末余额
	 */
	public String resp_FE23;
	/**
	 * 减:现金等价物的期初余额
	 */
	public String resp_FE24;
	/**
	 * (附注)现金及现金等价物净增加额
	 */
	public String resp_FE25;

	public ZXF10XJLLProtocol(String resp_Flag, boolean autoReFreshStatus) {
		super(resp_Flag, autoReFreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/xjll-";
	}

}
