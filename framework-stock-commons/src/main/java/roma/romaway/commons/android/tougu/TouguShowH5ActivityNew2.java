package roma.romaway.commons.android.tougu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.romalibs.common.ApiInterface;
import com.romalibs.common.ApiManager;
import com.romalibs.common.ApiProvider;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.R;
import com.romaway.android.phone.RomaShareImageManager;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.utils.ColorUtils;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.VideoDataProtocol;
import com.romaway.common.protocol.service.DLServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.ArrayList;
import java.util.List;

import roma.romaway.commons.android.share.ShareManager;
import roma.romaway.commons.android.tougu.ImageView.ImageViewActivity;
import roma.romaway.commons.android.tougu.photoview.ImageGalleryActivity;
import roma.romaway.commons.android.tougu.photoview.LinkMovementMethodExt;
import roma.romaway.commons.android.tougu.photoview.MessageSpan;
import roma.romaway.commons.android.tougu.videoview.UniversalMediaController;
import roma.romaway.commons.android.tougu.videoview.UniversalVideoView;

/**
 * Created by hongrb on 2017/10/19.
 */
public class TouguShowH5ActivityNew2 extends Activity implements UniversalVideoView.VideoViewCallback, View.OnClickListener {

    private Activity mActivity;
    private WebView mRomaWebView;

    private static final String TAG = "TouguShowH5ActivityNew2";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private String VIDEO_URL = "";
    private String title = "";
    private String videoID = "";

    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;

    View mVideoLayout;

    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;


    private ImageView img_left, img_right;

    private TextView txt_left, txt_title;

    private RelativeLayout rl_img_right;

    private LinearLayout ll_img_left;

    private RelativeLayout rl_actionbar_title;

    private LinearLayout mBottomLayout;

    private TextView tv_content;

    private WebView web_content;

    private String content_url;

    private ApiProvider apiProvider;

    // 分享
    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    private String shareUrl;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        mActivity = this;
        this.setContentView(R.layout.roma_gxs_video_detail_layout);

        if (this.getIntent() != null) {
            Bundle bundle = this.getIntent().getExtras();
            VIDEO_URL = bundle.getString("videoValue", "");
            title = bundle.getString("title", "新人学堂");
            videoID = bundle.getString("videoID", "");
            shareUrl = bundle.getString("shareUrl", "");
            content_url = bundle.getString("content", "");
        }

        mVideoLayout = findViewById(R.id.video_layout);
        mBottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        tv_content = (TextView) findViewById(R.id.tv_content);
        mVideoView.setMediaController(mMediaController);
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(this);
        web_content = (WebView) findViewById(R.id.web_content);
        if (!StringUtils.isEmpty(content_url) && content_url.contains("http")) {
            tv_content.setVisibility(View.GONE);
            web_content.setVisibility(View.VISIBLE);
        } else {
            tv_content.setVisibility(View.VISIBLE);
            web_content.setVisibility(View.GONE);
        }
        //添加通用的接口API
        apiProvider = (new ApiManager("CommonApiProvider")).getApiProvider();
        apiProvider.getSettings().setEnableWebView(web_content);//设置该项可支持原生给webView回传数据
        apiProvider.getSettings().setActivity(mActivity);

        if(apiProvider != null) {
            web_content.addJavascriptInterface(apiProvider, ApiInterface.JS_BRIDGE_NATIVE_NAME);
            if (Build.VERSION.SDK_INT < 17) {
                web_content.removeJavascriptInterface("accessibility");
                web_content.removeJavascriptInterface("accessibilityTraversal");
                web_content.removeJavascriptInterface("searchBoxJavaBridge_");
            }
        }
//        web_content.setInitialScale(100);
        WebSettings webSettings = web_content.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setBlockNetworkImage(false); // 解决图片不显示
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDefaultTextEncodingName("UTF-8");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//		webSettings.setPluginsEnabled(true);  //支持插件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setPluginState(WebSettings.PluginState.ON);
        }
        // 设置自适应屏幕，两者合用
        if (Build.VERSION.SDK_INT >= 19)
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH); // 设置这个主要是用于流畅的滑动

        if (web_content != null) {
            if (!StringUtils.isEmpty(content_url) && content_url.contains("http")) {
                web_content.loadUrl(content_url);
            }
        }

        if (mSeekPosition > 0) {
            mVideoView.seekTo(mSeekPosition);
        }
