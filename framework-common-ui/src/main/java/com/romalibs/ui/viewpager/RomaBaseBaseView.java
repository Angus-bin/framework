package com.romalibs.ui.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.romawaylibs.theme.ROMA_SkinManager;

public class RomaBaseBaseView extends FrameLayout implements OnObject, ROMA_SkinManager.OnSkinChangeListener {
	
	/**
	 * 用于数据的传递
	 */
	private Intent intent;
	public RomaBaseBaseView(Context context, Intent intent) {
		super(context);
		// TODO Auto-generated constructor stub
		onIntentChanged(intent);
		
		View ChildView = onCreateView(context, LayoutInflater.from(context), this, null);
		if(ChildView != null){
			addView(ChildView);
			onViewCreated(context, this, null);
		}
	}

	@Override
	public View onCreateView(Context context, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onViewCreated(Context context, View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 换肤相关
		onRegisterSkinListener();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// 换肤相关
		onUnRegisterSkinListener();
		
	}

	private boolean isHasOnResume = false;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if(!isHasOnResume) {// 以免每次恢复界面都会刷新皮肤
			onSkinChanged(null);
			isHasOnResume = true;
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIntentChanged(Intent intent) {
		// TODO Auto-generated method stub
		if(intent == null)
			return;
		this.intent = intent;
	}

	@Override
	public Intent getIntent() {
		// TODO Auto-generated method stub
		return intent;
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
	
	@Override
	public void onSkinChanged(String skinName) {
		// TODO Auto-generated method stub
		
	}

}
