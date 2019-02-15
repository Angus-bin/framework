package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * @author qinyn
 * 修改日志：
 * 行情期货分笔协议
 * 1. 2014.2.19 期货分时主功能号由行情ProtocolConstant.MF_HQ改为期货ProtocolConstant.MF_HQ_FUTURES
 */
public class HQQHFBProtocol extends AProtocol
{

	public final static short HQ_QHFB = 19;
	
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
		 * 昨核算
		 */
		public int resp_nZhs;
		/**
		 * 买盘
		 */
		public int resp_nBp;
		/**
		 * 卖盘
		 */
		public int resp_nSp;
		/**
		 * 持仓量
		 */
		public int resp_nCcl;
		/**
		 * 仓差
		 */
		public int resp_nCc;
		/**
		 * 成交数量
		 */
		public int resp_nCjss;
	
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
		 * 仓差
		 */
		public int[] resp_nCc_s;
		/**
		 * 成交性质
		 */
		public byte[] resp_bCjxz_s;

		/**
		 *
		 * @param flag
		 * @param cmdVersion
		 */
		public HQQHFBProtocol(String flag, int cmdVersion)
		{
			super(flag, ProtocolConstant.MF_HQ/*MF_HQ_FUTURES*/, HQ_QHFB, cmdVersion, true, false);
		}
}
