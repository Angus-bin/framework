package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
/**
 * 获取券商信息
 * @author XY
 *
 */
public class XTQSLBProtocol extends AProtocol
{

	public final static short XT_QSLB = 6;

	// 请求
	/** 手机号 */
	public String req_sUserMblPhone;
	/** 版本标示 */
	public String req_sCPID;

	// 响应
	/**
	 * 券商个数
	 */
	public short resp_wCount1;
	/**
	 * 用户手机号
	 */
	public String[] resp_sCPID_s;
	/**
	 * 券商名
	 */
	public String[] resp_sBrokerName_s;
	/**
	 * 券商IP
	 */
	public String[] resp_sBrokerIP_s;
	/**
	 * 券商端口
	 */
	public String[] resp_sBrokerPort_s;
	/**
	 * 营业部个数
	 */
	public short resp_wCount2;
	/**
	 * 用户手机号
	 */
	public String[] resp_sCPIDS_s;
	/**
	 * 营业部代码
	 */
	public String[] resp_sDeptId_s;
	/**
	 * 营业部名称
	 */
	public String[] resp_sDeptName_s;
	/**
	 * ing
	 */
	public String[] resp_sDeptShor_s;
	/**
	 * 资金账号前缀，用于补位
	 */
	public String[] resp_sDeptCode_s;

	public XTQSLBProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_XT, XT_QSLB, cmdVersion, false, true);
	}

}
