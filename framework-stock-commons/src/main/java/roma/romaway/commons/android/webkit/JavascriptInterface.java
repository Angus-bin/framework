package roma.romaway.commons.android.webkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.romaway.activity.basephone.BaseSherlockFragmentActivity;
import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.RomaAgentMgr;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.R;
import com.romaway.android.phone.ViewParams;
import com.romaway.android.phone.config.Configs;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.keyboardelf.RomaSearchActivityNew;
import com.romaway.android.phone.keyboardelf.KeyboardElfDBUtil;
import com.romaway.android.phone.keyboardelf.UserStockSQLMgr;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.service.DownloadService;
import com.romaway.android.phone.share.RomaSharePopMenu;
import com.romaway.android.phone.timer.JYTimer;
import com.romaway.android.phone.timer.OnTimerOutListener;
import com.romaway.android.phone.timer.RZRQ_JYTimer;
import com.romaway.android.phone.utils.BitmapUtils;
import com.romaway.android.phone.utils.CommonUtils;
import com.romaway.android.phone.utils.DateDialogUtils;
import com.romaway.android.phone.utils.ExitConfirm;
import com.romaway.android.phone.utils.JYOuttimeUtil;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.android.phone.utils.StockCacheInfo;
import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.android.phone.widget.SysInfo;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.hq.HQZXGTBAddProtocol;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.szkingdom.stocksearch.keyboard.KeyCodes;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roma.romaway.commons.android.tougu.TouguShowH5Activity;
import roma.romaway.commons.android.webkit.keyboard.ZXJT_KeyboardManager;
import kds.szkingdom.commons.cipher.rsa.RSAUtils;

public class JavascriptInterface implements BaseInterface {

	private String interfaceName = "ROMA_Native";
	private String[][][] shiDang;
	private String currentPrice;
	private String closePrice;
	private WebkitSherlockFragment mBaseFragment;
	private String pName;
	private ZXJT_KeyboardManager mKeyboardManager;

	@SuppressLint("NewApi")
	/**
	 * 键盘按键监听器
	 */
	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {

		@Override
		public void onPress(int primaryCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			// TODO Auto-generated method stub

			if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
				mBaseFragment.setInputContent("del");
			} else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换

			} else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换

			} else if (primaryCode == Keyboard.KEYCODE_DONE) {// 确定按键
				hideKeyBoard();
				mBaseFragment.getKdsWebView().postDelayed(new Runnable() {
					@Override
					public void run() {
						mBaseFragment.getKdsWebView().loadUrl("javascript:closeKeyBoard()");
					}
				}, 10);
			} else if (primaryCode == 519000) {
				mBaseFragment.setInputContent("000");

			} else if (primaryCode == 519001) {
				mBaseFragment.setInputContent(".");
			} else {// 得到键值
				mBaseFragment.setInputContent(Character
						.toString((char) primaryCode));
			}
		}

		@Override
		public void onText(CharSequence text) {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeLeft() {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeRight() {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeDown() {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeUp() {
			// TODO Auto-generated method stub

		}

	};

	public JavascriptInterface(WebkitSherlockFragment baseFragment) {

		mBaseFragment = baseFragment;
	}

	//初始化键盘的管理类
	public void setKeyboardManager(ZXJT_KeyboardManager keyboardManager) {
		mKeyboardManager = keyboardManager;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceName() {

		return interfaceName;
	}

	private final int SWITCH_WEBVIEW = 0;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case SWITCH_WEBVIEW:// 用于切换webView页面
				mBaseFragment.switchFragmentForStack(newFragment);
				break;
			}
		}
	};

	private WebkitSherlockFragment newFragment = null;

	@android.webkit.JavascriptInterface
	@Override
	public void switchWebView(String url, int hasRefresh, int direction) {
		// TODO Auto-generated method stub

		// 用于Fragment处理切换后的事件
		if(mBaseFragment != null)
		mBaseFragment.onSwitchWebView(url, hasRefresh);
	}

	/**
	 * 处理返回上一级界面的情况
	 *
	 * @param myurl
	 */
	@android.webkit.JavascriptInterface
	public void UIBack(String myurl, int direction) {

		int stackCount = ((BaseSherlockFragmentActivity) mBaseFragment.mActivity)
				.getBackStackEntryCount();

		if (stackCount >= 2 && direction == -1) {

			List<SherlockFragment> fragmentStack = ((BaseSherlockFragmentActivity) mBaseFragment.mActivity)
					.getFragmentList();

			WebkitSherlockFragment wsf = (WebkitSherlockFragment) fragmentStack
					.get(fragmentStack.size() - 2);
			wsf.setUrl(myurl);
			wsf.setRomaTag(myurl);
			wsf.setFragmentTag(wsf);
			wsf.resetLoadUrl(wsf.getUrl());
			// 必须放在这后头
			mBaseFragment.finishWebView();
			mBaseFragment.switchWebViewForStack(wsf);

		}
	}

	public JavascriptInterface onChangeJavascriptInterface(
			WebkitSherlockFragment newFragment) {
		return new JavascriptInterface(newFragment);
	}

	/**
	 * 拼接传递 level2 的 json
	 * @param code
	 * @param id
	 * @return
	 */
	private String setData(String code, String id) {
		StringBuilder sb = new StringBuilder();
		if (shiDang[0][0][0] == null) {
			return "";
		}
		sb.append("{" +
				"\"data\": {" +
				"\"stockCode\": \"" + code + "\",\n" +
				"\"marketID\": \"" + id + "\"," +
				"\"sellItems\": [");
		for (int i = 0,j = 10; i < shiDang[0].length; i++,j--) {
			if (i != shiDang[0].length - 1)
				sb.append("{\"name\": \"卖" + j + "\", \"sellPrice\": \"" + shiDang[0][i][0] + "\", \"sellVolume\": \"" + shiDang[0][i][1] + "\"},");
			else
				sb.append("{\"name\": \"卖" + j + "\", \"sellPrice\": \"" + shiDang[0][i][0] + "\", \"sellVolume\": \"" + shiDang[0][i][1] + "\"}");
		}
		sb.append("]," +
				"\"buyItems\":[");
		for (int i = 0; i < shiDang[1].length; i++) {
			if (i != shiDang[1].length - 1) {
				sb.append("{\"name\": \"买" + (i + 1) + "\", \"buyPrice\": \"" + shiDang[1][i][0] + "\", \"buyVolume\": \"" + shiDang[1][i][1] + "\"},");
			} else {
				sb.append("{\"name\": \"买" + (i + 1) + "\", \"buyPrice\": \"" + shiDang[1][i][0] + "\", \"buyVolume\": \"" + shiDang[1][i][1] + "\"}");
			}
		}
		sb.append("]," + "\"currentPrice\": \"" + currentPrice + "\"" +
				",\"closePrice\": \"" + closePrice + "\"" +
				"}" +
				"}");
		return sb.toString();
	}

	@android.webkit.JavascriptInterface
	@SuppressLint("NewApi")
	@Override
	public void showKeyBoard(String type, String yCoordinate) {

		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		mBaseFragment.mActivity.getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		int mScreenHeight = localDisplayMetrics.heightPixels;
		int mScreenWidth = localDisplayMetrics.widthPixels;
		Rect frame = new Rect();
		mBaseFragment.mActivity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// yCoordinate = "1000";
		int edity = /* mScreenHeight - */(int) (Float.parseFloat(yCoordinate) * mScreenHeight)
				+ statusBarHeight
				+ Res.getDimen(R.dimen.jy_hide_keybord_btn_height);

		Logger.i("JavascriptInterface", "showKeyBoard type =" + type
				+ ",yCoordinate=" + yCoordinate + ",mScreenHeight:"
				+ mScreenHeight + ",statusBarHeight:" + statusBarHeight + "edity = " + edity);

		if (edity > mScreenHeight) {
			edity = mScreenHeight;
		}

		//是否使用键盘宝2.0
		if (Res.getBoolean(R.bool.is_use_2_0_keyboard)) {
			ZXJT_KeyboardManager.KdsOnWebKeyboardActionListener keyboardClickListener = mKeyboardManager.new KdsOnWebKeyboardActionListener() {
				@Override
				public void setInputContent(String inputContent) {
					mBaseFragment.setInputContent(inputContent);
				}

				@Override
				public void onKey(int primaryCode) {
					super.onKey(primaryCode);
					if(primaryCode == KeyCodes.KEYCODE_CANCEL || primaryCode == KeyCodes.KEYCODE_DONE){
						if(mKeyboardManager!=null){
							mBaseFragment.getKdsWebView().postDelayed(new Runnable() {
								@Override
								public void run() {
									mBaseFragment.getKdsWebView().loadUrl("javascript:closeKeyBoard()");
								}
							}, 10);
						}
						mBaseFragment.onKeyBoardChangeObserver(false);
					}
				}
			};
			int index = Integer.parseInt(type);
			switch (index) {
				case 1:
					mKeyboardManager.loadKeyboardAndShow(R.xml.kds_stock_keyboard_number, edity, keyboardClickListener, new HideKeyOnClickListener());
					break;
				case 2:
					mKeyboardManager.loadKeyboardAndShow(R.xml.kds_stock_keyboard_number_point, edity, keyboardClickListener, new HideKeyOnClickListener());
					break;
				case 3:
					mKeyboardManager.loadKeyboardAndShow(R.xml.kds_stock_keyboard_number_for_switch_abc_symbols, edity, keyboardClickListener, new HideKeyOnClickListener());
					break;
				case 4:
					mKeyboardManager.loadKeyboardAndShow(R.xml.kds_stock_keyboard_number_for_trade_buysell, edity, keyboardClickListener, new HideKeyOnClickListener());
					break;
				case 5:
					mKeyboardManager.loadKeyboardAndShow(R.xml.kds_stock_keyboard_number_for_switch_abc_system, edity, keyboardClickListener, new HideKeyOnClickListener());
					break;
				case 6:
					mKeyboardManager.loadKeyboardAndShow(R.xml.kds_stock_keyboard_number_for_switch_system, edity, keyboardClickListener, new HideKeyOnClickListener());
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					mBaseFragment.setInputContent("");
					ViewParams.bundle.putBoolean("TradeSearch", true);
					ViewParams.bundle.putInt("StockQueryType", 9);
					if(mKeyboardManager!=null&&mKeyboardManager.isShowBoard()){
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mKeyboardManager.hideKeyboard();
							}
						});
					}

//					if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_v2_0))
//						KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
//								Roma_SearchActivity.class, ViewParams.bundle, false);
//					else
						KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
								RomaSearchActivityNew.class, ViewParams.bundle, false);
					break;
				case 10:
					ViewParams.bundle.putBoolean("TradeSearch", true);
					ViewParams.bundle.putInt("StockQueryType", 0);		// 清空搜索港股类型标志污染
					if(mKeyboardManager!=null&&mKeyboardManager.isShowBoard()){
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mKeyboardManager.hideKeyboard();
							}
						});
					}
