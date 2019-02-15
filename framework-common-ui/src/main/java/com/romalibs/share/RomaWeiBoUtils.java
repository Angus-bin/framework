package com.romalibs.share;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
//import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
//import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
//import com.sina.weibo.sdk.api.share.WeiboShareSDK;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//import com.sina.weibo.sdk.auth.WeiboAuthListener;
//import com.sina.weibo.sdk.auth.sso.SsoHandler;
//import com.sina.weibo.sdk.exception.WeiboException;
//import com.sina.weibo.sdk.utils.Utility;
//import com.romaway.libs.R;
//import com.tencent.open.t.Weibo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//import android.media.Image;

/**
 * 新浪微博分享类  支持分享纯文字，文字+连接，图文
 * @author wusq
 */
public class RomaWeiBoUtils {
	private static final String TAG = "RomaWeiBoUtils";
	
	public static String APP_KEY = "";
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
	private static RomaWeiBoUtils mRomaWeiBoUtils;
	private Activity mActivity;
//	public SsoHandler mSsoHandler;
//	private AuthInfo mAuthInfo;
//	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
//	private Oauth2AccessToken mAccessToken;
//	private LoginListener mListener;
//	public IWeiboShareAPI mWeiboShareAPI;
//	private Weibo mWeibo;
    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;
    public static final int SHARE_ALL_IN_ONE = 2;
    private int mShareType = SHARE_CLIENT;


    public RomaWeiBoUtils(Activity activity){
		mActivity = activity;
//		APP_KEY=activity.getResources().getString(R.string.sinaweiboShare);
		initShare();
        mShareType =mActivity.getIntent().getIntExtra(KEY_SHARE_TYPE, SHARE_CLIENT);

//        mAuthInfo = new AuthInfo(activity, APP_KEY, REDIRECT_URL, SCOPE);
//		mSsoHandler = new SsoHandler(activity, mAuthInfo);
	}
	
	public RomaWeiBoUtils getInstance(Activity activity){
		if (mRomaWeiBoUtils == null) {
			synchronized (RomaWeiBoUtils.class) {
				if (mRomaWeiBoUtils == null) {
					mRomaWeiBoUtils = new RomaWeiBoUtils(activity);
				}
			}
		}
		return mRomaWeiBoUtils;
	}
	
	/**
	 * 初始化分享
	 */
	public void initShare(){
		// 将应用注册到微博客户端
//		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, APP_KEY);
//		mWeiboShareAPI.registerApp();
	}
	
	/**
	 * 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
	 */
	public void judgeWeiboIsUsed(){
//		boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
//        int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
//        if(!isInstalledWeibo){
//        	 Toast.makeText(mActivity,  "请先安装微博", Toast.LENGTH_LONG).show();
//        	 return;
//        }
	}
	
	/**
	 * 调用微博登录认证
	 */
	public void login(){
//		mSsoHandler.authorize(new AuthListener());
	}

//    class AuthListener implements WeiboAuthListener {
//        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
//        @Override
//        public void onComplete(Bundle values) {
//            mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从 Bundle 中解析 Token
//            if (mAccessToken.isSessionValid()) {
//                AccessTokenKeeper.writeAccessToken(mActivity, mAccessToken); //保存Token
//            } else {
//                // 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
//                String code = values.getString("code", "");
//                Toast.makeText(mActivity, "签名不正确" + code, Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onWeiboException(WeiboException e) {
//
//        }
//
//        @Override
//        public void onCancel() {
//
//        }
//    }
	
