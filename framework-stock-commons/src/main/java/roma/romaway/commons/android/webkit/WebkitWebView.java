package roma.romaway.commons.android.webkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

public class WebkitWebView extends RomaWebView {

	private static final String APP_CACAHE_DIRNAME = "/webcache"; 
	 
	private Context context;
	public WebkitWebView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public WebkitWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		init();
	}

	
	@SuppressLint("NewApi")
	private void init(){
		
		getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		getSettings().setSupportZoom(true);
		setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		setBackgroundColor(0x00000000);

		getSettings().setJavaScriptEnabled(true); 
        getSettings().setRenderPriority(RenderPriority.HIGH); 
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式 
        // 开启 DOM storage API 功能 
        getSettings().setDomStorageEnabled(true); 
        //开启 database storage API 功能 
        getSettings().setDatabaseEnabled(true);  
        
        String cacheDirPath = 
        		context.
        		getFilesDir().
        		getAbsolutePath()+APP_CACAHE_DIRNAME; 
        //设置数据库缓存路径 
        getSettings().setDatabasePath(cacheDirPath); 
        //设置  Application Caches 缓存目录 
        getSettings().setAppCachePath(cacheDirPath); 
        //开启 Application Caches 功能 
        getSettings().setAppCacheEnabled(true); 
		setFocusable(false);
		
		
		this.setKdsWebViewClient(new RomaWebViewClient(){
			@Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            //Log.i(TAG, "intercept url=" + url);
	            view.loadUrl(url);
	            return true;
	        }
		});
	}
}
