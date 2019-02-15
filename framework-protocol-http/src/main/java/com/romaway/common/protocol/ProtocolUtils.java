/**
 * 
 */
package com.romaway.common.protocol;


import android.support.annotation.Keep;

/**
 * @author duminghui
 * 
 */
@Keep
public class ProtocolUtils
{
	/**
	 * 个股
	 */
	public static final int STOCK = 0;

	/**
	 * 指数
	 */
	public static final int INDEX = 1;

	/**
	 * 沪港通
	 */
	public static final int HGT_STOCK = 2;

	/**
	 * 港股指数
	 */
	public static final int HK_INDEX = 3;

	/**
	 * 期货
	 */
	public static final int FUTURES = 4;

	/** 上证债券 **/
	public static final int BOND_SHANGZHEN = 5;
	/** 深圳债券 **/
	public static final int BOND_SHENZHEN = 6;
	/**
	 * 三板
	 */
	public static final int SANBAN = 7;
	/**
	 * 个股期权
	 */
	public static final int OPTION = 8;
	/**
	 * 基金
	 */
	public static final int ST_FUND = 9;
	/**
	 * 风险警示
	 */
	public static final int ST_FXJS = 10;
	/**
	 * 退市整理
	 */
	public static final int ST_TSZL = 11;
	/** 沪深债券 **/
	public static final int BOND_HUSHEN = 12;
	/**
	 * 港股
	 */
	public static final int HK_STOCK = 13;
	/**
	 * B转H股
	 */
	public static final int BH_STOCK = 14;
    /**
     * 深港通
     */
    public static final int SGT_STOCK = 15;

	/**
	 * 根据市场代码、商品类型判断得出何种类型（0 个股、1指數、2港股、3港股指數、4期貨）
	 * 
	 * @param marketId
	 * @param type
	 * @return （0 个股、1指數、2港股、3港股指數、4期貨）
	 */
	public static final int getTypeAsMarketIdWType(int marketId, int type)
	{
		int stockType = STOCK;
		switch (marketId)
		{
			//20141113 cjp 增加对沪港通股票的判断
		    case ProtocolConstant.SE_HGT:
			    stockType = HGT_STOCK;
			    break;
			case ProtocolConstant.SE_GG:
				if (type == ProtocolConstant.STOCKTYPES_ST_INDEX
						|| type == ProtocolConstant.STOCKTYPES_ST_HSINDEX) {
					stockType = HK_INDEX;// 港股指数
				} else {
					stockType = HK_STOCK;// 港股
				}
				break;
			case ProtocolConstant.SE_SGT:
                    stockType = SGT_STOCK;// 深港通
                break;
			case ProtocolConstant.SE_SH:
				if (type == ProtocolConstant.STOCKTYPES_ST_BOND)
				{// 上证债券
					stockType = BOND_SHANGZHEN;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_FUND) {
					stockType = ST_FUND;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_FXJS) {
					stockType = ST_FXJS;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_TSZL) {
					stockType = ST_TSZL;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_INDEX)
				{
					stockType = INDEX;// 指数
				} else
				{
					stockType = STOCK;// 个股
				}
				break;
			case ProtocolConstant.SE_SZ:
				if (type == ProtocolConstant.STOCKTYPES_ST_BOND){
					stockType = BOND_SHENZHEN;// 深圳债券
				} else if (type == ProtocolConstant.STOCKTYPES_ST_FUND) {
					stockType = ST_FUND;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_FXJS) {
					stockType = ST_FXJS;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_TSZL) {
					stockType = ST_TSZL;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_ETF) {
					stockType = ST_FUND;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_LOF) {
					stockType = ST_FUND;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_INDEX) {
					stockType = INDEX;// 指数
				} else {
					stockType = STOCK;// 个股
				}
				break;
			case ProtocolConstant.SE_SS:
				if (type == ProtocolConstant.STOCKTYPES_ST_INDEX){
					stockType = INDEX;// 指数
				} else if (type == ProtocolConstant.STOCKTYPES_ST_BOND) {
					stockType = BOND_HUSHEN;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_TSZL) {
					stockType = ST_TSZL;
				} else if (type == ProtocolConstant.STOCKTYPES_ST_FUND) {
					stockType = ST_FUND;
				} else {
					stockType = STOCK;// 个股
				}
				break;
			case ProtocolConstant.SE_DL_QH:
			case ProtocolConstant.SE_ZZ_QH:
			case ProtocolConstant.SE_ZQ_QH:
			case ProtocolConstant.SE_SH_QH:
			case ProtocolConstant.SE_ZG_QH:
				stockType = FUTURES;// 期货
				break;
			case ProtocolConstant.SE_OPTION:
				stockType = OPTION;//个股期权
				break;
			case ProtocolConstant.SE_SB:
				stockType = SANBAN;//三板
				break;
			case ProtocolConstant.SE_BH:
				stockType = BH_STOCK;//B转H股
				break;
			default:
				stockType = STOCK;// 默認個股
				break;
		}
		return stockType;
	}

	/**
	 * 根据市场代码判断为何种类型（A股，港股或是其它）
	 * 
	 * @param marketId
	 * @return stockType
	 */
	public static final int getStockTypeAsMarketId(int marketId)
	{
		int stockType = ProtocolConstant.STOCKTYPES_ST_UNKNOWN;
		switch (marketId)
		{
			case ProtocolConstant.SE_SH:
			case ProtocolConstant.SE_SZ:
			case ProtocolConstant.SE_SS:
				stockType = ProtocolConstant.STOCKTYPES_ST_A;// A股
				break;
			case ProtocolConstant.SE_GG:
			case ProtocolConstant.SE_SGT:
			case ProtocolConstant.SE_HGT: //20141113 港股通
				stockType = ProtocolConstant.STOCKTYPES_ST_MAINBORAD;// 港股
				break;
			default:
				stockType = ProtocolConstant.STOCKTYPES_ST_UNKNOWN;
				break;
		}
		return stockType;
	}

	/**
	 * 根据商品类型来判断当前是否为指数
	 * 
	 * @param wType
	 * @return
	 */
	public static final boolean isStockIndex(short wType)
	{
		return (ProtocolConstant.STOCKTYPES_ST_INDEX == (wType & ProtocolConstant.STOCKTYPES_ST_INDEX))
		        || (ProtocolConstant.STOCKTYPES_ST_HSINDEX == (wType & ProtocolConstant.STOCKTYPES_ST_HSINDEX));

	}
}
