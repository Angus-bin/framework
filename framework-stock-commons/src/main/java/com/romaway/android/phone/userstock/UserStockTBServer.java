package com.romaway.android.phone.userstock;

import android.app.Activity;

import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.netreq.HQReq;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.keyboardelf.UserStockSQLMgr;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.netreq.UserStockReq;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.StringRandom;
import com.romaway.common.protocol.hq.HQZXGTBAddProtocol;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocol;
import com.romaway.common.protocol.hq.HQZXGTBSelectProtocol2;
import com.romaway.common.protocol.hq.HQZXGTBUploadProtocol;
import com.romaway.common.protocol.hq.HQZXProtocol;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.protocol.util.ULongUtils;
import com.romaway.common.utils.DES3;
import com.romaway.common.utils.HttpUtils;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自选股同步服务程序
 *
 * @author 万籁唤
 *
 */
public class UserStockTBServer {

	private Activity activity;

	private OnTBUpdateListener mOnTBUpdateListener;
	private boolean isExistWrongData = false;//同步下载的自选股是否存在错误数据

	public void setOnStartUpdateUserStockListener(
			OnTBUpdateListener onTBUpdateListener) {
		mOnTBUpdateListener = onTBUpdateListener;
	}

	public interface OnTBUpdateListener {
		public void onTBComplete();

		public void onTBReqHqComplete();
	}

