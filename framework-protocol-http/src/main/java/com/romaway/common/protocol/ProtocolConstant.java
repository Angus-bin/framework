/**
 * 
 */
package com.romaway.common.protocol;

import android.support.annotation.Keep;

/**
 * 协议中使用的常量
 * 
 * @author duminghui
 * 
 */
@Keep
public class ProtocolConstant
{
	/**
	 * 不排序
	 */
	public final static int ORDER_NONE = -1;
	/**
	 * 正序:从小到大
	 */
	public final static int ORDER_Z = 0;

	/**
	 * 逆序:从大到小
	 */
	public final static int ORDER_N = 1;
	/**
	 * 股票代码长度
	 */
	public final static int MAX_CODE_LENGTH = 9;
	/**
	 * 证券名称长度
	 */
	public final static int MAX_NAME_LENGTH = 26;

	// 4.2主功能类型常量
	/** 行情(+三板) */
	public final static short MF_HQ = 1;
	/** 交易 */
	public final static short MF_JY = 2;
	/** 系统 */
	public final static short MF_XT = 3;
	/** 信息 */
	public final static short MF_XX = 4;
	/** 登录 */
	public final static short MF_DL = 5;
	/** 基金 */
	public final static short MF_FUND = 6;
	/** 期货行情 */
	public final static short MF_HQ_FUTURES = 7;
	/** 港股行情 */
	public final static short MF_HQ_HK = 8;
	/** 互动交流 */
	public final static short MF_IACT = 9;
	/** 直付 */
	public final static short MF_ZF = 10;
	/** 监控 */
	public final static short MF_MONI = 1981;
	/** 站点测速 */
	public final static short MF_TEST = 101;
	/** 推送服务器(中间件) (长连接) */
	public final static short MF_PUSH_MID = 2012;
	/** 推送服务器(第三方)(短连接) */
	public final static short MF_PUSH_EPNS = 2013;
	/** 沪港通 */
	public final static short MF_HGT = 40;
	/** 股转 */
	public final static short ST_SB = 512;
	/** 两网 */
	public final static short ST_LWTS = 513;
	/** 协议 */
	public final static short ST_XYZR = 514;
	/** 做市 */
	public final static short ST_ZSZR = 515;
	/** 竞价 */
	public final static short ST_JJZR = 516;
	/** 个股期权 */
	public final static short ST_SHOP = 519;

	// 行情设置数据类型
	/**
	 * 标识所有
	 */
	public static final int HQ_SETDATA_TYPE_SD_ALL = 0;
	/**
	 * 可设置的排序类型
	 */
	public static final int HQ_SETDATA_TYPE_SD_PX = 1;
	/**
	 * 可设置支持的板块类型
	 */
	public static final int HQ_SETDATA_TYPE_BK = 2;
	/**
	 * 市场
	 */
	public static final int HQ_SETDATA_TYPE_SC = 3;
	/**
	 * K线周期类型
	 */
	public static final int HQ_SETDATA_TYPE_ZQ = 4;
	/**
	 * 商品类型
	 */
	public static final int HQ_SETDATA_TYPE_SP = 5;

