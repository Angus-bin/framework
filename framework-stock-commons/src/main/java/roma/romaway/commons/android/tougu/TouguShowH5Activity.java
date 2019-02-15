package roma.romaway.commons.android.tougu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.utils.ColorUtils;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.android.phone.utils.JYStatusUtil.OnLoginAccountListener;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.service.DLServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.db.PersistentCookieStore;
import com.romaway.commons.json.JSONUtils;
import com.romaway.commons.lang.Base64;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romawaylibs.theme.ROMA_SkinManager;
import com.romawaylibs.theme.svg.SVGManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.magicwindow.mlink.annotation.MLinkRouter;
import roma.romaway.commons.android.share.ShareManager;
import roma.romaway.commons.android.webkit.WebkitActivity;

public class TouguShowH5Activity extends WebkitActivity implements ISubTabView, ROMA_SkinManager.OnSkinChangeListener{

	/**与js数据交互的接口*/
	public TouguJavascripInterface mJsInterface;
	protected TextView closeView;
	private Activity mActivity;
	public final static String ACTIVITY_TYPE = "ACTIVITY_TYPE";
	public final static String ACTIVITY_TYPE_PUSH_MSG = "ACTIVITY_TYPE_PUSH_MSG";

	/**	
	 * 搜索返回监听广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("KDS_TG_STOCK_INFO")) {//键盘精灵操作之后
				String stockCode = intent.getExtras().getString(BundleKeyValue.HQ_STOCKCODE);
				String stockName = intent.getExtras().getString(BundleKeyValue.HQ_STOCKNAME);
				
				//键盘精灵
			    Map<String, String> map = new HashMap<String, String>();
			    map.put("stockName", stockName);
			    map.put("stockCode", stockCode);
			    map.put("stockIndustry", "证券金融");
			    
			    mJsInterface.commit(
			    		mJsInterface.getBackMethodName("showKeyBoardTG"), map);
				
			}else if (intent.getAction()
					.equals("action." + mActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl")) {//交易登录成功之后
				handleLoginedAction(JYStatusUtil.JY_LOGIN_TYPE, null);
				Logger.d("tag", "BroadcastReceiver onReceive: " + intent.getAction());

			}else if(("action." + mActivity.getPackageName() + ".rzrq.homepage.resetLoadUrl").equalsIgnoreCase(intent.getAction())){		// 融资融券登录成功之后
				handleLoginedAction(JYStatusUtil.RZRQ_LOGIN_TYPE, null);
				Logger.d("tag", "BroadcastReceiver onReceive: " + intent.getAction());

			}else if(intent.getAction().equals("action.reglogin.onSuccess")){//手机注册登录成功
				handleLoginedAction(TouguJavascripInterface.PHONE_NUM, null);
				Logger.d("tag", "BroadcastReceiver onReceive: " + intent.getAction());

			}else if(intent.getAction().equals("action.youwen.talkingTimeOut")){	// 页面会话超时, 关闭WebViewActivity
				TouguShowH5Activity.this.finish();

			}else if(intent.getAction().equals("action.stock.quiz.callback")){		// 抢红包活动校验结束, 回调第三方Web页面JS方法
				// [需求] 回调第三方Web页面, 返回当前登录中普通交易账号给JS方法:
				Map<String, String> map = new HashMap<String, String>();
				map.put("state", intent.getExtras().getString("state"));
				map.put("msg", intent.getExtras().getString("msg"));
				map.put(TouguJavascripInterface.PHONE_NUM, RomaUserAccount.getUsername());
				map.put(TouguJavascripInterface.DEVICE_ID, SysConfigs.DEVICE_ID);
				map.put(TouguJavascripInterface.CLIENT_VERSION, RomaSysConfig.getClientInnerVersion());
				map.put(TouguJavascripInterface.CURRENT_TRADE_ACCOUNT, intent.getExtras().getString("tradeAccount"));
				Logger.d("tag", "jsonBuilder : " +
						JSONUtils.toJson(mJsInterface.getBackMethodName(
								"getTradeAccountAndCheckLimit"), map));

				mJsInterface.commit(
						mJsInterface.getBackMethodName("getTradeAccountAndCheckLimit"), map);

				Logger.d("tag", "BroadcastReceiver onReceive: " + intent.getAction());
			}else if(intent.getAction().equals("action.risk.levelCallback")) {
				final Intent riskIntent = intent;
				JYStatusUtil mJYStatusUtil = new JYStatusUtil(TouguShowH5Activity.this, true);
				mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener() {
					@Override
					public void onLoginAccount(int status, String loginAccount) {
						if (status == JYStatusUtil.JY_LOGIN_STATUS_OK) {
							HashMap<String, String> hashMap = new HashMap<String, String>();
							hashMap.put("RiskLevel", riskIntent.getExtras().getString("riskLevel"));
							hashMap.put("ExpireDate", riskIntent.getExtras().getString("expireDate"));
							hashMap.put("RemainingTime", riskIntent.getExtras().getString("remainingTime"));
							hashMap.put(TouguJavascripInterface.PHONE_NUM, RomaUserAccount.getUsername());
							hashMap.put(TouguJavascripInterface.DEVICE_ID, SysConfigs.DEVICE_ID);
							hashMap.put(TouguJavascripInterface.CLIENT_VERSION, RomaSysConfig.getClientInnerVersion());
							hashMap.put(TouguJavascripInterface.CURRENT_TRADE_ACCOUNT, loginAccount);
							Logger.d("tag", "jsonBuilder : " +
									JSONUtils.toJson(mJsInterface.getBackMethodName(
											"goto_risk_view"), hashMap));
							String backMethodName = mJsInterface.getBackMethodName("goto_risk_view");
							mJsInterface.commitStrData(backMethodName, hashMap);
						}
					}
				}, JYStatusUtil.JY_LOGIN_TYPE);
			}else if (intent.getAction().equals("action.ticket.loginCallback")){
				// 将登录获取回来的ticket等用户信息返回给第三方H5界面:
				handlePageFinished(null, intent);
			}
		}
	};

	public interface HandleUnLoginListener{
		void onHandleUnLogin();
	}

	public void handleLoginedAction(String loginType, final HandleUnLoginListener handleUnLoginListener) {
		if (TouguJavascripInterface.PHONE_NUM.equalsIgnoreCase(loginType)){
			PersistentCookieStore cookieStore = new PersistentCookieStore(TouguShowH5Activity.this);
			String userId = cookieStore.getValue("user_id");

			Map<String, String> map = new HashMap<String, String>();
			if (!RomaUserAccount.isGuest()){
				map.put("userId", userId);
				map.put("state", "0");//表示手机注册成功

				// [需求] 回调第三方Web页面, 返回当前登录手机号码给JS方法:
				map.put(TouguJavascripInterface.PHONE_NUM, RomaUserAccount.getUsername());
				mJsInterface.commit(
						mJsInterface.getBackMethodName("ShowRegisterView"), map);
			}
		}else if(JYStatusUtil.JY_LOGIN_TYPE.equalsIgnoreCase(loginType)){
			JYStatusUtil mJYStatusUtil = new JYStatusUtil(TouguShowH5Activity.this, true);
			mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener() {
				@Override
				public void onLoginAccount(int status, String loginAccount) {
					if (status == JYStatusUtil.JY_LOGIN_STATUS_OK) {
						PersistentCookieStore cookieStore = new PersistentCookieStore(TouguShowH5Activity.this);
						String userId = cookieStore.getValue("user_id");

						//交易登录成功后的返回
						Map<String, String> map = new HashMap<String, String>();
						map.put("state", "0");//表示资金账号登录成功
						map.put("userId", userId);
						map.put("userId", userId);
						map.put("fundId", loginAccount);
						map.put("clientId", "null");

						// [需求] 回调第三方Web页面, 返回当前登录中普通交易账号给JS方法:
						map.put(TouguJavascripInterface.PHONE_NUM, RomaUserAccount.getUsername());
						map.put(TouguJavascripInterface.CURRENT_TRADE_ACCOUNT, loginAccount);
						Logger.d("tag", "jsonBuilder : " +
								JSONUtils.toJson(mJsInterface.getBackMethodName(
										"gotoTradeLoginViewTG"), map));

						mJsInterface.commit(
								mJsInterface.getBackMethodName("gotoTradeLoginViewTG"), map);
						return;
					}
					if (handleUnLoginListener != null)
						handleUnLoginListener.onHandleUnLogin();
				}
			}, JYStatusUtil.JY_LOGIN_TYPE);
		}else if(JYStatusUtil.RZRQ_LOGIN_TYPE.equalsIgnoreCase(loginType)){
			JYStatusUtil mJYStatusUtil = new JYStatusUtil(TouguShowH5Activity.this, true);
			mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener() {
				@Override
				public void onLoginAccount(int status, String loginAccount) {
					if (status == JYStatusUtil.RZRQ_LOGIN_STATUS_OK) {
						PersistentCookieStore cookieStore = new PersistentCookieStore(TouguShowH5Activity.this);
						String userId = cookieStore.getValue("user_id");

						//交易登录成功后的返回
						Map<String, String> map = new HashMap<String, String>();
						map.put("state", "0");//表示资金账号登录成功
						map.put("userId", userId);
						map.put("userId", userId);
						map.put("fundId", loginAccount);
						map.put("clientId", "null");

						// [需求] 回调第三方Web页面, 返回当前登录中融资融券账号给JS方法:
						map.put(TouguJavascripInterface.PHONE_NUM, RomaUserAccount.getUsername());
						map.put(TouguJavascripInterface.CURRENT_RZRQ_TRADE_ACCOUNT, loginAccount);
						Logger.d("tag", "jsonBuilder : " +
								JSONUtils.toJson(mJsInterface.getBackMethodName(
										"gotoTradeLoginViewTG"), map));

						mJsInterface.commit(
								mJsInterface.getBackMethodName("gotoRZRQTradeLoginViewTG"), map);
						return;
					}
					if (handleUnLoginListener != null)
						handleUnLoginListener.onHandleUnLogin();
				}
			}, JYStatusUtil.RZRQ_LOGIN_TYPE);
		}else if(TouguJavascripInterface.PTJY_CHECK_ACTION_LIMIT.equalsIgnoreCase(loginType)){
			JYStatusUtil mJYStatusUtil = new JYStatusUtil(TouguShowH5Activity.this, true);
			mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener() {
				@Override
				public void onLoginAccount(int status, String loginAccount) {
					if (status == JYStatusUtil.JY_LOGIN_STATUS_OK) {
						mJsInterface.switchWebViewForResult(Res.getString(R.string.trade_url_quiz));
						return;
					}
					if (handleUnLoginListener != null)
						handleUnLoginListener.onHandleUnLogin();
				}
			}, JYStatusUtil.JY_LOGIN_TYPE);
		}else if(JYStatusUtil.JY_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType)){
			JYStatusUtil mJYStatusUtil = new JYStatusUtil(TouguShowH5Activity.this, true);
			mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener() {
				@Override
				public void onLoginAccount(int status, String loginAccount) {
					if (status == JYStatusUtil.JY_LOGIN_STATUS_OK) {
						Intent intent = new Intent();
						intent.putExtra("hasFunction", true);
						Bundle bundle = KActivityMgr.switchJiaoyiTicketHasKey("KDS_TICKET_NO_URL");
						intent.putExtras(bundle);
						KActivityMgr.switchJiaoYiFun(TouguShowH5Activity.this, intent, bundle);
						return;
					}
					if (handleUnLoginListener != null)
						handleUnLoginListener.onHandleUnLogin();
				}
			}, JYStatusUtil.JY_LOGIN_TYPE);
		}else if(JYStatusUtil.RZRQ_LOGIN_RISK_TYPE.equalsIgnoreCase(loginType)){
			JYStatusUtil mJYStatusUtil = new JYStatusUtil(TouguShowH5Activity.this, true);
			mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener() {
				@Override
				public void onLoginAccount(int status, String loginAccount) {
					if (status == JYStatusUtil.RZRQ_LOGIN_STATUS_OK) {
						JYStatusUtil.currentRzrqFragment.getKdsWebView().loadJsMethodUrl("javascript:queryRiskLevel()");
						return;
					}
					if (handleUnLoginListener != null)
						handleUnLoginListener.onHandleUnLogin();
				}
			}, JYStatusUtil.RZRQ_LOGIN_TYPE);
		}
	}

	/**
	 * 返回键类型：0：按照web页一页一页返回；1：直接finish Activity; 
	 * <p>2: 不销毁,后台缓存Activity;(当前类启动模式需为singleInstance);
	 */
	private int backType = 1;


