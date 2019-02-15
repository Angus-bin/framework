package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;


/**
 * @author qinyn

 *   行情排序协议
 */
public class HQPXProtocol extends AProtocol
{

	public final static short HQ_PX = 2;

	// 请求数据
	/**
	 * Protobuf请求位图值
	 */
	public long req_fieldsBitMap = -1;
	/**
	 * 交易所代码
	 */
	public short req_wMarketID;

	/**
	 * 交易所代码
	 */
	public short[] req_wMarketID_array;
	/**
	 * 商品类型
	 */
	public short req_wType;
	/**
	 * 商品类型
	 */
	public short[] req_wType_array;
	/**
	 * 商品类型股转分类
	 */
	public short[] resp_wType_fix;
	/**
	 * 商品类型股转分类
	 */
	public short[][] resp_wType_fix_array;
	/**
	 * 商品类型股转分类
	 */
	public short[][] resp_wType_fix_new;
	/**
	 * 商品类型股转分类
	 */
	public short[][][] resp_wType_fix_new_array;
	/**
	 * 请求的相应内容的第几项，比如涨幅榜的代码为3
	 */
	public int[] req_autoRefreshArray;
	/**
	 * 排序方式,针对市场首页
	 */
	public int[] req_bSort_new;
	/**
	 * 排序方式,针对市场首页
	 */
	public int[][] req_bSort_new_array;
	/**
	 * 排序方式
	 */
	public int req_bSort;
	/**
	 * 排序方式
	 */
	public int[] req_bSort_array;
	/**
	 * 正向/逆向,针对市场首页
	 */
	public int[] req_bDirect_new;
	/**
	 * 正向/逆向
	 */
	public int req_bDirect;
	/**
	 * 正向/逆向
	 */
	public int[] req_bDirect_array;
	/**
	 * 开始序号
	 */
	public short req_wFrom;
	/**
	 * 开始序号
	 */
	public short[] req_wFrom_array;
	/**
	 * 返回个数
	 */
	public short req_wCount;
	/**
	 * 返回个数
	 */
	public short[] req_wCount_array;
	/**
	 * 板块代码
	 */
	public String req_pszBKCode;

	// 返回数据
	/**
	 * 开始序号
	 */
	public int resp_wFrom;
	/**
	 * 开始序号
	 */
	public int[] resp_wFrom_array;
	/**
	 * 排序方式
	 */
	public int resp_bSort;
	/**
	 * 排序方式
	 */
	public int[] resp_bSort_array;
	/**
	 * 正向/逆向
	 */
	public byte resp_bDirect;
	/**
	 * 正向/逆向
	 */
	public byte[] resp_bDirect_array;
	/**
	 * 排序记录个数
	 */
	public int resp_wCount;
	/**
	 * 排序记录个数
	 */
	public int[] resp_wCount_array;

	/**
	 * 交易所代码
	 */
	public int[] resp_wMarketID_s;
	/**
	 * 交易所代码
	 */
	public int[][] resp_wMarketID_s_array;
	/**
	 * 商品类型
	 */
	public short[] resp_wType_s;
	/**
	 * 商品类型
	 */
	public short[][] resp_wType_s_array;
	/**
	 * 代码
	 */
	public String[] resp_pszCode_s;
	/**
	 * 代码
	 */
	public String[][] resp_pszCode_s_array;
	/**
	 * 证券名称
	 */
	public String[] resp_pszName_s;
	/**
	 * 证券名称
	 */
	public String[][] resp_pszName_s_array;
	/**
	 * 特殊股票标示
	 * HK 港股HGT 港股通R 融资融券
	 */
	public String[] resp_pszMark_s;
	/**
	 * 特殊股票标示
	 * HK 港股HGT 港股通R 融资融券
	 */
	public String[][] resp_pszMark_s_array;
	/**
	 * 昨收
	 */
	public int[] resp_nZrsp_s;
	/**
	 * 昨收
	 */
	public int[][] resp_nZrsp_s_array;
	/**
	 * 昨核算
	 */
	public int[] resp_nZhsj_s;
	/**
	 * 昨核算
	 */
	public int[][] resp_nZhsj_s_array;
	/**
	 * 今日开盘
	 */
	public int[] resp_nJrkp_s;
	/**
	 * 今日开盘
	 */
	public int[][] resp_nJrkp_s_array;
	/**
	 * 最高成交
	 */
	public int[] resp_nZgcj_s;
	/**
	 * 最高成交
	 */
	public int[][] resp_nZgcj_s_array;
	/**
	 * 最低成交
	 */
	public int[] resp_nZdcj_s;
	/**
	 * 最低成交
	 */
	public int[][] resp_nZdcj_s_array;
	/**
	 * 最近成交
	 */
	public int [] resp_nZjcj_s;
	/**
	 * 最近成交
	 */
	public int [][] resp_nZjcj_s_array;

