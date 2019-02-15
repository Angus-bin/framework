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
import com.romaway.common.protocol.dl.WoHistoryProtocol;
import com.romaway.common.protocol.wo.MsgAddCommentProtocol;
import com.romaway.common.protocol.wo.WoFeedbackAddProtocol;
import com.romaway.common.protocol.wo.WoTaskListProtocol;
import com.romaway.common.protocol.wo.WoUpdateUserImageProtocol;
import com.romaway.common.protocol.wo.WoUpdateUserNameProtocol;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocol;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocolNew;
import com.romaway.common.protocol.wo.WoUserInfoUpdateProtocol;
import com.romaway.common.protocol.wo.WoWxPayDataProtocol;
import com.romaway.common.utils.DES3;
import com.romaway.common.utils.RomaSysConfig;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class WoService {

	/**
	 * 用户信息下载接口
	 * @param userId
	 * @param listener
	 * @param msgFlag
	 * @return
	 */
	public static NetMsg wo_userInfo_slelect(int userId, INetReceiveListener listener,String msgFlag){
		WoUserInfoSelectProtocolNew protocol = new WoUserInfoSelectProtocolNew(msgFlag);
		protocol.req_userId = userId;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, protocol.subFunUrl);
		ServerInfo s = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", "http://sjzqali.csc108.com:9800", true, 9801);
		s.setSubFunUrl(protocol.subFunUrl);
		connInfo.setServerInfo(s);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, protocol, connInfo, true, listener);
		return msg;
	}
	
	/**
	 * 用户信息上传接口
	 * @param level
	 * @param name
	 * @param fundId
	 * @param userId
	 * @param moblieId
	 * @param avatar
	 * @param listener
	 * @param msgFlag
	 * @return
	 */
	public static NetMsg wo_userInfo_update(String level, String name, String fundId, String userId, String moblieId, String avatar, INetReceiveListener listener,String msgFlag){
		WoUserInfoUpdateProtocol protocol = new WoUserInfoUpdateProtocol(msgFlag);
		protocol.req_level = level;
		protocol.req_name = name;
		protocol.req_fundId = fundId;
		protocol.req_userId = userId;
		protocol.req_mobileId = moblieId;
		protocol.req_avatar = avatar;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_AUTH, protocol.subFunUrl);
		/*ServerInfo s = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", "http://120.24.103.19:31800", true, 21900);
		s.setSubFunUrl(protocol.subFunUrl);
		connInfo.setServerInfo(s);*/
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, protocol, connInfo, true, listener);
		return msg;
	}

	/**
	 * 用户信息下载接口
	 * @param userId
	 * @param listener
	 * @param msgFlag
	 * @return
	 */
	public static final void wo_userInfo_select(String userId, String is_refresh, INetReceiveListener listener,String msgFlag){
		WoUserInfoSelectProtocol ptl = new WoUserInfoSelectProtocol(msgFlag);
		ptl.req_userId = userId;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("user_id", userId);
		jsonObject.put("is_refresh", is_refresh);

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
		ServerInfo temp = new ServerInfo("gxs_userInfo", ProtocolConstant.SERVER_FW_AUTH, "tougu", ip, false, 9801);
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

	public static final void wo_update_name(String userId, String name, INetReceiveListener listener,String msgFlag) {
		WoUpdateUserNameProtocol ptl = new WoUpdateUserNameProtocol(msgFlag);
		ptl.req_userID = userId;
		ptl.req_userName = name;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("user_id", userId);
		jsonObject.put("realname", name);

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
		ServerInfo temp = new ServerInfo("gxs_update_name", ProtocolConstant.SERVER_FW_AUTH, "gxs_update_name", ip, false, 9801);
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

	public static final void wo_update_img(String userId, String base64Icon, INetReceiveListener listener,String msgFlag) {
		WoUpdateUserImageProtocol ptl = new WoUpdateUserImageProtocol(msgFlag);
		ptl.req_userID = userId;
		ptl.req_userImage = base64Icon;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("user_id", userId);
		jsonObject.put("img", base64Icon);

		String xy = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ptl.req_xy = jsonObject.toString();
		ptl.req_iv = DES3.getIv();

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl);
		ServerInfo temp = new ServerInfo("gxs_update_img", ProtocolConstant.SERVER_FW_AUTH, "gxs_update_img", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl);
		try {
			Logger.d("test", DES3.decode(DES3.decodeHex(xy.toUpperCase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = false;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void wo_feed_back(String userId, String feedback, INetReceiveListener listener,String msgFlag) {
		WoFeedbackAddProtocol ptl = new WoFeedbackAddProtocol(msgFlag);
		ptl.req_userID = userId;
		ptl.req_feedback = feedback;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("user_id", userId);
		jsonObject.put("content", feedback);

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
		ServerInfo temp = new ServerInfo("gxs_feed_back", ProtocolConstant.SERVER_FW_AUTH, "gxs_feed_back", ip, false, 9801);
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

	public static final void wo_history_data(String userId, INetReceiveListener listener,String msgFlag) {
		WoHistoryProtocol ptl = new WoHistoryProtocol(msgFlag);
		ptl.req_userID = userId;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("user_id", userId);

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
		ServerInfo temp = new ServerInfo("gxs_history_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_history_data", ip, false, 9801);
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

	public static final void wo_task_list_data(String userId, INetReceiveListener listener,String msgFlag) {
		WoTaskListProtocol ptl = new WoTaskListProtocol(msgFlag);
		ptl.req_userID = userId;

		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("user_id", userId);

		String xy = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger.d("test1", jsonObject.toString());

		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		ServerInfo temp = new ServerInfo("gxs_history_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_history_data", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
		try {
			Logger.d("test1", DES3.decode(DES3.decodeHex(xy.toUpperCase())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void wo_WxPayData(String userID, String productID, INetReceiveListener listener,String msgFlag) {
		WoWxPayDataProtocol ptl = new WoWxPayDataProtocol(msgFlag);
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
			ServerInfo temp = new ServerInfo("gxs_wxpay_data", ProtocolConstant.SERVER_FW_AUTH, "gxs_wxpay_data", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqMsgAddComment(String userID, String module_id, String pro_id, String content, String item_id, INetReceiveListener listener,String msgFlag) {
		MsgAddCommentProtocol ptl = new MsgAddCommentProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("content", content);
			if (!StringUtils.isEmpty(module_id)) {
				jsonObject.put("module_id", module_id);
			}
			if (!StringUtils.isEmpty(pro_id)) {
				jsonObject.put("pro_id", pro_id);
			}
			if (!StringUtils.isEmpty(item_id)) {
				jsonObject.put("item_id", item_id);
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
			ServerInfo temp = new ServerInfo("add_msg_comment", ProtocolConstant.SERVER_FW_AUTH, "add_msg_comment", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
