package com.romaway.android.phone.keyboardelf;

import android.os.Bundle;

import com.romaway.activity.basephone.BaseSherlockFragmentActivity;

public class RomaSearchActivityNew extends BaseSherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		RomaSearchFragmentNew mKdsSearchFragment = new RomaSearchFragmentNew();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getInt("StockQueryType") == 9) {
				mKdsSearchFragment.setSerachGg(true);
			}
		}
		initFragmentForStack(mKdsSearchFragment);
	}
}
