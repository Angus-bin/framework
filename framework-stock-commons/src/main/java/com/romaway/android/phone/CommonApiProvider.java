package com.romaway.android.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.view.View;

import com.romalibs.common.ApiProvider;
import com.romalibs.common.CommonEvent;
import com.romalibs.photo.PhotoViewPagerActivity;
import com.romalibs.utils.MD5;
import com.romalibs.utils.cache.CacheUtils;
import com.romalibs.utils.cache.FilesDownloadTask.OnDownloadListener;
import com.romalibs.utils.gson.GsonHelper;
import com.romaway.activity.basephone.PDFViewActivity;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.keyboardelf.UserStockSQLMgr;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.utils.StockCacheInfo;
import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.PermissionsUtils;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.dl.BookProductProtocol;
import com.romaway.common.protocol.dl.SignUpProtocol;
import com.romaway.common.protocol.service.DLServices;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.utils.HttpUtils2;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.json.JSONUtils;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.commons.utils.NetUtils;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import roma.romaway.commons.android.tougu.PhotoChooser;
import roma.romaway.commons.android.tougu.TouguShowH5ActivityNew;
import roma.romaway.commons.android.tougu.TouguShowH5ActivityNew2;

//import com.kdslibs.photo.PhotoViewPagerActivity;

/**
 * 
 * @author wanlh
 *
 */
@Keep
public class CommonApiProvider extends ApiProvider {

	private final String TAG = "RomaCommonApi.java";
	
