package com.romaway.android.phone.keyboardelf;

import android.content.Context;

import com.romaway.commons.lang.StringUtils;

/**
 * Created by hongrb on 2016/11/4 15:39
 */
public class Roma_MarketIdManager {

    // Roma_MarketIdManager
    private static Roma_MarketIdManager marketIdManager;

    // 上下文对象
    private Context context;

    private Roma_MarketIdManager(Context context) {
        this.context = context;
    }

    public static Roma_MarketIdManager getInstance(Context context) {

        if (marketIdManager == null) {
            marketIdManager = new Roma_MarketIdManager(context);
        }
        return marketIdManager;
    }

    /**
     * 纠正市场代码: 行情市场代码 --> 交易市场代码
     * 暂仅支持沪深AB股
     * @param marketId	行情市场代码
     * @return			交易市场代码
     */
    public String tradeMarketIdCorrect(String stockCode, String marketId, String codeType){
        String tradeMarketId = "";
        if (!StringUtils.isEmpty(marketId)) {
            if ("1".equals(marketId)) {  // 深证
                if ("2".equals(codeType)) {  // B股
                    tradeMarketId = "2";
                } else {  // A股
                    tradeMarketId = "0";
                }
            } else if ("2".equals(marketId)) {  // 上证
                if ("2".equals(codeType)) {  // B股
                    tradeMarketId = "3";
                } else {  // A股
                    tradeMarketId = "1";
                }
            } else if ("4".equals(marketId)) {  // 股转
                String code = stockCode.substring(0, 3);
                if ("420".equals(code)) {  // B股
                    tradeMarketId = "5";
                } else {  // A股
                    tradeMarketId = "4";
                }
            } else if ("33".equals(marketId)) {  // 沪港通
                tradeMarketId = "6";
            } else if ("32".equals(marketId)) {  // 深港通
                tradeMarketId = "9";
            }  else if ("5".equals(marketId)) {  // 港股
                tradeMarketId = "100001";   // 由于交易内市场代码为5是股转B股，这里设置一个交易内不会使用到的市场代码
            } else {   // 其他保持不变
                tradeMarketId = marketId;
            }
        }
        return tradeMarketId;
    }

}
