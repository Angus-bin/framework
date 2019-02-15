package com.romaway.android.phone.view;

import java.util.List;

import android.view.View;
import android.widget.BaseAdapter;

public abstract class MyAdapter<T> extends BaseAdapter {

	private List<T> mList;
	
	public MyAdapter(List<T> list) {
		this.mList = list;
	}
	
	@Override
	public int getCount() {
		
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public abstract View getView();
	
}