//					if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_v2_0)) {
//						KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
//								Roma_SearchActivity.class, ViewParams.bundle, false);
//					} else
						KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
								RomaSearchActivityNew.class, ViewParams.bundle, false);
					break;
			}
			mBaseFragment.onKeyBoardChangeObserver(true);
		} else {
			// type 键盘类型 1.纯数字键盘,2.带小数点的数字键盘,3.数字字母切换键盘,4.纯字母键盘
			if (Integer.parseInt(type) == 1) {// 纯数字键盘
				mBaseFragment.loadRomaKeyboard(R.xml.roma_stock_keyboard_symbols,
						edity, listener, new HideKeyOnClickListener());

			} else if (Integer.parseInt(type) == 2) {// 带小数点键盘
				mBaseFragment.loadRomaKeyboard(
						R.xml.roma_stock_keyboard_point_symbols, edity, listener,
						new HideKeyOnClickListener());

			} else if (Integer.parseInt(type) == 3) {// 字母与数字切换键盘
				mBaseFragment.loadRomaKeyboard(-1, edity, listener,
						new HideKeyOnClickListener());

			} else if (Integer.parseInt(type) == 4) {
				mBaseFragment.loadRomaKeyboard(R.xml.roma_stock_keyboard_qwerty,
						edity, listener, new HideKeyOnClickListener());// 字母键盘

			} else if (Integer.parseInt(type) == 10) {
				// mBaseFragment.loadRomaKeyboard(R.xml.roma_stock_keyboard_qwerty,
				// edity, listener);//股票代码搜索键盘
				// 先清空数据
//			mBaseFragment.setInputContent("");
				ViewParams.bundle.putBoolean("TradeSearch", true);
				ViewParams.bundle.putInt("StockQueryType", 0);		// 清空搜索港股类型标志污染
//				if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_v2_0)) {
//					KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
//							Roma_SearchActivity.class, ViewParams.bundle, false);
//				} else
					KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
							RomaSearchActivityNew.class, ViewParams.bundle, false);

			} else if (Integer.parseInt(type) == 9) {
				mBaseFragment.setInputContent("");
				ViewParams.bundle.putBoolean("TradeSearch", true);
				ViewParams.bundle.putInt("StockQueryType", 9);

//				if (Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_v2_0))
//					KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
//							Roma_SearchActivity.class, ViewParams.bundle, false);
//				else
					KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
							RomaSearchActivityNew.class, ViewParams.bundle, false);

			}
			mBaseFragment.onShowKeyBorad(type);
		}
	}

	private class HideKeyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			hideKeyBoard();
			mBaseFragment.getKdsWebView().postDelayed(new Runnable() {
				@Override
				public void run() {
					mBaseFragment.getKdsWebView().loadUrl("javascript:closeKeyBoard()");
				}
			}, 10);
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void hideKeyBoard() {
		// TODO Auto-generated method stub
		try{
			if(mBaseFragment != null){
				mBaseFragment.onHideKeyBoard();
				mBaseFragment.onKeyBoardChangeObserver(false);
			}
		}catch(Exception e){
			
		}
	}

	/**
	 * 用于处理交易定时器的问题
	 * 
	 * @param timerName
	 * @param actionType
	 */
	@android.webkit.JavascriptInterface
	@Override
	public void timerAction(String timerName, String actionType) {

		Logger.d("定时器", "timerAction timerName:" + timerName + ",actionType:"
				+ actionType);

		if (timerName != null && timerName.equals("ptjy")) {
			JYTimer.getInstance().setOnTimerOutListener(
					new OnTimerOutListener() {

						@Override
						public void onTime() {
							// TODO Auto-generated method stub
							Logger.d("定时器", "时间已到！");
							try{
								if (JYTimer.currentJyFragment != null) {
									JYTimer.currentJyFragment
											.getKdsWebView().post(new Runnable() {
										@Override
										public void run() {
											JYTimer.currentJyFragment
													.getKdsWebView()
													.loadUrl(
															"javascript:TimeoutHandler('"
																	+ JYOuttimeUtil.lockTypePtjy
																	+ "')");
										}
									});
									// Logger.d("定时器", "mBaseFragmentClassName:"+
									// JYTimer.currentJyFragment.getClass().getName());
								}
							}catch(Exception e){
								
							}
						}
					});

			if (actionType != null) {
				if (actionType.equals("reset")) {
					Logger.d("定时器", "定时重置");
					JYTimer.getInstance().reset();

				} else if (actionType.equals("stop")) {
					Logger.d("定时器", "定时结束");
					JYTimer.getInstance().stop();

				} else if (actionType.equals("start")) {
					Logger.d("定时器", "开始定时");
					JYTimer.getInstance().start();

				}
			}
		} else if (timerName != null && timerName.equals("rzrq")) {

			RZRQ_JYTimer.getInstance().setOnTimerOutListener(
					new OnTimerOutListener() {

						@Override
						public void onTime() {
							// TODO Auto-generated method stub
							Logger.d("定时器", "时间已到！");
							if (RZRQ_JYTimer.currentRzrqFragment != null) {
								RZRQ_JYTimer.currentRzrqFragment
										.getKdsWebView().post(new Runnable() {
									@Override
									public void run() {
										RZRQ_JYTimer.currentRzrqFragment
												.getKdsWebView()
												.loadUrl(
														"javascript:TimeoutHandler('"
																+ JYOuttimeUtil.lockTypeRzrq
																+ "')");
									}
								});
								// Logger.d("定时器", "mBaseFragmentClassName:"+
								// RZRQ_JYTimer.currentRzrqFragment.getClass().getName());
							}
						}
					});

			if (actionType != null) {
				if (actionType.equals("reset")) {
					Logger.d("定时器", "定时重置");
					RZRQ_JYTimer.getInstance().reset();

				} else if (actionType.equals("stop")) {
					Logger.d("定时器", "定时结束");
					RZRQ_JYTimer.getInstance().stop();
				} else if (actionType.equals("start")) {
					Logger.d("定时器", "开始定时");
					RZRQ_JYTimer.getInstance().start();
				}
			}
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void addUserStock(String stockName, String stockCode, String marketId) {
		Logger.d("addUserStock", "---stockName:" + stockName + " stockCode:"
				+ stockCode + " marketId:" + marketId);
		// 纠正市场代码:
		marketId = marketIdCorrect(marketId);

		boolean isMyStock = UserStockSQLMgr.isExistStock(
				mBaseFragment.mActivity, stockCode, marketId);
        //是否显示委托收藏股票
        if (Res.getBoolean(R.bool.is_add_principal_sc_stock)) {
            if (!isMyStock && RomaSysConfig.isChecked) {
                UserStockSQLMgr.insertData(mBaseFragment.mActivity, stockName,
						stockCode, marketId, "", "", "0", "");
                // 4.自选股同步：增加自选股请求
                /**
                 * 1.用户级别判断 2.增加自选股 3.响应失败，就弹出提示：添加/删除自选股失败，并且回退数据库的修改和界面的修改
                 */
                String stockCode_marketId = marketId + ":" + stockCode;
                Logger.i("UserStockTBServer", "自选股[新增]：stockCode_marketId："
						+ stockCode_marketId);
            }
        } else {
            if (!isMyStock) {
                UserStockSQLMgr.insertData(mBaseFragment.mActivity, stockName,
						stockCode, marketId, "", "", "0", "");
                // 4.自选股同步：增加自选股请求
                /**
                 * 1.用户级别判断 2.增加自选股 3.响应失败，就弹出提示：添加/删除自选股失败，并且回退数据库的修改和界面的修改
                 */
                String stockCode_marketId = marketId + ":" + stockCode;
                Logger.i("UserStockTBServer", "自选股[新增]：stockCode_marketId："
						+ stockCode_marketId);
            }
        }
	}

	@android.webkit.JavascriptInterface
	public void batchAddUserStock(String stockJson) {
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
				stockName = jsonObject.getString("stockName");
				stockCode = jsonObject.getString("stockCode");
				marketId = jsonObject.getString("marketId");
				// 纠正市场代码:
				marketId = marketIdCorrect(marketId);
				boolean isMyStock = UserStockSQLMgr.isExistStock(
						mBaseFragment.mActivity, stockCode, marketId);
				//是否显示委托收藏股票
				if (Res.getBoolean(R.bool.is_add_principal_sc_stock)) {
					if (!isMyStock/* && RomaSysConfig.isChecked*/) {
						UserStockSQLMgr.insertData(mBaseFragment.mActivity, stockName,
								stockCode, marketId, "", "", "0", "");
						// 4.自选股同步：增加自选股请求
						/**
						 * 1.用户级别判断 2.增加自选股 3.响应失败，就弹出提示：添加/删除自选股失败，并且回退数据库的修改和界面的修改
						 */
						String stockCode_marketId = marketId + ":" + stockCode;
						Logger.i("UserStockTBServer", "自选股[新增]：stockCode_marketId："
								+ stockCode_marketId);
					}
				} else {
					if (!isMyStock) {
						UserStockSQLMgr.insertData(mBaseFragment.mActivity, stockName,
								stockCode, marketId, "", "", "0", "");
						// 4.自选股同步：增加自选股请求
						/**
						 * 1.用户级别判断 2.增加自选股 3.响应失败，就弹出提示：添加/删除自选股失败，并且回退数据库的修改和界面的修改
						 */
						String stockCode_marketId = marketId + ":" + stockCode;
						Logger.i("UserStockTBServer", "自选股[新增]：stockCode_marketId："
								+ stockCode_marketId);
					}
				}
			}
		} catch (Exception e) {
			Logger.e("batchAddUserStock", e.getMessage());
		}
	}

	@android.webkit.JavascriptInterface
	public void cheakUserStockInfo(String stockJson) {
		if (StringUtils.isEmpty(stockJson)) {
			return;
		}
		try {
			String stockName = "";
			String stockCode = "";
			String marketId = "";
			String marketId2 = "";
			final BaseJSONArray jsonArray2 = new BaseJSONArray();
			BaseJSONObject json = new BaseJSONObject();
			BaseJSONArray jsonArray = new BaseJSONArray(stockJson);
			for (int i = 0; i < jsonArray.length(); i++) {
				BaseJSONObject jsonObject = jsonArray.getJSONObject(i);
				stockName = jsonObject.getString("stockName");
				stockCode = jsonObject.getString("stockCode");
				marketId = jsonObject.getString("marketId");
				// 纠正市场代码:
				marketId2 = marketIdCorrect(marketId);
				boolean isMyStock = UserStockSQLMgr.isExistStock(
						mBaseFragment.mActivity, stockCode, marketId2);
				if (isMyStock) {
					json.put("stockName", stockName);
					json.put("stockCode", stockCode);
					json.put("marketId", marketId);
				}
			}
			if (!StringUtils.isEmpty(json.toString()))
				jsonArray2.put(json);
			if(mBaseFragment != null) {
				mBaseFragment.getKdsWebView().postDelayed(new Runnable() {
					@Override
					public void run() {
						mBaseFragment.getKdsWebView().loadJsMethodUrl("javascript:setUserStockList(" + jsonArray2.toString() + ")");
					}
				}, 10);
			}
		} catch (Exception e) {
			Logger.e("cheakUserStockInfo", e.getMessage());
		}
	}

	@android.webkit.JavascriptInterface
	public void getIsHaveSIM() {
		TelephonyManager mTelephonyManager=(TelephonyManager) OriginalContext.getContext().getSystemService(Service.TELEPHONY_SERVICE);
		int absent = mTelephonyManager.getSimState();
		//[特殊处理]Debug模式时默认返回是否含有SIM卡都为true:
		if (absent == 1 && !Logger.getDebugMode()) {
			try{
				if(mBaseFragment != null){
					mBaseFragment.getKdsWebView().postDelayed(new Runnable() {
						@Override
						public void run() {
							mBaseFragment.getKdsWebView().loadUrl("javascript:setIsHaveSIM('" + "false" +"')");
						}
					}, 10);
				}
			}catch(Exception e){
				Logger.e("getIsHaveSIM", e.getMessage());
			}
		} else {
			try{
				if(mBaseFragment != null){
					mBaseFragment.getKdsWebView().postDelayed(new Runnable() {
						@Override
						public void run() {
							mBaseFragment.getKdsWebView().loadUrl("javascript:setIsHaveSIM('" + "true" +"')");
						}
					}, 10);
				}
			}catch(Exception e){
				Logger.e("getIsHaveSIM", e.getMessage());
			}
		}
	}

	@android.webkit.JavascriptInterface
	public void getDeveiceID() {
		try{
			if(mBaseFragment != null){
				mBaseFragment.getKdsWebView().postDelayed(new Runnable() {
					@Override
					public void run() {
						String deviceId = SysConfigs.DEVICE_ID;
						mBaseFragment.getKdsWebView().loadUrl("javascript:setDeveiceID('" + deviceId +"')");
					}
				}, 10);
			}
		}catch(Exception e){
			Logger.e("getDeveiceID", e.getMessage());
		}
	}

	/** 自选行情网络监听者 */
	private class UserStockHQListener extends UINetReceiveListener {
		public UserStockHQListener(Activity activity) {
			super(activity);
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			// 自选股同步添加
			HQZXGTBAddProtocol hqzxgtb = (HQZXGTBAddProtocol) ptl;
			if (hqzxgtb.serverErrCode != 0) {
				String stockCode = hqzxgtb.req_favors.split(":")[1];
				String marketId = hqzxgtb.req_favors.split(":")[0];
				// 回退本地自选股
				UserStockSQLMgr.deleteByStockCode(mBaseFragment.mActivity, stockCode, marketId);
			}
			Logger.i("UserStockTBServer", "自选股[新增]：成功");
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
			try{
				if (status != SUCCESS) {
					String stockCode = ((HQZXGTBAddProtocol) msg.getProtocol()).req_favors
							.split(":")[1];
					String marketId = ((HQZXGTBAddProtocol) msg.getProtocol()).req_favors
							.split(":")[0];
					// 回退本地自选股
					UserStockSQLMgr.deleteByStockCode(mBaseFragment.mActivity, stockCode, marketId);
				}
			}catch(Exception e){
				
			}
		}
	}

	/**
	 * 回退本地数据库
	 */
	@android.webkit.JavascriptInterface
	public void rollBack(String stockCode, String marketId) {
		UserStockSQLMgr.deleteByStockCode(mBaseFragment.mActivity, stockCode, marketId);
	}
	
	/**
	 * 获取本地数据
	 */
	@android.webkit.JavascriptInterface
	@Override
	public String getLocalData(String key){
		
		pName = Res.getString(R.string.localName);
		
		String localValue = SharedPreferenceUtils.getPreference(pName, key, "");
		
		if(StringUtils.isEmpty(localValue))
			localValue = null;
		
		Logger.d("JavascriptInterface", "[getLocalData]:value:" + localValue);
		
		return localValue;
	}


	/**
	 * 保存数据到本地或者更新本地数据
	 */
	@android.webkit.JavascriptInterface
	@Override
	public void saveOrUpdateLocalData(String key, String value) {
		// TODO Auto-generated method stub
		pName = Res.getString(R.string.localName);
		
	//	Toast.makeText(mBaseFragment.mActivity, 
	//			"保存数据到本地：pName = "+pName+"key = "+key+",value = "+value, 
	//			Toast.LENGTH_LONG).show();
		
		/**
		 * 1.判断value值是否为空，如果为空，就将对应的key-value键值对删掉； 如果不为空，2.就直接保存；
		 */
		if (StringUtils.isEmpty(value)) {
			SharedPreferenceUtils.removePreference(pName, key);
		} else {
			SharedPreferenceUtils.setPreference(pName, key, value);
		}

		if ("ptjyLszhList".equalsIgnoreCase(key) || "rzrqLszhList".equalsIgnoreCase(key)){
			// [需求]定制版TalkingData用于统计用户唯一标识(talkingdata自带数据加密):
			if(!TextUtils.isEmpty(Res.getString(R.string.roma_accountMarkTag))) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("UserPhoneNum_account", RomaUserAccount.getUsername());
				RomaAgentMgr.onEvent(mBaseFragment.getActivity(), "eventObtainPhoneNum_account", "labelObtainPhoneNum_account", map);
				RomaAgentMgr.onUserDataEvent(mBaseFragment.getActivity(), Res.getString(R.string.roma_accountMarkTag), RomaUserAccount.getAccountAsTradeType(key));
				Logger.i("TAG", "talkingdata保存: " + key + ", " + RomaUserAccount.getAccountAsTradeType(key) + ", " + RomaUserAccount.getUsername());
			}
		}
	}

	/**
	 * 提供给H5页面调用的函数接口
	 * 
	 * @param loginAccount
	 */
	@Override
	@android.webkit.JavascriptInterface
	public void onLoginAccount(String loginAccount) {
		JYStatusUtil.loginStatus = JYStatusUtil.JY_LOGIN_STATUS_NONE;

		boolean isPtjyLogin = true;
		if(JYStatusUtil.RZRQ_LOGIN_TYPE.equalsIgnoreCase(JYStatusUtil.loginType)){
			isPtjyLogin = false;
		}

		if (loginAccount == null || loginAccount.equals("")) {
			Logger.d("JavascriptInterface", "[交易状态]:当前未登录");
			JYStatusUtil.loginStatus = isPtjyLogin ? JYStatusUtil.JY_LOGIN_STATUS_NONE : JYStatusUtil.RZRQ_LOGIN_STATUS_NONE;
		} else {
			Logger.d("JavascriptInterface", "[交易状态]:当前已登录");
			JYStatusUtil.loginStatus = isPtjyLogin ? JYStatusUtil.JY_LOGIN_STATUS_OK : JYStatusUtil.RZRQ_LOGIN_STATUS_OK;
		}

		if (JYStatusUtil.mOnLoginAccountListener != null) {
			Logger.d("JavascriptInterface", "[交易状态]:已设置状态监听器!");
			JYStatusUtil.mOnLoginAccountListener
					.onLoginAccount(JYStatusUtil.loginStatus, loginAccount);
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void selfserviceAccount() {
		// TODO Auto-generated method stub
		if(mBaseFragment != null)
		mBaseFragment.onSelfserviceAccount();
	}

	@android.webkit.JavascriptInterface
	@Override
	public void onCancelLogin() {
		// TODO Auto-generated method stub
		
		//Logger.d("tag", "onCancelLogin onCancelLogin");
		//清除所有cookie
		CookieManager.getInstance().removeAllCookie();
	}

	/**
	 * 交易登录传递信息
	 */
	@android.webkit.JavascriptInterface
	@Override
	public void onLoginTrade(String value) {
		if (value !=null && !"".equals(value)) {
			try {
				JSONObject obj = new JSONObject(value);
				String PersonalName = obj.getString("name");//普通交易登录的姓名
				String yybjc = obj.getString("yybjc");//普通交易登录的姓名
				SharedPreferenceUtils.setPreference("PersonalData", "PersonalName", PersonalName);
				SharedPreferenceUtils.setPreference("PersonalData", "yybjc", yybjc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void onInterfaceA(String jsonstr) {   
		// TODO Auto-generated method stub
		
		String enableOnInterfaceA = RomaSysConfig.getParamsValue("enableOnInterfaceA");
		if(StringUtils.isEmpty(enableOnInterfaceA) || !enableOnInterfaceA.equals("1")){
			if(Logger.getDebugMode())
				Toast.makeText(mBaseFragment.mActivity, "【警告】enableOnInterfaceA 中台参数配置未开启！", Toast.LENGTH_LONG).show();
			return;
		}
		
		try {
			JSONObject json = new JSONObject(jsonstr);
			String  loginPassword = json.getString("loginPassword");
			RomaSysConfig.onInterfaceA_loginAccount = json.getString("loginAccount");
			
			if(!StringUtils.isEmpty(loginPassword)){
				// 从文件中得到公钥
				InputStream inPublic = mBaseFragment.mActivity.getResources().
						getAssets().open("rsa_public_key.pem");
				if(inPublic == null && Logger.getDebugMode())
					Toast.makeText(mBaseFragment.mActivity, "【警告】缺少加密公钥！", Toast.LENGTH_LONG).show();
				
				//密文保存在内存中
				RomaSysConfig.onInterfaceA_loginPassword = RSAUtils.encryptData(inPublic, loginPassword);
			}else
				RomaSysConfig.onInterfaceA_loginPassword = "";
			
			//Logger.d("tag", "0-onInterfaceA_loginAccount ="+RomaSysConfig.onInterfaceA_loginAccount+
	        //		",onInterfaceA_loginPassword = "+RomaSysConfig.onInterfaceA_loginPassword);
//			if(!StringUtils.isEmpty(RomaSysConfig.onInterfaceA_loginPassword)){
//				// 从文件中得到私钥
//				InputStream inPrivate = mBaseFragment.mActivity.getResources().
//						getAssets().open("pkcs8_rsa_private_key.pem");
//				String decryptdata = RSAUtils.decryptData(inPrivate, RomaSysConfig.onInterfaceA_loginPassword);
//				Logger.d("tag", "加解密 解密后的明文= "+decryptdata); 
//			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@android.webkit.JavascriptInterface
	@Override
	public void closeApp() {
		ExitConfirm.confirmExit(mBaseFragment.mActivity);
//		ExitConfirm.finishProcess(mBaseFragment.mActivity);
	}

	@android.webkit.JavascriptInterface
	public void call() {
		ExitConfirm.confirmExit(mBaseFragment.mActivity);
//		ExitConfirm.finishProcess(mBaseFragment.mActivity);
	}

	@android.webkit.JavascriptInterface
	public void openPDF() {

	}

	@android.webkit.JavascriptInterface
	public void showcontacts() {
		SysInfo.showMessage(mBaseFragment.mActivity, "JS 调用了");
	}

	@android.webkit.JavascriptInterface
	public void openNewWindow(String linkUrl) {
		// TODO Auto-generated method stub
		Logger.i("TAG", "[js调用原生接口]  openNewWindow linkUrl:" + linkUrl);
		if(mBaseFragment.mActivity != null){
			//启动新的页面
			Intent intent = new Intent(); 
			intent.putExtra("key_h5url",linkUrl);
	        intent.setClass(mBaseFragment.mActivity, TouguShowH5Activity.class);
	        mBaseFragment.mActivity.startActivity(intent);
		}
	}

	/**
	 * JS调用打开加载融信通Web页面:
	 * @param webUrl		要加载的第三方Web页面;
	 */
	@android.webkit.JavascriptInterface
	public void passRXTUrlString(String webUrl) {
		openThirdPartyWebInterface("1", webUrl);
	}

	/**
	 * JS调用打开加载第三方Web页面:
	 * @param isCloseCurrent	是否关闭当前已有WebActivity[0: 不关闭 1: 关闭];
	 * @param webUrl			要加载的第三方Web页面;
     */
	@android.webkit.JavascriptInterface
	public void openThirdPartyWebInterface(String isCloseCurrent, String webUrl){
		Logger.i("TAG", "[js调用原生接口]  openThirdPartyWebInterface webUrl:" + webUrl);
		openWebUrl(isCloseCurrent, null, webUrl, null);
	}

	@android.webkit.JavascriptInterface
	public void openWebUrl(String isCloseCurrent, String functionCode, String webUrl, String backupParams){
		Logger.i("TAG", "[js调用原生接口]  openWebUrl webUrl:" + webUrl);
		// 打开WebView界面加载指定url地址:
		Intent mIntent = new Intent();

		if (!TextUtils.isEmpty(functionCode) && (functionCode.startsWith("KDS_TICKET") || functionCode.startsWith("KDS_PHONE_TICKET"))){
			String standard = "ticket=";
			if(!TextUtils.isEmpty(webUrl) && webUrl.contains(standard)){
				StringBuilder sb = new StringBuilder();
				try {
					int index = webUrl.indexOf(standard) + standard.length();
					sb.append(webUrl.substring(0, index));
					String ticket = webUrl.substring(index, webUrl.length());
					ticket = java.net.URLEncoder.encode(ticket, "utf-8");
					sb.append(ticket);
					sb.append("&type=android");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				webUrl = sb.toString();
			}
		}

		mIntent.putExtra("key_h5url", webUrl);
		mIntent.putExtra("key_titleVisibility", View.VISIBLE);
		mIntent.putExtra("backType", 0);		// 逐级关闭
		mIntent.setClass(mBaseFragment.mActivity, TouguShowH5Activity.class);
		mBaseFragment.mActivity.startActivity(mIntent);
		try {
			Class mClass = Class.forName(Res.getString(R.string.Package_Class_UserMainActivity));
			if("1".equalsIgnoreCase(isCloseCurrent) && !mBaseFragment.mActivity.getClass().equals(mClass))
				mBaseFragment.mActivity.finish();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * JS调用打开加载第三方Web页面:
	 * @param isCloseCurrent	是否关闭当前已有WebActivity[0: 不关闭 1: 关闭];
	 * @param loadUrl			要加载的第三方Web页面;
	 * @param functionCode		功能Code
	 * @param custid			客户代码
	 * @param fundid			资金账号
	 * @param userrole			用户角色
	 * @param orgid				操作机构
	 * @param ticket			ticket票据
     * @param targetUrl			目的地址
     */
	@android.webkit.JavascriptInterface
	public void openThirdPartyWebSuperInterface(String isCloseCurrent, String loadUrl, String functionCode,
						String custid, String fundid, String userrole, String orgid, String ticket, String targetUrl){
		Logger.i("TAG", "[js调用原生接口]  openThirdPartyWebInterface webUrl:" + loadUrl + ", functionCode: " + functionCode);
		// 打开WebView界面加载指定url地址:
		Intent mIntent = new Intent();
		mIntent.putExtra("key_h5url", loadUrl);
		mIntent.putExtra("key_titleVisibility", View.VISIBLE);
		mIntent.putExtra("backType", 0);		// 逐级关闭

		Bundle bundle = new Bundle();
		bundle.putString("functionCode", functionCode);
		bundle.putString("custid", custid);
		bundle.putString("fundid", fundid);
		bundle.putString("userrole", userrole);
		bundle.putString("orgid", orgid);
		bundle.putString("ticket", ticket);
		bundle.putString("targetUrl", CommonUtils.addAppTypeToTargetUrl(targetUrl));
		mIntent.putExtras(bundle);

		if (!TextUtils.isEmpty(functionCode) && !functionCode.endsWith("_HomeShortcutMenu")
				&& (functionCode.startsWith("KDS_TICKET_NO") || functionCode.startsWith("KDS_TICKET_PHONE_NO"))) {
			//[需求] 处理投顾锦囊、公募基金等在资金账号未登录时, 第三方Web页面调用原生方法获取Ticket后, 回调给第三方Web页面:
			// 发送登录成功广播, 刷新主界面:
			sendJYLoginSuccessBroadcast();

			//风险等级回调的发送广播:
			mIntent.setAction("action.ticket.loginCallback");
			mBaseFragment.mActivity.sendBroadcast(mIntent);
		}else if(!TextUtils.isEmpty(functionCode) && functionCode.endsWith("_HomeShortcutMenu")
				&& (functionCode.startsWith("KDS_TICKET_NO") || functionCode.startsWith("KDS_TICKET_PHONE_NO"))){
			//[需求] 特殊处理投顾锦囊、公募基金等在资金账号已登录时, 从快捷菜单处获取Ticket后, 跳转启动第三方Web功能界面:
			// 根据功能 funKey 获取对应的中台/默认配置的 portalUrl、targetUrl 地址:
			functionCode = functionCode.substring(0, functionCode.indexOf("_HomeShortcutMenu"));
			String[] configUrls = CommonUtils.getLoadCommonConfigUrls(functionCode);
			bundle.putString("functionCode", functionCode);
			mIntent.putExtra("key_h5url", configUrls[0]);		// portalUrl
			bundle.putString("targetUrl", configUrls[1]);		// targetUrl
			mIntent.putExtras(bundle);

			mIntent.setClass(mBaseFragment.mActivity, TouguShowH5Activity.class);
			mBaseFragment.mActivity.startActivity(mIntent);
		}else{
			if ("ROMA_MyBusinessManagement".equalsIgnoreCase(functionCode))
				mIntent.putExtra("backType", 0);		// 直接关闭

			mIntent.setClass(mBaseFragment.mActivity, TouguShowH5Activity.class);
			mBaseFragment.mActivity.startActivity(mIntent);
		}

		try {
			Class mClass = Class.forName(Res.getString(R.string.Package_Class_UserMainActivity));
			if("1".equalsIgnoreCase(isCloseCurrent) && !mBaseFragment.mActivity.getClass().equals(mClass))
				mBaseFragment.mActivity.finish();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * JS调用,目前的建此方法是为了传递客户号:
	 * @param clientId
	 */
	@android.webkit.JavascriptInterface
	public void tradeClientId(String clientId){
		Logger.i("TAG", "[js调用原生接口]  tradeClientId: " + clientId);
	}

	/**
	 * JS调用弹出原生系统日期框:
	 * @param cutDate		当前默认选中日期
	 * @param minDate		最小可选日期, 可空
	 * @param maxDate		最大可选日期, 可空
	 * @param callbackFunction	确定后回调的JS函数
	 */
	@android.webkit.JavascriptInterface
	public void showDateDialog(String cutDate, String minDate, String maxDate, String callbackFunction){
		Logger.i("TAG", "[js调用原生接口]  showDateDialog");
		DateDialogUtils.showDateDialog(mBaseFragment.mRomaWebView, cutDate, minDate, maxDate, callbackFunction);
	}

	/**
	 * JS跳到个股详情界面:
	 */
	@android.webkit.JavascriptInterface
	public void JumpStockDetailInterface(String stockCode, String marketId){
		Logger.i("TAG", "[js调用原生接口]  JumpStockDetailInterface stockCode: " + stockCode);
		Bundle bundle = new Bundle();
		bundle.putInt("HQ_POSITION", 0);
		bundle.putInt(BundleKeyValue.HQ_FROM, BundleKeyValue.HQ_FROM_TRADE);

		if (ViewParams.bundle != null) {
			marketId = marketIdCorrect(marketId);

			KeyboardElfDBUtil dbutil = new KeyboardElfDBUtil(mBaseFragment.mActivity);
			// 0:股票代码,1:股票名称,2:拼音,3:股票类型
			String[][] stockData =  dbutil.search(stockCode, Integer.parseInt(marketId));

			if (stockData.length != 0){
				// 0:股票名称；1：股票代码；2：市场ID；3：股票类型
				String[][] temp = new String[stockData.length][4];
				for (int i = 0; i < stockData.length; i++){
					temp[i][0] = stockData[i][1];
					temp[i][1] = stockData[i][0];
					temp[i][2] = marketId;
					temp[i][3] = stockData[i][3];
				}
				StockCacheInfo.clearCacheList();
				StockCacheInfo.saveListToCache(temp, new int[]{0, 1, 2, 3});

				KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
						"", bundle, false);
			}else{
				SysInfo.showMessage(mBaseFragment.mActivity, "未查询到该股票信息");
			}
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
			} else if(marketId.equals("6")){
                marketId = "33";//沪港通
            } else if(marketId.equals("9")){
                marketId = "32";//深港通
            }
		}
		return marketId;
	}

	@android.webkit.JavascriptInterface
	public void UpdateH5() {
		closeApp();
	}

	@android.webkit.JavascriptInterface
	public void UpdateApp() {
		//如果有手机号码，则进行beta版本判断
		if (!StringUtils.isEmpty(RomaUserAccount.getUsername())) {
			//如果有测试版本的升级信息，则请求当前用户是否需要升级测试版
			if (!StringUtils.isEmpty(RomaSysConfig.upgradeVersion_beta) || !StringUtils.isEmpty(RomaSysConfig.h5_versionNum_beta)) {
				String upgradeFlag = SharedPreferenceUtils.getPreference(RomaSysConfig.pName, "kds_upgrade_flag", "0");
				if ("0".equals(upgradeFlag) || "1".equals(upgradeFlag)) {//0表示不升级 1 代表 升级 h5，不升级 app
					upgradeByRelease();
				} else { //2 代表 升级 app，不升级 h5  3 代表 升级 h5 和 app
					upgradeByBeta();
				}
			} else {
				upgradeByRelease();
			}
		} else {
			upgradeByRelease();
		}
	}

	private void upgradeByRelease() {
		if (!StringUtils.isEmpty(RomaSysConfig.upgradeVersion_release)) {
			Logger.d("SystemSettingSherlockFragment", "APP服务端版本号：" + RomaSysConfig.upgradeVersion_release + " 客户端版本号：" + RomaSysConfig.getClientVersion(mBaseFragment.mActivity));
			int result = RomaSysConfig.upgradeVersion_release
					.compareTo(RomaSysConfig.getClientVersion(mBaseFragment.mActivity));
			if (result > 0) {
				Dialog imageDialog = DialogFactory.getIconDialog(mBaseFragment.mActivity,
						Res.getString(R.string.roma_wo_systemsetting_version_update),
						RomaSysConfig.upgradeMsg_release,
						DialogFactory.DIALOG_TYPE_APP_UPDATE,
						Res.getString(R.string.roma_wo_systemsetting_do_it_later),
						new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {

							}
						},
						Res.getString(R.string.roma_wo_systemsetting_update_now),
						new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {
								startDownload("release");
							}
						});
				imageDialog.show();
			} else {
				// 没有新版本
				Dialog imageDialog = DialogFactory.getIconDialog(mBaseFragment.mActivity,
						Res.getString(R.string.roma_wo_systemsetting_version_update),
						Res.getString(R.string.roma_wo_currentVersionIsTheLatest),
						DialogFactory.DIALOG_TYPE_NO_ICON,
						null, null, null, new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {

							}
						});
				imageDialog.show();
			}
		} else {
			// 没有新版本
			Dialog imageDialog = DialogFactory.getIconDialog(mBaseFragment.mActivity,
					Res.getString(R.string.roma_wo_systemsetting_version_update),
					Res.getString(R.string.roma_wo_currentVersionIsTheLatest),
					DialogFactory.DIALOG_TYPE_NO_ICON,
					null, null, null, new OnClickDialogBtnListener() {
						@Override
						public void onClickButton(View view) {

						}
					});
			imageDialog.show();

		}
	}

	private void upgradeByBeta(){
		if (!StringUtils.isEmpty(RomaSysConfig.upgradeVersion_beta)) {
			Logger.d("SystemSettingSherlockFragment", "APP服务端版本号：" + RomaSysConfig.upgradeVersion_beta + " 客户端版本号：" + RomaSysConfig.getClientVersion(mBaseFragment.mActivity));
			int result = RomaSysConfig.upgradeVersion_beta.compareTo(RomaSysConfig.getClientVersion(mBaseFragment.mActivity));
			if (result > 0) {
				Dialog dialog = DialogFactory.getIconDialog(mBaseFragment.mActivity,
						Res.getString(R.string.roma_wo_systemsetting_new_version_update),
						RomaSysConfig.upgradeMsg_beta,
						DialogFactory.DIALOG_TYPE_EXCLAMATORY,
						Res.getString(R.string.roma_wo_systemsetting_do_it_later),
						new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {
							}
						}, Res.getString(R.string.roma_wo_systemsetting_update_now),
						new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {
								startDownload("beta");
							}
						}
				);
				if (mBaseFragment.mActivity != null && !mBaseFragment.mActivity.isFinishing()) {
					dialog.show();
				}
			} else {
				// 没有新版本
				Dialog imageDialog = DialogFactory.getIconDialog(mBaseFragment.mActivity,
						Res.getString(R.string.roma_wo_systemsetting_version_update),
						Res.getString(R.string.roma_wo_currentVersionIsTheLatest),
						DialogFactory.DIALOG_TYPE_NO_ICON,
						null, null, null, new OnClickDialogBtnListener() {
							@Override
							public void onClickButton(View view) {

							}
						});
				imageDialog.show();
			}
		} else {
			/*// 没有新版本
			RomaDialog mKdsDialog = new RomaDialog(
					mActivity,
					Res.getString(R.string.kds_wo_systemsetting_version_update),
					Res.getString(R.string.kds_wo_currentVersionIsTheLatest),
					null, null);
			mKdsDialog.show();
			mKdsDialog.setOnClickRightButtonListener(
					Res.getString(R.string.kds_wo_close),
					new OnClickButtonListener() {
						@Override
						public void onClickButton(View view) {
							// TODO Auto-generated method stub

						}
					});*/
			//是灰度升级，但没有升级版本信息时，进行release版本升级判断
			upgradeByRelease();
		}
	}

	private void startDownload(String flag) {
		Intent intent = new Intent(mBaseFragment.mActivity, DownloadService.class);
		intent.putExtra("KDS_UPGRADE_TYPE", flag);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mBaseFragment.mActivity.startService(intent);
	}

	/**
	 * 调用第三方分享, 微信朋友/朋友圈:
	 * @param title			分享标题
	 * @param shareUrl		分享地址
	 */
	@android.webkit.JavascriptInterface
	public void shareToWechat(String title, String shareUrl) {
		shareToWechat(title, shareUrl, "");
	}

	@Override
	@android.webkit.JavascriptInterface
	public void shareToWechat(final String title, final String shareUrl, final String summary) {
		shareToWechat(title, shareUrl, summary, null);
	}

	/**
	 * 支持分享图标的第三方分享接口:
	 * @param title
	 * @param shareUrl
	 * @param summary
	 * @param base64Icon
	 */
	@android.webkit.JavascriptInterface
	public void shareToWechat(final String title, final String shareUrl, final String summary, final String base64Icon) {
		Logger.d("shareToWechat", "shareToWechat shareUrl: " + shareUrl);
		mBaseFragment.mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				RomaSharePopMenu share = new RomaSharePopMenu(mBaseFragment.mActivity);
				share.setTitle(title);
				share.setUrl(shareUrl);
				share.setDescription(summary);
				if (!TextUtils.isEmpty(base64Icon)){
					Bitmap thumbImage = BitmapUtils.base64ToBitmap(base64Icon, 32);		// 32kb Bitmap大小限制;
					share.setThumbImage(thumbImage);
				}
				share.showAtLocation((View) mBaseFragment.getKdsWebView().getParent());
			}
		});
	}

	/**
	 * JS跳到自选股云同步界面:
	 * @param isCloseCurrent	是否关闭当前已有WebActivity[0: 不关闭 1: 关闭];
	 */
	@android.webkit.JavascriptInterface
	public void jumpToStockSysncInterface(String isCloseCurrent){
		Logger.i("TAG", "[js调用原生接口]  jumpToStockSysncInterface");

//		if (Res.getBoolean(R.bool.kconfigs_isSupportUserStockCloudSync)) {
//			// 发送广播,同时刷新主界面:
//			sendJYLoginSuccessBroadcast();
//
//			try {
//				KActivityMgr.switchWindow((ISubTabView) mBaseFragment.mActivity,
//						(Class<? extends Activity>) Class.forName("com.szkingdom.android.phone.activity.ZXJTUserStockCloudSyncActivity"), false);
//
//				Class mClass = Class.forName(Res.getString(R.string.Package_Class_UserMainActivity));
//				if("1".equalsIgnoreCase(isCloseCurrent) && !mBaseFragment.mActivity.getClass().equals(mClass))
//					mBaseFragment.mActivity.finish();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
	}

    /**
	 * 在二级界面加载股指竞猜校验页面接口:
	 * @param webUrl
	 */
	@android.webkit.JavascriptInterface
	public void switchWebViewForResult(String webUrl){
		try {
			// 发送登录成功广播, 刷新主界面:
			sendJYLoginSuccessBroadcast();

			Bundle bundle = new Bundle();
			bundle.putInt("hasRefresh", 1);
			bundle.putString("JYUrl", webUrl);
			bundle.putString("hostUrl", getLocalData("QHBHD_HOST_URL"));
			bundle.putString("KeyFunType", "KDS_STOCK_QUIZ");
			KActivityMgr.switchWindow(
					(ISubTabView) mBaseFragment.mActivity,
					(Class<? extends Activity>) Class.forName(Res.getString(R.string.jiaoYiLoginActivity)),
					bundle, false);
			mBaseFragment.mActivity.finish();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭当前二级界面回传参数回活动主界面接口:
	 * @param state
	 * @param msg
	 * @param tradeAccount
     */
	@android.webkit.JavascriptInterface
	public void closeWebViewForResult(String state, String msg, String tradeAccount){
		Logger.i("closeResult", state + " : " + msg + " : " + tradeAccount);
		Intent intent = new Intent("action.stock.quiz.callback");
		Bundle bundle = new Bundle();
		bundle.putString("state", state);
		bundle.putString("msg", msg);
		bundle.putString("tradeAccount", tradeAccount);
		intent.putExtras(bundle);
		mBaseFragment.mActivity.sendBroadcast(intent);
		mBaseFragment.finishActivity();
		if (mBaseFragment.mActivity != null && !mBaseFragment.mActivity.isFinishing())
			mBaseFragment.mActivity.finish();
	}

	/**
	 * 股指竞猜校验页面修改原生顶部栏背景接口:
	 * @param actionBarBgColor
	 */
	@android.webkit.JavascriptInterface
	public void setActionBarBgColor(final String actionBarBgColor){
		try {
			mBaseFragment.mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (!TextUtils.isEmpty(actionBarBgColor))
						mBaseFragment.mActionBar.setBackgroundColor(Color.parseColor(actionBarBgColor));
				}
			});
		}catch (Exception e){
			Logger.e("JavascriptInterface", "setActionBarBgColor: " + actionBarBgColor);
		}
	}

	@SuppressLint("NewApi")
	@android.webkit.JavascriptInterface
	public void queryRiskLevelCallback(String riskLevel, String expireDate, String remainingTime){
		// 发送登录成功广播, 刷新主界面:
		sendJYLoginSuccessBroadcast();

		//风险等级回调的发送广播
		Intent intent = new Intent("action.risk.levelCallback");
		intent.putExtra("riskLevel",riskLevel);
		intent.putExtra("expireDate",expireDate);
		intent.putExtra("remainingTime",remainingTime);
		mBaseFragment.mActivity.sendBroadcast(intent);

		// 关闭当前WebViewActivity:
		closeCurrentWebActivity();
	}

	/**
	 * 发送登录成功广播, 刷新主界面:
	 */
	@android.webkit.JavascriptInterface
	protected void sendJYLoginSuccessBroadcast() {
		// 发送广播,同时刷新主界面
		Intent mIntent = new Intent(
				"action." + mBaseFragment.mActivity.getPackageName() + ".jiaoyi.homepage.resetLoadUrl");
		mIntent.putExtra("resultUrl", /*myurls[1]*/Configs.getJiaoYiUrl(mBaseFragment.mActivity,
				"/kds519/view/ptjy/home/ptjy_index.html"));
		mIntent.putExtra("resetLoadFlag", true);
		mBaseFragment.mActivity.sendBroadcast(mIntent);
	}

	/**
	 * 关闭当前WebViewActivity, 前提为当前界面不是主屏Activity类:
	 * @throws ClassNotFoundException
     */
	@android.webkit.JavascriptInterface
	protected void closeCurrentWebActivity() {
		try {
//			Class mClass = Class.forName(Res.getString(R.string.Package_Class_UserMainActivity));
//			if (mBaseFragment.mActivity != null && !mBaseFragment.mActivity.isFinishing() && !mBaseFragment.mActivity.getClass().equals(mClass))
				mBaseFragment.mActivity.finish();
		} catch (Exception e) {
			Logger.e("TAG", e.getMessage());
		}
	}

	/**
	 * 添加接口支持, 实时刷新指定交易模块主页状态(已登录/未登录页面)
	 * @param tradeLoginType	普通交易/融资融券
	 */
	@android.webkit.JavascriptInterface
	public void syncRefreshJYHomePage(String tradeLoginType){
		//[接口需求] 根据当前登录状态刷新交易首页界面:
		JYStatusUtil jyStatusUtil = new JYStatusUtil(mBaseFragment.mActivity, true);
		jyStatusUtil.syncRefreshJYHomePage(tradeLoginType);
	}
}
