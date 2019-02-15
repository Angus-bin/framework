package com.romaway.common.protocol.service;

import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.tougu.AddAttentionProtocol;
import com.romaway.common.protocol.tougu.AdjustStoreHistoryProtocol;
import com.romaway.common.protocol.tougu.ChangeStoreProtocol;
import com.romaway.common.protocol.tougu.CreateGroupProtocol;
import com.romaway.common.protocol.tougu.GroupDetailProtocol;
import com.romaway.common.protocol.tougu.GroupInfoDetailProtocol;
import com.romaway.common.protocol.tougu.GroupInfoProtocol;
import com.romaway.common.protocol.tougu.GroupNameSensitiveProtocol;
import com.romaway.common.protocol.tougu.GroupStoreProtocol;
import com.romaway.common.protocol.tougu.IncomePercentTrendProtocol;
import com.romaway.common.protocol.tougu.ModifyGroupInfoProtocol;
import com.romaway.common.protocol.tougu.QueryInitAmountProtocol;
import com.romaway.common.protocol.tougu.entity.StockGroupEntity;
import com.romaway.common.protocol.tougu.entity.StockInfoEntity;
import com.romaway.commons.log.Logger;

import java.util.List;

/**
 * Created by kds on 2016/7/9.
 */
public class TouGuServices {

	private final static String TOUGU_SERVER = "http://111.13.63.1:9800";
//	private final static String TOUGU_SERVER = "http://172.16.210.200:21800";

	/**
	 * 组合列表
	 *
	 * @param userID
	 * @param zhlx
	 * @param page
	 * @param count
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqGroupInfoList(String userID, String zhlx, String page, String count, String appid, INetReceiveListener listener, String msgFlag) {
		Logger.d("PersistentCookieStore", "userID:   " + userID);
		GroupInfoProtocol ptl = new GroupInfoProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?userID=" + userID + "&zhlx=" + zhlx + "&page=" + page + "&count=" + count);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid + "?userID=" + userID + "&zhlx=" + zhlx + "&page=" + page + "&count=" + count);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 关注/取消关注
	 *
	 * @param userID   用户ID
	 * @param groupID  组合ID
	 * @param sfgz     操作类型
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqAddAttention(String userID, String groupID, String sfgz, String appid, INetReceiveListener listener, String msgFlag) {
		AddAttentionProtocol ptl = new AddAttentionProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?userID=" + userID + "&groupID=" + groupID + "&sfgz=" + sfgz);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid + "?userID=" + userID + "&groupID=" + groupID + "&sfgz=" + sfgz);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 获取初始总资产
	 *
	 * @param userID   用户ID
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqQueryInitAmount(String userID, String appid, INetReceiveListener listener, String msgFlag) {
		QueryInitAmountProtocol ptl = new QueryInitAmountProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?userID=" + userID);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid + "?userID=" + userID);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 创建组合
	 * @param userID
	 * @param opter
	 * @param groupName
	 * @param list
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqCreateGroup(String userID, String opter, String groupName, List<StockInfoEntity> list, String appid, INetReceiveListener listener, String msgFlag) {
		CreateGroupProtocol ptl = new CreateGroupProtocol(msgFlag);
		ptl.req_userID = userID;
		ptl.req_opter = opter;
		ptl.req_groupName = groupName;
		ptl.list = list;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 调仓协议
	 * @param userID
	 * @param opter
	 * @param groupID
	 * @param list
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqChangeStore(String userID, String opter, String groupID, List<StockInfoEntity> list, String appid, INetReceiveListener listener, String msgFlag) {
		ChangeStoreProtocol ptl = new ChangeStoreProtocol(msgFlag);
		ptl.req_userID = userID;
		ptl.req_opter = opter;
		ptl.req_groupID = groupID;
		ptl.list = list;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 获取组合持仓
	 *
	 * @param groupID  组合ID
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqGroupStore(String groupID, String appid, INetReceiveListener listener, String msgFlag) {
		GroupStoreProtocol ptl = new GroupStoreProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?groupID=" + groupID);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid + "?groupID=" + groupID);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 修改组合信息
	 *
	 * @param userID    用户ID
	 * @param groupID   组合ID
	 * @param groupName 组合名称
	 * @param groupHide 是否隐藏
	 * @param addvice   主理人建议
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqModifyGroupInfo(String userID, String groupID, String groupName, String groupHide, String addvice, String appid, INetReceiveListener listener, String msgFlag) {
		ModifyGroupInfoProtocol ptl = new ModifyGroupInfoProtocol(msgFlag);
		ptl.req_userID = userID;
		ptl.req_groupId = groupID;
		ptl.req_groupName = groupName;
		ptl.req_groupHide = groupHide;
		ptl.req_addvice = addvice;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 调仓记录
	 *
	 * @param userID
	 * @param groupID
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqAdjustStoryHistory(String userID, String groupID, String page, String count, String appid, INetReceiveListener listener, String msgFlag) {
		AdjustStoreHistoryProtocol ptl = new AdjustStoreHistoryProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?userID=" + userID + "&groupID=" + groupID + "&page=" + page + "&count=" + count);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid + "?userID=" + userID + "&groupID=" + groupID + "&page=" + page + "&count=" + count);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 收益率走势
	 *
	 * @param tjsj
	 * @param groupID
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqIncomePercentTrend(String tjsj, String groupID, String appid, INetReceiveListener listener, String msgFlag) {
		IncomePercentTrendProtocol ptl = new IncomePercentTrendProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?groupID=" + groupID + "&tjsj=" + tjsj);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid + "?groupID=" + groupID + "&tjsj=" + tjsj);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	/**
	 * 组合明细
	 * @param userID
	 * @param groupID
	 * @param appid
	 * @param listener
	 * @param msgFlag
	 */
	public static final void reqGroupDetail(String userID, String groupID, String appid, INetReceiveListener listener, String msgFlag) {
		GroupDetailProtocol ptl = new GroupDetailProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?userID=" + userID + "&groupID=" + groupID);
//		ServerInfo temp = new ServerInfo("tougu", ProtocolConstant.SERVER_FW_AUTH, "tougu", TOUGU_SERVER, false, 9801);
//		temp.setSubFunUrl(ptl.subFunUrl + appid + "?userID=" + userID + "&groupID=" + groupID);
//		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void reqGroupInfoDetail(String groupID, String appid, INetReceiveListener listener, String msgFlag) {
		GroupInfoDetailProtocol ptl = new GroupInfoDetailProtocol(msgFlag);
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid + "?groupID=" + groupID);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public static final void reqGroupNameSensitive(String GroupName, String appid, INetReceiveListener listener, String msgFlag) {
		GroupNameSensitiveProtocol ptl = new GroupNameSensitiveProtocol(msgFlag);
		ptl.req_groupName = GroupName;
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_TOUGU, ptl.subFunUrl + appid);
		NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
		NetMsgSenderProxy.getInstance().send(msg);
	}
}
