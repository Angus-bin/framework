package com.romaway.android.phone.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import com.romaway.android.phone.R;
import com.romaway.common.android.base.Res;
import com.romaway.framework.view.SysInfo;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.qq.tencent.Tencent;

import java.io.File;

/**
 * 分享工具类 com.romaway.im.ShareTools
 * 
 * @author 陈家平 <br/>
 *         create at 2014年7月17日 上午11:13:30
 */
public class ShareTools{
    private static final String TAG = "ShareTools";
    private Activity            context;
    //private IWXAPI api;
  //微信APP_ID
     public String mWechatShareID = "";
    //qq APP_ID
    private static String QQ_APP_KEY = "";
    private static Tencent mTencent;
    //新浪微博
    private static String sinaweibo_APP_KEY = "";
//    public IWeiboShareAPI mWeiboShareAPI;

    //Qzone/QQ APP_ID
//     public String mQQShareID="801526887";
    
    public ShareTools(Activity context) {
        this.context = context;
     // 将APP注册到微信
       // IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        mWechatShareID = Res.getString(R.string.wechatShare_FriendAndCircle);

        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID, false);
        api.registerApp(mWechatShareID);

        QQ_APP_KEY=context.getResources().getString(R.string.qqAndQzoneShare);
        mTencent = Tencent.createInstance(QQ_APP_KEY, context);

