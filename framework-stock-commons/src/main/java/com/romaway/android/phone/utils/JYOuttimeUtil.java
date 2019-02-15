package com.romaway.android.phone.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.Configs;
import com.romaway.commons.log.Logger;

public class JYOuttimeUtil {

	private Context mContext;
	private int jyouttime;

	public final static int TIMER_OUT_TYPE_SET_TIME = 0;
	public final static int TIMER_OUT_TYPE_SET_LOCK_SCREEN = 1;
	private int timerOutType = TIMER_OUT_TYPE_SET_TIME;
	private String lockScreenType = null;
	public final static String lockTypePtjy = "ptjy";
	public final static String lockTypeRzrq = "rzrq";

	public JYOuttimeUtil(Context context, int type) {
		mContext = context;
		timerOutType = type;
	}

	public void setJYOuttime(int jyoutime) {
		this.jyouttime = jyoutime;
		newWebviewLoadUrl();
	}

	public void setLockScreenType(String lockScreenType) {
		this.lockScreenType = lockScreenType;
		newWebviewLoadUrl();
	}

	@SuppressLint("NewApi")
	private void newWebviewLoadUrl() {
		WebView webView = new WebView(mContext);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.NORMAL);
		/*** 打开本地缓存提供JS调用 **/
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		String appCachePath = mContext.getApplicationContext().getCacheDir()
				.getAbsolutePath();
		webView.getSettings().setAppCachePath(appCachePath);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.getSettings().setSavePassword(false);
		webView.setWebChromeClient(new WebChromeClient() {

		});
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(final WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				// 设置交易地址，给网页传递交易地址
				Logger.d("newWebviewLoadUrl",
						"switchWebView onPageFinished url:" + url
								+ ",timerOutType:" + timerOutType
								+ ",lockScreenType:" + lockScreenType);
				if (timerOutType == TIMER_OUT_TYPE_SET_TIME) {
					Logger.d("jytime", "hehe" + jyouttime);
					view.post(new Runnable() {
						@Override
						public void run() {
							view.loadUrl("javascript:setTimeOut('" + jyouttime + "')");
						}
					});

				} else if (timerOutType == TIMER_OUT_TYPE_SET_LOCK_SCREEN) {
					view.post(new Runnable() {
						@Override
						public void run() {
							view.loadUrl("javascript:TimeoutHandler('" + lockScreenType
									+ "')");
						}
					});
				}
			}
		});

		// webView.addJavascriptInterface(this, "KDS_Native");

		String url = "";
		webView.loadUrl(url);
	}
}
