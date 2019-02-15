package roma.romaway.commons.android.webkit;

/**
 * Created by hongrb on 2017/4/11.
 */
public interface GxsInterface {

    /**
     * 跳转界面
     *
     * @param url
     */
    @android.webkit.JavascriptInterface
    public abstract void switchWebView(String url, int hasBottomLayout);

}
