package com.romalibs.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;

import com.romalibs.utils.Res;
//import com.romaway.libs.R;
//import com.tencent.connect.UserInfo;
//import com.tencent.connect.auth.QQToken;
//import com.tencent.connect.common.Constants;
//import com.tencent.connect.share.QQShare;
//import com.tencent.connect.share.QzonePublish;
//import com.tencent.connect.share.QzoneShare;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * qq分享类 ：qq空间分享图文连接，说说    ；  qq好友分享图片，图文连接
 * @author wusq
 *
 */

public class RomaQQUtils {
	private static final String TAG = "RomaQQUtils";
	
	private static String APP_ID = ""; //QQAPP_ID;
	private  Activity mActivity;
	private RomaQQUtils instance;
	//登录成功后回调
	private LoginListener mLoginListener;
//	private static Tencent mTencent;
	
	public RomaQQUtils(Activity activity){
		mActivity = activity;
//		APP_ID=activity.getResources().getString(R.string.qqAndQzoneShare);
//		mTencent = Tencent.createInstance(APP_ID, activity);
	}
	
	public RomaQQUtils getInstance(Activity activity){
		if (instance == null) {
			synchronized (RomaQQUtils.class) {
				if (instance == null) {
					instance = new RomaQQUtils(activity);
				}
			}
		}
		return instance;
	}
	/**
	 * 分享纯图片到 qq 只支持本地图
	 */	
//	public void SharePictureToQQ(String localImageUrl,IUiListener qqShareListener) {
//		    Bundle params = new Bundle();
//		    params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,localImageUrl);
//		    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, Res.getString(R.string.app_name));
//		    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//		    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
//		    mTencent.shareToQQ(mActivity, params, qqShareListener);
//		}
	/**
	 * 分享图文消息 qq
	 */		
//		public void SharePictureAndWordToQQ(String title,String summary,String imageUrl,String appname,String clickToTarget,IUiListener qqShareListener) {
//		    final Bundle params = new Bundle();
//		    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//		    params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//"要分享的标题"
//		    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary );//"要分享的摘要"
//		    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, clickToTarget);//"http://www.baidu.com/"
//		    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);//"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif"
//		    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appname);//"测试应用222222"
//		    //params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
//		    mTencent.shareToQQ(mActivity, params, qqShareListener);
//		}
	/**
	 * 分享图文消息  空间
	 */	
//	public void shareToQzone (String title,String summary,String imageUrl,String clickToTarget,IUiListener qqShareListener) {
//			 final Bundle params = new Bundle();
//	         params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
//	         params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//"要分享的标题"
//	         params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);//"要分享的摘要"
//	         params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, clickToTarget);//"http://www.baidu.com/"
//	         ArrayList<String> imageUrls = new ArrayList<String>();
//	         imageUrls.add(imageUrl);//"http://img3.douban.com/lpic/s3635685.jpg"
//	         params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
//			    mTencent.shareToQzone(mActivity, params, qqShareListener);
//			}
	/**
	 * 分享说说 空间
	 */	
//		public void shareToQzoneShuoshuo(IUiListener qqShareListener,String image) {
//			final Bundle params = new Bundle();
//	        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
//	        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "");
//	        ArrayList<String> imageUrls = new ArrayList<String>();
//            imageUrls.add(image);
//	        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
//	        if (null !=mTencent) {
//	            mTencent.publishToQzone(mActivity, params, qqShareListener);
//	        }
//	        return;
//		}
		
		/**
		 * qq获取本地图片
		 */	
		public void startPickLocaleImage() {
	        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

	        if (android.os.Build.VERSION.SDK_INT >= Util.Build_VERSION_KITKAT) {
	            intent.setAction(Util.ACTION_OPEN_DOCUMENT);
	        } else {
	            intent.setAction(Intent.ACTION_GET_CONTENT);
	        }
	        intent.addCategory(Intent.CATEGORY_OPENABLE);
	        intent.setType("image/*");
//	        mActivity.startActivityForResult(
//	                Intent.createChooser(intent, mActivity.getString(R.string.str_image_local)), 0);
	    }
	
	/**
	 * 调用QQ登录
	 */
//	private void loginWithQQ(){
//		mTencent.login(mActivity, "all", new IUiListener() {
//
//			@Override
//			public void onError(UiError arg0) {
//			}
//
//			@Override
//			public void onComplete(Object response) {
//				if (response == null) {
//					return;
//				}
//				JSONObject jsonResponse = (JSONObject) response;
//				if (jsonResponse != null && jsonResponse.length() == 0) {
//					return;
//				}
//				//登录成功回调
//				String token = jsonResponse.optString(Constants.PARAM_ACCESS_TOKEN);
//	            String expires = jsonResponse.optString(Constants.PARAM_EXPIRES_IN);
//	            String openId = jsonResponse.optString(Constants.PARAM_OPEN_ID);
//	            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
//	                    && !TextUtils.isEmpty(openId)) {
//	            	mTencent.setAccessToken(token, expires);
//	                mTencent.setOpenId(openId);
//				}
//			}
//
//			@Override
//			public void onCancel() {
//			}
//		});
//	}
	
	/**
	 * 获取用户信息
	 */
//	private void getQQUserInfo(){
//		if (mTencent != null && mTencent.isSessionValid()) {
//
//			IUiListener listener = new IUiListener() {
//
//				@Override
//				public void onError(UiError arg0) {
//				}
//
//				@Override
//				public void onComplete(Object arg0) {
//					//获取用户信息结果
//				}
//
//				@Override
//				public void onCancel() {
//				}
//			};
//			QQToken qqToken = mTencent.getQQToken();
//            UserInfo info = new UserInfo(mActivity, qqToken);
//            info.getUserInfo(listener);
//		}
//	}
	
	private String getAppMetaData(Context context) {
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			String msg=appInfo.metaData.getString("WEIXIN_APPKEY1");
			return msg; 
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	

}
