package roma.romaway.commons.android.tougu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.Configs;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.netreq.WoReq;
import com.romaway.android.phone.share.RomaSharePopMenu;
import com.romaway.android.phone.utils.BitmapUtils;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.android.phone.utils.JYStatusUtil.OnLoginAccountListener;
import com.romaway.android.phone.utils.StockCacheInfo;
import com.romaway.android.phone.utils.YBHelper;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.protocol.wo.WoUserInfoSelectProtocol;
import com.romaway.commons.db.PersistentCookieStore;
import com.romaway.commons.json.JSONUtils;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.commons.utils.NetUtils;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import roma.romaway.commons.android.webkit.JavascriptInterface;
import roma.romaway.commons.android.webkit.RomaWebView;
import roma.romaway.commons.android.webkit.WebkitActivity;
import roma.romaway.commons.android.webkit.WebkitSherlockFragment;

public class TouguJavascripInterface extends JavascriptInterface implements TouguInterface{

	private final static String TAG = "TouguJavascripInterface";
	private Activity webkitActivity;
	private WebView webView;
	
	private Map<String, String> backMethodNameMap = new HashMap<String, String>();

	public TouguJavascripInterface(Activity activity, WebView webView) {
		super(null);
		// TODO Auto-generated constructor stub
		
		this.webkitActivity = activity;
		this.webView = webView;
	}
	
	public TouguJavascripInterface(WebkitSherlockFragment baseFragment) {
		super(baseFragment);
		// TODO Auto-generated constructor stub
	}

	private String urlHeader;
	public void setSwitchUrlHeader(String urlHeader) {
		this.urlHeader = urlHeader;
	}

	private int switchTitleVisibility = View.GONE;
	public void setSwitchTitleVisibility(int switchTitleVisibility) {
		this.switchTitleVisibility = switchTitleVisibility;
	}
	private int webViewBackgroundColor = 0xffffffff;
	public void setSwitchWebViewBackgroundColor(int color) {
		webViewBackgroundColor = color;
	}

	@Override
	@android.webkit.JavascriptInterface
	public void switchWebView(String url, int hasRefresh, int direction) {
		// TODO Auto-generated method stub
		//super.switchWebView(url, hasRefresh, direction);
		
		//启动新的页面
		Intent intent = new Intent();
		intent.putExtra("key_h5url",urlHeader + url);
		intent.putExtra("key_titleVisibility", switchTitleVisibility);
		intent.putExtra("webViewBackgroundColor", webViewBackgroundColor);
        intent.setClass(webkitActivity, TouguShowH5Activity.class);
        webkitActivity.startActivity(intent);
	}
	
