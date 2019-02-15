package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.IncomePercentTrendEntity;

import java.util.List;

/** 收益率走势
 * Created by chenjp on 2016/7/9.
 */
public class IncomePercentTrendProtocol extends AProtocol {

	private List<IncomePercentTrendEntity> list;

	public List<IncomePercentTrendEntity> getList() {
		return list;
	}

	public void setList(List<IncomePercentTrendEntity> list) {
		this.list = list;
	}

	public IncomePercentTrendProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/tg-service/favor/del/";
	}
}
