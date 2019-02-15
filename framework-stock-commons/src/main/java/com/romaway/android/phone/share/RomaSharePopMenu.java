package com.romaway.android.phone.share;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.romaway.android.phone.R;
import com.romaway.common.android.base.Res;
import com.romaway.commons.android.fileutil.FileSystem;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;

import java.io.File;

public class RomaSharePopMenu implements OnClickListener{

	private Activity mActivity;
    private PopupWindow popupWindow;
    private ShareTools share;
    private String url;
    private String title;
    private SVGView svg_close;
	private String description;
//    private InitWeixinQQWeiboShare qqShare;
//    private InitWeixinQQWeiboShare sinaweiboShare;
	
	private Bitmap thumbImage;
	private final int mPopViewHeight;

	public RomaSharePopMenu(Activity activity) {
		mActivity = activity;
//        initShare();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.roma_share_pop_layout, null);
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w,h);
		mPopViewHeight = view.getMeasuredHeight();
//		if(Res.getBoolean(R.bool.kconfigs_popupWindow_isShowDimBgAnimaton))
//			popupWindow = new RomaPopupWindow(mActivity, view,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
//		else
        	popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        // 需要设置一下此参数，点击外边可消失 
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        view.findViewById(R.id.ll_share_wx).setOnClickListener(this);
        view.findViewById(R.id.ll_share_pyq).setOnClickListener(this);
        view.findViewById(R.id.ll_share_qq).setOnClickListener(this);
        view.findViewById(R.id.ll_share_qzone).setOnClickListener(this);
        view.findViewById(R.id.ll_share_sinaweibo).setOnClickListener(this);
        svg_close = (SVGView) view.findViewById(R.id.svg_share_close);
        svg_close.setSVGRenderer(new SVGParserRenderer(activity, R.drawable.roma_svg_close), "");
		((View)svg_close.getParent()).setOnClickListener(this);
        
        share=new ShareTools(activity);
	}
	
	@SuppressLint("NewApi")
	public void showAtLocation(View parent){
		Rect outRect = new Rect();
		mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        popupWindow.showAtLocation(parent, Gravity.TOP, 0, outRect.bottom - mPopViewHeight);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
	}
	
	public boolean isShowing(){
		return popupWindow.isShowing();
	}
	
	public void dismiss(){
        popupWindow.dismiss();
    }
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	public void setDescription(String description){
		this.description = description;
	}

	public void setThumbImage(Bitmap thumbImage){
		this.thumbImage = thumbImage;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ll_share_wx) {//分享到微信
			if(Res.getBoolean(R.bool.is_similar_jiantou_zixun_share)){
				share.shareToWXUrlAndDescipe(SendMessageToWX.Req.WXSceneSession, description,url, title, thumbImage);
			}else{
				share.shareToWXUrl(SendMessageToWX.Req.WXSceneSession, url, title);
			}			
			
		} else if (v.getId() == R.id.ll_share_pyq) {//分享到朋友圈
             if(Res.getBoolean(R.bool.is_similar_jiantou_zixun_share)){
            	 share.shareToWXUrlAndDescipe(SendMessageToWX.Req.WXSceneTimeline,description, url, title, thumbImage);
			}else{
				share.shareToWXUrl(SendMessageToWX.Req.WXSceneTimeline, url, title);
			}	
		} else if (v.getId() == R.id.ll_share_qq) {//qq
            File file = new File(FileSystem.getCacheRootDir(mActivity, "image"), "screenTemp.png");
            ScreenShot.shoot(mActivity, file);
//            share.SharePictureAndWordToQQ(title,description,file.getAbsolutePath(),"",url,qqShareListener);
        } else if (v.getId() == R.id.ll_share_qzone) {//qq空间
            File file = new File(FileSystem.getCacheRootDir(mActivity, "image"), "screenTemp.png");
            ScreenShot.shoot(mActivity, file);
//            share.shareToQzoneShuoshuo(qqShareListener,file.getAbsolutePath());

        } else if (v.getId() == R.id.ll_share_sinaweibo) {//新浪微博
            File file = new File(FileSystem.getCacheRootDir(mActivity, "image"), "screenTemp.png");
            ScreenShot.shoot(mActivity, file);
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inPreferredConfig = Bitmap.Config.ARGB_4444;

            Bitmap img = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

//            share.shareImageAndUrlTOsinaWeibo(title, description, url, img);
        }
		popupWindow.dismiss();
	}


    //qq分享结果监听
//    private IUiListener qqShareListener = new IUiListener() {
//
//        @Override
//        public void onComplete(Object response) {
//            Toast.makeText(mActivity, "分享成功", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onError(UiError e) {
//            Toast.makeText(mActivity, e.errorMessage, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCancel() {
//            // Toast.makeText(KDS_NewsDetailsActivity.this, "分享取消了", Toast.LENGTH_LONG).show();
//        }
//    };

}
