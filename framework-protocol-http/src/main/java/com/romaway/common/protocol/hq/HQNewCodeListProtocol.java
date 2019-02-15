package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 新键盘精灵
 * 
 * @author xueyan
 * 
 */
public class HQNewCodeListProtocol extends AProtocol
{
	public static final short HQ_NewCodeList = 16;
	// 请求
	/*
	 * 版本更新日期
	 */
	public int req_nDate;
	/**
	 * 交易所代码
	 */
	public short req_wMarketID;

	// 响应
	/**
	 * uuid
	 */
	public String req_sUuid;
	// 响应
	/**
	 +	 * uuid
	 +	 */
	public String resp_sUuid;
	/**
	 * 版本更新日期 0代表获取所有数据，其它日期格式采用YYYYMMDD描述
	 */
	public int resp_nDate;
	/**
	 * 增加的键盘精灵代码数量
	 */
	public int resp_nAddCodeNum;
	/**
	 * 交易所代码
	 */
	public int[] resp_wMarketID_s;
	/**
	 * 股票代码类型 0表示代码，其它表示各种类型
	 */
	public int[] resp_wCodeType_s;
	/**
	 * 增加的股票代码
	 */
	public String[] resp_pszAddCode_s;
	/**
	 * 拼音代码
	 */
	public String[] resp_pszPYCode_s;
	/**
	 * 股票标识
	 */
	public String[] resp_pszMark_s;
	/**
	 * 名称
	 */
	public String[] resp_pszName_s;
	/**
	 * 删除的键盘精灵代码数量
	 */
	public int resp_nDelCodeNum;
	/**
	 * 删除的股票代码
	 */
	public String[] resp_pszDelCode_s;
  
  	/**
	 * 删除的市场代码
	 */
	public int[] resp_del_wMarketID;

	/**
	 * @param flag
	 * @param cmdVersion
	 */
	public HQNewCodeListProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_NewCodeList, cmdVersion, true,
		        false);
		this.subFunUrl = "/api/quote/pb_codeList";
		isBuffer=true;
	}

}
