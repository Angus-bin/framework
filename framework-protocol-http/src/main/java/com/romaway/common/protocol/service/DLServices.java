/**
 * 
 */
package com.romaway.common.protocol.service;

import com.romalibs.utils.Res;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.R;
import com.romaway.common.protocol.StringRandom;
import com.romaway.common.protocol.dl.AIStockDataProtocol;
import com.romaway.common.protocol.dl.BannerDetailProtocol;
import com.romaway.common.protocol.dl.BindPhoneProtocol;
import com.romaway.common.protocol.dl.BookListDataProtocol;
import com.romaway.common.protocol.dl.BookProductProtocol;
import com.romaway.common.protocol.dl.BroadcastDetailProtocol;
import com.romaway.common.protocol.dl.BroadcastProtocol;
import com.romaway.common.protocol.dl.GetWxPayOrderProtocol;
import com.romaway.common.protocol.dl.HistoryProductProtocol;
import com.romaway.common.protocol.dl.HoldStockProtocol;
import com.romaway.common.protocol.dl.HomeAdjustStoreHistoryProtocol;
import com.romaway.common.protocol.dl.HomeBrandDataProtocol;
import com.romaway.common.protocol.dl.HomeDaPanProtocol;
import com.romaway.common.protocol.dl.HomeLimitStockDataProtocol;
import com.romaway.common.protocol.dl.HomeNewsListProtocol;
import com.romaway.common.protocol.dl.HomeProductDataListProtocol;
import com.romaway.common.protocol.dl.HomeProductDataProtocol;
import com.romaway.common.protocol.dl.HomeProductDataProtocolNew;
import com.romaway.common.protocol.dl.HomeScrollAndBannerProtocol;
import com.romaway.common.protocol.dl.HomeTitleDetailProtocol;
import com.romaway.common.protocol.dl.HomeTitleProductDataProtocol;
import com.romaway.common.protocol.dl.HomeVideoListProtocol;
import com.romaway.common.protocol.dl.InitProtocol;
import com.romaway.common.protocol.dl.InitProtocolNew;
import com.romaway.common.protocol.dl.LoginCleanTokenProtocol;
import com.romaway.common.protocol.dl.LoginForOld2NewProtocol;
import com.romaway.common.protocol.dl.LoginProtocol;
import com.romaway.common.protocol.dl.LoginProtocolNew;
import com.romaway.common.protocol.dl.LoginSetTokenProtocol;
import com.romaway.common.protocol.dl.MsgListDataProtocol;
import com.romaway.common.protocol.dl.MsgMainListDataProtocol;
import com.romaway.common.protocol.dl.MsgReadFlagProtocol;
import com.romaway.common.protocol.dl.ProduceServiceListDataProtocol;
import com.romaway.common.protocol.dl.ProductDataProtocol;
import com.romaway.common.protocol.dl.PushSettingListProtocol;
import com.romaway.common.protocol.dl.PushSettingProtocol;
import com.romaway.common.protocol.dl.QswyRecommendProtocol;
import com.romaway.common.protocol.dl.QswyZxtcDataProtocol;
import com.romaway.common.protocol.dl.ShareTimesProtocol;
import com.romaway.common.protocol.dl.SignUpProtocol;
import com.romaway.common.protocol.dl.SmsRegisterProtocol;
import com.romaway.common.protocol.dl.SmsRegisterProtocolNew;
import com.romaway.common.protocol.dl.SmsRegisterProtocolNewNew;
import com.romaway.common.protocol.dl.StockDetailCashDataProtocol;
import com.romaway.common.protocol.dl.StockDetailCommentPointProtocol;
import com.romaway.common.protocol.dl.StockDetailCommentProtocol;
import com.romaway.common.protocol.dl.StockDetailCommentSendProtocol;
import com.romaway.common.protocol.dl.StockDetailFundDebtDataProtocol;
import com.romaway.common.protocol.dl.StockDetailGdrsDataProtocol;
import com.romaway.common.protocol.dl.StockDetailGsgkDataProtocol;
import com.romaway.common.protocol.dl.StockDetailHxtcDataProtocol;
import com.romaway.common.protocol.dl.StockDetailMainTargetDataprotocol;
import com.romaway.common.protocol.dl.StockDetailNoticeDataProtocol;
import com.romaway.common.protocol.dl.StockDetailPartnerTenDataProtocol;
import com.romaway.common.protocol.dl.StockDetailProfitDataProtocol;
import com.romaway.common.protocol.dl.StockDetailProtocol;
import com.romaway.common.protocol.dl.StockDetailProtocolNew;
import com.romaway.common.protocol.dl.StockDetailRecommendProtocol;
import com.romaway.common.protocol.dl.StockDetailReportDataProtocol;
import com.romaway.common.protocol.dl.StockDetailShareBonusDataProtocol;
import com.romaway.common.protocol.dl.StockDetailShuoBa2DataProtocol;
import com.romaway.common.protocol.dl.StockDetailShuoBaDataProtocol;
import com.romaway.common.protocol.dl.StockDetailShuoBaPointProtocol;
import com.romaway.common.protocol.dl.StockDetailTenPartnerDateProtocol;
import com.romaway.common.protocol.dl.StockFeedBackProtocol;
import com.romaway.common.protocol.dl.StockNewsListProtocol;
import com.romaway.common.protocol.dl.StockShareToSeeProtocol;
import com.romaway.common.protocol.dl.StockShuoBaDetailAddCommentProtocol;
import com.romaway.common.protocol.dl.StockShuoBaDetailAddReadProtocol;
import com.romaway.common.protocol.dl.StockShuoBaDetailCommentListProtocol;
import com.romaway.common.protocol.dl.StockShuoBaDetailCommentProtocol;
import com.romaway.common.protocol.dl.StockShuoBaDetailHotCommentProtocol;
import com.romaway.common.protocol.dl.StockShuoBaDetailDataProtocol;
import com.romaway.common.protocol.dl.TestProtocol;
import com.romaway.common.protocol.dl.TextAdsDetailProtocol;
import com.romaway.common.protocol.dl.UpgradeStateProtocol;
import com.romaway.common.protocol.dl.VideoDataProtocol;
import com.romaway.common.protocol.dl.WenjianHistoryHoldProtocol;
import com.romaway.common.protocol.dl.WenjianListProtocol;
import com.romaway.common.protocol.dl.WxBindUserInfoProtocol;
import com.romaway.common.protocol.dl.WxSetUserInfoProtocol;
import com.romaway.common.protocol.dl.WxUserInfoProtocol;
import com.romaway.common.protocol.dl.WxUserInfoProtocol2;
import com.romaway.common.protocol.dl.WxUserInfoProtocol3;
import com.romaway.common.protocol.dl.WxUserInfoProtocol4;
import com.romaway.common.protocol.dl.ZtdjDataProtocol;
import com.romaway.common.protocol.dl.ZtdjWeekDataProtocol;
import com.romaway.common.protocol.dl.ZxczDataProtocol;
import com.romaway.common.protocol.hq.HQZXGYuYinGetCodeProtocol;
import com.romaway.common.utils.DES3;
import com.romaway.common.utils.RomaSysConfig;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * @author chenjp
 * 
 */
