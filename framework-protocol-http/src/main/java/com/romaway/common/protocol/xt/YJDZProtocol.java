package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 预警定制
 * 
 * @author qinyn
 * 
 */
public class YJDZProtocol extends AProtocol
{

	public static final short XT_YJDZ = 35;
	/** 券商ID **/
	public String req_sCPID;
	/** 用户标识类型 **/
	public String req_sIdentifierType;
	/** 用户标识符 **/
	public String req_sIdentifier;
	/** 定制数量 **/
	public short req_wNum;
	/** 订单号 **/
	public int[] req_nOrderId_s;
	/** 定制类型 **/
	public short[] req_wOrderType_s;
	/** 预警类型 **/
	public int[] req_nServiceId_s;
	/** 参数1 **/
	public String[] req_sParam1_s;
	/** 参数2 **/
	public String[] req_sParam2_s;
	/** 参数3 **/
	public String[] req_sParam3_s;
	/** 参数4 **/
	public String[] req_sParam4_s;
	/** 参数5 **/
	public String[] req_sParam5_s;
	/** 通道标识符 **/
	public short[] req_wChannel_s;

	// version>=2
	/** * 交易所代码（为了兼容沪深A股多市场重叠代码） **/
	public short[] req_wMarketID_s;

	/** 结果数量 **/
	public short resp_wCount;
	/** 订单号 **/
	public int[] resp_nOrderId_s;
	/** 返回码 **/
	public short[] resp_sRetNo_s;
	/** 返回消息 **/
	public String[] resp_sRetInfo_s;

	public YJDZProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_XT, XT_YJDZ, cmdVersion, true, false);
	}

}
