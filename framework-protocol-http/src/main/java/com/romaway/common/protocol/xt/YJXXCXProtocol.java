package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 预警消息查询
 * 
 * @author qinyn
 * 
 */
public class YJXXCXProtocol extends AProtocol
{

	public static final short XT_YJCX = 37;
	/** 券商ID **/
	public String req_sCPID;
	/** 用户标识类型 **/
	public String req_sIdentifierType;
	/** 用户标识符 **/
	public String req_sIdentifier;
	/** 预警类型 **/
	public int req_nServiceId;

	/** 消息数量 **/
	public short resp_wCount;
	/** 预警类型 **/
	public int[] resp_nServiceId_s;
	/** 时间 **/
	public String[] resp_sTime_s;
	/** 来源 **/
	public String[] resp_wsSource_s;
	/** 预警消息 **/
	public String[] resp_wsMsg_s;

	public YJXXCXProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_XT, XT_YJCX, cmdVersion, true, false);
	}

}
