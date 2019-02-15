package com.romalibs.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.romaway.commons.json.JSONUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.commons.utils.NetUtils;

public class ApiProvider implements ApiInterface {
	
	private Settings mSettings;
	protected Context context;
	protected WebView webView;
	private Handler mHandler;
	private Bundle mBundle;
	protected Activity mActivity;
	
	protected ApiProvider(Context context){
		this.context = context;
	}
	
	/**
	 * 用于数据回调
	 * @author wanlh
	 *
	 */
	public interface Callback {
		/**
		 * 用于数据回调
		 * @param callback
		 */
		public void callback(Object callback);
	}
	
	/**
	 * api设置类，用于设置相关参数
	 * @author wanlh
	 *
	 */
	public class Settings{
		/**
		 * 设置WebView，可能实现类需要用到该对象,比如，需要给H5回传数据需要用到该对象
		 * @param webView
		 */
		public void setEnableWebView(WebView webView) {
			// TODO Auto-generated method stub
			ApiProvider.this.webView = webView;
		}
		
		public void setHandler(Handler handler) {
			mHandler = handler;
		}
		
		public void setBundle(Bundle bundle) {
			mBundle = bundle;
		}

		public void setActivity(Activity activity) {
			mActivity = activity;
		}
	}
	
	public WebView getWebView(){
		return webView;
	}
	
	public Handler getHandler() {
		return mHandler;
	}
	
	public Bundle getBundle() {
		return mBundle;
	}
	
	public Settings getSettings(){
		if(mSettings == null)
			mSettings = new Settings();
		
		return mSettings;
	}
	
	/**
	 * 保存接口名对应的回调函数名称
	 */
	private Map<String, String> backMethodNameMap = new HashMap<String, String>();
	
	/**
	 * 注册js接口的回调方法名
	 * @param toJsInterfaceName
	 * @param jsBackMethodName
	 */
	protected void registerBackMethodName(String toJsInterfaceName, String jsBackMethodName){
		
		backMethodNameMap.put(toJsInterfaceName, jsBackMethodName);
	}
	
	/**
	 * 通过原生接口名称获取回调方法名称
	 * @param key 原生提供给js的调用接口字符串
	 * @return
	 */
	public String getBackMethodName(String interfaceMethodName){
		String backMethodName = null;
		
		backMethodName = backMethodNameMap.get(interfaceMethodName);
		
		return backMethodName;
	}
	
	/**
	 * 提交map数据
	 * @param interfaceMethodName
	 * @param map 回传提交map数据给H5map
	 */
	@Override
	public void callBackToJs(String interfaceMethodName, Map<String, String> map){
		commit(getBackMethodName(interfaceMethodName),map);
	}
	
	
	/**
	 * 提交给js数据
	 * @param backMethodName
	 * @param map
	 */
	public void commit(String backMethodName, Map<String, String> map){
		String json = JSONUtils.toJson(backMethodName, map);
		Logger.d("TAG", "[js调用原生接口] to json data，jsonBuilder : " + json);
		//在没网络的情况下是不会传递数据给js
		if(!NetUtils.isNetworkConnected(mActivity))
			return;

		if(webView != null && !StringUtils.isEmpty(backMethodName)){
			try{
				webView.loadUrl(
						"javascript:"+backMethodName+"("+json+")");

			}catch(Exception e){
				e.printStackTrace();
				Logger.d("TAG", "[js调用原生接口] 异常！");
			}
		}
	}

	/**
	 * 提交给js数据
	 * @param backMethodName
	 * @param map
	 */
	public void commitStrData(String backMethodName, Map<String, String> map){
		String json = JSONUtils.toJson(backMethodName, map);
		Logger.d("TAG", "[js调用原生接口] to json data，jsonBuilder : " + json);
		//在没网络的情况下是不会传递数据给js
		if(!NetUtils.isNetworkConnected(mActivity))
			return;

		if(webView != null && !StringUtils.isEmpty(backMethodName)){
			try{
				webView.loadUrl(
						"javascript:"+backMethodName+"("+json+")");

			}catch(Exception e){
				e.printStackTrace();
				Logger.d("TAG", "[js调用原生接口] 异常！");
			}
		}
	}
	
	@Override
	public void showKeyBoardTG(String backMethodName, String stockCodes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLoginStateTG(String backMethodName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotoStockDetailTG(String backMethodName, String stockCode,
			String marketId, String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ShowRegisterView(String backMethodName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotoTradeLoginViewTG(String backMethodName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showShareTG(String backMethodName, String title, String url,
			String summary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeCurrentWindow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openNewWindow(String linkUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showToast(String backMethodName, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickImage(String backMethodName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imageDidClicked(String backMethodName, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void linkDidClicked(String backMethodName, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@JavascriptInterface
	public void pdfViewForApp(String pdfPath) {
		// TODO Auto-generated method stub
		final String pdfPath1 = pdfPath;
		(new Handler()).post(new Runnable(){// ui线程

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					Intent intent = new Intent("android.intent.action.VIEW");
				    intent.addCategory("android.intent.category.DEFAULT");
				    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				    //File file = getCacheRootDir(this, path);
				    //"/storage/emulated/0/kds/pdfPath/sample.pdf"
					Uri uri = Uri.fromFile(new File(pdfPath1));
				    intent.setDataAndType(uri,"application/pdf"); 
				    context.startActivity(intent); 
				    
				}catch(ActivityNotFoundException e) {
					Toast.makeText(context, "不支持打开pdf文件，请先安装pdf阅读app", Toast.LENGTH_LONG).show();
				}
			}
			
		});
	}
	
	@Override
	public void openPDF() {
		
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openPAY(String productID) {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void openBook(String productID) {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void openSignUp(String value) {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void openStockDetail(String value) {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void getUserID() {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void setShareValue(String text, String[] imageUrls, String logoUrl) {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void setShareValue(String text, String[] imageUrls, String logoUrl, String shareUrl) {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void setShareValue(String value) {

	}

	@android.webkit.JavascriptInterface
	@Override
	public void gotoStockDetail(String typeID, String stockID, String stockCode) {

	}

	@Override
	public void getUserStockList(Callback callback) {
		// TODO Auto-generated method stub
	}

	@Override
	public void gotoBlock(String callBackMethod, String blockCode,
			String marketId, String productType, String blockName) {
		// TODO Auto-generated method stub
		
	}

}
