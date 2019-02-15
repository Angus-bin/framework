package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.StockGroupEntity;

import java.util.List;

/** 组合持仓情况
 * Created by chenjp on 2016/7/9.
 */
public class GroupStoreProtocol extends AProtocol {
	public GroupStoreProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/tg-service/portfolio/zhcc/select/";
	}

	private String userID;
	private String groupName;
	private List<StockGroupEntity> list;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<StockGroupEntity> getList() {
		return list;
	}

	public void setList(List<StockGroupEntity> list) {
		this.list = list;
	}
}