        sinaweibo_APP_KEY = context.getResources().getString(R.string.sinaweiboShare);
//        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, sinaweibo_APP_KEY);
//        mWeiboShareAPI.registerApp();
    }

    private static final int THUMB_SIZE = 150;
    
    /**
     * 微信图片分享
     * @param shareType SendMessageToWX.Req.WXSceneSession 分享给微信好友；
     * 					SendMessageToWX.Req.WXSceneTimeline 分享给朋友圈
     *					SendMessageToWX.Req.WXSceneFavorite 收藏
     */
    public void shareToWXImage(int shareType, Bitmap bitmap){
    	// 将APP注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
        //判断是否安装微信
        if(!api.isWXAppInstalled()){
        	SysInfo.showMessage(context, Res.getString(R.string.roma_hq_share_without_weixin));
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
     */
    public void shareToWXImage(int shareType, String imagePath) {
        // 将APP注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
        //判断是否安装微信
        if(!api.isWXAppInstalled()){
            SysInfo.showMessage(context, Res.getString(R.string.roma_hq_share_without_weixin));
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

    
    /**
     * 微信连接分享
     * @param shareType
     * @param url
     */
    public void shareToWXUrl(int shareType, String url, String title){
    	// 将APP注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
        //判断是否安装微信
        if(!api.isWXAppInstalled()){
        	SysInfo.showMessage(context, Res.getString(R.string.roma_hq_share_without_weixin));
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

		WXMediaMessage msg = new WXMediaMessage();
		msg.title = title;
		msg.setThumbImage(changeColor(BitmapFactory.decodeResource(context.getResources(), R.drawable.roma_sicon)));
		msg.mediaObject = webpage;
		
        SendMessageToWX.Req req = new SendMessageToWX.Req();

		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = shareType;
		api.sendReq(req);
    }

    /**
     * 微信链接分享
     * @param shareType
     * @param url
     * @param description
     */
    public void shareToWXUrlAndDescipe(int shareType, String description,String url, String title){
        shareToWXUrlAndDescipe(shareType, description,  url, title, null);
    }

    /**
     * 微信链接分享
     * @param shareType
     * @param url
     * @param description
     * @param shareImage
     */
    public void shareToWXUrlAndDescipe(int shareType, String description,String url, String title, Bitmap shareImage){
        // 将APP注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, mWechatShareID);
        //判断是否安装微信
        if(!api.isWXAppInstalled()){
            SysInfo.showMessage(context, Res.getString(R.string.roma_hq_share_without_weixin));
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.title = title;
        msg.description = description;
        if (shareImage == null)
            msg.setThumbImage(changeColor(BitmapFactory.decodeResource(context.getResources(), R.drawable.roma_sicon)));
        else
            msg.setThumbImage(changeColor(shareImage));
        msg.mediaObject = webpage;
        msg.mediaTagName = "WECHAT_TAG_JUMP_SHOWRANK";

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

    /**
     * 分享纯图片到 qq 只支持本地图
     */
//    public void SharePictureToQQ(String localImageUrl,IUiListener qqShareListener) {
//        Bundle params = new Bundle();
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,localImageUrl);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, Res.getString(R.string.app_name));
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
//        mTencent.shareToQQ(context, params, qqShareListener);
//    }
    /**
     * 分享图文消息 qq
     */
//    public void SharePictureAndWordToQQ(String title,String summary,String imageUrl,String appname,String clickToTarget,IUiListener qqShareListener) {
//        final Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//"要分享的标题"
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary );//"要分享的摘要"
//        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, clickToTarget);//"http://www.baidu.com/"
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);//"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif"
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appname);//"测试应用222222"
//        //params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
//        mTencent.shareToQQ(context, params, qqShareListener);
//    }

    /**
     * 分享图文消息  空间
     */
//    public void shareToQzone (String title,String summary,String imageUrl,String clickToTarget,IUiListener qqShareListener) {
//        final Bundle params = new Bundle();
//        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
//        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//"要分享的标题"
//        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);//"要分享的摘要"
//        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, clickToTarget);//"http://www.baidu.com/"
//        ArrayList<String> imageUrls = new ArrayList<String>();
//        imageUrls.add(imageUrl);//"http://img3.douban.com/lpic/s3635685.jpg"
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
//        mTencent.shareToQzone(context, params, qqShareListener);
//    }
    /**
     * 分享说说 空间
     */
//    public void shareToQzoneShuoshuo(IUiListener qqShareListener,String image) {
//        final Bundle params = new Bundle();
//        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
//        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "");
//        ArrayList<String> imageUrls = new ArrayList<String>();
//        imageUrls.add(image);
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
//        if (null !=mTencent) {
//            mTencent.publishToQzone(context, params, qqShareListener);
//        }
//        return;
//    }

//    public void judgeWeiboIsUsed(){
//        boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
//        int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
//        if(!isInstalledWeibo){
//            Toast.makeText(context,  "请先安装微博", Toast.LENGTH_LONG).show();
//            return;
//        }
//    }

    /**
     * 分享文字内容到微博
     * @param text 内容
     */
//    public void shareTextTOsinaWeibo(String text,String image){
//        judgeWeiboIsUsed();
//        // 1. 初始化微博的分享消息
//        // 用户可以分享文本、图片、网页、音乐、视频中的一种
//        WeiboMessage weiboMessage = new WeiboMessage();
//        TextObject textObject = new TextObject();
//        ImageObject imageObject = new ImageObject();
//        imageObject.imagePath = image;
//        textObject.text = text;
//        weiboMessage.mediaObject = textObject;
//        weiboMessage.mediaObject = imageObject;
//        // 2. 初始化从第三方到微博的消息请求
//        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.message = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        mWeiboShareAPI.sendRequest(context, request);
//    }

    /**
     * 分享图文链接到微博
     * @param title 标题
     * @param descriptiion 描述
     * @param url 网页链接
     */
//    public void shareImageAndUrlTOsinaWeibo(String title, String descriptiion, String url,Bitmap bmp){
//        judgeWeiboIsUsed();
//        WeiboMessage weiboMessage = new WeiboMessage();
//        WebpageObject webpageObject = new WebpageObject();
//        webpageObject.identify = Utility.generateGUID();
//        webpageObject.title = title;
//        webpageObject.description = descriptiion;
//        webpageObject.actionUrl = url;
//        webpageObject.setThumbImage(bmp);
//        //webpageObject.setThumbImage(bitmap);
//        weiboMessage.mediaObject = webpageObject;
//        // 2. 初始化从第三方到微博的消息请求
//        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.message = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        mWeiboShareAPI.sendRequest(context, request);
//    }

    /**
     * 分享图片到微博
     */
//    public void shareImageTOsinaWeibo(Bitmap bmp){
//        judgeWeiboIsUsed();
//        // 1. 初始化微博的分享消息
//        // 用户可以分享文本、图片、网页、音乐、视频中的一种
//        WeiboMessage weiboMessage = new WeiboMessage();
//        ImageObject imageObject = new ImageObject();
//        //BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//        imageObject.setImageObject(bmp);
//        weiboMessage.mediaObject =imageObject;
//        // 2. 初始化从第三方到微博的消息请求
//        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.message = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        mWeiboShareAPI.sendRequest(context, request);
//    }

    /**
     * bitmap中的透明色用白色替换
     *
     * @param bitmap
     * @return
     */
    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }

    /**
     * 获取和白色混合颜色
     *
     * @return
     */
    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite

                        (green, alpha),
                getSingleMixtureWhite(blue, alpha));
    }

    /**
     * 获取单色的混合值
     *
     * @param color
     * @param alpha
     * @return
     */
    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }

}
