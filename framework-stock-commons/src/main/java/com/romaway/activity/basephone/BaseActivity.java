package com.romaway.activity.basephone;

import com.romaway.android.phone.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.romaway.common.android.phone.ISubTabView;

public class BaseActivity extends Activity implements ISubTabView{

	private LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
	}
	
	/***
	 * 
	 * @param titleLayoutResID  顶部栏布局资源
	 * @param contentLayoutResId 主界面布局资源
	 * @param bottomLayoutResId  底部栏布局资源
	 */
	public void setContentView(int titleLayoutResID, int contentLayoutResId, int bottomLayoutResId) {
		// TODO Auto-generated method stub
		
		FrameLayout root = (FrameLayout)inflater.inflate(R.layout.roma_mbp_base_layout, null);
		
		if(titleLayoutResID > 0){
			View title = inflater.inflate(titleLayoutResID, null);
			((FrameLayout)root.findViewById(R.id.title)).addView(title);
		}
		
		if(contentLayoutResId > 0){
			View bottom = inflater.inflate(contentLayoutResId, null);
			((FrameLayout)root.findViewById(R.id.content)).addView(bottom);
		}
		
		if(bottomLayoutResId > 0){
			View bottom = inflater.inflate(bottomLayoutResId, null);
			((FrameLayout)root.findViewById(R.id.bottom)).addView(bottom);
		}
		
		super.setContentView(root);
	}


	@Override
	public boolean AddZixuan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delZiXuan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showOrHideAddStock(boolean showAdd, boolean hideAll) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentSubView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fastTrade() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFromID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getModeID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEnableFastTrade() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Activity getCurrentAct() {
		// TODO Auto-generated method stub
		return null;
	}

}
