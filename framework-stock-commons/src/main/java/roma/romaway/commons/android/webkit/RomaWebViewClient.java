package roma.romaway.commons.android.webkit;

import android.net.http.SslError;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.romaway.commons.log.Logger;

public class RomaWebViewClient extends WebViewClient{

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, 
            String failingUrl) {
        // TODO Auto-generated method stub
        super.onReceivedError(view, errorCode, description, failingUrl);
        
        Logger.d("RomaWebViewClient", "onReceivedError description:"+description+
                ",failingUrl:"+failingUrl+",errorCode:"+errorCode);
        
        ViewGroup root = (ViewGroup)view.getParent();
        if(root != null)
        	root.setVisibility(View.GONE);
    }

    @Override
    public void onPageFinished(final WebView view, final String url) {  
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);
        
    }
    
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
    		SslError error) {
    	// TODO Auto-generated method stub
    	//super.onReceivedSslError(view, handler, error);
    	Logger.d("RomaWebViewClient", "onReceivedSslError " + error);
    	handler.proceed();// 接受所有网站的证书
    }
}