	private UMShareListener mShareListener;
	private ShareAction mShareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		PushAgent.getInstance(this).onAppStart();
		mActivity = this;

		String url = this.getIntent().getStringExtra("key_h5url");

		//Logger.d("tag", "1-TouguShowH5Activity url:"+url);
		//[需求]：将带参数的url转化成实际的url链接
//		url = UrlParamsManager.getInstance(this).toNewUrl(url);
		//Logger.d("tag", "2-TouguShowH5Activity url:"+url);

		int visibilityTile = this.getIntent().getIntExtra("key_titleVisibility", View.VISIBLE);
		int visibilityRightImg = this.getIntent().getIntExtra("ket_right_img_visibility", View.GONE);
		final String title = this.getIntent().getStringExtra("shareTitle");
		String description = this.getIntent().getStringExtra("shareDescription");
		String shareUrls = this.getIntent().getStringExtra("shareUrl");
		setShareUrl(shareUrls);
		setShareTitle(title);
		setShareDescription(description);
		setTitleVisibility(visibilityTile);
		setRightImgVisibility(visibilityRightImg);

		backType = this.getIntent().getIntExtra("backType", 0);

		this.setUrl(url);

		closeView = (TextView) this.findViewById(R.id.txt_finish);
		closeView.setTextColor(Color.WHITE);
		closeView.setTextSize(15);
		closeView.setOnClickListener(this);

