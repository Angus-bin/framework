package roma.romaway.commons.android.webkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romaway.android.phone.R;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import roma.romaway.commons.android.theme.SkinManager;

public class WebkitActivity extends Activity implements View.OnClickListener{

	protected RelativeLayout rl_title_layout;
	protected RelativeLayout rootView;
	protected RelativeLayout rl_img_right;
	protected ImageView img_right_icon;
	protected TextView txt_title, txt_tiaocang, txt_bianji, txt_share;
	protected SVGView svg_back;
	private RomaWebView mRomaWebView;
	public ProgressBar mProgressBar;
	public ProgressBar mProgressBar2;
	private String mUrl;
	private SVGView tiaocang_svg, txt_bianji_svg, txt_share_svg;
	private RelativeLayout rl_tougu_tiaocang, rl_tougu_bianji, rl_tougu_share;
	/** Web页面后退类型管理栈表 */
	public Map<String, String> urlBackTypeMap;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		urlBackTypeMap = new HashMap<String, String>();

		//String hasCache = this.getIntent().getStringExtra("key_hasCache");
		
		this.setContentView(R.layout.roma_webview_title_layout);
		//RomaPullToRefreshWebView kdsPullToRefreshWebView = (RomaPullToRefreshWebView) findViewById(R.id.ptrw_pull_refresh_webview);
			//mRomaWebView = (RomaWebView) kdsPullToRefreshWebView.getRefreshableView();
		
		rootView = (RelativeLayout) this.findViewById(R.id.root);
		mRomaWebView = (RomaWebView) this.findViewById(R.id.wv_romaWebView);//new RomaWebView(this);
		mProgressBar = (ProgressBar) this.findViewById(R.id.wv_ProgressBar);
		mProgressBar.setVisibility(View.GONE);
		mProgressBar2 = (ProgressBar) this.findViewById(R.id.wv_progressBar_title);
		//		mRomaWebView.setLayoutParams(new ViewGroup.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.MATCH_PARENT));
//		root.addView(mRomaWebView);

//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && mRomaWebView.isHardwareAccelerated())
//			mRomaWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		// 设置这个主要是用于流畅的滑动
		mRomaWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		if (Build.VERSION.SDK_INT >= 19)
			mRomaWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		mRomaWebView.getSettings().setBlockNetworkImage(true);//用于支持图片的显示
		mRomaWebView.getSettings().setJavaScriptEnabled(true);
		//mRomaWebView.getSettings().setLayoutAlgorithm(
		//		WebSettings.LayoutAlgorithm.NORMAL);

		/*** 打开本地缓存提供JS调用 **/
		//if(!StringUtils.isEmpty(hasCache) && 
		//		hasCache.equals("true"))
		{
			// Set cache size to 8 mb by default. should be more than enough
			mRomaWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
			// This next one is crazy. It's the DEFAULT location for your app's
			// cache
			// But it didn't work for me without this line.
			// UPDATE: no hardcoded path. Thanks to Kevin Hawkins
			String appCachePath = getApplicationContext().getCacheDir()
					.getAbsolutePath()
					+ "/webcache";
			mRomaWebView.getSettings().setDatabaseEnabled(true);
			mRomaWebView.getSettings().setDatabasePath(appCachePath);
			mRomaWebView.getSettings().setDomStorageEnabled(true);
			mRomaWebView.getSettings().setAppCachePath(appCachePath);
			mRomaWebView.getSettings().setAllowFileAccess(true);
			mRomaWebView.getSettings().setAppCacheEnabled(true);
			mRomaWebView.getSettings().setCacheMode(cacheMode);
			mRomaWebView.getSettings().setSavePassword(false);
			mRomaWebView.getSettings().setSaveFormData(true);
		}
		mRomaWebView.setWebChromeClient(new WebChromeClient() {
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if(newProgress == 100)
					mProgressBar.setVisibility(View.GONE);
			}
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);

				Logger.d("tag","web title:"+title);
				//if (mRomaWebView.getTitle() != null) {
//					if (getWebTitle() == null || getWebTitle().length() == 0) {
						//setWebTitle(mRomaWebView.getTitle());
						txt_title.setText(title/*mRomaWebView.getTitle()*/);
//					}
				//}
			}
		});
	}
	
	private int cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
	public void setWebViewCacheMode(int mode){
		cacheMode = mode;
		mRomaWebView.getSettings().setCacheMode(cacheMode);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		
		//++ 处理title
		rl_title_layout = (RelativeLayout) this.findViewById(R.id.rl_title_layout);
		rl_title_layout.setVisibility(visibility);
		
		if(visibility == View.VISIBLE){
			rl_title_layout.setBackgroundColor(Res.getColor(R.color.gxs_red));
			svg_back = (SVGView) this.findViewById(R.id.svg_back);
//			SVGView svg_guanzhu_icon = (SVGView) this.findViewById(R.id.svg_guanzhu_icon);
			svg_back.setSVGRenderer(new SVGParserRenderer(this, R.drawable.abs__navigation_back), "");
//			svg_guanzhu_icon.setSVGRenderer(SvgRes1.getSVGParserRenderer(
//					this, R.drawable.tougu_attention), null);
			svg_back.setOnClickListener(this);
			txt_title = (TextView) this.findViewById(R.id.txt_title);
		}
		//-- 处理title
	}
	private int visibility = View.GONE;
	public void setTitleVisibility(int visibility){
		this.visibility = visibility;
	}

	public void setTitle(String title){
		if(txt_title != null)
			if (!StringUtils.isEmpty(title)) {
				if (title.length() > 15) {
					title = title.substring(0, 15) + "...";
				}
				txt_title.setText(title);
			}
	}
	/**
	 * 添加与H5交互
	 * 
	 * @param javascriptInterface
	 * @param interfaceName
	 */
	public void addJavascriptInterface(JavascriptInterface javascriptInterface,
			String interfaceName) {

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
	
	public void resetLoadUrl(String url) {
		if (mRomaWebView != null)
			mRomaWebView.loadUrl(url);
	}

	public RomaWebView getKdsWebView() {
		return mRomaWebView;
	}
	
	public void setUrl(String url) {
		mUrl = url;
	}

	public String getUrl() {
		return mUrl;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		getKdsWebView().removeAllViews();
		getKdsWebView().destroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.svg_back){//web后退键
			if(mRomaWebView != null && mRomaWebView.canGoBack()){
				mRomaWebView.goBack();
			}else{
				this.finish();
			}
		}
	}
}
