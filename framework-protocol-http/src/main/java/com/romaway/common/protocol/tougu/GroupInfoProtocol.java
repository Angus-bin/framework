package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.GroupInfoEntity;

import java.util.List;

/** 组合列表协议
 * Created by chenjp on 2016/7/9.
 */
public class GroupInfoProtocol extends AProtocol {

	public String resp_errorCode;

	public String resp_errorMsg;

	private List<GroupInfoEntity> list;

	public List<GroupInfoEntity> getList() {
		return list;
	}

	public void setList(List<GroupInfoEntity> list) {
		this.list = list;
	}

	/**
	 * 组合列表总数
	 */
	public int resp_count;

	public GroupInfoProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "article/apiList";
	}
}
