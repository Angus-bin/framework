package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

public class XTTSXXCXProtocol extends AProtocol
{
	public final static short XT_TSXXCX = 38;

	// 请求
	/** 券商ID **/
	public String req_sCPID;
	/**
	 * 用户标识类型，KDS_ID（0） TYPE_PHONE（1） 电话号码； TYPE_ZZJH（2） 资金账号； TYPE_IMEI（3）
	 * IMEI号
	 **/
	public String req_sIdentifierType;
	/** 用户标识符，统一采用KDS_ID **/
	public String req_sIdentifier;
	/**
	 * 消息类型，信息分类号： 0：无分类（所有类型） 1：预警 2：资讯 3：公告
	 **/
	public int req_dwServiceId;
	/** 排序方式 **/
	public byte req_bSort;
	/** 开始序号 **/
	public short req_wFrom;
	/** 返回个数 **/
	public short req_wCount;
	// **********version>=2
	/**
	 * 数据请求方式
	 * 
	 * 0:请求所有数据
	 * 
	 * 1:按照wCount值返回最新的wCount条数据
	 * 
	 * 2：按照在wCount值范围内查询出的所有无分类消息，返回具体某个消息类型的消息记录数（返回的消息记录数将小于等于wCount）。
	 **/
	public short req_nType;

	// 响应
	/** 消息数量 **/
	public short resp_wNum;
	/** 消息类型 **/
	public int[] resp_dwService_id_s;
	/** 时间 **/
	public String[] resp_sTime_s;
	/** 来源 **/
	public String[] resp_wsSource_s;
	/** 消息 **/
	public String[] resp_wsMsg_s;
	/** 推送通道 **/
	public short[] resp_wChannel_s;
	/**
	 * 股票代码
	 **/
	public String[] resp_sStockCode_s;
	/**
	 * 股票名称
	 **/
	public String[] resp_wsStockName_s;

	// ************** version>=3
	/** 信息id **/
	public String[] resp_sMsgID_s;
	/**
	 * 信息阅读状态 0: 未读 1: 已读
	 **/
	public byte[] resp_bReadStatus_s;

	// ******************* version>=4
	/** 交易所代码 **/
	public short[] resp_wMarketID_s;

	/** 总消息数量 **/
	public short resp_wNum_all;

	public XTTSXXCXProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_XT, XT_TSXXCX, cmdVersion, true, false);
	}

}
