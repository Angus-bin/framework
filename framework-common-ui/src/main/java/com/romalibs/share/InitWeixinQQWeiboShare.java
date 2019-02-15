package com.romalibs.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

//import com.romaway.libs.R;
//import com.tencent.tauth.IUiListener;

/**
 * 此类为一次性初始化微信，qq，微博，要放在相应Activity的oncreate方法下
 * 
 * @author wusq
 * 
 */
public class InitWeixinQQWeiboShare{
	private RomaQQUtils mRomaQQUtils;
	private RomaWeiXinUtils mRomaWeiXinUtils;
	public RomaWeiBoUtils mRomaWeiBoUtils;
	private Activity mActivity;

	/**
	 * 
	 * @param activity
	 * @param ShareToWhere
	 *            分享到哪个平台：微信："weixin"，qq："qq"，微博："weibo"
	 */
	public InitWeixinQQWeiboShare(Activity activity, String ShareToWhere) {
		mActivity = activity;
		if (ShareToWhere.equals("qq")) {
			mRomaQQUtils = new RomaQQUtils(activity);
		} else if (ShareToWhere.equals("weixin")) {
			mRomaWeiXinUtils = new RomaWeiXinUtils(activity);
		} else if (ShareToWhere.equals("weibo")) {
			mRomaWeiBoUtils = new RomaWeiBoUtils(activity);
		}
	}

	/**
	 * 分享文字qq空间说说，微信好友，朋友圈，微博
	 * 
	 * @param text
	 * @param toFriendOrGroup
	 *            微信：1为微信朋友圈和qq空间，0为好友
	 * @param qqShareListener
	 *            qq空间说说返回监听
	 * @param ShareToWhere
	 *            分享到哪个平台：微信："weixin"，qq："qq"，微博："weibo"
	 */
//	public void shareText(String ShareToWhere, int toFriendOrGroup,
//			String text,String image,IUiListener qqShareListener) {
//		if (ShareToWhere.equals("qq")) {
//			if (mRomaQQUtils == null) {
//				Toast.makeText(mActivity, "qq分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			if (toFriendOrGroup == 1) {
//				mRomaQQUtils.shareToQzoneShuoshuo(qqShareListener,image);
//			} else {
//				Toast.makeText(mActivity, "qq好友不支持纯文字分享", Toast.LENGTH_LONG)
//						.show();
//			}
//
//		} else if (ShareToWhere.equals("weixin")) {
//			if (mRomaWeiXinUtils == null) {
//				Toast.makeText(mActivity, "微信分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			mRomaWeiXinUtils.shareText(text, toFriendOrGroup);
//		} else if (ShareToWhere.equals("weibo")) {
//			if (mRomaWeiBoUtils == null) {
//				Toast.makeText(mActivity, "微博分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			mRomaWeiBoUtils.shareText(text,image);
//		}
//	}

	/**
	 * 分享图片 qq只有qq好友支持此功能
	 * 
	 * @param ShareToWhere
	 *            分享到哪个平台：微信："weixin"，qq："qq"，微博："weibo"
	 * @param bmpForWeixinAndWeibo
	 *            微信和微博使用的图片
	 * @param toFriendOrGroup
	 *            此参数只为qq分享：不过只有qq好友支持此功能；1为qq空间，0为好友
	 * @param localImageUrlForQQFriend
	 *            qq分享，只支持本地图片
	 * @param qqShareListener
	 *            qq分享返回监听
	 */
//	public void shareImage(String ShareToWhere, int toFriendOrGroup,
//			Bitmap bmpForWeixinAndWeibo, String localImageUrlForQQFriend,IUiListener qqShareListener) {
//		if (ShareToWhere.equals("qq")) {
//			if (mRomaQQUtils == null) {
//				Toast.makeText(mActivity, "qq分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			if (toFriendOrGroup == 0) {
//				mRomaQQUtils.SharePictureToQQ(localImageUrlForQQFriend,
//						qqShareListener);
//			} else {
//				Toast.makeText(mActivity, "qq空间只支持图文连接和说说", Toast.LENGTH_LONG)
//						.show();
//			}
//		} else if (ShareToWhere.equals("weixin")) {
//			if (mRomaWeiXinUtils == null) {
//				Toast.makeText(mActivity, "微信分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			mRomaWeiXinUtils.shareImage(bmpForWeixinAndWeibo, toFriendOrGroup);
//			;
//		} else if (ShareToWhere.equals("weibo")) {
//			if (mRomaWeiBoUtils == null) {
//				Toast.makeText(mActivity, "微博分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			mRomaWeiBoUtils.shareImage(bmpForWeixinAndWeibo);
//		}
//	}

	/**
	 * 图文连接分享 支持qq空间，好友，微信朋友圈，好友，微博
	 * 
	 * @param ShareToWhere
	 *            分享到哪个平台：微信："weixin"，qq："qq"，微博："weibo"
	 * @param toFriendOrGroup
	 *            微信：1为微信朋友圈和qq空间，0为好友
	 * @param title
	 * @param summary
	 * @param imageUrlForQQ
	 *            qq图片地址
	 * @param bmpForWeixinAndWeibo
	 *            微信，微博图片
	 * @param appname
	 *            应用名
	 * @param clickToTargetUrl
	 *            点击图文去向的url
	 * @param qqShareListener
	 *            qq分享返回监听
	 */
//	public void shareImageAndUrl(String ShareToWhere, int toFriendOrGroup,
//			String title, String summary, String imageUrlForQQ,
//			Bitmap bmpForWeixinAndWeibo, String appname,
//			String clickToTargetUrl,IUiListener qqShareListener) {
//		if (ShareToWhere.equals("qq")) {
//			if (mRomaQQUtils == null) {
//				Toast.makeText(mActivity, "qq分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			if (toFriendOrGroup == 0) {
//				mRomaQQUtils.SharePictureAndWordToQQ(title, summary,
//						imageUrlForQQ, appname, clickToTargetUrl,
//						qqShareListener);
//			} else {
//				mRomaQQUtils.shareToQzone(title, summary, imageUrlForQQ,
//						clickToTargetUrl, qqShareListener);
//			}
//		} else if (ShareToWhere.equals("weixin")) {
//			if (mRomaWeiXinUtils == null) {
//				Toast.makeText(mActivity, "微信分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			mRomaWeiXinUtils.shareImageAndUrl(title, summary, clickToTargetUrl,
//					bmpForWeixinAndWeibo, toFriendOrGroup);
//		} else if (ShareToWhere.equals("weibo")) {
//			if (mRomaWeiBoUtils == null) {
//				Toast.makeText(mActivity, "微博分享未初始化", Toast.LENGTH_LONG).show();
//				return;
//			}
//			mRomaWeiBoUtils.shareImageAndUrl(title, summary, clickToTargetUrl,
//					bmpForWeixinAndWeibo);
//		}
//	}
	
	/**
	 * 微信分享返回有自己的一个类WXEntryActivity.java
	 */
	
	
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
//        mActivity.startActivityForResult(
//                Intent.createChooser(intent, mActivity.getString(R.string.str_image_local)), 0);
    }

}
