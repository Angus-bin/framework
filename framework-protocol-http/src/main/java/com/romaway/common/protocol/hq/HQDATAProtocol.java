package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 获取支持的行情类型数据（如排序支持的排序类型、板块支持的板块分类、支持的市场分类)
 * @author xueyan 
 */
public class HQDATAProtocol extends AProtocol
{

	public static final short HQ_DATA = 13;

	/**
	 * 请求数据
	 */

	/**
	 * 交易所代码,区分港股、期货、沪深A股
	 */
	public short req_wMarketID;

	/**
	 * 请求的数据类型(行情设置数据类型 enum HQSetDataType)
	 */
	public short req_wType;

	/**
	 * 判断是否获取指定市场所有支持的排序类型、板块分类、市场分类等相关数据 0—获取指定类型数据， 1-获取所有支F
	 */
	public byte req_bAll;

	/**
	 * 应答数据
	 */

	/**
	 * 
	 */
	public short resp_wCount;
	/**
	 * 交易所代码
	 */
	public short[] resp_wMarketID_s;

	/**
	 * 商品类型
	 */
	public short[] resp_wType_s;

	/**
	 * 代码
	 */
	public String[] resp_pszCode_s;

	/**
	 * 名称
	 */
	public String[] resp_pszName_s;

	/**
	 * 
	 * @param flag
	 * @param cmdVersion
	 */
	public HQDATAProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_DATA, cmdVersion, true, false);
		// TODO Auto-generated constructor stub
	}

}
