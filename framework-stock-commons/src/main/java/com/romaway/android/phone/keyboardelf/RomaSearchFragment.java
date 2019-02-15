package com.romaway.android.phone.keyboardelf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romaway.activity.basephone.ActivityStackMgr;
import com.romaway.activity.basephone.BaseSherlockFragment;
import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.SuperUserAdminActivity;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.netreq.HQReq;
import com.romaway.android.phone.utils.DensityUtil;
import com.romaway.android.phone.utils.DrawableUtils;
import com.romaway.android.phone.utils.RegexpUtils;
import com.romaway.android.phone.utils.StockCacheInfo;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.android.phone.R;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol;
import com.romaway.common.protocol.hq.HQNewCodeListProtocol2;
import com.romaway.common.protocol.hq.HQNewStockProtocol;
import com.romaway.common.protocol.hq.HQZXGTBAddProtocol;
import com.romaway.common.protocol.service.ANetReceiveUIListener;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.utils.HttpUtils;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.framework.view.SysInfo;
import com.szkingdom.stocksearch.keyboard.KDS_KeyboardManager;
import com.szkingdom.stocksearch.keyboard.Kds_KeyBoardView;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;
import com.trevorpage.tpsvg.SvgRes1;
import com.romawaylibs.picasso.PicassoHelper;
import com.romawaylibs.theme.ROMA_SkinManager;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Timer;
import java.util.TimerTask;

import roma.romaway.commons.android.theme.SkinManager;
import roma.romaway.commons.android.theme.svg.SVGManager;
import roma.romaway.commons.android.webkit.keyboard.ZXJT_KeyboardManager;

/**
 * 键盘精灵搜索界面
 */
public class RomaSearchFragment extends BaseSherlockFragment implements OnItemClickListener, OnTouchListener{

	private KeyboardElfDBUtil dbutil;
	private LayoutInflater mInflater;
	public KeyboardElfAdapter adapter;
	public String[][] datas = new String[0][0];
	private EditText edt_search;
	private SVGView svg_search_icon;
	private ListView lv_search_list;
	private TextView txt_search_cancle, txt_search_clear;
	private LinearLayout ll_roma_search, ll_search_top, ll_search_head;
	private boolean isSwitch = false;
	private boolean isTbzxg = true;
	private boolean isSerachGg = false;
	private View footerView;
	private String searchStock_clearBgColor;

