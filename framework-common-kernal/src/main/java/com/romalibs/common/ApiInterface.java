package com.romalibs.common;

import java.util.Map;

import android.webkit.JavascriptInterface;

/**
 * 标准化接口API
 * @author wanlh 2016-04-07
 *
 */
public interface ApiInterface {
	
	/**
	 * 用于js调用原生接口的native
	 */
	public static final String JS_BRIDGE_NATIVE_NAME = "ROMA_Native";
	
	/**
	 * 提交map数据到
	 * @param interfaceMethodName
	 * @param map
	 */
	public void callBackToJs(String interfaceMethodName, Map<String, String> map);
	
	/**
	 * 设置WebView，可能实现类需要用到该对象,比如，需要给H5回传数据需要用到该对象
	 * @param webView
	 */
	//public void setWebView(WebView webView);
	
	/**
	 * 搜索证券,键盘精灵
	 * 投顾的js代码启动原生的键盘精灵接口
	 * @param backMethodName 回调函数名 用于给js回调传递数据的方法名称
	 * @param stockCodes 股票代码串 已添加的股票代码,可为空.eg:"000001,000002,000003"
	 */
	@ JavascriptInterface
	public void showKeyBoardTG(String backMethodName, String stockCodes);
	
	/**
	 * 登录状态判断接口
	 * 投顾的js代码获取交易登录状态接口
	 * @param backMethodName 回调函数名 用于给js回调传递数据的方法名称
	 */
	@ JavascriptInterface
	public void getLoginStateTG(String backMethodName);
	
	/**
	 * 个股详情页面
	 * 投顾的js代码启动原生代码的股票详情页面接口
	 * @param backMethodName 回调函数名 用于给js回调传递数据的方法名称
	 * @param stockCode 股票代码
	 * @param marketId  市场代码
	 * @param type 类型
	 */
	@ JavascriptInterface
	public void gotoStockDetailTG(String backMethodName, String stockCode, String marketId, String type);
	
	/**
	* 跳转到板块
	*/
	@ JavascriptInterface
	public void gotoBlock(String callBackMethod, String blockCode, String marketId, String productType, String blockName);

	/**
	 * 用户注册
	 * 投顾的js代码调用原生的注册界面
	 * @param backMethodName 回调函数名 用于给js回调传递数据的方法名称
	 */
	@ JavascriptInterface
	public void ShowRegisterView(String backMethodName);
	
	/**
	 * 实盘/模拟盘登录
	 * 投顾的js代码调用原生的登录界面
	 * @param backMethodName 回调函数名 用于给js回调传递数据的方法名称
	 */
	@ JavascriptInterface
	public void gotoTradeLoginViewTG(String backMethodName);
	
	/**
	 * 分享接口
	 * @param backMethodName 回调函数名 用于给js回调传递数据的方法名称
	 * @param title 分享内容标题
	 * @param url 分享内容摘要
	 * @param summary 分享内容的web url
	 */
	@ JavascriptInterface
	public void showShareTG(String backMethodName, String title,String url,String summary);
	
	/**
	 * 左上角返回接口
	 */
	@ JavascriptInterface
	public void closeCurrentWindow();
	
	/**
	 * 用于点击H5页面按钮时调用该方法创建新的窗体加载页面
	 * @param linkUrl 页面加载的url
	 */
	@ JavascriptInterface
	public void openNewWindow(String linkUrl);
	
	/**
	 * 用于H5展示短暂提示信息 Toast
	 * @param backMethodName
	 * @param message
	 */
	@ JavascriptInterface
	public void showToast(String backMethodName, String message);

	/**
	 * 调用原生的相册、拍照的接口
	 * @param backMethodName
	 */
	@ JavascriptInterface
	public void pickImage(String backMethodName);

	/**
	 * 用于点击H5页面图片后传递给原声的数据 message，
	 * 点击H5的图片包含三个事件：1、进入相册；2、进入个股详情；3、进入weburl
	 * @param backMethodName 用于回传数据的方法名称
	 * @param message
	 *   {
	 *     'index':"index",
	 *     'x':"x",'y':"y",
	 *     'width':"width",'height':"height",
	 *     'linkType': "imgType",
	 *     'href': "href"
     *   }
	 */
	@ JavascriptInterface
	public void imageDidClicked(String backMethodName, String message);
	
	/**
	 * 点击WebView上的文本链接，1、支持点击进入个股详情界面；2、支持点击进入web超链接
	 * @param backMethodName
	 * @param message
	 *   {
     *   'linkType': 'linkType',
     *   'linkUrl': 'linkUrl',
     *   'stockId': 'parentNodeId'
     *   }
	 */
	@ JavascriptInterface
	public void linkDidClicked(String backMethodName, String message);
	
	/**
	 * PDF阅读接口，会调用第三方pdf阅读器进行查看
	 * @param pdfPath
	 */
	@ JavascriptInterface
	public void pdfViewForApp(String pdfPath);
	
	/**
	 * 打开PDF
	 */
	@ JavascriptInterface
	public void openPDF();
	
	/**
	 * get接口， 获取自选股列表
	 */
	public void getUserStockList(ApiProvider.Callback callback);

	/**
	 * 调起支付
	 */
	@ JavascriptInterface
	public void openPAY(String productID);

	/**
	 * 调起订阅
	 */
	@ JavascriptInterface
	public void openBook(String productID);

	/**
	 * 调起报名
	 */
	@ JavascriptInterface
	public void openSignUp(String value);

	/**
	 * 调起股票详情
	 */
	@ JavascriptInterface
	public void openStockDetail(String value);

	/**
	 * 获取用户 ID
	 */
	@ JavascriptInterface
	public void getUserID();

	/**
	 * 获取分享内容
	 */
	@ JavascriptInterface
	public void setShareValue(String text, String[] imageUrls, String logoUrl);

	/**
	 * 获取分享内容
	 */
	@ JavascriptInterface
	public void setShareValue(String text, String[] imageUrls, String logoUrl, String shareUrl);

	/**
	 * 获取分享内容
	 */
	@ JavascriptInterface
	public void setShareValue(String value);

	/**
	 * 个股详情页面
	 * @param typeID 产品ID
	 * @param stockID 股票ID
	 * @param stockCode 股票代码
	 */
	@ JavascriptInterface
	public void gotoStockDetail(String typeID, String stockID, String stockCode);

}
