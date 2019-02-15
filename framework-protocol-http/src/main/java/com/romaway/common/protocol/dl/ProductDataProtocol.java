package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.GoldStockHoldListEntity;
import com.romaway.common.protocol.dl.entity.GoldStockOperationListEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/12.
 */
public class ProductDataProtocol extends AProtocol {

    public String resp_errCode;

    public String resp_errMsg;

    public String resp_new_sum;
    public String resp_month_sum;
    public String resp_year_sum;
    public String resp_quarter_sum;

    public String resp_id;
    public String resp_title;
    public String resp_sub_title;
    public String resp_intro;
    public String resp_zhuanantese;
    public String resp_zhisun;
    public String resp_qishu;
    public String resp_days;
    public String resp_price;
    public String resp_allow_num;
    public String resp_in_num;
    public String resp_service_time;
    public String resp_less_time;
    public String resp_is_subscribe;
    public String resp_start_at;
    public String resp_end_at;
    public String resp_service_desc;

    public String resp_five_sum;

    public String resp_pro_sum;
    public String resp_sort_sum;
    public String resp_total_space;
    public String resp_time_type;


    private List<GoldStockOperationListEntity> list_operation;

    public List<GoldStockOperationListEntity> getList_operation() {
        return list_operation;
    }

    public void setList_operation(List<GoldStockOperationListEntity> list_operation) {
        this.list_operation = list_operation;
    }

    private List<GoldStockHoldListEntity> list_hold;

    public List<GoldStockHoldListEntity> getList_hold() {
        return list_hold;
    }

    public void setList_hold(List<GoldStockHoldListEntity> list_hold) {
        this.list_hold = list_hold;
    }

    /**
     *
     * @param flag
     */
    public ProductDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/index";
    }

}
