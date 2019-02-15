package roma.romaway.commons.android.tougu;

import java.util.HashMap;
import java.util.Map;

import roma.romaway.commons.android.webkit.WebkitSherlockFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.android.phone.utils.JYStatusUtil.OnLoginAccountListener;
import com.romaway.commons.db.PersistentCookieStore;
import com.romaway.commons.json.JSONUtils;
import com.romaway.commons.log.Logger;

public class KdsWebKitSherlockFragment  extends WebkitSherlockFragment{

	/**与js数据交互的接口*/
	private TouguJavascripInterface mJsInterface;
	
	/**	
	 * 搜索返回监听广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("KDS_TG_STOCK_INFO")) {//键盘精灵操作之后
				String stockCode = intent.getExtras().getString(BundleKeyValue.HQ_STOCKCODE);
				String stockName = intent.getExtras().getString(BundleKeyValue.HQ_STOCKNAME);
				
				//键盘精灵
			    Map<String, String> map = new HashMap<String, String>();
			    map.put("stockName", stockName);
			    map.put("stockCode", stockCode);
			    map.put("stockIndustry", "证券金融");
			    
			    mJsInterface.commit(
			    		mJsInterface.getBackMethodName("showKeyBoardTG"), map);
				
			}else if (intent.getAction()
					.equals("action." + mActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl")) {//交易登录成功之后
				
				final JYStatusUtil mJYStatusUtil = new JYStatusUtil(mActivity, true);
				mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener(){

					@Override
					public void onLoginAccount(int status, String loginAccount) {
						// TODO Auto-generated method stub
						if(status == JYStatusUtil.JY_LOGIN_STATUS_OK){
							PersistentCookieStore cookieStore = new PersistentCookieStore(mActivity);
							String userId = cookieStore.getValue("user_id");
							
							//交易登录成功后的返回
						    Map<String, String> map = new HashMap<String, String>();
						    map.put("state", "0");//表示资金账号登录成功
						    map.put("userId", userId);
						    map.put("userId", userId);
						    map.put("fundId", loginAccount);
						    map.put("clientId", "null");
						    Logger.d("tag", "jsonBuilder : "+
						    		JSONUtils.toJson(mJsInterface.getBackMethodName(
						    				"gotoTradeLoginViewTG"), map));
						    
						    mJsInterface.commit(
						    		mJsInterface.getBackMethodName("gotoTradeLoginViewTG"), map);
						}
					}
				});
				Logger.d("tag", "setAppVersion onReceive");
				
			}else if(intent.getAction().equals("action.reglogin.onSuccess")){//手机注册登录成功
				PersistentCookieStore cookieStore = new PersistentCookieStore(mActivity);
				String userId = cookieStore.getValue("user_id");
				
				Map<String, String> map = new HashMap<String, String>();
				if (!RomaUserAccount.isGuest()){
					map.put("userId", userId);
				    map.put("state", "0");//表示手机注册成功
				    mJsInterface.commit(
				    		mJsInterface.getBackMethodName("ShowRegisterView"), map);
				}
			}
		}
	};
	
	/**
	 * 返回类型：0：按照web页一页一页返回；1：直接finish Activity
	 */
	private int backType = 0;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);  
    }
    
//    private ProgressBar mProgressBar;
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        setIsResumeLoad(false);
        
        String url = getArguments().getString("WebUrl");
		System.out.println("WebUrl:  "+url);
		
		//Logger.d("tag", "1-TouguShowH5Activity url:"+url);
		//[需求]：将带参数的url转化成实际的url链接
		url = UrlParamsManager.getInstance(mActivity).toNewUrl(url);
		//Logger.d("tag", "2-TouguShowH5Activity url:"+url);
		
		System.out.println("toNewUrl:  "+url);
		
		/*
		int visibilityTile = this.getIntent().getIntExtra("key_titleVisibility", View.GONE);
		setTitleVisibility(visibilityTile);
		*/
		
//		backType = this.getIntent().getIntExtra("backType", 0);
		this.setUrl(url);
//		getKdsWebView().setWebChromeClient(new WebChromeClient(){
//			@Override
//			public void onReceivedTitle(WebView view, String title) {
//				// TODO Auto-generated method stub
//				super.onReceivedTitle(view, title);
//				mActionBar.setTitle(title);
//			}
//			
//			public void onProgressChanged(WebView view,int newProgress){
//				if(newProgress == 100)
//					mProgressBar.setVisibility(View.GONE);
//	           super.onProgressChanged(view, newProgress);
//            }
//		});
		
		mJsInterface = new TouguJavascripInterface(mActivity, getKdsWebView());
		
		//不可以删除该项，不然登录无法跳转
        this.addJavascriptInterface(mJsInterface);
        
        String url1 = getUrl();
        getKdsWebView().loadUrl(url1);
        
        Logger.d("TouguShowH5Activity", "setUrl:"+url+",getUrl:"+url1);
        
        IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("action." + mActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl");
        myIntentFilter.addAction("KDS_TG_STOCK_INFO");
        myIntentFilter.addAction("action.reglogin.onSuccess");
		mActivity.registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
    
    @Override
    public void onResumeInit() {
        // TODO Auto-generated method stub
        super.onResumeInit();
        
        // if()
        mActionBar.hideIcon();
    }
    
    
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
        //注销广播接收器
		mActivity.unregisterReceiver(mBroadcastReceiver);
	}
}