	// 商品代码
	/**
	 * 未知
	 */
	public static final int STOCKTYPES_ST_UNKNOWN = 0;
	/**
	 * A股
	 */
	public static final int STOCKTYPES_ST_A = 1;
	/**
	 * B股
	 */
	public static final int STOCKTYPES_ST_B = 2;
	/**
	 * 基金
	 */
	public static final int STOCKTYPES_ST_FUND = 4;
	/**
	 * 权证
	 */
	public static final int STOCKTYPES_ST_WARRANT = 8;
	/**
	 * 指数
	 */
	public static final int STOCKTYPES_ST_INDEX = 16;
	/**
	 * 债券
	 */
	public static final int STOCKTYPES_ST_BOND = 32;
	/**
	 * 恒生指数
	 */
	public static final int STOCKTYPES_ST_HSINDEX = 64;
	/**
	 * 创业板
	 */
	public static final int STOCKTYPES_ST_CYB = 128;
	/**
	 * 中小版
	 */
	public static final int STOCKTYPES_ST_ZXB = 256;
	/**
	 * 主板(港股市场)
	 */
	public static final int STOCKTYPES_ST_MAINBORAD = 257;
	/**
	 * 全球指数
	 */
	public static final int STOCKTYPES_ST_QQINDEX = 258;
	/**
	 * 外汇
	 */
	public static final int STOCKTYPES_ST_WH = 259;
	/**
	 * 成分股
	 */
	public static final int STOCKTYPES_ST_CFG = 518;
	/**
	 * 个股期权
	 */
	public static final int STOCKTYPES_ST_OPTION = 519;
	/**
	 * 新股
	 */
	public static final int STOCKTYPES_ST_NEWSTOCK = 1024;
	/**
	 * 三板行情
	 */
	public static final int STOCKTYPES_ST_SB = 1025;
	/**
	 * AB股对照
	 */
	public static final int STOCKTYPES_ST_AB = 1026;
	/**
	 * AH股对照
	 */
	public static final int STOCKTYPES_ST_AH = 1027;
	/**
	 * LOF基金板块
	 */
	public static final int STOCKTYPES_ST_LOF = 1028;
	/**
	 * ETF基金板块
	 */
	public static final int STOCKTYPES_ST_ETF = 1029;
	/**
	 * 板块管理
	 */
	public static final int STOCKTYPES_ST_BK = 1030;
	/**
	 * B股转H商品Id
	 */
	public static final int ST_B2H = 4096;
	/**
	 * 退市整理
	 */
	public static final int STOCKTYPES_ST_TSZL = 2048;
	/**
	 * 风险提示
	 */
	public static final int STOCKTYPES_ST_FXJS = 8192;
	
	//全球指数区域划分
	/**
	 * specialIndex
	 * 四个重点指数;道琼工业;NASDAQ;SP;500;香港恒生
	 */
	public static final int STOCKTYPES_QQ_SPECIAL = 0;
	/**
	 * 亚洲指数
	 */
	public static final int STOCKTYPES_QQ_ASIA = 1;
	/**
	 * 欧洲指数
	 */
	public static final int STOCKTYPES_QQ_EUROPE = 2;
	/**
	 * 美洲指数
	 */
	public static final int STOCKTYPES_QQ_AMERICA = 3;
	/**
	 * 全球指数
	 */
	public static final int STOCKTYPES_QQ_WORLD = 4;

	// 资讯
	/**
	 * 扩展字段属性关键字-时间戳
	 */
	public static final String XX_HQLB_EXPAND_KEY_TIMESTAMP = "timestamp";
	/**
	 * 扩展字段属性关键字-来源
	 */
	public static final String XX_HQLB_EXPAND_KEY_SOURCE = "source";

	// 服务器
	/** 资讯服务器 */
	public static final int SERVER_FW_YUJING = 205;
	/** 认证服务器 */
	public static final int SERVER_FW_AUTH = 204;
	/** 资讯服务器 */
	public static final int SERVER_FW_NEWS = 203;
	/** 资讯服务器2.0 */
	public static final int SERVER_FW_NEWS2 = 205;
	/** 行情服务器 */
	public static final int SERVER_FW_QUOTES = 202;
	/** 交易服务器 */
	public static final int SERVER_FW_TRADING = 201;
	/** 投顾服务器 */
	public static final int SERVER_FW_TOUGU = 206;
	/**KDS推送 */
	public static final int SERVER_FW_PUSH = 207;

	/** 分时数据 */
	public final static short FS_TYPE = 0x000;
	public final static short KLINE_TYPE = 0x01;
	
