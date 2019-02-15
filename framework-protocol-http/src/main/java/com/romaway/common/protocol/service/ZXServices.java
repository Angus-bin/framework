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
import com.romaway.common.protocol.tougu.BannerInfoProtocol;
import com.romaway.common.protocol.tougu.GroupInfoProtocol;
import com.romaway.common.protocol.zx.GYShiDianAndNoticeDetailProtocol;
import com.romaway.common.protocol.zx.GYShiDianAndNoticeListProtocol;
import com.romaway.common.protocol.zx.NewsDataListProtocol;
import com.romaway.common.protocol.zx.NewsTopBarListProtocol;
import com.romaway.common.protocol.zx.RealTimeZiXunProtocol;
import com.romaway.common.protocol.zx.ZXDetailProtocol;
import com.romaway.common.protocol.zx.ZXF10CWXXProtocol;
import com.romaway.common.protocol.zx.ZXF10FHSPProtocol;
import com.romaway.common.protocol.zx.ZXF10GBQKProtocol;
import com.romaway.common.protocol.zx.ZXF10GDYJProtocol;
import com.romaway.common.protocol.zx.ZXF10GSGKProtocol;
import com.romaway.common.protocol.zx.ZXF10JJCGProtocol;
import com.romaway.common.protocol.zx.ZXF10LRBProtocol;
import com.romaway.common.protocol.zx.ZXF10XJLLProtocol;
import com.romaway.common.protocol.zx.ZXF10ZCFZProtocol;
import com.romaway.common.protocol.zx.ZXF10ZYGCProtocol;
import com.romaway.common.protocol.zx.ZXIPOProtocol;
import com.romaway.common.protocol.zx.ZXListProtocol;
import com.romaway.common.protocol.zx.ZXNewStockProtocol;
import com.romaway.common.protocol.zx.ZXXGZXDetailProtocol;
import com.romaway.common.protocol.zx.ZXXGZXListProcotol;
import com.romaway.common.utils.DES3;
import com.romaway.common.utils.RomaSysConfig;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

public class ZXServices {

	public static final void reqNewsZiXunList(String userID, String pageIndex, INetReceiveListener listener, String msgFlag) {
		GroupInfoProtocol ptl = new GroupInfoProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pageindex", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			Logger.d("GroupInfoProtocolCoder", "jsonObject = " + jsonObject.toString());
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
			ServerInfo temp = new ServerInfo("get_NewsZiXun", ProtocolConstant.SERVER_FW_AUTH, "get_NewsZiXun", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqNewsZiXunBanner(String userID, INetReceiveListener listener, String msgFlag) {
		BannerInfoProtocol ptl = new BannerInfoProtocol(msgFlag);
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
			ServerInfo temp = new ServerInfo("get_Banner", ProtocolConstant.SERVER_FW_AUTH, "get_Banner", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqRealTimaZiXun(String userID, String pageIndex, INetReceiveListener listener, String msgFlag) {
		RealTimeZiXunProtocol ptl = new RealTimeZiXunProtocol(msgFlag);
		String xy = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject();
			jsonObject.put("user_id", userID);
			jsonObject.put("pageindex", pageIndex);
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			Logger.d("RealTimeZiXunProtocolCoder", "jsonObject = " + jsonObject.toString());
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
			ServerInfo temp = new ServerInfo("get_RealNewsZiXun", ProtocolConstant.SERVER_FW_AUTH, "get_RealNewsZiXun", ip, false, 9801);
			temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
			connInfo.setServerInfo(temp);
			NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
			msg.sendByGet = true;
			NetMsgSenderProxy.getInstance().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void reqNewsTopBarList(String ID, INetReceiveListener listener, String msgFlag) {
		NewsTopBarListProtocol ptl = new NewsTopBarListProtocol(msgFlag);
		String ip = "";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?id=" + ID);
		ServerInfo temp = new ServerInfo("get_NewsTopBarList", ProtocolConstant.SERVER_FW_AUTH, "get_NewsTopBarList", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?id=" + ID);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void reqNewsDataList(String sort_id, String pageIndex, String pageSize, INetReceiveListener listener, String msgFlag) {
		NewsDataListProtocol ptl = new NewsDataListProtocol(msgFlag);
		String ip = "http://api.guxiansen.test1.romawaysz.com/";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?sort_id=" + sort_id + "&page=" + pageIndex + "&pageSize=" + pageSize);
		ServerInfo temp = new ServerInfo("get_NewsDataList", ProtocolConstant.SERVER_FW_AUTH, "get_NewsDataList", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?sort_id=" + sort_id + "&page=" + pageIndex + "&pageSize=" + pageSize);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void reqNewsDataList(String sort_id, String pageIndex, String pageSize, String userID, INetReceiveListener listener, String msgFlag) {
		NewsDataListProtocol ptl = new NewsDataListProtocol(msgFlag);
		ptl.req_sort_id = sort_id;
		String ip = "http://api.guxiansen.test1.romawaysz.com/";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?sort_id=" + sort_id + "&page=" + pageIndex + "&pageSize=" + pageSize + "&user_id=" + userID);
		ServerInfo temp = new ServerInfo("get_NewsDataList", ProtocolConstant.SERVER_FW_AUTH, "get_NewsDataList", ip, false, 9801);
		temp.setSubFunUrl(ptl.subFunUrl + "?sort_id=" + sort_id + "&page=" + pageIndex + "&pageSize=" + pageSize + "&user_id=" + userID);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

}
