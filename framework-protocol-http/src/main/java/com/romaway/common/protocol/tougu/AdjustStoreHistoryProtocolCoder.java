package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.tougu.entity.AdjustStoreHistoryEntity;
import com.romaway.common.protocol.tougu.entity.AdjustStoreHistoryEntity2;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 调仓记录
 * Created by chenjp on 2016/7/9.
 */
public class AdjustStoreHistoryProtocolCoder extends AProtocolCoder<AdjustStoreHistoryProtocol> {

	@Override
	protected byte[] encode(AdjustStoreHistoryProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(AdjustStoreHistoryProtocol protocol) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("AdjustStoreHistoryProtocolCoder", "decode >>> result body = " + result);
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
					Logger.d("AdjustStoreHistoryProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
					BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
					BaseJSONArray jsonArray = jsonObject1.getJSONArray("list");
					ArrayList<AdjustStoreHistoryEntity> list = new ArrayList<AdjustStoreHistoryEntity>();
					ArrayList<AdjustStoreHistoryEntity2> list2 = new ArrayList<AdjustStoreHistoryEntity2>();
					LinkedHashMap<Integer, List<AdjustStoreHistoryEntity2>> datas = new LinkedHashMap<Integer, List<AdjustStoreHistoryEntity2>>();
					int count = jsonArray.length();
					for (int i = 0; i < count; i++) {
						BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
						AdjustStoreHistoryEntity item = new AdjustStoreHistoryEntity();
						List<AdjustStoreHistoryEntity2> tempList = new ArrayList<AdjustStoreHistoryEntity2>();
						if (jsonObject2.has("dayandtimes")) {
							item.setDayandtimes(jsonObject2.getString("dayandtimes"));
						}
						if (jsonObject2.has("earningsrate")) {
							item.setEarningsrates(jsonObject2.getString("earningsrate"));
						}
						if (jsonObject2.has("ishold")) {
							item.setIsholds(jsonObject2.getString("ishold"));
						}
						if (jsonObject2.has("status")) {
							item.setStatus(jsonObject2.getString("status"));
						}
						if (jsonObject2.has("dataarr")) {
							BaseJSONArray jsonArray1 = jsonObject2.getJSONArray("dataarr");
							int count2 = jsonArray1.length();
							for (int j = 0; j < count2; j++) {
								BaseJSONObject jsonObject3 = jsonArray1.getJSONObject(j);
								AdjustStoreHistoryEntity2 item2 = new AdjustStoreHistoryEntity2();
								if (jsonObject3.has("sharename")) {
									item2.setSharename(jsonObject3.getString("sharename"));
								}
								if (jsonObject3.has("sharenum")) {
									item2.setSharenum(jsonObject3.getString("sharenum"));
								}
								if (jsonObject3.has("shareid")) {
									item2.setShareid(jsonObject3.getString("shareid"));
								}
								if (jsonObject3.has("dealprice")) {
									item2.setDealprice(jsonObject3.getString("dealprice"));
								}
								if (jsonObject3.has("sharetype")) {
									item2.setSharetype(jsonObject3.getString("sharetype"));
								}
								if (jsonObject3.has("earningsrate")) {
									item2.setEarningsrate(jsonObject3.getString("earningsrate"));
								}
								if (jsonObject3.has("ishold")) {
									item2.setIshold(jsonObject3.getString("ishold"));
								}
								if (jsonObject3.has("holdday")) {
									item2.setHoldday(jsonObject3.getString("holdday"));
								}
								list2.add(item2);
								tempList.add(item2);
							}
						}
						list.add(item);
						datas.put(i, tempList);
					}
					protocol.setDatas(datas);
					protocol.setList2(list2);
					protocol.setList(list);
				}
//				JSONArray jsonArray = new JSONArray(result);
//				ArrayList<AdjustStoreHistoryEntity> list = new ArrayList<AdjustStoreHistoryEntity>();
//				int count = jsonArray.length();
//				for (int i = 0; i < count; i++){
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					AdjustStoreHistoryEntity item = new AdjustStoreHistoryEntity();
//					item.setAfterPercent(jsonObject.optString("afterPercent"));
//					item.setBatchid(jsonObject.optString("batchid"));
//					item.setBeforePercent(jsonObject.optString("beforePercent"));
//					item.setCreateTime(jsonObject.optString("createTime"));
//					item.setId(jsonObject.optString("id"));
//					item.setIsBuyOrSell(jsonObject.optString("isBuyOrSell"));
//					item.setPrice(jsonObject.optString("price"));
//					item.setStockCode(jsonObject.optString("stockCode"));
//					item.setStockName(jsonObject.optString("stockName"));
//					item.setMarketID(jsonObject.optString("marketCode"));
//					item.setIsTraded(jsonObject.optString("isTraded"));
//					item.setTradePrice(jsonObject.optString("tradePrice"));
//					item.setTradeTime(jsonObject.optString("tradeTime"));
//					item.setTradeVolume(jsonObject.optString("tradeVolume"));
//					list.add(item);
//				}
//				protocol.setList(list);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
