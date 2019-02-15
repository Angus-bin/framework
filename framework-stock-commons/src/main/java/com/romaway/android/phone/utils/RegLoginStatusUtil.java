package com.romaway.android.phone.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.romaway.android.phone.config.Configs;
import com.romaway.commons.log.Logger;

public class RegLoginStatusUtil {
    
    private Context mContext;
    public RegLoginStatusUtil(Context context){
        mContext = context;
    }
    
    @SuppressLint("NewApi")
    public void sendTelephoneToJYMode(final String telephone){
        WebView webView = new WebView(mContext);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        //    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        /***打开本地缓存提供JS调用**/  
        webView.getSettings().setDomStorageEnabled(true);  
        webView.getSettings().setAppCacheMaxSize(1024*1024*8);  
        String appCachePath = mContext.getApplicationContext().getCacheDir().getAbsolutePath();  
        webView.getSettings().setAppCachePath(appCachePath);  
        webView.getSettings().setAllowFileAccess(true);   
        webView.getSettings().setAppCacheEnabled(true);  
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setSavePassword(false);
        
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(final WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                
              //设置交易地址，给网页传递交易地址
                Logger.d("RegLoginStatusUtil", "switchWebView onPageFinished url:"+url+",telephone:"+telephone);

               // if(getToJavascriptEnable() && funKey != null){//需要调用接口（交易模块）
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadUrl("javascript:setIsRegister('"+telephone+"')");
                    }
                });
               //       funKey = null;//必须设置空，以免重新加载后再次进入该接口，死循环调用
               // }
            }
        });

        webView.addJavascriptInterface(this, "KDS_Native");
        if (Build.VERSION.SDK_INT < 17) {
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        String url = Configs.getJiaoYiUrl(mContext, 
                "/kds519/view/rzrq/home/rzrq_header.html");
        webView.loadUrl(url);
    }
}
