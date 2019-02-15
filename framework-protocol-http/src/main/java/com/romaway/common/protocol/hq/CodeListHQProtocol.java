package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
/**
 * 	键盘精灵
 * @author xueyan
 *
 */
public class CodeListHQProtocol extends AProtocol
{
	public static final short HQ_CodeList = 7;
	// 请求
	/*
	 *	版本更新日期，0代表获取所有数据，其它日期格式采用YYYYMMDD描述
	 */
	public int req_nDate;
	/**
	 *  交易所代码
	 */
	public short req_wMarketID;

	// 响应
	/**
	 * 版本更新日期，0代表获取所有数据，其它日期格式采用YYYYMMDD描述
	 */
	public int resp_nDate;
	/**
	 *增加的数据数量
	 */
	public short resp_nTotalCount;
	/**
	 * 增加的数据数量
	 */
	public short resp_nAddCount;
	/**
	 * 删除的数据数量
	 */
	public short resp_nDelCount;
	/**
	 * 增加的键盘精灵代码数量
	 */
	public short resp_nAddCodeNum;
	/**
	 *交易所代码	
	 */
	public short[] resp_wMarketID_s;
	/**
	 *股票代码类型(0表示代码，其它表示各种类型）
	 */
	public short[] resp_wCodeType_s;
	/**
	 * 股票代码数值(实际运算需要转换成整型变量)
	 */
	public int[] resp_iCodeValue_s;
	/**
	 * 代码
	 */
	public String[] resp_pszCode_s;
	
	/**
	 *拼音代码
	 */
	public String[] resp_pszPYCode_s;
	/**
	 *名称
	 */
	public String[] resp_pszName_s;
	
	/**
	 * 增加的键盘精灵拼音查询代码数量
	 */
	public short resp_nAddPinyinNum;
	
	/**
	 * 股票代码数值(实际运算需要转换成整型变量)
	 */
	//public int[] resp_iCodeValue_s;

	/**
	 *拼音代码数值(实际运算需要转换成整型变量)
	 */
	public int[] resp_ipinyinCode_s;
	
	/**
	 * @param flag
	 * @param cmdVersion
	 */
	public CodeListHQProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_CodeList, cmdVersion, true, false);
		// TODO Auto-generated constructor stub
	}

}
