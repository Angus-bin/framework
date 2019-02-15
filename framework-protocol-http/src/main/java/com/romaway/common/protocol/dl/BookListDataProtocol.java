package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.entity.BookQswyEntity;

import java.util.List;

/**
 * Created by hongrb on 2017/9/18.
 */
public class BookListDataProtocol extends AProtocol {

    public String resp_errorCode;

    public String resp_errorMsg;

    public String resp_gold_title;

    public String resp_gold_sub_title;

    public String resp_gold_in_num;

    public String resp_gold_new_sum;

    public String resp_gold_month_sum;

    public String resp_gold_year_sum;

    public String resp_gold_quarter_sum;

    public String resp_gold_less_time;

    public String resp_ai_title;

    public String resp_ai_sub_title;

    public String resp_ai_in_num;

    public String resp_ai_new_sum;

    public String resp_ai_month_sum;

    public String resp_ai_year_sum;

    public String resp_ai_quarter_sum;

    public String resp_ai_less_time;

    public String resp_limit_title;

    public String resp_limit_sub_title;

    public String resp_limit_in_num;

    public String resp_limit_new_sum;

    public String resp_limit_month_sum;

    public String resp_limit_year_sum;

    public String resp_limit_five_sum;

    private List<BookQswyEntity> list;

    public List<BookQswyEntity> getList() {
        return list;
    }

    public void setList(List<BookQswyEntity> list) {
        this.list = list;
    }

    /**
     * 列表总数
     */
    public int resp_count;

    public BookListDataProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "product/subscribeList";
    }

}
