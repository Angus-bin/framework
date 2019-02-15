package roma.romaway.commons.android.webkit;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.romaway.commons.log.Logger;

public class RomaWebView extends WebView{

    public RomaWebView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public RomaWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        
        this.setWebViewClient(new RomaWebViewClient());
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        invalidate();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    /**
     * 设置 KDS WebViewClient
     * @param romaWebViewClient
     */
    public void setKdsWebViewClient(RomaWebViewClient romaWebViewClient){
        this.setWebViewClient(romaWebViewClient);
    }

    /**
     * 用于高版本Android系统 安全同线程调用JS方法
     * @param url
     */
    public void loadJsMethodUrl(final String url) {
        post(new Runnable() {
            @Override
            public void run() {
                loadUrl(url);
            }
        });
    }

    /**
     * 用于高版本Android系统 安全同线程调用JS方法
     */
    public void reloadUrl() {
        post(new Runnable() {
            @Override
            public void run() {
                reload();
            }
        });
    }
}
