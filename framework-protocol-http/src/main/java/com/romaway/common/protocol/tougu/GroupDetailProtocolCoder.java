package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.GroupDetailEntity;
import com.romaway.common.protocol.tougu.entity.GroupInfoEntity;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 组合明细
 * Created by chenjp on 2016/7/9.
 */
public class GroupDetailProtocolCoder extends AProtocolCoder<GroupDetailProtocol> {
	@Override
	protected byte[] encode(GroupDetailProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(GroupDetailProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("GroupDetailProtocolCoder", "decode >>> result body = " + result);
		try {
			if (!TextUtils.isEmpty(result)) {
				JSONArray jsonArray = new JSONArray(result);
				ArrayList<GroupDetailEntity> list = new ArrayList<GroupDetailEntity>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					GroupDetailEntity entity = new GroupDetailEntity();
					entity.setBeatPercent(jsonObject.optString("beatPercent"));
					entity.setName(jsonObject.optString("name"));
					entity.setCreateTime(jsonObject.optString("createTime"));
					entity.setIdea(jsonObject.optString("idea"));
					entity.setNetWorth(jsonObject.optString("netWorth"));
					entity.setMonthIncomeRate(jsonObject.optString("monthIncomeRate"));
					entity.setTotalIncomeRate(jsonObject.optString("totalIncomeRate"));
					entity.setLastUpdateTime(jsonObject.optString("lastUpdateTime"));
					entity.setDayIncomeRate(jsonObject.optString("dayIncomeRate"));
					list.add(entity);
				}
				protocol.setList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