	public CommonApiProvider(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@android.webkit.JavascriptInterface
	@Override
	public void showKeyBoardTG(String backMethodName, String stockCodes) {
		// TODO Auto-generated method stub
		registerBackMethodName("showKeyBoardTG", backMethodName);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void getLoginStateTG(String backMethodName) {
		// TODO Auto-generated method stub
		registerBackMethodName("getLoginStateTG", backMethodName);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void gotoStockDetailTG(String stockName, String stockCode, String marketId, String type) {
		Logger.i(TAG, "[js调用原生接口] 启动个股详情界面 gotoStockDetailTG");
		
		//registerBackMethodName("gotoStockDetailTG", backMethodName);
		
		//跳转到行情分时K线界面
		String[][] hqData = new String[][]{{stockName,stockCode, marketId, type}};
		Bundle bundle = new Bundle();
		bundle.putInt("HQ_POSITION", 0);
		
		StockCacheInfo.clearCacheList();
		StockCacheInfo.saveListToCache(hqData, new int[]{0,1,2,3});
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
		ComponentName componentName = new ComponentName(context.getPackageName(), "roma.romaway.android.phone.activity.hq.HQStockDataInfoFragmentActivity");
		intent.setComponent(componentName);
		intent.putExtras(bundle);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void gotoBlock(String callBackMethod, String blockId, String marketId, String productType, String blockName) {
		super.gotoBlock(callBackMethod, blockId, marketId, productType, blockName);
		Bundle bundle = new Bundle();
		bundle.putInt("ReqDataType", 1);
		bundle.putString(BundleKeyValue.HQ_STOCKNAME, blockName);
		bundle.putString(BundleKeyValue.HQ_BK_CODE, blockId);
		bundle.putInt(BundleKeyValue.HQ_BK_MARKETID, Integer.parseInt(marketId));
		bundle.putInt(BundleKeyValue.HQ_BK_TYPE, Integer.parseInt(productType));
		
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
		ComponentName componentName = new ComponentName(context.getPackageName(),"roma.romaway.android.phone.activity.hq.HqShiChangActivityNew");
		intent.setComponent(componentName);
		intent.putExtras(bundle);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void ShowRegisterView(String backMethodName) {
		// TODO Auto-generated method stub
		registerBackMethodName("ShowRegisterView", backMethodName);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void gotoTradeLoginViewTG(String backMethodName) {
		// TODO Auto-generated method stub
		registerBackMethodName("gotoTradeLoginViewTG", backMethodName);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void showShareTG(String backMethodName, String title, String url, String summary) {
		registerBackMethodName("showShareTG", backMethodName);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void closeCurrentWindow() {
		// TODO Auto-generated method stub
		
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openNewWindow(String linkUrl) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(linkUrl)) {
			return;
		}
		Bundle bundle = new Bundle();
		bundle.putString("key_h5url", linkUrl);
		bundle.putInt("ket_right_img_visibility", View.VISIBLE);
		Logger.d("key_h5url", "key_h5url = " + linkUrl);
		bundle.putString("shareUrl", linkUrl + "&share=1");
		bundle.putString("shareTitle", "webTitle");
		bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
		KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void showToast(String backMethodName, String message) {
		// TODO Auto-generated method stub
		registerBackMethodName("showToast", backMethodName);
		if (mActivity != null) {
			RomaToast.showMessage(mActivity, message);
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void pickImage(String backMethodName) {
		// TODO Auto-generated method stub
		registerBackMethodName("pickImage", backMethodName);
	}

	/**
	 * 用于点击H5页面图片后传递给原声的数据 message，
	 * 点击H5的图片包含三个事件：1、进入相册；2、进入个股详情；3、进入weburl
	 * @param backMethodName 用于回传数据的方法名称
	 * @param message
	 *   {
	 *     'index':index,
	 *     'x':x,'y':y,
	 *     'width':width,'height':height,
	 *     'linkType': imgType,
	 *     'href': href
     *   }
	 */
	@android.webkit.JavascriptInterface
	@SuppressLint("NewApi")
	@Override
	public void imageDidClicked(final String backMethodName, final String message){
		Logger.d("ROMA_Native", "backMethodName = " + backMethodName + "     message = " + message);
		registerBackMethodName("imageDidClicked", backMethodName);
		//((Activity)context).runOnUiThread(action);
		
		new Handler().post(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (StringUtils.isEmpty(backMethodName)) {
					Map<String, String> map = GsonHelper.dataMapFromJson(message);
					final String index = map.get("index");
					final String x = map.get("x");
					final String y = map.get("y");
					final String width = map.get("width");
					final String height = map.get("height");
					final String linkType = map.get("linkType");
					final String href = map.get("href");
					final String yRatio = map.get("yRatio");
					final String offsetRatio = map.get("offsetRatio");

					if (!TextUtils.isEmpty(linkType) && linkType.equals("toStockDetail")) {
						//进入个股详情类型
						gotoStockDetailTG("", "000001", "2", "1111");

					} else if (!TextUtils.isEmpty(linkType) && linkType.equals("toWebUrl")) {
						//进入web 链接类型
						if (!TextUtils.isEmpty(href) && href.contains("http")) {
							//启动新的页面
							Intent intent = new Intent();
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
							intent.putExtra("key_h5url", href);
							intent.putExtra("key_titleVisibility", View.VISIBLE);
							intent.setClass(context, TouguShowH5ActivityNew.class);
							context.startActivity(intent);
						}
					} else if (!TextUtils.isEmpty(linkType) && linkType.equals("toVideo")) {
						//发送视频播放事件
						CommonEvent.VideoPlayEvent videoPlayEvent = new CommonEvent.VideoPlayEvent();
						videoPlayEvent.attribute = message;
						EventBus.getDefault().post(videoPlayEvent);

					} else {
						//进入相册类型的
						ComponentName cn = new ComponentName(context.getPackageName(), "com.romalibs.photo.PhotoViewPagerActivity");
						Intent intent = new Intent();
						intent.setComponent(cn);
						//Intent intent = new Intent("com.kdslibs.photo.PhotoViewPagerActivity"/*context, PhotoViewPagerActivity.class*/);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						//intent.putExtra("images", imageJson);
						intent.putExtra("position", Integer.parseInt(index));
						intent.putExtra("locationX", Float.parseFloat(x));
						intent.putExtra("locationY", Float.parseFloat(y));
						intent.putExtra("width", Float.parseFloat(width));
						intent.putExtra("height", Float.parseFloat(height));

						intent.putExtra("yRatio", Float.parseFloat(yRatio));
						intent.putExtra("offsetRatio", Float.parseFloat(offsetRatio));

						//PhotoViewPagerActivity.class.overridePendingTransition(0, 0);
						context.startActivity(intent);
					}
				} else {
					try {
						BaseJSONObject jsonObject = new BaseJSONObject(message);
						String index = "";
						if (jsonObject.has("index")) {
							index = jsonObject.getString("index");
						}
						if (mActivity instanceof TouguShowH5ActivityNew) {
							((TouguShowH5ActivityNew) mActivity).setReload(false);
							//进入相册类型的
//							ComponentName cn = new ComponentName(context.getPackageName(), "com.romalibs.photo.PhotoViewPagerActivity");
//							Intent intent = new Intent();
//							intent.setComponent(cn);
//							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//							intent.putExtra("position", Integer.parseInt(index));
//							context.startActivity(intent);
							Bundle bundle = new Bundle();
							bundle.putInt("position", Integer.parseInt(index));
							KActivityMgr.switchWindow((ISubTabView) mActivity, PhotoViewPagerActivity.class, bundle, false);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@android.webkit.JavascriptInterface
	public void setImageUrl(String jsonMessage) {
		Logger.d("ROMA_Native", "setImageUrl_jsonMessage = " + jsonMessage);
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(jsonMessage);
			if (jsonObject.has("images")) {
				CommonEvent.ImgPhotoPreviewEvent imgPhotoPreviewEvent =
						new CommonEvent.ImgPhotoPreviewEvent();
				BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("images"));
				int count = jsonArray.length();
				for (int i = 0; i < count; i++) {
					BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
					if (jsonObject1.has("image_url")) {
						String imageUrl = jsonObject1.getString("image_url");
						if (imageUrl.contains("?")) {
							imgPhotoPreviewEvent.imagePathList.add(imageUrl + "&toPhoto_");
						} else {
							imgPhotoPreviewEvent.imagePathList.add(imageUrl + "?toPhoto_");
						}
					}
				}
				//发送图片浏览粘性事件
				EventBus.getDefault().postSticky(imgPhotoPreviewEvent);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@android.webkit.JavascriptInterface
	public void openVedio(String backMethodName, final String jsonMessage) {
		registerBackMethodName("openVedio", backMethodName);
		
		new Handler().post(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				Map<String, String> map = GsonHelper.dataMapFromJson(jsonMessage);
//				final String x = map.get("x");
//				final String y = map.get("y");
//				final String width = map.get("width");
//				final String height = map.get("height");
//				final String href = map.get("src");
//				final String yRatio = map.get("yRatio");
				
				//发送视频播放事件
				CommonEvent.VideoPlayEvent videoPlayEvent =
						new CommonEvent.VideoPlayEvent();
				videoPlayEvent.attribute = jsonMessage;
				EventBus.getDefault().post(videoPlayEvent);
			}
		});
	}
	
	/**
	 * 点击WebView上的文本链接。1、支持点击进入个股详情界面；2、支持点击进入web超链接
	 * @param backMethodName
	 * @param message
	 *   {
     *   'linkType': linkType,
     *   'linkUrl': linkUrl,
     *   'stockId': parentNodeId
     *   }
	 */
	@android.webkit.JavascriptInterface
	@Override
	public void linkDidClicked(final String backMethodName, final String message){
		Logger.d("ROMA_Native", "backMethodName = " + backMethodName + "    message = " + message);
		registerBackMethodName("linkDidClicked", backMethodName);
		
		new Handler().post(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "json:"+message, 500).show();
				if (StringUtils.isEmpty(backMethodName)) {
					Map<String, String> map = GsonHelper.dataMapFromJson(message);
					final String linkType = map.get("linkType");
					final String stockType = map.get("stockType");
					final String stockId = map.get("stockId");
					final String linkUrl = map.get("linkUrl");
					final String stockName = map.get("stockName");
					// "http://fja4imml5.lightyy.com/index.html#quote?code=601326&prod_name="
					if (!TextUtils.isEmpty(linkType) && linkType.equals("toStockDetail")) {
						String[] stockIds = stockId.split("_");
						if (stockIds.length > 1) {
							String stockCode = stockIds[0];
							String marketId = stockIds[1];
							//进入个股详情类型
//						gotoStockDetailTG(stockName, stockCode, marketId, stockType);
//							if (!StringUtils.isEmpty(marketId)) {
//								if ("1".equals(marketId)) {
//									stockCode = stockCode + ".SZ";
//								} else if ("2".equals(marketId)) {
//									stockCode = stockCode + ".SS";
//								}
//							}
//							Intent intent = new Intent();
//							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//							Bundle bundle = new Bundle();
//							bundle.putString("key_h5url", "http://fja4imml5.lightyy.com/index.html#quote?code=" + stockCode + "&prod_name="/* + stockName*/);
//							bundle.putInt("ket_right_img_visibility", View.GONE);
//							bundle.putInt("key_titleVisibility", View.VISIBLE);
//							intent.putExtras(bundle);
//							intent.setClass(context, TouguShowH5Activity.class);
//							context.startActivity(intent);
//							Logger.d("key_h5url", "key_h5url = " + "http://fja4imml5.lightyy.com/index.html#quote?code=" + stockCode + "&prod_name="/* + stockName*/);
							if (!StringUtils.isEmpty(marketId)) {
								if ("1".equals(marketId)) {
									stockCode = "sz" + stockCode;
								} else if ("2".equals(marketId)) {
									stockCode = "sh" + stockCode;
								}
							}
							Intent intent = new Intent();
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
							ComponentName componentName = new ComponentName(context.getPackageName(), "roma.romaway.homepage.android.phone.StockDetailActivityNewNew");
							intent.setComponent(componentName);
							Bundle bundle = new Bundle();
							bundle.putString("stockName", stockName);
							bundle.putString("stockCode", stockCode);
							bundle.putString("type", "2");
							intent.putExtras(bundle);
							context.startActivity(intent);
//							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNew", bundle, false);
						}

					} else if (!TextUtils.isEmpty(linkType) && linkType.equals("toWebUrl")) {
						//进入web 链接类型
						if (!TextUtils.isEmpty(linkUrl) && linkUrl.contains("http")) {
							//启动新的页面
							Intent intent = new Intent();
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
							intent.putExtra("key_h5url", linkUrl);
							intent.putExtra("key_titleVisibility", View.VISIBLE);
							intent.setClass(context, TouguShowH5ActivityNew.class);
							context.startActivity(intent);
						}
					}
				} else {
					try {
						BaseJSONObject jsonObject = new BaseJSONObject(message);
						String productID = "";
						String type = "";
						String module_id = "";
						String shareID = "";
						String stockCode = "";
						String stockName = "";
						String is_finish = "";
						String being_day = "";
						String increase = "";
						String title = "";
						String start_time = "";
						String end_time = "";
						if (jsonObject.has("pro_id")) {
							productID = jsonObject.getString("pro_id");
						}
						if (jsonObject.has("type")) {
							type = jsonObject.getString("type");
						}
						if (jsonObject.has("module_id")) {
							module_id = jsonObject.getString("module_id");
						}
						if (jsonObject.has("stock_code")) {
							stockCode = jsonObject.getString("stock_code");
						}
						if (jsonObject.has("stock_id")) {
							shareID = jsonObject.getString("stock_id");
						}
						if (jsonObject.has("stock_name")) {
							stockName = jsonObject.getString("stock_name");
						}
						if (jsonObject.has("is_finish")) {
							is_finish = jsonObject.getString("is_finish");
						}
						if (jsonObject.has("being_days")) {
							being_day = jsonObject.getString("being_days");
						}
						if (jsonObject.has("increase")) {
							increase = jsonObject.getString("increase");
						}
						if (jsonObject.has("title")) {
							title = jsonObject.getString("title");
						}
						if (jsonObject.has("start_time")) {
							start_time = jsonObject.getString("start_time");
						}
						if (jsonObject.has("end_time")) {
							end_time = jsonObject.getString("end_time");
						}
						if (mActivity instanceof TouguShowH5ActivityNew) {
							((TouguShowH5ActivityNew) mActivity).setReload(false);
							if (!StringUtils.isEmpty(type)) {
								if ("2".equals(type)) {
									if (!StringUtils.isEmpty(stockCode) && !StringUtils.isEmpty(shareID)) {
										Bundle bundle = new Bundle();
										bundle.putString("shareID", shareID);
										bundle.putString("stockCode", stockCode);
										bundle.putString("title", "VIP股票池");
//										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNew", bundle, false);
										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivity", bundle, false);
									}
								} else if ("3".equals(type)) {
									Bundle bundle = new Bundle();
									bundle.putString("shareID", shareID);
									bundle.putString("stockCode", stockCode);
									bundle.putString("type", "1");
									bundle.putString("title", "涨停点睛");
//									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNew", bundle, false);
									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivity", bundle, false);
								} else if ("4".equals(type)) {
									if (!StringUtils.isEmpty(productID)) {
										Bundle bundle = new Bundle();
										bundle.putString("productID", productID);
										bundle.putString("title", title);
										bundle.putString("beingDay", being_day);
										bundle.putString("increase", increase);
										bundle.putBoolean("finish", "1".equals(is_finish));
										bundle.putString("startAt", start_time);
										bundle.putString("endAt", end_time);
										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.WenjianOneActivity", bundle, false);
									}
								}
							} else if (!StringUtils.isEmpty(module_id)) {
								if ("2".equals(module_id)) {
									if (!StringUtils.isEmpty(stockCode) && !StringUtils.isEmpty(shareID)) {
										Bundle bundle = new Bundle();
										bundle.putString("shareID", shareID);
										bundle.putString("stockCode", stockCode);
										bundle.putString("title", "VIP股票池");
//										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNew", bundle, false);
										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivity", bundle, false);
									}
								} else if ("3".equals(module_id)) {
									Bundle bundle = new Bundle();
									bundle.putString("shareID", shareID);
									bundle.putString("stockCode", stockCode);
									bundle.putString("type", "1");
									bundle.putString("title", "涨停点睛");
//									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNew", bundle, false);
									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivity", bundle, false);
								} else if ("4".equals(module_id)) {
									if (!StringUtils.isEmpty(productID)) {
										Bundle bundle = new Bundle();
										bundle.putString("productID", productID);
										bundle.putString("title", title);
										bundle.putString("beingDay", being_day);
										bundle.putString("increase", increase);
										bundle.putBoolean("finish", "1".equals(is_finish));
										bundle.putString("startAt", start_time);
										bundle.putString("endAt", end_time);
										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.WenjianOneActivity", bundle, false);
									}
								}
							} else {
								if (!StringUtils.isEmpty(stockCode) && !StringUtils.isEmpty(stockName)) {
									Bundle bundle = new Bundle();
									bundle.putString("stockName", stockName);
									bundle.putString("stockCode", stockCode);
									bundle.putString("type", "2");
									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNewNew", bundle, false);
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openPDF() {
		// TODO Auto-generated method stub
		super.openPDF();
		Logger.d("ROMA_Native", "接口调用了");
		if(getBundle() == null)
			throw new NullPointerException("pdf下载URL路径数据 NULL 错误！");
		
		String pdfUrl = getBundle().getString("pdfUrl");
		final String savePath = FileSystem.getCacheRootDir(context,"zixun2_0/pdfCache")
				.getAbsolutePath() + File.separator +  MD5.getInstance().getMD5(pdfUrl);
		File saveFile = new File(savePath);
		
		if(saveFile.exists()) {// 如果存在就立马打开
			pdfViewForAppOther(savePath);
			
		}else {// 否则就先下载
			CacheUtils.toCacheFiles(
				"pdfCache", 
				pdfUrl, 
				saveFile, 
				new OnDownloadListener(){

					@Override
					public void onDownloadComplete(String tag) {
						// TODO Auto-generated method stub
						pdfViewForAppOther(savePath);// 下载完成就立马打开
					}

					@Override
					public void onDownloadError(String tag) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onDownloading(int progress) {
						// TODO Auto-generated method stub
						// 下载完成
						if(getHandler() != null){
							Message msg = Message.obtain();
							msg.arg1 = progress;
							msg.what = 0;
							getHandler().sendMessage(msg);
						}
					}
				});
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void getUserStockList(final Callback callback) {
		// TODO Auto-generated method stub
		super.getUserStockList(callback);
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String userstockCode = UserStockSQLMgr.queryStockCodesInvert1(context);
				if(callback != null)
					callback.callback(userstockCode);
			}
		}).start();
	}

	@android.webkit.JavascriptInterface
	public void pdfViewForAppOther(String pdfPath){
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, PDFViewActivity.class);
		intent.putExtra("PDFPath", pdfPath);
		context.startActivity(intent);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openPAY(String value) {
		super.openPAY(value);
		Logger.d("ROMA_Native", "调起支付");
		Logger.d("ROMA_Native", "openPAY = " + value);
//		RomaToast.showMessage(mActivity, "暂未对外开放，内部会员测试中");
		String productID = "";
		String type = "";
		String module_id = "";
		String content = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("pro_id")) {
				productID = jsonObject.getString("pro_id");
			}
			if (jsonObject.has("module_id")) {
				module_id = jsonObject.getString("module_id");
			}
			if (jsonObject.has("type")) {
				type = jsonObject.getString("type");
			}
			if (jsonObject.has("content")) {
				content = jsonObject.getString("content");
				RomaSysConfig.setGuideContent(content);
			}
			if (!StringUtils.isEmpty(type)) {
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.WenjianListAvtivityNew", null, false);
				return;
			}
			boolean isTime = false;
			String lessDays = "";
//			if (mActivity instanceof TouguShowH5ActivityNew) {
//				isTime = ((TouguShowH5ActivityNew) mActivity).isTime();
//				lessDays = ((TouguShowH5ActivityNew) mActivity).getLessDays();
//			}
			isTime = RomaSysConfig.getTime();
			lessDays = RomaSysConfig.getLessDays();
			Logger.d("ROMA_Native", "isTime = " + isTime);
			Logger.d("ROMA_Native", "lessDays = " + lessDays);
			Logger.d("ROMA_Native", "productID = " + productID);
			if (!StringUtils.isEmpty(RomaUserAccount.getQuestionUserType())) {
				if (isTime) {
					final String finalProductID = productID;
					final String finalContent = content;
					Dialog dialog = DialogFactory.getIconDialog(mActivity,
							"订阅须知", "本期运行时间还剩" + lessDays + "天，是否继续订阅", DialogFactory.DIALOG_TYPE_NO_ICON,
							Res.getString(R.string.tougu_dialog_cancel), new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
								}
							}, "前往", new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									if (!StringUtils.isEmpty(RomaSysConfig.getWechatCode())) {
										Bundle bundle = new Bundle();
										bundle.putString("module_id", "4");
										bundle.putString("pro_id", finalProductID);
										bundle.putString("content", finalContent);
										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.guide.HomePageGuideActivityNew", bundle, false);
									}
//									Bundle bundle = new Bundle();
//									bundle.putString("productID", finalProductID);
//									SharedPreferenceUtils.setPreference("productID", "productID", finalProductID);
//									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.HomePageWeiXinPayActivityNewNew", bundle, true);
								}
							});
					dialog.show();
				} else {
					Dialog dialog = DialogFactory.getIconDialog(mActivity,
							"订阅须知", "本期已过订阅时间，请前往订阅其他组合", DialogFactory.DIALOG_TYPE_NO_ICON,
							Res.getString(R.string.tougu_dialog_cancel), new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
								}
							}, "前往", new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.WenjianListAvtivityNew", null, true);
								}
							});
					dialog.show();
				}
			} else {
				final boolean finalIsTime = isTime;
				final String finalLessDays = lessDays;
				Dialog dialog = DialogFactory.getIconDialog(mActivity,
						"风险测评", "您还未进行风险测评，是否前往进行测评", DialogFactory.DIALOG_TYPE_NO_ICON,
						Res.getString(R.string.tougu_dialog_cancel), new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {
							}
						}, "前往", new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {
								String ip = "";
								if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
									ip = RomaSysConfig.getIp();
								} else {
									ip = Res.getString(R.string.NetWork_IP);
								}
								Bundle bundle = new Bundle();
								bundle.putString("key_h5url", ip + "question/index?site_id=" + 48 + "&user_id=" + RomaUserAccount.getUserID());
								bundle.putInt("ket_right_img_visibility", View.VISIBLE);
								bundle.putString("shareUrl", ip + "question/index?site_id=" + 48 + "&user_id=" + RomaUserAccount.getUserID() + "&share=1");
								bundle.putString("shareTitle", "webTitle");
								bundle.putString("shareDescription", "完成任务获取权限");
								Logger.d("key_h5url", "key_h5url = " + ip + "question/index?site_id=" + 48 + "&user_id=" + RomaUserAccount.getUserID());
								bundle.putBoolean("time", finalIsTime);
								bundle.putString("lessDays", finalLessDays);
								bundle.putBoolean("isScroll", false);
								KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, true);
							}
						});
				dialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openBook(String value) {
		Logger.d("ROMA_Native", "调起订阅");
		Logger.d("ROMA_Native", "openBook = " + value);
		String userID = "";
		String productID = "";
		String type = "";
		String module_id = "";
		String content = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("user_id")) {
				userID = jsonObject.getString("user_id");
			}
			if (jsonObject.has("pro_id")) {
				productID = jsonObject.getString("pro_id");
			}
			if (jsonObject.has("type")) {
				type = jsonObject.getString("type");
			}
			if (jsonObject.has("module_id")) {
				module_id = jsonObject.getString("module_id");
			}
			if (jsonObject.has("content")) {
				content = jsonObject.getString("content");
				RomaSysConfig.setGuideContent(content);
			}
			if (RomaUserAccount.isGuest()) {
				Bundle bundle = new Bundle();
				bundle.putString("h5_key", "openBook");
				bundle.putString("h5_value", jsonObject.toString());
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", bundle, false);
				return;
			}
			if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
				Bundle bundle = new Bundle();
				bundle.putString("h5_key", "openBook");
				bundle.putString("h5_value", jsonObject.toString());
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", bundle, false);
				return;
			}
			if (!StringUtils.isEmpty(RomaSysConfig.getWechatCode())) {
				Bundle bundle = new Bundle();
				bundle.putString("module_id", module_id);
				bundle.putString("pro_id", productID);
				bundle.putString("content", content);
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.guide.HomePageGuideActivityNew", bundle, false);
			}
//			DLServices.reqBookProduct(userID, productID, type, new Listener(mActivity), "book_product");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openSignUp(String value) {
		super.openSignUp(value);
		Logger.d("ROMA_Native", "openSignUp = " + value);
		String userID = "";
		String name = "";
		String productID = "";
		String type = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("user_id")) {
				userID = jsonObject.getString("user_id");
			}
			if (jsonObject.has("name")) {
				name = jsonObject.getString("name");
			}
			if (jsonObject.has("pro_id")) {
				productID = jsonObject.getString("pro_id");
			}
			if (jsonObject.has("type")) {
				type = jsonObject.getString("type");
			}
			if (RomaUserAccount.isGuest()) {
				Bundle bundle = new Bundle();
				bundle.putString("h5_key", "openSignUp");
				bundle.putString("h5_value", jsonObject.toString());
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", bundle, false);
				return;
			}
			if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
				Bundle bundle = new Bundle();
				bundle.putString("h5_key", "openSignUp");
				bundle.putString("h5_value", jsonObject.toString());
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", bundle, false);
				return;
			}
			DLServices.reqSignUp(userID, name, productID, type, new Listener(mActivity), "sign_up");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class Listener extends UINetReceiveListener {

		/**
		 * @param activity
		 */
		public Listener(Activity activity) {
			super(activity);
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			super.onSuccess(msg, ptl);
			if (ptl instanceof SignUpProtocol) {
				SignUpProtocol protocol = (SignUpProtocol) ptl;
				Logger.d("SignUpProtocolCoder", "is_success = " + protocol.resp_is_success);
				if ("1".equals(protocol.resp_is_success)) {
					webView.reload();
				}
			} else if (ptl instanceof BookProductProtocol) {
				BookProductProtocol protocol = (BookProductProtocol) ptl;
				Logger.d("BookProductProtocolCoder", "is_success = " + protocol.resp_is_success);
				webView.reload();
//				if ("1".equals(protocol.resp_is_success)) {
//				}
			}
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void openStockDetail(String value) {
		super.openStockDetail(value);
		Logger.d("ROMA_Native", "调起跳转股票详情---value = " + value);
	}

	@android.webkit.JavascriptInterface
	@Override
	public void getUserID() {
		super.getUserID();
		Logger.d("ROMA_Native", "获取用户 ID");
		BaseJSONObject jsonObject = new BaseJSONObject();
		jsonObject.put("user_id", RomaUserAccount.getUserID());

		final BaseJSONObject json;
		try {
			json = new BaseJSONObject(jsonObject.toString());
			webView.post(new Runnable() {
				@Override
				public void run() {
					webView.loadUrl("javascript:getUserInfo('" + json + "')");
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void setShareValue(String text, String[] imageUrls, String logoUrl) {
		super.setShareValue(text, imageUrls, logoUrl);
		Logger.d("ROMA_Native", "text = " + text);
		Logger.d("ROMA_Native", "logoUrl = " + logoUrl);
		if (imageUrls != null && imageUrls.length > 0) {
			for (int i = 0; i < imageUrls.length; i++) {
				Logger.d("ROMA_Native", "imageUrls = " + imageUrls[i]);
			}
		}
		BaseJSONObject jsonObject = new BaseJSONObject();
		jsonObject.put("text", text);
		jsonObject.put("logo_url", logoUrl);
		if (imageUrls != null && imageUrls.length > 0) {
			jsonObject.put("image_url", imageUrls[0]);
		}
		if (mActivity instanceof TouguShowH5ActivityNew) {
			((TouguShowH5ActivityNew) mActivity).setValue(jsonObject.toString());
		}

	}

	@android.webkit.JavascriptInterface
	@Override
	public void setShareValue(String text, String[] imageUrls, String logoUrl, String shareUrl) {
		super.setShareValue(text, imageUrls, logoUrl, shareUrl);
		Logger.d("ROMA_Native", "text = " + text);
		Logger.d("ROMA_Native", "logoUrl = " + logoUrl);
		if (imageUrls != null && imageUrls.length > 0) {
			for (int i = 0; i < imageUrls.length; i++) {
				Logger.d("ROMA_Native", "imageUrls = " + imageUrls[i]);
			}
		}
		BaseJSONObject jsonObject = new BaseJSONObject();
		jsonObject.put("text", text);
		jsonObject.put("logo_url", logoUrl);
		jsonObject.put("share_url", shareUrl);
		if (imageUrls != null && imageUrls.length > 0) {
			jsonObject.put("image_url", imageUrls[0]);
		}
		if (mActivity instanceof TouguShowH5ActivityNew) {
			((TouguShowH5ActivityNew) mActivity).setValue(jsonObject.toString());
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void setShareValue(String value) {
		super.setShareValue(value);
		Logger.d("ROMA_Native", "setShareValue = " + value);
		String text = "";
		String logoUrl = "";
		String shareUrl = "";
		String imageUrls = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("shareText")) {
				text = jsonObject.getString("shareText");
			}
			if (jsonObject.has("shareLogo")) {
				logoUrl = jsonObject.getString("shareLogo");
			}
			if (jsonObject.has("shareUrl")) {
				shareUrl = jsonObject.getString("shareUrl");
				if (!StringUtils.isEmpty(shareUrl)) {
					if (shareUrl.contains("?")) {
						shareUrl = shareUrl +"&user_id=" + RomaUserAccount.getUserID();
					} else {
						shareUrl = shareUrl +"?user_id=" + RomaUserAccount.getUserID();
					}
				}
			}
			if (jsonObject.has("shareImg")) {
				imageUrls = jsonObject.getString("shareImg");
			}
			BaseJSONObject jsonObject1 = new BaseJSONObject();
			jsonObject1.put("text", text);
			jsonObject1.put("logo_url", logoUrl);
			jsonObject1.put("share_url", shareUrl);
			jsonObject1.put("image_url", imageUrls);
			if (mActivity instanceof TouguShowH5ActivityNew) {
				((TouguShowH5ActivityNew) mActivity).setValue(jsonObject1.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void gotoStockDetail(String typeID, String stockID, String stockCode) {
		super.gotoStockDetail(typeID, stockID, stockCode);
		Logger.d("ROMA_Native", "typeID = " + typeID + "   stockID = " + stockID + "   stockCode = " + stockCode);
		Bundle bundle = new Bundle();
		bundle.putString("type", typeID);
		bundle.putString("shareID", stockID);
		bundle.putString("stockCode", stockCode);
		if (mActivity != null) {
			KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNew", bundle, false);
		}
	}

	@android.webkit.JavascriptInterface
	public void gotoStockDetail(String value) {
		Logger.d("ROMA_Native", "gotoStockDetail = " + value);
		String typeID = "";
		String stockID = "";
		String stockCode = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("typeID")) {
				typeID = jsonObject.getString("typeID");
			}
			if (jsonObject.has("stockID")) {
				stockID = jsonObject.getString("stockID");
			}
			if (jsonObject.has("stockCode")) {
				stockCode = jsonObject.getString("stockCode");
			}
			Bundle bundle = new Bundle();
			bundle.putString("type", typeID);
			bundle.putString("shareID", stockID);
			bundle.putString("stockCode", stockCode);
			if (mActivity != null) {
				KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.StockDetailActivityNew", bundle, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@android.webkit.JavascriptInterface
	public void setVideoValue(String value) {
		Logger.d("ROMA_Native", "setVideoValue = " + value);
		String videoID = "";
		String title = "";
		String videoValue = "";
		String shareUrl = "";
		String content = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("title")) {
				title = jsonObject.getString("title");
			}
			if (jsonObject.has("url")) {
				videoValue = jsonObject.getString("url");
			}
			if (jsonObject.has("id")) {
				videoID = jsonObject.getString("id");
			}
			if (jsonObject.has("link")) {
				shareUrl = jsonObject.getString("link");
			}
			if (jsonObject.has("content")) {
				content = jsonObject.getString("content");
			}
			if (!StringUtils.isEmpty(videoValue) && !"undefined".equals(videoValue)) {
				Bundle bundle = new Bundle();
				bundle.putString("videoID", videoID);
				bundle.putString("title", title);
				bundle.putString("videoValue", videoValue);
				bundle.putString("shareUrl", shareUrl);
				bundle.putString("content", content);
				if (mActivity != null) {
					KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew2.class, bundle, false);
				}
			} else {
				if (mActivity != null) {
					RomaToast.showMessage(mActivity, "视频资源错误，请稍后刷新重试");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置测评结果
	 * @param type 测评结果类型
     */
	@android.webkit.JavascriptInterface
	public void setTestType(String type) {
		Logger.d("ROMA_Native", "type = " + type);
		if (!StringUtils.isEmpty(type)) {
			RomaUserAccount.setQuestionUserType(type);
			boolean isTime = false;
			String lessDays = "";
			String productID = "";
			isTime = RomaSysConfig.getTime();
			lessDays = RomaSysConfig.getLessDays();
			productID = RomaSysConfig.getProductID();
			Logger.d("ROMA_Native", "isTime = " + isTime);
			Logger.d("ROMA_Native", "lessDays = " + lessDays);
			Logger.d("ROMA_Native", "productID = " + productID);
			if (isTime) {
				if (!StringUtils.isEmpty(lessDays)) {
					final String finalProductID = productID;
					Dialog dialog = DialogFactory.getIconDialog(mActivity,
							"订阅须知", "本期运行时间还剩" + lessDays + "天，是否继续订阅", DialogFactory.DIALOG_TYPE_NO_ICON,
							Res.getString(R.string.tougu_dialog_cancel), new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
								}
							}, "前往", new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									if (!StringUtils.isEmpty(RomaSysConfig.getWechatCode())) {
										Bundle bundle = new Bundle();
										bundle.putString("module_id", "4");
										bundle.putString("pro_id", finalProductID);
										KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.guide.HomePageGuideActivityNew", bundle, false);
									}
//									Bundle bundle = new Bundle();
//									bundle.putString("productID", finalProductID);
//									SharedPreferenceUtils.setPreference("productID", "productID", finalProductID);
//									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.HomePageWeiXinPayActivityNewNew", bundle, true);
								}
							});
					dialog.show();
				}
			} else {
				if (!StringUtils.isEmpty(lessDays)) {
					Dialog dialog = DialogFactory.getIconDialog(mActivity,
							"订阅须知", "本期已过订阅时间，请前往订阅其他组合", DialogFactory.DIALOG_TYPE_NO_ICON,
							Res.getString(R.string.tougu_dialog_cancel), new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
								}
							}, "前往", new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.WenjianListAvtivityNew", null, true);
								}
							});
					dialog.show();
				}
			}
		}
	}

	@android.webkit.JavascriptInterface
	public void goBack() {
		Logger.d("ROMA_Native", "goBack--------------");
		if (mActivity instanceof TouguShowH5ActivityNew) {
			Logger.d("ROMA_Native", "goBack1--------------");
			int handleBackType = ((TouguShowH5ActivityNew) mActivity).getBackType();
			Logger.d("ROMA_Native", "handleBackType = " + handleBackType);
			switch (handleBackType) {
				case 1:				// 直接finish;
					((TouguShowH5ActivityNew) mActivity).finish();
					break;
				case 2: 			// 关闭不销毁;
					((TouguShowH5ActivityNew) mActivity).findViewById(R.id.svg_back).setEnabled(false); // 避免重复点击，在返回后的界面重新设为可点击
					if(webView != null && webView.canGoBack()){
						webView.goBack();
						break;
					}
					((TouguShowH5ActivityNew) mActivity).moveTaskToBack(true);
					// 将另一组进程中Activity Task队列移至前台:
					((TouguShowH5ActivityNew) mActivity).moveTaskToFront();
					break;
				case 0:
				default:			// 逐级关闭, 最后finish;
					final boolean[] canBack = {false};
					((TouguShowH5ActivityNew) mActivity).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							canBack[0] = webView.canGoBack();
						}
					});
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(webView != null && canBack[0]){
						webView.goBack();
						break;
					}
					((TouguShowH5ActivityNew) mActivity).finish();
			}
		}
	}

	/**
	 * 跳转产品介绍页
	 * @param value
     */
	@android.webkit.JavascriptInterface
	public void switchProductUrl(String value) {
		Logger.d("ROMA_Native", "value = " + value);
		String time_type = "";
		String days = "";
		String less_time = "";
		String urls = "";
		String productID = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("time_type")) {
				time_type = jsonObject.getString("time_type");
			}
			if (jsonObject.has("days")) {
				days = jsonObject.getString("days");
			}
			if (jsonObject.has("less_time")) {
				less_time = jsonObject.getString("less_time");
			}
			if (jsonObject.has("url")) {
				urls = jsonObject.getString("url");
			}
			if (jsonObject.has("productID")) {
				productID = jsonObject.getString("productID");
			}
			if (!StringUtils.isEmpty(time_type) && !StringUtils.isEmpty(days) && !StringUtils.isEmpty(less_time)) {
				if ("0".equals(time_type)) {
					SharedPreferenceUtils.setPreference("time_type", "time_type", time_type);
					Bundle bundle = new Bundle();
					bundle.putString("key_h5url", urls);
					bundle.putInt("ket_right_img_visibility", View.VISIBLE);
					Logger.d("key_h5url", "key_h5url = " + urls);
					bundle.putString("shareUrl", urls + "&share=1");
					bundle.putString("shareTitle", "webTitle");
					bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
					bundle.putInt("type", 1);
					bundle.putString("productID", productID);
					bundle.putBoolean("time", (StringUtils.compare(less_time, days) >= 0));
					bundle.putString("lessDays", less_time);
					if (mActivity != null) {
						KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
					}
					return;
				}
				String day2 = (NumberUtils.toInt(days) / 2) + "";
				Bundle bundle = new Bundle();
				bundle.putString("key_h5url", urls);
				bundle.putInt("ket_right_img_visibility", View.VISIBLE);
				Logger.d("key_h5url", "key_h5url = " + urls);
				bundle.putString("shareUrl", urls + "&share=1");
				bundle.putString("shareTitle", "webTitle");
				bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
				bundle.putInt("type", 1);
				bundle.putString("productID", productID);
				bundle.putBoolean("time", (StringUtils.compare(less_time, day2) >= 0));
				bundle.putString("lessDays", less_time);
				if (mActivity != null) {
					KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
				}
			} else {
				if (mActivity != null) {
					RomaToast.showMessage(mActivity, "数据错误，请稍后刷新重试");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跳转产品介绍页
	 * @param value
	 */
	@android.webkit.JavascriptInterface
	public void switchProductValue(String value) {
		Logger.d("ROMA_Native", "value = " + value);
		String time_type = "";
		String days = "";
		String less_time = "";
		String urls = "";
		String productID = "";
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(value);
			if (jsonObject.has("time_type")) {
				time_type = jsonObject.getString("time_type");
				SharedPreferenceUtils.setPreference("time_type", "time_type", time_type);
			}
			if (jsonObject.has("days")) {
				days = jsonObject.getString("days");
			}
			if (jsonObject.has("less_time")) {
				less_time = jsonObject.getString("less_time");
			}
			if (jsonObject.has("url")) {
				urls = jsonObject.getString("url");
			}
			if (jsonObject.has("productID")) {
				productID = jsonObject.getString("productID");
			}
			if (!StringUtils.isEmpty(time_type) && !StringUtils.isEmpty(days) && !StringUtils.isEmpty(less_time)) {
				if ("0".equals(time_type)) {
					SharedPreferenceUtils.setPreference("time_type", "time_type", time_type);
					RomaSysConfig.setProductID(productID);
					RomaSysConfig.setTime((StringUtils.compare(less_time, days) >= 0));
					RomaSysConfig.setLessDays(less_time);
					return;
				}
				String day2 = (NumberUtils.toInt(days) / 2) + "";
				RomaSysConfig.setProductID(productID);
				RomaSysConfig.setTime((StringUtils.compare(less_time, day2) >= 0));
				RomaSysConfig.setLessDays(less_time);
			} else {
				if (mActivity != null) {
					RomaToast.showMessage(mActivity, "数据错误，请稍后刷新重试");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置网页是否执行 onResume onPause
	 * @param value 0: 不执行，1: 执行
	 */
	@android.webkit.JavascriptInterface
	public void setRefresh(String value) {
		if (!StringUtils.isEmpty(value)) {
			if ("0".equals(value)) {
				if (mActivity instanceof TouguShowH5ActivityNew) {
					((TouguShowH5ActivityNew) mActivity).setReload(false);
				}
			} else if ("1".equals(value)) {
				if (mActivity instanceof TouguShowH5ActivityNew) {
					((TouguShowH5ActivityNew) mActivity).setReload(true);
				}
			}
		}
	}

	/**
	 * 开启分享
	 */
	@android.webkit.JavascriptInterface
	public void openShare() {
		Logger.d("ROMA_Native", "调用分享");
		if (RomaUserAccount.isGuest()) {
			KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", null, false);
			return;
		}
		if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
			KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", null, false);
			return;
		}
		if (mActivity instanceof TouguShowH5ActivityNew) {
			((TouguShowH5ActivityNew) mActivity).openHtmlShare("0");
		}
	}

	/**
	 * 开启指定分享
	 */
	@android.webkit.JavascriptInterface
	public void openShare(String shareType) {
		Logger.d("ROMA_Native", "调用指定分享---shareType = " + shareType);
		if (RomaUserAccount.isGuest()) {
			KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", null, false);
			return;
		}
		if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
			KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", null, false);
			return;
		}
		if (mActivity instanceof TouguShowH5ActivityNew) {
			((TouguShowH5ActivityNew) mActivity).openHtmlShare(shareType);
		}
	}

	/**
	 * 开启悬浮窗
	 */
	@android.webkit.JavascriptInterface
	public void showFloatWindow(String value) {
		Logger.d("ROMA_Native", "value = " + value);
		String module_id = "";
		String productID = "";
		String is_show = "0";
		String content = "";
		if (!StringUtils.isEmpty(value)) {
			try {
				BaseJSONObject jsonObject = new BaseJSONObject(value);
				if (jsonObject.has("is_show")) {
					is_show = jsonObject.getString("is_show");
				}
				if (jsonObject.has("module_id")) {
					module_id = jsonObject.getString("module_id");
				}
				if (jsonObject.has("pro_id")) {
					productID = jsonObject.getString("pro_id");
				}
				if (jsonObject.has("content")) {
					content = jsonObject.getString("content");
				}
				if (StringUtils.isEmpty(module_id)) {
					if (mActivity != null) {
						RomaToast.showMessage(mActivity, "数据有误，请稍后再试");
					}
					return;
				}
				if (mActivity instanceof TouguShowH5ActivityNew) {
					((TouguShowH5ActivityNew) mActivity).setModule_id(module_id);
					((TouguShowH5ActivityNew) mActivity).setPro_id(productID);
					((TouguShowH5ActivityNew) mActivity).setGuide_content(content);
					((TouguShowH5ActivityNew) mActivity).setShowFloatWindow("1".equals(is_show));
				}
				if ("4".equals(module_id)) {
					if (jsonObject.has("pro_id")) {
						productID = jsonObject.getString("pro_id");
					}
					if (!StringUtils.isEmpty(productID)) {
						if (mActivity instanceof TouguShowH5ActivityNew) {
							((TouguShowH5ActivityNew) mActivity).setPro_id(productID);
						}
					}
				}
//				if (mActivity instanceof TouguShowH5ActivityNew) {
//					((TouguShowH5ActivityNew) mActivity).showFloatWindow();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置导航栏颜色
	 * @param ActionBarColor 导航栏背景色
	 * @param BackColor 返回键背景色
	 * @param ShareColor 分享按钮背景色
     */
	@android.webkit.JavascriptInterface
	public void setActionBarBg(int ActionBarColor, int BackColor, int ShareColor) {
		Logger.d("ROMA_Native", "ActionBarColor = " + ActionBarColor);
		Logger.d("ROMA_Native", "BackColor = " + BackColor);
		Logger.d("ROMA_Native", "ShareColor = " + ShareColor);
		if (mActivity instanceof TouguShowH5ActivityNew) {
			((TouguShowH5ActivityNew) mActivity).setTitleBg(ActionBarColor);
			((TouguShowH5ActivityNew) mActivity).setBackBg(BackColor);
			((TouguShowH5ActivityNew) mActivity).setShareBg(ShareColor);
		}
	}

	/**
	 * 调用相册/相机
	 */
	@android.webkit.JavascriptInterface
	public void openCamera(String backMethodName) {
		Logger.i(TAG, "[js调用原生接口]  openCamera" + backMethodName);
		setRefresh("0");

		registerBackMethodName("openCamera", backMethodName);
		PhotoChooser mPhotoChooser = new PhotoChooser(mActivity);
		mPhotoChooser.showPhotoChooser(mActivity.findViewById(R.id.root));
	}

	/**
	 * 提交给js数据
	 * @param backMethodName
	 * @param map
	 */
	@android.webkit.JavascriptInterface
	public void commit(final String backMethodName, Map<String, String> map){

		String json = JSONUtils.toJson(backMethodName, map);
		Logger.d(TAG, "[js调用原生接口] to json data，jsonBuilder : " + json);
		//在没网络的情况下是不会传递数据给js
		if(!NetUtils.isNetworkConnected(mActivity))
			return;

		if(webView != null && !StringUtils.isEmpty(backMethodName)){
			try{
				final BaseJSONObject jsonObject = new BaseJSONObject();
				String value = "";
				for (String key : map.keySet()) {
					value = map.get(key);
					jsonObject.put(key, value);
				}
				final BaseJSONObject jsonObject1;
				try {
					jsonObject1 = new BaseJSONObject(jsonObject.toString());
					final String finalValue = value;
					webView.post(new Runnable() {
						@Override
						public void run() {
							String url = "javascript:" + backMethodName + "(" + jsonObject1 + ")";
							Logger.d(TAG, "[js调用原生接口] to json data，url : " + url);
							webView.loadUrl(url);
//							String ip = "http://api.guxiansen.test1.romawaysz.com/";
//							if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
//								ip = RomaSysConfig.getIp();
//							} else {
//								ip = Res.getString(R.string.NetWork_IP);
//							}
////							//参数
//							final Map<String,String> params = new HashMap<String,String>();
//							params.put("img", finalValue);
//							final String finalIp = ip;
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//									String result = HttpUtils2.submitPostData(finalIp + "/external/imgToStock/", params, "utf-8");
//									Logger.d("[App 请求接口]", "result = " + result);
//								}
//							}).start();
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}catch(Exception e){
				e.printStackTrace();
				Logger.d(TAG, "[js调用原生接口] 异常！");
			}
		}
	}

	/**
	 * 提交给js数据
	 * @param backMethodName
	 * @param map
	 */
	@android.webkit.JavascriptInterface
	public void commitStrData(final String backMethodName, Map<String, String> map){
		String json = JSONUtils.toJson(backMethodName, map);
		Logger.d(TAG, "[js调用原生接口] to json data，jsonBuilder : " + json);
		//在没网络的情况下是不会传递数据给js
		if(!NetUtils.isNetworkConnected(mActivity))
			return;

		if(webView != null && !StringUtils.isEmpty(backMethodName)){
			try{
				final BaseJSONObject jsonObject = new BaseJSONObject();
				String value = "";
				for (String key : map.keySet()) {
					value = map.get(key);
					jsonObject.put(key, value);
				}
				final BaseJSONObject jsonObject1;
				try {
					jsonObject1 = new BaseJSONObject(jsonObject.toString());
					final String finalValue = value;
					webView.post(new Runnable() {
						@Override
						public void run() {
							String url = "javascript:" + backMethodName + "('" + finalValue + "')";
							Logger.d(TAG, "[js调用原生接口] to json data，url : " + url);
							webView.loadUrl(url);
//							String ip = "http://api.guxiansen.test1.romawaysz.com/";
//							if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
//								ip = RomaSysConfig.getIp();
//							} else {
//								ip = Res.getString(R.string.NetWork_IP);
//							}
							//参数
//							final Map<String,String> params = new HashMap<String,String>();
//							params.put("img", finalValue);
//							final String finalIp = ip;
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//									String result = HttpUtils2.submitPostData(finalIp + "/external/imgToStock/", params, "utf-8");
//									Logger.d("[App 请求接口]", "result = " + result);
//								}
//							}).start();
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}catch(Exception e){
				e.printStackTrace();
				Logger.d(TAG, "[js调用原生接口] 异常！");
			}
		}
	}

	/**
	 * 获取 App 版本号
	 * @param backMethodName
     */
	@android.webkit.JavascriptInterface
	public void getAppVersion(final String backMethodName) {
		Logger.i(TAG, "[js调用原生接口]  getAppVersion");

		registerBackMethodName("getAppVersion", backMethodName);
		if(webView != null && !StringUtils.isEmpty(backMethodName)){
			try {
				webView.post(new Runnable() {
					@Override
					public void run() {
						webView.loadUrl(
								"javascript:"+backMethodName+"('"+ Res.getString(R.string.roma_app_version)+"')");
					}
				});

			} catch(Exception e) {
				e.printStackTrace();
				Logger.d(TAG, "[js调用原生接口] 异常！");
			}
		}
	}

	/**
	 * 批量添加自选股
	 * @param stockJson
     */
	@android.webkit.JavascriptInterface
	public void batchAddUserStock(String stockJson) {
		Logger.d("ROMA_Native", "stockJson = " + stockJson);
		if (StringUtils.isEmpty(stockJson)) {
			return;
		}
		try {
			String stockName = "";
			String stockCode = "";
			String marketId = "";
			BaseJSONArray jsonArray = new BaseJSONArray(stockJson);
			for (int i = 0; i < jsonArray.length(); i++) {
				BaseJSONObject jsonObject = jsonArray.getJSONObject(i);
				stockName = jsonObject.getString("stock_name");
				stockCode = jsonObject.getString("stock_code");
				marketId = jsonObject.getString("stock_market");
				// 纠正市场代码:
				marketId = marketIdCorrect(marketId);
				boolean isMyStock = UserStockSQLMgr.isExistStock(mActivity, stockCode, marketId);
				if (!isMyStock) {
					UserStockSQLMgr.insertData(mActivity, stockName, stockCode, marketId, "", "", "0", "");
					// 4.自选股同步：增加自选股请求
					/**
					 * 1.用户级别判断 2.增加自选股 3.响应失败，就弹出提示：添加/删除自选股失败，并且回退数据库的修改和界面的修改
					 */
					String stockCode_marketId = marketId + ":" + stockCode;
					Logger.i("UserStockTBServer", "自选股[新增]：stockCode_marketId："
							+ stockCode_marketId);
				}
			}
		} catch (Exception e) {
			Logger.e("batchAddUserStock", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 纠正市场代码: 交易市场代码 --> 行情市场代码
	 * 暂仅支持沪深AB股
	 * @param marketId	交易市场代码
	 * @return			行情市场代码
	 */
	private String marketIdCorrect(String marketId){
		if (!StringUtils.isEmpty(marketId)) {
			if (marketId.equals("0") || marketId.equals("2")) {
				marketId = "1";// 深证
			} else if (marketId.endsWith("1") || marketId.equals("3")) {
				marketId = "2";// 上证
			}
		}
		return marketId;
	}

	/**
	 * 复制微信号，跳转微信
	 * @param wechatCode
     */
	@android.webkit.JavascriptInterface
	public void openToWeChat(String wechatCode) {
		Logger.d("ROMA_Native", "goToWeChat = " + wechatCode);
		if (mActivity instanceof TouguShowH5ActivityNew) {
			ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
			// 将文本内容放到系统剪贴板里。
			cm.setText(RomaSysConfig.getWechatCode());
			if (PermissionsUtils.isWeixinAvilible(OriginalContext.getContext())) {
				Intent intent = new Intent();
				ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setComponent(cmp);
				mActivity.startActivity(intent);
			}
		}
	}

	/**
	 * 跳转APP本地产品页
	 * @param value
	 */
	@android.webkit.JavascriptInterface
	public void goToProductWindow(String value) {
		Logger.d("ROMA_Native", "goToProductWindow = " + value);
		String funCode = "";
		String isBook = "0"; // 0:未订阅，1:已订阅
		if (!StringUtils.isEmpty(value)) {
			try {
				BaseJSONObject jsonObject = new BaseJSONObject(value);
				if (jsonObject.has("funCode")) {
					funCode = jsonObject.getString("funCode");
				}
				if (jsonObject.has("isBook")) {
					isBook = jsonObject.getString("isBook");
				}
				if (!StringUtils.isEmpty(funCode)) {
					if ("ROMA_SHARE_GIFT".equals(funCode)) {
						KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.HomePageShareMainActivityNewNew", null, false);
					} else if ("ROMA_VIP_STOCK".equals(funCode)) {
						if (RomaUserAccount.isGuest()) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", null, false);
							return;
						}
						if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", null, false);
							return;
						}
						if ("1".equals(isBook)) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.RomaGoldStockPondActivityNew", null, false);
						} else {
							String ip = "http://api.guxiansen.test1.romawaysz.com/";
							if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
								ip = RomaSysConfig.getIp();
							} else {
								ip = Res.getString(R.string.NetWork_IP);
							}
							String url = ip + "product/intro?pro_id=" + RomaSysConfig.getJpgpcId() +"&user_id=" + RomaUserAccount.getUserID();
							Bundle bundle = new Bundle();
							bundle.putString("key_h5url", url);
							bundle.putInt("ket_right_img_visibility", View.VISIBLE);
							Logger.d("key_h5url", "key_h5url = " + url);
							bundle.putString("shareUrl", url + "&share=1");
							bundle.putString("shareTitle", "webTitle");
							bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
							bundle.putBoolean("isShowFloatWindow", true);
							bundle.putString("module_id", "2");
							KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
						}
					} else if ("ROMA_STYLE_INVEST".equals(funCode)) {
						if (RomaUserAccount.isGuest()) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", null, false);
							return;
						}
						if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", null, false);
							return;
						}
						if ("1".equals(isBook)) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.RomaLimitUpActivityNew", null, false);
						} else {
							String ip = "http://api.guxiansen.test1.romawaysz.com/";
							if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
								ip = RomaSysConfig.getIp();
							} else {
								ip = Res.getString(R.string.NetWork_IP);
							}
							String url = ip + "product/intro?pro_id=" + RomaSysConfig.getZtdjId() +"&user_id=" + RomaUserAccount.getUserID();
							Bundle bundle = new Bundle();
							bundle.putString("key_h5url", url);
							bundle.putInt("ket_right_img_visibility", View.VISIBLE);
							Logger.d("key_h5url", "key_h5url = " + url);
							bundle.putString("shareUrl", url + "&share=1");
							bundle.putString("shareTitle", "webTitle");
							bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
							bundle.putBoolean("isShowFloatWindow", true);
							bundle.putString("module_id", "3");
							KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
						}
					} else if ("ROMA_TG_SPECIALIST".equals(funCode)) {
						if (RomaUserAccount.isGuest()) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", null, false);
							return;
						}
						if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", null, false);
							return;
						}
						if ("1".equals(isBook)) {
							Bundle bundle = new Bundle();
							bundle.putInt("TopIndex", 1);
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.WenjianListAvtivityNew", bundle, false);
						} else {
							String ip = "http://api.guxiansen.test1.romawaysz.com/";
							if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
								ip = RomaSysConfig.getIp();
							} else {
								ip = Res.getString(R.string.NetWork_IP);
							}
							String url = ip + "product/intro?pro_id=12" +"&user_id=" + RomaUserAccount.getUserID();
							Bundle bundle = new Bundle();
							bundle.putString("key_h5url", url);
							bundle.putInt("ket_right_img_visibility", View.VISIBLE);
							Logger.d("key_h5url", "key_h5url = " + url);
							bundle.putString("shareUrl", url + "&share=1");
							bundle.putString("shareTitle", "webTitle");
							bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
							if (url.contains("school/teacher") || url.contains("product/Diagnosis")) {
								bundle.putBoolean("isScroll", false);
							}
							KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
						}
					} else if ("ROMA_AI_ROBOT".equals(funCode)) {
						if (RomaUserAccount.isGuest()) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.main.android.RomaLoginActivityNew", null, false);
							return;
						}
						if (StringUtils.isEmpty(RomaUserAccount.getMobileLoginNumber())) {
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.wo.android.phone.RomaAccountBindPhoneActivity", null, false);
							return;
						}
						if ("1".equals(isBook)) {
							Bundle bundle = new Bundle();
							bundle.putInt("TopIndex", 2);
							KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.WenjianListAvtivityNew", bundle, false);
						} else {
							String ip = "http://api.guxiansen.test1.romawaysz.com/";
							if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
								ip = RomaSysConfig.getIp();
							} else {
								ip = Res.getString(R.string.NetWork_IP);
							}
							String url = ip + "product/productSort?pro_id=59" + "&user_id=" + RomaUserAccount.getUserID();
							Bundle bundle = new Bundle();
							bundle.putString("key_h5url", url);
							bundle.putInt("ket_right_img_visibility", View.VISIBLE);
							Logger.d("key_h5url", "key_h5url = " + url);
							bundle.putString("shareUrl", url + "&share=1");
							bundle.putString("shareTitle", "webTitle");
							bundle.putString("shareDescription", Res.getString(R.string.news_share_description));
							bundle.putBoolean("isShowFloatWindow", true);
							bundle.putString("module_id", "4");
							KActivityMgr.switchWindow((ISubTabView) mActivity, TouguShowH5ActivityNew.class, bundle, false);
						}
					} else if ("ROMA_LIMIT_UP".equals(funCode)) {
						KActivityMgr.switchWindow((ISubTabView) mActivity, "roma.romaway.homepage.android.phone.RomaLimitUpHistoryActivity", null, false);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
