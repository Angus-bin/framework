package roma.romaway.commons.android.tougu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase2;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedHeaderListView;
import com.handmark.pulltorefresh.library.SectionedBaseAdapter;
import com.romalibs.common.ApiInterface;
import com.romalibs.common.ApiManager;
import com.romalibs.common.ApiProvider;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.R;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.utils.BitmapUtils;
import com.romaway.android.phone.utils.ColorUtils;
import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.PermissionsUtils;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.ShareTimesProtocol;
import com.romaway.common.protocol.service.DLServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.utils.HttpUtils;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.Base64;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romawaylibs.theme.ROMA_SkinManager;
import com.trevorpage.tpsvg.SVGView;
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
import com.youdao.ocr.online.ImageOCRecognizer;
import com.youdao.ocr.online.Line;
import com.youdao.ocr.online.Line_Line;
import com.youdao.ocr.online.OCRListener;
import com.youdao.ocr.online.OCRParameters;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.OcrErrorCode;
import com.youdao.ocr.online.RecognizeLanguage;
import com.youdao.ocr.online.Region;
import com.youdao.ocr.online.Region_Line;
import com.youdao.ocr.online.Word;
import com.youdao.sdk.app.EncryptHelper;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roma.romaway.commons.android.FloatWindow.FloatWindowManager;
import roma.romaway.commons.android.share.ShareManager;
import roma.romaway.commons.android.webkit.JavascriptInterface;
import roma.romaway.commons.android.webkit.ReEventsController;
import roma.romaway.commons.android.webkit.RomaPullToRefreshWebView2;

public class TouguShowH5ActivityNew extends Activity implements ISubTabView, ROMA_SkinManager.OnSkinChangeListener, View.OnClickListener {


	/**
	 * 返回键类型：0：按照web页一页一页返回；1：直接finish Activity;
	 * <p>2: 不销毁,后台缓存Activity;(当前类启动模式需为singleInstance);
	 */
	private int backType = 1;
	private int backType2 = 0; // 询问返回使用
	private int backTypes = 0;
	private Activity mActivity;
	protected TextView closeView;
	private WebView mRomaWebView;
	private RomaPullToRefreshWebView2 mRomaPullToRefreshWebView;
	private RomaPullToRefreshWebView2 mPullToRefreshWebView;
	public ProgressBar mProgressBar;
	public ProgressBar mProgressBar2;

	private UMShareListener mShareListener;
	private ShareAction mShareAction;

	private ReEventsController mReEventController;

	private ApiProvider apiProvider;

	private String value;

	private int type = 0;

	private LinearLayout ll_text, ll_note;
	private TextView tv_plan, tv_book, tv_note;

	private RelativeLayout root;
	private String productID;
	private String readFlag = "1";
	private String msgID;

	private boolean time;
	private String lessDays;

	/**与js数据交互的接口*/
	public TouguJavascripInterface mJsInterface;

	private WebViewClient wvc;
	private WebChromeClient wvcc;

	private boolean isHasFinish = false;

	private boolean isScroll = true;

	private boolean isRefresh = true;

	private boolean isFinished = false;

	private boolean is_home_ztdj = false;

	OCRParameters tps;

	private ProgressDialog progressDialog;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		PushAgent.getInstance(this).onAppStart();
		mActivity = this;
		this.setContentView(R.layout.roma_webview_title_layout_new);

		String url = this.getIntent().getStringExtra("key_h5url");
		String pushUrl = this.getIntent().getStringExtra("key_h5url_push");
		Logger.d("key_h5url_push2", "key_h5url = " + pushUrl);
		setPushUrls(pushUrl);
		//Logger.d("tag", "1-TouguShowH5Activity url:"+url);
		//[需求]：将带参数的url转化成实际的url链接
//		url = UrlParamsManager.getInstance(this).toNewUrl(url);
		//Logger.d("tag", "2-TouguShowH5Activity url:"+url);
        // 推送问题
		SharedPreferenceUtils.setPreference("key_h5url", "key_h5url", "");

		int visibilityTile = this.getIntent().getIntExtra("key_titleVisibility", View.VISIBLE);
		int visibilityRightImg = this.getIntent().getIntExtra("ket_right_img_visibility", View.GONE);
		final String title = this.getIntent().getStringExtra("shareTitle");
		String description = this.getIntent().getStringExtra("shareDescription");
		final String shareUrls = this.getIntent().getStringExtra("shareUrl");
		String newsTitle = this.getIntent().getStringExtra("newsTitle");
		if (StringUtils.isEmpty(shareUrls)) {
			visibilityRightImg = View.GONE;
		}
		setShareUrl(shareUrls);
		setShareTitle(title);
		setShareDescription(description);
		setNewsTitle(newsTitle);
		setTitleVisibility(visibilityTile);
		setRightImgVisibility(visibilityRightImg);

		if (this.getIntent() != null) {
			productID = this.getIntent().getStringExtra("productID");
			RomaSysConfig.setProductID(productID);
			readFlag = this.getIntent().getStringExtra("readFlag");
			msgID = this.getIntent().getStringExtra("msgID");
			time = this.getIntent().getBooleanExtra("time", false);
			lessDays = this.getIntent().getStringExtra("lessDays");
			RomaSysConfig.setTime(time);
			RomaSysConfig.setLessDays(!StringUtils.isEmpty(lessDays) ? lessDays : "");
			isScroll = this.getIntent().getBooleanExtra("isScroll", true);
			isRefresh = this.getIntent().getBooleanExtra("isRefresh", true);
			isShowFloatWindow = this.getIntent().getBooleanExtra("isShowFloatWindow", false);
			module_id = this.getIntent().getStringExtra("module_id");
			is_home_ztdj = this.getIntent().getBooleanExtra("is_home_ztdj", false);
		}

		if (!StringUtils.isEmpty(readFlag) && !StringUtils.isEmpty(msgID)) {
			if (!"1".equals(readFlag)) {
				DLServices.reqUpdateReadFlag(RomaUserAccount.getUserID(), msgID, new Listener(TouguShowH5ActivityNew.this), "read_flag");
			}
		}

		root = (RelativeLayout) this.findViewById(R.id.root);
		ll_text = (LinearLayout) this.findViewById(R.id.ll_text);
		ll_note = (LinearLayout) this.findViewById(R.id.ll_note);
		tv_plan = (TextView) this.findViewById(R.id.tv_plan);
		tv_book = (TextView) this.findViewById(R.id.tv_book);
		tv_note = (TextView) this.findViewById(R.id.tv_note);
		tv_plan.setTextColor(Res.getColor(R.color.gxs_red));
		tv_plan.setOnClickListener(this);
		tv_book.setOnClickListener(this);
		tv_note.setOnClickListener(this);

		backType = this.getIntent().getIntExtra("backType", backTypes);
		type = this.getIntent().getIntExtra("type", 0);
		if (type == 1) {
			ll_text.setVisibility(View.GONE);
		} else {
			ll_text.setVisibility(View.GONE);
		}

		this.setUrl(url);

		closeView = (TextView) this.findViewById(R.id.txt_finish);
		closeView.setTextColor(Res.getColor(R.color.gxs_title));
		closeView.setTextSize(15);
		closeView.setOnClickListener(this);

		Logger.d("TouguShowH5Activity1", "加载完成");

		mRomaWebView = (WebView) this.findViewById(R.id.wv_romaWebView);
		mRomaPullToRefreshWebView = (RomaPullToRefreshWebView2) this.findViewById(R.id.roma_pull_refresh_webview);
		mPullToRefreshWebView = (RomaPullToRefreshWebView2) this.findViewById(R.id.roma_pull_refresh_webview2);
//		mRomaWebView = (WebView) mRomaPullToRefreshWebView.getRefreshableView();
		if (!isScroll) {
			mRomaPullToRefreshWebView.setVisibility(View.GONE);
			mPullToRefreshWebView.setVisibility(View.GONE);
			mRomaWebView.setVisibility(View.VISIBLE);
		} else {
			mRomaPullToRefreshWebView.setVisibility(View.VISIBLE);
			mPullToRefreshWebView.setVisibility(View.GONE);
			mRomaWebView = (WebView) mRomaPullToRefreshWebView.getRefreshableView();
		}
		mProgressBar = (ProgressBar) this.findViewById(R.id.wv_ProgressBar);
		mProgressBar.setVisibility(View.GONE);
		mProgressBar2 = (ProgressBar) this.findViewById(R.id.wv_progressBar_title);
		mProgressBar2.setVisibility(View.VISIBLE);

		mReEventController = new ReEventsController();