	private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            
            switch(msg.what){
            case 0:
            	if((datas == null || datas.length == 0) && searchType==0){
                	SysInfo.showMessage(mActivity, "您输入的股票不存在");
                }
                adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
            	break;
            case 1:
            	if(datas != null && datas.length == 1 && adapter.getVisible()){
                	if (!isSwitch) {
    	            	try{
//    	            		if (Res.getBoolean(R.bool.kconfigs_is_goto_DataInfo)) {
//    	            			goToStockInfo(0);
//							}
    	            	}catch(Exception e){
    	        			
    	        		}
                		isSwitch = true;
    				}
                }
            	break;
            }
        }
	};

	// =============== 自定义键盘接口 ================
	private RelativeLayout rl_parent;
	private FrameLayout fr_keyboard_parent;
	private Kds_KeyBoardView kdsKeyBoardView;
	private KDS_KeyboardManager keyboardManager;
	private View mUnderDevier;
	private View mUpDevier;

	private void initKeyBoard() {
		if (kdsKeyBoardView == null) {
			kdsKeyBoardView = new Kds_KeyBoardView(getActivity());
			RelativeLayout.LayoutParams lp =
					new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT,
							RelativeLayout.LayoutParams.WRAP_CONTENT);
			kdsKeyBoardView.setLayoutParams(lp);
			kdsKeyBoardView.setVisibility(View.GONE);
			fr_keyboard_parent.addView(kdsKeyBoardView);
		}

		if (keyboardManager == null) {
			keyboardManager = new ZXJT_KeyboardManager(getActivity(), rl_parent, kdsKeyBoardView);
		}
	}
	// =============== 自定义键盘接口 ================

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		return inflater.inflate(R.layout.roma_search_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ll_roma_search=(LinearLayout) view.findViewById(R.id.ll_roma_search);
		ll_roma_search.setBackgroundColor(SkinManager.getColor("JPJLColor"));
		dbutil = new KeyboardElfDBUtil(mActivity);
		adapter = new KeyboardElfAdapter(mActivity, datas);
		ll_search_top=(LinearLayout) view.findViewById(R.id.ll_search_top);
		ll_search_top.setBackgroundColor(SkinManager.getColor("JPJLTopColor"));
		ll_search_head=(LinearLayout) view.findViewById(R.id.ll_search_head);
		ll_search_head.setBackgroundColor(SkinManager.getColor("JPJLTopColor"));
		if(Res.getBoolean(R.bool.is_use_2_0_keyboard)) {
			fr_keyboard_parent = (FrameLayout) view.findViewById(R.id.fl_keyboard_parent);
			rl_parent = (RelativeLayout) view.findViewById(R.id.rl_parent);
		}
		/** 设置键盘精灵顶部编辑框 */
		int mCorner = Res.getInteger(R.integer.keyboardElf_inputboxBg_corner);
//		BorderShapeDrawable mBorderShapeDrawable = new BorderShapeDrawable(new RoundRectShape(
//				new float[]{mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner}, null, null) ,
//				SkinManager.getColor("SearchKuangColor"), AutoUtils.getPercentWidthSizeBigger(3));
//		mBorderShapeDrawable.setCanvasColor(SkinManager.getColor("JPJLSeachInputBoxBgColor"));
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(ROMA_SkinManager.getColor("JPJLSeachInputBoxBgColor",0xffFFFFFF));
		gd.setCornerRadius(mCorner);
		gd.setStroke(AutoUtils.getPercentWidthSizeBigger(2), ROMA_SkinManager.getColor("SearchKuangColor",0xffe6e9f1));
		ll_search_head.setBackgroundDrawable(gd);
		svg_search_icon = (SVGView) view.findViewById(R.id.svg_search_icon);
		svg_search_icon.setSVGRenderer(SVGManager.getSVGParserRenderer(mActivity, "roma_common_search_btn_svg"), null);
		edt_search = (EditText) view.findViewById(R.id.edt_search);
		edt_search.setHintTextColor(ROMA_SkinManager.getColor("SearchHintTextColor", 0xff7e7e7e));
		edt_search.setTextColor(ROMA_SkinManager.getColor("SearchTextColor", 0xffffffff));
//		edt_search.setHintTextColor(SkinManager.getColor("SearchHintTextColor"));
//		edt_search.setBackgroundColor(SkinManager.getColor("JPJLSeachInputBoxBgColor"));
//		BorderShapeDrawable mBorderShapeDrawable2 = new BorderShapeDrawable(new RoundRectShape(
//				new float[]{mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner}, null, null) ,
//				SkinManager.getColor("JPJLSeachInputBoxBgColor"), 2.0f);
//		edt_search.setBackgroundDrawable(mBorderShapeDrawable2);
		if (Res.getBoolean(R.bool.roma_UserBack_edit_isSelf)) {
			GradientDrawable drawable = (GradientDrawable) getActivity().getResources().getDrawable(R.drawable.roma_search_bg);
			drawable.setColor(SkinManager.getColor("JPJLSeachInputBoxBgColor"));
			drawable.setStroke(DensityUtil.dip2px(getActivity(), 1), SkinManager.getColor("JPJLSeachInputBoxBgColor"));
			edt_search.setBackgroundDrawable(drawable);
		}
		//判断是否需要使用键盘宝2.0
		if(Res.getBoolean(R.bool.is_use_2_0_keyboard)){
			//绑定默认的数字切换字母和中文的搜索键盘
			initKeyBoard();
			keyboardManager.bundingKdsKeyboardAndLoad(edt_search,
					R.xml.kds_stock_keyboard_number_for_switch_system, null);// 绑定自定义键盘
		}else {
			if (!Res.getBoolean(R.bool.JPJL_is_use_system_keyboard)) {
				//绑定默认的数字切换字母键盘
				mActionBar.bundingRomaKeyboardAndLoad(edt_search, -1, null);
			} else {
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					               @SuppressLint("NewApi")
					               public void run() {
						               InputMethodManager inputManager =
								               (InputMethodManager) edt_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
						               inputManager.showSoftInput(edt_search, 0);
					               }
				               },
						500);
			}
		}

		txt_search_cancle = (TextView) view
				.findViewById(R.id.txt_search_cancle);
		lv_search_list = (ListView) view.findViewById(R.id.lv_search_list);
		lv_search_list.setBackgroundColor(SkinManager.getColor("JPJLColor"));
		if (!SkinManager.getCurSkinType().equals("BLACK_SKIN")) {
			lv_search_list.setDivider(getResources().getDrawable(R.color.abs__background_holo_light));
		}
		footerView = mInflater.inflate(R.layout.roma_search_footer_layout, null,false);
		footerView.setBackgroundColor(SkinManager.getColor("SearchFootColor"));
		txt_search_clear = (TextView) footerView.findViewById(R.id.txt_footer);
		txt_search_clear.setTextColor(SkinManager.getColor("SearchClearTextColor"));
		mUnderDevier = (View) footerView.findViewById(R.id.v_under_txt_footer_devider);
		mUpDevier = (View) footerView.findViewById(R.id.v_up_txt_footer_devider);
		mUnderDevier.setBackgroundColor(SkinManager.getColor("v_up_txt_footer_deviderColor"));
		mUpDevier.setBackgroundColor(SkinManager.getColor("v_up_txt_footer_deviderColor"));

		SVGView mSVGView = ((SVGView)footerView.findViewById(R.id.svg_search_clear));
		if(mSVGView != null) {
			mSVGView.setSVGRenderer(SVGManager.getSVGParserRenderer(mActivity, "roma_search_clear_history", R.drawable.roma_search_clear_history), null);
//			mSVGView.setSVGRenderer(OtherPageConfigsManager.getInstance().getSVGParserRenderer(
//					mActivity, "roma_search_clear_history", searchStock_clearBgColor), "");
		}

