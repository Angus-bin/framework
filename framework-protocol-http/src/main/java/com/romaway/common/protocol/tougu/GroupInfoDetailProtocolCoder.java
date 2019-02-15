package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.GroupDetailEntity;
import com.romaway.common.protocol.tougu.entity.GroupInfoDetailEntity;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 组合信息
 * Created by hrb on 2016/8/4.
 */
public class GroupInfoDetailProtocolCoder extends AProtocolCoder<GroupInfoDetailProtocol> {
	@Override
	protected byte[] encode(GroupInfoDetailProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(GroupInfoDetailProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("GroupInfoDetailProtocolCoder", "decode >>> result body = " + result);
		try {
			if (!TextUtils.isEmpty(result)) {
				JSONArray jsonArray = new JSONArray(result);
				ArrayList<GroupInfoDetailEntity> list = new ArrayList<GroupInfoDetailEntity>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					GroupInfoDetailEntity entity = new GroupInfoDetailEntity();
					entity.setName(jsonObject.optString("name"));
					entity.setIdea(jsonObject.optString("idea"));
					entity.setSfyc(jsonObject.optString("sfyc"));
					entity.setId(jsonObject.optString("id"));
					list.add(entity);
				}
				protocol.setList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