		Logger.d("TouguShowH5Activity1", "加载完成");

		getKdsWebView().setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
				String functionCode = getIntent().getStringExtra("functionCode");
				if ("ROMA_YouWen_YWYD".equalsIgnoreCase(functionCode))
					setTitle(Res.getString(R.string.yt_youwen_title));
				else if(!TextUtils.isEmpty(functionCode) &&
						(functionCode.startsWith("KDS_TICKET")
						|| functionCode.startsWith("KDS_PHONE_TICKET"))){
					//[需求]加载建投公募基金、我的Level 2等Web界面时不读取 Html页面的Title.

				}else
					setTitle(title);
			}

			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					mProgressBar.setVisibility(View.GONE);
					mProgressBar2.setVisibility(View.GONE);
				} else {
//					mProgressBar2.setVisibility(View.VISIBLE);
					mProgressBar.setVisibility(View.GONE);
					mProgressBar2.setProgress(newProgress);
				}

				super.onProgressChanged(view, newProgress);
			}

			//支持webview调用原生相册进行上传图片到WebView中
			// Android > 4.1.1 调用这个方法
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
										String acceptType, String capture) {

				mUploadMessage = uploadMsg;
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");

				TouguShowH5Activity.this.startActivityForResult(
						Intent.createChooser(intent, "完成操作需要使用"),
						FILECHOOSER_RESULTCODE);
			}

			// 3.0 + 调用这个方法
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
										String acceptType) {
				mUploadMessage = uploadMsg;
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");
				TouguShowH5Activity.this.startActivityForResult(
						Intent.createChooser(intent, "完成操作需要使用"),
						FILECHOOSER_RESULTCODE);
			}

			// Android < 3.0 调用这个方法
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg;
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");
				TouguShowH5Activity.this.startActivityForResult(
						Intent.createChooser(intent, "完成操作需要使用"),
						FILECHOOSER_RESULTCODE);
			}
		});
		getKdsWebView().setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				int handleBackType = getIntent().getIntExtra("backType", 0);
