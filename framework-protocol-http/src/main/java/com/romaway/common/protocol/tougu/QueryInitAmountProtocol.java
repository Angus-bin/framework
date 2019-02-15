package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;

/**
 * 获取初始总资产
 * Created by Edward on 2016/07/12.
 */
public class QueryInitAmountProtocol extends AProtocol {

	public QueryInitAmountProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/tg-service/portfolio/cszzc/select/";
	}

	public String resp_errMsg;
	public String resp_errCode;
	public String resp_initAmount;
}
