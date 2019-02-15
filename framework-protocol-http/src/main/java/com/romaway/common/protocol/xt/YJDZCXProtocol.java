package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 预警定制查询
 * 
 * @author qinyn
 * 
 */
public class YJDZCXProtocol extends AProtocol
{

	public static final short XT_YJDZCX = 36;

	/** 券商ID **/
	public String req_sCPID;
	/** 用户标识类型 **/
	public String req_sIdentifierType;
	/** 用户标识符 **/
	public String req_sIdentifier;
	/** 订单号 **/
	public int req_nOrderId;
	/** 预警类型 **/
	public int req_nServiceId;
	/** 参数1 **/
	public String req_sParam1;
	/** 参数2 **/
	public String req_sParam2;
	/** 参数3 **/
	public String req_sParam3;
	/** 参数4 **/
	public String req_sParam4;
	/** 参数5 **/
	public String req_sParam5;

	/** 定制数量 **/
	public short resp_wCount;
	/** 订单号 **/
	public int[] resp_nOrderId_s;
	/** 预警类型 **/
	public int[] resp_nServiceId_s;
	/** 参数1 **/
	public String[] resp_sParam1_s;
	/** 参数2 **/
	public String[] resp_sParam2_s;
	/** 参数3 **/
	public String[] resp_sParam3_s;
	/** 参数4 **/
	public String[] resp_sParam4_s;
	/** 参数5 **/
	public String[] resp_sParam5_s;
	/** 通道标识符 **/
	public short[] resp_wChannel_s;
	/** 股票名称 **/
	public String[] resp_wsStockName_s;

	// dwVersion>=3,增加交易所代码
	/** 交易所代码 **/
	public short[] resp_wMarketID_s;

	public YJDZCXProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_XT, XT_YJDZCX, cmdVersion, true, false);
	}

}
