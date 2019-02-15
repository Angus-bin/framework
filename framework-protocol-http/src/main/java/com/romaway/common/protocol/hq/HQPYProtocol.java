package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 股票匹配协议
 * 
 * @author xueyan
 */
public class HQPYProtocol extends AProtocol
{

	public static final short HQ_PY = 6;

	/**
	 * 请求数据
	 */

	/**
	 * 0:all, 1:代码匹配, 2:拼音匹配
	 */
	public short req_wType;

	/**
	 * 查询串
	 */
	public String req_pszPattern;

	/**
	 * 交易所代码
	 */
	public short req_wMarketID;

	/**
	 * 应答数据
	 */

	/**
	 * 交易所代码
	 */
	public short resp_wMarketID;
	/**
	 * 商品类别
	 */
	public short resp_wType;

	/**
	 * 代码
	 */
	public String resp_pszCode;

	/**
	 * 拼音代码
	 */
	public String resp_pszPYCode;

	/**
	 * 名称
	 */
	public String resp_pszName;

	/**
	 * 匹配到的商品种类数
	 */
	public short resp_wCount;

	/**
	 * 描述信息
	 */
	public String[] resp_pData_s;

	/**
	 * 
	 * @param flag
	 * @param cmdVersion
	 */
	public HQPYProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_PY, cmdVersion, true, false);
		// TODO Auto-generated constructor stub
	}

}