	public UserStockTBServer(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 开始同步下载
	 *
	 * @param onTBUpdateListener
	 */
	public void startTBDownload(boolean isReqHq,
			OnTBUpdateListener onTBUpdateListener) {
		Logger.i("UserStockTBServer", "自选股[同步下载]:服务程序已启动...");

		if (RomaUserAccount.isGuest()){//浏览用户的同步处理
			Logger.i("UserStockTBServer", "自选股[同步下载]:浏览用户，不可同步...");
//			int userStockCount = UserStockSQLMgr.getUserStockCount(activity);
//			if (userStockCount <= 0 && SysConfigs.isAddDefaultStockOnFirst()) {
//				try {
//					insertDefaultZXDataToDB();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			return;
		}
		mOnTBUpdateListener = onTBUpdateListener;
		UserStockHQListener ushql = new UserStockHQListener(activity, "同步下载");
		ushql.setIsReqHq(isReqHq);
//		UserStockReq.reqZXGTBSelect("自选", ushql, "zxgtbSelect", true);
		HQReq.reqStockZXGDown(RomaUserAccount.getUserID(), ushql, "gxs_zxg_tb");
	}

	/**
	 * 开始同步上传
	 *
	 * @param onTBUpdateListener
	 */
	public void startTBUpload(OnTBUpdateListener onTBUpdateListener) {
		Logger.i("UserStockTBServer", "自选股[同步上传]:服务程序已启动...");
		if (RomaUserAccount.isGuest()){//浏览用户的同步处理
			Logger.i("UserStockTBServer", "自选股[同步上传]:浏览用户，不可同步上传...");
			return;
		}
		mOnTBUpdateListener = onTBUpdateListener;
		String[][] result = UserStockSQLMgr.queryAll(activity);
		String stockCodes = "";
		String marketIds = "";
		String favors = "";
		for (int i = 0; i < UserStockSQLMgr.getUserStockCount(activity); i++) {
			stockCodes = result[i][2];
			marketIds = result[i][3];
			if (StringUtils.isEmpty(favors)) {
				favors = marketIds + ":" + stockCodes;
			} else {
				favors += "," + marketIds + ":" + stockCodes;
			}
		}

		if (!StringUtils.isEmpty(favors)) {
			UserStockReq.reqZXGTBAdd/* reqZXGTBUpload */(favors, "自选",
					new UserStockHQListener(activity, "同步上传"), "zxgtbUpload",
					true);
		} else {
			Logger.d("UserStockTBServer", "自选股[同步上传]:本地没有自选股");
			if (mOnTBUpdateListener != null)
				mOnTBUpdateListener.onTBComplete();
		}
	}

	/**
	 * 开始同步上传
	 *
	 * @param onTBUpdateListener
	 */
	public void startTBUpload(String favors,
			OnTBUpdateListener onTBUpdateListener) {
		Logger.i("UserStockTBServer", "自选股[同步上传]:服务程序已启动...");

		if (RomaUserAccount.isGuest()){//浏览用户的同步处理
			Logger.i("UserStockTBServer", "自选股[同步上传]:浏览用户，不可同步上传...");
			return;
		}

		mOnTBUpdateListener = onTBUpdateListener;

		if (!StringUtils.isEmpty(favors)) {
			UserStockReq.reqZXGTBAdd(favors, "自选", new UserStockHQListener(
					activity, "同步上传"), "zxgtbUpload", true);
		}
	}

	private String getDefaultFavors() {

		String favors = "";

		if(RomaSysConfig.defaultStockCode == null)
			return favors;

		int num = RomaSysConfig.defaultStockCode.length;
		String[] codes = RomaSysConfig.defaultStockCode;
		String[] marketIds = RomaSysConfig.defaultMarketCode;

		for (int i = 0; i < num; i++) {
			String stockCode = codes[i];
			String marketId = marketIds[i];
			if (StringUtils.isEmpty(favors)) {
				favors = marketId + ":" + stockCode;
			} else {
				favors += "," + marketId + ":" + stockCode;
			}
		}

		return favors;
	}

	/** 自选行情网络监听者 */
	public class UserStockHQListener extends UINetReceiveListener {
		private Activity activity;
		private String tag;
		private boolean isReqHq;

		public UserStockHQListener(Activity activity, String tag) {
			super(activity);
			this.activity = activity;
			this.tag = tag;
		}

		public void setIsReqHq(boolean isReqHq) {
			this.isReqHq = isReqHq;
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			// 自选股同步下载
			if (ptl instanceof HQZXGTBSelectProtocol) {
				HQZXGTBSelectProtocol hqzxgtb = (HQZXGTBSelectProtocol) ptl;
				if (hqzxgtb.resp_favors != null
						&& !StringUtils.isEmpty(hqzxgtb.resp_favors)) {

					String[] stockArray;

					insertZXDataToDB(hqzxgtb);

					// 同步下载成功解析
					if (isReqHq
							&& (stockArray = HQZXGTBSelectProtocol
									.parseZXGTBFavorsToArray(hqzxgtb)) != null) {
						Logger.i("UserStockTBServer",
								"自选股[同步下载]:同步下载成功![stockCode]:" + stockArray[0]
										+ "[marketId]:" + stockArray[1]);

						String[] dbUserStock = UserStockSQLMgr.queryStockCodes(activity);
						int count = dbUserStock[0].split(",").length;
						Logger.i("UserStockTBServer",
								"自选股[同步下载]:同步下载成功!请求自选股个数:" + count);
						Logger.i("UserStockTBServer",
								"自选股[同步下载]:更新本地数据库自选行情[stockCode]:" + dbUserStock[0]
										+ "[marketId]:" + dbUserStock[1]);
                        //102590为自选股送参的需要数，表示需要服务器送回的参数
						if (!StringUtils.isEmpty(dbUserStock[0])) {
							UserStockReq.req(dbUserStock[0], count,
									(byte) ProtocolConstant.PX_NONE,
									ProtocolConstant.ORDER_NONE, 0,
									dbUserStock[1], ULongUtils.getWholeBitMap(Res.getIngegerArray(R.array.hq_stocklist_protocol_bitmap)),
									new UserStockHQListener(activity, "行情数据获取"), "hq_zx_tb_server");
						}
					}

					// 同步完成进行界面跳转
					if (mOnTBUpdateListener != null)
						mOnTBUpdateListener.onTBComplete();

				} else {
//					if(Res.getBoolean(R.bool.kconfigs_isDeleteLocalUserStock))
//						UserStockSQLMgr.deleteAll(activity);

					if (SysConfigs.isAddDefaultStockOnFirst()) {//只有在添加默认自选股时才将默认自选股同步上传
						// 将默认的进行同步上传
						String favors = getDefaultFavors();
						Logger.i("UserStockTBServer", "自选股[同步下载]:同步下载没数据！默认自选股："
								+ favors);
						if (!StringUtils.isEmpty(favors)) {
							UserStockReq
									.reqZXGTBUpload(favors, "自选",
											new DefaultUserStockHQListener(
													activity, "同步上传"),
											"zxgtbUpload", false);
						}
					} else {
						if (mOnTBUpdateListener != null)
							mOnTBUpdateListener.onTBComplete();
					}

				}
			} else if (ptl instanceof HQZXGTBUploadProtocol) {// 自选股同步上传
				Logger.d("UserStockTBServer", "注册之后，自选股[同步上传]:同步上传自选股");
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER, "isUserStockChange", false);
				// 同步完成进行界面跳转
				if (mOnTBUpdateListener != null)
					mOnTBUpdateListener.onTBComplete();
			} else if (ptl instanceof HQZXGTBAddProtocol) {// 自选股添加
				Logger.d("UserStockTBServer", "注册之后，自选股[同步添加]:同步添加自选股");
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER, "isUserStockChange", false);
				// 同步完成进行界面跳转
				if (mOnTBUpdateListener != null)
					mOnTBUpdateListener.onTBComplete();
			} else if (ptl instanceof HQZXProtocol) {// 自选行情数据
				// 自选行情
				HQZXProtocol hqzx = (HQZXProtocol) ptl;
				if ((hqzx.resp_wCount != 0) && (hqzx.resp_pszCode_s != null)) {
					Logger.i("UserStockTBServer", "自选股[获取行情数据]:行情数据获取成功!");
					insertZXDataToDB(hqzx);
					if (mOnTBUpdateListener != null)
						mOnTBUpdateListener.onTBReqHqComplete();
				} else {
					Logger.i("UserStockTBServer", "自选股[获取行情数据]:没有行情数据!");
				}
			} else if (ptl instanceof HQZXGTBSelectProtocol2) {
				HQZXGTBSelectProtocol2 hqzxgtb = (HQZXGTBSelectProtocol2) ptl;
				if (hqzxgtb.resp_favors != null && !StringUtils.isEmpty(hqzxgtb.resp_favors)) {
					insertZXDataToDB(hqzxgtb);
				}
				if (mOnTBUpdateListener != null)
					mOnTBUpdateListener.onTBComplete();
			}
		}

