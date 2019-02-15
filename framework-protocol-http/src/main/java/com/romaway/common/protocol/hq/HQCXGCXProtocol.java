package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by dell on 2016/11/15.
 */
public class HQCXGCXProtocol extends AProtocol {
    public String req_from; //begin
    public String req_count; //num

    /*上市日期*/
    public String[] resp_date;
    /*上市首日涨跌幅*/
    public String[] resp_first_day_change_percent;
    /*发行价*/
    public String[] resp_price;
    /*股票代码*/
    public String[] resp_stock_code;
    /*市场代码*/
    public String[] resp_stock_market;
    /*股票名称*/
    public String[] resp_stock_name;
    /*上市至今涨跌幅*/
    public String[] resp_to_this_day_change_percent;
    /*id*/
    public String[] resp_id;
    /*服务器总共次新股数量*/
    public int resp_count;
    public HQCXGCXProtocol(String flag, boolean autoRefreshStatus) {
        super(flag, false);
        isJson = true;
        subFunUrl = "/api/quote/cxg/?";
    }
}
