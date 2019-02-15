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

public class HQRefreshtimeUtil {

	private Context mContext;
	private int hqRefreshtime;

	public final static int TIMER_OUT_TYPE_SET_TIME = 0;
	public final static int TIMER_OUT_TYPE_SET_LOCK_SCREEN = 1;
	private String lockScreenType = null;
	public final static String lockTypePtjy = "ptjy";
	public final static String lockTypeRzrq = "rzrq";

	public HQRefreshtimeUtil(Context context) {
		mContext = context;
	}

	public void setHQRefreshOuttime(int hqRefreshtime) {
		this.hqRefreshtime = hqRefreshtime;
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
				Logger.d("hqRefreshtime", "switchWebView onPageFinished url:"
						+ url + ",hqRefreshtime:" + hqRefreshtime);
				view.post(new Runnable() {
					@Override
					public void run() {
						view.loadUrl("javascript:setHqTime('" + hqRefreshtime + "')");
					}
				});
			}
		});

		// webView.addJavascriptInterface(this, "KDS_Native");

		String url = "";
		webView.loadUrl(url);
	}
}
