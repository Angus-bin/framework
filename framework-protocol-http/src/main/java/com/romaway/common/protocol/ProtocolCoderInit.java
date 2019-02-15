/**
 * 
 */
package com.romaway.common.protocol;

import android.support.annotation.Keep;

import com.romaway.common.protocol.dl.*;
import com.romaway.common.protocol.hq.HQBKProtocol;
import com.romaway.common.protocol.hq.HQBKProtocolCoder;
import com.romaway.common.protocol.hq.HQCXGCXProtocol;
import com.romaway.common.protocol.hq.HQCXGCXProtocolCoder;
import com.romaway.common.protocol.hq.HQDATAProtocol;
import com.romaway.common.protocol.hq.HQDATAProtocolCoder;
import com.romaway.common.protocol.hq.HQFBProtocol;
import com.romaway.common.protocol.hq.HQFBProtocolCoder;
import com.romaway.common.protocol.hq.HQFSProtocol;
import com.romaway.common.protocol.hq.HQFSProtocolCoder;
import com.romaway.common.protocol.hq.HQFSZHProtocol;
import com.romaway.common.protocol.hq.HQFSZHProtocolCoder;
import com.romaway.common.protocol.hq.HQGGTEDProtocol;
import com.romaway.common.protocol.hq.HQGGTEDProtocolCoder;
import com.romaway.common.protocol.hq.HQGgqqProtocol;
import com.romaway.common.protocol.hq.HQGgqqProtocolCoder;
import com.romaway.common.protocol.hq.HQGgqqTDataProtocol;
import com.romaway.common.protocol.hq.HQGgqqTDataProtocolCoder;
import com.romaway.common.protocol.hq.HQKXProtocol;
import com.romaway.common.protocol.hq.HQKXProtocolCoder;
import com.romaway.common.protocol.hq.HQKXZHProtocol;
import com.romaway.common.protocol.hq.HQKXZHProtocolCoder;
import com.romaway.common.protocol.hq.HQLINKProtocol;
import com.romaway.common.protocol.hq.HQLINKProtocolCoder;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol2;
import com.romaway.common.protocol.hq.HQNewCodeListProtocolCoder;
import com.romaway.common.protocol.hq.HQNewCodeListProtocolCoder2;
import com.romaway.common.protocol.hq.HQNewStockProtocol;
import com.romaway.common.protocol.hq.HQNewStockProtocolCoder;
import com.romaway.common.protocol.hq.HQPXProtocol;
import com.romaway.common.protocol.hq.HQPXProtocolCoder;
import com.romaway.common.protocol.hq.HQPXProtocolCoderOld;
import com.romaway.common.protocol.hq.HQPXProtocolOld;
import com.romaway.common.protocol.hq.HQPYProtocol;
import com.romaway.common.protocol.hq.HQPYProtocolCoder;
import com.romaway.common.protocol.hq.HQQHFBProtocol;
import com.romaway.common.protocol.hq.HQQHFBProtocolCoder;
import com.romaway.common.protocol.hq.HQQHFSZHProtocol;
import com.romaway.common.protocol.hq.HQQHFSZHProtocolCoder;
import com.romaway.common.protocol.hq.HQQHKXZHProtocol;
import com.romaway.common.protocol.hq.HQQHKXZHProtocolCoder;
import com.romaway.common.protocol.hq.HQQHPXProtocol;
import com.romaway.common.protocol.hq.HQQHPXProtocolCoder;
import com.romaway.common.protocol.hq.HQZXGTBAddProtocol;
import com.romaway.common.protocol.hq.HQZXGTBAddProtocolCoder;
import com.romaway.common.protocol.hq.HQZXGTBBindProtocol;
import com.romaway.common.protocol.hq.HQZXGTBBindProtocolCoder;
import com.romaway.common.protocol.hq.HQZXGTBDelProtocol;
import com.romaway.common.protocol.hq.HQZXGTBDelProtocolCoder;
import com.romaway.common.protocol.hq.HQZXGTBSelectBindProtocol;
import com.romaway.common.protocol.hq.HQZXGTBSelectBindProtocolCoder;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocol;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocol2;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocolCoder;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocolCoder2;
import com.romaway.common.protocol.hq.HQZXGTBUploadProtocol;
import com.romaway.common.protocol.hq.HQZXGTBUploadProtocolCoder;
import com.romaway.common.protocol.hq.HQZXGYuYinGetCodeProtocol;
import com.romaway.common.protocol.hq.HQZXGYuYinGetCodeProtocolCoder;
import com.romaway.common.protocol.hq.HQZXProtocol;
import com.romaway.common.protocol.hq.HQZXProtocolCoder;
import com.romaway.common.protocol.hq.HQZZZQProtocol;
import com.romaway.common.protocol.hq.HQZZZQProtocolCoder;
import com.romaway.common.protocol.hq.zxjt.HQZXGCloudSysncDownloadProtocol;
import com.romaway.common.protocol.hq.zxjt.HQZXGCloudSysncDownloadProtocolCoder;
import com.romaway.common.protocol.hq.zxjt.HQZXGCloudSysncUploadProtocol;
import com.romaway.common.protocol.hq.zxjt.HQZXGCloudSysncUploadProtocolCoder;
import com.romaway.common.protocol.ping.PINGProtocol;
import com.romaway.common.protocol.ping.PINGProtocolCoder;
import com.romaway.common.protocol.tougu.AddAttentionProtocol;
import com.romaway.common.protocol.tougu.AddAttentionProtocolCoder;
import com.romaway.common.protocol.tougu.AdjustStoreHistoryProtocol;
import com.romaway.common.protocol.tougu.AdjustStoreHistoryProtocolCoder;
import com.romaway.common.protocol.tougu.BannerInfoProtocol;
import com.romaway.common.protocol.tougu.BannerInfoProtocolCoder;
import com.romaway.common.protocol.tougu.ChangeStoreProtocol;
import com.romaway.common.protocol.tougu.ChangeStoreProtocolCoder;
import com.romaway.common.protocol.tougu.CreateGroupProtocol;
import com.romaway.common.protocol.tougu.CreateGroupProtocolCoder;
import com.romaway.common.protocol.tougu.GroupDetailProtocol;
import com.romaway.common.protocol.tougu.GroupDetailProtocolCoder;
import com.romaway.common.protocol.tougu.GroupInfoDetailProtocol;
import com.romaway.common.protocol.tougu.GroupInfoDetailProtocolCoder;
import com.romaway.common.protocol.tougu.GroupInfoProtocol;
import com.romaway.common.protocol.tougu.GroupInfoProtocolCoder;
import com.romaway.common.protocol.tougu.GroupNameSensitiveProtocol;
import com.romaway.common.protocol.tougu.GroupNameSensitiveProtocolCoder;
import com.romaway.common.protocol.tougu.GroupStoreProtocol;
import com.romaway.common.protocol.tougu.GroupStoreProtocolCoder;
import com.romaway.common.protocol.tougu.IncomePercentTrendProtocol;
import com.romaway.common.protocol.tougu.IncomePercentTrendProtocolCoder;
import com.romaway.common.protocol.tougu.ModifyGroupInfoProtocol;
import com.romaway.common.protocol.tougu.ModifyGroupInfoProtocolCoder;
import com.romaway.common.protocol.tougu.QueryInitAmountProtocol;
import com.romaway.common.protocol.tougu.QueryInitAmountProtocolCoder;
import com.romaway.common.protocol.wo.MsgAddCommentProtocol;
import com.romaway.common.protocol.wo.MsgAddCommentProtocolCoder;
import com.romaway.common.protocol.wo.WoFeedbackAddProtocol;
import com.romaway.common.protocol.wo.WoFeedbackAddProtocolCoder;
import com.romaway.common.protocol.wo.WoFeedbackSelectProtocol;
import com.romaway.common.protocol.wo.WoFeedbackSelectProtocolCoder;
import com.romaway.common.protocol.wo.WoPersonalCenterSelectProtocol;
import com.romaway.common.protocol.wo.WoPersonalCenterSelectProtocolCoder;
import com.romaway.common.protocol.wo.WoTaskListProtocol;
import com.romaway.common.protocol.wo.WoTaskListProtocolCoder;
import com.romaway.common.protocol.wo.WoUpdateUserImageProtocol;
import com.romaway.common.protocol.wo.WoUpdateUserImageProtocolCoder;
import com.romaway.common.protocol.wo.WoUpdateUserNameProtocol;
import com.romaway.common.protocol.wo.WoUpdateUserNameProtocolCoder;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocol;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocolCoder;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocolNew;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocolNewCoder;
import com.romaway.common.protocol.wo.WoUserInfoUpdateProtocol;
import com.romaway.common.protocol.wo.WoUserInfoUpdateProtocolCoder;
import com.romaway.common.protocol.wo.WoWxPayDataProtocol;
import com.romaway.common.protocol.wo.WoWxPayDataProtocolCoder;
import com.romaway.common.protocol.yj.InfoCenterCXProtocol;
import com.romaway.common.protocol.yj.InfoCenterCXProtocolCoder;
import com.romaway.common.protocol.yj.InfoContentCXProtocol;
import com.romaway.common.protocol.yj.InfoContentCXProtocolCoder;
import com.romaway.common.protocol.yj.InfoContentClearProtocol;
import com.romaway.common.protocol.yj.InfoContentClearProtocolCoder;
import com.romaway.common.protocol.yj.KDSInfoCenterCXProtocol;
import com.romaway.common.protocol.yj.KDSInfoCenterCXProtocolCoder;
import com.romaway.common.protocol.yj.KDSInfoContentCXProtocol;
import com.romaway.common.protocol.yj.KDSInfoContentCXProtocolCoder;
import com.romaway.common.protocol.yj.YuJingCXProtocol;
import com.romaway.common.protocol.yj.YuJingCXProtocolCoder;
import com.romaway.common.protocol.yj.YuJingSetProtocol;
import com.romaway.common.protocol.yj.YuJingSetProtocolCoder;
import com.romaway.common.protocol.zx.GYShiDianAndNoticeDetailProtocol;
import com.romaway.common.protocol.zx.GYShiDianAndNoticeDetailProtocolCoder;
import com.romaway.common.protocol.zx.GYShiDianAndNoticeListProtocol;
import com.romaway.common.protocol.zx.GYShiDianAndNoticeListProtocolCoder;
import com.romaway.common.protocol.zx.NewsDataListProtocol;
import com.romaway.common.protocol.zx.NewsDataListProtocolCoder;
import com.romaway.common.protocol.zx.NewsTopBarListProtocol;
import com.romaway.common.protocol.zx.NewsTopBarListProtocolCoder;
import com.romaway.common.protocol.zx.RealTimeZiXunProtocol;
import com.romaway.common.protocol.zx.RealTimeZiXunProtocolCoder;
import com.romaway.common.protocol.zx.ZXDetailProtocol;
import com.romaway.common.protocol.zx.ZXDetailProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10CWXXProtocol;
import com.romaway.common.protocol.zx.ZXF10CWXXProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10FHSPProtocol;
import com.romaway.common.protocol.zx.ZXF10FHSPProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10GBQKProtocol;
import com.romaway.common.protocol.zx.ZXF10GBQKProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10GDYJProtocol;
import com.romaway.common.protocol.zx.ZXF10GDYJProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10GSGKProtocol;
import com.romaway.common.protocol.zx.ZXF10GSGKProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10JJCGProtocol;
import com.romaway.common.protocol.zx.ZXF10JJCGProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10LRBProtocol;
import com.romaway.common.protocol.zx.ZXF10LRBProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10XJLLProtocol;
import com.romaway.common.protocol.zx.ZXF10XJLLProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10ZCFZProtocol;
import com.romaway.common.protocol.zx.ZXF10ZCFZProtocolCoder;
import com.romaway.common.protocol.zx.ZXF10ZYGCProtocol;
import com.romaway.common.protocol.zx.ZXF10ZYGCProtocolCoder;
import com.romaway.common.protocol.zx.ZXIPOProtocol;
import com.romaway.common.protocol.zx.ZXIPOProtocolCoder;
import com.romaway.common.protocol.zx.ZXListProtocol;
import com.romaway.common.protocol.zx.ZXListProtocolCoder;
import com.romaway.common.protocol.zx.ZXNewStockProtocol;
import com.romaway.common.protocol.zx.ZXNewStockProtocolCoder;
import com.romaway.common.protocol.zx.ZXXGZXDetailProtocol;
import com.romaway.common.protocol.zx.ZXXGZXDetailProtocolCoder;
import com.romaway.common.protocol.zx.ZXXGZXListProcotol;
import com.romaway.common.protocol.zx.ZXXGZXListProcotolCoder;

