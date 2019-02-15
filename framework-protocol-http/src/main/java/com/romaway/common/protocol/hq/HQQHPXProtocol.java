package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * @author qinyn
 * 
 * 期货排序协议
 * 修改日志：
 * 行情期货分笔协议
 * 1. 2014.2.19 期货分时主功能号由行情ProtocolConstant.MF_HQ改为期货ProtocolConstant.MF_HQ_FUTURES 
 */
public class HQQHPXProtocol extends AProtocol
{
	public final static short HQ_QHPX = 20;

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
	 * 正向/逆向
	 */
	public byte req_bDirect;
	/**
	 * 开始序号
	 */
	public short req_wFrom;
	/**
	 * 返回个数
	 */
	public short req_wCount;

	// 返回数据
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
	 * 代码
	 */
	public String[] resp_pszCode_s;
	/**
	 * 名称
	 */
	public String[] resp_pszName_s;
	/**
	 * 昨收
	 */
	public int[] resp_nZrsp_s;
	/**
	 * 昨核算
	 */
	public int[] resp_nZhsj_s;
	/**
	 * 今日开盘
	 */
	public int[] resp_nJrkp_s;
	/**
	 * 最高成交
	 */
	public int[] resp_nZgcj_s;
	/**
	 * 最低成交
	 */
	public int[] resp_nZdcj_s;
	/**
	 * 最近成交
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
	 * 均价
	 */
	public int[] resp_nJj_s;
	/**
	 * 持仓量
	 */
	public int[] resp_nCcl_s;
	/**
	 * 仓差
	 */
	public int[] resp_nCc_s;
	/**
	 * 现量
	 */
	public int[] resp_nXl_s;
	/**
	 * 核算价
	 */
	public int[] resp_nHsj_s;
	/**
	 * 买入(买一)价格
	 */
	public int[] resp_nBjg1_s;
	/**
	 * 卖出(卖一)价格
	 */
	public int[] resp_nSjg1_s;
	/**
	 * 涨跌值
	 */
	public int[] resp_nZd_s;
	/**
	 * 涨跌幅
	 */
	public int[] resp_nZdf_s;
	/**
	 * 震幅
	 */
	public int[] resp_nZf_s;
	/**
	 * 增量
	 */
	public int[] resp_nZl_s;

	/**
	 * 委比
	 */
	public int[] resp_nWb_s;
	/**
	 * 量比
	 */
	public int[] resp_nLb_s;
	/**
	 * 5分钟涨跌幅
	 */
	public int[] resp_n5Min_s;
	/**
	 * 停牌标示
	 */
	public byte[] resp_bSuspended_s;
	/**
	 * 换手率
	 */
	public int[] resp_nHsl_s;
	/**
	 * 市盈率
	 */
	public int[] resp_nSyl_s;
	/**
	 * 买盘
	 */
	public int[] resp_nBP_s;
	/**
	 * 卖盘
	 */
	public int[] resp_nSP_s;

	/**
	 *
	 * @param flag
	 * @param cmdVersion
     */
	public HQQHPXProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ/*MF_HQ_FUTURES*/, HQ_QHPX, cmdVersion, true, false);
	}

}