	/**
	 * 成交数量
	 */
	public int[] resp_nCjss_s;
	/**
	 * 成交数量
	 */
	public int[][] resp_nCjss_s_array;
	/**
	 * 成交金额
	 */
	public int[] resp_nCjje_s;
	/**
	 * 成交金额
	 */
	public int[][] resp_nCjje_s_array;
	/**
	 * 持仓量
	 */
	public int[] resp_nCcl_s;
	/**
	 * 持仓量
	 */
	public int[][] resp_nCcl_s_array;
	/**
	 * 核算价
	 */
	public int[] resp_nHsj_s;
	/**
	 * 核算价
	 */
	public int[][] resp_nHsj_s_array;
	/**
	 * 买入(买一)价格
	 */
	public int[] resp_nBjg1_s;
	/**
	 * 买入(买一)价格
	 */
	public int[][] resp_nBjg1_s_array;
	/**
	 * 买入(买一)量
	 */
	public int[] resp_nBsl1_s;
	/**
	 * 买入(买一)量
	 */
	public int[][] resp_nBsl1_s_array;
	/**
	 * 卖出(卖一)价格
	 */
	public int[] resp_nSjg1_s;
	/**
	 * 卖出(卖一)价格
	 */
	public int[][] resp_nSjg1_s_array;
	/**
	 * 卖出(卖一)量
	 */
	public int[] resp_nSsl1_s;
	/**
	 * 卖出(卖一)量
	 */
	public int[][] resp_nSsl1_s_array;
	/**
	 * 涨跌值
	 */
	public int[] resp_nZd_s;
	/**
	 * 涨跌值
	 */
	public int[][] resp_nZd_s_array;
	/**
	 * 涨跌幅
	 */
	public int[] resp_nZdf_s;
	/**
	 * 涨跌幅
	 */
	public int[][] resp_nZdf_s_array;
	/**
	 * 震幅
	 */
	public int[] resp_nZf_s;
	/**
	 * 震幅
	 */
	public int[][] resp_nZf_s_array;
	/**
	 * 增量
	 */
	public int[] resp_nZl_s;
	/**
	 * 增量
	 */
	public int[][] resp_nZl_s_array;
	/**
	 * 委比
	 */
	public int[] resp_nWb_s;
	/**
	 * 委比
	 */
	public int[][] resp_nWb_s_array;
	/**
	 * 量比
	 */
	public int[] resp_nLb_s;
	/**
	 * 量比
	 */
	public int[][] resp_nLb_s_array;
	/**
	 * 5分钟涨跌幅
	 */
	public int[] resp_n5Min_s;
	/**
	 * 5分钟涨跌幅
	 */
	public int[][] resp_n5Min_s_array;
	/**
	 * 停牌标示
	 */
	public byte[] resp_bSuspended_s;
	/**
	 * 停牌标示
	 */
	public byte[][] resp_bSuspended_s_array;
	/**
	 * 换手率
	 */
	public int[] resp_nHsl_s;
	/**
	 * 换手率
	 */
	public int[][] resp_nHsl_s_array;
	/**
	 * 市盈率
	 */
	public int[] resp_nSyl_s;
	/**
	 * 市盈率
	 */
	public int[][] resp_nSyl_s_array;
	/**
	 * 保留
	 */
	public int[] resp_nReserved_s;
	/**
	 * 保留
	 */
	public int[][] resp_nReserved_s_array;
	/**
	 * 买盘
	 */
	public int[] resp_nBP_s;
	/**
	 * 买盘
	 */
	public int[][] resp_nBP_s_array;
	/**
	 * 卖盘
	 */
	public int[] resp_nSP_s;
	/**
	 * 卖盘
	 */
	public int[][] resp_nSP_s_array;
	/**
	 * 是否有关联股票
	 */
	public String[] resp_sLinkFlag_s;
	/**
	 * 是否有关联股票
	 */
	public String[][] resp_sLinkFlag_s_array;
	/**
	 * 样本数量
	 */
	public int[] resp_dwsampleNum_s;
	/**
	 * 样本数量
	 */
	public int[][] resp_dwsampleNum_s_array;
	/**
	 * 样本均价
	 */
	public int[] resp_nsampleAvgPrice_s;
	/**
	 * 样本均价
	 */
	public int[][] resp_nsampleAvgPrice_s_array;
	/**
	 * 平均股本
	 */
	public int[] resp_navgStock_s;
	/**
	 * 平均股本
	 */
	public int[][] resp_navgStock_s_array;
	/**
	 * 总市值
	 */
	public int[] resp_nmarketValue_s;
	/**
	 * 总市值
	 */
	public int[][] resp_nmarketValue_s_array;
	/**
	 * 占比%
	 */
	public int[] resp_nzb_s;
	/**
	 * 占比%
	 */
	public int[][] resp_nzb_s_array;
	/**
	 * 指数级别标识
	 */
	public String[] resp_slevelFlag_s;
	/**
	 * 指数级别标识
	 */
	public String[][] resp_slevelFlag_s_array;
	/**
	 * 所属板块代码
	 */
	public String[] resp_pszBKCode_s;
	/**
	 * 所属板块代码
	 */
	public String[][] resp_pszBKCode_s_array;
	/**
	 * 所属板块名称
	 */
	public String[] resp_pszBKName_s;
	/**
	 * 所属板块名称
	 */
	public String[][] resp_pszBKName_s_array;
	/**
	 * 所属板块涨跌幅
	 */
	public int[] resp_nBKzdf_s;
	/**
	 * 所属板块涨跌幅
	 */
	public int[][] resp_nBKzdf_s_array;
	/**
	 * 记录总数
	 */
	public int resp_wTotalCount = 0;
	/**
	 * 记录总数
	 */
	public int[] resp_wTotalCount_array;
	/**
	 * 请求返回响应条数
	 */
	public int resp_requestCount_new;
	/**
	 * 请求返回响应条数
	 */
	public int[] resp_requestCount_new_array;
	/**
	 * 开始序号
	 */
	public int[] resp_wFrom_new;
	/**
	 * 开始序号Array
	 */
	public int[][] resp_wFrom_new_array;
	/**
	 * 排序方式
	 */
	public int[] resp_bSort_new;
	/**
	 * 排序方式Array
	 */
	public int[][] resp_bSort_new_arry;
	/**
	 * 正向/逆向
	 */
	public byte[] resp_bDirect_new;
	/**
	 * 正向/逆向 Array
	 */
	public byte[][] resp_bDirect_new_array;
	/**
	 * 排序记录个数
	 */
	public int[] resp_wCount_new;
	/**
	 * 排序记录个数Array
	 */
	public int[][] getResp_wTotalCount_new_array;
	/**
	 * 交易所代码
	 */
	public int[][] resp_wMarketID_s_new;
	/**
	 * 交易所代码Array
	 */
	public int[][][] resp_wMarketID_s_new_array;
	/**
	 * 商品类型
	 */
	public short[][] resp_wType_s_new;
	/**
	 * 商品类型
	 */
	public short[][][] resp_wType_s_new_array;
	/**
	 * 代码
	 */
	public String[][] resp_pszCode_s_new;
	/**
	 * 代码
	 */
	public String[][][] resp_pszCode_s_new_array;
	/**
	 * 证券名称
	 */
	public String[][] resp_pszName_s_new;
	/**
	 * 证券名称
	 */
	public String[][][] resp_pszName_s_new_array;
	/**
	 * 特殊股票标示
	 * HK 港股HGT 港股通R 融资融券
	 */
	public String[][] resp_pszMark_s_new;
	/**
	 * 特殊股票标示
	 * HK 港股HGT 港股通R 融资融券
	 */
	public String[][][] resp_pszMark_s_new_array;
	/**
	 * 昨收
	 */
	public int[][] resp_nZrsp_s_new;
	/**
	 * 昨收
	 */
	public int[][][] resp_nZrsp_s_new_array;
	/**
	 * 昨核算
	 */
	public int[][] resp_nZhsj_s_new;
	/**
	 * 昨核算
	 */
	public int[][][] resp_nZhsj_s_new_array;
	/**
	 * 今日开盘
	 */
	public int[][] resp_nJrkp_s_new;
	/**
	 * 今日开盘
	 */
	public int[][][] resp_nJrkp_s_new_array;
	/**
	 * 最高成交
	 */
	public int[][] resp_nZgcj_s_new;
	/**
	 * 最高成交
	 */
	public int[][][] resp_nZgcj_s_new_array;
	/**
	 * 最低成交
	 */
	public int[][] resp_nZdcj_s_new;
	/**
	 * 最低成交
	 */
	public int[][][] resp_nZdcj_s_new_array;
	/**
	 * 最近成交
	 */
	public int [][] resp_nZjcj_s_new;
	/**
	 * 最近成交
	 */
	public int [][][] resp_nZjcj_s_new_array;
	