		mRomaPullToRefreshWebView.setOnRefreshListener(new PullToRefreshBase2.OnRefreshListener<WebView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase2<WebView> refreshView) {
				Logger.d("HomePageSherlockFragment", "getScrollY = " + refreshView.getScrollY());
				isShareBack = false;
				isFinished = false;
				if(mRomaWebView != null) {
					//500毫秒内不允许刷新
					if (mReEventController.isRepeat(mRomaWebView, 500)) {
						return;
					}
					if (!StringUtils.isEmpty(mRomaWebView.getUrl())) {
						Logger.d("key_h5url", "loadUrl = " + mRomaWebView.getUrl());
						mRomaWebView.loadUrl(mRomaWebView.getUrl());
					} else {
						if (isRefresh) {
							isReload = true;
						}
						if(mRomaPullToRefreshWebView != null)
							mRomaPullToRefreshWebView.onPullDownRefreshComplete();

						if(mRomaPullToRefreshWebView != null)
							mRomaPullToRefreshWebView.setLastUpdateTime();

						if (mProgressBar2 != null)
							mProgressBar2.setVisibility(View.GONE);
					}
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase2<WebView> refreshView) {

			}

		});

		if(mRomaPullToRefreshWebView != null)
			mRomaPullToRefreshWebView.setLastUpdateTime();

//		mJsInterface = new TouguJavascripInterface(this, mRomaWebView);
//
//		//不可以删除该项，不然登录无法跳转
//		this.addJavascriptInterface(mJsInterface);

		//添加通用的接口API
		apiProvider = (new ApiManager("CommonApiProvider")).getApiProvider();
		apiProvider.getSettings().setEnableWebView(mRomaWebView);//设置该项可支持原生给webView回传数据
		apiProvider.getSettings().setActivity(mActivity);

		if(apiProvider != null) {
			mRomaWebView.addJavascriptInterface(apiProvider, ApiInterface.JS_BRIDGE_NATIVE_NAME);
			if (Build.VERSION.SDK_INT < 17) {
				mRomaWebView.removeJavascriptInterface("accessibility");
				mRomaWebView.removeJavascriptInterface("accessibilityTraversal");
				mRomaWebView.removeJavascriptInterface("searchBoxJavaBridge_");
			}
		}

