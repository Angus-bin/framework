package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * Created by hongrb on 2018/3/5.
 */
public class StockDetailGsgkDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    /**
     * 上市日期
     */
    public String resp_ssrq;
    /**
     * 发行市盈率
     */
    public String resp_fxsyl;
    /**
     * 发行量
     */
    public String resp_fxl;
    /**
     * 发行价格
     */
    public String resp_mgfxj;
    /**
     * 募资净额
     */
    public String resp_mjzjje;
    /**
     * 中签率
     */
    public String resp_wxpszql;
    /**
     * 成立日期
     */
    public String resp_clrq;
    /**
     * 公司名称
     */
    public String resp_gsmc;
    /**
     * 曾用名
     */
    public String resp_cym;
    /**
     * 所属区域
     */
    public String resp_qy;
    /**
     * 所属行业
     */
    public String resp_sszjhhy;
    /**
     * 董事长
     */
    public String resp_dsz;
    /**
     * 法人代表
     */
    public String resp_frdb;
    /**
     * 董秘
     */
    public String resp_dm;
    /**
     * 总经理
     */
    public String resp_zjl;
    /**
     * 注册资本
     */
    public String resp_zczb;
    /**
     * 员工人数
     */
    public String resp_gyrs;
    /**
     * 管理层人数
     */
    public String resp_glryrs;
    /**
     * 联系电话
     */
    public String resp_lxdh;
    /**
     * 电子邮箱
     */
    public String resp_dzxx;
    /**
     * 公司网址
     */
    public String resp_gswz;
    /**
     * 办公地址
     */
    public String resp_bgdz;
    /**
     * 注册地址
     */
    public String resp_zcdz;
    /**
     * 公司简介
     */
    public String resp_gsjj;
    /**
     * 主营业务
     */
    public String resp_jyfw;

    public StockDetailGsgkDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "cli/gsgk/";
    }

}
