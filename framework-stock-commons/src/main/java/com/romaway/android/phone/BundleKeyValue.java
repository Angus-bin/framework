/**
 * 
 */
package com.romaway.android.phone;

public interface BundleKeyValue {
	
	final String USER_LOGIN_NAME = "USER_LOGIN_NAME";
	final String USER_LOGIN_PSW = "USER_LGOIN_PSW";

	/**
	 * 市场
	 */
	final String HQ_BK_MARKETID = "HQ_BK_MARKET";
	/**
	 * 商品类型
	 */
	final String HQ_BK_TYPE = "HQ_BK_TYPE";
	/**
	 * 板块代码
	 */
	final String HQ_BK_CODE = "HQ_BK_CODE";
	/**
	 * 市场-市场ID
	 */
	final String HQ_SHICHANG_MARKETID = "HQ_SHICHANG_MARKETID";
	/**
	 * 市场-商品类型
	 */
	final String HQ_SHICHANG_TYPE = "HQ_SHICHANG_TYPE";
	/**
	 * 股转-股转ID
	 */
	final String HQ_GUZHUAN_MARKETID = "HQ_GUZHUAN_MARKETID";
	/**
	 * 股转-商品类型
	 */
	final String HQ_GUZHUAN_TYPE = "HQ_GUZHUAN_TYPE";

	/**
	 * 港股-商品类型
	 */
	final String HQ_GANGGU_TYPE = "HQ_GANGGU_TYPE";

	/**
	 * 沪港通-商品类型
	 */
	final String HQ_HGT_TYPE = "HQ_HGT_TYPE";

	/**
	 * 期货-市场ID
	 */
	final String HQ_QIHUO_MARKETID = "HQ_QIHUO_MARKETID";
	/**
	 * 期货-商品类型
	 */
	final String HQ_QIHUO_TYPE = "HQ_QIHUO_TYPE";

	/**
	 * 列表跳转至内容：证券名称
	 */
	final String HQ_STOCKNAME = "HQ_STOCKNAME";
	/**
	 * 列表跳转至内容：股票代码
	 */
	final String HQ_STOCKCODE = "HQ_STOCKCODE";
	/**
	 * 列表跳转至内容：股票类型
	 */
	final String HQ_STOCKTYPE = "HQ_STOCKTYPE";
	/**
	 * 列表跳转至内容：市场ID
	 */
	final String HQ_MARKETID = "HQ_MARKETID";
	/**
	 * 列表跳转至内容：是否新的内容
	 */
	final String HQ_ISNEW = "HQ_ISNEW";
	/**
	 * 列表跳转至内容：来源
	 */
	final String HQ_FROM = "HQ_FROM";
	
	/**
	 * 来自，用于保存from值(int类型)
	 */
	final String FROM = "FROM";
	/**
	 * 跳转key:GO
	 */
	final String GO = "GO";

	/**
	 * 来源:主页
	 */
	final int HQ_FROM_HOME = 0;
	/**
	 * 来源:各列表
	 */
	final int HQ_FROM_HQLIST = 1;

	/**
	 * 来源:各自选
	 */
	final int HQ_FROM_USERSTOCK = 2;
	/**
	 * 来源:交易
	 */
	final int HQ_FROM_TRADE = 3;
	/**
	 * 来源:主页自选
	 */
	final int HQ_FROM_HOME_ZIXUAN = 61;
	
	/**
	 * 行情--股票数据
	 */
	final String HQ_STOCKDATA = "HQ_STOCKDATA";
	/**
	 * 证券索引
	 */
	final String STOCKINDEX = "STOCKINDEX";
	/**
	 * 证券类别
	 */
	final String STOCKTYPE = "STOCKTYPE";
	/**
	 * 协议类别
	 */
	final String STOCKPTL = "STOCKPTL";

	// *****************用户登陆注册**************************

	final String USER_DB = "user_data";
	/**
	 * 是否显示风险提示
	 */
	final String USERDB_RISK_ON_FIRST = "key_risk_on_first";
	/**
	 * 操作索引
	 */
	final String USERDB_HELP_ON_FIRST = "key_help_on_first_new";

	final String USERDB_VERSION_INTRODUCTION_ON_FIRST = "key_version_introduction_on_first";
	/**
	 * 是否首次添加默认自选股
	 */
	final String USERDB_ADD_DEFAULTSTOCK_ON_FIRST = "key_add_default_stock_on_first";

	/**
	 * 记住密码
	 */
	final String USER_LOGIN_FLAG_KEY = "USER_LOGIN_FLAG_KEY";
	/**
	 * 记住密码
	 */
	final String USER_LOGIN_FLAG_VEL = "USER_LOGIN_FLAG_VEL";
	/**
	 * 自动登录
	 */
	final String USER_LOGIN_AUTOLOGFLAG_KEY = "USER_LOGIN_AUTOLOGFLAG_KEY";
	/**
	 * 自动登录
	 */
	final String USER_LOGIN_AUTOLOGFLAG_VEL = "USER_LOGIN_AUTOLOGFLAG_VEL";
	
	/**
	 * 从首页快捷菜单前往目的地的title
	 */
	final String FROM_SHORTCUT_TO_TITLE = "FROM_SHORTCUT_TO_TITLE";
	/**
	 * 从首页快捷菜单前往目的地
	 */
	final String FROM_SHORTCUT_TO = "FROM_SHORTCUT_TO";
	/**
	 * 个股详情分时K线索引
	 */
	final String HQ_STOCKINFO_FSKLINE_TYPE = "HQ_STOCKINFO_FSKLINE_TYPE";
	/**
	 * 个股详情资讯索引
	 */
	final String HQ_STOCKINFO_ZX_INDEX = "HQ_STOCKINFO_ZX_INDEX";
	/**
	 * 个股详情F10索引
	 */
	final String HQ_STOCKINFO_F10_INDEX = "HQ_STOCKINFO_F10_INDEX";
}