//		if (SkinManager.getCurSkinType().equals("WHITE_SKIN")) {
//			footerView.setBackgroundColor(Color.WHITE);
//			txt_search_clear.setTextColor(Color.BLACK);
//		}
		footerView.setVisibility(View.GONE);
		lv_search_list.addFooterView(footerView);
		lv_search_list.setAdapter(adapter);

		lv_search_list.setOnItemClickListener(this);
		lv_search_list.setOnTouchListener(this);
		/**
		 * 是否更新键盘精灵数据库
		 */
		if (isTbzxg()) {
			reqKeyboardElf();
		}
		
		//添加文本监听器
		addTextChangedListener(0);
		
		txt_search_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取消
				backHomeCallBack();
				mActionBar.showBottomBar();
			}
		});
		txt_search_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 清除搜索记录列表数据
				clearListView();

				// 清除搜索记数据库表
			}
		});
		getHistoryList();
	}
	
	private SearchRunnable mSearchRunnable;
	class SearchRunnable implements Runnable{
		private String py;
		public SearchRunnable(){
			
		}
		
		public void setPy(String py){
			this.py = py;
		}
		
		@Override
		public void run() {
			try {// 捕获快速请求，数据未到位崩溃bug
				dbutil.setIsSearchHSAgu(true);
				if (isSerachGg) {
					datas = dbutil.searchGangGu(py);
				} else {
					datas = dbutil.search(py);
				}
                if (datas == null || datas.length == 0) {
//                	if (py.length() == 5 || py.length() == 6 || py.length() == 8) {
//                		if (!py.equalsIgnoreCase("kds88") && !py.equalsIgnoreCase("kds888")) {
//                			reqSearchStock(py.replace(" ",""));
//						}
//					}
					if (py.length() == 6) {
						if ((!py.equalsIgnoreCase("kds88") && !py.equalsIgnoreCase("kds888")) && ("0".equalsIgnoreCase(py.substring(0, 1)) || "3".equalsIgnoreCase(py.substring(0, 1)) || "6".equalsIgnoreCase(py.substring(0, 1)))) {
							reqSearchStock(py.replace(" ",""));
						}
					}
				}
            } catch (Exception e) {

            }
            
            if (py.equalsIgnoreCase("roma888")) {
                KActivityMgr.switchWindow((ISubTabView) mActivity,
                        SuperUserAdminActivity.class, false);

			// [Bug]修复基金以及港股指数存在stockCode带有字母的情况:
            }/* else if (searchType == 1 && py.length() >= 5 && RegexpUtils.isValidStockCode(py)
                    && (datas == null || datas.length == 0)) {
                // 0:股票代码,1:股票名称,2:拼音,3:股票类型,4:市场代码
				// [Bug]传递给交易 H5 市场代码建议要求为空或 0, 不能为 -1 ;
                datas = new String[][] { { py, "", "", "0", "0", ""} };
            }*/
            //mHandler.sendEmptyMessage(0);
            mHandler.sendEmptyMessage(0);
            mHandler.removeMessages(1);
            if( datas != null && datas.length == 1 && adapter.getVisible() && searchType != 1)
            	mHandler.sendEmptyMessageDelayed(1, 300);
		}
	}
	
	/**0：代表正常的行情搜索；1：代表交易下单流程搜索*/
	private int searchType;
	/**
	 * 添加文本监听器
	 * @param type  0：代表正常的行情搜索；1：代表交易下单流程搜索
	 */
	public void addTextChangedListener(final int type){
		this.searchType = type;
		
		edt_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				//隐藏清空按钮
				//txt_search_clear.setVisibility(View.GONE);
				// 查询数据库，更新列表显示数据
				final String py = s.toString();
				
				if (py.length() > 0 && py.length() <= 8) { 
					
					new Thread(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
							Object lock = new Object();  
				            synchronized (lock){
								if(mSearchRunnable == null)
									mSearchRunnable = new SearchRunnable();
								
								mSearchRunnable.setPy(py);
								edt_search.removeCallbacks(mSearchRunnable);
								edt_search.postDelayed(mSearchRunnable, 200);
				            }
						}
					}.start();
					
				    if(datas != null && datas.length >0){
						footerView.setVisibility(View.GONE);
                    } else {
//						reqSearchStock(py);
					}
				}else{
					//txt_search_clear.setVisibility(View.VISIBLE);
					getHistoryList();
				}
			}
		});
	}
	
	/** 请求键盘精灵数据 */
	private void reqKeyboardElf() {
		KeyboardElfContorl.req(ProtocolConstant.SE_SS,
				new KeyboardElfNetListener(getActivity()), "keyboardelf_hq");
//		HQReq.reqStockCodeData(new KeyboardElfNetListener2(getActivity()), "keyboardelf_hq2");
	}
	
	private class KeyboardElfNetListener extends ANetReceiveUIListener {
		public KeyboardElfNetListener(Activity activity) {
			super(activity);
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			HQNewCodeListProtocol nclp = (HQNewCodeListProtocol) ptl;
			try{
				KeyboardElfContorl.parseProtocol(getActivity(), nclp);
				setTbzxg(false);
			}catch(Exception e){
				
			}
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
			if (status != SUCCESS) {
				
			}
		}
	}

	private class KeyboardElfNetListener2 extends ANetReceiveUIListener {
		public KeyboardElfNetListener2(Activity activity) {
			super(activity);
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			HQNewCodeListProtocol2 nclp = (HQNewCodeListProtocol2) ptl;
			try{
				KeyboardElfContorl.parseProtocol(getActivity(), nclp);
				setTbzxg(false);
			}catch(Exception e){

			}
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
			if (status != SUCCESS) {

			}
		}
	}

	/**
	 * 插入数据库
	 * @throws Exception
	 */
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
		UserStockSQLMgr.insertData(getActivity(), stockNames, codes, marketIds, type,
				groupName, stockWarning, stockMark);
		Logger.d("zxgtb", "添加到数据库成功");
	}

	@Override
	public void onResumeInit() {
		// TODO Auto-generated method stub
		super.onResumeInit();
		mActionBar.hide();
		//隐藏输入法的隐藏键盘按钮
		setRomaKeyboardHideButtonVisibility(View.GONE);
	}

	@SuppressLint({ "InlinedApi", "NewApi" })
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try{
			InputMethodManager im = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(edt_search.getWindowToken(), 0);
			goToStockInfo(position);
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 跳转到个股详情界面
	 * @param position
	 */
	private void goToStockInfo(final int position){
		
		//保存历史记录到数据库
		addStockToSearchHistory(datas[position][0], datas[position][1], datas[position][3], datas[position][4], datas[position][5]);
		
//		Bundle bundle = mActivity.getIntent().getExtras();
//		if (bundle == null ) {
//			bundle = new Bundle();
//		}
//
//		// 跳转到分时K线界面
//		bundle.putString(BundleKeyValue.HQ_STOCKNAME,
//				datas[position][1]);
//		bundle.putString(BundleKeyValue.HQ_STOCKCODE,
//				datas[position][0]);
//		bundle.putShort(BundleKeyValue.HQ_MARKETID,
//				(short) NumberUtils.toInt(datas[position][4]));
//		bundle.putShort(BundleKeyValue.HQ_STOCKTYPE,
//				(short) NumberUtils.toInt(datas[position][3]));
//		bundle.putInt("HQ_POSITION", position);
//		StockCacheInfo.clearCacheList();
//		StockCacheInfo.saveListToCache(datas, new int[] { 1, 0, 4, 3});
		
		//先将上一个个股详情finish掉,
		Activity activity = (Activity) ActivityStackMgr.getActivityHistoryTopStack();
		String className = activity.getLocalClassName();
//		if(className != null && className.
//		        equals("kds.szkingdom.android.phone.activity.hq.HQStockDataInfoFragmentActivity"))
//		    activity.finish();
//
//		KActivityMgr.switchWindow((ISubTabView) this.getActivity(),
//				"kds.szkingdom.android.phone.activity.hq.HQStockDataInfoFragmentActivity",
//				bundle, true);
		String marketId = datas[position][4];
		String stockCode = datas[position][0];
		String stockName = datas[position][1];
		if (!StringUtils.isEmpty(marketId)) {
			if ("1".equals(marketId)) {
				stockCode = "sz" + stockCode;
			} else if ("2".equals(marketId)) {
				stockCode = "sh" + stockCode;
			}
		}
		Bundle bundle = new Bundle();
		bundle.putString("stockName", stockName);
		bundle.putString("stockCode", stockCode);
		bundle.putString("type", "2");
		KActivityMgr.switchWindow((ISubTabView) this.getActivity(), "roma.romaway.homepage.android.phone.StockDetailActivityNewNew", bundle, false);

	}

	/** 添加浏览的自选股到搜索历史 */
	private void addStockToSearchHistory(String stockCode, String stockName, String codeType, String marketId, String stockMark) {
		// 添加浏览的自选股到搜索历史:
		if(RomaSearchHistorySQLMgr.getCount(mActivity) > 10){
			RomaSearchHistorySQLMgr.deleteFirst(mActivity);
		}
		RomaSearchHistorySQLMgr.insertData(mActivity, stockCode, stockName, codeType, marketId, stockMark);
	}

	private void getHistoryList(){
		edt_search.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				datas = RomaSearchHistorySQLMgr.queryAll(mActivity);
				if (isSerachGg) {
					datas = null;
				}
				if(datas != null){
					adapter.setDatas(datas);
					adapter.notifyDataSetChanged();
					footerView.setVisibility(View.VISIBLE);
				}else {
					footerView.setVisibility(View.GONE);
				}
			}
		}, 300);
		
	}

	/**
	 * 清空搜索记录列表
	 */
	private void clearListView() {
		RomaSearchHistorySQLMgr.deleteAll(mActivity);
		footerView.setVisibility(View.GONE);
		datas = new String[0][0];
		adapter.setDatas(datas);
		adapter.notifyDataSetChanged();

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mActionBar.hideKeyboard();
		if(keyboardManager!=null){
			keyboardManager.hideKeyboard();
		}
		return false;
	}

	class KeyboardElfAdapter extends BaseAdapter {

		private String[][] datas;
		private Context mContext;
		private boolean isVisible = true;
		ShapeDrawable mHKMarkDrawable;
		ShapeDrawable mRZRQMarkDrawable;
		SVGParserRenderer unAddRenderer;
		SVGParserRenderer addedRenderer;
		int stockNameColor;
		int stockCodeColor;
		int geguDetail_topDataText_defaultColor;
		boolean is_show_HK_Or_RZRQ;
		private String[] additionalTexts = Res.getStringArray(R.array.key_search_stock_code_additional_texts);

		public KeyboardElfAdapter(Context context, String[][] datas) {
			this.datas = datas;
			mContext = context;
			unAddRenderer = SvgRes1.getSVGParserRenderer(mContext, R.drawable.roma_search_add);
			addedRenderer = SvgRes1.getSVGParserRenderer(mContext, R.drawable.roma_search_added);
			// 港股, 融资融券标识:
			mHKMarkDrawable = DrawableUtils.getShapeDrawable(SkinManager.getColor("HqHKMarkColor"), Res.getInteger(R.integer.hq_stockType_mark_bg_corner));
			mRZRQMarkDrawable = DrawableUtils.getShapeDrawable(SkinManager.getColor("HqRZRQMarkColor"), Res.getInteger(R.integer.hq_stockType_mark_bg_corner));
			stockNameColor = SkinManager.getColor("hqMode_stockName_textcolor");
			searchStock_clearBgColor = ROMA_SkinManager.getColor("hqMode_searchStock_clearBgColor", null);
			stockCodeColor = SkinManager.getColor("hqMode_stockNameCode_textcolor");
			geguDetail_topDataText_defaultColor = SkinManager.getColor("geguDetail_topDataText_defaultColor");
			is_show_HK_Or_RZRQ = Res.getBoolean(R.bool.keyboardelf_is_show_stock_mark);
		}

		public void setDatas(String[][] datas) {
			this.datas = datas;
		}

		@Override
		public int getCount() {
			return datas == null ? 0 : datas.length /*<= 10 ? datas.length : 10*/;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		public void setAddVisible(boolean visible){
			isVisible = visible;
		}
		
		public boolean getVisible(){
			return isVisible;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				LinearLayout ll = (LinearLayout) mInflater.inflate(
						R.layout.roma_search_list_item_layout, parent,
						false);
				viewHolder.txt_stockMark = (TextView) ll.findViewById(R.id.txt_stockMark);
				viewHolder.img_stockMark = (ImageView) ll.findViewById(R.id.img_stockMark);
				viewHolder.ll_add = (LinearLayout) ll.findViewById(R.id.ll_add);
				viewHolder.txt_stockName = (TextView) ll
						.findViewById(R.id.txt_stockName);
				viewHolder.txt_stockCode = (TextView) ll
						.findViewById(R.id.txt_stockCode);
				viewHolder.txt_add_flag = (TextView) ll
						.findViewById(R.id.txt_add_flag);
				viewHolder.simg_add = (SVGView) ll
						.findViewById(R.id.simg_add);
				viewHolder.onClickListener = new AddOnClickListener();
				convertView = ll;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (is_show_HK_Or_RZRQ) {
//				viewHolder.txt_stockMark.setVisibility(View.VISIBLE);
				String codeMark = datas[position][5];
				if (!StringUtils.isEmpty(codeMark)) {
					if(Res.getBoolean(R.bool.is_roma_stock_mark_use_img)) {
						viewHolder.img_stockMark.setVisibility(View.VISIBLE);
						viewHolder.txt_stockMark.setVisibility(View.GONE);
						if ("HK".equalsIgnoreCase(codeMark)) {
							PicassoHelper.load(mContext, viewHolder.img_stockMark, R.drawable.roma_stock_mark_hk);
						} else if ("SGT".equalsIgnoreCase(codeMark)) {
							PicassoHelper.load(mContext, viewHolder.img_stockMark, R.drawable.roma_stock_mark_sgt);
						} else if ("HGT".equalsIgnoreCase(codeMark)) {
							PicassoHelper.load(mContext, viewHolder.img_stockMark, R.drawable.roma_stock_mark_hgt);
						} else if ("R".equalsIgnoreCase(codeMark)) {
							PicassoHelper.load(mContext, viewHolder.img_stockMark, R.drawable.roma_stock_mark_r);
						}else{
							viewHolder.img_stockMark.setVisibility(View.GONE);
							viewHolder.txt_stockMark.setVisibility(View.INVISIBLE);
						}
					}else {
						viewHolder.img_stockMark.setVisibility(View.GONE);
						viewHolder.txt_stockMark.setVisibility(View.INVISIBLE);
						if (codeMark.equals("HK")) {// 港股
							viewHolder.txt_stockMark.setVisibility(View.VISIBLE);
							viewHolder.txt_stockMark.setText("HK");
							viewHolder.txt_stockMark.setBackgroundDrawable(mHKMarkDrawable);
						} else if (codeMark.equals("R")) {// 融资融券
							viewHolder.txt_stockMark.setVisibility(View.VISIBLE);
							viewHolder.txt_stockMark.setText("R");
							viewHolder.txt_stockMark.setBackgroundDrawable(mRZRQMarkDrawable);
						} else {
							viewHolder.txt_stockMark.setVisibility(View.INVISIBLE);
						}
					}
				} else {
					viewHolder.img_stockMark.setVisibility(View.GONE);
					viewHolder.txt_stockMark.setVisibility(View.INVISIBLE);
				}
			} else {
				viewHolder.img_stockMark.setVisibility(View.GONE);
				viewHolder.txt_stockMark.setVisibility(View.INVISIBLE);
			}
			viewHolder.txt_stockCode.setText(additionalTexts[0]+datas[position][0]+additionalTexts[1]);
			viewHolder.txt_stockName.setTextSize(TypedValue.COMPLEX_UNIT_PX, Res.getDimen(R.dimen.roma_hq_search_stock_name_size));
			viewHolder.txt_stockName.setText(datas[position][1]);
				viewHolder.txt_stockCode.setTextColor(stockCodeColor);
				viewHolder.txt_stockName.setTextColor(stockNameColor);
				viewHolder.txt_add_flag.setTextColor(geguDetail_topDataText_defaultColor);
			viewHolder.simg_add.setSVGRenderer(unAddRenderer, null);
			viewHolder.simg_add.setVisibility(View.VISIBLE);

			if (StringUtils.isEmpty(datas[position][1])) {
				// 不存在的代码，名称为空，则不显示可添加
				viewHolder.txt_add_flag.setVisibility(View.GONE);
				viewHolder.simg_add.setVisibility(View.GONE);
			} else {
				// 是否存在于自选中
				boolean isMyStock = false;
				isMyStock = UserStockSQLMgr.isExistStock(mContext, datas[position][0], datas[position][4]);
				if (isMyStock) {// 存在于自选股中
					if(Res.getBoolean(R.bool.keyboardelf_isSupport_showIconAsStatus)){	// 是否支持仅用图标标识加/已自选状态;
						viewHolder.txt_add_flag.setVisibility(View.GONE);
						viewHolder.simg_add.setVisibility(View.VISIBLE);
						viewHolder.simg_add.setSVGRenderer(addedRenderer, null);
					}else{
						viewHolder.txt_add_flag.setVisibility(View.VISIBLE);
						viewHolder.simg_add.setVisibility(View.GONE);
					}
				} else {
					if(Res.getBoolean(R.bool.keyboardelf_isSupport_showIconAsStatus)){	// 是否支持仅用图标标识加/已自选状态;
						viewHolder.txt_add_flag.setVisibility(View.GONE);
						viewHolder.simg_add.setVisibility(View.VISIBLE);
						viewHolder.simg_add.setSVGRenderer(unAddRenderer, null);
					}else{
						viewHolder.txt_add_flag.setVisibility(View.GONE);
						viewHolder.simg_add.setVisibility(View.VISIBLE);
					}
				}
				notifyDataSetChanged();
			}
			// hongrongbin
//			System.out.println("hrb   " + datas[position][0] + "  "
//					+ datas[position][1] + "  " + datas[position][2] + "  "
//					+ datas[position][3] + "  " + datas[position][4] + "  "
//					+ datas[position][5]);
			viewHolder.onClickListener.setStockCode(datas[position][0],
					datas[position][1], datas[position][3], datas[position][4], datas[position][5]);
			
			((View)viewHolder.simg_add.getParent()).setOnClickListener(viewHolder.onClickListener);
			if(!isVisible){
				viewHolder.ll_add.setVisibility(View.GONE);
				//viewHolder.txt_add_flag.setVisibility(View.GONE);
				//viewHolder.simg_add.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class AddOnClickListener implements OnClickListener {
			/**
			 * 证券代码
			 */
			private String stockCode;
			/**
			 * 市场
			 */
			private String marketId;
			/**
			 * 证券类型
			 */
			private String codeType;
			/**
			 * 证券名称
			 */
			private String stockName;
			/**
			 * 证券标识
			 */
			private String stockMark;

			public void setStockCode(String stockCode, String stockName,
					String codeType, String marketId, String stockMark) {
				this.stockCode = stockCode;
				this.stockName = stockName;
				this.codeType = codeType;
				this.marketId = marketId;
				this.stockMark = stockMark;
			}

			@Override
			public void onClick(View v) {
				if(Res.getBoolean(R.bool.kconfigs_isAddStockToSearchHistory)){
					// 添加浏览的自选股到搜索历史
					addStockToSearchHistory(stockCode, stockName, codeType, marketId, stockMark);
				}

				addUserStock(stockCode, stockName, marketId, stockMark);
				notifyDataSetChanged();
				//4.自选股同步：增加自选股请求
				/**
				 * 1.用户级别判断
				 * 2.增加自选股
				 * 3.响应失败，就弹出提示：添加/删除自选股失败，并且回退数据库的修改和界面的修改
				 */
				String stockCode_marketId = marketId + ":" + stockCode;
				Logger.i("UserStockTBServer", "自选股[新增]：stockCode_marketId："+stockCode_marketId);
			}
		}

		/**
		 * 添加自选股票
		 * 
		 * @param stockCode
		 *            证券代码
		 * @param stockName
		 *            证券名称
		 * @param marketId
		 *            市场
		 */
		protected void addUserStock(final String stockCode,
				final String stockName, final String marketId, String stockMark) {
			UserStockSQLMgr.insertData(mContext, stockName, stockCode, marketId, "", "", "0", stockMark);
			if (Res.getBoolean(R.bool.kconfigs_isTimingUploadUserStock)) {
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER, "isUserStockChange", true);
			}
		}

		private class ViewHolder {
			TextView txt_stockMark;
			TextView txt_stockName;
			TextView txt_stockCode;
			TextView txt_add_flag;
			SVGView simg_add;
			LinearLayout ll_add;
			AddOnClickListener onClickListener;
			ImageView img_stockMark;
		}
	}

	/** 自选行情网络监听者*/
	private class UserStockHQListener extends UINetReceiveListener {
		public UserStockHQListener(Activity activity) {
			super(activity);
		}

		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			//自选股同步添加
			HQZXGTBAddProtocol hqzxgtb = (HQZXGTBAddProtocol) ptl;
			if(hqzxgtb.serverErrCode!=0){
				String stockCode = hqzxgtb.req_favors.split(":")[1];
				String marketId = hqzxgtb.req_favors.split(":")[0];
				if (mActivity != null) {
					if(StringUtils.isEmpty(hqzxgtb.serverMsg)){
						SysInfo.showMessage(mActivity, "添加自选股" + stockCode + "失败");
					}else{
						SysInfo.showMessage(mActivity, hqzxgtb.serverMsg);
					}
				}
				//回退本地自选股
				rollBack(stockCode, marketId);
			}
			Logger.i("UserStockTBServer", "自选股[新增]：成功");
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			super.onShowStatus(status, msg);
			if (status != SUCCESS) {
				String stockCode = ((HQZXGTBAddProtocol)msg.getProtocol()).req_favors.split(":")[1];
				String marketId = ((HQZXGTBAddProtocol)msg.getProtocol()).req_favors.split(":")[0];
				if(mActivity != null)
					SysInfo.showMessage(mActivity, "添加自选股" + stockCode + "失败");
				//回退本地自选股
				rollBack(stockCode, marketId);
			}
		}
	}
	
	/**
	 * 回退本地数据库
	 */
	public void rollBack(String stockCode, String marketId){
		UserStockSQLMgr.deleteByStockCode(mActivity, stockCode, marketId);
		adapter.notifyDataSetChanged();
	}

	public boolean isTbzxg() {
		return isTbzxg;
	}

	public void setTbzxg(boolean isTbzxg) {
		this.isTbzxg = isTbzxg;
	}
	
	public boolean isSerachGg() {
		return isSerachGg;
	}

	public void setSerachGg(boolean isSerachGg) {
		this.isSerachGg = isSerachGg;
	}

	String stockCode;
	private void reqSearchStock(String StockCode){
		stockCode = StockCode;
		HQReq.reqNewStockData(StockCode, "new_stock", new NewStockHQListener(mActivity));
	}
	
	private class NewStockHQListener extends UINetReceiveListener {

		public NewStockHQListener(Activity activity) {
			super(activity);
		}
		
		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			super.onSuccess(msg, ptl);
			HQNewStockProtocol newStockData = (HQNewStockProtocol) ptl;
			datas = new String[newStockData.req_wCount][8];
			if(newStockData.req_wCount == 0 && searchType == 1 && NumberUtils.isDigits(stockCode)){//交易搜索
        		datas[0][0] = stockCode;
				datas[0][1] = "";
				datas[0][2] = "";
				datas[0][3] = "";
				datas[0][4] = "";
				datas[0][5] = "";
        	}else{
				for (int i = 0; i < newStockData.req_wCount; i++) {
					datas[i][0] = newStockData.stock_code;
					datas[i][1] = newStockData.stock_name;
					datas[i][2] = newStockData.stock_pinyin;
					datas[i][3] = newStockData.stock_type;
					datas[i][4] = newStockData.stock_market;
					datas[i][5] = newStockData.stock_mark;
				}
        	}
			adapter.notifyDataSetChanged();
			mHandler.sendEmptyMessage(0);
			mHandler.removeMessages(1);
			if( datas != null && datas.length == 1 && adapter.getVisible() && searchType != 1)
				mHandler.sendEmptyMessageDelayed(1, 300);
		}

		@Override
		protected void onConnError(NetMsg msg) {
		}

		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			// TODO Auto-generated method stub
			super.onShowStatus(status, msg);
		}
		
	}
	
}