		@Override
		protected void onConnError(NetMsg msg) {
			// TODO Auto-generated method stub
			super.onConnError(msg);
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
			if (status != SUCCESS) {
				Logger.i("UserStockTBServer", "自选股[同步]:" + tag + "失败...");
				if (mOnTBUpdateListener != null)
					mOnTBUpdateListener.onTBComplete();
			}
		}
	}

	private class DefaultUserStockHQListener extends UINetReceiveListener {
		private Activity activity;
		private String tag;
		private boolean isReqHq;

		public DefaultUserStockHQListener(Activity activity, String tag) {
			super(activity);
			this.activity = activity;
			this.tag = tag;
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			if (ptl instanceof HQZXGTBUploadProtocol) {// 自选股同步上传

				Logger.d("UserStockTBServer",
						"默认自选股[同步上传]:同步上传自选股 serverErrCode:"
								+ ptl.serverErrCode);

				if (ptl.serverErrCode >= 0) {
					try {
						if (SysConfigs.isAddDefaultStockOnFirst()) {
							insertDefaultZXDataToDB();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					if(Logger.getDebugMode())
						RomaToast.showMessage(activity, "自选股同步上传失败！最大可能是服务端出错了！");
				}
				// 同步完成进行界面跳转
				if (mOnTBUpdateListener != null)
					mOnTBUpdateListener.onTBComplete();
			}
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
			if (status != SUCCESS) {
				Logger.i("UserStockTBServer", "自选股[同步]:" + tag + "失败...");
				if (mOnTBUpdateListener != null)
					mOnTBUpdateListener.onTBComplete();
			}
		}
	}

	/**
	 * 请求到有数据，就更新数据库
	 *
	 * @param hqzxgtb
	 */
	public void insertZXDataToDB(HQZXGTBSelectProtocol2 hqzxgtb) {
		if (hqzxgtb.resp_favors == null || StringUtils.isEmpty(hqzxgtb.resp_favors)) {
			return;
		}
		UserStockSQLMgr.deleteAll(activity);
		String favors = hqzxgtb.resp_favors;
		Logger.d("UserStockTBServer", "插入同步下载自选股至数据库：" + favors);
		String[] favors_array = favors.split(",");
		int length = favors_array.length;

		String[] codes_marketId;
		List<String> stockCodeList = new ArrayList<String>();
		List<String> marketList = new ArrayList<String>();
		List<String> stockNameList = new ArrayList<String>();
		List<String> typeList = new ArrayList<String>();
		List<String> groupList = new ArrayList<String>();
		List<String> stockWarningList = new ArrayList<String>();
		List<String> stockMarkList = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			if (favors_array[i] == null || StringUtils.isEmpty(favors_array[i])) {
				return;
			}
			codes_marketId = new String[2];
			if (favors_array[i].contains("sz")) {
				codes_marketId[0] = "1";
				codes_marketId[1] = favors_array[i].replaceAll("sz", "");
			} else if (favors_array[i].contains("sh")) {
				codes_marketId[0] = "2";
				codes_marketId[1] = favors_array[i].replaceAll("sh", "");
			}
			String stockCode = codes_marketId[1];
			String marketId = codes_marketId[0];
			if (StringUtils.isEmpty(stockCode) || StringUtils.isEmpty(marketId)) {
				isExistWrongData = true;
			} else {
				stockCodeList.add(stockCode);
				marketList.add(marketId);
				stockNameList.add("");
				typeList.add("");
				groupList.add("");
				stockWarningList.add("");
				stockMarkList.add("");
			}
		}
		UserStockSQLMgr.insertData(activity, stockCodeList, stockCodeList, marketList, typeList, "", "", stockMarkList);
		Logger.d("zxgtb", "添加到数据库成功");
		if (isExistWrongData) {
			String[][] userStocks = UserStockSQLMgr.queryAll(activity);
			int len = userStocks.length;
			String str = "";
			for (int i = 0; i < userStocks.length; i++) {
				String stockCode = userStocks[i][2];
				String marketId = userStocks[i][3];
				if ("1".equals(marketId)) {
					stockCode = "sz" + stockCode;
				} else if ("2".equals(marketId)) {
					stockCode = "sh" + stockCode;
				}
				str += stockCode + ",";
			}
			if (!StringUtils.isEmpty(str)) {
				str = str.substring(0, str.length() - 1);
			}
			Logger.d("UserStockTBServer", "上传自选股去除脏数据：" + str);
			reqZXGTBUpload(str);
		}
	}

	private void reqZXGTBUpload(String codes) {
		Logger.d("UserStockTBServer", "codes = " + codes);
		BaseJSONObject jsonObject = new BaseJSONObject();

		jsonObject.put("user_id", RomaUserAccount.getUserID());
		jsonObject.put("code", codes);
		String xy = "";
		try {
			DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
			xy = DES3.encode(jsonObject.toString());
			xy = DES3.encodeToHex(xy);
			xy = xy.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//参数
		final Map<String,String> params = new HashMap<String,String>();
		params.put("xy", xy);
		params.put("iv", DES3.getIv());
		new Thread(new Runnable() {
			@Override
			public void run() {
				String ip = "";
				if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
					ip = RomaSysConfig.getIp();
				} else {
					ip = Res.getString(R.string.NetWork_IP);
				}
				String result = HttpUtils.submitPostData(ip+ "SelfStock/add", params, "utf-8");
				Logger.d("UserStockTBServer", "result = " + result);
				if (!StringUtils.isEmpty(result)) {
					try {
						BaseJSONObject jsonObject1 = new BaseJSONObject(result);
						String errorCode = "";
						if (jsonObject1.has("error")) {
							errorCode = jsonObject1.getString("error");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 请求到有数据，就更新数据库
	 *
	 * @param hqzxgtb
	 */
	public void insertZXDataToDB(HQZXGTBSelectProtocol hqzxgtb) {
		// [需求]是否切换账号后不删除本地自选股, true删除, false仅做合并, 如是删除也需在客户端第一次用户登录时，将本地自选股与用户的自选股同步(PS: 先存储用户名/手机号码, 后同步自选股判断, 故需以大于1作为判断).
//		if(Res.getBoolean(R.bool.kconfigs_isDeleteLocalUserStock) && SharedPreferenceUtils.getPreference(RomaUserAccount.pName, RomaUserAccount.mobileLoginNumber, 0) > 1)
//			UserStockSQLMgr.deleteAll(activity);

		if (hqzxgtb.resp_favors == null
				|| StringUtils.isEmpty(hqzxgtb.resp_favors)) {
			return;
		}
		String favors = hqzxgtb.resp_favors;
		Logger.d("UserStockTBServer", "插入同步下载自选股至数据库：" + favors);
		String[] favors_array = favors.split(",");
		int length = favors_array.length;

		String[] codes_marketId;
		/*String[] codes = new String[length];
		String[] marketIds = new String[length];
		String[] stockNames = new String[length];
		String[] type = new String[length];
		String[] groupName = new String[length];
		String[] stockWarning = new String[length];
		String[] stockMark = new String[length];*/

		List<String> stockCodeList = new ArrayList<String>();
		List<String> marketList = new ArrayList<String>();
		List<String> stockNameList = new ArrayList<String>();
		List<String> typeList = new ArrayList<String>();
		List<String> groupList = new ArrayList<String>();
		List<String> stockWarningList = new ArrayList<String>();
		List<String> stockMarkList = new ArrayList<String>();

		for (int i = 0; i < length; i++) {
			if (favors_array[i] == null || StringUtils.isEmpty(favors_array[i])) {
				return;
			}
			codes_marketId = favors_array[i].split(":");

			if (codes_marketId.length < 2)
				continue;

			String stockCode = codes_marketId[1];
			String marketId = codes_marketId[0];
			if (StringUtils.isEmpty(stockCode) || StringUtils.isEmpty(marketId)) {
				isExistWrongData = true;
			} else {
				stockCodeList.add(stockCode);
				marketList.add(marketId);
				stockNameList.add("");
				typeList.add("");
				groupList.add("");
				stockWarningList.add("");
				stockMarkList.add("");
			}

			/*codes[i] = codes_marketId[1];
			marketIds[i] = codes_marketId[0];
			stockNames[i] = "";
			type[i] = "";
			groupName[i] = "";
			stockWarning[i] = "";
			stockMark[i] = "";*/
		}
//		UserStockSQLMgr.insertData(activity, stockNames, codes, marketIds, type, groupName, stockWarning, stockMark);
		UserStockSQLMgr.insertData(activity, stockCodeList, stockCodeList, marketList, typeList, "", "", stockMarkList);
		Logger.d("zxgtb", "添加到数据库成功");
		if (isExistWrongData) {
			String[][] userStocks = UserStockSQLMgr.queryAll(activity);
			int len = userStocks.length;
			String str = "";
			for (int i = 0; i < userStocks.length; i++) {
				String stockCode = userStocks[i][2];
				String marketId = userStocks[i][3];
				str += marketId + ":" + stockCode + ",";
			}
			if (!StringUtils.isEmpty(str)) {
				str = str.substring(0, str.length() - 1);
			}
			Logger.d("UserStockTBServer", "上传自选股去除脏数据：" + str);
			UserStockReq.reqZXGTBUpload(str, "自选", new UploadListener(activity), "zxgtbUpload", false);
		}
	}

	private class UploadListener extends UINetReceiveListener{

		public UploadListener(Activity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			// TODO Auto-generated method stub
			super.onSuccess(msg, ptl);
			if (ptl.serverErrCode >= 0) {
				Logger.d("UserStockTBServer", "自选股去除脏数据后上传成功");
			} else {
				if(Logger.getDebugMode())
					RomaToast.showMessage(activity, "自选股同步上传失败！最大可能是服务端出错了！");
			}
		}

	}

	/**
	 * 请求到有数据，就更新数据库
	 *
	 */

	public synchronized void insertDefaultZXDataToDB() throws Exception {

		//先清除一下数据库所有，以防止同步上传失败后，下次进入时会重复添加。
		UserStockSQLMgr.deleteAll(activity);
		SysConfigs.setAddDefaultStockOnFirst(false, true);

		int num = RomaSysConfig.defaultStockCode.length;
		String[] codes = RomaSysConfig.defaultStockCode;
		String[] marketIds = RomaSysConfig.defaultMarketCode;
		String[] stockNames = new String[num];
		String[] type = new String[num];
		String[] groupName = new String[num];
		String[] stockWarning = new String[num];
		String[] stockMark = new String[num];

		String stockCode = "";
		for (int i = 0; i < num; i++) {
			stockNames[i] = "";
			type[i] = "";
			groupName[i] = "";
			stockWarning[i] = "";
			stockCode += ("," + codes[i]);
			stockMark[i] = "";
		}
		Logger.i("UserStockTBServer", "自选股[同步下载]:插入默认自选股,代码：" + stockCode);
		UserStockSQLMgr.insertData(activity, stockNames, codes, marketIds,
				type, groupName, stockWarning, stockMark);
		Logger.d("zxgtb", "添加到数据库成功");
	}

	/**
	 * 将行情请求数据添加到数据库里
	 *
	 * @param hqzx
	 */
	public void insertZXDataToDB(HQZXProtocol hqzx) {
		UserStockSQLMgr.deleteAll(activity);

		String[] marketId = new String[hqzx.resp_wCount];
		String[] type = new String[hqzx.resp_wCount];
		String[] groupName = new String[hqzx.resp_wCount];
		String[] stockWarning = new String[hqzx.resp_wCount];
		String[] stockMark = new String[hqzx.resp_wCount];
		for (int i = 0; i < hqzx.resp_wCount; i++) {
			groupName[i] = "自选";
			stockWarning[i] = "0";
			marketId[i] = Integer.toString(hqzx.resp_wMarketID_s[i]);
			type[i] = Integer.toString(hqzx.resp_wType_s[i]);
			stockMark[i] = hqzx.resp_pszMark_s[i];
		}
		// 添加到数据库
		UserStockSQLMgr.insertData(activity, hqzx.resp_pszName_s,
				hqzx.resp_pszCode_s, marketId, type, groupName, stockWarning, stockMark);
	}

	/** 查询数据库自选股, 请求获取详细股票数据 */
	public void queryUserStockDetailInfo(){
		String[] dbUserStock = UserStockSQLMgr.queryStockCodes(activity);
		if(dbUserStock != null && dbUserStock.length > 0) {
			int count = dbUserStock[0].split(",").length;
			 //102590为自选股送参的需要数，表示需要服务器送回的参数
			if (!StringUtils.isEmpty(dbUserStock[0])) {
				UserStockReq.req(dbUserStock[0], count,
						(byte) ProtocolConstant.PX_NONE,
						ProtocolConstant.ORDER_NONE, 0,
						dbUserStock[1], ULongUtils.getWholeBitMap(Res.getIngegerArray(R.array.hq_stocklist_protocol_bitmap)),
						new UserStockHQListener(activity, "行情数据获取"), "hq_zx_tb_server");
			}
		}
	}

	public synchronized void insertZXDataToDB() throws Exception {

		int num = RomaSysConfig.defaultStockCode.length;
		String[] codes = RomaSysConfig.defaultStockCode;
		String[] marketIds = RomaSysConfig.defaultMarketCode;
		String[] stockNames = new String[num];
		String[] type = new String[num];
		String[] groupName = new String[num];
		String[] stockWarning = new String[num];
		String[] stockMark = new String[num];

		for (int i = 0; i < num; i++) {
			stockNames[i] = "";
			type[i] = "";
			groupName[i] = "";
			stockWarning[i] = "";
			stockMark[i] = "";
		}
		UserStockSQLMgr.insertData(activity, stockNames, codes, marketIds,
				type, groupName, stockWarning, stockMark);
		Logger.d("zxgtb", "添加到数据库成功");
	}

}
