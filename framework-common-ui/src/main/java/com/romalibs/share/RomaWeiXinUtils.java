package com.romalibs.share;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.romaway.libs.R;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信分享类：支持分享图文链接，图片，文字
 * @author wusq
 */
public class RomaWeiXinUtils {
	private static final String TAG = "KdsShareUtils";
	
	public static String WX_APP_ID = ""; //微信APP_ID
	private static RomaWeiXinUtils instance;
	private static Activity mActivity;
	//第三方APP和微信通信的openapi接口
	public IWXAPI wxAPI;
	//登录成功后回调
	private static LoginListener mLoginListener;

	public IWXAPI getWxAPI() {
		return wxAPI;
	}

	public void setWxAPI(IWXAPI wxAPI) {
		this.wxAPI = wxAPI;
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (mLoginListener != null) {
				mLoginListener.onLoginSuccess("");
			}
		}
	};
	
	public RomaWeiXinUtils(Activity activity){
		mActivity = activity;
		WX_APP_ID=activity.getResources().getString(R.string.wechatShare_FriendAndCircle);
		regToWX(activity);
		//loginWithWX();
		//activity.registerReceiver(receiver, null);
	}
	
	public static RomaWeiXinUtils getInstance(Activity activity){
		if (instance == null) {
			synchronized (RomaWeiXinUtils.class) {
				if (instance == null) {
					instance = new RomaWeiXinUtils(activity);
				}
			}
		}
		return instance;
	}
	
	/**
	 * 将应用的appId注册到微信
	 * @param activity
	 */
	private void regToWX(Activity activity){
		//通过WXAPIFactory获取WXAPI实例
		wxAPI = WXAPIFactory.createWXAPI(activity, WX_APP_ID, true);
		//将应用的appId注册到微信
		wxAPI.registerApp(WX_APP_ID);
	}
	
	/**
	 * 通过微信登录
	 */
	public void loginWithWX(){
		//判断是否安装微信
		if (!wxAPI.isWXAppInstalled()) {
			Toast.makeText(mActivity, "您当前未安装微信！", Toast.LENGTH_LONG).show();
			return;
		}
		SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = /*"gxs"*/String.valueOf(System.currentTimeMillis());
		wxAPI.sendReq(req);
	}
	
	/**
	 * 分享文字
	 * @param text 内容
	 * @param toFriendOrGroup:	1为朋友圈，0为好友
	 */
	public void shareText(String text,int toFriendOrGroup){
		WXTextObject textobj=new WXTextObject();
		textobj.text=text;
		
		WXMediaMessage msg=new WXMediaMessage();
		msg.mediaObject=textobj;
		msg.description=text;
		
		SendMessageToWX.Req req=new SendMessageToWX.Req();
		req.transaction=System.currentTimeMillis()+"";
		req.message=msg;
		if(toFriendOrGroup==SendMessageToWX.Req.WXSceneSession){
			req.scene = SendMessageToWX.Req.WXSceneSession;
		}else if(toFriendOrGroup==SendMessageToWX.Req.WXSceneTimeline){
			req.scene =SendMessageToWX.Req.WXSceneTimeline;
		}
		
		wxAPI.sendReq(req);
	}
	
	/**
	 * 分享图文链接
	 * toFriendOrGroup:	1为朋友圈，0为好友
	 */
	public void shareImageAndUrl(String title,String description,String url,Bitmap bmp,int toFriendOrGroup){
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl=url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title=title;		
		msg.description=description;
		msg.thumbData = Util.bmpToByteArray(bmp, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		if(toFriendOrGroup==SendMessageToWX.Req.WXSceneSession){
			req.scene = SendMessageToWX.Req.WXSceneSession;
		}else if(toFriendOrGroup==SendMessageToWX.Req.WXSceneTimeline){
			req.scene =SendMessageToWX.Req.WXSceneTimeline;
		}
		wxAPI.sendReq(req);
	}
	
	/**
	 * 分享图片 
	 * toFriendOrGroup:	1为朋友圈，0为好友
	 */
	public void shareImage(Bitmap bmp, int toFriendOrGroup){
		WXImageObject imgObj = new WXImageObject(bmp);
		
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
		bmp.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		if(toFriendOrGroup==SendMessageToWX.Req.WXSceneSession){
			req.scene = SendMessageToWX.Req.WXSceneSession;
		}else if(toFriendOrGroup==SendMessageToWX.Req.WXSceneTimeline){
			req.scene =SendMessageToWX.Req.WXSceneTimeline;
		}	
		wxAPI.sendReq(req);
	}
	
	/**
	 * 在activity onDestroy时注销广播接收器
	 */
	public void unRegister(){
		if (mActivity != null && receiver != null) {
			mActivity.unregisterReceiver(receiver);
		}
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
	private String getAppMetaData(Context context) {
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			String msg=appInfo.metaData.getString("WEIXIN_APPKEY");
			return msg; 
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
}