//		WebSettings webSettings = mRomaWebView.getSettings();
//		webSettings.setJavaScriptEnabled(true);
//		webSettings.setAllowFileAccess(true);
//		webSettings.setAllowFileAccessFromFileURLs(true);
//		webSettings.setAllowUniversalAccessFromFileURLs(true);
//		webSettings.setAppCacheEnabled(true);
//		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//		webSettings.setPluginsEnabled(true);  //支持插件
////		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////			webSettings.setPluginState(WebSettings.PluginState.ON);
////		}
//		// 设置自适应屏幕，两者合用
//		webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
//		webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//		webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH); // 设置这个主要是用于流畅的滑动
//
//		mRomaWebView.setWebChromeClient(new WebChromeClient() {
//			@Override
//			public void onReceivedTitle(WebView view, String title) {
//				// TODO Auto-generated method stub
//				super.onReceivedTitle(view, title);
//				setTitle(title);
//			}
//
//			public void onProgressChanged(WebView view, int newProgress) {
//				if (newProgress == 100) {
//					mProgressBar.setVisibility(View.GONE);
//					mProgressBar2.setVisibility(View.GONE);
//				} else {
////					mProgressBar2.setVisibility(View.VISIBLE);
//					mProgressBar.setVisibility(View.GONE);
//					mProgressBar2.setProgress(newProgress);
//				}
//
//				super.onProgressChanged(view, newProgress);
//			}
//
//		});
//		mRomaWebView.setWebViewClient(new WebViewClient() {
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				super.onPageFinished(view, url);
//				if(mRomaPullToRefreshWebView != null)
//					mRomaPullToRefreshWebView.onPullDownRefreshComplete();
//
//				if(mRomaPullToRefreshWebView != null)
//					mRomaPullToRefreshWebView.setLastUpdateTime();
//
//				int handleBackType = getIntent().getIntExtra("backType", backTypes);
//
//				// backType = 1为直接finish Activity, 则无需显示关闭按钮;
//				if (view != null && view.canGoBack() && handleBackType != 1) {
//					closeView.setVisibility(View.VISIBLE);
//				} else {
//					closeView.setVisibility(View.GONE);
//				}
//
//				BaseJSONObject jsonObject = new BaseJSONObject();
//				jsonObject.put("user_id", RomaUserAccount.getUserID());
//
//				final BaseJSONObject json;
//				try {
//					json = new BaseJSONObject(jsonObject.toString());
//					mRomaWebView.post(new Runnable() {
//						@Override
//						public void run() {
//							mRomaWebView.loadUrl("javascript:getUserInfo('" + json + "')");
//						}
//					});
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//
//				Logger.d("TouguShowH5Activity2", "加载完成");
//				TouguShowH5ActivityNew.this.findViewById(R.id.svg_back).setEnabled(true);
//				handlePageFinished(url, getIntent());
//			}
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				return super.shouldOverrideUrlLoading(view, url);
//			}
//		});

		wvcc = new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				setTitle(title);
				Logger.d("key_h5url", "onReceivedTitle---Title = " + title);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				Logger.d("TouguShowH5ActivityNew", "newProgress = " + newProgress);
				if (newProgress == 100) {
//					Logger.d("TouguShowH5ActivityNew", "Title = " + view.getTitle());
//					setTitle(view.getTitle());
					mProgressBar.setVisibility(View.GONE);
					mProgressBar2.setVisibility(View.GONE);
				} else {
					mProgressBar2.setVisibility(View.VISIBLE);
					mProgressBar.setVisibility(View.GONE);
					mProgressBar2.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

			/*** 视频播放相关的方法 **/

			@Override
			public View getVideoLoadingProgressView() {
				Logger.d("TouguShowH5ActivityNew", "getVideoLoadingProgressView");
				FrameLayout frameLayout = new FrameLayout(TouguShowH5ActivityNew.this);
				frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				return frameLayout;
			}

			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				Logger.d("TouguShowH5ActivityNew", "onShowCustomView");
				showCustomView(view, callback);
				backType = 0;
			}

			@Override
			public void onHideCustomView() {
				Logger.d("TouguShowH5ActivityNew", "onHideCustomView");
				hideCustomView();
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

			}

		};
		WebSettings webSettings = mRomaWebView.getSettings();
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

		final int finalVisibilityRightImg = visibilityRightImg;
		wvc = new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Logger.d("key_h5url", "onPageFinished = " + url);
				if (!isRefresh) {
					isFinished = true;
				}
				if (!StringUtils.isEmpty(url)) {
					if (!isHasShareUrl) {
						if (url.contains("?")) {
							if (url.contains("user_id")) {
								shareUrl = url + "&share=1";
							} else {
								shareUrl = url + "&user_id=" + RomaUserAccount.getUserID() + "&share=1";
							}
						} else {
							if (url.contains("user_id")) {
								shareUrl = url + "&share=1";
							} else {
								shareUrl = url + "?user_id=" + RomaUserAccount.getUserID() + "&share=1";
							}
						}
					}
					if ("webTitle".equals(shareTitle) || "stock".equals(shareTitle)) {
						setShareTitle(mRomaWebView.getTitle());
					}
					if (StringUtils.isEmpty(productID) && url.contains("pro_id")) {
						productID = Uri.parse(url).getQueryParameter("pro_id");
						Logger.d("key_h5url", "value = " + value);
						RomaSysConfig.setProductID(productID);
					}
				}
				Logger.d("key_h5url", "onPageFinished---shareUrl = " + shareUrl);
				if (isRefresh) {
					isReload = true;
				}
				if(mRomaPullToRefreshWebView != null)
					mRomaPullToRefreshWebView.onPullDownRefreshComplete();

				if(mRomaPullToRefreshWebView != null)
					mRomaPullToRefreshWebView.setLastUpdateTime();

				if (mProgressBar2 != null)
					mProgressBar2.setVisibility(View.GONE);

				int handleBackType = getIntent().getIntExtra("backType", backTypes);

				Logger.d("TouguShowH5Activity", "visibilityRightImg = " + finalVisibilityRightImg);
				if (finalVisibilityRightImg == 0) {
					rl_img_right.setVisibility(View.VISIBLE);
					img_right_icon.setVisibility(View.VISIBLE);
				} else if (finalVisibilityRightImg == 8) {
					rl_img_right.setVisibility(View.GONE);
					img_right_icon.setVisibility(View.GONE);
				}

				if (type == 1) {
					rl_img_right2.setVisibility(View.GONE);
					svg_right_icon1.setVisibility(View.GONE);
				} else {
					rl_img_right2.setVisibility(View.GONE);
					svg_right_icon1.setVisibility(View.GONE);
				}

				// backType = 1为直接finish Activity, 则无需显示关闭按钮;
				if (view != null && view.canGoBack() && handleBackType != 1) {
					closeView.setVisibility(View.VISIBLE);
					isHasFinish = true;
				} else {
					closeView.setVisibility(View.GONE);
					backTypes = 0;
					isHasFinish = false;
				}

				setShareTitle(mRomaWebView.getTitle());
				setTitle(mRomaWebView.getTitle());
				Logger.d("key_h5url", "onPageFinished---Title = " + mRomaWebView.getTitle());

				if (!StringUtils.isEmpty(mRomaWebView.getTitle())) {
					if (mRomaWebView.getTitle().contains("涨停点睛")) {
//						rl_img_right.setVisibility(View.GONE);
//						img_right_icon.setVisibility(View.GONE);
//						if (txt_right != null) {
//							txt_right.setVisibility(View.VISIBLE);
//							txt_right.setText("历史战绩");
//							txt_right.setTextColor(Res.getColor(R.color.gxs_sub_title));
//						}
						if (!SharedPreferenceUtils.getPreference("App_help", "App_help7", false)) {
							Bundle bundle = new Bundle();
							bundle.putInt("help_type", 2);
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.guide.HomePageHelpActivity", bundle, false);
						}
					}
				}

				BaseJSONObject jsonObject = new BaseJSONObject();
				jsonObject.put("user_id", RomaUserAccount.getUserID());

				final BaseJSONObject json;
				try {
					json = new BaseJSONObject(jsonObject.toString());
					mRomaWebView.post(new Runnable() {
						@Override
						public void run() {
							mRomaWebView.loadUrl("javascript:getUserInfo('" + json + "')");
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (isShareBack) {
					isShareBack = false;
					final BaseJSONObject jsonObject1;
					try {
						jsonObject1 = new BaseJSONObject(jsonObject.toString());
						mRomaWebView.post(new Runnable() {
							@Override
							public void run() {
								mRomaWebView.loadUrl("javascript:isCheckShare('" + jsonObject1 + "')");
							}
						});
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (mPlatform != null) {
						if (!StringUtils.isEmpty(mPlatform.name())) {
							RomaToast.showMessage(mActivity, "分享成功啦");
						}
					}
				}

				Logger.d("TouguShowH5Activity2", "加载完成");
				TouguShowH5ActivityNew.this.findViewById(R.id.ll_img_left).setEnabled(true);
				handlePageFinished(url, getIntent());
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Logger.d("ROMA_Native", "loadUrl = " + url);
				if (url.contains("http")) {
					Logger.d("TouguShowH5ActivityNew", "url = " + url);
					Logger.d("key_h5url", "loadUrl = " + url);
					if (url.contains("product/showDiagnosis")) {
						Bundle bundle = new Bundle();
						bundle.putString("key_h5url", url);
						bundle.putInt("ket_right_img_visibility", View.VISIBLE);
						Logger.d("key_h5url", "key_h5url = " + url);
						bundle.putString("shareUrl", url + "&share=1");
						bundle.putString("shareTitle", "webTitle");
						bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
						KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
					} else {
						mRomaWebView.loadUrl(url);
					}
				}
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Logger.d("key_h5url", "errorCode = " + errorCode);
				Logger.d("key_h5url", "description = " + description);
				Logger.d("key_h5url", "failingUrl = " + failingUrl);
			}

			// 处理广告问题
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
				url= url.toLowerCase();
				Logger.d("key_h5url", "WebResourceResponse = " + url);
				if (!url.contains(getUrl())) {
					Logger.d("key_h5url", "WebResourceResponse = " + url);
					if (!ADFilterTool.hasAd(OriginalContext.getContext(), url)) {
						Logger.d("key_h5url", "WebResourceResponse = " + url);
						return super.shouldInterceptRequest(view, url);
					} else {
						return new WebResourceResponse(null, null, null);
					}
				} else {
					return super.shouldInterceptRequest(view, url);
				}
			}
		};
		mRomaWebView.setWebViewClient(wvc);
		mRomaWebView.setWebChromeClient(wvcc);

		String url1 = getUrl();

		mRomaWebView.loadUrl(url1);

		Logger.d("key_h5url", "loadUrl = " + url1);

		Logger.d("TouguShowH5Activity", "setUrl:" + url + ",getUrl:" + url1);

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
				public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
					Logger.d("ShareAction", "snsPlatform.mShowWord = " + snsPlatform.mShowWord + "   share_media = " + share_media + "");

//				if (mRomaWebView != null && !StringUtils.isEmpty(mRomaWebView.getUrl())) {
					if (StringUtils.isEmpty(shareUrl)) {
						RomaToast.showMessage(TouguShowH5ActivityNew.this, "分享链接为空，请重试");
						mShareAction.close();
						return;
					}
//					if ("webTitle".equals(shareTitle) || "stock".equals(shareTitle)) {
//						setShareTitle(mRomaWebView.getTitle());
////						setShareUrl(mRomaWebView.getUrl() + "/share/1");
//					}
					if (share_media == SHARE_MEDIA.SINA) {
						if (!StringUtils.isEmpty(text)) {
							if (!StringUtils.isEmpty(imageUrl)) {
								new ShareAction(mActivity).withText(TouguShowH5ActivityNew.this.text + shareUrl + " @容维财经APP#容维财经app##新浪理财师##牛股推荐##上证快讯##今日看盘##投资达人说#@微博股票").withMedia(new UMImage(TouguShowH5ActivityNew.this, imageUrl))
										.setPlatform(share_media)
										.setCallback(mShareListener)
										.share();
							} else {
								new ShareAction(mActivity).withText(TouguShowH5ActivityNew.this.text + shareUrl + " @容维财经APP#容维财经app##新浪理财师##牛股推荐##上证快讯##今日看盘##投资达人说#@微博股票")
										.withMedia(!StringUtils.isEmpty(logoUrl) ? new UMImage(TouguShowH5ActivityNew.this, logoUrl) : new UMImage(TouguShowH5ActivityNew.this, R.drawable.roma_sicon))
										.setPlatform(share_media)
										.setCallback(mShareListener)
										.share();
							}
						} else {
							UMWeb web = new UMWeb(shareUrl);
							web.setTitle(!StringUtils.isEmpty(shareTitle) ? shareTitle : "容维财经");
							web.setDescription(!StringUtils.isEmpty(text) ? text : !StringUtils.isEmpty(shareDescription) ? shareDescription : "容维财经-大数据决策帮您炒股");
							web.setThumb(!StringUtils.isEmpty(imageUrl) ? new UMImage(mActivity, imageUrl) : !StringUtils.isEmpty(logoUrl) ? new UMImage(mActivity, logoUrl) : new UMImage(mActivity, R.drawable.roma_sicon));
							new ShareAction(mActivity).withMedia(web)
									.setPlatform(share_media)
									.setCallback(mShareListener)
									.share();
						}
						return;
					}
					UMWeb web = new UMWeb(shareUrl);
					web.setTitle(!StringUtils.isEmpty(shareTitle) ? shareTitle : "容维财经");
					web.setDescription(!StringUtils.isEmpty(text) ? text : !StringUtils.isEmpty(shareDescription) ? shareDescription : "容维财经-大数据决策帮您炒股");
					web.setThumb(!StringUtils.isEmpty(imageUrl) ? new UMImage(mActivity, imageUrl) : !StringUtils.isEmpty(logoUrl) ? new UMImage(mActivity, logoUrl) : new UMImage(mActivity, R.drawable.roma_sicon));
					new ShareAction(mActivity).withMedia(web)
							.setPlatform(share_media)
							.setCallback(mShareListener)
							.share();
//					new ShareAction(mActivity).withMedia(new UMImage(TouguShowH5ActivityNew.this, getWebViewBp(mRomaWebView)))
//							.setPlatform(share_media)
//							.setCallback(mShareListener)
//							.share();
//				}
				}
			});
		}

		tps = new OCRParameters.Builder().source("youdaoocr").timeout(100000)
				.type(OCRParameters.TYPE_LINE).lanType(RecognizeLanguage.LINE_CHINESE_ENGLISH.getCode()).build();

		progressDialog = new ProgressDialog(mActivity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

	}

	private boolean isHaveShare = false;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private String text, imageUrl, logoUrl;

	private boolean isHasShareUrl = false;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("text")) {
				text = jsonObject.getString("text");
			}
			if (jsonObject.has("image_url")) {
				imageUrl = jsonObject.getString("image_url");
			}
			if (jsonObject.has("logo_url")) {
				logoUrl = jsonObject.getString("logo_url");
			}
			if (jsonObject.has("share_url")) {
				if (!StringUtils.isEmpty(jsonObject.getString("share_url"))) {
					isHasShareUrl = true;
					setShareUrl(jsonObject.getString("share_url"));
				}
			} else {
				isHasShareUrl = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger.d("TouguShowH5Activity", "this.value = " + this.value);
	}

	public String getProductID() {
		return productID;
	}

	public boolean isTime() {
		return time;
	}

	public String getLessDays() {
		return lessDays;
	}

	/**
	 * 添加与H5交互
	 *
	 * @param javascriptInterface
	 */
	public void addJavascriptInterface(JavascriptInterface javascriptInterface) {

		if (javascriptInterface != null && mRomaWebView != null) {

			Logger.i("WebkitSherlockFragment",
					"addJavascriptInterface true:interfaceName:"
							+ javascriptInterface.getInterfaceName());

			mRomaWebView.addJavascriptInterface(javascriptInterface,
					javascriptInterface.getInterfaceName());
			if (Build.VERSION.SDK_INT < 17) {
				mRomaWebView.removeJavascriptInterface("accessibility");
				mRomaWebView.removeJavascriptInterface("accessibilityTraversal");
				mRomaWebView.removeJavascriptInterface("searchBoxJavaBridge_");
			}

		} else {
			Logger.i("WebkitActivity",
					"addJavascriptInterface false javascriptInterface:"
							+ javascriptInterface + ",RomaWebView:"
							+ mRomaWebView);
		}
	}

	private int visibility = View.GONE;
	public void setTitleVisibility(int visibility){
		this.visibility = visibility;
	}

	public void setTitle(String title){
		if(txt_title != null)
			if (!StringUtils.isEmpty(title)) {
				if (!isHasFinish) {
					if (title.length() > 15) {
						title = title.substring(0, 15) + "...";
					}
				} else {
					if (title.length() > 12) {
						title = title.substring(0, 10) + "...";
					}
				}
				if ("newsTitle".equals(newsTitle)) {
					txt_title.setText("详情");
				} else {
					txt_title.setText(title);
				}
			} else {
				if ("newsTitle".equals(newsTitle)) {
					txt_title.setText("详情");
				} else {
					txt_title.setText("");
				}
			}
	}

	private int ActionBarColor = 0xff333333;
	private int BackColor = 0xff333333;
	private int ShareColor = 0xff333333;

	public void setTitleBg(int color){
		ActionBarColor = color;
		handler.sendEmptyMessage(1);
	}

	public void setBackBg(int color){
		BackColor = color;
		handler.sendEmptyMessage(2);
	}

	public void setShareBg(int color){
		ShareColor = color;
		handler.sendEmptyMessage(3);
	}

	private String mUrl;

	public void setUrl(String url) {
		mUrl = url;
	}

	public String getUrl() {
		return mUrl;
	}

	private String shareTitle;

	private String shareDescription;

	private String shareUrl;

	private String newsTitle;

	public void setShareUrl(String shareUrl) {
		Logger.d("key_h5url", "setShareUrl---shareUrl = " + shareUrl);
		this.shareUrl = shareUrl;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	private String pushUrls;

	public void setPushUrls(String pushUrl) {
		this.pushUrls = pushUrl;
	}

	private int visibilityRightImg = View.GONE;
	public void setRightImgVisibility(int visibility){
		this.visibilityRightImg = visibility;
	}

	protected RelativeLayout rl_img_right, rl_img_right2;
	protected ImageView img_right_icon, svg_right_icon1;
	protected RelativeLayout rl_title_layout;
	protected SVGView svg_back;
	protected TextView txt_title, txt_right;

	private ImageView img_left;

	private LinearLayout ll_img_left;

	private boolean isReload = false;

	public boolean isReload() {
		return isReload;
	}

	public void setReload(boolean reload) {
		isReload = reload;
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("TouguShowH5ActivityNew");
		MobclickAgent.onResume(this);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				showFloatWindow();
			}
		}, 3000);
		//++ 处理title
		rl_title_layout = (RelativeLayout) this.findViewById(R.id.rl_title_layout);
		rl_title_layout.setVisibility(visibility);

		if (isReload) {
			if (mRomaWebView != null) {
				mRomaWebView.onResume();
				mRomaWebView.setWebChromeClient(wvcc);
				mRomaWebView.setWebViewClient(wvc);
				mRomaWebView.getSettings().setJavaScriptEnabled(true);
				if (!StringUtils.isEmpty(mRomaWebView.getUrl())) {
					Logger.d("key_h5url", "loadUrl1 = " + mRomaWebView.getUrl());
					mRomaWebView.loadUrl(mRomaWebView.getUrl());
				}
			}
		}

		if(visibility == View.VISIBLE){
			rl_title_layout.setBackgroundColor(Res.getColor(R.color.gxs_while));
//			svg_back = (SVGView) this.findViewById(R.id.svg_back);
//			svg_back.setSVGRenderer(new SVGParserRenderer(this, R.drawable.abs__navigation_back), "");
//			svg_back.setOnClickListener(this);
			img_left = (ImageView) findViewById(R.id.img_left);
			img_left.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.title_back)), Res.getColor(R.color.gxs_title)));
			img_left.setVisibility(View.VISIBLE);
			ll_img_left = (LinearLayout) findViewById(R.id.ll_img_left);
			ll_img_left.setOnClickListener(this);
			txt_title = (TextView) this.findViewById(R.id.txt_title);
			txt_right = (TextView) this.findViewById(R.id.txt_right);
			txt_right.setOnClickListener(this);
		}
		Logger.d("TouguShowH5Activity3", "加载完成");
		TouguShowH5ActivityNew.this.findViewById(R.id.ll_img_left).setEnabled(true);
		rl_img_right = (RelativeLayout) TouguShowH5ActivityNew.this.findViewById(R.id.rl_img_right);
		rl_img_right2 = (RelativeLayout) TouguShowH5ActivityNew.this.findViewById(R.id.rl_img_right2);
		img_right_icon = (ImageView) TouguShowH5ActivityNew.this.findViewById(R.id.img_right_icon);
		svg_right_icon1 = (ImageView) TouguShowH5ActivityNew.this.findViewById(R.id.svg_right_icon1);
		svg_right_icon1.setImageResource(R.drawable.img_qswy_ask);
