package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.AdjustStoreHistoryEntity;
import com.romaway.common.protocol.tougu.entity.AdjustStoreHistoryEntity2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 调仓记录
 * Created by chenjp on 2016/7/9.
 */
public class AdjustStoreHistoryProtocol extends AProtocol {

	public AdjustStoreHistoryProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "dayOptionLog/apiList";
	}

	public String resp_errorCode;

	public String resp_errorMsg;

	private List<AdjustStoreHistoryEntity> list;

	private List<AdjustStoreHistoryEntity2> list2;

	public List<AdjustStoreHistoryEntity> getList() {
		return list;
	}

	public void setList(List<AdjustStoreHistoryEntity> list) {
		this.list = list;
	}

	public List<AdjustStoreHistoryEntity2> getList2() {
		return list2;
	}

	public void setList2(List<AdjustStoreHistoryEntity2> list2) {
		this.list2 = list2;
	}

	private LinkedHashMap<Integer, List<AdjustStoreHistoryEntity2>> datas;

	public LinkedHashMap<Integer, List<AdjustStoreHistoryEntity2>> getDatas() {
		return datas;
	}

	public void setDatas(LinkedHashMap<Integer, List<AdjustStoreHistoryEntity2>> datas) {
		this.datas = datas;
	}
}
