package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * @author qinyn
 * 行情分笔协议
 */
public class HQFBProtocol extends AProtocol
{
	public static final short HQ_FB = 5;
	
	//请求数据
	/**
	 * 代码
	 */
	public String req_pszCode;
	/**
	 * 分笔数据请求方式
	 */
	public Byte req_nType;
	/**
	 * 最新的N笔数据
	 */
	public int req_nNum;
	/**
	 * 数据起始时间
	 */
	public int req_nTimeS;
	/**
	 * 数据结束时间
	 */
	public int req_nTimeE;
	/**
	 * 交易所代码
	 */
	public short req_wMarketID;
	
	//返回数据
	/**
	 * 交易所代码
	 */
	public short resp_wMarketID;
	/**
	 * 商品类型
	 */
	public short resp_wType;
	/**
	 * 分笔数据日期
	 */
	public int resp_nDate;
	/**
	 * 代码
	 */
	public String resp_pszCode;
	/**
	 * 名称
	 */
	public String resp_pszName;
	/**
	 * 昨收
	 */
	public int resp_nZrsp;
	/**
	 * 分笔数据个数
	 */
	public short resp_wCount;
	//具体分笔数据
	/**
	 * 时间
	 */
	public int[] resp_nTime_s;
	/**
	 * 成交类别
	 */
	public byte[] resp_bCjlb_s;
	/**
	 * 成交价格
	 */
	public int[] resp_nZjcj_s;
	/**
	 * 成交数量
	 */
	public int[] resp_nCjss_s;
	/**
	 * 成交金额
	 */
	public int[] resp_nCjje_s;
	
	/**
	 * 是否有关联股票
	 */
	public String resp_sLinkFlag;

	/**
	 *
	 * @param flag
	 * @param cmdVersion
     */
	public HQFBProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_FB, cmdVersion, true, false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
