package com.romaway.android.phone.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.romaway.android.phone.config.Configs;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.commons.log.Logger;

public class MarkInfoUtil {

    private Context mContext;
    public MarkInfoUtil(Context context){
        mContext = context;
    }

    @SuppressLint("NewApi")
    public void sendMarkInfoToJYMode(){
        WebView webView = new WebView(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        /***打开本地缓存提供JS调用**/
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024*1024);
        String appCachePath = mContext.getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setSavePassword(false);
        webView.loadData("","text/html","UTF-8");

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(final WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);

              //设置交易地址，给网页传递交易地址
                Logger.d("MarkInfosUtil", "mark:"+SysConfigs.getDeviceAddress());
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadUrl("javascript:setMarkInfo('"+SysConfigs.getDeviceAddress()+"')");//传递交易留痕给交易网页模块
                    }
                });
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
