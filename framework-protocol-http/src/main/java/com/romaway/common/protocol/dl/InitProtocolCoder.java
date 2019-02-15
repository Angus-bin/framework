package com.romaway.common.protocol.dl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romalibs.utils.gson.GsonHelper;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class InitProtocolCoder extends AProtocolCoder<InitProtocol> {

	@Override
	protected byte[] encode(InitProtocol ptl) {
		/**
		 * 设置请求header
		 */
		HashMap<String, String> sendHeads = new HashMap<String, String>();
		sendHeads.put("sign_token", ptl.req_signToken);
		sendHeads.put("phone_num", ptl.req_phoneNum);
		sendHeads.put("device_id", ptl.req_deviceID);
		sendHeads.put("cpid", ptl.req_cpid);
		sendHeads.put("app_type", ptl.req_appType);
		ptl.setSendHeader(sendHeads);
		/**
		 * 设置返回header
		 */
		String[] respHeaders = new String[] { "user_lvl", "kds_auth_type" };
		ptl.setResponseHeader(respHeaders);
		/**
		 * 设置请求body
		 */
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(InitProtocol ptl) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		String result = r.getString();
		//初始化数据保存全局
		InitProtocol.respJsonContent = result;

		// Gson 解析初始化的下发数据
		InitProtocol.datasMap = GsonHelper.dataMapFromJson(result);

		Logger.d("InitProtocolCoder", "decode >>> result body = " + result
				+ " header=" + ptl.getRespHeaderValue());
		HashMap<String, String> header = ptl.getRespHeaderValue();
		if (header != null && header.size() > 0) {
			ptl.resp_user_lvl = header.get("user_lvl");
			ptl.resp_auth_type = header.get("kds_auth_type");
		}
		try {
			JSONObject json = new JSONObject(result);
			//站点列表
			if(json.has("station")){
				JSONArray stationArr = json.getJSONArray("station");
				if (stationArr != null) {
					int stationLen = stationArr.length();
					ptl.resp_station_count = stationLen;
					ptl.resp_station_ip = new String[stationLen];
					ptl.resp_station_name = new String[stationLen];
					ptl.resp_station_port = new String[stationLen];
					ptl.resp_station_https_port = new String[stationLen];
					ptl.resp_station_moduleList = new String[stationLen][];
					for (int i = 0; i < stationArr.length(); i++) {
						JSONObject station = stationArr.getJSONObject(i);
						ptl.resp_station_ip[i] = station.optString("stationIp");
						ptl.resp_station_name[i] = station.optString("stationName");
						ptl.resp_station_port[i] = station.optString("stationPort");
						if (station.has("stationHttpsPort")) {
							ptl.resp_station_https_port[i] = station.optString("stationHttpsPort");
						}
						if (station.has("modulelist")) {
							try{
								JSONArray moduleArray = station.getJSONArray("modulelist");
								int len = moduleArray.length();
								ptl.resp_station_moduleList[i] = new String[len];
								for (int j = 0; j < len; j++) {
									ptl.resp_station_moduleList[i][j] = moduleArray.optString(j);
								}
							}catch(Exception e){

							}
						}
					}
				}
			}
			//站点配置信息
			if (json.has("stationConfig")) {
				JSONArray stationConfig = json.getJSONArray("stationConfig");
				if(stationConfig != null && stationConfig.length() >0){
					ptl.resp_stationCount = stationConfig.getJSONObject(0).getInt("stationCount");
					String range = stationConfig.getJSONObject(1).optString("stationLoadRange");
					ptl.resp_station_load_range_min = StringUtils.stringToInt(range.substring(0, range.indexOf(",")));
					ptl.resp_station_load_range = StringUtils.stringToInt(range.substring(range.indexOf(",")+1));
					ptl.resp_speed_load_rate = stationConfig.getJSONObject(2).getDouble("speedLoadRate");
				}
			}
			//最新推荐
			if (json.has("financials")) {
				JSONArray stationConfig = json.getJSONArray("financials");
				ptl.resp_newest_recommend=stationConfig.toString();
			}

			//客户端版本号
			if (json.has("versionNum")) {
				JSONArray versionArr = json.getJSONArray("versionNum");
				if(versionArr != null && versionArr.length() > 0){
					ptl.resp_beta_versionNo = ((JSONObject)versionArr.get(0)).optString("betaVersionNo");
					ptl.resp_online_versionno = ((JSONObject)versionArr.get(1)).optString("onlineVersionNo");
				}
			}
			//默认自选股
			if (json.has("defaultStock")) {
				JSONArray stockArr = json.getJSONArray("defaultStock");
				if(stockArr != null){
					int stockLen = stockArr.length();
					ptl.resp_stock_count = stockLen;
					ptl.resp_stock_code = new String[stockLen];
					ptl.resp_stock_apps_id = new String[stockLen];
					ptl.resp_stock_market_code = new String[stockLen];
					ptl.resp_group_name = new String[stockLen];
					for (int i = 0; i < stockLen; i++) {
						JSONObject stock = stockArr.getJSONObject(i);
						ptl.resp_stock_code[i] = stock.optString("stock_code");
						ptl.resp_stock_apps_id[i] = stock.optString("appsId");
						ptl.resp_stock_market_code[i] = stock.optString("market_code");
						ptl.resp_group_name[i] = stock.optString("group");
					}
				}
			}
			//公告-notice
			if (json.has("notice")) {
				JSONArray noticeArr = json.getJSONArray("notice");
				if (noticeArr != null) {
					int noticeLen = noticeArr.length();
					ptl.resp_notice_count = noticeLen;
					ptl.resp_notice_content = new String[noticeLen];
					ptl.resp_notice_cpid = new String[noticeLen];
					ptl.resp_notice_priority = new String[noticeLen];
					ptl.resp_notice_title = new String[noticeLen];
					ptl.resp_notice_type = new String[noticeLen];
					ptl.resp_notice_valid_time = new String[noticeLen];
					for (int i = 0; i < noticeLen; i++) {
						JSONObject notice = noticeArr.getJSONObject(i);
						ptl.resp_notice_content[i] = notice.optString("content");
						ptl.resp_notice_priority[i] = notice.optString("priority");
						ptl.resp_notice_valid_time[i] = notice.optString("validTime");
						ptl.resp_notice_type[i] = notice.optString("type");
						ptl.resp_notice_title[i] = notice.optString("title");
						ptl.resp_notice_cpid[i] = notice.optString("cpId");
					}

				}
			}
			//升级信息
			if (json.has("upgradeInfo")) {
				JSONArray upgradeArr = json.getJSONArray("upgradeInfo");
				if (upgradeArr != null ) {
					int upgradeLen = upgradeArr.length();
					ptl.resp_upgrade_count = upgradeLen;
					ptl.resp_upgrade_ctName = new String[upgradeLen];
					ptl.resp_upgrade_ctCode = new String[upgradeLen];
					ptl.resp_upgrade_category = new String[upgradeLen];
					ptl.resp_upgrade_mode = new String[upgradeLen];
					ptl.resp_upgrade_download_url = new String[upgradeLen];
					ptl.resp_upgrade_version = new String[upgradeLen];
					ptl.resp_upgrade_msg = new String[upgradeLen];
					ptl.resp_upgrade_type = new String[upgradeLen];
					for (int i = 0; i <upgradeLen; i++) {
						JSONObject upgrade = upgradeArr.getJSONObject(i);
						ptl.resp_upgrade_ctName[i] = upgrade.optString("ctName");
						ptl.resp_upgrade_ctCode[i] = upgrade.optString("ctCode");
						ptl.resp_upgrade_category[i] = upgrade.optString("upgradeCategory");
						ptl.resp_upgrade_mode[i] = upgrade.optString("upgradeMode");
						ptl.resp_upgrade_download_url[i] = upgrade.optString("downloadAddr");
						ptl.resp_upgrade_version[i] = upgrade.optString("version");
						ptl.resp_upgrade_msg[i] = upgrade.optString("upgradeMsg");
						ptl.resp_upgrade_type[i] = upgrade.optString("type");
					}
				}
			}
			//自选股同步类型信息 -downZxgType
			if (json.has("downZxgType")) {
				JSONArray syncStockArr = json.getJSONArray("downZxgType");
				if(syncStockArr != null && syncStockArr.length() > 0){
					ptl.resp_sync_type = ((JSONObject) syncStockArr.getJSONObject(0)).getInt("type");
					ptl.resp_sync_apps_id = ((JSONObject) syncStockArr.getJSONObject(0)).getInt("appsId");
				}
			}
			//是否节假日-isHoliday
			if (json.has("isHoliday")) {
				JSONArray holidayArr = json.getJSONArray("isHoliday");
				if(holidayArr != null && holidayArr.length() > 0){
					ptl.resp_is_holiday = ((JSONObject) holidayArr.getJSONObject(0)).optString("isHoliday");
				}
			}
			//服务热线  APP 信息-appsInfo
			if (json.has("appsInfo")) {
				JSONArray appsArr = json.getJSONArray("appsInfo");
				if(appsArr != null && appsArr.length() > 0){
					ptl.resp_service_hot_line = ((JSONObject) appsArr.getJSONObject(0)).optString("serviceHotline");
					//微信公众号 serviceAccountCode
					if(((JSONObject) appsArr.getJSONObject(0)).has("serviceAccountCode")){
						ptl.resp_service_wechat_code = ((JSONObject) appsArr.getJSONObject(0)).optString("serviceAccountCode");
					}
					//服务号serviceServiceNo
					if(((JSONObject) appsArr.getJSONObject(0)).has("serviceServiceNo")){
						ptl.resp_serviceServiceNo = ((JSONObject) appsArr.getJSONObject(0)).optString("serviceServiceNo");
					}
				}
			}
			
			//H5下载升级模块
			JSONArray H5array = json.getJSONArray("h5UpgradePatch");
			if(H5array != null && H5array.length() > 0)
			{
				int len = H5array.length();
				ptl.resp_versionNum = new String[len];
				ptl.resp_fileName = new String[len];
				ptl.resp_fileSize = new int[len];
				ptl.resp_downloadAddr = new String[len];
				ptl.resp_h5_upgrade_type = new String[len];
				ptl.resp_h5_upgradeCategory = new int[len];
				ptl.resp_h5_upgradeMsg = new String[len];
				for (int i = 0; i < H5array.length(); i++) 
				{
					JSONObject jsonObject = H5array.getJSONObject(i);
					ptl.resp_versionNum[i] = jsonObject.optString("versionNum");
					ptl.resp_fileName[i] = jsonObject.optString("fileName");
					ptl.resp_fileSize[i] = jsonObject.optInt("fileSize");
					ptl.resp_downloadAddr[i] = jsonObject.optString("downloadAddr");
					ptl.resp_h5_upgrade_type[i] = jsonObject.optString("type");
					if(jsonObject.has("upgradeCategory"))
						ptl.resp_h5_upgradeCategory[i] = jsonObject.optInt("upgradeCategory");
					if(jsonObject.has("resp_h5_pushCategory"))
						ptl.resp_h5_upgradeCategory[i] = jsonObject.optInt("resp_h5_pushCategory");
					if(jsonObject.has("upgradeMsg"))
						ptl.resp_h5_upgradeMsg[i] = jsonObject.optString("upgradeMsg");
				 }
			}
			//广告信息
			if (json.has("advtInfo")) {
				JSONArray advtArray = json.getJSONArray("advtInfo");
				if (advtArray != null ) {
					int advtLen = advtArray.length();
					ptl.resp_advtCount = advtLen;
					ptl.resp_advtTitle = new String[advtLen];
					ptl.resp_advtPosition = new String[advtLen];
					ptl.resp_advtType = new int[advtLen];
					ptl.resp_advtContent = new String[advtLen];
					ptl.resp_advtPicUrl = new String[advtLen];
					ptl.resp_advtLinked = new String[advtLen];
					ptl.resp_advtMemo = new String[advtLen];
					ptl.resp_advSrcTitleVisibility = new String[advtLen];
					ptl.resp_advWebViewLoginFlag = new String[advtLen];
					
					for (int i = 0; i < advtLen; i++) {
						JSONObject jsonObject = advtArray.getJSONObject(i);
						ptl.resp_advtTitle[i] = jsonObject.optString("advtTitle");
						ptl.resp_advtPosition[i] = jsonObject.optString("advtPosition");
						ptl.resp_advtType[i] = jsonObject.getInt("advtType");
						ptl.resp_advtContent[i] = jsonObject.optString("advtContent");
						ptl.resp_advtPicUrl[i] = jsonObject.optString("picUrl");
						ptl.resp_advtLinked[i] = jsonObject.optString("advtLinked");
						ptl.resp_advtMemo[i] = jsonObject.optString("memo");
						ptl.resp_advSrcTitleVisibility[i] = jsonObject.optString("src_TitleVisibility");
						ptl.resp_advWebViewLoginFlag[i] = jsonObject.optString("webViewLoginFlag");
					}
				}
			}
			
			//新广告信息
			if (json.has("advtnewInfo")) {
				JSONArray advtArray = json.getJSONArray("advtnewInfo");
				if (advtArray != null ) {
					int advtLen = advtArray.length();
					ptl.resp_initNewAdvInfo.resp_advtCount = advtLen;
					ptl.resp_initNewAdvInfo.resp_advtTitle = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_advtPosition = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_advtType = new int[advtLen];
					ptl.resp_initNewAdvInfo.resp_advtContent = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_advtPicUrl = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_advtLinked = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_advtMemo = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_advSrcTitleVisibility = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_advWebViewLoginFlag = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_endtime = new String[advtLen];
					ptl.resp_initNewAdvInfo.resp_starttime = new String[advtLen];
					for (int i = 0; i < advtLen; i++) {
						JSONObject jsonObject = advtArray.getJSONObject(i);
						ptl.resp_initNewAdvInfo.resp_advtTitle[i] = jsonObject.optString("advtTitle");
						ptl.resp_initNewAdvInfo.resp_advtPosition[i] = jsonObject.optString("advtPosition");
						ptl.resp_initNewAdvInfo.resp_advtType[i] = jsonObject.getInt("advtType");
						ptl.resp_initNewAdvInfo.resp_advtContent[i] = jsonObject.optString("advtContent");
						ptl.resp_initNewAdvInfo.resp_advtPicUrl[i] = jsonObject.optString("picUrl");
						ptl.resp_initNewAdvInfo.resp_advtLinked[i] = jsonObject.optString("advtLinked");
						ptl.resp_initNewAdvInfo.resp_advtMemo[i] = jsonObject.optString("memo");
						ptl.resp_initNewAdvInfo.resp_advSrcTitleVisibility[i] = jsonObject.optString("src_TitleVisibility");
						ptl.resp_initNewAdvInfo.resp_advWebViewLoginFlag[i] = jsonObject.optString("webViewLoginFlag");
						ptl.resp_initNewAdvInfo.resp_endtime[i] = jsonObject.optString("endtime");
						ptl.resp_initNewAdvInfo.resp_starttime[i] = jsonObject.optString("starttime");
					}
				}
			}

			//新广告信息(支持快捷菜单Key功能跳转)
			if (json.has("advtInfoV3")) {
				JSONArray advtArray = json.getJSONArray("advtInfoV3");
				if (advtArray != null ) {
					int advtLen = advtArray.length();
					ptl.resp_initNewAdvInfoV3.resp_advtCount = advtLen;
					ptl.resp_initNewAdvInfoV3.resp_advtTitle = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advtPosition = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advtType = new int[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advtContent = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advtPicUrl = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advtLinked = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advtMemo = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advSrcTitleVisibility = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_advWebViewLoginFlag = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_endtime = new String[advtLen];
					ptl.resp_initNewAdvInfoV3.resp_starttime = new String[advtLen];
					for (int i = 0; i < advtLen; i++) {
						JSONObject jsonObject = advtArray.getJSONObject(i);
						ptl.resp_initNewAdvInfoV3.resp_advtTitle[i] = jsonObject.optString("advtTitle");
						ptl.resp_initNewAdvInfoV3.resp_advtPosition[i] = jsonObject.optString("advtPosition");
						ptl.resp_initNewAdvInfoV3.resp_advtType[i] = jsonObject.getInt("advtType");
						ptl.resp_initNewAdvInfoV3.resp_advtContent[i] = jsonObject.optString("advtContent");
						ptl.resp_initNewAdvInfoV3.resp_advtPicUrl[i] = jsonObject.optString("picUrl");
						ptl.resp_initNewAdvInfoV3.resp_advtLinked[i] = jsonObject.optString("advtLinked");
						ptl.resp_initNewAdvInfoV3.resp_advtMemo[i] = jsonObject.optString("memo");
						ptl.resp_initNewAdvInfoV3.resp_advSrcTitleVisibility[i] = jsonObject.optString("src_TitleVisibility");
						ptl.resp_initNewAdvInfoV3.resp_advWebViewLoginFlag[i] = jsonObject.optString("webViewLoginFlag");
						ptl.resp_initNewAdvInfoV3.resp_endtime[i] = jsonObject.optString("endtime");
						ptl.resp_initNewAdvInfoV3.resp_starttime[i] = jsonObject.optString("starttime");
					}
				}
			}
			
			//参数配置
			if (json.has("params")) {
				JSONArray paramsArray = json.getJSONArray("params");
				if (paramsArray != null ) {
					int paramsLen = paramsArray.length();
					ptl.resp_paramsValue = new String[paramsLen];
					ptl.resp_paramsName = new String[paramsLen];
					ptl.resp_paramsId = new int[paramsLen];
					
					for (int i = 0; i < paramsLen; i++) {
						JSONObject jsonObject = paramsArray.getJSONObject(i);
						ptl.resp_paramsValue[i] = jsonObject.optString("param_value");
						ptl.resp_paramsName[i] = jsonObject.optString("param_name");
						ptl.resp_paramsId[i] = jsonObject.getInt("param_id");
					}
				}
			}
			
			//营业部信息
			if (json.has("deptinfos")) {
				JSONArray advtArray = json.getJSONArray("deptinfos");
				
				ptl.resp_deptinfos = advtArray.toString();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptl.serverErrCode = -1;//陈家平-1
			ptl.serverMsg = "网络请求失败";//ptl.getJSONExceptionInfo(e);
		}
	}
}
