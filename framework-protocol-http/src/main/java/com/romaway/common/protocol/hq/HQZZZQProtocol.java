package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

public class HQZZZQProtocol  extends AProtocol {

	public final static short HQ_ZZZQ = 21;
	// 请求数据
	/**
	 * 交易所代码
	 */
	public short req_wMarketID;
	/**
	 * 商品类型
	 */
	public short req_wType;
	/**
	 * 排序方式
	 */
	public byte req_bSort;
	/**
	 * 正向/逆向 0-从小大大，1-从大到小
	 */
	public byte req_bDirect;
	/**
	 * 开始序号
	 */
	public short req_wFrom;
	/**
	 * 请求个数
	 */
	public short req_wCount;


	// 返回数据
	/**
	 * 总记录条数
	 */
	public short resp_wTotolCount=0;
	/**
	 * 排序记录个数
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
	 * 债券名称
	 */
	public String[] resp_pszName_s;
	
	/**
	 * 日期
	 */
	public String[] resp_sDate;
	
	/**
	 * 沪市代码
	 */
	public String[] resp_sHSDM;
	
	/**
	 * 深市代码
	 */
	public String[] resp_sSSDM;
	
	/**
	 * 银行间代码
	 */
	public String[] resp_sYHJDM;
	/**
	 * 估值
	 */
	public String[] resp_nGZ;
	/**
	 * 计算收益率
	 */
	public String[] resp_nJSSYL;
	/**
	 * 修正久期
	 */
	public String[] resp_nXZJQ;
	/**
	 * 凸性
	 */
	public String[] resp_nTX;
	/**
	 * 净价
	 */
	public String[] resp_nJJ;
	/**
	 * 应计利息
	 */
	public String[] resp_nYJLX;

	/**
	 * 保留字段
	 */
	public String[] resp_sReserved;
	
	public HQZZZQProtocol(String flag, 
			int cmdVersion) {
		super(flag, ProtocolConstant.MF_HQ, HQ_ZZZQ, cmdVersion, true, false);
		// TODO Auto-generated constructor stub
	}

}