	/** 1分钟数据 */
	public final static short KX_1MIN = 0x100;
	/** 5分钟数据 */
	public final static short KX_5MIN = 0x101;
	/** 15分钟数据 */
	public final static short KX_15MIN = 0x103;
	/** 30分钟数据 */
	public final static short KX_30MIN = 0x106;
	/** 60分钟数据 */
	public final static short KX_60MIN = 0x10C;
	/** 日K线数据 */
	public final static short KX_DAY = 0x201;
	/** 周K线数据 */
	public final static short KX_WEEK = 0x301;
	/** 月K线数据 */
	public final static short KX_MONTH = 0x401;
	/** 季K线数据 */
	public final static short KX_QUARTER = 0x403;
	/** 半年K线数据 */
	public final static short KX_HALFYEAR = 0x406;
	/** 年K线数据 */
	public final static short KX_YEAR = 0x40C;

	// 排序方式
	/** 不排序 */
	public final static byte PX_NONE = 0;
	/** 排序方式- 代码 */
	public final static byte PX_CODE = 1;
	/** 排序方式- 昨收 */
	public final static byte PX_ZRSP = 2;
	/** 排序方式- 最高 */
	public final static byte PX_ZGCJ = 3;
	/** 排序方式- 最低 */
	public final static byte PX_ZDCJ = 4;
	/** 排序方式- 最新 */
	public final static byte PX_ZJCJ = 5;
	/** 排序方式- 成交数量 */
	public final static byte PX_CJSL = 6;
	/** 排序方式- 成交金额 */
	public final static byte PX_CJJE = 7;
	/** 排序方式- 涨跌幅 */
	public final static byte PX_ZDF = 8;
	/** 排序方式- 震幅 */
	public final static byte PX_ZF = 9;
	/** 排序方式- 换手 */
	public final static byte PX_HS = 10;
	/** 排序方式- 市盈率 */
	public final static byte PX_SY = 11;
	/** 排序方式- 委比 */
	public final static byte PX_WB = 12;
	/** 排序方式- 量比 */
	public final static byte PX_LB = 13;
	/** 排序方式- 涨速 */
	public final static byte PX_ZS = 14;
	/** 排序方式- 今开 */
	public final static byte PX_JK = 15;
	/** 排序方式- 涨跌 */
	public final static byte PX_ZD = 16;
	/**
	 * 排序方式-买入价
	 */
	public final static byte PX_BUY = 17;
	/**
	 * 排序方式-卖出价
	 */
	public final static byte PX_SELL = 18;
	/**
	 * 排序方式-持仓
	 */
	public final static byte PX_CC = 19;
	/**
	 * 排序方式-板块领涨股涨跌幅
	 */
	public final static byte PX_LZG_ZDF = 20;
	/**
	 * 排序方式-名称
	 */
	public final static byte PX_NAME = 21;
	/**
	 * 排序方式-买入量
	 */
	public final static byte PX_BUY_VOLUM = 22;
	/**
	 * 排序方式-卖出量
	 */
	public final static byte PX_SELL_VOLUM = 23;
	// //////////////////////////////////////////////////////////
	/** 深圳交易所 */
	public final static short SE_SZ = 1;
	/** 上海交易所 */
	public final static short SE_SH = 2;
	/** 上海交易所+深圳交易所 */
	public final static short SE_SS = 3;
	/** 三板市场 */
	public final static short SE_SB = 4;
	/** 港股市场 */
	public final static short SE_GG = 5;
	/**
	 * B转H股
	 */
	public final static short SE_BH = 6;
	/** 上海交易所+深圳交易所+三板市场 */
	public final static short SE_CSE = 7;
	/** 全球股指*/
	public final static short SE_QQ = 8;
	/** 个股期权*/
	public final static short SE_OPTION = 9;
	/** 上海商品期货交易所 */
	public final static short SE_SH_QH = 17;
	/** 大连商品期货交易所 */
	public final static short SE_DL_QH = 18;
	/** 郑州商品期货交易所 */
	public final static short SE_ZZ_QH = 20;
	/** 中国金融期货交易所(中金所) */
	public final static short SE_ZQ_QH = 24;
	/** 上海期货+大连期货+郑州期货+中金所 */
	public final static short SE_ZG_QH = 31;
    /** 深港通 */
    public final static short SE_SGT = 32;
    /** 沪港通*/
	public final static short SE_HGT = 33;
	/**
	 * 融资融券
	 */
	public final static short RZRQ = 34;

}
