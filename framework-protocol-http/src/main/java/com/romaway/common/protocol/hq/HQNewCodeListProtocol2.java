package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * Created by Administrator on 2018/5/24.
 */
public class HQNewCodeListProtocol2 extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    /**
     * 增加的股票代码
     */
    public String[] resp_pszAddCode_s;
    /**
     * 名称
     */
    public String[] resp_pszName_s;
    /**
     * 拼音代码
     */
    public String[] resp_pszPYCode_s;
    /**
     * 交易所代码
     */
    public int[] resp_wMarketID_s;
    /**
     * 股票代码类型 0表示代码，其它表示各种类型
     */
    public int[] resp_wCodeType_s;

    /**
     * @param flag
     */
    public HQNewCodeListProtocol2(String flag)
    {
        super(flag, false);
        this.subFunUrl = "static/upload/code.js";
        isJson=true;
    }

}
