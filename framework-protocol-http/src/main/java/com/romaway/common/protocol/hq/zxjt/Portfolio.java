package com.romaway.common.protocol.hq.zxjt;

import android.support.annotation.Keep;

import java.util.List;

/**
 * Created by wanlh on 2016/10/8.
 */
@Keep
public class Portfolio{
    /**
     * 自选股版本，空(长度无限制) (服务端会管理 真实值为数字 ，每次自增 1)
     */
    public String version;

    public List<PorStockGroup> groupList;
}
