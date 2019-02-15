/**
 * 
 */
package com.romaway.android.phone.netreq;

import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.protocol.hq.zxjt.HQZXGCloudSysncUploadProtocol;
import com.romaway.common.protocol.service.HQServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.protocol.hq.zxjt.Portfolio;

/**
 * @author duminghui
 * 
 */
public class UserStockReq
{

	public static final void req(String stockCodes, int count, byte pxField,
	        int pxType, int wFrom, String marketIds, INetReceiveListener listener,
	        String msgFlag, boolean reSend, boolean isAutoRefresh)
	{
		// if (count > 0)
		// {
		NetMsg msg = HQServices.hq_zx((short) count, stockCodes, pxField,
		        (byte) pxType, (short) wFrom, marketIds, listener, EMsgLevel.normal,
		        msgFlag, 1, reSend, isAutoRefresh);
		NetMsgSend.sendMsg(msg);
		// }
	}
	
	public static final void reqHGT(String stockCodes, int count, byte pxField,
	        int pxType, String marketIds, INetReceiveListener listener,
	        String msgFlag, boolean reSend, boolean isAutoRefresh)
	{
		NetMsg msg = HQServices.hq_zx_hgt((short) count, stockCodes, pxField,
		        (byte) pxType, (byte) 0, marketIds, listener, EMsgLevel.normal,
		        msgFlag, 1, reSend, isAutoRefresh);
		NetMsgSend.sendMsg(msg);
	}

	// ****************新自选股请求****************************************************
	/**
	 * 根据用户存储的自选股读取数据
	 * @param stockCodes	证券代码(全部)
	 * @param count			证券返回数量
	 * @param pxField		排序字段
	 * @param pxType		排序类型
	 * @param wFrom			开始序号
	 * @param marketid_s	市场ID
	 * @param listener		请求回调接口
	 */
	public static final void req(String stockCodes, int count, byte pxField,
	        int pxType, int wFrom, String marketid_s, long req_bitmap,INetReceiveListener listener, String msgFlag)
	{
		req(stockCodes, count, pxField, pxType, wFrom, marketid_s,req_bitmap,listener, msgFlag, false);
	}
	
	public static final void req(String stockCodes, int count, byte pxField,
	        int pxType, int wFrom, String marketid_s, INetReceiveListener listener, boolean isAutoRefersh)
	{
		req(stockCodes, count, pxField, pxType, wFrom, marketid_s, listener, "hq_zx",
		        false, isAutoRefersh);
	}
	// *****************网络请求****************************************************
	public static final void req(String stockCodes, int count, byte pxField,
	        int pxType, int wFrom, String marketid_s, long req_bitmap,INetReceiveListener listener,
	        String msgFlag, boolean reSend)
	{
		NetMsg msg = HQServices.hq_zx((short) count, stockCodes, pxField,
		        (byte) pxType, (short) wFrom, marketid_s, req_bitmap,listener,
		        EMsgLevel.normal, msgFlag, 1, reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 自选股同步-查询所有自选股
	 */
	public static final void reqZXGTBSelect(String group,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.hq_zxgtb_select(group, appid, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 自选股同步-上传自选股
	 */
	public static final void reqZXGTBUpload(/*String key_type,String key_value,*/String favors,String group,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.hq_zxgtb_upload(/*key_type,key_value,*/favors,group, appid, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 自选股同步-增加自选股
	 */
	public static final void reqZXGTBAdd(/*String key_type,String key_value,*/String favors,String group,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.hq_zxgtb_add(/*key_type,key_value,*/favors,group, appid, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 自选股同步-删除自选股
	 */
	public static final void reqZXGTBDel(/*String key_type,String key_value,*/String favors,String group,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.hq_zxgtb_del(/*key_type,key_value,*/favors,group, appid, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 自选股同步-绑定资金账号
	 */
	public static final void reqZXGTBBind(String deviceId,String bacc,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.hq_zxgtb_bind(deviceId,bacc,  appid, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}
	
	/**
	 * 自选股同步-查询绑定资金账号
	 */
	public static final void reqZXGTBSelectBind(String deviceId,INetReceiveListener listener,
			String msgFlag,boolean reSend){
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.hq_zxgtb_select_bind(deviceId, appid, listener,EMsgLevel.normal,msgFlag,reSend);
		NetMsgSend.sendMsg(msg);
	}

	/**
	 * 自选股云同步-上传自选股
	 */
	public static final void reqZXGCloudSysncUpload(String userId, Portfolio portfolio, INetReceiveListener listener,
											String msgFlag,boolean reSend){
		String userCategory = "";
		String clientName = "TY-MOBILE";
		String clientVersion = RomaSysConfig.getClientVersion(OriginalContext.getContext());
		String appid = SysConfigs.APPID;
		NetMsg msg = HQServices.hq_zxg_cloud_sysnc_upload(userId, userCategory, clientName, clientVersion,
				appid, portfolio, listener,EMsgLevel.normal, msgFlag, reSend);
		NetMsgSend.sendMsg(msg);
	}

	/**
	 * 自选股云同步-下载自选股
	 */
	public static final void reqZXGCloudSysncDownload(String userId,
						INetReceiveListener listener, String msgFlag, boolean reSend) {
		String userCategory = "";
		String clientName = "TY-MOBILE";
		String clientVersion = RomaSysConfig.getClientVersion(OriginalContext.getContext());
		String appid = SysConfigs.APPID;

		NetMsg msg = HQServices.hq_zxg_cloud_sysnc_download(
				userId, userCategory, clientName, clientVersion, appid, listener, EMsgLevel.normal, msgFlag, reSend);
		NetMsgSend.sendMsg(msg);
	}
}
