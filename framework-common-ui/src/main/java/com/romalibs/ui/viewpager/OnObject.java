package com.romalibs.ui.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface OnObject {
	/**
	 * 获取视图
	 * @param inflater
	 * @param container
	 * @return
	 */
	public View onCreateView(Context context, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	public void onViewCreated(Context context, View view, Bundle savedInstanceState);
	
	/**
	 * 设置intent 用于数据传递
	 * @param intent
	 */
	public void onIntentChanged(Intent intent);
	/**
	 * 获取Intent，获取携带的数据
	 * @return
	 */
	public Intent getIntent();
	/**
	 * 暂停视图
	 */
	public void onResume();
	
	/**
	 * 暂停视图
	 */
	public void onPause();
	
	/**
	 * 销毁视图调用
	 */
	public void onDestroy();
}