public class DLServices {

	/**
	 * 初始化认证
	 * @param phone
	 * @param appType
	 * @param signToken
	 * @param deviceId
	 * @param cpid
	 * @param softtype
	 * @param appid
	 * @param listener
	 * @param level
     * @param msgFlag
     * @param reSend
     * @return
     */
	public static NetMsg rz_dl(String phone, String appType, String signToken,
			String deviceId, String cpid, String softtype, String appid,
			INetReceiveListener listener, EMsgLevel level, String msgFlag,
			boolean reSend) {

		InitProtocol ptl = new InitProtocol(msgFlag);
		ptl.req_phoneNum = phone;
		ptl.req_appType = appType;
		ptl.req_signToken = signToken;
		ptl.req_deviceID = deviceId;
		ptl.req_cpid = cpid;

        //[需求] 添加软件类型 用于初始化协议入参 wanlh 2105/11/30
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + softtype + appid, true, 15000);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		msg.sendByGet = true;
		msg.sendByHttps = true;
		return msg;
	}

	/**
	 * 获取验证码
	 * 
	 * @param phoneNum
	 * @param deviceId
	 * @param cpid
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param reSend
	 * @return
	 */
	public static NetMsg rz_smsRegister(String phoneNum, String deviceId,
			String cpid, String appid, INetReceiveListener listener,
			EMsgLevel level, String msgFlag, boolean reSend) {
		SmsRegisterProtocol ptl = new SmsRegisterProtocol(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_deviceID = deviceId;
		ptl.req_cpid = cpid;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, true);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		msg.sendByHttps = true;
		return msg;
	}

	/**
	 * 获取验证码
	 *
	 * @param phoneNum
	 * @param deviceId
	 * @param cpid
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param reSend
	 * @return
	 */
	public static NetMsg rz_smsRegisterNew(String phoneNum, String deviceId,
										String cpid, String appid, INetReceiveListener listener,
										EMsgLevel level, String msgFlag, boolean reSend) {
		SmsRegisterProtocolNew ptl = new SmsRegisterProtocolNew(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_deviceID = deviceId;
		ptl.req_cpid = cpid;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, true);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		msg.sendByHttps = true;
		return msg;
	}

	/**
	 * 获取语音验证码
	 *
	 * @param phoneNum
	 * @param deviceId
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param reSend
	 * @return
	 */
	public static NetMsg rz_yuyinGetCode(String phoneNum, String deviceId, String appid, INetReceiveListener listener,
			EMsgLevel level, String msgFlag, boolean reSend) {
		HQZXGYuYinGetCodeProtocol ptl = new HQZXGYuYinGetCodeProtocol(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_deviceID = deviceId;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, true);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		msg.sendByHttps = true;
		return msg;
	}

	/**
	 * 验证码登录
	 * 
	 * @param phoneNum
	 * @param deviceId
	 * @param secCode
	 * @param type
	 * @param listener
	 * @param level
	 * @param msgFlag
	 * @param reSend
	 * @return
	 */
	public static NetMsg rz_smsLogin(String phoneNum, String deviceId,
			String secCode, String type, String appid,String invitation_code,
			INetReceiveListener listener, EMsgLevel level, String msgFlag,
			boolean reSend) {
		LoginProtocol ptl = new LoginProtocol(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_deviceID = deviceId;
		ptl.req_secCode = secCode;
		// ptl.req_type = type;
		ptl.invitation_code = invitation_code;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, true);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		msg.sendByHttps = true;
		return msg;
	}