//				try {
//					//[需求]添加支持设置Url后退类型栈 判断当前Url的后退处理方式:
//					if (urlBackTypeMap != null && !urlBackTypeMap.isEmpty()) {
//						String cutUrl = getKdsWebView().getUrl();
//						if (!TextUtils.isEmpty(cutUrl)) {
//							// "0"为逐步后退(Web历史记录栈), "1"为直接关闭(遇到此地址直接关闭Activity);
//							String backType = null;
//							// 判断 后退类型Map表里是否包含指定 urlKey 键:
//							if (urlBackTypeMap.containsKey(cutUrl)) {
//								backType = urlBackTypeMap.get(cutUrl);
//							} else {
//								Set<String> urlKeys = urlBackTypeMap.keySet();
//								for (String urlKey : urlKeys) {
//									if (cutUrl.contains(urlKey)) {
//										backType = urlBackTypeMap.get(urlKey);
//										break;
//									}
//								}
//							}
//
//							if (!TextUtils.isEmpty(backType) && NumberUtils.isNumber(backType)) {
//								handleBackType = Integer.parseInt(backType);
//							}
//						}
//					}
//				}catch (Exception e){
//					Logger.e("TAG", e.getMessage());
//				}

				// backType = 1为直接finish Activity, 则无需显示关闭按钮;
				if (view != null && view.canGoBack() && handleBackType != 1) {
					closeView.setVisibility(View.VISIBLE);
				} else {
					closeView.setVisibility(View.GONE);
				}
				Logger.d("TouguShowH5Activity2", "加载完成");
				TouguShowH5Activity.this.findViewById(R.id.svg_back).setEnabled(true);
				handlePageFinished(url, getIntent());
			}
		});

		mJsInterface = new TouguJavascripInterface(this, getKdsWebView());
		mJsInterface.setSwitchTitleVisibility(getIntent().getIntExtra("key_titleVisibility", View.GONE));
		mJsInterface.setSwitchUrlHeader("file:///android_asset/QuanShang/zixun2_0/f10/views/");
		mJsInterface.setSwitchWebViewBackgroundColor(getIntent().getIntExtra("webViewBackgroundColor", 0xffffffff));

		//不可以删除该项，不然登录无法跳转
		this.addJavascriptInterface(mJsInterface);

		String url1 = getUrl();

		getKdsWebView().loadUrl(url1);

		Logger.d("TouguShowH5Activity", "setUrl:" + url + ",getUrl:" + url1);

		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("action." + mActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl");
		myIntentFilter.addAction("action." + mActivity.getPackageName() + ".rzrq.homepage.resetLoadUrl");
		myIntentFilter.addAction("KDS_TG_STOCK_INFO");
		myIntentFilter.addAction("action.reglogin.onSuccess");
		myIntentFilter.addAction("action.youwen.talkingTimeOut");
		myIntentFilter.addAction("action.stock.quiz.callback");
		myIntentFilter.addAction("action.risk.levelCallback");
		myIntentFilter.addAction("action.ticket.loginCallback");
		this.registerReceiver(mBroadcastReceiver, myIntentFilter);

		if (getIntent() != null) {
			String functionCode = getIntent().getStringExtra("functionCode");
			if (!TextUtils.isEmpty(functionCode) && functionCode.equalsIgnoreCase("ROMA_TICKET_NO_GMJJ")) {
				ImageView rightSVGView1 = (ImageView) this.findViewById(R.id.svg_right_icon1);
				ImageView rightSVGView2 = (ImageView) this.findViewById(R.id.svg_right_icon2);
				rightSVGView1.setVisibility(View.VISIBLE);
				rightSVGView2.setVisibility(View.VISIBLE);
//				rightSVGView1.setImageResource(R.drawable.kds_web_search_icon);
//				rightSVGView2.setImageResource(R.drawable.kds_web_user_center_icon);

				rightSVGView1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getKdsWebView().loadUrl("javascript:externalCallSearch()");
					}
				});
				rightSVGView2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra("hasFunction", true);
						Bundle bundle = KActivityMgr.getJiaoYiSwitchBundle("KDS_SM_KFSJJ", Res.getString(R.string.trade_url_kfsjj));
						intent.putExtras(bundle);
						KActivityMgr.switchJiaoYiFun(TouguShowH5Activity.this, intent, bundle);
					}
				});
			}else if ("ROMA_PHONE_TICKET_NO_LEVEL2_PDMY".equalsIgnoreCase(functionCode)){
				TextView orderRecord = (TextView) this.findViewById(R.id.txt_order_record);
				orderRecord.setVisibility(View.VISIBLE);
				orderRecord.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							KActivityMgr.shortcutClickSwitch(mActivity,
                                    Intent.parseUri(KActivityMgr.getIntentString("KDS_PHONE_TICKET_NO_LEVEL2_PDLIST", null), 0));
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}

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
					Logger.d("ShareAction", "snsPlatform.mShowWord = " + snsPlatform.mShowWord + "   share_media = " + share_media);
					if (getKdsWebView() != null && !StringUtils.isEmpty(getKdsWebView().getUrl())) {
						if (StringUtils.isEmpty(shareUrl)) {
							RomaToast.showMessage(TouguShowH5Activity.this, "分享链接为空，请重试");
							mShareAction.close();
							return;
						}
						if ("webTitle".equals(shareTitle)) {
							setShareTitle(getKdsWebView().getTitle());
							setShareUrl(shareUrl + "&share=1");
						}
						UMWeb web = new UMWeb(shareUrl);
						web.setTitle(!StringUtils.isEmpty(shareTitle) ? shareTitle : "容维财经");
						web.setDescription(!StringUtils.isEmpty(shareDescription) ? shareDescription : "点击查看个股详情");
						web.setThumb(new UMImage(mActivity, R.drawable.roma_sicon));
						new ShareAction(mActivity).withMedia(web)
								.setPlatform(share_media)
								.setCallback(mShareListener)
								.share();
					}
				}
			});
		}

	}

	private boolean isHaveShare = false;

	private String shareTitle;

	private String shareDescription;

	private String shareUrl;

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}

	private int visibilityRightImg = View.GONE;
	public void setRightImgVisibility(int visibility){
		this.visibilityRightImg = visibility;
	}

	@Override
	protected void onResume() {
		MobclickAgent.onPageStart("TouguShowH5Activity");
		super.onResume();
		Logger.d("TouguShowH5Activity3", "加载完成");
		TouguShowH5Activity.this.findViewById(R.id.svg_back).setEnabled(true);
		rl_img_right = (RelativeLayout) TouguShowH5Activity.this.findViewById(R.id.rl_img_right);
		img_right_icon = (ImageView) TouguShowH5Activity.this.findViewById(R.id.img_right_icon);
		rl_img_right.setVisibility(visibilityRightImg);
		img_right_icon.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.roma_share_icon)), 0xffffffff));
		img_right_icon.setVisibility(visibilityRightImg);
		rl_img_right.setOnClickListener(this);
		onSkinChanged(null);
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPageEnd("TouguShowH5Activity");
		super.onPause();
	}

	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE=1;  
	@SuppressLint("NewApi")
	@Override  
	protected void onActivityResult(int requestCode, int resultCode,  
	        Intent intent) {
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, intent);
	    if (requestCode == FILECHOOSER_RESULTCODE) {  
	       // mUploadMessage = wcci.getmUploadMessage();  
	        if (null == mUploadMessage)  
	            return;  
	        Uri result = intent == null || resultCode != RESULT_OK ? null  
	                : intent.getData();  
	        mUploadMessage.onReceiveValue(result);  
	        mUploadMessage = null;   
	        
	    }else if(requestCode == PhotoChooser.REQUESTCODE_PHOTO){
	    	Logger.d("tag", "PhotoChooser.REQUESTCODE_PHOTO ");
	    	if(intent != null)
	    		commitBitmap(intent);
	    	
	    }else if(requestCode == PhotoChooser.REQUESTCODE_CAMERA){
	    	Logger.d("tag", "PhotoChooser.REQUESTCODE_CAMERA ");
	    	
	    	if(intent != null)
	    		commitBitmap(intent);
	    }
	} 

	private void commitBitmap(Intent intent){
		if(intent == null)
			return;
		
		Uri originalUri = intent.getData();
    	ContentResolver resolver = getContentResolver();
    	try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
			String base64Str = bitmaptoString(bitmap, 25);
			base64Str = StringUtils.replaceBlank(base64Str);
			
			//键盘精灵
		    Map<String, String> map = new HashMap<String, String>();
		    map.put("imageData", base64Str);
		    
		    mJsInterface.commit(
		    		mJsInterface.getBackMethodName("pickImage"), map);
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.svg_back){//web后退键
			handleBackAction();
		}else if(v.getId() == R.id.txt_finish){
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
			if (!isHaveShare) {
				RomaToast.showMessage(mActivity, "当前未安装分享平台，如微信");
				return;
			}
			ShareBoardConfig config = new ShareBoardConfig();
			config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
			config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE); // 圆角背景
