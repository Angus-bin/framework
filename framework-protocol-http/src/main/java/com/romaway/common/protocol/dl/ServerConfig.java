package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.coder.KCodeEngine;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * 券商配置信息，由服务器下发
 * 
 * @author dumh
 * 
 */
public class ServerConfig {
	/**
	 * 自选股同步时间版本
	 */
	public static String MYSTOCK_SYNCTIME_VERSION;

	/**
	 * 自选股同步分组数量
	 */
	public static String MYSTOCK_GROUPCOUNT;
	/**
	 * 初始化时获取手机号码
	 */
	public static final int GETPHONE_ON_FIRSTLOGIN = 1;

	/**
	 * 交易登录时获取手机号码
	 */
	public static final int GETPHONE_ON_TRADELOGIN = 2;

	/**
	 * 用户验证类型，0-不做身份验证
	 */
	public static final int AUTH_NONE = 0;
	/**
	 * 用户验证类型，1-只需手机号码
	 */
	public static final int AUTO_PHONEONLY = 1;
	/**
	 * 用户验证类型，2-同时验证手机号码和密码
	 */
	public static final int AUTH_PHONE_PWD = 2;
	/**
	 * 用户验证类型，4-一键注册
	 */
	public static final int AUTH_ONKEY_REGISTER = 4;

	// 以下内容来自协议中券商配置项，参考协议文档附录：10.18，券商系统标签及6.5 初始化协议之字段23券商配置选项
	/**
	 * 券商名称
	 */
	public static String name;

	/**
	 * webSiete
	 */
	public static String webSite = "";

	public static String phone;

	public static String hotLine;

	/**
	 * 是否支持信息地雷/及时播报
	 */
	public static boolean enableInfoBomb;

	/**
	 * 是否支持好友推荐
	 */
	public static boolean enableRecommand;

	/**
	 * 好友推荐提示语
	 */
	public static String recommandHint;

	/**
	 * 自动获取手机号码失败后，是否允许手动输入手机号码
	 */
	public static boolean enableInputPhoneManually;

	/**
	 * 三网合一是否显示短信发送界面(0 否 1 是，默认1)，该字段保留，暂时不用
	 */
	public static boolean enableShowSmsView;

	/**
	 * 交易登录时安全校验类型，0：不校验，1：验证码，2：动态令牌，默认值为1
	 */
	public static int securityType;

	/**
	 * 安全校验提示语
	 */
	public static String securityHint;

	/**
	 * 是否先显示运营商界面。运营商选择界面和一键注册界面先后显示次序。(0 否 1 是，默认1)
	 */
	public static boolean enableFirstShowSmsProvider;

	/**
	 * 获取手机号码时机。1表示初始化获取，2表示登录交易时获取，默认为1
	 */
	public static int getPhoneTime;

	/**
	 * 非三网合一情况下，是否显示短信指令控件(协议中 0 否 1 是，默认0)
	 */
	public static boolean enableShowSmsCmdControl;

	/**
	 * 一键注册界面，是否显示免责声明按钮(协议中 0 否 1 是，默认0)
	 */
	public static boolean enableShowMZSMButton = false;

	/**
	 * 免责声明URL地址，目前暂用于招商证券，默认为空，访问系统服务器
	 */
	public static String mzsmUrl;

	/**
	 * 成功发送完短信到获取手机号码之间的等待时间(单位：秒。默认为20秒)
	 */
	public static int smsWaitTime = 20;

	/**
	 * 一键注册界面提示语
	 */
	public static String autoKeyRegHint;

	/**
	 * 手工注册界面提示语
	 */
	public static String manulKeyRegHint;

	/**
	 * 是否显示手工注册发送短信按钮（协议中 0否，1是，默认值为0）
	 */
	public static boolean enableShowManulSmsButton;

	/**
	 * 帮助说明URL地址，目前只用于招商证券，默认值为空，使用系统服务器获取帮助说明
	 */
	public static String helpUrl;
	/**
	 * 服务器下发的信息，用于显示在经纪人推荐显示界面。
	 */
	public static String RecommendAgentMsg;
	/**
	 * 服务器下发的信息，西藏同信风险提示（设置里面的）
	 */
	public static String FXTSURL;
	/**
	 * 是否支持键盘精灵的数据从服务端获取(协议中：0 否 1 是，默认0)
	 */
	public static boolean enableStockKeyWizd;

	// 以上来自协议中券商配置项，参考协议文档附录：10.18，券商系统标签及6.5 初始化协议之字段23券商配置选项

	/** 用户验证类型 0-不做验证；1-只有电话号码验证；2-电话号码和密码验证；4-一键注册 */
	public static int authType = 0;

