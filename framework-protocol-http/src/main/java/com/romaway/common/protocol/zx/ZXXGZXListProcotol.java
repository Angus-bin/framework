package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by dell on 2016/11/14.
 */
public class ZXXGZXListProcotol extends AProtocol {
    /**
     * reg_funType是新股状态，X1今日申购，X2申购结束，X3待上市，X4即将申购，X5上市后表现
     */
    public String req_funType;
    /*
    * reg_sinceId表示取数据起始位置
    * */
    public String req_sinceId;
    /*reg_count为总共取列表记录条数*/
    public String req_count;

    /*当前价格（最新价格）*/
    public String[] resp_dqjg;
    /*发行价格*/
    public String[] resp_fxjg;
    /*发行日期（发行时间）*/
    public String[] resp_fxrq;
    /*发行总量（总发行量）*/
    public String[] resp_fxzl;
    /*股票代码*/
    public String[] resp_gpdm;
    /*股票名称（发行名称）*/
    public String[] resp_gpmc;
    /*消息id*/
    public String[] resp_id;
    /*交易所代码*/
    public String[] resp_jysdm;
    /*申购代码*/
    public String[] resp_sgdm;
    /*申购单位*/
    public String[] resp_sgdw;
    /*申购日期*/
    public String[] resp_sgrq;
    /*申购上限*/
    public String[] resp_sgsx;
    /*上市日期*/
    public String[] resp_ssrq;
    /*市盈率*/
    public String[] resp_syl;
    /*新股状态 1今日申购，2申购结束，3待上市，4即将申购，5上市后表现*/
    public String[] resp_xgzt;
    /*中签日期*/
    public String[] resp_zqrq;

    public String[] resp_wszql;

    public int resp_count;

    public ZXXGZXListProcotol(String flag, boolean autoRefreshStatus) {
        super(flag, false);
        isJson = true;
    }
}