//		rl_img_right.setVisibility(visibilityRightImg);
		img_right_icon.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.roma_share_icon)), Res.getColor(R.color.gxs_title)));
//		img_right_icon.setVisibility(visibilityRightImg);
		rl_img_right.setOnClickListener(this);
		rl_img_right2.setOnClickListener(this);
		onSkinChanged(null);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.svg_back){//web后退键
			if (backType2 == 1) {
				backType2 = 0;
				root.setVisibility(View.VISIBLE);
				ll_note.setVisibility(View.GONE);
				tv_plan.setTextColor(Res.getColor(R.color.gxs_red));
				tv_book.setTextColor(Res.getColor(R.color.gxs_sub_title));
				tv_note.setTextColor(Res.getColor(R.color.gxs_sub_title));
				String ip = "";
				if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
					ip = RomaSysConfig.getIp();
				} else {
					ip = Res.getString(R.string.NetWork_IP);
				}
				String url = ip + "product/desc?pro_id=" + productID +"&user_id=" + RomaUserAccount.getUserID();
				mRomaWebView.loadUrl(url);
				backTypes = 1;
			} else {
				handleBackAction();
			}
		} else if (v.getId() == R.id.ll_img_left) {
			if (backType2 == 1) {
				backType2 = 0;
				root.setVisibility(View.VISIBLE);
				ll_note.setVisibility(View.GONE);
				tv_plan.setTextColor(Res.getColor(R.color.gxs_red));
				tv_book.setTextColor(Res.getColor(R.color.gxs_sub_title));
				tv_note.setTextColor(Res.getColor(R.color.gxs_sub_title));
				String ip = "";
				if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
					ip = RomaSysConfig.getIp();
				} else {
					ip = Res.getString(R.string.NetWork_IP);
				}
				String url = ip + "product/desc?pro_id=" + productID +"&user_id=" + RomaUserAccount.getUserID();
				mRomaWebView.loadUrl(url);
				backTypes = 1;
			} else {
				handleBackAction();
			}
		} else if(v.getId() == R.id.txt_finish){
			switch (backType) {
			case 2:
				moveTaskToBack(true);
				// 将另一组进程中Activity Task队列移至前台:
				moveTaskToFront();
				break;
			default:
				this.finish();
				break;
			}
		} else if (v.getId() == R.id.rl_img_right) {// 分享
			if (RomaUserAccount.isGuest()) {
				SharedPreferenceUtils.setPreference("MainActivity", "0", false);
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", null, false);
				return;
			}
			if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", null, false);
				return;
			}
			if (is_home_ztdj) {
				openHtmlShare("2");
			} else {
				openHtmlShare("0");
			}
