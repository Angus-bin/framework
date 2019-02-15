package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by dell on 2016/11/14.
 */
public class ZXXGZXDetailProtocol extends AProtocol {
    /*id为新股属性列表中的消息id*/
    public String req_id;

    /*顶格申购上限*/
    public String resp_dgsgsz;
    /*当前价格（最新价格）*/
    public String resp_dqjg;
    /*发行价格*/
    public String resp_fxjg;
    /*发行类型*/
    public String resp_fxlx;
    /*发行日期（发行时间）*/
    public String resp_fxrq;
    /*发行总量（总发行量）*/
    public String resp_fxzl;
    /*股票代码*/
    public String resp_gpdm;
    /*股票名称（发行名称）*/
    public String resp_gpmc;
    /*公司简介*/
    public String resp_gsjj;
    /*消息id*/
    public String resp_id;
    /*缴款数量*/
    public String resp_jksl;
    /*交易所代码*/
    public String resp_jysdm;
    /*申购代码*/
    public String resp_sgdm;
    /*申购单位*/
    public String resp_sgdw;
    /*申购价格*/
    public String resp_sgjg;
    /*申购日期*/
    public String resp_sgrq;
    /*申购上限*/
    public String resp_sgsx;
    /*申购下限*/
    public String resp_sgxx;
    /*申购资金上限*/
    public String resp_sgzjsx;
    /*所属行业*/
    public String resp_sshy;
    /*上市日期*/
    public String resp_ssrq;
    /*上市首日涨跌幅*/
    public String resp_sssrzdf;
    /*上市至今涨跌幅*/
    public String resp_sszjzdf;
    /*市盈率*/
    public String resp_syl;
    /*网上中签率*/
    public String resp_wszql;
    /*新股状态 1今日申购，2申购结束，3待上市，4即将申购，5上市后表现*/
    public String resp_xgzt;
    /*中签日期*/
    public String resp_zqrq;
    /*网上发行数量*/
    public String resp_wsfxsl;
    /*中签情况*/
    public String resp_zqqk;


    public ZXXGZXDetailProtocol(String flag, boolean autoRefreshStatus) {
        super(flag, false);
        isJson = true;
    }
}