	@Override
	@android.webkit.JavascriptInterface
	public void showKeyBoardTG(String backMethodName, String stockCodes) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口] 启动键盘精灵接口 showKeyBoardTG");
		
		registerBackMethodName("showKeyBoardTG", backMethodName);
		
		final Bundle bundle= new Bundle();
		bundle.putString(BundleKeyValue.HQ_STOCKCODE, stockCodes);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				KActivityMgr.switchWindow((ISubTabView) webkitActivity, KdsSearchActivityForTG.class, bundle, false);
			}
		}).start();
	}

	@Override
	@android.webkit.JavascriptInterface
	public void getLoginStateTG(final String backMethodName) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口] 获取交易登录状态 getLoginStateTG");
		
		registerBackMethodName("getLoginStateTG", backMethodName);
		
		PersistentCookieStore cookieStore = new PersistentCookieStore(webkitActivity);
		final String userId = cookieStore.getValue("user_id");
		
		Map<String, String> map = new HashMap<String, String>();
		if (RomaUserAccount.isGuest()){
			map.put("userId", userId);
			map.put("fundId", "null");
		    map.put("state", "0");
		    commit(backMethodName, map);
		}else{
			//非浏览用户
			//判断显示交易登录信息
			final JYStatusUtil mJYStatusUtil = new JYStatusUtil(webkitActivity, true);
			//获取交易登录的状态
	        mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener(){
		        @Override
		        public void onLoginAccount(int status,String loginAccount) {
		            // TODO Auto-generated method stub
		        	Map<String, String> map = new HashMap<String, String>();
		        	
		            if( status == JYStatusUtil.JY_LOGIN_STATUS_OK){
		            	Logger.d(TAG, "JYStatusUtil.JY_LOGIN_STATUS_OK ");
		            	
		            	map.put("userId", userId);
		            	map.put("fundId", loginAccount);
					    map.put("state", "2");
						
		            }else{
		            	Logger.d(TAG, "JYStatusUtil.JY_LOGIN_STATUS_OK NO!");
		            	
		            	map.put("userId", userId);
		            	map.put("fundId", "null");
					    map.put("state", "1");
		            }
		            
		            commit(backMethodName, map);
		        }
	        });  
		}
	}

	@Override
	@android.webkit.JavascriptInterface
	public void gotoStockDetailTG(String backMethodName, String stockCode, String marketId) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口] 启动个股详情界面 gotoStockDetailTG");
		
		registerBackMethodName("gotoStockDetailTG", backMethodName);
		
		//跳转到行情分时K线界面
		String[][] hqData = new String[][]{{"",stockCode, marketId, ""}};
		Bundle bundle = new Bundle();
		bundle.putInt("HQ_POSITION", 0);
		
		StockCacheInfo.clearCacheList();
		StockCacheInfo.saveListToCache(hqData, new int[]{0,1,2,3});
		KActivityMgr.switchWindow((ISubTabView)webkitActivity, 
                "kds.szkingdom.android.phone.activity.hq.HQStockDataInfoFragmentActivity", 
                bundle, false);
				
	}

	@Override
	@android.webkit.JavascriptInterface
	public void ShowRegisterView(String backMethodName) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口] 启动用户注册登录界面 ShowRegisterView");
		
		registerBackMethodName("ShowRegisterView", backMethodName);

		KActivityMgr.switchWindow((ISubTabView) webkitActivity,
                "kds.szkingdom.modeinit.android.activity.login.UserLoginFragmentActivity",
                null, false);
	}

	@Override
	@android.webkit.JavascriptInterface
	public void gotoTradeLoginViewTG(String backMethodName) {
		Logger.i(TAG, "[js调用原生接口] 实盘/模拟盘登录 投顾的js代码调用原生的登录界面 gotoTradeLoginViewTG");
		
		registerBackMethodName("gotoTradeLoginViewTG", backMethodName);

		gotoTradeLoginView((ISubTabView) webkitActivity, "ptjy");
	}

	@android.webkit.JavascriptInterface
	public void gotoRZRQTradeLoginViewTG(String backMethodName){
		Logger.i(TAG, "[js调用原生接口] 实盘/模拟盘登录 投顾的js代码调用原生的融资融券登录界面 gotoRZRQTradeLoginViewTG");

		registerBackMethodName("gotoRZRQTradeLoginViewTG", backMethodName);

		gotoTradeLoginView((ISubTabView) webkitActivity, "rzrq");
	}

	// 私有公共方法, 非针对JS调用的方法:
	public static void gotoTradeLoginView(ISubTabView activity, String tradeType){
		if("rzrq".equalsIgnoreCase(tradeType) || "rzrq_risk".equalsIgnoreCase(tradeType)) {
			Bundle bundle = new Bundle();
			bundle.putString("url", Res.getString(R.string.mode_rzrq_ptjy_login));
			bundle.putInt("hasRefresh", 4);
			if("rzrq_risk".equalsIgnoreCase(tradeType)){
				bundle.putString("InputContentKey", "Risk_Level_Query");
			}

			KActivityMgr.switchWindow(
					activity,
					"kds.szkingdom.jiaoyi.android.phone.activity.jy.RzrqModeActivity",
					bundle, false);
		}else{
			Bundle bundle = new Bundle();
			bundle.putString("url", Res.getString(R.string.mode_jy_ptjy_login));
			bundle.putInt("hasRefresh", 4);
			if (PTJY_CHECK_ACTION_LIMIT.equalsIgnoreCase(tradeType)){
				bundle.putString("InputContentKey", "KDS_STOCK_QUIZ");		// 股指竞猜
			}else if("ptjy_risk".equalsIgnoreCase(tradeType) || (!TextUtils.isEmpty(tradeType) && tradeType.startsWith("KDS_TICKET_NO"))){
//				String funKey = null;
//				try {
//					if (activity.getCurrentAct() != null && activity.getCurrentAct().getIntent() != null)
//					funKey = activity.getCurrentAct().getIntent().getStringExtra("functionCode");
//				}catch (Exception e){
//					Logger.e("TAG", e.getMessage());
//				}
//				funKey = TextUtils.isEmpty(funKey) ? "KDS_TICKET_NO_URL" : funKey;
				bundle.putString("InputContentKey", "KDS_TICKET_NO_URL");

			}else if("KDS_STOCK_SYSNC".equalsIgnoreCase(tradeType)	// 自选股云同步
					|| "ROMA_SM_YJDX".equalsIgnoreCase(tradeType)    // 一键打新(新股申购新)
					|| "KDS_SM_XGSG".equalsIgnoreCase(tradeType)    // 新股申购
					|| "KDS_SM_YJSG".equalsIgnoreCase(tradeType)	// 一键申购
					|| (!TextUtils.isEmpty(tradeType) && tradeType.startsWith("KDS_PHONE_TICKET"))		// 我的Level 2
					|| (!TextUtils.isEmpty(tradeType) && tradeType.startsWith("KDS_TICKET"))){			// 产品购买记录
				bundle.putString("InputContentKey", tradeType);
			}
			KActivityMgr.switchWindow(activity,
					"kds.szkingdom.jiaoyi.android.phone.activity.jy.JiaoYiModeActivity",
					bundle, false);
		}
	}

	/**
	 * 第三方Web页面JS调用原生的登录界面
	 * @param loginType			登录类型: 普通交易/ptjy, 融资融券/rzrq, 手机号/PHONE_NUM, 普通交易风险等级/ptjy_risk;
	 * @param backMethodName	登录成功后回调JS方法名
     */
	@android.webkit.JavascriptInterface
	public void gotoLoginViewAsLoginType(final String loginType, final String backMethodName){
		TouguShowH5Activity touguShowH5Activity = (TouguShowH5Activity) webkitActivity;
		if (JYStatusUtil.JY_LOGIN_TYPE.equalsIgnoreCase(loginType) || PTJY_CHECK_ACTION_LIMIT.equalsIgnoreCase(loginType)
				|| JYStatusUtil.JY_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType)){
			if(PTJY_CHECK_ACTION_LIMIT.equalsIgnoreCase(loginType))
				registerBackMethodName("getTradeAccountAndCheckLimit", backMethodName);
			else if(JYStatusUtil.JY_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType))
				registerBackMethodName("goto_risk_view", backMethodName);
			else
				registerBackMethodName("gotoTradeLoginViewTG", backMethodName);
			// 是否已登录手机号(非游客):
			if (!RomaUserAccount.isGuest()) {
				touguShowH5Activity.handleLoginedAction(loginType, new TouguShowH5Activity.HandleUnLoginListener() {
					@Override
					public void onHandleUnLogin() {
						gotoTradeLoginView((ISubTabView) webkitActivity, loginType);
					}
				});
			}else {
				Bundle bundle = new Bundle();
				if (PTJY_CHECK_ACTION_LIMIT.equalsIgnoreCase(loginType))
					bundle.putString("from", "third_action_ptjy");
				else if(JYStatusUtil.JY_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType))
					bundle.putString("from", "third_web_ptjy_risk");
				else
					bundle.putString("from", "third_web_ptjy");
				KActivityMgr.switchWindow((ISubTabView) webkitActivity,
						"kds.szkingdom.modeinit.android.activity.login.UserLoginFragmentActivity",
						bundle, false);
			}
		}else if(JYStatusUtil.RZRQ_LOGIN_TYPE.equalsIgnoreCase(loginType) || JYStatusUtil.RZRQ_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType)){
			if(JYStatusUtil.RZRQ_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType)){
				registerBackMethodName("goto_risk_view", backMethodName);
			}else {
				registerBackMethodName("gotoRZRQTradeLoginViewTG", backMethodName);
			}
			// 是否已登录手机号(非游客):
			if (!RomaUserAccount.isGuest()) {
				touguShowH5Activity.handleLoginedAction(loginType, new TouguShowH5Activity.HandleUnLoginListener() {
					@Override
					public void onHandleUnLogin() {
						gotoRZRQTradeLoginViewTG(backMethodName);
					}
				});
			}else {
				Bundle bundle = new Bundle();
				bundle.putString("from", "third_web_rzrq");
				if(JYStatusUtil.RZRQ_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType)){
					bundle.putString("from", "third_web_rzrq_risk");
				}
				KActivityMgr.switchWindow((ISubTabView) webkitActivity,
						"kds.szkingdom.modeinit.android.activity.login.UserLoginFragmentActivity",
						bundle, false);
			}
		}else if(PHONE_NUM.equalsIgnoreCase(loginType)){
			if (!RomaUserAccount.isGuest()){
				registerBackMethodName("ShowRegisterView", backMethodName);
				touguShowH5Activity.handleLoginedAction(TouguJavascripInterface.PHONE_NUM, null);
			}else {
				ShowRegisterView(backMethodName);
			}
		}
	}

	@SuppressLint("NewApi")
	@Override
	@android.webkit.JavascriptInterface
	public void selfserviceAccount(String backMethodName) {
		Logger.i(TAG, "[js调用原生接口] (实盘)开户js接口 投顾的js代码调用原生开户接口");
		
		registerBackMethodName("selfserviceAccount", backMethodName);
		
		if(Res.getString(R.string.kcongigs_selfserviceAccounttype).equals("1")){
			ComponentName cn = new 
				ComponentName("dongzheng.szkingdom.android.phone",
						"com.thinkive.mobile.account.activitys.HomeActivity") ;
						Intent intent = new Intent() ;
						intent.setComponent(cn) ;
						webkitActivity.startActivity(intent);
		}else{
			String downLoadUrl = Res.getString(R.string.kconfigs_self_service_account_download_url);
			String appPackageName = Res.getString(R.string.kconfigs_self_service_account_package_namel);
			// 启动开户App, 如未安装则下载(下载地址，以http://开头)
			YBHelper.launchOrDownApp(webkitActivity, appPackageName, downLoadUrl);
		}
	}

	@SuppressLint("NewApi")
	@Override
	@android.webkit.JavascriptInterface
	public void gotoTradePlaceAnOrderTG(String backMethodName, String stockCode, String flag) {
		// TODO Auto-generated method stub
		
		Logger.i(TAG, "[js调用原生接口] (实盘)建仓js接口 gotoTradePlaceAnOrderTG");
		
		registerBackMethodName("gotoTradePlaceAnOrderTG", backMethodName);
		
		String funKey = "KDS_Buy";
		if(flag.equals("0"))//买入
			funKey = "KDS_Buy";
		else if(flag.equals("1"))//卖出
			funKey = "KDS_Sell";
		else if(flag.equals("2"))//撤单
			funKey = "KDS_CheDan";
		
		try {
			KActivityMgr.shortcutClickSwitch(webkitActivity,
					Intent.parseUri(KActivityMgr.getIntentString(funKey, stockCode), 0));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	@Override
	@android.webkit.JavascriptInterface
	public void gotoTradePositionViewTG(String backMethodName) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口] (实盘)持仓查询js接口 gotoTradePositionViewTG");
		
		registerBackMethodName("gotoTradePositionViewTG", backMethodName);
		
		String intentStr = KActivityMgr.getIntentString("KDS_SM_CCQuery", null);
		try {
			Intent intent = Intent.parseUri(intentStr, 0);
			KActivityMgr.shortcutClickSwitch(webkitActivity, intent);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@android.webkit.JavascriptInterface
	public void showShareTG(String backMethodName, String title, String url, String summary) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口] 分享接口 showShareTG title:"+title+",url:"+url+",summary:"+summary);
		
		registerBackMethodName("showShareTG", backMethodName);
		
		ServerInfo server = ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_NEWS);
		if (server == null ) {
			RomaToast.showMessage(webkitActivity, "获取链接失败，请稍后再试！");
			return;
		}
		RomaSharePopMenu share = new RomaSharePopMenu(webkitActivity);
		share.setTitle(title);
		share.setUrl(url);
		if(webView != null)
			share.showAtLocation(webView);
	}

	@Override
	@android.webkit.JavascriptInterface
	public void getUserInforTG(String backMethodName, String userId) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口] 获取用户信息 getUserInforTG");
		
		registerBackMethodName("getUserInforTG", backMethodName);
		reqUserInfo(backMethodName);
	}

	/**
	 * 提交给js数据
	 * @param backMethodName
	 * @param map
	 */
	@android.webkit.JavascriptInterface
	public void commit(String backMethodName, Map<String, String> map){

		String json = JSONUtils.toJson(backMethodName, map);
	    Logger.d(TAG, "[js调用原生接口] to json data，jsonBuilder : " + json);
		//在没网络的情况下是不会传递数据给js
	    if(!NetUtils.isNetworkConnected(webkitActivity))
			return;

	    if(webView != null && !StringUtils.isEmpty(backMethodName)){
	    	try{
			 webView.loadUrl(
        		"javascript:"+backMethodName+"("+json+")");

	    	}catch(Exception e){
	    		e.printStackTrace();
	    		 Logger.d(TAG, "[js调用原生接口] 异常！");
	    	}
	    }
	}

	/**
	 * 提交给js数据
	 * @param backMethodName
	 * @param map
	 */
	@android.webkit.JavascriptInterface
	public void commitStrData(final String backMethodName, Map<String, String> map){
		final String json = JSONUtils.toJson(backMethodName, map);
		Logger.d(TAG, "[js调用原生接口] to json data，jsonBuilder : " + json);
		//在没网络的情况下是不会传递数据给js
		if(!NetUtils.isNetworkConnected(webkitActivity))
			return;

		if(webView != null && !StringUtils.isEmpty(backMethodName)){
			try{
				Logger.e("TAG", json);
				Logger.e("TAG", "javascript:"+backMethodName+"('"+json+"')");
				webView.loadUrl(
								"javascript:"+backMethodName+"('"+json+"')");
			}catch(Exception e){
				e.printStackTrace();
				Logger.d(TAG, "[js调用原生接口] 异常！");
			}
		}
	}

	/**
	 * 注册js接口的回调方法名
	 * @param toJsInterfaceName
	 * @param jsBackMethodName
	 */
	@android.webkit.JavascriptInterface
	private void registerBackMethodName(String toJsInterfaceName, String jsBackMethodName){
		
		//Logger.d(TAG, "[js调用原生接口]  jsBackMethodName:"+jsBackMethodName);
		
		backMethodNameMap.put(toJsInterfaceName, jsBackMethodName);
	}
	
	/**
	 * 获取回调方法名称
	 * @param key 原生提供给js的调用接口字符串
	 * @return
	 */
	@android.webkit.JavascriptInterface
	public String getBackMethodName(String key){
		String backMethodName = null;
		
		backMethodName = backMethodNameMap.get(key);
		
		return backMethodName;
	}

	@Override
	@android.webkit.JavascriptInterface
	public void useridLevelUp(String backMethodName, String signUpFlag,
			String userId) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口]  提升用户等级接口 useridLevelUp");
		
		registerBackMethodName("useridLevelUp", backMethodName);
		
		Map<String, String> map = new HashMap<String, String>();
    	map.put("returnCode", "0");
        
        commit(backMethodName, map);
	}

	/**
	 * 关闭当前Web页面承载的Activity页面
	 */
	@Override
	@android.webkit.JavascriptInterface
	public void closeCurrentWindow() {
		// TODO Auto-generated method stub
		
		Logger.i(TAG, "[js调用原生接口]  closeCurrentWindow");
		
		webkitActivity.finish();
	}
	
	@Override
	@android.webkit.JavascriptInterface
	public void openNewWindow(String linkUrl) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口]  openNewWindow linkUrl:"+linkUrl);
		
		//启动新的页面
		final Intent intent = new Intent(); 
		intent.putExtra("key_h5url", linkUrl);
        intent.setClass(webkitActivity, TouguShowH5Activity.class);  
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				webkitActivity.startActivity(intent);
			}
		}).start();
	}

	private void reqUserInfo(String name){
		PersistentCookieStore cookieStore = new PersistentCookieStore(webkitActivity);
		int userId = NumberUtils.toInt(cookieStore.getValue("user_id"));
		WoReq.req_userInfo_select(userId, new UserInfoListener(name, webkitActivity), "user_info_select");
	}
	
	private class UserInfoListener extends UINetReceiveListener{
		
		String backMethodName;

		public UserInfoListener(String name, Activity activity) {
			super(activity);
			backMethodName = name;
		}
		
		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			// TODO Auto-generated method stub
			super.onSuccess(msg, ptl);
//			WoUserInfoSelectProtocol userInfo = (WoUserInfoSelectProtocol) ptl;
//			if (userInfo.resp_status == 0 && userInfo.resp_returnCode == 0) {
//
//				Map<String, String> map = new HashMap<String, String>();
//		    	map.put("userId", userInfo.resp_userId);
//			    map.put("name",userInfo.resp_name);
//			    map.put("avatar", userInfo.resp_avatar);
//			    map.put("mobileId", userInfo.resp_mobileId);
//			    map.put("fundId", userInfo.resp_fundId);
//			    map.put("level", userInfo.resp_level);
//
//		        Logger.i(TAG, "[js调用原生接口] 通过网络请求得到的用户信息！");
//
//		        commit(backMethodName, map);
//			}
		}
		
		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			// TODO Auto-generated method stub
			super.onShowStatus(status, msg);
		}
		
	}

	@Override
	@android.webkit.JavascriptInterface
	public void showToast(String backMethodName, String message) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口]  showToast message:"+message);
		
		registerBackMethodName("showToast", backMethodName);
		if(!StringUtils.isEmpty(message))
			RomaToast.showMessage(webkitActivity, message);
	}

	@Override
	@android.webkit.JavascriptInterface
	public void pickImage(String backMethodName) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口]  pickImage");
		
		registerBackMethodName("pickImage", backMethodName);
		
		PhotoChooser mPhotoChooser = new PhotoChooser(webkitActivity);
		mPhotoChooser.showPhotoChooser(webkitActivity.findViewById(R.id.root));
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openPAY() {
		Logger.d("ROMA_Native", "调起支付");
	}

	@Override
	public void openPDF() {
		super.openPDF();
		Logger.d("ROMA_Native", "调起支付");
	}

	@android.webkit.JavascriptInterface
	public void youwenTalkingTimeOut(String backMethodName) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "[js调用原生接口]  youwenTalkingTimeOut");

		Intent mIntent = new Intent("action.youwen.talkingTimeOut");
		// 发送广播
		webkitActivity.sendBroadcast(mIntent);
	}

	public void youWenTalkingTimeOut() {
		youwenTalkingTimeOut("");
	}

	/**
	 * 调用建投优问 视频开户SDK界面
	 *
	 * @param IDNumber
	 */
	@android.webkit.JavascriptInterface
	public void openAccountVideoAuth(String IDNumber) {
		Log.d("openAccountVideoAuth", "openAccountVideoAuth IDNumber: "+ IDNumber);

		Intent intent = new Intent();
//        PackageManager packageManager = getPackageManager();
//        intent = packageManager.getLaunchIntentForPackage(packageName);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
//                | Intent.FLAG_ACTIVITY_CLEAR_TOP);

		Uri mUri = Uri.parse("zxjt108://csc108.app/params?idno="+IDNumber+"&fromType=EmbedLib");
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(mUri);
		webkitActivity.startActivity(intent);

//		Toast.makeText(webkitActivity, "openAccountVideoAuth IDNumber: "+ IDNumber, Toast.LENGTH_LONG).show();
	}

	/**
	 * 调用第三方分享, 微信朋友/朋友圈:
	 * @param title			分享标题
	 * @param shareUrl		分享地址
	 */
	@android.webkit.JavascriptInterface
	public void shareToWechat(String title, String shareUrl) {
		Logger.d("shareToWechat", "shareToWechat shareUrl: " + shareUrl);
		shareToWechat(title, shareUrl, "");
	}
	
	@Override
	@android.webkit.JavascriptInterface
	public void shareToWechat(String title, String shareUrl, String summary) {
		shareToWechat(title, shareUrl, summary, null);
	}

	@android.webkit.JavascriptInterface
	public void shareToWechat(final String title, final String shareUrl, final String summary, final String base64Icon) {
		Logger.d("shareToWechat", "shareToWechat shareUrl: " + shareUrl);
		webkitActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				RomaSharePopMenu share = new RomaSharePopMenu(webkitActivity);
				share.setTitle(title);
				share.setUrl(shareUrl);
				share.setDescription(summary);
				if (!TextUtils.isEmpty(base64Icon)){
					Bitmap thumbImage = BitmapUtils.base64ToBitmap(base64Icon, 32);
					share.setThumbImage(thumbImage);
				}
				share.showAtLocation(webView.getRootView());
			}
		});
	}

	@android.webkit.JavascriptInterface
    public void gotoTransferRecord(String id) {
//        Bundle bundle = new Bundle();
//        bundle.putString("GroupID", id);
//        KActivityMgr.switchWindow(
//                (ISubTabView) webkitActivity,
//                TouguAdjustStoreHistoryActivity.class,
//                bundle, false);
    }

	@android.webkit.JavascriptInterface
    public void gotoGroupConfig(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("GroupID", id);
        KActivityMgr.switchWindow(
                (ISubTabView) webkitActivity,
                Res.getString(R.string.Package_TouguDeployPortfolioActivity),
                bundle, false);
    }

	@android.webkit.JavascriptInterface
    public void gotoInfoDetail(String infoID) {
        Logger.d("infoID", "infoID:  " + infoID);
        if (!StringUtils.isEmpty(infoID)) {
            Bundle bundle = new Bundle();
            bundle.putString("KDS_ZX_TITLE_ID", infoID);
            KActivityMgr.switchWindow((ISubTabView) webkitActivity, "kds.szkingdom.zx.android.phone.ZXDetailSherlockFragmentActivity", bundle, false);
        }
    }

	@android.webkit.JavascriptInterface
    public void gotoMoreInfos(String codes) {
        Logger.d("codes", "codes:  " + codes);
        if (!StringUtils.isEmpty(codes)) {
            Bundle bundle = new Bundle();
            bundle.putString("StockCode", codes);
            KActivityMgr.switchWindow((ISubTabView) webkitActivity, "kds.szkingdom.commons.android.tougu.TouguInformationActivity", bundle, false);
        }
    }

	@android.webkit.JavascriptInterface
    public void setTGTotalIncomeRate(String zsyl) {

    }

	@android.webkit.JavascriptInterface
	public void gotoLoginPage() {

	}

	/** 手机号码 */				public final static String PHONE_NUM = "PHONE_NUM";
	/** 当前登录中的普通交易账号 */	public final static String CURRENT_TRADE_ACCOUNT = "CURRENT_TRADE_ACCOUNT";
	/** 当前登录中的融资融券账号 */	public final static String CURRENT_RZRQ_TRADE_ACCOUNT = "CURRENT_RZRQ_TRADE_ACCOUNT";
	/** 最近登录的普通交易账号 */	public final static String RECENT_TRADE_ACCOUNT = "RECENT_TRADE_ACCOUNT";
	/** 最近登录的融资融券账号 */	public final static String RECENT_RZRQ_TRADE_ACCOUNT = "RECENT_RZRQ_TRADE_ACCOUNT";

	/** 客户端版本号 */			public final static String CLIENT_VERSION = "CLIENT_VERSION";
	/** 手机设备号(唯一标识) */	public final static String DEVICE_ID = "DEVICE_ID";

	/** 普通交易登录,校验抢红包活动限制功能Key */	public final static String PTJY_CHECK_ACTION_LIMIT = "PTJY_CHECK_ACTION_LIMIT";

	/**
	 * 获取App提供的用户数据:
	 * @param userDataType		用户数据类型
	 * 		  1. 手机号码:				PHONE_NUM
	 * 		  2. 当前登录中的普通交易账号:	CURRENT_TRADE_ACCOUNT
	 * 		  3. 当前登录中的融资融券账号:	CURRENT_RZRQ_TRADE_ACCOUNT
	 * 		  4. 最近登录的普通交易账号:	RECENT_TRADE_ACCOUNT
	 * 		  5. 最近登录的融资融券账号:	RECENT_RZRQ_TRADE_ACCOUNT
	 * @param backMethodName	回调JS方法名[ func(var userdataJson){}], 此接口该参数仅调用android时CURRENT_TRADE_ACCOUNT时使用, 其他传"";
	 * @return 					对应用户数据类型的用户数据, 类型不存在, 返回"";
	 */
	@android.webkit.JavascriptInterface
	public void getUserData(String userDataType, String backMethodName) {
		Map<String, String> userDataMap = new HashMap<String, String>();
		Logger.d("JavascriptInterface", "getUserData userDataType: " + userDataType);
		if (PHONE_NUM.equalsIgnoreCase(userDataType)) {
			// 手机号码:
			userDataMap.put(PHONE_NUM, RomaUserAccount.getUsername());
			userDataMap.put(DEVICE_ID, SysConfigs.DEVICE_ID);
			userDataMap.put(CLIENT_VERSION, RomaSysConfig.getClientInnerVersion());
		} else if (CURRENT_TRADE_ACCOUNT.equalsIgnoreCase(userDataType)) {
			// 当前登录中的交易账号:
			getCurrentAccountAsTradeType(JYStatusUtil.JY_LOGIN_TYPE, backMethodName);
		} else if (CURRENT_RZRQ_TRADE_ACCOUNT.equalsIgnoreCase(userDataType)) {
			// 当前登录中的融资融券账号:
			getCurrentAccountAsTradeType(JYStatusUtil.RZRQ_LOGIN_TYPE, backMethodName);
		} else if (RECENT_TRADE_ACCOUNT.equalsIgnoreCase(userDataType)) {
			// 最近登录的交易资金账号:
			userDataMap.put(RECENT_TRADE_ACCOUNT, RomaUserAccount.getTradeAccount());
		} else if (RECENT_RZRQ_TRADE_ACCOUNT.equalsIgnoreCase(userDataType)) {
			// 最近登录的融资融券账号:
			userDataMap.put(RECENT_RZRQ_TRADE_ACCOUNT, RomaUserAccount.getRZRQTradeAccount());
		}
		if (!CURRENT_TRADE_ACCOUNT.equalsIgnoreCase(userDataType) && !CURRENT_RZRQ_TRADE_ACCOUNT.equalsIgnoreCase(userDataType)) {
			commit(backMethodName, userDataMap);
		}
	}

	/**
	 * 获取当前登录资金账号(普通交易)
	 * @param callbackMethodName
	 * @return
	 */
	@android.webkit.JavascriptInterface
	public void getCurrentAccountAsTradeType(final String loginType, final String callbackMethodName){
		// 当前登录中的交易账号：
		JYStatusUtil mJYStatusUtil = new JYStatusUtil(webkitActivity, true);
		// 获取交易登录的状态
		mJYStatusUtil.setOnLoginAccountListener(new JYStatusUtil.OnLoginAccountListener() {
			@Override
			public void onLoginAccount(int status, String loginAccount) {
				Map<String, String> paramMap = new HashMap<String, String>();

				if (JYStatusUtil.JY_LOGIN_TYPE.equalsIgnoreCase(loginType))
					paramMap.put(CURRENT_TRADE_ACCOUNT, StringUtils.optString(loginAccount));
				else
					paramMap.put(CURRENT_RZRQ_TRADE_ACCOUNT, StringUtils.optString(loginAccount));

				paramMap.put(DEVICE_ID, SysConfigs.DEVICE_ID);
				paramMap.put(CLIENT_VERSION, RomaSysConfig.getClientInnerVersion());
				// if (status == JYStatusUtil.JY_LOGIN_STATUS_OK) {}
				commit(callbackMethodName, paramMap);
			}
		}, loginType);
	}

	/**
	 * 校验登录资金账号参加活动条件限制:
	 * @param backMethodName		校验结果回传的第三方H5页面的JS回调函数名
	 *                              (回传数据内容: String state, String msg, String tradeAccount)
	 * @param hostUrl				内部H5请求是否已参与抢红包活动接口使用的主机地址
	 */
	@android.webkit.JavascriptInterface
	public void getTradeAccountAndCheckLimit(String backMethodName, String hostUrl){
		// 保存内部H5用的请求抢红包活动接口使用的主机地址到本地存储, 供H5直接调用:
		saveOrUpdateLocalData("QHBHD_HOST_URL", hostUrl);
		gotoLoginViewAsLoginType(TouguJavascripInterface.PTJY_CHECK_ACTION_LIMIT, backMethodName);
	}

	/**
	 * 在二级界面加载股指竞猜校验页面接口:
	 * @param webUrl
	 */
	@Override
	@android.webkit.JavascriptInterface
	public void switchWebViewForResult(String webUrl){
		try {
			// 发送广播,同时刷新主界面
			Intent mIntent = new Intent(
					"action." + webkitActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl");
			mIntent.putExtra("resultUrl", /*myurls[1]*/Configs.getJiaoYiUrl(webkitActivity,
					"/kds519/view/ptjy/home/ptjy_index.html"));
			mIntent.putExtra("resetLoadFlag", true);
			webkitActivity.sendBroadcast(mIntent);

			Bundle bundle = new Bundle();
			bundle.putInt("hasRefresh", 1);
			bundle.putString("JYUrl", webUrl);
			bundle.putString("hostUrl", getLocalData("QHBHD_HOST_URL"));
			bundle.putString("KeyFunType", "KDS_STOCK_QUIZ");
			KActivityMgr.switchWindow(
					(ISubTabView) webkitActivity,
					(Class<? extends Activity>) Class.forName(Res.getString(R.string.jiaoYiLoginActivity)),
					bundle, false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置顶部栏个性化样式
	 * @param funKey		功能Key, 客户端支持个性化功能(个性化右上角或特殊事件处理)
	 *                      "ROMA_TICKET_NO_GMJJ"为显示右上角两个对应功能的图标;
	 *                      "ROMA_PHONE_TICKET_NO_LEVEL2_PDMY"为显示右上角"订单记录"按钮;
	 *                      ""为空时, 则不做特殊处理, 包括右上角有显示情况下则更改为隐藏;
	 * @param barBgColor	顶部栏背景颜色
	 * @param titleColor	顶部栏标题颜色(无标题时传同 barBgColor 一致颜色)
	 * @param paramsJson	扩展参数: {"titleName": "标题名", "iconColor": "#FFFFFF"}
     */
	@android.webkit.JavascriptInterface
	public void setActionBarStyle(final String funKey, final String barBgColor, final String titleColor, final String paramsJson){
		Logger.i(TAG, "[js调用原生接口]  setActionBarStyle(" + funKey + " , " + barBgColor + " , " + titleColor + " , " + paramsJson);
		if (webkitActivity instanceof WebkitActivity) {
			webView.post(new Runnable() {
				@Override
				public void run() {
					try {
						if (!TextUtils.isEmpty(barBgColor))
							webkitActivity.findViewById(R.id.rl_title_layout).setBackgroundColor(Color.parseColor(barBgColor));
						if (!TextUtils.isEmpty(titleColor)) {
							((TextView) webkitActivity.findViewById(R.id.txt_title)).setTextColor(Color.parseColor(titleColor));
							((TextView) webkitActivity.findViewById(R.id.txt_finish)).setTextColor(Color.parseColor(titleColor));
						}
						// 公募基金首页则显示右上角图标:
						if ("ROMA_TICKET_NO_GMJJ".equalsIgnoreCase(funKey)) {
							ImageView rightSVGView1 = (ImageView) webkitActivity.findViewById(R.id.svg_right_icon1);
							ImageView rightSVGView2 = (ImageView) webkitActivity.findViewById(R.id.svg_right_icon2);
							rightSVGView1.setVisibility(View.VISIBLE);
							rightSVGView2.setVisibility(View.VISIBLE);
							rightSVGView1.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									webView.loadUrl("javascript:externalCallSearch()");
								}
							});
							rightSVGView2.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									Intent intent = new Intent();
									intent.putExtra("hasFunction", true);
									Bundle bundle = KActivityMgr.getJiaoYiSwitchBundle("KDS_SM_KFSJJ", Res.getString(R.string.trade_url_kfsjj));
									intent.putExtras(bundle);
									KActivityMgr.switchJiaoYiFun(webkitActivity, intent, bundle);
								}
							});
							webkitActivity.findViewById(R.id.txt_order_record).setVisibility(View.GONE);
						} else if ("ROMA_PHONE_TICKET_NO_LEVEL2_PDMY".equalsIgnoreCase(funKey)) {
							TextView orderRecord = (TextView) webkitActivity.findViewById(R.id.txt_order_record);
							orderRecord.setVisibility(View.VISIBLE);
							orderRecord.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									try {
										KActivityMgr.shortcutClickSwitch(webkitActivity,
												Intent.parseUri(KActivityMgr.getIntentString("KDS_PHONE_TICKET_NO_LEVEL2_PDLIST", null), 0));
									} catch (URISyntaxException e) {
										e.printStackTrace();
									}
								}
							});
							webkitActivity.findViewById(R.id.svg_right_icon1).setVisibility(View.GONE);
							webkitActivity.findViewById(R.id.svg_right_icon2).setVisibility(View.GONE);
						} else {
							webkitActivity.findViewById(R.id.svg_right_icon1).setVisibility(View.INVISIBLE);
							webkitActivity.findViewById(R.id.svg_right_icon2).setVisibility(View.INVISIBLE);
							webkitActivity.findViewById(R.id.txt_order_record).setVisibility(View.GONE);
						}

						// 解析扩展参数:
						if (!TextUtils.isEmpty(paramsJson)) {
							JSONObject jsonObject = new JSONObject(paramsJson);
							String titleName = jsonObject.optString("titleName");
							String iconColor = jsonObject.optString("iconColor");

							if (!TextUtils.isEmpty(titleName)) {
								((WebkitActivity) webkitActivity).setTitle(titleName);
							}
							if (!TextUtils.isEmpty(iconColor)) {
//						((SVGView) webkitActivity.findViewById(R.id.svg_back)).setSVGRenderer(
//								SVGManager.getSVGParserRenderer(webkitActivity, "kds_common_search_btn_svg"), null);
//
//						arrowRightBitmap = ColorUtils.changeImageColor(
//								ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.abs__roma_icon_arrow_right)),
//								Color.parseColor(iconColor));
							}
						}
					} catch (Exception e) {
						Logger.e("TAG", e.getMessage());
					}
				}
			});
		}
	}

	/**
	 * 添加设置指定Url地址的后退方式
	 * @param urlKeys		传递 Url地址, 也可为Url地址的唯一标识(判断方式为是否包含, 且支持以","分割,传递UrlKey数组)
	 * @param backTypes		传递 Url后退类型值 (支持以","分割, 传递 backTypes 数组)
	 * 				"0"为逐步后退(Web历史记录栈), "1"为直接关闭(遇到此地址直接关闭Activity), 未设置过的Url默认以原有的方式(如Web历史记录栈后退方式处理)
	 * 注意事项:
	 *     A. urlKeys 与 backTypes 支持以 "," 分割的类数组格式,但不允许在","前后出现空格, 除非空格属于urlKey的一部分;
	 *     B. 要求 urlKeys 与 backTypes 数组长度一致,否则 urlKeys 中多余键的对应值以"0"补位;
	 * 数据格式为:
	 *    String urlKeys = "#index,#level2,#my-level2";
	 *    String backTypes = "1,1,0";
     */
	@android.webkit.JavascriptInterface
	public void setWebPageBackType(final String urlKeys, final String backTypes){
		((WebkitActivity) webkitActivity).getKdsWebView().post(new Runnable() {
			@Override
			public void run() {
				try {
					Logger.i(TAG, "[js调用原生接口]  setWebPageBackType([" + urlKeys + "] , [" + backTypes + "]");
					Map<String, String> urlBackTypeMaps = ((WebkitActivity) webkitActivity).urlBackTypeMap;
					if (urlBackTypeMaps != null) {
						if (!TextUtils.isEmpty(urlKeys) && !TextUtils.isEmpty(backTypes)){
							String[] urlKeyList = urlKeys.split(",");
							String[] backTypeList = backTypes.split(",");
							String urlKey, backType;

							String cutUrl = ((WebkitActivity) webkitActivity).getKdsWebView().getUrl();
							for (int i = 0; i < urlKeyList.length; i++){
								urlKey = urlKeyList[i];
								if (i < backTypeList.length)
									backType = backTypeList[i];
								else
									backType = "0";

								urlBackTypeMaps.put(urlKey, backType);

								if ("1".equalsIgnoreCase(backType) && cutUrl.contains(urlKey)){
									if (webkitActivity instanceof TouguShowH5Activity && ((TouguShowH5Activity) webkitActivity).closeView != null){
										((TouguShowH5Activity) webkitActivity).closeView.setVisibility(View.GONE);
									}
								}
							}
						}
					}
				}catch (Exception e){
					Logger.e("TAG", e.getMessage());
				}
			}
		});
	}
}