//                config.setTitleVisibility(false); // 隐藏title
//                config.setCancelButtonVisibility(false); // 隐藏取消按钮
			mShareAction.open(config);
		}
	}
	
	private void handleBackAction() {
		int handleBackType = backType;
		try {
			//[需求]添加支持设置Url后退类型栈 判断当前Url的后退处理方式:
			if (urlBackTypeMap != null && !urlBackTypeMap.isEmpty()) {
				String cutUrl = getKdsWebView().getUrl();
				if (!TextUtils.isEmpty(cutUrl)) {
					// "0"为逐步后退(Web历史记录栈), "1"为直接关闭(遇到此地址直接关闭Activity);
					String backType = null;
					// 判断 后退类型Map表里是否包含指定 urlKey 键:
					if (urlBackTypeMap.containsKey(cutUrl)) {
						backType = urlBackTypeMap.get(cutUrl);
					} else {
						Set<String> urlKeys = urlBackTypeMap.keySet();
						for (String urlKey : urlKeys) {
							if (cutUrl.contains(urlKey)) {
								backType = urlBackTypeMap.get(urlKey);
								break;
							}
						}
					}

					if (!TextUtils.isEmpty(backType) && NumberUtils.isNumber(backType)) {
						handleBackType = Integer.parseInt(backType);
					}
				}
			}
		}catch (Exception e){
			Logger.e("TAG", e.getMessage());
		}


		switch (handleBackType) {
        case 1:				// 直接finish;
            this.finish();
            break;
        case 2: 			// 关闭不销毁;
			TouguShowH5Activity.this.findViewById(R.id.svg_back).setEnabled(false); // 避免重复点击，在返回后的界面重新设为可点击
            if(getKdsWebView() != null && getKdsWebView().canGoBack()){
                getKdsWebView().goBack();
                break;
            }
            moveTaskToBack(true);
			// 将另一组进程中Activity Task队列移至前台:
			moveTaskToFront();
			break;
        case 0:
        default:			// 逐级关闭, 最后finish;
            if(getKdsWebView() != null && getKdsWebView().canGoBack()){
                getKdsWebView().goBack();
                break;
            }
            this.finish();
        }
	}

	/**
	 * 将当前App的另一组进程的 Activity Task队列移至前台:
	 */
	private void moveTaskToFront() {
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
		if(backType == 1)
			return super.onKeyDown(keyCode,event);
		
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// 系统软键盘返回键统一以顶部返回键事件处理:
			handleBackAction();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
	
	@Override
	public void onDestroy() {
		if (getKdsWebView() != null) {
			getKdsWebView().setWebChromeClient(null);
			getKdsWebView().setWebViewClient(null);
			getKdsWebView().getSettings().setJavaScriptEnabled(false);
			getKdsWebView().clearCache(true);
		}

		// TODO Auto-generated method stub
		super.onDestroy();
        //注销广播接收器
		this.unregisterReceiver(mBroadcastReceiver);

		try {
			if (getIntent() != null) {
				String functionCode = getIntent().getStringExtra("functionCode");
				if (!TextUtils.isEmpty(functionCode) && (functionCode.equalsIgnoreCase("ROMA_MyBusinessManagement")
								|| functionCode.startsWith("KDS_TICKET")
								|| functionCode.startsWith("KDS_PHONE_TICKET"))) {
					// 清空所有Cookie
					CookieSyncManager.createInstance(OriginalContext.getContext());  //Create a singleton CookieSyncManager within a context
					CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
					// cookieManager.removeAllCookie();// Removes all cookies.
					cookieManager.removeSessionCookie();
					CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
				}
			}
		}catch (Exception e){
			Logger.e("TAG", e.getMessage());
		}
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
					RomaToast.showMessage(mActivity, platform + " 分享成功啦");
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
}
