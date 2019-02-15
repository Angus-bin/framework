package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.GroupDetailEntity;

import java.util.List;

/**
 * 组合明细
 * Created by chenjp on 2016/7/9.
 */
public class GroupDetailProtocol extends AProtocol {

	private List<GroupDetailEntity> list;

	public List<GroupDetailEntity> getList() {
		return list;
	}

	public void setList(List<GroupDetailEntity> list) {
		this.list = list;
	}

	public GroupDetailProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/tg-service/bacc/update/";
	}
}