//			ShareBoardConfig config = new ShareBoardConfig();
//			config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
//			config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE); // 圆角背景
////                config.setTitleVisibility(false); // 隐藏title
////                config.setCancelButtonVisibility(false); // 隐藏取消按钮
//			mShareAction.open(config);
		} else if (v.getId() == R.id.tv_plan) {
			root.setVisibility(View.VISIBLE);
			ll_note.setVisibility(View.GONE);
			tv_plan.setTextColor(Res.getColor(R.color.gxs_red));
			tv_book.setTextColor(Res.getColor(R.color.gxs_sub_title));
			tv_note.setTextColor(Res.getColor(R.color.gxs_sub_title));
			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			String url = ip + "product/desc?pro_id=" + productID +"&user_id=" + RomaUserAccount.getUserID();
			mRomaWebView.loadUrl(url);
			backTypes = 1;
		} else if (v.getId() == R.id.tv_book) {
			root.setVisibility(View.VISIBLE);
			ll_note.setVisibility(View.GONE);
			tv_book.setTextColor(Res.getColor(R.color.gxs_red));
			tv_plan.setTextColor(Res.getColor(R.color.gxs_sub_title));
			tv_note.setTextColor(Res.getColor(R.color.gxs_sub_title));
			String ip = "";
			if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
				ip = RomaSysConfig.getIp();
			} else {
				ip = Res.getString(R.string.NetWork_IP);
			}
			String url = ip + "product/intro?pro_id=" + productID +"&user_id=" + RomaUserAccount.getUserID();
			mRomaWebView.loadUrl(url);
			backTypes = 1;
			backType = 1;
		} else if (v.getId() == R.id.tv_note) {
			root.setVisibility(View.GONE);
			ll_note.setVisibility(View.VISIBLE);
			setTitle("订阅须知");
			tv_note.setTextColor(Res.getColor(R.color.gxs_red));
			tv_book.setTextColor(Res.getColor(R.color.gxs_sub_title));
			tv_plan.setTextColor(Res.getColor(R.color.gxs_sub_title));
			setNoteData();
		} else if (v.getId() == R.id.rl_img_right2) { // 询问
			backType2 = 1;
			root.setVisibility(View.GONE);
			ll_note.setVisibility(View.VISIBLE);
			setTitle("订阅须知");
			setNoteData();
		} else if (v.getId() == R.id.txt_right) {
			KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.RomaLimitUpHistoryActivity", null, false);
		}
	}

	public void openHtmlShare(String shareType) { // 0:默认，1：仅微信好友，2：仅朋友圈，3：仅新浪，4：仅QQ，5：仅QQ空间

		if (StringUtils.isEmpty(shareType)) {
			return;
		}

		if (!isHaveShare) {
			RomaToast.showMessage(mActivity, "当前未安装分享平台，如微信");
			return;
		}

		SHARE_MEDIA share_media = null;

//		SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
//				SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE

		if (StringUtils.compare(shareType, "0") > 0) {
			if ("1".equals(shareType)) {
				share_media = SHARE_MEDIA.WEIXIN;
			} else if ("2".equals(shareType)) {
				share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
			} else if ("3".equals(shareType)) {
				share_media = SHARE_MEDIA.SINA;
			} else if ("4".equals(shareType)) {
				share_media = SHARE_MEDIA.QQ;
			} else if ("5".equals(shareType)) {
				share_media = SHARE_MEDIA.QZONE;
			} else {
				return;
			}
			UMWeb web = new UMWeb(shareUrl);
			web.setTitle(!StringUtils.isEmpty(shareTitle) ? shareTitle : "容维财经");
			web.setDescription(!StringUtils.isEmpty(text) ? text : !StringUtils.isEmpty(shareDescription) ? shareDescription : "容维财经-大数据决策帮您炒股");
			web.setThumb(!StringUtils.isEmpty(imageUrl) ? new UMImage(mActivity, imageUrl) : !StringUtils.isEmpty(logoUrl) ? new UMImage(mActivity, logoUrl) : new UMImage(mActivity, R.drawable.roma_sicon));
			new ShareAction(mActivity).withMedia(web)
					.setPlatform(share_media)
					.setCallback(mShareListener)
					.share();
		} else {
			ShareBoardConfig config = new ShareBoardConfig();
			config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
			config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE); // 圆角背景
//                config.setTitleVisibility(false); // 隐藏title
//                config.setCancelButtonVisibility(false); // 隐藏取消按钮
			mShareAction.open(config);
		}
	}

	/** Web页面后退类型管理栈表 */
	public Map<String, String> urlBackTypeMap;

	public int getBackType() {
		return backType;
	}

	private void handleBackAction() {
		int handleBackType = backType;
//		try {
//			//[需求]添加支持设置Url后退类型栈 判断当前Url的后退处理方式:
//			if (urlBackTypeMap != null && !urlBackTypeMap.isEmpty()) {
//				String cutUrl = mRomaWebView.getUrl();
//				if (!TextUtils.isEmpty(cutUrl)) {
//					// "0"为逐步后退(Web历史记录栈), "1"为直接关闭(遇到此地址直接关闭Activity);
//					String backType = null;
//					// 判断 后退类型Map表里是否包含指定 urlKey 键:
//					if (urlBackTypeMap.containsKey(cutUrl)) {
//						backType = urlBackTypeMap.get(cutUrl);
//					} else {
//						Set<String> urlKeys = urlBackTypeMap.keySet();
//						for (String urlKey : urlKeys) {
//							if (cutUrl.contains(urlKey)) {
//								backType = urlBackTypeMap.get(urlKey);
//								break;
//							}
//						}
//					}
//
//					if (!TextUtils.isEmpty(backType) && NumberUtils.isNumber(backType)) {
//						handleBackType = Integer.parseInt(backType);
//					}
//				}
//			}
//		}catch (Exception e){
//			Logger.e("TAG", e.getMessage());
//		}


		switch (handleBackType) {
        case 1:				// 直接finish;
            this.finish();
            break;
        case 2: 			// 关闭不销毁;
			TouguShowH5ActivityNew.this.findViewById(R.id.ll_img_left).setEnabled(false); // 避免重复点击，在返回后的界面重新设为可点击
            if(mRomaWebView != null && mRomaWebView.canGoBack()){
				mRomaWebView.goBack();
                break;
            }
            moveTaskToBack(true);
			// 将另一组进程中Activity Task队列移至前台:
			moveTaskToFront();
			break;
        case 0:
        default:			// 逐级关闭, 最后finish;
            if(mRomaWebView != null && mRomaWebView.canGoBack()){
				mRomaWebView.goBack();
                break;
            }
            this.finish();
        }
	}

	/**
	 * 将当前App的另一组进程的 Activity Task队列移至前台:
	 */
	public void moveTaskToFront() {
		//获取ActivityManager
		ActivityManager mAm = (ActivityManager) OriginalContext.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		//获得当前运行的task
		List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
		for (ActivityManager.RunningTaskInfo rti : taskList) {
            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
            if(rti.topActivity.getPackageName().equals(OriginalContext.getContext().getPackageName())) {
                mAm.moveTaskToFront(rti.id,0);
                return;
            }
        }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (backType == 1)
			return super.onKeyDown(keyCode, event);

		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// 系统软键盘返回键统一以顶部返回键事件处理:
			/** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
			if (customView != null) {
				hideCustomView();
			} /*else if (mRomaWebView.canGoBack()) {
				mRomaWebView.goBack();
			} */else {
				handleBackAction();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onDestroy() {
		if (mRomaWebView != null) {
			mRomaWebView.setWebChromeClient(null);
			mRomaWebView.setWebViewClient(null);
			mRomaWebView.getSettings().setJavaScriptEnabled(false);
			mRomaWebView.clearCache(true);
		}

		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	protected void onPause() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (isReload) {
				if (mRomaWebView != null) {
					mRomaWebView.onPause();
//				mRomaWebView.setWebChromeClient(null);
//				mRomaWebView.setWebViewClient(null);
//				mRomaWebView.getSettings().setJavaScriptEnabled(false);
//				mRomaWebView.clearCache(true);
				}
			}
		}
		isShowFloatWindow = false;
		hideFloatWindow();
		super.onPause();
		MobclickAgent.onPageEnd("TouguShowH5ActivityNew");
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean AddZixuan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delZiXuan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fastTrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public Activity getCurrentAct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFromID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getModeID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEnableFastTrade() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrentSubView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showOrHideAddStock(boolean arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * 仅第一次页面加载完毕时
	 */
	private boolean isFirstPageFinished = false;

	/**
	 * 处理Web/H5页面加载完毕回调方法:
	 */
	protected void handlePageFinished(String url, Intent intent){
		String functionCode = intent.getStringExtra("functionCode");
		if (!TextUtils.isEmpty(functionCode) && "action.ticket.loginCallback".equalsIgnoreCase(intent.getAction()) && functionCode.startsWith("KDS_TICKET_NO")){
			String custid = intent.getStringExtra("custid");
			String fundid = intent.getStringExtra("fundid");
			String userrole = intent.getStringExtra("userrole");
			String imei = SysConfigs.IMEI;
			String orgid = intent.getStringExtra("orgid");
			String ticket = intent.getStringExtra("ticket");
			String ip = SysConfigs.IP;
			String targetUrl = intent.getStringExtra("targetUrl");
//			setUserData(custid, fundid,userrole, imei, orgid, ticket, ip, targetUrl);
			return;
		}

		if (!TextUtils.isEmpty(functionCode) && !"ROMA_YouWen_YWYD".equalsIgnoreCase(functionCode)){
			if (!isFirstPageFinished){
				String custid = intent.getStringExtra("custid");
				String fundid = intent.getStringExtra("fundid");
				String userrole = intent.getStringExtra("userrole");
				String imei = SysConfigs.IMEI;
				String orgid = intent.getStringExtra("orgid");
				String ticket = intent.getStringExtra("ticket");
				String ip = SysConfigs.IP;
				String targetUrl = intent.getStringExtra("targetUrl");
//				setUserDataAndTargetUrl(custid, fundid,userrole, imei, orgid, ticket, ip, targetUrl);
				isFirstPageFinished = true;
			}
		}
	}

	private boolean isShareBack = false;
	private SHARE_MEDIA mPlatform;

	private class CustomShareListener implements UMShareListener {

		private Activity mActivity;

		private CustomShareListener(Activity activity) {
			mActivity = activity;
		}

		@Override
		public void onStart(SHARE_MEDIA platform) {

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
					Logger.d("CustomShareListener", "platform = " + platform.name());
					RomaToast.showMessage(mActivity, platform + " 分享成功啦");
					isShareBack = true;
					mPlatform = platform;
					if (isFinished) {
						BaseJSONObject jsonObject = new BaseJSONObject();
						jsonObject.put("user_id", RomaUserAccount.getUserID());

						final BaseJSONObject json;
						try {
							json = new BaseJSONObject(jsonObject.toString());
							mRomaWebView.post(new Runnable() {
								@Override
								public void run() {
									mRomaWebView.loadUrl("javascript:isCheckShare('" + json + "')");
								}
							});
							if (mPlatform != null) {
								Logger.d("CustomShareListener", "platform1 = " + platform.name());
								if (!StringUtils.isEmpty(mPlatform.name())) {
									Logger.d("CustomShareListener", "platform2 = " + platform.name());
									RomaToast.showMessage(OriginalContext.getContext(), "分享成功啦");
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
//					if (!RomaUserAccount.isGuest()) {
//						if (RomaUserAccount.getShareTimes() > 0) {
//							return;
//						}
//						DLServices.rz_share_times(RomaUserAccount.getUserID(), new ShareTimesListener(mActivity), "share_times");
//					}
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
//				RomaToast.showMessage(mActivity, platform + " 分享失败啦");
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

	private class ShareTimesListener extends UINetReceiveListener {

		/**
		 * @param activity
		 */
		public ShareTimesListener(Activity activity) {
			super(activity);
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			super.onSuccess(msg, ptl);
			ShareTimesProtocol protocol = (ShareTimesProtocol) ptl;
			if (!StringUtils.isEmpty(protocol.resp_errorCode) && "0".equals(protocol.resp_errorCode)) {
				if (!StringUtils.isEmpty(pushUrls)) {
					if (mRomaWebView != null) {
						backType = 1;
						backTypes = 1;
						mRomaWebView.loadUrl(pushUrls);
					}
				} else {
//					TouguShowH5ActivityNew.this.finish();
				}
			}
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
		}
	}

	@Override
	public void onSkinChanged(String skinTypeKey) {
//		if (ACTIVITY_TYPE_PUSH_MSG.equalsIgnoreCase(getIntent().getStringExtra(ACTIVITY_TYPE))) {
//			if (Res.getBoolean(R.bool.kconfigs_isShowPersonaliseActionBarBgColor)) {
//				rl_title_layout.setBackgroundColor(
//						ROMA_SkinManager.getColor("newActionBarBackgoundColor", Res.getColor(R.color.actionBarBackgoundColor)));
//				if (txt_title != null) {
//					txt_title.setTextColor(ROMA_SkinManager.getColor("newLightActionBarTitleColor", 0xff5a6773));
//					closeView.setTextColor(ROMA_SkinManager.getColor("newLightActionBarTitleColor", 0xff5a6773));
//				}
//				svg_back.setSVGRenderer(SVGManager.getSVGParserRenderer(this, "yt_actionbar_navigation_back", R.drawable.abs__navigation_back), null);
//			} else {
//				// 标题栏背景颜色，默认为正常的白天模式颜色
//				rl_title_layout.setBackgroundColor(
//						ROMA_SkinManager.getColor("actionBarBackgoundColor", Res.getColor(R.color.actionBarBackgoundColor)));
//				// 标题栏文本颜色
//				if (txt_title != null) {
//					txt_title.setTextColor(ROMA_SkinManager.getColor("news_Title_TextColor", 0xff5a6773));
//					closeView.setTextColor(ROMA_SkinManager.getColor("news_Title_TextColor", 0xff5a6773));
//				}
//			}
//			rootView.setBackgroundColor(ROMA_SkinManager.getColor("news_MainView_BackgroundColor", 0xffffffff));
//		}
	}

	private boolean isVideo = false;

	/** 视频全屏参数 */
	protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	private View customView;
	private FrameLayout fullscreenContainer;
	private WebChromeClient.CustomViewCallback customViewCallback;

	/** 视频播放全屏 **/
	private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
		// if a view already exists then immediately terminate the new one
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		if (customView != null) {
			callback.onCustomViewHidden();
			return;
		}
		isVideo = true;

		TouguShowH5ActivityNew.this.getWindow().getDecorView();

		FrameLayout decor = (FrameLayout) getWindow().getDecorView();
		fullscreenContainer = new FullscreenHolder(TouguShowH5ActivityNew.this);
		fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
		decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
		customView = view;
		setStatusBarVisibility(false);
		customViewCallback = callback;
	}

	/** 隐藏视频全屏 */
	private void hideCustomView() {
		if (customView == null) {
			return;
		}
		isVideo = false;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setStatusBarVisibility(true);
		FrameLayout decor = (FrameLayout) getWindow().getDecorView();
		decor.removeView(fullscreenContainer);
		fullscreenContainer = null;
		customView = null;
		customViewCallback.onCustomViewHidden();
		mRomaWebView.setVisibility(View.VISIBLE);
	}

	/** 全屏容器界面 */
	static class FullscreenHolder extends FrameLayout {

		public FullscreenHolder(Context ctx) {
			super(ctx);
			setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
		}

		@Override
		public boolean onTouchEvent(MotionEvent evt) {
			return true;
		}
	}

	private void setStatusBarVisibility(boolean visible) {
		int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		switch (keyCode) {
//			case KeyEvent.KEYCODE_BACK:
//				/** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
//				if (customView != null) {
//					hideCustomView();
//				} /*else if (mRomaWebView.canGoBack()) {
//					mRomaWebView.goBack();
//				} */else {
////					finish();
//					handleBackAction();
//				}
//				return true;
//			default:
//				return super.onKeyUp(keyCode, event);
//		}
//	}

	private PullToRefreshPinnedHeaderListView mPullRefreshListView;
	private RomaNoteDataAdapter mListAdapter;
	private String[] keys = new String[]{"一.什么是专家选股？", "二.如何订阅专家选股？需要满足什么条件？", "三.策略订阅后，如何使用？"
			, "四.策略服务期限有多长？我订阅了多长周期的策略就享受多长周期的服务？"
			, "五.专家选股的股票交易与实盘操作区别在哪里？"};

	private String[][] datas = new String[][]{{"容维财经专家选股是一项投资顾问模拟操盘A股的服务。专家选股预设目标收益" +
			"，止损线和最长运行周期。运行期间，会运用合理的仓位进行操盘，使其收益最大化。您购买专家选股服务后" +
			"，容维财经会实时向您同步该策略的选股，选时和仓位控制等信息，供您参考。您可以选择是否按该策略跟进，自主交易。"}
			, {"您注册容维财经并绑定手机号码后，登录即可订阅“即将启动”或者“正在服务”的策略组合。为保证您订阅成功" +
			"，我们的售后客服会第一时间跟您取得联系，协助您并完成订阅。"}
			, {"您可以登录容维财经在首页专家选股模块可以直接找到您订阅的策略或者进入订阅列表页，随时随地查看该策略的动态详情。" +
			"当您订阅的策略启动，有新的交易动态，结束时，容维财经也会通过消息盒子提醒您，您可以在APP消息中心实时查看。" +
			"届时请登录您订阅的账号，并保持手机畅通！"}
			, {"目前策略有5个期限：一个月，两个月，三个月，半年，一年。如果您是在策略开启之前订阅则可以享受该策略的整个服务时长" +
			"，如果您选择订阅正在运行的策略，则只会享受到服务截止的时长。整个服务的终止时间不会出现提早或延迟，还请您知悉。"}
			, {"容维财经专家选股策略采用模拟盘操作，由投资顾问在设定运行周期内对100万资金进行模拟操作，所有操作与实盘无关，" +
			"不代表投资建议，更多的是帮助您了解更为专业的股票投资策略及手法。您的实盘操作，完全由您自主交易，盈亏自负。"}};

	private void setNoteData() {
		mPullRefreshListView = (PullToRefreshPinnedHeaderListView) this.findViewById(R.id.pinned_pull_refresh_list);
		FrameLayout mTitleFloatRoot = (FrameLayout) this.findViewById(R.id.TitleFloatRoot);
		mPullRefreshListView.getRefreshableView().setFloatView(mTitleFloatRoot);
		mPullRefreshListView.getRefreshableView().setHeaderDividersEnabled(false);
		mPullRefreshListView.setBackgroundColor(Res.getColor(R.color.gxs_while));
		mListAdapter = new RomaNoteDataAdapter(mActivity);
		mPullRefreshListView.setAdapter(mListAdapter);
	}

	private class RomaNoteDataAdapter extends SectionedBaseAdapter {

		private Context mContext;
		private LayoutInflater mInflater;
		public List<Boolean> hideSectionFlag/* = new ArrayList<Boolean>()*/;

		public RomaNoteDataAdapter(Context mContext) {
			this.mContext = mContext;
			mInflater = LayoutInflater.from(mContext);
			if (hideSectionFlag == null) {
				hideSectionFlag = new ArrayList<Boolean>();
			}
			if (hideSectionFlag.size() < keys.length) {
				hideSectionFlag = new ArrayList<Boolean>();
				for (int i = 0; i < keys.length; i++) {
					hideSectionFlag.add(false);
					setCountForSection(i, 0/*datas.get(i).size()*/);
				}
			}
		}

		@Override
		public Object getItem(int section, int position) {
			return null;
		}

		@Override
		public long getItemId(int section, int position) {
			return 0;
		}

		@Override
		public int getSectionCount() {
			return datas == null ? 0 : datas.length;
		}

		@Override
		public int getCountForSection(int section) {
			return itemCount[section];
		}

		// ++处理选项Title打开与关闭问题
		private int[] itemCount = new int[getSectionCount()];

		public void setCountForSection(int section, int count) {
			itemCount[section] = count;
		}

		@Override
		public View getItemView(int section, int position, View convertView, ViewGroup parent) {
			ViewHolder mViewHolder = null;
			if (convertView == null){
				mViewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.roma_gxs_wenjian_book_note_item_layout, null);
				mViewHolder.tv_note_item = (TextView) convertView.findViewById(R.id.tv_note_item);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			mViewHolder.tv_note_item.setText(datas[section][position]);
			return convertView;
		}

		@Override
		public View getSectionHeaderView(int section, View convertView, ViewGroup parent, Bundle bundle) {
			HeaderViewHolder mViewHolder = null;
			if (convertView == null){
				convertView = mInflater.inflate(R.layout.roma_gxs_wenjian_book_note_head_layout, null);
				convertView.setBackgroundColor(0xfff7f7f7);
				mViewHolder = new HeaderViewHolder(convertView);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (HeaderViewHolder) convertView.getTag();
			}
			mViewHolder.tv_title.setText(keys[section]);
			if (hideSectionFlag.get(section)) {
				mViewHolder.img_arrows.setImageResource(R.drawable.img_arrows_up);
				RomaNoteDataAdapter.this.setCountForSection(section, datas[section].length);
			} else {
				mViewHolder.img_arrows.setImageResource(R.drawable.img_arrows_down);
			}
			mViewHolder.rl_title.setOnClickListener(new MyOnClickCategoryListener(section));
			return convertView;
		}

		@Override
		public boolean isHideFloatView() {
			return false;
		}

		@Override
		public void currentHeaderFloatView(View floatView) {
			Logger.d("floatView", "floatView = " + floatView);
			try {
				if (floatView != null) {
					HeaderViewHolder mViewHoder = new HeaderViewHolder(floatView);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		class ViewHolder {
			TextView tv_note_item;
		}

		class HeaderViewHolder {
			TextView tv_title;
			ImageView img_arrows;
			RelativeLayout rl_title;
			public HeaderViewHolder(View view) {
				tv_title = (TextView) view.findViewById(R.id.tv_title);
				img_arrows = (ImageView) view.findViewById(R.id.img_arrows);
				rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
			}
		}

		class MyOnClickCategoryListener implements View.OnClickListener {
			private int section;

			public MyOnClickCategoryListener(int section) {
				this.section = section;
			}

			@Override
			public void onClick(View v) {
				int id = v.getId();
				if (id == R.id.rl_title) {
					hideSectionFlag.set(section, !hideSectionFlag.get(section));
					if (hideSectionFlag.get(section)) {// 隐藏
						RomaNoteDataAdapter.this.setCountForSection(section, datas[section].length);
					} else {
						RomaNoteDataAdapter.this.setCountForSection(section, 0);
					}
					RomaNoteDataAdapter.this.notifyDataSetChanged();
				}
			}
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
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
		}
	}

	private boolean isFirstCheckAppOps = true;
	private boolean isShowFloatWindow = false;
	private String module_id = "";
	private String guide_content = "";

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

	public void setPro_id(String productID) {
		this.productID = productID;
	}

	public void setGuide_content(String guide_content) {
		this.guide_content = guide_content;
	}

	public void setShowFloatWindow(boolean showFloatWindow) {
		isShowFloatWindow = showFloatWindow;
//		handler.sendEmptyMessageDelayed(0, 3000);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				showFloatWindow();
			}
		}, 3000);
	}

	public void showFloatWindow() {
		Logger.d("TAG", "isShowFloatWindow = " + isShowFloatWindow);
		try {
			if (isShowFloatWindow) {
				Logger.e("TAG", PermissionsUtils.getAppOps(OriginalContext.getContext()) ? "已开启拥有悬浮框权限" : "当前应用未拥有悬浮窗显示权限, 将建议打开权限");
				if (!PermissionsUtils.getAppOps(OriginalContext.getContext()) && isFirstCheckAppOps) {
					isFirstCheckAppOps = false;
					Dialog dialog = DialogFactory.getIconDialog(TouguShowH5ActivityNew.this,
							"当前应用未拥有\"显示悬浮窗\"权限, 将影响您的正常使用, 请问是否前往应用权限管理打开? ",
							DialogFactory.DIALOG_TYPE_QUESTION,
							null, new OnClickDialogBtnListener(){
								@Override
								public void onClickButton(View view) {
								}
							}, null, new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									PermissionsUtils.openMiuiPermissionActivity(TouguShowH5ActivityNew.this);
								}
							});
					dialog.setCancelable(false);
					dialog.show();
				}

				FloatWindowManager.getInstance(OriginalContext.getContext()).showFloatWindow(OriginalContext.getContext(),
						R.layout.roma_float_window_small, R.id.small_window_img);
				FloatWindowManager.getInstance(OriginalContext.getContext()).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
//					RomaToast.showMessage(OriginalContext.getContext(), "敬请期待");
						if (!StringUtils.isEmpty(RomaSysConfig.getWechatCode())) {
							Bundle bundle = new Bundle();
							bundle.putString("module_id", module_id);
							bundle.putString("pro_id", productID);
							bundle.putString("content", guide_content);
							KActivityMgr.switchWindow((ISubTabView) TouguShowH5ActivityNew.this, "roma.romaway.homepage.android.guide.HomePageGuideActivityNew", bundle, false);
						}
					}
				});
			}else{
				FloatWindowManager.getInstance(OriginalContext.getContext()).hideFloatWindow();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hideFloatWindow() {
		Logger.d("TAG", "isShowFloatWindow = " + "隐藏了");
		FloatWindowManager.getInstance(OriginalContext.getContext()).hideFloatWindow();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					showFloatWindow();
					break;
				case 1:
					if (rl_title_layout != null) {
						rl_title_layout.setBackgroundColor(ActionBarColor);
					}
					break;
				case 2:
					if (img_left != null) {
						img_left.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.title_back)), BackColor));
						img_left.setVisibility(View.VISIBLE);
					}
					break;
				case 3:
					if (img_right_icon != null) {
						img_right_icon.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.roma_share_icon)), ShareColor));
					}
					if (svg_right_icon1 != null) {
						svg_right_icon1.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.img_qswy_ask)), ShareColor));
					}
					break;
			}
		}
	};

	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE=1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//		super.onActivityResult(requestCode, resultCode, data);
		Logger.d("tag", "requestCode = " + requestCode);
		Logger.d("tag", "resultCode = " + resultCode);
		if (requestCode == FILECHOOSER_RESULTCODE) {
			// mUploadMessage = wcci.getmUploadMessage();
			if (null == mUploadMessage)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null
					: intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;

		} else if (requestCode == PhotoChooser.REQUESTCODE_PHOTO) {
			Logger.d("tag", "PhotoChooser.REQUESTCODE_PHOTO ");
//			if(intent != null)
//				commitBitmap(intent);

			if (resultCode == Activity.RESULT_OK) {
				cuttingImg(intent.getData());
			}

		} else if (requestCode == PhotoChooser.REQUESTCODE_CAMERA) {
			Logger.d("tag", "PhotoChooser.REQUESTCODE_CAMERA ");

//			if(intent != null)
//				commitBitmap(intent);

			if (resultCode == mActivity.RESULT_OK) {
				if (intent != null && intent.getExtras() != null) {
					cuttingImg(intent.getData());
					Logger.d("tag", "get photo uri from intent");
				} else {
					if (resultCode == mActivity.RESULT_OK) {
						if (intent != null && intent.getExtras() != null) {
							cuttingImg(intent.getData());
							Logger.d("tag", "get photo uri from intent");
						} else {
							File temp = new File(PhotoChooser.file_dir, PhotoChooser.fileName);
							cuttingImg(Uri.fromFile(temp));
							Logger.d("tag", "get photo uri from file");
						}
					}
				}
			}

		} else if (requestCode == PhotoChooser.REQUESTCODE_CUTTING) {
			Logger.d("tag", "PhotoChooser.REQUESTCODE_CUTTING ");
			commitBitmap(intent);
		}
	}

	private void cuttingImg(Uri uri){
		Logger.d("tag", "file_dir = " + PhotoChooser.file_dir);
		File userPicfile = new File(PhotoChooser.file_dir, "roma_stock_temp.jpg");
		if(userPicfile.exists()){
			userPicfile.delete();
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "false");
		intent.putExtra("scale", true);
//		intent.putExtra("aspectX", 800);
//		intent.putExtra("aspectY", 800);
//		intent.putExtra("outputX", 100);
//		intent.putExtra("outputY", 600);
		intent.putExtra("return-data", true);
		PhotoChooser.tempFileUri = Uri.fromFile(userPicfile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, PhotoChooser.tempFileUri);
		startActivityForResult(intent, PhotoChooser.REQUESTCODE_CUTTING);
	}

	private void commitBitmap(Intent intent) {
		Logger.d("tag", "intent = " + intent);
		if (intent == null) {
			return;
		}
		try {
			Logger.d("tag", "正在识别... = ");
			if (progressDialog != null) {
				progressDialog.setMessage("正在识别...");
				if (!progressDialog.isShowing()) {
					progressDialog.show();
				}
			} else {
				progressDialog = new ProgressDialog(mActivity);
				progressDialog.setMessage("正在识别...");
				if (!progressDialog.isShowing()) {
					progressDialog.show();
				}
			}
			File file = new File(PhotoChooser.tempFileUri.getPath());
			if (file.exists() && file.isFile() && file.length() > 0) {
				File file1 = new File(mActivity.getFilesDir(), "roma_stock.jpg");
				FileSystem.copyFile(file, file1, true);
				Logger.d("tag", "路径：" + file1.getPath() + ".JPEG");
				Bitmap bitmap = BitmapFactory.decodeFile(file1.getPath()/*, getBitmapOption(2)*/);
				String bases64 = BitmapUtils.bitmapToBase64(bitmap);

				final String base64 = bases64;
				ImageOCRecognizer.getInstance(tps).recognize(base64,
						new OCRListener() {
							@Override
							public void onResult(final OCRResult result, String input) {
								//若有更新界面操作，请切换到主线程
								handler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										Logger.i("tag", "result = " + result);
										String text = getResult(result);
										req();
									}
								});
							}

							@Override
							public void onError(final OcrErrorCode error) {
								// resultText.setText("识别失败");
								handler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										RomaToast.showMessage(TouguShowH5ActivityNew.this, "识别失败");
										if (progressDialog != null) {
											progressDialog.setMessage("识别失败，请重新上传图片");
											if (!progressDialog.isShowing()) {
												progressDialog.show();
											} else {
												progressDialog.dismiss();
											}
										} else {
											progressDialog = new ProgressDialog(mActivity);
											progressDialog.setMessage("识别失败，请重新上传图片");
											if (!progressDialog.isShowing()) {
												progressDialog.show();
											} else {
												progressDialog.dismiss();
											}
										}
									}
								});
							}
						});
			}

		} /*catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */ catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.d("tag", "Exception = " + e.getMessage());
		}
	}

	private BitmapFactory.Options getBitmapOption(int inSampleSize){
		System.gc();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inSampleSize = inSampleSize;
		return options;
	}

	/**
	 * bitmap 压缩并转成base64编码字符串
	 * @param bitmap
	 * @param bitmapQuality
	 * @return
	 */
	public String bitmaptoString(Bitmap bitmap, int bitmapQuality) {

		// 将Bitmap转换成字符串
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, bitmapQuality, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.DEFAULT);
		return string;
	}

	public static Bitmap readBitmapFromFile(String filePath, int size) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		int inSampleSize = 1;
		Logger.d("readBitmapFromFile", "srcWidth = " + srcWidth);
		Logger.d("readBitmapFromFile", "srcHeight = " + srcHeight);

		if (srcHeight > size || srcWidth > size) {
			if (srcWidth < srcHeight) {
				inSampleSize = Math.round(srcHeight / size);
			} else {
				inSampleSize = Math.round(srcWidth / size);
			}
		}

		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize;
		Logger.d("tag", "inSampleSize = " + inSampleSize);
		return BitmapFactory.decodeFile(filePath, options);
	}

	private String content = "";

	private String getResult(OCRResult result) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		int i = 1;
		if(OCRParameters.TYPE_TEXT.equals(result.getType())){
			//按文本识别
			List<Region> regions = result.getRegions();
			for (Region region : regions) {
				List<Line> lines = region.getLines();
				for (Line line : lines) {
					sb.append("文本"+ i++ + "： ");
					List<Word> words = line.getWords();
					for (Word word : words) {
						sb1.append(word.getText()).append("|||");
						sb.append(word.getText()).append(" ");
					}
					sb.append("\n");
				}
			}
		}else{
			//按行识别
			List<Region_Line> regions = result.getRegions_Line();
			for (Region_Line region : regions) {
				List<Line_Line> lines = region.getLines();
				for (Line_Line line : lines) {
					sb1.append(line.getText()).append("|||");
					sb.append("文本"+ i++ + "： ");
					sb.append(line.getText());
					sb.append("\n");
				}
			}
		}
		content = sb1.toString();
		String text = sb.toString();
