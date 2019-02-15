package com.romaway.android.phone.netresp;

public enum HQEnumValue {

	/**
	 * 自选行情协议 功能号 3
	 */
	
	/**股票交易所类型*/
	STOCK_MARKET_ID(new String[]{"MarketId"}),//1：代表深证；2：代表上证
	/**股票商品类型*/
	STOCK_CODE_TYPE(new String[]{"商品类型"}),
	/**停牌标识*/
	STOCK_BPUSPENDED_S(new String[]{"停牌标识"}),
	/**
	 * 特殊股票标识
	 * HK 港股
	 * HGT 港股通
	 * R 融资融券
	 */
	STOCK_PSZMARK_S(new String[]{"特殊股票标识"}),
	
	
	/**股票名称*/
	STOCK_NAME(new String[]{"股票名称","自定义名称"}),
	/**股票代码*/
	STOCK_CODE(new String[]{"股票代码"}),
	/**股票现价*/
	STOCK_XIANJIA(new String[]{"最新价"}),
	/**股票涨幅*/
	STOCK_ZDF(new String[]{"涨幅"}),
	/**股票 涨跌*/
	STOCK_ZD(new String[]{"涨跌"}),
	/**股票昨收*/
	STOCK_ZSJ(new String[]{"昨收"}),
	/**股票总量*/
	STOCK_ZL(new String[]{"总量"}),
	/**成交金额*/
	STOCK_CJJE(new String[]{"金额"}),
	/**股票总额*/
	STOCK_ZE(new String[]{"总额"}),
	/**股票今开*/
	STOCK_JKJ(new String[]{"今开"}),
	/**股票最高*/
	STOCK_ZGJ(new String[]{"最高"}),
	/**股票最低*/
	STOCK_ZDJ(new String[]{"最低"}),
	/**股票市盈率*/
	STOCK_SYL(new String[]{"市盈率"}),
	/**股票换手率*/
	STOCK_HSL(new String[]{"换手"}),
	/**股票代码*/
	STOCK_MRJ(new String[]{"买入"}),
	/**股票卖出*/
	STOCK_MCJ(new String[]{"卖出"});
	
	private String[] value;
     
    private HQEnumValue(String[] value)
    {
        this.value=value;
    }
     
    public String[] toStringArray()
    {
         return value;
    }
    
}
