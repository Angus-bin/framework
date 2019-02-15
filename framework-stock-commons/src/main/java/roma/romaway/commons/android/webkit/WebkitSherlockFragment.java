package roma.romaway.commons.android.webkit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import roma.romaway.commons.android.webkit.keyboard.ZXJT_KeyboardManager;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.romaway.activity.basephone.BaseSherlockFragment;
import com.romaway.activity.basephone.BaseSherlockFragmentActivity;
import com.romaway.android.phone.ActionBarEventMgr;
import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.R;
import com.romaway.common.android.base.Res;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.szkingdom.stocksearch.keyboard.Kds_KeyBoardView;

public class WebkitSherlockFragment extends BaseSherlockFragment {

	private String mUrl;
	// 0:VISIBLE;4:INVISIBLE;8:GONE
	private int hasSearch = View.GONE;
	private int hasRefresh = View.GONE;
	public RomaWebView mRomaWebView;
	/** 标记是否调用H5接口 */
	private boolean toJavascriptEnable = true;
	private String webTitle = "";

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("KDS_STOCK_LIST_ITEM")) {
				String stockCode = intent.getExtras().getString(
						BundleKeyValue.HQ_STOCKCODE);
				String marketId = intent.getExtras().getString(
						BundleKeyValue.HQ_MARKETID);
				// Logger.d("RomaSearchFragmentNew", "onItemClick stockCode = "
				// + stockCode);
				Message message = Message.obtain();
				message.obj = stockCode;
				message.what = 0;
				setIsResumeLoad(false);// 不再重新刷新
				// [Bug]传递给交易 H5 市场代码建议要求为空或 0, 不能为 -1 ;
				if (!StringUtils.isEmpty(marketId) && !"-1".equalsIgnoreCase(marketId)) {
					setInputContentZxjt(stockCode, marketId);
				} else {
					setInputContent(stockCode);
				}
			}
		}
	};

	public WebkitSherlockFragment() {
		super();

	}

	// =============== 自定义键盘接口 ================
	private RelativeLayout rl_parent;
	private FrameLayout fr_keyboard_parent;
	private Kds_KeyBoardView kdsKeyBoardView;
	private ZXJT_KeyboardManager keyboardManager;

	private void initKeyBoard() {
		if (kdsKeyBoardView == null) {
			kdsKeyBoardView = new Kds_KeyBoardView(getActivity());
			RelativeLayout.LayoutParams lp =
					new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT,
							RelativeLayout.LayoutParams.WRAP_CONTENT);
			kdsKeyBoardView.setLayoutParams(lp);
			kdsKeyBoardView.setVisibility(View.GONE);
			fr_keyboard_parent.addView(kdsKeyBoardView);
		}

		if (keyboardManager == null) {
			keyboardManager = new ZXJT_KeyboardManager(getActivity(), rl_parent, kdsKeyBoardView);
		}
	}
	// =============== 自定义键盘接口 ================

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

	}

	@Override
	public void onResumeInit() {
		// TODO Auto-generated method stub
		super.onResumeInit();

		try{
			if(mActionBar == null)
				mActionBar = ((SherlockFragmentActivity)getActivity()).getSupportActionBar();
			
			mActionBar.resetTitleToDefault();
			mActionBar.showIcon();
			//首页不使用
			if (getWebTitle() != null && !getWebTitle().contains("普通交易") && !getWebTitle().endsWith(".html")) {
				mActionBar.setTitle(getWebTitle());
			}
			mActionBar.setSubtitle(null);
		}catch(Exception e){
			
		}
		mActivity = (SherlockFragmentActivity) this.getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		if (!isPullMode)
			return inflater.inflate(R.layout.roma_webview_normal_layout,
					container, false);
		else
			return inflater.inflate(R.layout.roma_ptr_webview, container, false);
	}

	private boolean isPullMode = false;// 用于控制采用哪种模式

	public void setPullMode(boolean flag) {
		isPullMode = flag;
	}

	private RomaPullToRefreshWebView romaPullToRefreshWebView;
	private LinearLayout root;
	ReEventsController mReEventController;
	
	protected RelativeLayout titleRoot;
	public void setTitleLayout(View view) {
		titleRoot.setVisibility(View.VISIBLE);
		titleRoot.addView(view);
	}

	public void setTitleBackgroundColor(int color) {
		titleRoot.setBackgroundColor(color);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		if(Res.getBoolean(R.bool.is_use_2_0_keyboard)) {
			rl_parent = (RelativeLayout) view.findViewById(R.id.rl_parent);
			View decorView = mActivity.getWindow().getDecorView();
			fr_keyboard_parent = (FrameLayout) decorView.findViewById(R.id.fr_keyboard_parent);
			initKeyBoard();
		}
		if (!isPullMode) {
			titleRoot = (RelativeLayout) view.findViewById(R.id.rl_titleRoot);
			root = (LinearLayout) view.findViewById(R.id.root);

			mRomaWebView = new RomaWebView(mActivity);
			mRomaWebView.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));

			root.addView(mRomaWebView);
		} else {

			romaPullToRefreshWebView = (RomaPullToRefreshWebView) view
					.findViewById(R.id.roma_pull_refresh_webview);
			mRomaWebView = (RomaWebView) romaPullToRefreshWebView
					.getRefreshableView();
			// 增加下拉刷新
			romaPullToRefreshWebView
					.setOnRefreshListener(new OnRefreshListener<WebView>() {

						@Override
						public void onRefresh(
								PullToRefreshBase<WebView> refreshView) {
							// TODO Auto-generated method stub
							if(mRomaWebView != null){
								//2500毫秒内不允许刷新
								if(mReEventController.isRepeat(mRomaWebView, 500))
									return;
								mRomaWebView.reload();
							}
						}
					});
			mRomaWebView.setKdsWebViewClient(new RomaWebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					// TODO Auto-generated method stub
					super.onPageFinished(view, url);
					if(romaPullToRefreshWebView != null)
						romaPullToRefreshWebView.onRefreshComplete();
				}
			});
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			mRomaWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		// 设置这个主要是用于流畅的滑动
		// mRomaWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		mRomaWebView.getSettings().setJavaScriptEnabled(true);
		mRomaWebView.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.NORMAL);

		/*** 打开本地缓存提供JS调用 **/

		// Set cache size to 8 mb by default. should be more than enough
		mRomaWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		// This next one is crazy. It's the DEFAULT location for your app's
		// cache
		// But it didn't work for me without this line.
		// UPDATE: no hardcoded path. Thanks to Kevin Hawkins
		String appCachePath = mActivity.getApplicationContext().getCacheDir()
				.getAbsolutePath()
				+ "/webcache";
		//设置webView的调试模式
		//if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		//	WebView.setWebContentsDebuggingEnabled(true);
		
		mRomaWebView.getSettings().setDatabaseEnabled(true);
		mRomaWebView.getSettings().setDatabasePath(appCachePath);
		mRomaWebView.getSettings().setDomStorageEnabled(true);
		mRomaWebView.getSettings().setAppCachePath(appCachePath);
		mRomaWebView.getSettings().setAllowFileAccess(true);
		mRomaWebView.getSettings().setAppCacheEnabled(true);
		mRomaWebView.getSettings().setCacheMode(
				WebSettings.LOAD_NO_CACHE);
		mRomaWebView.getSettings().setSavePassword(false);
		mRomaWebView.getSettings().setSaveFormData(true);
		mRomaWebView.getSettings().setAllowFileAccessFromFileURLs(true);
		mRomaWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		mRomaWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);

				if (mRomaWebView.getTitle() != null
						&& mActionBar != null
						&& mActionBar.getTitle() != null
						&& !mActionBar.getTitle().toString()
								.equals(mRomaWebView.getTitle())) {
					if (getWebTitle() == null || getWebTitle().length() == 0 || !getWebTitle().equalsIgnoreCase(mRomaWebView.getTitle())) {
						setWebTitle(mRomaWebView.getTitle());
					}
				}
			}

			@Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				if (Logger.getDebugMode()){
					return false;
				} else {
					return true;
				}
			}
			
			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				// TODO Auto-generated method stub
				super.onShowCustomView(view, callback);
			}
		});
		
		//[bug] 解决goback返回时title不更新的问题 wanlh 2015/12/05
		mRomaWebView.setWebViewClient(new RomaWebViewClient(){
			@Override
		    public void onPageFinished(final WebView view, final String url) {  
		        // TODO Auto-generated method stub
		        super.onPageFinished(view, url);
		        if(view != null)
		        	setWebTitle(view.getTitle());
		    }
		});
		mRomaWebView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
		
		mReEventController = new ReEventsController();
	}
	
	public void timerAction(String timerName, String actionType) {

	}

	/**
	 * 用于设置界面的展示模式
	 * 
	 * @return true:界面滚动不受屏幕高度限制，比如很长的列表界面；false：页面最大高度就是屏幕的高度,常用于非列表的界面
	 */
	public boolean onShowMode() {

		return false;
	}

	/**
	 * 网页加载完了之后会回调该方法
	 * 
	 * @param view
	 * @param url
	 */
	public void onLoadFinished(WebView view, String url) {

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		Logger.d("JiaoYiModeActivity", "WebkitSherlockFragment onDestroy()");

		// getKdsWebView().setVisibility(View.GONE);
		getKdsWebView().removeAllViews();
		// root.removeAllViews();
		getKdsWebView().destroy();
		//强制关闭自定义键盘
		onHideKeyBoard();
		try{
			mActivity.unregisterReceiver(mBroadcastReceiver);
		}catch(Exception e){
			
		}
	}

	/**
	 * 隐藏系统键盘,需要经过该方法隐藏，不然编辑框会没有光标的
	 * 
	 * @param ed
	 */
	@SuppressLint("NewApi")
	private void hideSoftInputMethod(EditText ed) {
		mActivity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		int currentVersion = android.os.Build.VERSION.SDK_INT;
		String methodName = null;
		if (currentVersion >= 16) {
			// 4.2
			methodName = "setShowSoftInputOnFocus";
		} else if (currentVersion >= 14) {
			// 4.0
			methodName = "setSoftInputShownOnFocus";
		}

		if (methodName == null) {
			ed.setInputType(InputType.TYPE_NULL);
		} else {
			Class<EditText> cls = EditText.class;
			Method setShowSoftInputOnFocus;
			try {
				setShowSoftInputOnFocus = cls.getMethod(methodName,
						boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(ed, false);
			} catch (NoSuchMethodException e) {
				ed.setInputType(InputType.TYPE_NULL);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private JavascriptInterface mJavascriptInterface = new JavascriptInterface(
			this);

	/**
	 * 添加与H5交互
	 * 
	 * @param javascriptInterface
	 */
	public void addJavascriptInterface(JavascriptInterface javascriptInterface) {
		mJavascriptInterface = javascriptInterface;
		javascriptInterface.setKeyboardManager(keyboardManager);
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
			Logger.i("WebkitSherlockFragment",
					"addJavascriptInterface false javascriptInterface:"
							+ javascriptInterface + ",RomaWebView:"
							+ mRomaWebView);
		}
	}

	public void resetLoadUrl(String url) {
		
		if(null != getView())
			getView().removeCallbacks(resumeRunnable);
		
		if (mRomaWebView != null){
			//2500毫秒内不允许刷新
			if(mReEventController != null && 
					mReEventController.isRepeat(mRomaWebView, 2000))
				return;
			mRomaWebView.loadJsMethodUrl(url);
		}
	}

	public void resetLoadUrl(String url, boolean flag) {
		if(null != getView())
			getView().removeCallbacks(resumeRunnable);
		
		if (mRomaWebView != null){
			//2500毫秒内不允许刷新
			if(!flag && 
					mReEventController != null && 
					mReEventController.isRepeat(mRomaWebView, 2000))
				return;
			mRomaWebView.loadJsMethodUrl(url);
		}
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setToJavascriptEnable(boolean flag) {
		this.toJavascriptEnable = flag;
	}

	public boolean getToJavascriptEnable() {
		return toJavascriptEnable;
	}

	public void setHasRefresh(int hasRefresh) {
		this.hasRefresh = hasRefresh;
	}

	public void setHasSearch(int hasSearch) {
		this.hasSearch = hasSearch;
	}
	
	public int getHasRefresh() {
		return hasRefresh;
	}

	public int getHasSearch() {
		return hasSearch;
	}
	
	public String getWebTitle() {
		return webTitle;
	}

	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
		if (mActionBar != null && 
				webTitle != null && //[bug] 修复点击url链接出现崩溃的问题 wanlh 2015/12/05
				!webTitle.contains("普通交易") &&
				!webTitle.endsWith(".html"))
			mActionBar.setTitle(webTitle);
	}

	/**
	 * 添加与H5交互
	 * 
	 * @param javascriptInterface
	 * @param interfaceName
	 */
	public void addJavascriptInterface(JavascriptInterface javascriptInterface,
			String interfaceName) {

		mJavascriptInterface = javascriptInterface;
		javascriptInterface.setKeyboardManager(keyboardManager);
		if (javascriptInterface != null && mRomaWebView != null) {
			javascriptInterface.setInterfaceName(interfaceName);
			mRomaWebView.addJavascriptInterface(javascriptInterface,
					javascriptInterface.getInterfaceName());
			if (Build.VERSION.SDK_INT < 17) {
				mRomaWebView.removeJavascriptInterface("accessibility");
				mRomaWebView.removeJavascriptInterface("accessibilityTraversal");
				mRomaWebView.removeJavascriptInterface("searchBoxJavaBridge_");
			}
		}
	}

	public RomaWebView getKdsWebView() {
		return mRomaWebView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		// 默认屏蔽右上角的按钮
		 if(hasSearch!=View.VISIBLE&&hasRefresh!=View.VISIBLE){
			 mActionBar.setMenuLayout(-1, null);
		 }else{// 显示某个按钮
			// 根据是否显示搜索按钮和刷新按钮修改界面。调用接口。
			if (hasRefresh != View.VISIBLE) {
				mActionBar.hideRefreshButton();
			}else
				mActionBar.showRefreshButton();
			
			if (hasSearch != View.VISIBLE) {
				mActionBar.hideSearchButton();
			}else
				mActionBar.showSearchButton();
			// 添加ActionBar右上角布局View:
			ActionBarEventMgr.getInitialize().setOptionsMenu(getSherlockActivity(), Res.getInteger(R.integer.roma_common_right_action_bar));
		}
	}

	/**
	 * 用于监听切换界面，之后处理事件
	 * 
	 * @param url
	 * @param flag
	 */
	public void onSwitchWebView(String url, int hasRefresh) {
	}

	/**
	 * 调用自定义键盘
	 * 
	 * @param type
	 * @param inputId
	 */
	public void onShowKeyBorad(String type) {

	}

	public void onKeyBoardChangeObserver(boolean isShow){

	}


	/**
	 * 发送自定义键盘键值给js代码
	 * 
	 * @param key
	 */
	public void setInputContent(final String keyboardCode) {
		if (mRomaWebView != null)
			mRomaWebView.loadJsMethodUrl("javascript:fillInputContent('" + keyboardCode
					+ "')");
	}

	/**
	 * 发送自定义键盘键值给js代码
	 *
	 * @param key
	 */
	public void setInputContentZxjt(final String keyboardCode, final String keyboardMarketId) {
		if (mRomaWebView != null)
			mRomaWebView.loadJsMethodUrl("javascript:fillInputContent('"+keyboardCode+"','" +keyboardMarketId+"')");
	}

	@Override
	public void onMenuItemAction(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.sb_refresh && mRomaWebView != null) {
			//2500毫秒内不允许刷新
			if(mReEventController.isRepeat(mRomaWebView, 500))
				return;
			
			if(Res.getBoolean(R.bool.h5_handlePageRefresh))
				refresh();
			else
				mRomaWebView.reload();
		}
	}

	private boolean pauseFlag = false;

	private boolean isResumeLoad = true;

	/**
	 * 设置返回到上一个页面时是否重新加载页面
	 * 
	 * @param isResumeLoad
	 */
	public void setIsResumeLoad(boolean isResumeLoad) {
		this.isResumeLoad = isResumeLoad;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		IntentFilter mFilter = new IntentFilter("KDS_STOCK_LIST_ITEM");
		mActivity.registerReceiver(mBroadcastReceiver, mFilter);

		Logger.d("WebkitSherlockFragment",
				"[WebkitSherlockFragment加载] pauseFlag:" + pauseFlag
						+ ",isResumeLoad:" + isResumeLoad + ",className:"
						+ this.getClass().getName());
		
		if (mRomaWebView != null /*&& pauseFlag*/ && isResumeLoad) {
			if(Res.getBoolean(R.bool.h5_handlePageRefresh))
				mRomaWebView.loadJsMethodUrl("javascript:handlePageBack()");
			else
				getView().postDelayed(resumeRunnable, 400);
		}

		pauseFlag = false;
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		showNetReqProgress();
		mRomaWebView.loadJsMethodUrl("javascript:handlePageBack()");
		mRomaWebView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				hideNetReqProgress();
			}
		}, 1000);
	}
	
	private Runnable resumeRunnable = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			resetLoadUrl(getUrl());
		}
	};
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		pauseFlag = true;
	}

	/**
	 * 隐藏自定义键盘
	 */
	public void onHideKeyBoard() {
		// 告诉主线程一个消息，帮我更新ui，内容：bitmap
		// Message msg = new Message();
		// // 消息的代号，是一个int类型
		// msg.what = 0;
		// // 要传递的消息对象
		// handler.sendMessage(msg);
		if(((BaseSherlockFragmentActivity) mActivity) != null)
			((BaseSherlockFragmentActivity) mActivity).hideKeyboard();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(keyboardManager!=null&&keyboardManager.isShowBoard()){
					keyboardManager.hideKeyboard();
				}
			}
		});
	}

	/**
	 * 自助开户
	 */
	public void onSelfserviceAccount() {

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	public void setFragmentTag(Fragment fragment) {
		Class superView = fragment.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();
		try {
			Field field =  superView.getDeclaredField("mTag");
			field.setAccessible(true);
			field.set(fragment, "android:switcher:" + fragment.getClass().getName()+":"+((SherlockFragment) fragment).getRomaTag());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	protected OnCallbackListener mOnCallbackListener;
	public void setCallbackListener(OnCallbackListener onCallbackListener){
		mOnCallbackListener = onCallbackListener;
	}

	public interface OnCallbackListener extends Parcelable {
		void onCallback();
	}
}
