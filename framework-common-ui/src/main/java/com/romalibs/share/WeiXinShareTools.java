package com.romalibs.share;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import com.romalibs.utils.Res;
import com.romaway.libs.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 分享工具类 com.szkingdom.im.ShareTools
 * 
 * @author 陈家平
 *  create at 2014年7月17日 上午11:13:30
 */
public class WeiXinShareTools{
    private static final String TAG = "ShareTools";
    private Activity            context;
    //private IWXAPI api;
  //微信APP_ID
     public String mWechatShareID = "";
     //Qzone/QQ APP_ID
//     public String mQQShareID="801526887";
    
    public WeiXinShareTools(Activity context) {
        this.context = context;
     // 将APP注册到微信
       // IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        mWechatShareID = Res.getString(R.string.wechatShare_FriendAndCircle);
//        mQQShareID = Res.getString(R.string.qqShare_FriendAndQzone);
        
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID, false);
        api.registerApp(mWechatShareID);
    }

    private static final int THUMB_SIZE = 150;
    
    /**
     * 微信图片分享
     * @param shareType SendMessageToWX.Req.WXSceneSession 分享给微信好友；
     * 					SendMessageToWX.Req.WXSceneTimeline 分享给朋友圈
     *					SendMessageToWX.Req.WXSceneFavorite 收藏
	 * @param bitmap
     */
    public void shareToWXImage(int shareType, Bitmap bitmap){
    	// 将APP注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
        //判断是否安装微信
        if(!api.isWXAppInstalled()){
        	Toast.makeText(context, "您当前未安装微信！", Toast.LENGTH_LONG).show();
        }
        
       // api.registerApp(mWechatShareID);
        
       // Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.send_img);
		WXImageObject imgObj = new WXImageObject(bitmap);
		
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
		bitmap.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = shareType;
		api.sendReq(req);
    }

    /**
     * 微信图片分享
     * @param shareType SendMessageToWX.Req.WXSceneSession 分享给微信好友；
     * 					SendMessageToWX.Req.WXSceneTimeline 分享给朋友圈
     *					SendMessageToWX.Req.WXSceneFavorite 收藏
	 * @param imagePath
     */
    public void shareToWXImage(int shareType, String imagePath) {
        // 将APP注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
        //判断是否安装微信
        if(!api.isWXAppInstalled()){
        	Toast.makeText(context, "您当前未安装微信！", Toast.LENGTH_LONG).show();
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(imagePath);
        
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = shareType;
        boolean flag = api.sendReq(req);
    }

    
//    /**
//     * 微信连接分享
//     * @param shareType
//     * @param url
//	 * @param title
//     */
//    public void shareToWXUrl(int shareType, String url, String title){
//    	// 将APP注册到微信
//        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
//        //判断是否安装微信
//        if(!api.isWXAppInstalled()){
//        	Toast.makeText(context, "您当前未安装微信！", Toast.LENGTH_LONG).show();
//        }
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = url;
//
//		WXMediaMessage msg = new WXMediaMessage();
//		msg.title = title;
//		msg.setThumbImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.kds_gphone_original));
//		msg.mediaObject = webpage;
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = buildTransaction("webpage");
//		req.message = msg;
//		req.scene = shareType;
//		api.sendReq(req);
//    }

    /**
     * 微信连接分享
     * @param shareType
     * @param url
     * @param title
     * @param drawableResId
     */
    public void shareToWXUrl(int shareType, String url, String title, int drawableResId){
        // 将APP注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
        //判断是否安装微信
        if(!api.isWXAppInstalled()){
            Toast.makeText(context, "您当前未安装微信！", Toast.LENGTH_LONG).show();
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.title = title;
        msg.setThumbImage(BitmapFactory.decodeResource(context.getResources(), drawableResId));
        msg.mediaObject = webpage;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareType;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type
                + System.currentTimeMillis();
    }
    
    /**
     * 分享到系统中的应用
     * 
     */
    
    public void shareToOthers(){
        String imgUrl="";
        Intent intent=new Intent(Intent.ACTION_SEND);
        if(imgUrl==null || imgUrl.equals("")){
            intent.setType("text/plain");//纯文本类型
        }else{
            File file=new File(imgUrl);
            if(file!=null && file.exists() && file.isFile()){
                intent.setType("image/jpg");
                Uri uri=Uri.fromFile(file);
                intent.putExtra(Intent.EXTRA_STREAM, uri);//分享的图片
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "系统应用分享");//要分享的文字内容
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
