package com.romalibs.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface ROMA_BaseView {
	/**
	 * 获取视图
	 * @param inflater
	 * @param container
	 * @return
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container);
	
	public void onViewCreated(View view, Bundle savedInstanceState);
	
	public void onResume();
	
	public void onPause();
	
	/**销毁视图调用*/
	public void onDestroy();
}
