package com.romaway.android.phone.keyboardelf;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class Roma_SearchActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		Roma_SearchFragment stocksearchFrgment = new Roma_SearchFragment();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {

			// 是否是交易搜索
			stocksearchFrgment.setSearchTradeType(bundle.getBoolean("TradeSearch"));

			// 设置是否搜索港股
			if (bundle.getInt("StockQueryType") == 9) {
				stocksearchFrgment.setSerachGg(true);
			}
		}

		transaction.replace(android.R.id.content, stocksearchFrgment);
		transaction.commit();
	}
}