	/**
	 * 成交数量
	 */
	public int[][] resp_nCjss_s_new;
	/**
	 * 成交数量
	 */
	public int[][][] resp_nCjss_s_new_array;
	/**
	 * 成交金额
	 */
	public int[][] resp_nCjje_s_new;
	/**
	 * 成交金额
	 */
	public int[][][] resp_nCjje_s_new_array;
	/**
	 * 持仓量
	 */
	public int[][] resp_nCcl_s_new;
	/**
	 * 持仓量
	 */
	public int[][][] resp_nCcl_s_new_array;
	/**
	 * 核算价
	 */
	public int[][] resp_nHsj_s_new;
	/**
	 * 核算价
	 */
	public int[][][] resp_nHsj_s_new_array;
	/**
	 * 买入(买一)价格
	 */
	public int[][] resp_nBjg1_s_new;
	/**
	 * 买入(买一)价格
	 */
	public int[][][] resp_nBjg1_s_new_array;
	/**
	 * 买入(买一)量
	 */
	public int[][] resp_nBsl1_s_new;
	/**
	 * 买入(买一)量
	 */
	public int[][][] resp_nBsl1_s_new_array;
	/**
	 * 卖出(卖一)价格
	 */
	public int[][] resp_nSjg1_s_new;
	/**
	 * 卖出(卖一)价格
	 */
	public int[][][] resp_nSjg1_s_new_array;
	/**
	 * 卖出(卖一)量
	 */
	public int[][] resp_nSsl1_s_new;
	/**
	 * 卖出(卖一)量
	 */
	public int[][][] resp_nSsl1_s_new_array;
	/**
	 * 涨跌值
	 */
	public int[][] resp_nZd_s_new;
	/**
	 * 涨跌值
	 */
	public int[][][] resp_nZd_s_new_array;
	/**
	 * 涨跌幅
	 */
	public int[][] resp_nZdf_s_new;
	/**
	 * 涨跌幅
	 */
	public int[][][] resp_nZdf_s_new_array;
	/**
	 * 震幅
	 */
	public int[][] resp_nZf_s_new;
	/**
	 * 震幅
	 */
	public int[][][] resp_nZf_s_new_array;
	/**
	 * 增量
	 */
	public int[][] resp_nZl_s_new;
	/**
	 * 增量
	 */
	public int[][][] resp_nZl_s_new_array;
	/**
	 * 委比
	 */
	public int[][] resp_nWb_s_new;
	/**
	 * 委比
	 */
	public int[][][] resp_nWb_s_new_array;
	/**
	 * 量比
	 */
	public int[][] resp_nLb_s_new;
	/**
	 * 量比
	 */
	public int[][][] resp_nLb_s_new_array;
	/**
	 * 5分钟涨跌幅
	 */
	public int[][] resp_n5Min_s_new;
	/**
	 * 5分钟涨跌幅
	 */
	public int[][][] resp_n5Min_s_new_array;
	/**
	 * 停牌标示
	 */
	public byte[][] resp_bSuspended_s_new;
	/**
	 * 停牌标示
	 */
	public byte[][][] resp_bSuspended_s_new_array;
	/**
	 * 换手率
	 */
	public int[][] resp_nHsl_s_new;
	/**
	 * 换手率
	 */
	public int[][][] resp_nHsl_s_new_array;
	/**
	 * 市盈率
	 */
	public int[][] resp_nSyl_s_new;
	/**
	 * 市盈率
	 */
	public int[][][] resp_nSyl_s_new_array;
	/**
	 * 保留
	 */
	public int[][] resp_nReserved_s_new;
	/**
	 * 保留
	 */
	public int[][][] resp_nReserved_s_new_array;
	/**
	 * 买盘
	 */
	public int[][] resp_nBP_s_new;
	/**
	 * 买盘
	 */
	public int[][][] resp_nBP_s_new_array;
	/**
	 * 卖盘
	 */
	public int[][] resp_nSP_s_new;
	/**
	 * 卖盘
	 */
	public int[][][] resp_nSP_s_new_array;
	/**
	 * 是否有关联股票
	 */
	public String[][] resp_sLinkFlag_s_new;
	/**
	 * 是否有关联股票
	 */
	public String[][][] resp_sLinkFlag_s_new_array;
	/**
	 * 样本数量
	 */
	public int[][] resp_dwsampleNum_s_new;
	/**
	 * 样本数量
	 */
	public int[][][] resp_dwsampleNum_s_new_array;
	/**
	 * 样本均价
	 */
	public int[][] resp_nsampleAvgPrice_s_new;
	/**
	 * 样本均价
	 */
	public int[][][] resp_nsampleAvgPrice_s_new_array;
	/**
	 * 平均股本
	 */
	public int[][] resp_navgStock_s_new;
	/**
	 * 平均股本
	 */
	public int[][][] resp_navgStock_s_new_array;
	/**
	 * 总市值
	 */
	public int[][] resp_nmarketValue_s_new;
	/**
	 * 总市值
	 */
	public int[][][] resp_nmarketValue_s_new_array;
	/**
	 * 占比%
	 */
	public int[][] resp_nzb_s_new;
	/**
	 * 占比%
	 */
	public int[][][] resp_nzb_s_new_array;
	/**
	 * 指数级别标识
	 */
	public String[][] resp_slevelFlag_s_new;
	/**
	 * 指数级别标识
	 */
	public String[][][] resp_slevelFlag_s_new_array;
	/**
	 * 所属板块代码
	 */
	public String[][] resp_pszBKCode_s_new;
	/**
	 * 所属板块代码
	 */
	public String[][][] resp_pszBKCode_s_new_array;
	/**
	 * 所属板块名称
	 */
	public String[][] resp_pszBKName_s_new;
	/**
	 * 所属板块名称
	 */
	public String[][][] resp_pszBKName_s_new_array;
	/**
	 * 所属板块涨跌幅
	 */
	public int[][] resp_nBKzdf_s_new;
	/**
	 * 所属板块涨跌幅
	 */
	public int[][][] resp_nBKzdf_s_new_array;
	/**
	 * 记录总数
	 */
	public int[] resp_wTotalCount_new;
	/**
	 * 记录总数Array
	 */
	public int[][] resp_wTotalCount_new_array;
	/**
	 *
	 * 是否为数组请求
	 */
	public boolean is_array_req = false;
//	/**
//	 * 全球分类
//	 */
//	public int[] resp_specialIndex ;
	/**
	 *
	 * @param flag
	 * @param cmdVersion
     */
	public HQPXProtocol(String flag, int cmdVersion)
	{
		super(flag, ProtocolConstant.MF_HQ, HQ_PX, cmdVersion, true, false);
		this.subFunUrl = "/api/quote/pb_stockRank";
		isBuffer=true;
	}
}
