package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.GroupInfoEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/** 组合列表协议
 * Created by kds on 2016/7/9.
 */
public class GroupInfoProtocolCoder extends AProtocolCoder<GroupInfoProtocol> {
	@Override
	protected byte[] encode(GroupInfoProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(GroupInfoProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("GroupInfoProtocolCoder", "decode >>> result body = " + result);

		try {
			if (!TextUtils.isEmpty(result)){
				BaseJSONObject jsonObject = new BaseJSONObject(result);
				if (jsonObject.has("error")) {
					protocol.resp_errorCode = jsonObject.getString("error");
				}
				if (jsonObject.has("msg")) {
					protocol.resp_errorMsg = jsonObject.getString("msg");
				}
				if (jsonObject.has("xy")) {
					String str = jsonObject.getString("xy").toUpperCase();
					DES3.setIv(jsonObject.getString("iv"));
					Logger.d("GroupInfoProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
					BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
					if (jsonObject1.has("items")) {
						BaseJSONArray jsonArray = jsonObject1.getJSONArray("items");
						ArrayList<GroupInfoEntity> list = new ArrayList<GroupInfoEntity>();
						protocol.resp_count = jsonArray.length();
						for (int i = 0; i < jsonArray.length(); i++){
							BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
							GroupInfoEntity entity = new GroupInfoEntity();
							if (jsonObject2.has("prosandcons")) {
								entity.setProsandcons(jsonObject2.getString("prosandcons"));
							}
							if (jsonObject2.has("newsname")) {
								entity.setNewsname(jsonObject2.getString("newsname"));
							}
							if (jsonObject2.has("newscomment")) {
								entity.setNewscomment(jsonObject2.getString("newscomment"));
							}
							if (jsonObject2.has("newsimage")) {
								entity.setNewsimage(jsonObject2.getString("newsimage"));
							}
							if (jsonObject2.has("publishtime")) {
								entity.setPublishtime(jsonObject2.getString("publishtime"));
							}
							if (jsonObject2.has("readcounts")) {
								entity.setReadcounts(jsonObject2.getString("readcounts"));
							}
							if (jsonObject2.has("weburl")) {
								entity.setWeburl(jsonObject2.getString("weburl"));
							}
							if (jsonObject2.has("newsid")) {
								entity.setNewsid(jsonObject2.getString("newsid"));
							}
							if (jsonObject2.has("heatlevel")) {
								entity.setHeatlevel(jsonObject2.getString("heatlevel"));
							}
							if (jsonObject2.has("goodnum")) {
								entity.setGoodnum(jsonObject2.getString("goodnum"));
							}
							if (jsonObject2.has("isgood")) {
								entity.setIsgood(jsonObject2.getString("isgood"));
							}
							list.add(entity);
						}
						protocol.setList(list);
					}
				}
//				JSONArray jsonArray = new JSONArray(result);
//				ArrayList<GroupInfoEntity> list = new ArrayList<GroupInfoEntity>();
//				protocol.resp_count = jsonArray.length();
//				for (int i = 0; i < jsonArray.length(); i++){
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					GroupInfoEntity entity = new GroupInfoEntity();
//					entity.setCreateTime(jsonObject.optString("createTime"));
//					entity.setGzrs(jsonObject.optString("gzrs"));
//					entity.setId(jsonObject.optString("id"));
//					entity.setName(jsonObject.optString("name"));
//					entity.setNetWorth(jsonObject.optString("netWorth"));
//					entity.setSfgz(jsonObject.optString("sfgz"));
//					entity.setTotalIncomeRate(jsonObject.optString("totalIncomeRate"));
//					JSONObject user = jsonObject.optJSONObject("user");
//					entity.setStravSmall(user.optString("strav_small"));
//					entity.setStrnickName(user.optString("strnickName"));
//					entity.setUserID(jsonObject.optString("userId"));
//					entity.setUpdateTime(jsonObject.optString("gxsj"));
//					list.add(entity);
//				}
//				protocol.setList(list);
			} else {
				protocol.resp_count = 0;
			}
		} catch (Exception e){
			e.printStackTrace();
		}

	}
}
