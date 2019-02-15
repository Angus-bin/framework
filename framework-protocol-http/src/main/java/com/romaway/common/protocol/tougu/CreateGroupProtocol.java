package com.romaway.common.protocol.tougu;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.tougu.entity.StockGroupEntity;
import com.romaway.common.protocol.tougu.entity.StockInfoEntity;

import java.util.List;

/** 创建组合/调仓协议
 * Created by chenjp on 2016/7/9.
 */
public class CreateGroupProtocol  extends AProtocol{
	public String req_opter;
	public String req_userID;
	public String req_groupName;
	public List<StockInfoEntity> list;

	public String resp_errCode;
	public String resp_errMsg;
	public String resp_GroupID;
	public CreateGroupProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/tg-service/portfolio/cjzh/add/";
	}
}
