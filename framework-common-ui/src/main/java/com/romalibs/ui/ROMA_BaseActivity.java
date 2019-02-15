package com.romalibs.ui;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romalibs.widget.UICenterProgressLayout;
import com.romaway.libs.R;
import com.szkingdom.stocksearch.keyboard.KDS_KeyboardManager;
import com.szkingdom.stocksearch.keyboard.Kds_KeyBoardView;
import com.romawaylibs.theme.ROMA_SkinManager;
import com.umeng.analytics.MobclickAgent;

/** 
 * 主界面的基类
 * @author  万籁唤
 * 创建时间：2016年3月30日 下午11:00:15 
 * @version 1.0 
 */
public class ROMA_BaseActivity extends Activity implements ROMA_SkinManager.OnSkinChangeListener{
	private LayoutInflater inflater;
	private TextView titleTextView;
	private TextView rightBt;
	private TextView backBt;
	private FrameLayout titleParent;
	private UICenterProgressLayout contentParent;
	private int titleLayoutResID = -1;
	private View titleView = null;
	private int titleVisibility = View.VISIBLE;
	private boolean isFloatTitleBar = false;
	private boolean isDefaultTitleFlag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		
		setContentView(-1, -1);
		
		onRegisterSkinListener();
	}

	// =============== 自定义键盘接口 ================
	private Kds_KeyBoardView kdsKeyBoardView;
	private KDS_KeyboardManager keyboardManager;
	private RelativeLayout fl_keyboard_parent;

	private void initKeyBoard() {
		fl_keyboard_parent = (RelativeLayout) findViewById(R.id.fl_keyboard_root);
		if (kdsKeyBoardView == null) {
			kdsKeyBoardView = new Kds_KeyBoardView(this);
			RelativeLayout.LayoutParams lp =
					new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT,
							RelativeLayout.LayoutParams.WRAP_CONTENT);
			kdsKeyBoardView.setLayoutParams(lp);
			kdsKeyBoardView.setVisibility(View.GONE);
			fl_keyboard_parent.addView(kdsKeyBoardView);
		}

		if (keyboardManager == null)
			keyboardManager = new KDS_KeyboardManager(
					this,
					contentParent,
					kdsKeyBoardView);
	}

	/**
	 * 获取自定义键盘管理器，用来加载自定义键盘
	 * @return
     */
	public KDS_KeyboardManager getKeyboardManager(){
		if(keyboardManager != null)
			return keyboardManager;

		initKeyBoard();
		return keyboardManager;
	}
	// =============== 自定义键盘接口 ================

	private boolean isHasOnResume = false;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(!isHasOnResume) {// 以免每次恢复界面都会刷新皮肤
		    onSkinChanged(null);
			isHasOnResume = true;
		}
		
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		onUnRegisterSkinListener();
		
		super.onDestroy();
	}

	/**
	 * 是否是默认的 标题栏
	 * @return
	 */
	public boolean isDefaultTitleFlag(){
		return isDefaultTitleFlag;
	}

	/**
	 * 设置添加主界面接口 
	 * @param titleLayoutResID 自定义标题栏的布局资源 ID。<br>
	 *        -1 去掉自定义标题栏；0采用默认 title；
	 * @param layoutResID 添加主界面视图布局资源 ID
	 */
	public void setContentView(int titleLayoutResID, int layoutResID) {
		this.titleLayoutResID = titleLayoutResID;
		setContentView(layoutResID);
	}
	
	/**
	 * 设置添加主界面接口
	 * @param titleLayoutResID 自定义标题栏的布局资源ID
	 * @param view 添加主界面的视图对象
	 */
	public void setContentView(int titleLayoutResID, View view) {
		this.titleLayoutResID = titleLayoutResID;
		setContentView(view);
	}

	/**
	 * 设置添加主界面接口
	 * @param titleView 自定义标题栏视图
	 * @param layoutResID 添加主界面视图布局资源 ID
	 */
	public void setContentView(View titleView, int layoutResID) {
		this.titleView = titleView;
		setContentView(layoutResID);
	}

	/**
	 * 设置添加主界面接口
	 * @param titleView 自定义标题栏视图
	 * @param view 添加主界面的视图对象
	 */
	public void setContentView(View titleView, View view) {
		this.titleView = titleView;
		setContentView(view);
	}
	
	/**
	 * 设置标题栏的可见性
	 * @param visibility
	 */
	public void setTitleVisibility(int visibility){
		titleVisibility = visibility;
	}
	
	/**
	 * 设置添加主界面的布局视图
	 */
	@Override
	public void setContentView(int layoutResID) { 
		// TODO Auto-generated method stub
		if(contentParent == null || titleParent == null){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.setContentView(isFloatTitleBar ? R.layout.roma_libs_base_ui_float_layout : R.layout.roma_libs_base_ui_layout);
			//初始化title
			titleParent = (FrameLayout) this.findViewById(R.id.fl_title);
			
			//初始化content
			contentParent = (UICenterProgressLayout) this.findViewById(R.id.content);
		}
		
		if(layoutResID > 0){
			inflater.inflate(layoutResID, contentParent, true);
			
			titleParent.setVisibility(titleVisibility);
			if(titleView == null && titleLayoutResID < 0)
				titleLayoutResID = 0;//采用默认的title
		}
		
		if(titleVisibility != View.VISIBLE) {
			titleLayoutResID = -1;
			titleView = null;
		}

		if(titleLayoutResID == 0){
			titleLayoutResID = R.layout.roma_libs_base_activity_titlebar;
			inflater.inflate(titleLayoutResID, titleParent, true);

		}else if(titleView != null) {
			titleParent.addView(titleView);

		}else if(titleLayoutResID > 0){
			inflater.inflate(titleLayoutResID, titleParent, true);
			
		}else if(titleLayoutResID < 0){//去除title
			titleParent.removeAllViews();
			
		}
		
		titleTextView = (TextView) this.findViewById(R.id.title);
		rightBt = (TextView) this.findViewById(R.id.right);
		backBt = (TextView) this.findViewById(R.id.back);
		if(backBt != null)
		backBt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		if(titleLayoutResID == R.layout.roma_libs_base_activity_titlebar)
			isDefaultTitleFlag = true;
		else
			isDefaultTitleFlag = false;
	}
	
	/**
	 * 设置添加主界面的布局视图
	 */
	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		if(contentParent != null){
			
			titleLayoutResID = R.layout.roma_libs_base_activity_titlebar;
			inflater.inflate(titleLayoutResID, titleParent, true);
			
			titleTextView = (TextView) this.findViewById(R.id.title);
			rightBt = (TextView) this.findViewById(R.id.right);
			backBt = (TextView) this.findViewById(R.id.back);
			if(backBt != null)
			backBt.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onBackPressed();
				}
			});
			
			contentParent.addView(view);
		}else
			super.setContentView(view);
	}

	/**
	 * 设置标题栏背景颜色
	 * @param color 背景颜色，如0xffffffff
	 */
	public void setTitleBackgroundColor(int color){
		titleParent.setBackgroundColor(color);
	}
	
	/**
	 * 获取标题栏右边按钮
	 * @return 标题栏右边按钮实例对象
	 */
	public TextView getRightBtn(){
		return rightBt;
	}
	/**
	 * 获取标题栏左边按钮
	 * @return 标题栏左边按钮实例对象
	 */
	public TextView getLeftBtn(){
		return backBt;
	}
	
	/**
	 * 仅仅隐藏左上角按钮
	 */
	public void hideBackBtn() {
		if(backBt != null)
			backBt.setVisibility(View.GONE);
	}
	
	/**
	 * 仅仅显示出左上角按钮，当标题栏是隐藏状态，该按钮显示无效
	 */
	public void showBackBtn() {
		if(backBt != null)
			backBt.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置自定义title布局
	 * @param titleResId
	 */
	public void setTitleView(int titleResId){
		if(titleResId > 0 && titleParent != null){
			titleParent.removeAllViews();
			inflater.inflate(titleLayoutResID, titleParent, true);
		}
	}
	
	/**
	 * 设置标题栏文本
	 */
	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		//super.setTitle(title);
		if(titleTextView != null)
			titleTextView.setText(title.toString());
	}
	
	/**
	 * 设置标题栏文本
	 */
	@Override
	public void setTitle(int titleId) {
		// TODO Auto-generated method stub
		//super.setTitle(titleId);
		if(titleTextView != null)
			titleTextView.setText(this.getResources().getString(titleId));
	}
	
	/**
	 * 设置标题栏显示文本的颜色
	 */
	@Override
	public void setTitleColor(int textColor) {
		// TODO Auto-generated method stub
		//super.setTitleColor(textColor);
		if(titleTextView != null)
			titleTextView.setTextColor(textColor);
	}
	
	/**
	 * 获取标题栏父容器
	 * @return
	 */
	public ViewGroup getTitleParent(){
		return titleParent;
	}
	
	/**
	 * 获取主页面的根容器
	 * @return 主页面的根容器
	 */
	public ViewGroup getContentParent(){
		return contentParent;
	}
	
	/**
	 * 获取界面中间显示部分,可以用来处理界面的数据请求状态以及网络状态等<br>
	 * 屏幕中间会根据不同状态显示不同内容，相关对象的接口如下，<br>
	 * showLoading() 屏幕中间会显示数据正在加载中的进度条。<br>
	 * showEmpty()   屏幕中间会显示暂无数据。<br>
	 * showError()  屏幕中间会显示错误，比如网络未连接时。<br>
	 * showContent() 数据加载完毕，展示主界面<br>
	 * @return 主界面的跟容器对象，该方法对象常用来处理状态页面展示<br>
	 */
	public UICenterProgressLayout getUiCenter(){
		return contentParent;
	}
	
	/**
	 * 隐藏整个标题栏，可用来设置无标题栏的页面
	 */
	public void hideTitle(){
		titleParent.setVisibility(View.GONE);
	}
	
	/**
	 * 显示整个标题栏，默认是显示状态
	 */
	public void showTitle(){
		titleParent.setVisibility(View.VISIBLE);
	}

	/**
	 * 整个标题栏样式: 是否为相对悬浮的标题栏
	 */
	public void setTitleBarStyle(boolean isFloatTitleBar){
		this.isFloatTitleBar = isFloatTitleBar;
	}
	
	/**
	 * 用来监听物理返回按键，可通过子类复现该方法截获返回按键
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		finish();
	}

	/**
	 * 用来注册换肤功能的统一方法
	 */
	public void onRegisterSkinListener(){
		
	}
	
	/**
	 * 用来注册换肤功能的统一方法
	 */
	public void onUnRegisterSkinListener(){
		
	}
	
	/**
	 * 换肤监听回调方法，换肤功能统一复写该方法，禁止在其他相关位置实现
	 */
	@Override
	public void onSkinChanged(String skinName) {
		// TODO Auto-generated method stub
		// 统一设置 contentParent 的皮肤
		
		contentParent.setEmptyStateColor(
				ROMA_SkinManager.getColor("YT_libs_ui_center_titleColor", 0xff888888),
				ROMA_SkinManager.getColor("YT_libs_ui_center_titleColor", 0xff888888));
		
		contentParent.setErrorStateColor(
				ROMA_SkinManager.getColor("YT_libs_ui_center_titleColor", 0xff888888),
				ROMA_SkinManager.getColor("YT_libs_ui_center_titleColor", 0xff888888));
	}
}