//	/**
//	 * 验证码登录
//	 *
//	 * @param phoneNum
//	 * @param deviceId
//	 * @param secCode
//	 * @param type
//	 * @param listener
//	 * @param level
//	 * @param msgFlag
//	 * @param reSend
//	 * @return
//	 */
//	public static NetMsg rz_smsLoginNew(String phoneNum, String deviceId,
//									 String secCode, String type, String appid,String invitation_code,
//									 INetReceiveListener listener, EMsgLevel level, String msgFlag,
//									 boolean reSend) {
//		LoginProtocolNew ptl = new LoginProtocolNew(msgFlag);
//		ptl.req_phoneNum = phoneNum;
//		ptl.req_deviceID = deviceId;
//		ptl.req_secCode = secCode;
//		// ptl.req_type = type;
//		ptl.invitation_code = invitation_code;
//		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
//				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, true);
//		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
//		msg.sendByHttps = true;
//		return msg;
//	}

	/**
	 * 验证码登录,从老版本升级到新版本
	 * @param phoneNum
	 * @param deviceId
	 * @param password
	 * @param type
	 * @param appid
	 * @param listener
	 * @param level
	 * @param msgFlag
     * @param reSend
     * @return
     */
	public static NetMsg rz_smsLoginForOld2New(String phoneNum,
			String deviceId, String password, String type, String appid,
			INetReceiveListener listener, EMsgLevel level, String msgFlag,
			boolean reSend) {
		LoginForOld2NewProtocol ptl = new LoginForOld2NewProtocol(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_deviceID = deviceId;
		ptl.req_password = password;
		// ptl.req_type = type;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + appid, true);
		NetMsg msg = new NetMsg(msgFlag, level, ptl, connInfo, reSend, listener);
		msg.sendByHttps = true;
		return msg;
	}
	
	/**
	 * 获取升级状态
	 * @param phoneNum
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 * @return
	 */
	public static NetMsg rz_upgrade_state(String phoneNum, String appid, INetReceiveListener listener, String msgFlag){
		UpgradeStateProtocol ptl = new UpgradeStateProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + phoneNum + "/" + appid, false);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		return msg;
	}

	public static final void rz_test(String userID, INetReceiveListener listener, String msgFlag) {
		TestProtocol ptl = new TestProtocol(msgFlag);

		BaseJSONObject jsonObject = new BaseJSONObject();
		jsonObject.put("user_id", userID);

		String id = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			id = DES3.encode(jsonObject.toString());
			id = DES3.encodeToHex(id);
			id = id.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + id);
		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + id  + "&iv=" + DES3.getIv());
		try {
			Logger.d("test", DES3.decode(DES3.decodeHex(id.toUpperCase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 获取验证码
	 *
	 * @param phoneNum
	 * @param listener
	 * @param msgFlag
	 * @return
	 */
	public static final void rz_smsRegister(String phoneNum, String typeId, INetReceiveListener listener,String msgFlag) {
		SmsRegisterProtocolNewNew ptl = new SmsRegisterProtocolNewNew(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_typeId = typeId;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("mobile", phoneNum);
		jsonObject.put("type_id", typeId);

		String xy = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + xy);
		ServerInfo temp = new ServerInfo("gxs_sms", ProtocolConstant.SERVER_FW_AUTH, "tougu", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy  + "&iv=" + DES3.getIv());
		try {
			Logger.d("test", DES3.decode(DES3.decodeHex(xy.toUpperCase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_Login(String phoneNum, String secCode, String friendId, INetReceiveListener listener,String msgFlag) {
		LoginProtocolNew ptl = new LoginProtocolNew(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_secCode = secCode;
		ptl.invitation_code = friendId;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("mobile", phoneNum);
		jsonObject.put("invite_code", friendId);
		jsonObject.put("sms_code", secCode);
		jsonObject.put("device_id", "1");

		String xy = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		ServerInfo temp = new ServerInfo("gxs_Login", ProtocolConstant.SERVER_FW_AUTH, "gxs_Login", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		try {
			Logger.d("test", DES3.decode(DES3.decodeHex(xy.toUpperCase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_wx_userinfo(String code, String appkey, String secret, String grant_type, INetReceiveListener listener,String msgFlag) {
		WxUserInfoProtocol ptl = new WxUserInfoProtocol(msgFlag);
		ptl.req_code = code;
		ptl.req_appid = appkey;
		ptl.req_secret = secret;
		ptl.req_grant_type = grant_type;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "appid=" + appkey + "&secret=" + secret + "&code=" + code + "&grant_type=" + grant_type);
		ServerInfo temp = new ServerInfo("gxs_wx_userinfo", ProtocolConstant.SERVER_FW_AUTH, "gxs_wx_userinfo", "https://api.weixin.qq.com/", false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "appid=" + appkey + "&secret=" + secret + "&code=" + code + "&grant_type=" + grant_type);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		msg.sendByHttps = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_wx_userinfo2(String appkey, String refresh_token, String grant_type, INetReceiveListener listener,String msgFlag) {
		WxUserInfoProtocol2 ptl = new WxUserInfoProtocol2(msgFlag);
		ptl.req_appid = appkey;
		ptl.req_grant_type = grant_type;

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "appid=" + appkey + "&grant_type=" + grant_type + "&refresh_token=" + refresh_token);
		ServerInfo temp = new ServerInfo("gxs_wx_userinfo", ProtocolConstant.SERVER_FW_AUTH, "gxs_wx_userinfo", "https://api.weixin.qq.com/", false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "appid=" + appkey + "&grant_type=" + grant_type + "&refresh_token=" + refresh_token);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		msg.sendByHttps = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_wx_userinfo3(String access_token, String openid, INetReceiveListener listener,String msgFlag) {
		WxUserInfoProtocol3 ptl = new WxUserInfoProtocol3(msgFlag);

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "access_token=" + access_token + "&openid=" + openid);
		ServerInfo temp = new ServerInfo("gxs_wx_userinfo", ProtocolConstant.SERVER_FW_AUTH, "gxs_wx_userinfo", "https://api.weixin.qq.com/", false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "access_token=" + access_token + "&openid=" + openid);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		msg.sendByHttps = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_wx_userinfo4(String access_token, String openid, INetReceiveListener listener,String msgFlag) {
		WxUserInfoProtocol4 ptl = new WxUserInfoProtocol4(msgFlag);

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "access_token=" + access_token + "&openid=" + openid);
		ServerInfo temp = new ServerInfo("gxs_wx_userinfo", ProtocolConstant.SERVER_FW_AUTH, "gxs_wx_userinfo", "https://api.weixin.qq.com/", false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "access_token=" + access_token + "&openid=" + openid);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		msg.sendByHttps = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_set_wx_userinfo(String wx_string, INetReceiveListener listener,String msgFlag) {
		WxSetUserInfoProtocol ptl = new WxSetUserInfoProtocol(msgFlag);

		String xy = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(wx_string);
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		ServerInfo temp = new ServerInfo("gxs_wx_userinfo", ProtocolConstant.SERVER_FW_AUTH, "gxs_wx_userinfo", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_bind_mobile_phone(String userId, String phoneNum, String secCode, String friendId, INetReceiveListener listener,String msgFlag) {
		BindPhoneProtocol ptl = new BindPhoneProtocol(msgFlag);
		ptl.req_phoneNum = phoneNum;
		ptl.req_secCode = secCode;
		ptl.req_userId = userId;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("mobile", phoneNum);
		jsonObject.put("user_id", userId);
		jsonObject.put("invite_code", friendId);
		jsonObject.put("sms_code", secCode);

		String xy = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		ServerInfo temp = new ServerInfo("gxs_bind", ProtocolConstant.SERVER_FW_AUTH, "gxs_bind", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		try {
			Logger.d("test", DES3.decode(DES3.decodeHex(xy.toUpperCase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);

	}

	public static final void rz_bind_wx_userinfo(String userID, String wx_string, INetReceiveListener listener,String msgFlag) {
		WxBindUserInfoProtocol ptl = new WxBindUserInfoProtocol(msgFlag);

		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(wx_string);
			jsonObject.put("user_id", userID);
			wx_string = jsonObject.toString();
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(wx_string);
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		ServerInfo temp = new ServerInfo("gxs_bind_userinfo", ProtocolConstant.SERVER_FW_AUTH, "gxs_bind_userinfo", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void rz_set_token(String userID, String token, INetReceiveListener listener,String msgFlag) {
		LoginSetTokenProtocol ptl = new LoginSetTokenProtocol(msgFlag);
		ptl.req_userID = userID;
		ptl.req_token = token;

		String xy = "";
		try {

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("token", token);
			jsonObject.put("device_id", "1");
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_set_token", ProtocolConstant.SERVER_FW_AUTH, "gxs_set_token", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void rz_clean_token(String userID, INetReceiveListener listener,String msgFlag) {
		LoginCleanTokenProtocol ptl = new LoginCleanTokenProtocol(msgFlag);
		ptl.req_userID = userID;

		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_clean_token", ProtocolConstant.SERVER_FW_AUTH, "gxs_clean_token", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void rz_share_times(String userID, INetReceiveListener listener,String msgFlag) {
		ShareTimesProtocol ptl = new ShareTimesProtocol(msgFlag);
		ptl.req_userID = userID;
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_share_times", ProtocolConstant.SERVER_FW_AUTH, "gxs_share_times", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void rz_get_wx_pay(String userID, String device_id, String type_id, INetReceiveListener listener,String msgFlag) {
		GetWxPayOrderProtocol ptl = new GetWxPayOrderProtocol(msgFlag);
		ptl.req_userID = userID;
		ptl.req_device_id = device_id;
		ptl.req_type_id = type_id;
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("device_info", device_id);
			jsonObject.put("type_id", type_id);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_wx_pay", ProtocolConstant.SERVER_FW_AUTH, "get_wx_pay", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeAdjustStoryHistory(String userID, INetReceiveListener listener,String msgFlag) {
		HomeAdjustStoreHistoryProtocol ptl = new HomeAdjustStoreHistoryProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_home_hiostory", ProtocolConstant.SERVER_FW_AUTH, "get_home_hiostory", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeTitleDetail(String userID, INetReceiveListener listener,String msgFlag) {
		HomeTitleDetailProtocol ptl = new HomeTitleDetailProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_home_title", ProtocolConstant.SERVER_FW_AUTH, "get_home_title", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeNewsList(String userID, INetReceiveListener listener,String msgFlag) {
		HomeNewsListProtocol ptl = new HomeNewsListProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_home_news_list", ProtocolConstant.SERVER_FW_AUTH, "get_home_news_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeNewsList(String userID, String pageSize, INetReceiveListener listener,String msgFlag) {
		HomeNewsListProtocol ptl = new HomeNewsListProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?user_id=" + userID + "&pageSize=" + pageSize);
			ServerInfo temp = new ServerInfo("get_home_news_list", ProtocolConstant.SERVER_FW_AUTH, "get_home_news_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?user_id=" + userID + "&pageSize=" + pageSize);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeScrollAndBannerData(String userID, INetReceiveListener listener,String msgFlag) {
		HomeScrollAndBannerProtocol ptl = new HomeScrollAndBannerProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_home_news_list", ProtocolConstant.SERVER_FW_AUTH, "get_home_news_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockDetail(String userID, String shareID, INetReceiveListener listener,String msgFlag) {
		StockDetailProtocol ptl = new StockDetailProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("shareid", shareID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final  void reqStockDetailCommentList(String userID, String optionLogId, INetReceiveListener listener, String msgFlag) {
		StockDetailCommentProtocol ptl = new StockDetailCommentProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("optionlogid", optionLogId);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail_comment", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  static final void reqHoldStock(String userID, INetReceiveListener listener,String msgFlag) {
		HoldStockProtocol ptl = new HoldStockProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_hold_stock", ProtocolConstant.SERVER_FW_AUTH, "get_hold_stock", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final  void reqStockDetailCommentPoint(String userID, String commentid, INetReceiveListener listener,String msgFlag) {
		StockDetailCommentPointProtocol ptl = new StockDetailCommentPointProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("commentid", commentid);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail_comment", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockDetailCommentSend(String userID, String shareId, String content, INetReceiveListener listener,String msgFlag) {
		StockDetailCommentSendProtocol ptl = new StockDetailCommentSendProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("shareid", shareId);
			jsonObject.put("content", content);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail_comment", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqInit(INetReceiveListener listener, String version, String msgFlag) {
		InitProtocolNew ptl = new InitProtocolNew(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("device", "android");
			jsonObject.put("version", version);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_init", ProtocolConstant.SERVER_FW_AUTH, "gxs_init", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqBroadcastData(String pageIndex, INetReceiveListener listener,String msgFlag) {
		BroadcastProtocol ptl = new BroadcastProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_broad", ProtocolConstant.SERVER_FW_AUTH, "gxs_broad", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqDaPanData(String type, String productID, INetReceiveListener listener,String msgFlag) {
		HomeDaPanProtocol ptl = new HomeDaPanProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("type", type);
			jsonObject.put("pro_id", productID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_dapan", ProtocolConstant.SERVER_FW_AUTH, "gxs_dapan", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqProductData(String userID, String productID, INetReceiveListener listener,String msgFlag) {
		ProductDataProtocol ptl = new ProductDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_product_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_product_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqProductData(String userID, String productID, String type, INetReceiveListener listener,String msgFlag) {
		ProductDataProtocol ptl = new ProductDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			jsonObject.put("incomeType", type);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_product_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_product_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeProductData(String userID, INetReceiveListener listener,String msgFlag) {
		HomeProductDataProtocol ptl = new HomeProductDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_home_product_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_product_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqBookProduct(String userID, String productID, String type, INetReceiveListener listener,String msgFlag) {
		BookProductProtocol ptl = new BookProductProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			jsonObject.put("type", type);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_book_product", ProtocolConstant.SERVER_FW_AUTH, "gxs_book_product", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqZxczData(String userID, String productID, INetReceiveListener listener,String msgFlag) {
		ZxczDataProtocol ptl = new ZxczDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_zxcz_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_zxcz_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqZxczData(String userID, String productID, String userType, INetReceiveListener listener,String msgFlag) {
		ZxczDataProtocol ptl = new ZxczDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			jsonObject.put("user_type", userType);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_zxcz_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_zxcz_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqMoNiShouYi(String pageIndex, INetReceiveListener listener,String msgFlag) {
		BroadcastDetailProtocol ptl = new BroadcastDetailProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_zxcz_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_zxcz_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqZtdjData(String userID, String productID, INetReceiveListener listener,String msgFlag) {
		ZtdjDataProtocol ptl = new ZtdjDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			xy = xy.toLowerCase();
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_ztdj_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_ztdj_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqBannerDetail(String type, INetReceiveListener listener,String msgFlag) {
		BannerDetailProtocol ptl = new BannerDetailProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("type", type);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			Logger.d("init_info", "ip2 = " + ip);

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_banner_detail", ProtocolConstant.SERVER_FW_AUTH, "gxs_banner_detail", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqTextAdsData(String pageIndex, INetReceiveListener listener,String msgFlag) {
		TextAdsDetailProtocol ptl = new TextAdsDetailProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_textads_detail", ProtocolConstant.SERVER_FW_AUTH, "gxs_textads_detail", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeVideoData(String userID, String pageIndex, INetReceiveListener listener,String msgFlag) {
		HomeVideoListProtocol ptl = new HomeVideoListProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_video_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_video_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqBookListData(String userID, INetReceiveListener listener,String msgFlag) {
		BookListDataProtocol ptl = new BookListDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_book_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_book_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHistoryProductData(String userID, String productID, String pageIndex, INetReceiveListener listener,String msgFlag) {
		HistoryProductProtocol ptl = new HistoryProductProtocol(msgFlag);
		ptl.req_productID = productID;
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			Logger.d("limit_up_history", "jsonObject = " + jsonObject.toString());

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_book_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_book_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHistoryProductData(String userID, String productID, String pageIndex, String is_index, INetReceiveListener listener,String msgFlag) {
		HistoryProductProtocol ptl = new HistoryProductProtocol(msgFlag);
		ptl.req_productID = productID;
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			jsonObject.put("page", pageIndex);
			jsonObject.put("is_index", is_index);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			Logger.d("limit_up_history", "jsonObject = " + jsonObject.toString());

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_book_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_book_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqMsgMainList(String userID, String module_id, INetReceiveListener listener,String msgFlag) {
		MsgMainListDataProtocol ptl = new MsgMainListDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			if (!StringUtils.isEmpty(module_id)) {
				jsonObject.put("module_id", module_id);
			}
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_msg_main_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_msg_main_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqMsgList(String userID, String pageIndex, INetReceiveListener listener,String msgFlag) {
		MsgListDataProtocol ptl = new MsgListDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_msg_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_msg_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqMsgList(String userID, String pageIndex, String type, String productID, INetReceiveListener listener,String msgFlag) {
		MsgListDataProtocol ptl = new MsgListDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("page", pageIndex);
			jsonObject.put("module_id", type);
			if (!StringUtils.isEmpty(productID)) {
				jsonObject.put("pro_id", productID);
			}
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_msg_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_msg_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqSignUp(String userID, String name, String productID, String type, INetReceiveListener listener,String msgFlag) {
		SignUpProtocol ptl = new SignUpProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("name", name);
			jsonObject.put("pro_id", productID);
			jsonObject.put("type", type);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_sign_up", ProtocolConstant.SERVER_FW_AUTH, "gxs_sign_up", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqWenjianList(String userID, String pageIndex, INetReceiveListener listener,String msgFlag) {
		WenjianListProtocol ptl = new WenjianListProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_wenjian_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_wenjian_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqQswyZxtcData(String userID, String productID, INetReceiveListener listener,String msgFlag) {
		QswyZxtcDataProtocol ptl = new QswyZxtcDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("gxs_zxtc_list", ProtocolConstant.SERVER_FW_AUTH, "gxs_zxtc_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockDetailNew(String userID, String id, INetReceiveListener listener,String msgFlag) {
		StockDetailProtocolNew ptl = new StockDetailProtocolNew(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("id", id);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail_new", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail_new", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final  void reqStockDetailCommentList(String userID, String id, String pageIndex, INetReceiveListener listener, String msgFlag) {
		StockDetailCommentProtocol ptl = new StockDetailCommentProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("id", id);
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail_comment", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final  void reqStockDetailRecommendList(String userID, String id, String pageIndex, INetReceiveListener listener, String msgFlag) {
		StockDetailRecommendProtocol ptl = new StockDetailRecommendProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("id", id);
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail_recommend", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail_recommend", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockDetailCommentSendNew(String userID, String shareId, String content, INetReceiveListener listener,String msgFlag) {
		StockDetailCommentSendProtocol ptl = new StockDetailCommentSendProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("option_id", shareId);
			jsonObject.put("content", content);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_detail_comment_send", ProtocolConstant.SERVER_FW_AUTH, "get_stock_detail_comment_send", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqPushSettingList(String userID, INetReceiveListener listener,String msgFlag) {
		PushSettingListProtocol ptl = new PushSettingListProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_push_setting_list", ProtocolConstant.SERVER_FW_AUTH, "get_push_setting_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqPushSetting(String userID, String type, String type_v, INetReceiveListener listener,String msgFlag) {
		PushSettingProtocol ptl = new PushSettingProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("type", type);
			jsonObject.put("type_v", type_v);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_push_setting_list", ProtocolConstant.SERVER_FW_AUTH, "get_push_setting_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqPushSetting(String userID, String data, INetReceiveListener listener,String msgFlag) {
		PushSettingProtocol ptl = new PushSettingProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			if (!StringUtils.isEmpty(data)) {
				jsonObject.put("setData", data);
			}
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_push_setting_list", ProtocolConstant.SERVER_FW_AUTH, "get_push_setting_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockFeedBack(String userID, String stockID, String content, INetReceiveListener listener,String msgFlag) {
		StockFeedBackProtocol ptl = new StockFeedBackProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("stock_id", stockID);
			jsonObject.put("type", 6);
			jsonObject.put("content", content);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_push_setting_list", ProtocolConstant.SERVER_FW_AUTH, "get_push_setting_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqQswyHistoryHold(String userID, String productID, String pageIndex, INetReceiveListener listener,String msgFlag) {
		WenjianHistoryHoldProtocol ptl = new WenjianHistoryHoldProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pro_id", productID);
			jsonObject.put("page", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_history_hold", ProtocolConstant.SERVER_FW_AUTH, "get_history_hold", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeTitleProductData(INetReceiveListener listener, String msgFlag) {
		HomeTitleProductDataProtocol ptl = new HomeTitleProductDataProtocol(msgFlag);
		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl);
		ServerInfo temp = new ServerInfo("get_home_product_title", ProtocolConstant.SERVER_FW_AUTH, "get_home_product_title", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);

	}

	public static final void reqHomeTitleProductData(String type, INetReceiveListener listener, String msgFlag) {
		HomeTitleProductDataProtocol ptl = new HomeTitleProductDataProtocol(msgFlag);
		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?type=" + type);
		ServerInfo temp = new ServerInfo("get_home_product_title", ProtocolConstant.SERVER_FW_AUTH, "get_home_product_title", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?type=" + type);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);

	}

	public static final void reqHomeProductDataNew(INetReceiveListener listener,String msgFlag) {
		HomeProductDataProtocolNew ptl = new HomeProductDataProtocolNew(msgFlag);
		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl);
		ServerInfo temp = new ServerInfo("get_home_product_new", ProtocolConstant.SERVER_FW_AUTH, "get_home_product_new", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void reqHomeProductDataList(String userID, String productID, String pageIndex, String typeID, INetReceiveListener listener,String msgFlag) {
		HomeProductDataListProtocol ptl = new HomeProductDataListProtocol(msgFlag);
		ptl.productID = productID;
		ptl.type = typeID;
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("id", productID);
			jsonObject.put("page", pageIndex);
			jsonObject.put("type", typeID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_home_product_list", ProtocolConstant.SERVER_FW_AUTH, "get_home_product_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeBrandData(String positionID, INetReceiveListener listener,String msgFlag) {
		HomeBrandDataProtocol ptl = new HomeBrandDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("position_id", positionID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_home_brand_data", ProtocolConstant.SERVER_FW_AUTH, "get_home_brand_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqHomeBrandData(String positionID, String num, INetReceiveListener listener,String msgFlag) {
		HomeBrandDataProtocol ptl = new HomeBrandDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("position_id", positionID);
			jsonObject.put("num", num);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_home_brand_data", ProtocolConstant.SERVER_FW_AUTH, "get_home_brand_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqUpdateReadFlag(String userID, String msgID, INetReceiveListener listener,String msgFlag) {
		MsgReadFlagProtocol ptl = new MsgReadFlagProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("item_id", msgID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("set_read_flag", ProtocolConstant.SERVER_FW_AUTH, "set_read_flag", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqQswyRecommendData(String productID, INetReceiveListener listener,String msgFlag) {
		QswyRecommendProtocol ptl = new QswyRecommendProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("pro_id", productID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_qswy_recommend_data", ProtocolConstant.SERVER_FW_AUTH, "get_qswy_recommend_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqVideoData(String videoID, INetReceiveListener listener,String msgFlag) {
		VideoDataProtocol ptl = new VideoDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("id", videoID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();

			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_video_data", ProtocolConstant.SERVER_FW_AUTH, "get_video_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockNews(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockNewsListProtocol ptl = new StockNewsListProtocol(msgFlag);
		String xy = "";
		try {
//			BaseJSONObject jsonObject = new BaseJSONObject();
//			jsonObject.put("page", pageIndex);
//			jsonObject.put("code", stockCode);
//			jsonObject.put("size", size);
//			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
//			xy = DES3.encode(jsonObject.toString());
//			xy = DES3.encodeToHex(xy);
//			xy = xy.toLowerCase();

			String ip = "http://api.romawaysz.com/";
//			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
//				ip = RomaSysConfig.getIp();
//			} else {
//				ip = Res.getString(R.string.NetWork_IP);
//			}

			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_news", ProtocolConstant.SERVER_FW_AUTH, "get_stock_news", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockShareToSee(String userID, String stockID, String productID, INetReceiveListener listener,String msgFlag) {
		StockShareToSeeProtocol ptl = new StockShareToSeeProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("stock_id", stockID);
			jsonObject.put("pro_id", productID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("stock_share", ProtocolConstant.SERVER_FW_AUTH, "stock_share", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {

		}
	}

	public static final void reqZtdjWeekData(INetReceiveListener listener,String msgFlag) {
		ZtdjWeekDataProtocol ptl = new ZtdjWeekDataProtocol(msgFlag);
		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl);
		ServerInfo temp = new ServerInfo("limit_week_data", ProtocolConstant.SERVER_FW_AUTH, "limit_week_data", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void reqZtdjWeekData(String type, String productID, String end_at, String start_at, INetReceiveListener listener, String msgFlag) {
		ZtdjWeekDataProtocol ptl = new ZtdjWeekDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("type", type);
			jsonObject.put("pro_id", productID);
			jsonObject.put("end_at", end_at);
			jsonObject.put("start_at", start_at);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			String urls = "";
			if (!StringUtils.isEmpty(type)) {
				urls += "?type=" + type;
			}
			if (!StringUtils.isEmpty(productID)) {
				urls += "&pro_id=" + productID;
			}
			if (!StringUtils.isEmpty(end_at)) {
				urls += "&end_at=" + end_at;
			}
			if (!StringUtils.isEmpty(start_at)) {
				urls += "&start_at=" + start_at;
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + urls);
			ServerInfo temp = new ServerInfo("limit_week_data", ProtocolConstant.SERVER_FW_AUTH, "limit_week_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + urls);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockNoticeData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailNoticeDataProtocol ptl = new StockDetailNoticeDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_notice", ProtocolConstant.SERVER_FW_AUTH, "get_stock_notice", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockReportData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailReportDataProtocol ptl = new StockDetailReportDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_notice", ProtocolConstant.SERVER_FW_AUTH, "get_stock_notice", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockGsgkData(String stockCode, INetReceiveListener listener,String msgFlag) {
		StockDetailGsgkDataProtocol ptl = new StockDetailGsgkDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + stockCode + ".js");
			ServerInfo temp = new ServerInfo("get_stock_gsgk", ProtocolConstant.SERVER_FW_AUTH, "get_stock_gsgk", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + stockCode + ".js");
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockMainTargetData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailMainTargetDataprotocol ptl = new StockDetailMainTargetDataprotocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_main_target", ProtocolConstant.SERVER_FW_AUTH, "get_stock_main_target", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockFundDebtData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailFundDebtDataProtocol ptl = new StockDetailFundDebtDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_fund_debt", ProtocolConstant.SERVER_FW_AUTH, "get_stock_fund_debt", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockProfitData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailProfitDataProtocol ptl = new StockDetailProfitDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_profit", ProtocolConstant.SERVER_FW_AUTH, "get_stock_profit", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockCashData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailCashDataProtocol ptl = new StockDetailCashDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_cash", ProtocolConstant.SERVER_FW_AUTH, "get_stock_cash", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockGdrsData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailGdrsDataProtocol ptl = new StockDetailGdrsDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_gbgd", ProtocolConstant.SERVER_FW_AUTH, "get_stock_gbgd", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockTenPartnerData(String stockCode, INetReceiveListener listener,String msgFlag) {
		StockDetailTenPartnerDateProtocol ptl = new StockDetailTenPartnerDateProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?code=" + stockCode);
			ServerInfo temp = new ServerInfo("get_stock_ten_partner", ProtocolConstant.SERVER_FW_AUTH, "get_stock_ten_partner", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?code=" + stockCode);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockPartnerTenData(String date, String stockCode, INetReceiveListener listener,String msgFlag) {
		StockDetailPartnerTenDataProtocol ptl = new StockDetailPartnerTenDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?date=" + date + "&code=" + stockCode);
			ServerInfo temp = new ServerInfo("get_stock_partner_ten", ProtocolConstant.SERVER_FW_AUTH, "get_stock_partner_ten", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?date=" + date + "&code=" + stockCode);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockHxtcData(String stockCode, INetReceiveListener listener,String msgFlag) {
		StockDetailHxtcDataProtocol ptl = new StockDetailHxtcDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + stockCode + ".js");
			ServerInfo temp = new ServerInfo("get_stock_hxtc", ProtocolConstant.SERVER_FW_AUTH, "get_stock_hxtc", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + stockCode + ".js");
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockShareBonusData(String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailShareBonusDataProtocol ptl = new StockDetailShareBonusDataProtocol(msgFlag);
		try {
			String ip = "http://api.romawaysz.com/";
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			ServerInfo temp = new ServerInfo("get_stock_share_bonus", ProtocolConstant.SERVER_FW_AUTH, "get_stock_share_bonus", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?page=" + pageIndex + "&code=" + stockCode + "&size=" + size);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockShuoBaData(String userID, String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailShuoBaDataProtocol ptl = new StockDetailShuoBaDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("page", pageIndex);
			jsonObject.put("code", stockCode);
			jsonObject.put("size", size);
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_shuoba", ProtocolConstant.SERVER_FW_AUTH, "get_stock_shuoba", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockShuoBa2Data(String userID, String pageIndex, String stockCode, String size, INetReceiveListener listener,String msgFlag) {
		StockDetailShuoBa2DataProtocol ptl = new StockDetailShuoBa2DataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("page", pageIndex);
			jsonObject.put("code", stockCode);
			jsonObject.put("size", size);
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_shuoba2", ProtocolConstant.SERVER_FW_AUTH, "get_stock_shuoba2", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqUpLoadingShuoBaData(String userID, String title, String content, String stockCode, String imgs, INetReceiveListener listener,String msgFlag) {

	}

	public static final void reqStockShuoBaPointData(String userID, String type, String id, String check, INetReceiveListener listener,String msgFlag) {
		StockDetailShuoBaPointProtocol ptl = new StockDetailShuoBaPointProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("type", type);
			jsonObject.put("id", id);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_point", ProtocolConstant.SERVER_FW_AUTH, "get_stock_point", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqStockShuoBaDetailData(String userID, String no, String id, INetReceiveListener listener,String msgFlag) {
		StockShuoBaDetailDataProtocol ptl = new StockShuoBaDetailDataProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			if (!StringUtils.isEmpty(no)) {
				jsonObject.put("no", no);
			}
			if (!StringUtils.isEmpty(id)) {
				jsonObject.put("id", id);
			}
			jsonObject.put("user_id", userID);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_point", ProtocolConstant.SERVER_FW_AUTH, "get_stock_point", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqShuoBaAddRead(String userID, String type, String id, String check, INetReceiveListener listener,String msgFlag) {
		StockShuoBaDetailAddReadProtocol ptl = new StockShuoBaDetailAddReadProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("type", type);
			jsonObject.put("id", id);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_read", ProtocolConstant.SERVER_FW_AUTH, "get_stock_read", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqShuoBaHotCommentData(String no, String userID, String page, String size, INetReceiveListener listener,String msgFlag) {
		StockShuoBaDetailHotCommentProtocol ptl = new StockShuoBaDetailHotCommentProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("no", no);
			jsonObject.put("user_id", userID);
			jsonObject.put("page", page);
			jsonObject.put("size", size);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_shuoba_hot_comment", ProtocolConstant.SERVER_FW_AUTH, "get_shuoba_hot_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqShuoBaAllCommentData(String userID, String type, String id, String page, String size, INetReceiveListener listener,String msgFlag) {
		StockShuoBaDetailCommentProtocol ptl = new StockShuoBaDetailCommentProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("type", type);
			jsonObject.put("id", id);
			jsonObject.put("page", page);
			jsonObject.put("size", size);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_shuoba_hot_comment", ProtocolConstant.SERVER_FW_AUTH, "get_shuoba_hot_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqShuoBaAddComment(String userID, String type, String id, String content, String p_id, String to, INetReceiveListener listener,String msgFlag, boolean isReply) {
		StockShuoBaDetailAddCommentProtocol ptl = new StockShuoBaDetailAddCommentProtocol(msgFlag, isReply);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			if (!isReply) {
				jsonObject.put("user_id", userID);
				jsonObject.put("type", type);
				jsonObject.put("id", id);
				jsonObject.put("content", content);
			} else {
				jsonObject.put("user_id", userID);
				jsonObject.put("type", type);
				jsonObject.put("p_id", p_id);
				jsonObject.put("to", to);
				jsonObject.put("id", id);
				jsonObject.put("content", content);
			}
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_comment", ProtocolConstant.SERVER_FW_AUTH, "get_stock_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqShuoBaCommentList(String id, String userID, String page, String size, INetReceiveListener listener,String msgFlag) {
		StockShuoBaDetailCommentListProtocol ptl = new StockShuoBaDetailCommentListProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("id", id);
			jsonObject.put("user_id", userID);
			jsonObject.put("page", page);
			jsonObject.put("size", size);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			ServerInfo temp = new ServerInfo("get_stock_comment_list", ProtocolConstant.SERVER_FW_AUTH, "get_stock_comment_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqProductList(String user_id, INetReceiveListener listener,String msgFlag) {
		ProduceServiceListDataProtocol ptl = new ProduceServiceListDataProtocol(msgFlag);
		String xy = "";
		try {
			String ip = "http://api.guxiansen.test1.romawaysz.com/";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
					ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?user_id=" + user_id);
			ServerInfo temp = new ServerInfo("get_product_list", ProtocolConstant.SERVER_FW_AUTH, "get_product_list", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?user_id=" + user_id);
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqAIStockData(String size, String page, INetReceiveListener listener,String msgFlag) {
		AIStockDataProtocol ptl = new AIStockDataProtocol(msgFlag);
		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		String urls = "";
		if (!StringUtils.isEmpty(page)) {
			urls += "?page=" + page;
		}
		if (!StringUtils.isEmpty(size)) {
			urls += "&size=" + size;
		}
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + urls);
		ServerInfo temp = new ServerInfo("roma_gxs_ai", ProtocolConstant.SERVER_FW_AUTH, "roma_gxs_ai", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + urls);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void reqHomeLimitStockData(INetReceiveListener listener,String msgFlag) {
		HomeLimitStockDataProtocol ptl = new HomeLimitStockDataProtocol(msgFlag);
		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		String urls = "";
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + urls);
		ServerInfo temp = new ServerInfo("roma_home_limit", ProtocolConstant.SERVER_FW_AUTH, "roma_home_limit", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + urls);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

}
