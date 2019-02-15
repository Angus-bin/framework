package com.romaway.android.phone.keyboardelf;

import android.os.Bundle;

import com.romaway.activity.basephone.BaseSherlockFragmentActivity;

public class RomaSearchActivity extends BaseSherlockFragmentActivity {

	private RomaSearchFragment mRomaSearchFragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mRomaSearchFragment = new RomaSearchFragment();

		initFragmentForStack(mRomaSearchFragment);
	}
}