	/**
	 * 
	 * 一键注册验证时机<br>
	 * 默认：1-初始化完验证并且只有第一次的时候<br>
	 * 2-交易时验证，第一次交易登陆验证完之后再弹出用户登录验证流程
	 */
	public static int authTime = 1;

	/**
	 * 一键注册之短信指令
	 */
	public static String smsCommand;

	/**
	 * 短信端口
	 */
	public static String[] smsPorts;

	/**
	 * 短信运营商
	 */
	public static String[] smsProviders;

	/**
	 * 验证页面提示文字。<br>
	 * 如果以“ERROR+错误信息”开始, 表示客户端重新进行短信注册的原因客户端解析之后将错误提示放在验证页面提示文字之前，<br>
	 * 用红色突出信息，使得用户知道为什么需要进行重新短信注册。
	 */
	public static String smsAuthPageHint;
	/**
	 * 投资者保护信息的网址
	 */
	public static String InvestorProtectURL;

	// **************************************
	/**
	 * 服务器日期
	 */
	public static int serverDate;

	/**
	 * 公告内容
	 */
	public static String noticeInfo;

	/**
	 * 交易风险提示
	 */
	public static String fxts_notice;
	// ***************************************

	private static boolean canCheckUserOnTradeLogin = true;

	// *******************web 资讯地址 begin********************
	/**
	 * F10 网址
	 */
	public static String f10_url;

	/**
	 * 港股通F10网址
	 */
	public static String f10_ggt_url;

	/**
	 * 券商资讯/资讯中心 网址
	 */
	public static String news_url;
	/**
	 * 风险提示网址
	 */
	public static String fxts_url;
	/**
	 * 金融产品网址
	 */
	public static String financial_products_url;

	/**
	 * 营业部信息网址
	 */
	public static String the_sales_information_url;

	/**
	 * 国元视点网址
	 */
	public static String guoyuan_viewpoint_url;
	/**
	 * 国元关联自选网址 2014.5.14
	 */
	public static String GlzxURLForPHONE;

	/** 强身份认证绑定URL. */
	public static String ExtAuthBindURL;

	/** 强身份认证解绑URL */
	public static String ExtAuthDivURL;

	/** 华龙非凡财富微信介绍信息 */
	public static String FFCFWeiXinText;

	/** 华龙点金微信介绍信息 */
	public static String DJWeiXinText;

	// ********************web 资讯地址end


	/**
	 * 辅助方法：查询券商系统标签内容
	 * 
	 * @param array
	 * @param value
	 * @return
	 */
	public static String sysConfigTag(String[] array, String value,
			String defaultValue) {
		String returnValue = "";
		String tt = "";
		int count = 0;
		int t = array.length;
		for (int u = 0; u < t; u++) {
			try {
				String str = array[u];
				if (str.indexOf("=") != -1) {
					String[] oneStr = str.split("=");
					if (oneStr[0].equalsIgnoreCase(value)) {
						count = 0;
						if (StringUtils.isEmpty(oneStr[1])) {
							count = 1;
							tt = defaultValue;
						} else {
							tt = oneStr[1];
							for (int i = 2; i < oneStr.length; i++) {
								tt = tt + "=" + oneStr[i];
							}
							// 2014.5.14 zhxk
							// 当下发的网址中包含“=”的时候，后面的部分会被截取掉。所以，这样修改，把截取的几个再加上。
							// tt = oneStr[1];
						}
						Logger.i(":::sysConfigTag_oneStr[1]",
								String.format(":::[%s]", tt));

						return tt;
					}
				} else {
					count = 0;
				}

			} catch (Exception e) {
				Logger.i(":::Exception_oneStr[1]", String.format(":::[%s]", tt));
				count = 0;
				break;
			}

		}
		if (count == 0) {
			returnValue = defaultValue;
		}
		return returnValue;
	}

	/**
	 * 是否先显示运营商
	 * 
	 * @return
	 */
	public static boolean isFirstShowSmsProvider() {
		enableFirstShowSmsProvider = true;
		return (enableFirstShowSmsProvider && smsPorts.length > 1);
	}

	/**
	 * 判断登录交易时是否需要检查用户一键注册标识
	 * 
	 * @return
	 */
	public static boolean isEnableCheckUserOnTradeLogin() {
		return ((getPhoneTime == GETPHONE_ON_TRADELOGIN) && canCheckUserOnTradeLogin);
	}

	/**
	 * 设置禁止交易登录时获取手机号码。 当交易登录时获取过后，则调用此方法，以使在交易登录模块手机号码之获取一次。
	 */
	public static void disableCheckUserOnTradeLogin() {
		canCheckUserOnTradeLogin = false;
	}

}
