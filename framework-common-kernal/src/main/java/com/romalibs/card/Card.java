package com.romalibs.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 卡片式开发的基类
 * @author wanlh
 *
 */
public interface Card {

	
	/**
	 * 获取卡片视图类
	 * @param inflater
	 * @return
	 */
	public View inView(LayoutInflater inflater);
	
	/**
	 * 获取卡片视图类
	 * @param inflater
	 * @param layoutResId 自定义布局资源ID,适合界面数据结构一样，仅仅是布局展示不一样的情况
	 * @return
	 */
	public View inView(LayoutInflater inflater, int layoutResId);
	
	
	/**
	 * 事件处理模型接口
	 * @param event 事件类型
	 */
	public void onEvent(CardEvent event);
	
	public void onResume();
	
	public void onPause();
	
	/**
	 * 用来给卡片传递数据进行界面展示的
	 * @param bundle
	 * @param optionBundle
	 */
	public void push(Bundle bundle, Bundle optionBundle);
}