//        if(!TextUtils.isEmpty(text)){
//            text = text.substring(0, text.length() - 2);
//        }
//        if(!TextUtils.isEmpty(content)){
//            content = content.substring(0, content.length() - 4);
//        }
		Log.i("TAG", "content = " + content);
		return text;

	}

	private void req() {
		String ip = "http://api.guxiansen.test1.romawaysz.com/";
		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
			ip = RomaSysConfig.getIp();
		} else {
			ip = Res.getString(R.string.NetWork_IP);
		}
		final String finalIp = ip;
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String result1 = HttpUtils.submitGetData(finalIp + "external/getCode?content=" + content.replaceAll("%", ""), "utf-8");
				Logger.i("tag", "result1 = " + result1);
				String url = "http://api.guxiansen.test0.romawaysz.com/product/showDiagnosis?code=";
				if (!TextUtils.isEmpty(result1)) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (progressDialog != null) {
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
							}
							Map<String, String> map = new HashMap<String, String>();
							map.put("result1", result1);
							apiProvider.commitStrData(apiProvider.getBackMethodName("openCamera"), map);
						}
					});
				} else {
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (progressDialog != null) {
								progressDialog.setMessage("识别失败，请重新上传图片");
								if (!progressDialog.isShowing()) {
									progressDialog.show();
								} else {
									progressDialog.dismiss();
								}
							}
						}
					});
				}
			}
		}).start();
	}

}
