package com.romaway.common.protocol.hq.zxjt;

import android.support.annotation.Keep;

/**
 * Created by wanlh on 2016/10/9.
 */
@Keep
public class PorStockInfo {
    /** 市场代码 深圳 1、上海 2(服务端会将分别转化为 0 和 1) */
    public String marketCode;
    /** 股票代码(最长 8 位) */
    public String stockCode;
    /** 股票名称 */
    public String stockNote;
}
