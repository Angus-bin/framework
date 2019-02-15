package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.StockInfoEntity;

import java.util.List;

/**
 * Created by hrb on 2016/7/16.
 */
public class ChangeStoreProtocol extends AProtocol {
    public String req_opter;
	public String req_userID;
	public String req_groupID;
	public List<StockInfoEntity> list;

    public String resp_errCode;
    public String resp_errMsg;
    public ChangeStoreProtocol(String flag) {
        super(flag, false);
        isJson = true;
        subFunUrl = "/api/tg-service/portfolio/cjzh/add/";
    }
}