/**
 * @author duminghui
 * 
 */
@Keep
public class ProtocolCoderInit {
	public static void init() {
		ProtocolCoderMgr mgr = ProtocolCoderMgr.getInstance();
		mgr.putCoder(InitProtocol.class, new InitProtocolCoder());
		mgr.putCoder(SmsRegisterProtocol.class, new SmsRegisterProtocolCoder());
		mgr.putCoder(LoginProtocol.class, new LoginProtocolCoder());
		mgr.putCoder(LoginForOld2NewProtocol.class,
				new LoginForOld2NewProtocolCoder());
		mgr.putCoder(SmsRegisterProtocolNew.class, new SmsRegisterProtocolNewCoder());
//		mgr.putCoder(LoginProtocolNew.class, new LoginProtocolNewCoder());
		// 行情
		mgr.putCoder(HQFSProtocol.class, new HQFSProtocolCoder());
		mgr.putCoder(HQFBProtocol.class, new HQFBProtocolCoder());
		mgr.putCoder(HQQHFBProtocol.class, new HQQHFBProtocolCoder());
		mgr.putCoder(HQPXProtocol.class, new HQPXProtocolCoder());
		mgr.putCoder(HQZZZQProtocol.class, new HQZZZQProtocolCoder());
		mgr.putCoder(HQQHPXProtocol.class, new HQQHPXProtocolCoder());
		mgr.putCoder(HQZXProtocol.class, new HQZXProtocolCoder());
		mgr.putCoder(HQFSZHProtocol.class, new HQFSZHProtocolCoder());
		mgr.putCoder(HQQHFSZHProtocol.class, new HQQHFSZHProtocolCoder());
		mgr.putCoder(HQPXProtocolOld.class, new HQPXProtocolCoderOld());

		mgr.putCoder(HQDATAProtocol.class, new HQDATAProtocolCoder());
		mgr.putCoder(HQBKProtocol.class, new HQBKProtocolCoder());
		mgr.putCoder(HQLINKProtocol.class, new HQLINKProtocolCoder());
		mgr.putCoder(HQNewCodeListProtocol.class,
				new HQNewCodeListProtocolCoder());
		mgr.putCoder(HQPYProtocol.class, new HQPYProtocolCoder());
		mgr.putCoder(HQKXProtocol.class, new HQKXProtocolCoder());
		mgr.putCoder(HQKXZHProtocol.class, new HQKXZHProtocolCoder());
		mgr.putCoder(HQQHKXZHProtocol.class, new HQQHKXZHProtocolCoder());
		// 自选股同步
		mgr.putCoder(HQZXGTBSelectProtocol.class,
				new HQZXGTBSelectProtocolCoder());
		mgr.putCoder(HQZXGTBUploadProtocol.class,
				new HQZXGTBUploadProtocolCoder());
		mgr.putCoder(HQZXGTBAddProtocol.class, new HQZXGTBAddProtocolCoder());
		mgr.putCoder(HQZXGTBDelProtocol.class, new HQZXGTBDelProtocolCoder());
		mgr.putCoder(HQZXGTBBindProtocol.class, new HQZXGTBBindProtocolCoder());
		mgr.putCoder(HQZXGTBSelectBindProtocol.class,
				new HQZXGTBSelectBindProtocolCoder());
		mgr.putCoder(HQGGTEDProtocol.class, new HQGGTEDProtocolCoder());

		// 自选股云同步
		mgr.putCoder(HQZXGCloudSysncUploadProtocol.class, new HQZXGCloudSysncUploadProtocolCoder());
		mgr.putCoder(HQZXGCloudSysncDownloadProtocol.class, new HQZXGCloudSysncDownloadProtocolCoder());

		// 语音验证
		mgr.putCoder(HQZXGYuYinGetCodeProtocol.class,new HQZXGYuYinGetCodeProtocolCoder());

		// 测速
		mgr.putCoder(PINGProtocol.class, new PINGProtocolCoder());

		mgr.putCoder(WoFeedbackSelectProtocol.class,
				new WoFeedbackSelectProtocolCoder());
//		mgr.putCoder(WoFeedbackAddProtocol.class,
//				new WoFeedbackAddProtocolCoder());
		mgr.putCoder(WoPersonalCenterSelectProtocol.class,
				new WoPersonalCenterSelectProtocolCoder());

		// 资讯
		mgr.putCoder(ZXListProtocol.class, new ZXListProtocolCoder());
		mgr.putCoder(ZXDetailProtocol.class, new ZXDetailProtocolCoder());
		mgr.putCoder(ZXNewStockProtocol.class, new ZXNewStockProtocolCoder());
		mgr.putCoder(ZXF10GSGKProtocol.class, new ZXF10GSGKProtocolCoder());
		mgr.putCoder(ZXF10ZYGCProtocol.class, new ZXF10ZYGCProtocolCoder());
		mgr.putCoder(ZXF10FHSPProtocol.class, new ZXF10FHSPProtocolCoder());
		mgr.putCoder(ZXF10GBQKProtocol.class, new ZXF10GBQKProtocolCoder());
		mgr.putCoder(ZXF10GDYJProtocol.class, new ZXF10GDYJProtocolCoder());
		mgr.putCoder(ZXF10JJCGProtocol.class, new ZXF10JJCGProtocolCoder());
		mgr.putCoder(ZXF10CWXXProtocol.class, new ZXF10CWXXProtocolCoder());
		mgr.putCoder(ZXF10XJLLProtocol.class, new ZXF10XJLLProtocolCoder());
		mgr.putCoder(ZXF10ZCFZProtocol.class, new ZXF10ZCFZProtocolCoder());
		mgr.putCoder(ZXF10LRBProtocol.class, new ZXF10LRBProtocolCoder());
		mgr.putCoder(ZXIPOProtocol.class, new ZXIPOProtocolCoder());
		//国元视点和最新公告
		mgr.putCoder(GYShiDianAndNoticeListProtocol.class, new GYShiDianAndNoticeListProtocolCoder());
		mgr.putCoder(GYShiDianAndNoticeDetailProtocol.class, new GYShiDianAndNoticeDetailProtocolCoder());
		//升级状态信息
		mgr.putCoder(UpgradeStateProtocol.class, new UpgradeStateProtocolCoder());
		//用户信息上传、下载接口
//		mgr.putCoder(WoUserInfoSelectProtocol.class, new WoUserInfoSelectProtocolCoder());
		mgr.putCoder(WoUserInfoUpdateProtocol.class, new WoUserInfoUpdateProtocolCoder());
		
		// 向服务器防松请求新股
		mgr.putCoder(HQNewStockProtocol.class, new HQNewStockProtocolCoder());
		// 预警定制
		mgr.putCoder(YuJingSetProtocol.class, new YuJingSetProtocolCoder());
		// 预警查询
		mgr.putCoder(YuJingCXProtocol.class, new YuJingCXProtocolCoder());
		// 消息中心
		mgr.putCoder(InfoCenterCXProtocol.class, new InfoCenterCXProtocolCoder());
		// 消息中心
		mgr.putCoder(KDSInfoCenterCXProtocol.class, new KDSInfoCenterCXProtocolCoder());
		// 消息内容
		mgr.putCoder(InfoContentCXProtocol.class, new InfoContentCXProtocolCoder());
		// 消息内容
		mgr.putCoder(KDSInfoContentCXProtocol.class, new KDSInfoContentCXProtocolCoder());
		// 消息清空
		mgr.putCoder(InfoContentClearProtocol.class, new InfoContentClearProtocolCoder());
		
		// 个股期权
		mgr.putCoder(HQGgqqProtocol.class, new HQGgqqProtocolCoder());
		mgr.putCoder(HQGgqqTDataProtocol.class, new HQGgqqTDataProtocolCoder());

		//投顾
//		mgr.putCoder(AdjustStoreHistoryProtocol.class, new AdjustStoreHistoryProtocolCoder());
//		mgr.putCoder(GroupInfoProtocol.class, new GroupInfoProtocolCoder());
		mgr.putCoder(AddAttentionProtocol.class, new AddAttentionProtocolCoder());
		mgr.putCoder(GroupStoreProtocol.class, new GroupStoreProtocolCoder());
		mgr.putCoder(ModifyGroupInfoProtocol.class, new ModifyGroupInfoProtocolCoder());
		mgr.putCoder(IncomePercentTrendProtocol.class, new IncomePercentTrendProtocolCoder());
		mgr.putCoder(CreateGroupProtocol.class, new CreateGroupProtocolCoder());
		mgr.putCoder(GroupDetailProtocol.class, new GroupDetailProtocolCoder());
		mgr.putCoder(QueryInitAmountProtocol.class, new QueryInitAmountProtocolCoder());
		mgr.putCoder(ChangeStoreProtocol.class, new ChangeStoreProtocolCoder());
		mgr.putCoder(GroupInfoDetailProtocol.class, new GroupInfoDetailProtocolCoder());
		mgr.putCoder(GroupNameSensitiveProtocol.class, new GroupNameSensitiveProtocolCoder());


		mgr.putCoder(ZXXGZXListProcotol.class, new ZXXGZXListProcotolCoder());
		mgr.putCoder(ZXXGZXDetailProtocol.class, new ZXXGZXDetailProtocolCoder());
		mgr.putCoder(HQCXGCXProtocol.class, new HQCXGCXProtocolCoder());

		// test
		mgr.putCoder(TestProtocol.class, new TestProtocolCoder());

		// 股先森
		mgr.putCoder(SmsRegisterProtocolNewNew.class, new SmsRegisterProtocolNewNewCoder());
		mgr.putCoder(LoginProtocolNew.class, new LoginProtocolNewCoder());
		mgr.putCoder(WoUserInfoSelectProtocol.class, new WoUserInfoSelectProtocolCoder());
		mgr.putCoder(WoUpdateUserNameProtocol.class, new WoUpdateUserNameProtocolCoder());
		mgr.putCoder(WoUpdateUserImageProtocol.class, new WoUpdateUserImageProtocolCoder());
		mgr.putCoder(WoFeedbackAddProtocol.class, new WoFeedbackAddProtocolCoder());
		mgr.putCoder(WxUserInfoProtocol.class,new WxUserInfoProtocolCoder());
		mgr.putCoder(WxUserInfoProtocol2.class,new WxUserInfoProtocolCoder2());
		mgr.putCoder(WxUserInfoProtocol3.class,new WxUserInfoProtocolCoder3());
		mgr.putCoder(WxUserInfoProtocol4.class,new WxUserInfoProtocolCoder4());
		mgr.putCoder(WoHistoryProtocol.class,new WoHistoryProtocolCoder());
		mgr.putCoder(WxSetUserInfoProtocol.class, new WxSetUserInfoProtocolCoder());
		mgr.putCoder(WxBindUserInfoProtocol.class, new WxBindUserInfoProtocolCoder());
		mgr.putCoder(BindPhoneProtocol.class, new BindPhoneProtocolCoder());
		mgr.putCoder(LoginSetTokenProtocol.class, new LoginSetTokenProtocolCoder());
		mgr.putCoder(LoginCleanTokenProtocol.class, new LoginCleanTokenProtocolCoder());
		mgr.putCoder(ShareTimesProtocol.class, new ShareTimesProtocolCoder());
		mgr.putCoder(GetWxPayOrderProtocol.class, new GetWxPayOrderProtocolCoder());
		mgr.putCoder(WoTaskListProtocol.class, new WoTaskListProtocolCoder());
		mgr.putCoder(GroupInfoProtocol.class, new GroupInfoProtocolCoder());
		mgr.putCoder(BannerInfoProtocol.class, new BannerInfoProtocolCoder());
		mgr.putCoder(HomeAdjustStoreHistoryProtocol.class, new HomeAdjustStoreHistoryProtocolCoder());
		mgr.putCoder(HomeTitleDetailProtocol.class, new HomeTitleDetailProtocolCoder());
		mgr.putCoder(AdjustStoreHistoryProtocol.class, new AdjustStoreHistoryProtocolCoder());
		mgr.putCoder(HomeNewsListProtocol.class, new HomeNewsListProtocolCoder());
		mgr.putCoder(HomeScrollAndBannerProtocol.class, new HomeScrollAndBannerProtocolCoder());
		mgr.putCoder(StockDetailProtocol.class, new StockDetailProtocolCoder());
		mgr.putCoder(HoldStockProtocol.class, new HoldStockProtocolCoder());
		mgr.putCoder(StockDetailCommentProtocol.class, new StockDetailCommentProtocolCoder());
		mgr.putCoder(StockDetailCommentPointProtocol.class, new StockDetailCommentPointProtocolCoder());
		mgr.putCoder(StockDetailCommentSendProtocol.class, new StockDetailCommentSendProtocolCoder());
		mgr.putCoder(RealTimeZiXunProtocol.class, new RealTimeZiXunProtocolCoder());
		mgr.putCoder(InitProtocolNew.class, new InitProtocolNewCoder());
		mgr.putCoder(BroadcastProtocol.class, new BroadcastProtocolCoder());
		mgr.putCoder(HomeDaPanProtocol.class, new HomeDaPanProtocolCoder());
		mgr.putCoder(ProductDataProtocol.class, new ProductDataProtocolCoder());
		mgr.putCoder(HomeProductDataProtocol.class, new HomeProductDataProtocolCoder());
		mgr.putCoder(BookProductProtocol.class, new BookProductProtocolCoder());
		mgr.putCoder(ZxczDataProtocol.class, new ZxczDataProtocolCoder());
		mgr.putCoder(BroadcastDetailProtocol.class, new BroadcastDetailProtocolCoder());
		mgr.putCoder(ZtdjDataProtocol.class, new ZtdjDataProtocolCoder());
		mgr.putCoder(BannerDetailProtocol.class, new BannerDetailProtocolCoder());
		mgr.putCoder(TextAdsDetailProtocol.class, new TextAdsDetailProtocolCoder());
		mgr.putCoder(BookListDataProtocol.class, new BookListDataProtocolCoder());
		mgr.putCoder(HistoryProductProtocol.class, new HistoryProductProtocolCoder());
		mgr.putCoder(MsgListDataProtocol.class, new MsgListDataProtocolCoder());
		mgr.putCoder(SignUpProtocol.class, new SignUpProtocolCoder());
		mgr.putCoder(WenjianListProtocol.class, new WenjianListProtocolCoder());
		mgr.putCoder(QswyZxtcDataProtocol.class, new QswyZxtcDataProtocolCoder());
		mgr.putCoder(StockDetailProtocolNew.class, new StockDetailProtocolCoderNew());
		mgr.putCoder(StockDetailRecommendProtocol.class, new StockDetailRecommendProtocolCoder());
		mgr.putCoder(PushSettingListProtocol.class, new PushSettingListProtocolCoder());
		mgr.putCoder(PushSettingProtocol.class, new PushSettingProtocolCoder());
		mgr.putCoder(StockFeedBackProtocol.class, new StockFeedBackProtocolCoder());
		mgr.putCoder(WenjianHistoryHoldProtocol.class, new WenjianHistoryHoldProtocolCoder());
		mgr.putCoder(HomeTitleProductDataProtocol.class, new HomeTitleProductDataProtocolCoder());
		mgr.putCoder(HomeProductDataProtocolNew.class, new HomeProductDataProtocolNewCoder());
		mgr.putCoder(HomeProductDataListProtocol.class, new HomeProductDataListProtocolCoder());
		mgr.putCoder(HomeBrandDataProtocol.class, new HomeBrandDataProtocolCoder());
		mgr.putCoder(WoWxPayDataProtocol.class, new WoWxPayDataProtocolCoder());
		mgr.putCoder(MsgReadFlagProtocol.class, new MsgReadFlagProtocolCoder());
		mgr.putCoder(QswyRecommendProtocol.class, new QswyRecommendProtocolCoder());
		mgr.putCoder(VideoDataProtocol.class, new VideoDataProtocolCoder());
		mgr.putCoder(StockNewsListProtocol.class, new StockNewsListProtocolCoder());
		mgr.putCoder(StockShareToSeeProtocol.class, new StockShareToSeeProtocolCoder());
		mgr.putCoder(ZtdjWeekDataProtocol.class, new ZtdjWeekDataProtocolCoder());
		mgr.putCoder(WoUserInfoSelectProtocolNew.class, new WoUserInfoSelectProtocolNewCoder());
		mgr.putCoder(StockDetailNoticeDataProtocol.class, new StockDetailNoticeDataProtocolCoder());
		mgr.putCoder(StockDetailReportDataProtocol.class, new StockDetailReportDataProtocolCoder());
		mgr.putCoder(StockDetailGsgkDataProtocol.class, new StockDetailGsgkDataProtocolCoder());
		mgr.putCoder(StockDetailMainTargetDataprotocol.class, new StockDetailMainTargetDataprotocolCoder());
		mgr.putCoder(StockDetailFundDebtDataProtocol.class, new StockDetailFundDebtDataProtocolCoder());
		mgr.putCoder(StockDetailProfitDataProtocol.class, new StockDetailProfitDataProtocolCoder());
		mgr.putCoder(StockDetailCashDataProtocol.class, new StockDetailCashDataProtocolCoder());
		mgr.putCoder(StockDetailGdrsDataProtocol.class, new StockDetailGdrsDataProtocolCoder());
		mgr.putCoder(StockDetailTenPartnerDateProtocol.class, new StockDetailTenPartnerDateProtocolCoder());
		mgr.putCoder(StockDetailPartnerTenDataProtocol.class, new StockDetailPartnerTenDataProtocolCoder());
		mgr.putCoder(StockDetailHxtcDataProtocol.class, new StockDetailHxtcDataProtocolCoder());
		mgr.putCoder(StockDetailShareBonusDataProtocol.class, new StockDetailShareBonusDataProtocolCoder());
		mgr.putCoder(StockDetailShuoBaDataProtocol.class, new StockDetailShuoBaDataProtocolCoder());
		mgr.putCoder(StockDetailShuoBa2DataProtocol.class, new StockDetailShuoBa2DataProtocolCoder());
		mgr.putCoder(StockDetailShuoBaPointProtocol.class, new StockDetailShuoBaPointProtocolCoder());
		mgr.putCoder(StockShuoBaDetailDataProtocol.class, new StockShuoBaDetailDataProtocolCoder());
		mgr.putCoder(StockShuoBaDetailAddReadProtocol.class, new StockShuoBaDetailAddReadProtocolCoder());
		mgr.putCoder(StockShuoBaDetailHotCommentProtocol.class, new StockShuoBaDetailHotCommentProtocolCoder());
		mgr.putCoder(StockShuoBaDetailCommentProtocol.class, new StockShuoBaDetailCommentProtocolCoder());
		mgr.putCoder(StockShuoBaDetailAddCommentProtocol.class, new StockShuoBaDetailAddCommentProtocolCoder());
		mgr.putCoder(StockShuoBaDetailCommentListProtocol.class, new StockShuoBaDetailCommentListProtocolCoder());
		mgr.putCoder(MsgMainListDataProtocol.class, new MsgMainListDataProtocolCoder());
		mgr.putCoder(MsgAddCommentProtocol.class, new MsgAddCommentProtocolCoder());
		mgr.putCoder(NewsTopBarListProtocol.class, new NewsTopBarListProtocolCoder());
		mgr.putCoder(NewsDataListProtocol.class, new NewsDataListProtocolCoder());
		mgr.putCoder(HQNewCodeListProtocol2.class, new HQNewCodeListProtocolCoder2());
		mgr.putCoder(HQZXGTBSelectProtocol2.class, new HQZXGTBSelectProtocolCoder2());
		mgr.putCoder(ProduceServiceListDataProtocol.class, new ProduceServiceListDataProtocolCoder());
		mgr.putCoder(AIStockDataProtocol.class, new AIStockDataProtocolCoder());
		mgr.putCoder(HomeLimitStockDataProtocol.class, new HomeLimitStockDataProtocolCoder());
	}
}