//        mVideoView.start();
        mMediaController.setTitle(title);

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Logger.d(TAG, "onCompletion ");
            }
        });

        img_left = (ImageView) findViewById(R.id.img_left);
        img_right = (ImageView) findViewById(R.id.img_right);
        txt_left = (TextView) findViewById(R.id.txt_left);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setTextColor(Res.getColor(R.color.gxs_title));
        rl_img_right = (RelativeLayout) findViewById(R.id.rl_img_right);
        ll_img_left = (LinearLayout) findViewById(R.id.ll_img_left);
        rl_actionbar_title = (RelativeLayout) findViewById(R.id.actionbar_title);
        ll_img_left.setOnClickListener(this);
        rl_img_right.setOnClickListener(this);

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 200) {
                    MessageSpan ms = (MessageSpan) msg.obj;
                    Object[] spans = (Object[]) ms.getObj();
                    final ArrayList<String> list = new ArrayList<String>();
                    for (Object span : spans) {
                        if (span instanceof ImageSpan) {
                            Logger.i("picUrl==", ((ImageSpan) span).getSource());
                            list.add(((ImageSpan) span).getSource());
//                            Intent intent = new Intent(getActivity(), ImageGalleryActivity.class);
//                            intent.putStringArrayListExtra("images", list);
//                            startActivity(intent);
//                            Bundle bundle1 = new Bundle();
//                            bundle1.putStringArrayList("images", list);
//                            KActivityMgr.switchWindow((ISubTabView) mActivity, ImageGalleryActivity.class, bundle1, -1, false);
                            Intent intent = new Intent(TouguShowH5ActivityNew2.this, ImageViewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("image_url", ((ImageSpan) span).getSource());
                            intent.putExtras(bundle);
                            startActivity(intent);
//                            KActivityMgr.switchWindow((ISubTabView) TouguShowH5ActivityNew2.this, ImageViewActivity.class, bundle, false);
                        }
                    }
                }
            }
        };
        tv_content.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class, mActivity));

        mShareListener = new CustomShareListener(mActivity);

        SHARE_MEDIA[] share_medias = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE};
        SHARE_MEDIA[] displaylist = ShareManager.getInstance(mActivity).Install(mActivity, mActivity, share_medias);

        if (displaylist != null && displaylist.length > 0) {
            isHaveShare = true;
        }
        if (isHaveShare) {
            mShareAction = new ShareAction(mActivity).setDisplayList(
                    displaylist).setShareboardclickCallback(new ShareBoardlistener() {
                @Override
                public void onclick(SnsPlatform snsPlatform, final SHARE_MEDIA share_media) {
                    Logger.d("ShareAction", "snsPlatform.mShowWord = " + snsPlatform.mShowWord + "   share_media = " + share_media + "");
                    if (StringUtils.isEmpty(shareUrl)) {
                        RomaToast.showMessage(TouguShowH5ActivityNew2.this, "分享链接为空，请重试");
                        mShareAction.close();
                        return;
                    }
                    if (share_media == SHARE_MEDIA.SINA) {
                        UMWeb web = new UMWeb(shareUrl);
                        web.setTitle(!StringUtils.isEmpty(title) ? title : "容维财经");
                        web.setDescription("点击播放视频" + " @容维财经APP");
                        web.setThumb(new UMImage(mActivity, R.drawable.roma_sicon));
                        new ShareAction(mActivity).withMedia(web)
                                .setPlatform(share_media)
                                .setCallback(mShareListener)
                                .share();
                        return;
                    }
                    UMWeb web = new UMWeb(shareUrl);
                    web.setTitle(!StringUtils.isEmpty(title) ? title : "容维财经");
                    web.setDescription("点击播放视频");
                    web.setThumb(new UMImage(mActivity, R.drawable.roma_sicon));
                    new ShareAction(mActivity).withMedia(web)
                            .setPlatform(share_media)
                            .setCallback(mShareListener)
                            .share();
                }
            });
        }

    }

    private boolean isHaveShare = false;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("TouguShowH5ActivityNew2");
        MobclickAgent.onResume(this);

        setTitle(StringUtils.isEmpty(title) ? "新人学堂" : title);
        if (rl_actionbar_title != null) {
            rl_actionbar_title.setBackgroundColor(Res.getColor(R.color.gxs_while));
        }
        if (txt_left != null) {
            txt_left.setVisibility(View.GONE);
        }
        if (img_left != null) {
            img_left.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.title_back)), Res.getColor(R.color.gxs_title)));
            img_left.setVisibility(View.VISIBLE);
        }
        if (img_right != null) {
            img_right.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.roma_share_icon)), Res.getColor(R.color.gxs_title)));
            img_right.setVisibility(View.VISIBLE);
        }
        mVideoView.start();
        if (StringUtils.isEmpty(content_url) || !content_url.contains("http")) {
            req();
        } else {
            if (web_content != null) {
                if (!StringUtils.isEmpty(content_url) && content_url.contains("http")) {
                    web_content.loadUrl(content_url);
                }
            }
        }
    }

    public void setTitle(CharSequence text) {
        if (txt_title != null) {
            if (text.length() > 15) {
                text = text.toString().substring(0, 15) + "...";
            }
            txt_title.setText(text);
        }
    }

    private void req() {
        Logger.d("TouguShowH5ActivityNew2", "videoID = " + videoID);
        if (!StringUtils.isEmpty(videoID)) {
            DLServices.reqVideoData(videoID, new Listener(TouguShowH5ActivityNew2.this), "video_data");
        }
    }

    private class Listener extends UINetReceiveListener {

        /**
         * @param activity
         */
        public Listener(Activity activity) {
            super(activity);
        }

        @Override
        protected void onSuccess(NetMsg msg, AProtocol ptl) {
            super.onSuccess(msg, ptl);
            if (ptl instanceof VideoDataProtocol) {
                VideoDataProtocol protocol = (VideoDataProtocol) ptl;
                if (!StringUtils.isEmpty(protocol.resp_errorCode) && "0".equals(protocol.resp_errorCode)) {
                    if (!StringUtils.isEmpty(protocol.resp_content)) {
                        if (tv_content != null && tv_content.getVisibility() == View.VISIBLE) {
                            tv_content.setText(Html.fromHtml(protocol.resp_content, new MImageGetter(tv_content, OriginalContext.getContext()), null));
                        }
                        if (web_content != null && web_content.getVisibility() == View.VISIBLE) {
//                            web_content.loadDataWithBaseURL(null, protocol.resp_content, "text/html", "UTF-8", null);
                            web_content.loadUrl(content_url);
                        }
                    }
                }
            }
        }

        @Override
        protected void onShowStatus(int status, NetMsg msg) {
            super.onShowStatus(status, msg);
        }
    }

    public class MImageGetter implements Html.ImageGetter {
        Context c;
        TextView container;
        private List<Target> targets = new ArrayList<>();


        public MImageGetter(TextView text,Context c) {
            this.c = c;
            this.container = text;
        }
        public Drawable getDrawable(final String source) {

            final Drawable drawable = new LevelListDrawable();
            final URLDrawable urlDrawable = new URLDrawable();
//            Glide.with(c).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    if(resource != null) {
////                        BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
////                        bitmapDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
////                        urlDrawable.setDrawable(bitmapDrawable);
////                        urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
//////                        drawable.addLevel(1, 1, bitmapDrawable);
//////                        drawable.setBounds(0, 0, tv_content.getWidth(),resource.getHeight());
//////                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2);
//////                        drawable.setLevel(1);
////                        container.invalidate();
////                        container.setText(container.getText());
//                    }
//                }
//            });
            final Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Drawable drawable = new BitmapDrawable(bitmap);
                    drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    urlDrawable.setDrawable(drawable);
                    urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    container.invalidate();
                    container.setText(container.getText());
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    errorDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
                    urlDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
                    urlDrawable.setDrawable(errorDrawable);
                    container.invalidate();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    placeHolderDrawable.setBounds(0, 0, placeHolderDrawable.getIntrinsicWidth(), placeHolderDrawable.getIntrinsicHeight());
                    urlDrawable.setBounds(0, 0, placeHolderDrawable.getIntrinsicWidth(), placeHolderDrawable.getIntrinsicHeight());
                    urlDrawable.setDrawable(placeHolderDrawable);
                    container.invalidate();
                }
            };

            targets.add(target);
            loadPlaceholder(c, source, target);

            return urlDrawable;
        }

    }

    public class URLDrawable extends BitmapDrawable {
        private Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

    }

    public void loadPlaceholder(Context context, String url, Target target) {

        Picasso picasso = new Picasso.Builder(context).loggingEnabled(true).build();
        picasso.load(url)
                .placeholder(R.drawable.place_icon)
                .error(R.drawable.place_icon)
                .transform(new ImageTransform())
                .into(target);
    }

    public class ImageTransform implements Transformation {

        private String Key = "ImageTransform";

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
            if (source.getWidth() == 0) {
                return source;
            }

            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    source.recycle();
                }
                return result;
            } else {
                return source;
            }
        }

        @Override
        public String key() {
            return Key;
        }
    }

    private void setBottomData() {

    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(VIDEO_URL);
                mVideoView.requestFocus();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("TouguShowH5ActivityNew2");
        MobclickAgent.onPause(this);
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            Logger.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
        }

        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        if (rl_actionbar_title != null) {
            if (show) {
                rl_actionbar_title.setVisibility(View.VISIBLE);
            } else {
                rl_actionbar_title.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_img_left) {
            TouguShowH5ActivityNew2.this.finish();
        } else if (id == R.id.rl_img_right) {
            if (!isHaveShare) {
                RomaToast.showMessage(mActivity, "当前未安装分享平台，如微信");
                return;
            }
            ShareBoardConfig config = new ShareBoardConfig();
            config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
            config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE); // 圆角背景
            mShareAction.open(config);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    private class CustomShareListener implements UMShareListener {

        private Activity mActivity;

        private CustomShareListener(Activity activity) {
            mActivity = activity;
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {
            Logger.d("getUrlBitmap", "platform = " + platform.name());
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                RomaToast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
                RomaToast.showMessage(mActivity, platform + " 收藏成功啦");
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    RomaToast.showMessage(mActivity, platform + " 分享成功啦");
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
//                RomaToast.showMessage(mActivity, platform + " 分享失败啦");
                if (t != null) {
                    Logger.d("throw", "throw:" + t.getMessage());
                    if (!StringUtils.isEmpty(t.getMessage())) {
                        try {
                            String str = t.getMessage().substring(t.getMessage().lastIndexOf("没"), t.getMessage().lastIndexOf("点"));
                            RomaToast.showMessage(mActivity, platform + " 分享失败啦 " + str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            RomaToast.showMessage(mActivity, platform + " 分享取消了");
        }
    }
}
