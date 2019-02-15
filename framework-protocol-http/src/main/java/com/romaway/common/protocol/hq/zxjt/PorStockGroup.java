package com.romaway.common.protocol.hq.zxjt;

import android.support.annotation.Keep;

import java.util.List;

/**
 * Created by wanlh on 2016/10/9.
 */
@Keep
public class PorStockGroup {
    /** 组名称 非空 */
    public String groupName;
    /** 组简称 非空 */
    public String shortName;

    public List<PorStockInfo> stockList;
}
