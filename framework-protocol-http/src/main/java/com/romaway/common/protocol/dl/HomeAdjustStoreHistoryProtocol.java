package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.AdjustStoreHistoryEntity;
import com.romaway.common.protocol.tougu.entity.HomeAdjustStoreHistoryEntity;

import java.util.List;

/** 调仓记录
 * Created by chenjp on 2016/7/9.
 */
public class HomeAdjustStoreHistoryProtocol extends AProtocol {

	public HomeAdjustStoreHistoryProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "site/apiNewLog";
	}

	public String resp_errorCode;

	public String resp_errorMsg;

	private List<HomeAdjustStoreHistoryEntity> list;

	public List<HomeAdjustStoreHistoryEntity> getList() {
		return list;
	}

	public void setList(List<HomeAdjustStoreHistoryEntity> list) {
		this.list = list;
	}


}