	/**
	 * 分享文字内容到微博
	 * @param text 内容
	 */
//	public void shareText(String text,String image){
//		judgeWeiboIsUsed();
//        login();
//        // 1. 初始化微博的分享消息
//		// 用户可以分享文本、图片、网页、音乐、视频中的一种
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        TextObject textObject = new TextObject();
//        ImageObject imageObject = new ImageObject();
//        imageObject.imagePath = image;
//        textObject.text = text;
//        weiboMessage.mediaObject = textObject;
//        weiboMessage.mediaObject = imageObject;
//        // 2. 初始化从第三方到微博的消息请求
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        if (mShareType == SHARE_CLIENT) {
//            mWeiboShareAPI.sendRequest(mActivity, request);
//        }
//        else if (mShareType == SHARE_ALL_IN_ONE) {
//            AuthInfo authInfo = new AuthInfo(mActivity, APP_KEY,REDIRECT_URL, SCOPE);
//            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity);
//            String token = "";
//            if (accessToken != null) {
//                token = accessToken.getToken();
//            }
//            mWeiboShareAPI.sendRequest(mActivity, request, authInfo, token, new WeiboAuthListener() {
//
//                @Override
//                public void onWeiboException( WeiboException arg0 ) {
//                }
//
//                @Override
//                public void onComplete( Bundle bundle ) {
//                    // TODO Auto-generated method stub
//                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
//                    AccessTokenKeeper.writeAccessToken(mActivity, newToken);
//                    Toast.makeText(mActivity, "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancel() {
//                }
//            });
//        }
//	}
	
	/**
	 * 分享图文链接到微博
	 * @param title 标题
	 * @param descriptiion 描述
	 * @param url 网页链接
	 */
//	public void shareImageAndUrl(String title, String descriptiion, String url,Bitmap bmp){
//		judgeWeiboIsUsed();
//        login();
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        WebpageObject webpageObject = new WebpageObject();
//		webpageObject.identify = Utility.generateGUID();
//		webpageObject.title = title;
//		webpageObject.description = descriptiion;
//		webpageObject.actionUrl = url;
//		webpageObject.setThumbImage(bmp);
//        webpageObject.defaultText = "分享";
//		weiboMessage.mediaObject = webpageObject;
//
//        // 2. 初始化从第三方到微博的消息请求
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        if (mShareType == SHARE_CLIENT) {
//            mWeiboShareAPI.sendRequest(mActivity, request);
//        }
//        else if (mShareType == SHARE_ALL_IN_ONE) {
//            AuthInfo authInfo = new AuthInfo(mActivity, APP_KEY,REDIRECT_URL, SCOPE);
//            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity);
//            String token = "";
//            if (accessToken != null) {
//                token = accessToken.getToken();
//            }
//            mWeiboShareAPI.sendRequest(mActivity, request, authInfo, token, new WeiboAuthListener() {
//
//                @Override
//                public void onWeiboException( WeiboException arg0 ) {
//                }
//
//                @Override
//                public void onComplete( Bundle bundle ) {
//                    // TODO Auto-generated method stub
//                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
//                    AccessTokenKeeper.writeAccessToken(mActivity, newToken);
//                    Toast.makeText(mActivity, "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancel() {
//                }
//            });
//        }
//	}
	
	/**
	 * 分享图片到微博
	 * @param bmp 图片
	 */
//	public void shareImage(Bitmap bmp){
//		judgeWeiboIsUsed();
//        login();
//		// 1. 初始化微博的分享消息
//		// 用户可以分享文本、图片、网页、音乐、视频中的一种
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        ImageObject imageObject = new ImageObject();
//		//BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//		imageObject.setImageObject(bmp);
//		weiboMessage.mediaObject =imageObject;
//
//        // 2. 初始化从第三方到微博的消息请求
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        if (mShareType == SHARE_CLIENT) {
//            mWeiboShareAPI.sendRequest(mActivity, request);
//        } else if (mShareType == SHARE_ALL_IN_ONE) {
//            AuthInfo authInfo = new AuthInfo(mActivity, APP_KEY,REDIRECT_URL, SCOPE);
//            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity);
//            String token = "";
//            if (accessToken != null) {
//                token = accessToken.getToken();
//            }
//            mWeiboShareAPI.sendRequest(mActivity, request, authInfo, token, new WeiboAuthListener() {
//
//                @Override
//                public void onWeiboException( WeiboException arg0 ) {
//                }
//
//                @Override
//                public void onComplete( Bundle bundle ) {
//                    // TODO Auto-generated method stub
//                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
//                    AccessTokenKeeper.writeAccessToken(mActivity, newToken);
//                    Toast.makeText(mActivity, "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancel() {
//                }
//            });
//        }
//	}
	
	//质量压缩图片
	private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>32) {    //循环判断如果压缩后图片是否大于32kb,大于继续压缩        
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
	
	private String getAppMetaData(Context context) {
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			String msg=appInfo.metaData.getString("WEIBO_APPKEY");
			return msg; 
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
