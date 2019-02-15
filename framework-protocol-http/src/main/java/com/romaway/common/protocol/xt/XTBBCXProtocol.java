package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 版本查询协议，用于手动升级
 * @author dumh
 *
 */
public class XTBBCXProtocol extends AProtocol
{

	public final static short XT_BBCX = 11;

	// 请求
	/** 客户端版本 */
	public String req_sKHDBB;
	/** 厂商品牌 */
	public String req_sCSPP;
	/** 终端类型 */
	public String req_sZDLX;
	/** 软件ID */
	public String req_sRJID;
	/** 软件类型 */
	public String req_sRJLX;

	// 响应
	/** 客户端版本 */
	public String resp_sKHDBB;
	/** 是否兼容 :1-兼容,0-不兼容 */
	public byte resp_bJR;
	/** 服务器上的最新版本 */
	public String resp_sZXKHDBB;
	/** 最新客户端的下载地址 */
	public String resp_sXZDZ;
	/** 更新说明 */
	public String resp_sGXSM;

	public XTBBCXProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_XT, XT_BBCX, cmdVersion, false, true);
	}

}
