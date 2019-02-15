package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.AdjustStoreHistoryProtocol;
import com.romaway.common.protocol.tougu.entity.AdjustStoreHistoryEntity;
import com.romaway.common.protocol.tougu.entity.HomeAdjustStoreHistoryEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/** 调仓记录
 * Created by hongrb on 2016/7/9.
 */
public class HomeAdjustStoreHistoryProtocolCoder extends AProtocolCoder<HomeAdjustStoreHistoryProtocol> {

	@Override
	protected byte[] encode(HomeAdjustStoreHistoryProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(HomeAdjustStoreHistoryProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("HomeAdjustStoreHistoryProtocolCoder", "decode >>> result body = " + result);
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
					Logger.d("HomeAdjustStoreHistoryProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
					BaseJSONArray jsonArray = new BaseJSONArray(DES3.decode(DES3.decodeHex(str)));
					ArrayList<HomeAdjustStoreHistoryEntity> list = new ArrayList<HomeAdjustStoreHistoryEntity>();
					int count = jsonArray.length();
					for (int i = 0; i < count; i++) {
						BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
						HomeAdjustStoreHistoryEntity item = new HomeAdjustStoreHistoryEntity();
						if (jsonObject1.has("sharename")) {
							item.setShareName(jsonObject1.getString("sharename"));
						}
						if (jsonObject1.has("sharenum")) {
							item.setShareNum(jsonObject1.getString("sharenum"));
						}
						if (jsonObject1.has("shareid")) {
							item.setShareId(jsonObject1.getString("shareid"));
						}
						if (jsonObject1.has("sharetype")) {
							item.setShareType(jsonObject1.getString("sharetype"));
						}
						if (jsonObject1.has("earningsrate")) {
							item.setEarningSrate(jsonObject1.getString("earningsrate"));
						}
						if (jsonObject1.has("dayandtimes")) {
							item.setDayAndTimes(jsonObject1.getString("dayandtimes"));
						}
						if (jsonObject1.has("holdday")) {
							item.setHoldDay(jsonObject1.getString("holdday"));
						}
						if (jsonObject1.has("dealprice")) {
							item.setDealPrice(jsonObject1.getString("dealprice"));
						}
						if (jsonObject1.has("ishold")) {
							item.setIshold(jsonObject1.getString("ishold"));
						}
						list.add(item);
					}
					protocol.setList(list);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
